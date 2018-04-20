import java.io.File;
import jaco.mp3.player.MP3Player;

public class SongPlayer {
	MP3Player playMP3;

	public SongPlayer(String song) {
		try {
			playMP3 = new MP3Player(new File(this.getClass().getResource(song).toURI()));
			playMP3.play();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void stop() {
		playMP3.pause();

	}

}
