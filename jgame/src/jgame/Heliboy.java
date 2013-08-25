package jgame;

import java.util.Random;

public class Heliboy extends Enemy {

	private static int PHASE = 0;
	private int phase = 0;
	private int baseY;
	private boolean dead = false;
	private int hp = 2;

	public Heliboy(int x, int y) {
		super();
		setCenterX(x);
		setCenterY(y);
		baseY = y;
		phase = PHASE++;
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void die() {
		dead = true;
		StartingClass.collisions.remove(getR());
	}

	@Override
	public void update() {
		super.update();
		this.setCenterY((int) (baseY + Math.cos((phase + StartingClass
				.getTick())) * 10));
		this.getR().setBounds(this.getCenterX() + 25, this.getCenterY() + 25,
				50, 60);
	}

	@Override
	public void hit() {
		hp--;
		if (hp == 0)
			die();
	}

	@Override
	public boolean isDead() {
		return dead;
	}

}
