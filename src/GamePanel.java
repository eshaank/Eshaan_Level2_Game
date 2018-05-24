
import java.applet.AudioClip;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

//http://freesound.org/people/B_Lamerichs/sounds/264057/
//game over music

//https://www.youtube.com/watch?v=9Fv5cuYZFC0
//Game music



//MAKE LEVELS:
//   BLACK ----------> Black blocks push you all the way down to the bottom
// Make the blocks appear after a certain y point, and make the map endless. Add score thingy too
public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
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
	int stageY = 841;
	int stageX = 490;
	int score = 0;
	float sec = 0;
	int highScore;
	Timer timer;
	Font titleFont;
	Font titleFont2;
	Font titleFont3;
	Stickman stickman;
	JButton button;
	SimplePlayer music;
	SimplePlayer music2;
	SimplePlayer music3;
	
	ObjectManager manager = new ObjectManager();
	// public static BufferedImage alienImg;
	public static BufferedImage main2Img;
	// public static BufferedImage FallingBImg;
	// public static BufferedImage fallingImg;

	// public static BufferedImage bulletImg;
	GamePanel() {
		Random random = new Random();
		init();
		
		titleFont = new Font("Ariel", Font.PLAIN, 54);
		titleFont2 = new Font("Ariel", Font.PLAIN, 30);
		titleFont3 = new Font("Ariel", Font.PLAIN, 25);
		
		
		try {
			main2Img = ImageIO.read(this.getClass().getResourceAsStream("Main2.jpg"));
			// FallingBImg =
			// ImageIO.read(this.getClass().getResourceAsStream("FallingBlocks.jpg"));
			// fallingImg =
			// ImageIO.read(this.getClass().getResourceAsStream("falling.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		timer = new Timer(1000 / 60, this);

	}
void init(){
	 score = 0;
	 sec = 0;
 manager = new ObjectManager();
 ObjectManager.initLanes();
 stickman = new Stickman(200, 865, 30, 30);
 manager.addObject(stickman);
}
	void startGame() {

		timer.start();
		music = new SimplePlayer("menuMusic.mp3");
		
		
		
		

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
		// blocks.draw(g);
		manager.draw(g);
		sec += 0.1;
		score = Math.round(sec);
		g.drawString("Score: "+score  , 30, 30);
		g.drawString("Highscore: " + highScore , 30, 60);
		//System.out.println(score +=1);

		if (score > highScore ) {
			highScore = score;
		}
	}

	void drawEndState(Graphics g) {
		music2.stop();
		//music = new SimplePlayer("endGame.mp3");
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
		
//		if (score > highScore ) {
//			highScore = score;
//		}
		g.setFont(titleFont2);
		g.setColor(Color.blue);
		g.drawString("Your score was " +score + ".", 100, 100);

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
		
		
		//g.fillRect(x, y, width, height);
		g.setColor(Color.BLUE);
		g.fillRect(345, 870, 140, 25);

		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Start -->", 350, 895);

		
	}
	void drawWinState(Graphics g) {
<<<<<<< HEAD
		// 250 is the X coordinate for where the "l" in "You beat this level" touches the edge of the screen.
=======

		
>>>>>>> 71d7be40f8447ff1cc0a5184389cbd23a0cc1185
	WinX += winX;
if (WinX >= stageX) {
	WinX =0;
	WinY += 100;
	
}
if (WinY >= stageY) {
	WinX = 0;
	WinY -= 100;
}
music2.stop();
		//System.out.println("HELLO");
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 900);
		
		g.setFont(titleFont3);
		g.setColor(new Color(0, 255, 255));
		g.drawString("You Win :D", WinX , WinY);
		g.setFont(titleFont2);
		g.setColor(Color.CYAN);
		g.drawString("Your score was " +score + ".", 100, 100);
	
		
//		if (score > highScore ) {
//			highScore = score;
//		}
		g.setFont(titleFont2);
		g.setColor(Color.RED);
		g.drawString("The highscore is " + highScore + ".", 90, 150);
		
		g.setColor(Color.BLUE);
		g.fillRect(345, 870, 140, 25);
		
		g.setColor(Color.WHITE);
		g.setFont(titleFont2);
		g.drawString("Start -->", 350, 895);
	}
//	private void menuMusic() {
//		
//		
//		AudioClip sound = JApplet.newAudioClip(getClass().getResource("menu.wav"));
//			
//		sound.play();
//		
//	}

	void updateMenuState() {
		
	}

	void updateGameState() {

		manager.checkCollision();
		manager.update();
		manager.manageEnemies();
	}

	void updateEndState() {

	}

	void updateInstructState() {

	}
void updateWinState() {
	
}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (currentState == MENU_STATE) {
			updateMenuState();
		} else if (currentState == GAME_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		}
		else 	if (currentState == INSTRUCT_STATE) {
			updateInstructState();
		}
		else if (currentState == WIN_STATE) {
			updateWinState();
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("keyTyped");

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		


		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			stickman.rightKey = true;

		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			stickman.leftKey = true;
		}
	
		updateGameState();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// System.out.println("keyReleased");
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			stickman.leftKey = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			stickman.rightKey = false;

		}
	
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		 System.out.println(e.getX());
		 System.out.println(e.getY());

		if (MENU_STATE == currentState) {
			if (e.getX() > 190 && e.getX() < 290 && e.getY() > 70 && e.getY() < 100) {
				currentState = GAME_STATE;
				music.stop();
				music2 = new SimplePlayer("Tetris.mp3");
			}
		}
		if (MENU_STATE == currentState) {
			if (e.getX() > 150 && e.getX() < 340 && e.getY() > 135 && e.getY() < 160) {
				
				currentState = INSTRUCT_STATE;
				
			}
		}
		if (END_STATE == currentState) {
			if (e.getX() > 185 && e.getX() < 301 && e.getY() > 375 && e.getY() < 400) {
				init();
				currentState = GAME_STATE;
				music2 = new SimplePlayer("Tetris.mp3");

			}
		}
		if (INSTRUCT_STATE == currentState) {
			if (e.getX() > 345 && e.getX() < 485 && e.getY() > 895 && e.getY() < 916) {
				currentState = GAME_STATE;
				music.stop();
				music2 = new SimplePlayer("Tetris.mp3");
			}
			
		}
		if (WIN_STATE == currentState) {
			if (e.getX() > 345 && e.getX() < 485 && e.getY() > 895 && e.getY() < 916) {
				currentState = GAME_STATE;
				init();
				music2 = new SimplePlayer("Tetris.mp3");
			}
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}