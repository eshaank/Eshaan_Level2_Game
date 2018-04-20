  
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StickmanParkour extends JPanel {
	
	GamePanel panel;
	JFrame frame;
	static int width = 490;
	int height = 920;
	

	StickmanParkour() {
		panel = new GamePanel();
		frame = new JFrame();
		setup();
	}

	public static void main(String[] args) {
		 new StickmanParkour();
	}

	void setup() {
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.addKeyListener(panel.getListener());
		frame.addMouseListener(panel.getListener());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.startGame();

	}

}
