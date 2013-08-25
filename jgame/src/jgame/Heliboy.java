package jgame;

import java.util.Random;

public class Heliboy extends Enemy {

	private static int PHASE = 0;
	private int phase = 0;
	private int baseY;
	
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
		// TODO Auto-generated method stub

	}

	@Override
	public void update() {
		super.update();
		this.setCenterY((int)(baseY + Math.cos((phase + StartingClass.getTick())) * 5 ));
	}

}
