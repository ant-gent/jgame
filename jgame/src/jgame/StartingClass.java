package jgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.*;

public class StartingClass extends Applet implements Runnable, KeyListener {

	private static Background bg1, bg2;
	
	private static double tick = 0;

	public static double getTick() {
		return tick;
	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	private Thread thread;
	private boolean stop = false;
	private Robot robot;
	private Image image;
	private Graphics second;
	private Image character, characterDown, characterJump, currentSprite,
			background, heliboy;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() {
		super.init();
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		frame.setResizable(false);
		addKeyListener(this);

		URL base = null;

		try {
			base = getDocumentBase();
			character = getImage(base, "../data/character.png");
			background = getImage(base, "../data/background.png");
			characterDown = getImage(base, "../data/down.png");
			characterJump = getImage(base, "../data/jumped.png");
			heliboy = getImage(base, "../data/heliboy.png");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		currentSprite = character;

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			break;
		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			break;
		case KeyEvent.VK_SPACE:
			robot.jump();
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		case KeyEvent.VK_CONTROL:
			if(!robot.isDucked() && !robot.isJumped()){
				robot.shoot();
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			robot.stop();
			break;
		case KeyEvent.VK_RIGHT:
			robot.stop();
			break;

		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {

		g.drawImage(background, bg1.getScrolledX(), bg1.getScrolledY(), this);
		g.drawImage(background, bg2.getScrolledX(), bg1.getScrolledY(), this);

		g.drawImage(currentSprite, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);
		
		for(Enemy e : enemies){
			if(e instanceof Heliboy){
				g.drawImage(heliboy, e.getCenterX(), e.getCenterY(), this);
			}
		}
		
		for(Projectile p : robot.getProjectiles()){
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		

	}

	@Override
	public void run() {

		while (!stop) {

			robot.update();

			if (robot.isJumped()) {
				currentSprite = characterJump;
			} else if (!robot.isJumped() && !robot.isDucked()) {
				currentSprite = character;
			}
			
			
			List<Projectile> toRemove = new ArrayList<Projectile>();
			
			for(Projectile p : robot.getProjectiles()){
				if(p.isVisible()){
					p.update();
				} else {
					toRemove.add(p);
				}
			}
			
			robot.getProjectiles().removeAll(toRemove);
			
			
			bg1.update();
			bg2.update();

			for(Enemy en : enemies){
				en.update();
			}
			
			repaint();

			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tick + 0.1 > Math.PI){
				tick = Math.PI * -1;
			} else {
				tick += 0.1; 
			}

		}

	}

	@Override
	public void start() {
		super.start();

		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);

		robot = new Robot();
		
		enemies.add(new Heliboy(340, 220));
		enemies.add(new Heliboy(700, 185));
		enemies.add(new Heliboy(850, 185));
		enemies.add(new Heliboy(980, 185));


		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		super.stop();
	}

	@Override
	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

}
