import java.awt.Graphics;

public class Stickman extends GameObject {
	
	float speed;
	int ground = 840;
	boolean rightKeyPressed;
	boolean leftKeyPressed;

	Stickman(int x, int y, int height, int width) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		speed = 5;
	}

	void update() {
		super.update();

		if (rightKeyPressed) {
			moveRight();
		}
		if (leftKeyPressed) {
			moveLeft();
		}

		checkingXPos();

		if (y > 865) {
			isAlive = false;
		}

	}

	private void checkingXPos() {
		if (x >= 463) {
			x = 460;
		} else if (x <= 0) {
			x = 0;
		}
	}

	void moveRight() {
		x += speed;
	}

	void moveLeft() {
		x -= speed;
	}

	public void draw(Graphics g) {
		g.drawImage(GamePanel.main2Img, x, y, width, height, null);
	}

}
