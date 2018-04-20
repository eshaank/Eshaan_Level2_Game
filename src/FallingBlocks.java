import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class FallingBlocks extends GameObject {

	boolean left = true;
	boolean isPlaced = false;
	boolean isRed;
	boolean isBlue;
	boolean isBlack;

	FallingBlocks(int x, int y, int width, int height, int lane) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.lane = lane;
		coloringBlocks();
	}

	private void coloringBlocks() {
		if (Math.random() > 0.96) {
			isRed = false;
			isBlue = false;
			isBlack = true;
		} 
		if (Math.random() > 0.96) {
			isRed = false;
			isBlue = true;
			isBlack = false;
		}
		if (Math.random() > 0.7) {
			isBlack = false;
			isBlue = false;
			isRed = true;
		}
	}

	void update() {
		super.update();
		if (!isPlaced) {
			y++;

			if (checkingLeft()) {
				y = y + new Random().nextInt(10);
			}

			if (y > ObjectManager.lanes[lane]) {
				y = ObjectManager.lanes[lane];
				isPlaced = true;
				ObjectManager.lanes[lane] -= height;
			}
		}
	}

	private boolean checkingLeft() {
		if (x <= 0) {
			left = false;
		}
		if (x >= 0) {
			left = true;
		}
		return left;
	}

	public void draw(Graphics g) {
		if (isBlue) {
			g.setColor(Color.BLUE);
		} else if (isRed) {
			g.setColor(Color.RED);
		} else if (isBlack) {
			g.setColor(Color.white);
		} else {
			g.setColor(Color.GRAY);
		}
		g.fillRect(x, y, width, height);
	}
}
