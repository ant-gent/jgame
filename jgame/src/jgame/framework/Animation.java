package jgame.framework;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.spi.SyncResolver;

public class Animation {

	private class AnimFrame {
		Image image;
		long endTime;

		public AnimFrame(Image image, long endtime) {
			super();
			this.image = image;
			this.endTime = endtime;
		}

	}

	private List<AnimFrame> frames = new ArrayList<AnimFrame>();
	private int currentFrame;
	private long animTime;
	private long totalDuration = 0;

	public Animation() {
		synchronized (this) {
			animTime = 0;
			currentFrame = 0;
		}
	}

	public synchronized void addFrame(Image image, long duration) {
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	public synchronized void update(long elaspedTime) {
		if (frames.size() > 1) {
			animTime += elaspedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

			while (animTime > getFrame(currentFrame).endTime) {
				currentFrame++;
			}

		}
	}

	private AnimFrame getFrame(int i) {
		return frames.get(i);
	}
	
	public Image getImage(){
		return getFrame(currentFrame).image;
	}

}
