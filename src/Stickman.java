import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Stickman extends GameObject {
	float speed;

	Stickman(int x, int y, int height, int width) {
		super();
		speed = 5;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}

	//int gravity = 0;
	//int jumpPower = 13;
	int yVelocity = 1;
	int ground = 840;
	boolean rightKey;
	boolean leftKey;
	boolean upkey;
	boolean jump;
	boolean onGround = true;
	//public int getGravity() {
		//return gravity;
	//}

//	public void setGravity(int gravity) {
//		this.gravity = gravity;
//	}

	public int getyVelocity() {
		return yVelocity;
	}

	public void setyVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}

	void update() {
		super.update();
		System.out.println (y + "THIS IS THE Y <--------------");
		if (x >= 225) {
			speed = speed;
		}
		if (rightKey) {
			System.out.println(speed);
			//System.out.println(speed);
			// x = x + speed;
			x += speed;
			//System.out.println(y);
			// System.out.println("Right key");
		}
		if (leftKey) {
			System.out.println(speed);
			// x = x - speed;
			x -= speed;
			//System.out.println("Left key");
		}

		
		if (x >= 463) {
			// x = 1000 - width;
			x = 460;

		} else if (x <= 0) {
			x = 0;
		
		}
		
		if(y > 865) {
			isAlive = false;
		}

	}



	public void draw(Graphics g) {
		 g.drawImage(GamePanel.main2Img, x, y, width, height , null);
//		g.setColor(Color.BLUE);
//		((Graphics2D) g).draw(collisionBox);
//		g.setColor(Color.red);
//		((Graphics2D) g).draw(collisionBox2);
//		g.setColor(Color.CYAN);
//		((Graphics2D) g).draw(collisionBox3);
//		g.setColor(Color.ORANGE);
//		((Graphics2D) g).draw(collisionBox4);
//		g.setColor(Color.MAGENTA);
//		((Graphics2D) g).draw(collisionBox5);
//		g.setColor(Color.PINK);
//		((Graphics2D) g).draw(collisionBox6);
		// g.setColor(Color.BLUE);
		// g.fillRect(x, y, width, height);
		// g.drawImage(, x, y, width, height, null);

	}

}
