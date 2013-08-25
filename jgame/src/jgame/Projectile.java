package jgame;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Projectile {

	private int x, y, speedX;
	private boolean visible;
	private Rectangle r;

	public Projectile(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 10;
		visible = true;
		r = new Rectangle(x, y, 10, 5);
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update() {
		x += speedX;

		if (x > 800) {
			visible = false;
		} else if (visible) {
			checkCollision(StartingClass.enemies);
		}
		
		
		r = new Rectangle(x, y, 10, 5);

	}

	private void checkCollision(List<Enemy> enemies) {

		List<Enemy> deadEnemies = new ArrayList<Enemy>();
		
		for(Enemy e : enemies){
			if(r.intersects(e.getR())){
				e.hit();
				visible = false;
				if(e.isDead()){
					deadEnemies.add(e);
				}
			}
		}
		
		for(Enemy e : deadEnemies){
			enemies.remove(e);
		}
		
		
	}

}
