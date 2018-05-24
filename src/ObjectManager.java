
import java.applet.AudioClip;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JApplet;

public class ObjectManager {
	ArrayList<GameObject> objects;

	private int score = 0;

	long enemyTimer = 0;
	int enemySpawnTime = 700;

	static int[] lanes;

	public static void initLanes() {
		lanes = new int[] { 865, 865, 865, 865, 865, 865, 865, 865, 865, 865, 865, 865, 865, 865 };
	}

	public int getLanes(int i) {
		return lanes[i];
	}

	public void setLanes(int lane, int h) {
		lanes[lane] = h;
	}

	public ObjectManager() {
		initLanes();
		objects = new ArrayList<GameObject>();

	}

	public void addObject(GameObject o) {
		objects.add(o);
	}

	public void update() {
		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.update();
		}

		// purgeObjects();
	}

	public void draw(Graphics g) {

		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			o.draw(g);
		}
	}

	private void purgeObjects() {
		for (int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).isAlive) {
				objects.remove(i);
			}
		}
	}

	public void manageEnemies() {
		int enemyLane = new Random().nextInt(Stickman_Parkour.width) / 35;
		if (System.currentTimeMillis() - enemyTimer >= enemySpawnTime) {
			addObject(new Falling_Blocks(enemyLane * 35, 0, 35, 35, enemyLane));
			enemyTimer = System.currentTimeMillis();
		}

	}

	public void checkCollision() {
		System.out.println(enemySpawnTime);
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) {
				GameObject stickman = objects.get(i);
				GameObject fallingBlock = objects.get(j);

				if (stickman instanceof Stickman) {

					if (stickman.collisionBox.intersects(fallingBlock.collisionBox)) {
						if (((Falling_Blocks) fallingBlock).isRed) {

							stickman.y -= 6;

						} else {

						}
					}
					if (stickman instanceof Stickman) {

//						if (stickman.collisionBox.intersects(fallingBlock.collisionBox)) {
//							if (((Falling_Blocks) fallingBlock).isBlue) {
//
//								stickman.y -= 6;
//
//							} else {
//
//							}
//						}
						if (stickman instanceof Stickman) {

							if (stickman.collisionBox.intersects(fallingBlock.collisionBox)
									|| stickman.collisionBox2.intersects(fallingBlock.collisionBox)
									|| stickman.collisionBox3.intersects(fallingBlock.collisionBox)
									|| stickman.collisionBox4.intersects(fallingBlock.collisionBox)) {
								if (!(((Falling_Blocks) fallingBlock).isRed)
										&& !(((Falling_Blocks) fallingBlock).isBlue) && !(((Falling_Blocks) fallingBlock).isBlack)) {
									stickman.isAlive = false;

									// System.out.println("DEAD");
								}
							} else {

							}
						}
						if (stickman instanceof Stickman) {
//							if (stickman.y > 220) {
//								enemySpawnTime = 700;
//							}

						}
						if (stickman instanceof Stickman) {
							if (stickman.y <= 375) {
								enemySpawnTime = 225;
							} 
						}
						if ((stickman.collisionBox.intersects(fallingBlock.collisionBox))) {
							if (((Falling_Blocks) fallingBlock).isBlue) {
								stickman.y -= 6;
								((Stickman) stickman).speed += 0.3;

							} else {

							}

						}
						if ((stickman.collisionBox.intersects(fallingBlock.collisionBox))) {
							if (((Falling_Blocks) fallingBlock).isBlack) {
								enemySpawnTime -= 10;
								System.out.println("hi");
							}
						}
						if (stickman.collisionBox3.intersects(fallingBlock.collisionBox)) {
							if (((Falling_Blocks) fallingBlock).isRed == false && ((Falling_Blocks) fallingBlock).isBlue == false && ((Falling_Blocks) fallingBlock).isBlack == false) {
								stickman.x -= 6;

							} else {

							}

						}
						if (stickman.collisionBox4.intersects(fallingBlock.collisionBox)) {
							if (((Falling_Blocks) fallingBlock).isRed == false && ((Falling_Blocks) fallingBlock).isBlue == false && ((Falling_Blocks) fallingBlock).isBlack == false) {
								stickman.x += 6;
								// System.out.println("COLLISION");

							} else {

							}
						}
					}
				}
			}
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int s) {
		score = s;
	}

	public void reset() {
		objects.clear();
	}
}
