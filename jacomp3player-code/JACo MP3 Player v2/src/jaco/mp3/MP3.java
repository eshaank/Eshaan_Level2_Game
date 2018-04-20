/*
 * Copyright (C) <2010> Cristian Sulea ( http://cristiansulea.entrust.ro )
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package jaco.mp3;

import jaco.mp3.resources.Decoder;
import jaco.mp3.resources.Frame;
import jaco.mp3.resources.SampleBuffer;
import jaco.mp3.resources.SoundStream;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.SourceDataLine;

/**
 * Java MP3
 * 
 * @version 1.1.0, June 09, 2011
 * @author Cristian Sulea ( http://cristiansulea.entrust.ro )
 */
public class MP3 {

  private static final Logger LOGGER = Logger.getLogger(MP3.class.getName());

  private File file;
  private URL url;

  private volatile SourceDataLine source;

  private volatile boolean isPaused = false;
  private volatile boolean isStopped = true;

  private volatile int volume = 25;
  private volatile int sourceVolume = 0;

  private volatile Thread playThread;

  public MP3(String file) {
    this(new File(file));
  }

  public MP3(File file) {
    this.file = file;
  }

  public MP3(URL url) {
    this.url = url;
  }

  /**
   * Starts the play (or resume if is paused).
   */
  public void play() {

    if (isPaused) {

      isPaused = false;

      synchronized (MP3.this) {
        MP3.this.notifyAll();
      }

      return;
    }

    if (playThread == null) {

      playThread = new Thread() {
        public void run() {

          isStopped = false;

          Decoder decoder = new Decoder();

          SoundStream stream = null;

          try {
            if (file != null) {
              stream = new SoundStream(new FileInputStream(file));
            } else if (url != null) {
              stream = new SoundStream(url.openStream());
            }
          } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "unable to open the sound stream", e);
          }

          if (stream != null) {

            while (true) {

              if (isStopped) {
                break;
              }

              if (isPaused) {

                if (source != null) {
                  source.flush();
                }

                sourceVolume = volume;

                synchronized (MP3.this) {
                  try {
                    MP3.this.wait();
                  } catch (InterruptedException e) {}
                }

                continue;
              }

              try {

                Frame frame = stream.readFrame();

                if (frame == null) {
                  break;
                }

                if (source == null) {

                  int frequency = frame.frequency();
                  int channels = (frame.mode() == Frame.SINGLE_CHANNEL) ? 1 : 2;

                  AudioFormat format = new AudioFormat(frequency, 16, channels, true, false);
                  Line line = AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));

                  source = (SourceDataLine) line;
                  source.open(format);
                  source.start();

                  setVolume(source, sourceVolume = 0);
                }

                SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frame, stream);

                short[] buffer = output.getBuffer();
                int offs = 0;
                int len = output.getBufferLength();

                if (sourceVolume != volume) {

                  if (sourceVolume > volume) {
                    sourceVolume -= 10;
                    if (sourceVolume < volume) {
                      sourceVolume = volume;
                    }
                  } else {
                    sourceVolume += 10;
                    if (sourceVolume > volume) {
                      sourceVolume = volume;
                    }
                  }

                  setVolume(source, sourceVolume);
                }

                source.write(toByteArray(buffer, offs, len), 0, len * 2);

                stream.closeFrame();
              }

              catch (Exception e) {
                LOGGER.log(Level.WARNING, "unexpected problems while playing " + toString(), e);
                break;
              }
            }

            //
            // source is null at this point only if first frame is null
            // this means that probably the file is not a mp3

            if (source == null) {
              LOGGER.log(Level.INFO, "source is null because first frame is null, so probably the file is not a mp3");
            }

            else {

              if (!isStopped) {
                source.drain();
              } else {
                source.flush();
              }

              source.stop();
              source.close();

              source = null;
            }

            try {
              stream.close();
            } catch (Exception e) {}
          }

          isPaused = false;
          isStopped = true;

          playThread = null;
        }
      };

      playThread.start();
    }
  }

  public void joinPlayThread() {
    try {
      playThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Starts the play (or resume if is paused) at the specified volume.
   * 
   * @see #setVolume(int)
   * @see #play()
   */
  public void play(int volume) {
    setVolume(volume);
    play();
  }

  public boolean isPlaying() {
    return !isPaused && !isStopped;
  }

  public void pause() {
    isPaused = true;
    synchronized (MP3.this) {
      MP3.this.notifyAll();
    }
  }

  public boolean isPaused() {
    return isPaused;
  }

  public void stop() {
    isStopped = true;
    synchronized (MP3.this) {
      MP3.this.notifyAll();
    }
  }

  public boolean isStopped() {
    return isStopped;
  }

  /**
   * Sets the volume for the source of this {@link MP3}. The value is actually
   * the percent value, so the value must be in interval [0..100] or a runtime
   * exception will be throw.
   * 
   * @param volume
   * 
   * @throws RuntimeException
   *           if the volume is not in interval [0..100]
   */
  public void setVolume(int volume) {

    if (volume < 0 || volume > 100) {
      throw new IllegalArgumentException("Wrong value for volume, must be in interval [0..100].");
    }

    this.volume = volume;
  }

  /**
   * Returns the actual volume.
   */
  public int getVolume() {
    return volume;
  }

  private void setVolume(SourceDataLine source, int volume) {

    try {

      FloatControl gainControl = (FloatControl) source.getControl(FloatControl.Type.MASTER_GAIN);
      BooleanControl muteControl = (BooleanControl) source.getControl(BooleanControl.Type.MUTE);

      if (volume == 0) {
        muteControl.setValue(true);
      } else {
        muteControl.setValue(false);
        gainControl.setValue((float) (Math.log(volume / 100d) / Math.log(10.0) * 20.0));
      }
    }

    catch (Exception e) {
      LOGGER.log(Level.WARNING, "unable to set the volume to the source", e);
    }
  }

  /**
   * Retrieves the position in milliseconds of the current audio sample being
   * played. This method delegates to the <code>
   * AudioDevice</code> that is used by this player to sound the decoded audio
   * samples.
   */
  public int getPosition() {
    int pos = 0;
    if (source != null) {
      pos = (int) (source.getMicrosecondPosition() / 1000);
    }
    return pos;
  }

  @Override
  public String toString() {

    if (file != null) {
      return file.getAbsolutePath();
    } else if (url != null) {
      return url.toString();
    }

    return super.toString();
  }

  private byte[] toByteArray(short[] ss, int offs, int len) {
    byte[] bb = new byte[len * 2];
    int idx = 0;
    short s;
    while (len-- > 0) {
      s = ss[offs++];
      bb[idx++] = (byte) s;
      bb[idx++] = (byte) (s >>> 8);
    }
    return bb;
  }

}
