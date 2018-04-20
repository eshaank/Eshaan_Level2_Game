
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int INSTRUCT_STATE = 2;
	final int END_STATE = 3;
	final int WIN_STATE = 4;
	int currentState = MENU_STATE;
	int winX = 2;
	int winY = 2;
	int WinX = 0;
	int WinY = 241;
	int screenY = 841;
	int screenX = 490;
	int score;
	float sec;
	int highScore;
	Timer timer;
	Font titleFont;
	Font titleFont2;
	Font titleFont3;
	Stickman stickman;
	JButton button;
	SongPlayer music;
	SongPlayer music2;
	SongPlayer music3;

	ObjectManager manager;
	private Listener listener;
	public static BufferedImage main2Img;

	GamePanel() {
		init();

		titleFont = new Font("Ariel", Font.PLAIN, 54);
		titleFont2 = new Font("Ariel", Font.PLAIN, 30);
		titleFont3 = new Font("Ariel", Font.PLAIN, 25);

		try {
			main2Img = ImageIO.read(this.getClass().getResourceAsStream("Main2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		timer = new Timer(1000 / 60, this.listener);
	}

	public Listener getListener() {
		return listener;
	}

	void init() {

		score = 0;
		sec = 0;
		this.listener = new Listener();
		manager = new ObjectManager();
		ObjectManager.initLanes();
		stickman = new Stickman(200, 865, 30, 30);
		manager.addObject(stickman);

	}

	void startGame() {

		timer.start();
		music = new SongPlayer("menuMusic.mp3");

	}

	public void paintComponent(Graphics g) {

		if (currentState == MENU_STATE) {
			drawMenuState(g);
		}
		if (currentState == GAME_STATE) {
			drawGameState(g);
		}
		if (currentState == END_STATE) {
			drawEndState(g);
		}
		if (currentState == INSTRUCT_STATE) {
			drawInstructState(g);
		}
		if (currentState == WIN_STATE) {
			drawWinState(g);
		}

	}

	void drawMenuState(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 900);

		g.setFont(titleFont);
		g.setColor(Color.MAGENTA);
		g.drawString("Falling Sky", 100, 200);

		g.setColor(Color.BLUE);
		g.fillRect(190, 50, 100, 25);

		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Start", 205, 75);

		g.setColor(Color.BLUE);
		g.fillRect(150, 110, 190, 25);

		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Instructions", 160, 135);

		g.setFont(titleFont3);
		g.setColor(Color.ORANGE);
		g.drawString("Click on Start to play the game", 45, 300);

		g.setFont(titleFont3);
		g.setColor(Color.MAGENTA);
		g.drawString("Click Instructions for How To Play", 25, 400);

	}

	void drawGameState(Graphics g) {
		if (button != null) {
			remove(button);
			button = null;
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 900);
		stickman.draw(g);
		manager.draw(g);
		sec += 0.1;
		score = Math.round(sec);
		g.drawString("Score: " + score, 30, 30);
		g.drawString("Highscore: " + highScore, 30, 60);
		if (score > highScore) {
			highScore = score;
		}
	}

	void drawEndState(Graphics g) {
		if(music2 != null) {
		music2.stop();
		}
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 900);

		g.setFont(titleFont3);
		g.setColor(Color.RED);
		g.drawString("GAME OVER", 175, 241);

		g.setColor(Color.BLUE);
		g.fillRect(185, 350, 115, 25);

		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Restart", 190, 375);
		g.setFont(titleFont2);
		g.setColor(Color.BLACK);

		g.setFont(titleFont2);
		g.setColor(Color.blue);
		g.drawString("Your score was " + score + ".", 100, 100);

		g.setFont(titleFont2);
		g.setColor(Color.RED);
		g.drawString("The highscore is " + highScore + ".", 90, 150);
	}

	void drawInstructState(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 900);
		g.setFont(titleFont);
		g.setColor(Color.MAGENTA);
		g.drawString("Objective", 30, 80);

		g.setFont(titleFont3);
		g.setColor(Color.WHITE);
		g.drawString("Try to get to the top of the screen", 10, 110);
		g.setColor(Color.GRAY);
		g.drawString("GRAY BLOCKS: Kill the character", 10, 140);

		g.setColor(Color.RED);
		g.drawString("RED BLOCKS: Moves the character up ", 10, 170);
		g.drawString("one level", 10, 200);

		g.setColor(Color.BLUE);
		g.drawString("BLUE BLOCKS: Moves the character up ", 10, 230);
		g.drawString("one level, as well as speeds up the", 10, 260);
		g.drawString("character's movement", 10, 290);

		g.setColor(Color.WHITE);
		g.drawString("WHITE BLOCKS: Subtracts spawn time ", 10, 320);
		g.drawString("for all blocks", 10, 350);

		g.setFont(titleFont);
		g.setColor(Color.MAGENTA);
		g.drawString("How to Play", 30, 400);

		g.setFont(titleFont3);
		g.setColor(Color.ORANGE);
		g.drawString("Use the left <-- arrow key to move to", 10, 430);
		g.drawString("the left", 10, 460);
		g.drawString("Use the right --> arrow key to move to", 10, 490);
		g.drawString("the right", 10, 520);

		g.setColor(Color.BLUE);
		g.fillRect(345, 870, 140, 25);

		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Start -->", 350, 895);

	}

	void drawWinState(Graphics g) {

		WinX += winX;
		if (WinX >= screenX) {
			WinX = 0;
			WinY += 100;

		}
		if (WinY >= screenY) {
			WinX = 0;
			WinY -= 100;
		}

		music2.stop();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 900);

		g.setFont(titleFont3);
		g.setColor(new Color(0, 255, 255));
		g.drawString("You Win :D", WinX, WinY);
		g.setFont(titleFont2);
		g.setColor(Color.CYAN);
		g.drawString("Your score was " + score + ".", 100, 100);

		g.setFont(titleFont2);
		g.setColor(Color.RED);
		g.drawString("The highscore is " + highScore + ".", 90, 150);

		g.setColor(Color.BLUE);
		g.fillRect(345, 870, 140, 25);

		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Start -->", 350, 895);
	}

	void updateGameState() {

		manager.checkCollision();
		manager.update();
		manager.manageEnemies();
	}

	class Listener extends ListenerAdapter {
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if (currentState == GAME_STATE) {
				updateGameState();
			}

			if (stickman.isAlive == false) {
				currentState = END_STATE;
			}
			if (stickman.y < 0) {
				currentState = WIN_STATE;
			}
			repaint();

		}

		@Override
		public void keyPressed(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				stickman.rightKeyPressed = true;

			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				stickman.leftKeyPressed = true;
			}

			updateGameState();
		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				stickman.leftKeyPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				stickman.rightKeyPressed = false;

			}

		}

		@Override
		public void mousePressed(MouseEvent e) {

			if (MENU_STATE == currentState) {
				if (e.getX() > 190 && e.getX() < 290 && e.getY() > 70 && e.getY() < 100) {
					currentState = GAME_STATE;
					music.stop();
					music2 = new SongPlayer("Tetris.mp3");
				}
				if (e.getX() > 150 && e.getX() < 340 && e.getY() > 135 && e.getY() < 160) {
					currentState = INSTRUCT_STATE;
				}
			}

			if (END_STATE == currentState) {
				if (e.getX() > 185 && e.getX() < 301 && e.getY() > 375 && e.getY() < 400) {
					init();
					currentState = GAME_STATE;
					music2 = new SongPlayer("Tetris.mp3");

				}
			}

			if (INSTRUCT_STATE == currentState) {
				if (e.getX() > 345 && e.getX() < 485 && e.getY() > 895 && e.getY() < 916) {
					currentState = GAME_STATE;
					music.stop();
					music2 = new SongPlayer("Tetris.mp3");
				}
			}

			if (WIN_STATE == currentState) {
				if (e.getX() > 345 && e.getX() < 485 && e.getY() > 895 && e.getY() < 916) {
					currentState = GAME_STATE;
					init();
					music2 = new SongPlayer("Tetris.mp3");
				}

			}
		}

	}
}