package jgame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Robot {

	private enum State {
		MOVINGLEFT, MOVINGRIGHT, JUMPING, STOPPED, FALLING
	}

	private Background bg1 = StartingClass.getBg1();
	private Background bg2 = StartingClass.getBg2();

	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private State state = State.STOPPED;
	private int centerX = 100;

	private int centerY = 377;

	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;

	private List<Projectile> projectiles = new ArrayList<Projectile>();

	private int speedX = 0;

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	private int speedY = 0;

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public static Rectangle rect = new Rectangle();
	public static Rectangle rect2 = new Rectangle();
	public static Rectangle rect3 = new Rectangle(0, 0, 0, 0);
	public static Rectangle rect4 = new Rectangle(0, 0, 0, 0);
	public static Rectangle yellowRed = new Rectangle(0, 0, 0, 0);
	public static Rectangle footleft = new Rectangle(0, 0, 0, 0);
	public static Rectangle footright = new Rectangle(0, 0, 0, 0);

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public boolean isDucked() {
		return ducked;
	}

	public boolean isJumped() {
		return jumped;
	}

	public void jump() {
		if (jumped == false) {
			speedY = -15;
			jumped = true;
		}
	}

	public void moveLeft() {
		state = State.MOVINGLEFT;
	}

	public void moveRight() {
		state = State.MOVINGRIGHT;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void shoot() {
		projectiles.add(new Projectile(centerX + 50, centerY - 25));
	}

	public void stop() {
		state = State.STOPPED;
	}

	public void stopLeft() {
		movingLeft = false;
	}

	public void stopRight() {
		movingRight = false;
	}

	public void update() {

		if (state == State.MOVINGLEFT) {

			if (speedX > 0) {
				speedX = 0;
			}

			if (speedX - 1 < -MOVESPEED) {
				speedX = -MOVESPEED;
			} else {
				speedX -= 1;
			}
		} else if (state == State.MOVINGRIGHT) {

			if (speedX < 0) {
				speedX = 0;
			}

			if (speedX + 1 > MOVESPEED) {
				speedX = MOVESPEED;
			} else {
				speedX += 1;
			}
		} else if (state == State.STOPPED) {
			speedX = 0;
		}

		if (speedX < 0) {
			centerX += speedX;
		}

		if (speedX == 0 || speedX < 0) {
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);

		}
		if (centerX <= 200 && speedX > 0) {
			centerX += speedX;
		}
		if (speedX > 0 && centerX > 200) {
			bg1.setSpeedX(-MOVESPEED);
			bg2.setSpeedX(-MOVESPEED);
		}

		// Updates Y Position
		centerY += speedY;

		speedY += 1;

		if (speedY > 3) {
			jumped = true;
		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60) {
			centerX = 61;
		}

		rect.setRect(centerX - 34, centerY - 63, 68, 63);
		rect2.setRect(rect.getX(), rect.getY() + 63, 68, 64);
		rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
		rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
		yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);
		footleft.setRect(centerX - 50, centerY + 20, 50, 15);
		footright.setRect(centerX, centerY + 20, 50, 15);

	}

	public boolean isMovingRight() {
		return state == State.MOVINGRIGHT;
	}

	public boolean isMovingLeft() {
		return state == State.MOVINGLEFT;
	}

	public void fall() {
		state = State.FALLING;
	}

	public void touchdown() {
		if (speedY > 0) {
			speedY = 0;
			jumped = false;
		}
	}

}
