package jgame;

public class Background {

	private int bgX, bgY, speedX;
	private float scrollRate = 3.25f;
	
	
	public int getBgX() {
		return bgX;
	}

	public void setBgX(int bgX) {
		this.bgX = bgX;
	}

	public int getBgY() {
		return bgY;
	}

	public void setBgY(int bgY) {
		this.bgY = bgY;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
	
	public int getScrolledX(){
		return (int)((float)bgX / scrollRate);
	}
	
	public int getScrolledY(){
		return (int)((float)bgY / scrollRate);
	}

	public Background(int x, int y) {
		super();
		this.bgX = x;
		this.bgY = y;
	}
	
	public void update(){
		bgX += speedX;
		
		if(bgX <= -2160){
			bgX += 4320;
		}
	}
	
	
	
}
