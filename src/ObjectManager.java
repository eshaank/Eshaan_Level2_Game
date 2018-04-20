
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {

	ArrayList<GameObject> gameObjects;

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
		gameObjects = new ArrayList<GameObject>();

	}

	public void addObject(GameObject o) {
		gameObjects.add(o);
	}

	public void update() {
		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject o = gameObjects.get(i);
			o.update();
		}
	}

	public void draw(Graphics g) {

		for (int i = 0; i < gameObjects.size(); i++) {
			GameObject o = gameObjects.get(i);
			o.draw(g);
		}
	}

	public void manageEnemies() {
		int enemyLane = new Random().nextInt(StickmanParkour.width) / 35;
		if (System.currentTimeMillis() - enemyTimer >= enemySpawnTime) {
			addObject(new FallingBlocks(enemyLane * 35, 0, 35, 35, enemyLane));
			enemyTimer = System.currentTimeMillis();
		}
	}

	public void checkCollision() {
		for (int i = 0; i < gameObjects.size(); i++) {
			for (int j = i + 1; j < gameObjects.size(); j++) {
				GameObject stickman = gameObjects.get(i);
				GameObject fallingBlock = gameObjects.get(j);

				if (stickman instanceof Stickman) {

					if (stickman.collisionBox.intersects(fallingBlock.collisionBox)) {
						if (((FallingBlocks) fallingBlock).isRed) {
							stickman.y -= 6;
						}
					}
					if (stickman instanceof Stickman) {
						if (stickman instanceof Stickman) {
							if (stickman.collisionBox.intersects(fallingBlock.collisionBox)
									|| stickman.collisionBox2.intersects(fallingBlock.collisionBox)
									|| stickman.collisionBox3.intersects(fallingBlock.collisionBox)
									|| stickman.collisionBox4.intersects(fallingBlock.collisionBox)) {
								if (!(((FallingBlocks) fallingBlock).isRed) && !(((FallingBlocks) fallingBlock).isBlue)
										&& !(((FallingBlocks) fallingBlock).isBlack)) {
									stickman.isAlive = false;

								}
							}
							if (stickman.y <= 375) {
								enemySpawnTime = 225;
							}
						}

						if ((stickman.collisionBox.intersects(fallingBlock.collisionBox))) {
							if (((FallingBlocks) fallingBlock).isBlue) {
								stickman.y -= 6;
								((Stickman) stickman).speed += 0.3;

							}

						}
						if ((stickman.collisionBox.intersects(fallingBlock.collisionBox))) {
							if (((FallingBlocks) fallingBlock).isBlack) {
								enemySpawnTime -= 10;
							}
						}
						if (stickman.collisionBox3.intersects(fallingBlock.collisionBox)) {
							if (((FallingBlocks) fallingBlock).isRed == false
									&& ((FallingBlocks) fallingBlock).isBlue == false
									&& ((FallingBlocks) fallingBlock).isBlack == false) {
								stickman.x -= 6;
							}
						}
						if (stickman.collisionBox4.intersects(fallingBlock.collisionBox)) {
							if (((FallingBlocks) fallingBlock).isRed == false
									&& ((FallingBlocks) fallingBlock).isBlue == false
									&& ((FallingBlocks) fallingBlock).isBlack == false) {
								stickman.x += 6;
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
		gameObjects.clear();
	}
}
