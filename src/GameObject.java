import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class GameObject {

	int x;
	int y;
	int width;
	int height;
	int lane;
	boolean isAlive = true;
	
	Rectangle collisionBox = new Rectangle();
	Rectangle collisionBox2 = new Rectangle();
	Rectangle collisionBox3 = new Rectangle();
	Rectangle collisionBox4 = new Rectangle();
	Rectangle collisionBox5 = new Rectangle();
	Rectangle collisionBox6 = new Rectangle();

	GameObject() {
		collisionBox.setBounds(x, y, width, height);
	}

	void update() {
		collisionBox.setBounds(x, y, width, height);
		collisionBox2.setBounds(x + 5, y, width - 10, height - 25);
		collisionBox3.setBounds(x + 25, y, width - 20, height - 10);
		collisionBox4.setBounds(x - 5, y, width - 25, height - 10);
		collisionBox5.setBounds(x, y + 30, width, height - 25);
		collisionBox6.setBounds(x, y + 20, width, height - 25);
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
	}
	
}
