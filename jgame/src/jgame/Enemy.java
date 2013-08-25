package jgame;

import java.awt.Rectangle;

public abstract class Enemy {

	private int maxHealth, currentHealth, power, speedX, centerX, centerY;
	private Background bg = StartingClass.getBg1();
	private Rectangle r = new Rectangle();

	public Rectangle getR() {
		return r;
	}
	public void setR(Rectangle r) {
		this.r = r;
	}
	public abstract void attack();
	public abstract void die();

	public Background getBg() {
		return bg;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getPower() {
		return power;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setBg(Background bg) {
		this.bg = bg;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public void update(){
		centerX += speedX;
		speedX = bg.getSpeedX();
	}
	
	public abstract void hit();
	
	public abstract boolean isDead();
	
}
