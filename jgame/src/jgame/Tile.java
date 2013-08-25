package jgame;

import java.awt.Image;

public class Tile {

	private int tileX, tileY, speedX, type;
	private Image tileImage;

	private Background bg = StartingClass.getBg1();

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
			tileImage = StartingClass.tileDirt;
			break;
		}
		
	}
	
	public void update(){
		switch(type){
		case 1:
			if(bg.getSpeedX() == 0){
				speedX = -1;
			} else{
				speedX = -2;
			}
			break;
		case 2:
			speedX = bg.getSpeedX();
			break;
		}
		
		tileX += speedX;
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
	
	

}
