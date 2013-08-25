package jgame;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.Collections;

public class Tile {

	private int tileX, tileY, speedX, type;
	private Image tileImage;

	private Background bg = StartingClass.getBg1();
	private Robot robot = StartingClass.getRobot();

	private Rectangle r = new Rectangle();

	public Tile(int x, int y, int type) {
		super();
		this.tileX = x * 40;
		this.tileY = y * 40;
		this.type = type;

		switch (type) {
		case 1:
			tileImage = StartingClass.tileOcean;
			break;
		case 2:
			tileImage = StartingClass.tilegrassBot;
			break;
		case 5:
			tileImage = StartingClass.tileDirt;
			break;
		case 6:
			tileImage = StartingClass.tilegrassRight;
			break;
		case 8:
			tileImage = StartingClass.tilegrassTop;
			break;
		case 4:
			tileImage = StartingClass.tilegrassLeft;
			break;
		default:
			type = 0;
			break;
		}
		
		if(type < 0){
			type = 0;
		}

	}

	public void update() {
		switch (type) {
		case 1:
			if (bg.getSpeedX() == 0) {
				speedX = -1;
			} else {
				speedX = -2;
			}
			break;
		default:
			speedX = bg.getSpeedX();
			break;
		}

		tileX += speedX;

		r.setBounds(tileX, tileY, 40, 40);

		if (r.intersects(Robot.yellowRed) && type != 0) {
			checkVerticalCollision(Robot.rect, Robot.rect2);
			checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft,
					Robot.footright);
		}

	}

	public int getTileX() {
		return tileX;
	}

	public void setTileX(int tileX) {
		this.tileX = tileX;
	}

	public int getTileY() {
		return tileY;
	}

	public void setTileY(int tileY) {
		this.tileY = tileY;
	}

	public Image getTileImage() {
		return tileImage;
	}

	public void setTileImage(Image tileImage) {
		this.tileImage = tileImage;
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {
		if (rtop.intersects(r) && type == 2) {
			StartingClass.collisions.add(rtop);
		}

		if (rbot.intersects(r) && type == 8) {
			StartingClass.collisions.add(rbot);
			robot.touchdown();
			robot.setCenterY(tileY - 63);
		}

	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright,
			Rectangle leftfoot, Rectangle rightfoot) {
		if (type != 5 && type != 2 && type != 0 && type != -1) {
			if (rleft.intersects(r)) {
				robot.setCenterX(tileX + 102);
				robot.setSpeedX(0);
				StartingClass.collisions.add(rleft);

			} else if (leftfoot.intersects(r)) {
				robot.setCenterX(tileX + 85);
				robot.setSpeedX(0);
				StartingClass.collisions.add(leftfoot);
			}

			if (rright.intersects(r)) {
				robot.setCenterX(tileX - 62);
				robot.setSpeedX(0);
				StartingClass.collisions.add(rright);
			}

			else if (rightfoot.intersects(r)) {
				robot.setCenterX(tileX - 45);
				robot.setSpeedX(0);
				StartingClass.collisions.add(rightfoot);
			}
		}
	}

}
