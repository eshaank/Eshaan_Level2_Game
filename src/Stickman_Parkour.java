  
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import FinalJavaProject.GamePanel;

public class Stickman_Parkour extends JPanel implements ActionListener, KeyListener {
	GamePanel panel;

	JFrame frame;
	static int width = 490;
	int height = 920;

	Stickman_Parkour() {
		panel = new GamePanel();
		frame = new JFrame();
		setup();
	}

	public static void main(String[] args) {
		new Stickman_Parkour();

	}

	void setup() {
		frame.add(panel);
		frame.addKeyListener(panel);
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.addMouseListener(panel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.startGame();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
