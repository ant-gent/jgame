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
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Key;
import net.java.games.input.Controller.Type;
import jgame.framework.Animation;

public class StartingClass extends Applet implements Runnable {

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
	private Image currentSprite, character, character2, character3,
			characterDown, characterJump, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private Animation anim, hanim;

	private Controller kb;

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
		URL base = null;

		try {
			base = getDocumentBase();
			character = getImage(base, "../data/character.png");
			character2 = getImage(base, "../data/character2.png");
			character3 = getImage(base, "../data/character3.png");
			background = getImage(base, "../data/background.png");
			characterDown = getImage(base, "../data/down.png");
			characterJump = getImage(base, "../data/jumped.png");
			heliboy = getImage(base, "../data/heliboy.png");
			heliboy2 = getImage(base, "../data/heliboy2.png");
			heliboy3 = getImage(base, "../data/heliboy3.png");
			heliboy4 = getImage(base, "../data/heliboy4.png");
			heliboy5 = getImage(base, "../data/heliboy5.png");

		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);
		
		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);
		
		currentSprite = anim.getImage();

		// Controller set-up
		Controller[] ca = ControllerEnvironment.getDefaultEnvironment()
				.getControllers();
		System.out.println("Found " + ca.length + " controller(s).");

		kb = null;

		for (Controller ctrler : ca) {
			if (ctrler.getType() == Type.KEYBOARD) {
				kb = ctrler;
				break;
			}
		}

		if (kb == null) {
			System.err.println("Can't find suitable controller.");
			System.exit(1);
		} else {
			System.out.println("Controller : " + kb.getName());
		}

	}

	/*
	 * @Override public void keyPressed(KeyEvent e) {
	 * 
	 * switch (e.getKeyCode()) { case KeyEvent.VK_UP: break; case
	 * KeyEvent.VK_DOWN: currentSprite = characterDown; break; case
	 * KeyEvent.VK_LEFT: // robot.moveLeft(); break; case KeyEvent.VK_RIGHT: //
	 * robot.moveRight(); break; case KeyEvent.VK_SPACE: robot.jump(); break;
	 * case KeyEvent.VK_ESCAPE: System.exit(0); break; case KeyEvent.VK_CONTROL:
	 * if (!robot.isDucked() && !robot.isJumped()) { robot.shoot(); } break;
	 * default: break; }
	 * 
	 * }
	 */

	/*
	 * @Override public void keyReleased(KeyEvent e) {
	 * 
	 * switch (e.getKeyCode()) {
	 * 
	 * case KeyEvent.VK_LEFT: robot.stop(); break; case KeyEvent.VK_RIGHT:
	 * robot.stop(); break;
	 * 
	 * default: break; }
	 * 
	 * }
	 */

	/*
	 * @Override public void keyTyped(KeyEvent arg0) { // TODO Auto-generated
	 * method stub
	 * 
	 * }
	 */

	@Override
	public void paint(Graphics g) {

		g.drawImage(background, bg1.getScrolledX(), bg1.getScrolledY(), this);
		g.drawImage(background, bg2.getScrolledX(), bg1.getScrolledY(), this);

		g.drawImage(currentSprite, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);

		for (Enemy e : enemies) {
			if (e instanceof Heliboy) {
				g.drawImage(hanim.getImage(), e.getCenterX(), e.getCenterY(), this);
			}
		}

		for (Projectile p : robot.getProjectiles()) {
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}

	}

	@Override
	public void run() {

		while (!stop) {

			pollInput();

			robot.update();

			if (robot.isJumped()) {
				currentSprite = characterJump;
			} else if (!robot.isJumped() && !robot.isDucked()) {
				currentSprite = anim.getImage();
			}

			List<Projectile> toRemove = new ArrayList<Projectile>();

			for (Projectile p : robot.getProjectiles()) {
				if (p.isVisible()) {
					p.update();
				} else {
					toRemove.add(p);
				}
			}

			robot.getProjectiles().removeAll(toRemove);

			bg1.update();
			bg2.update();
			
			animate();

			for (Enemy en : enemies) {
				en.update();
			}

			repaint();

			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (tick + 0.1 > Math.PI) {
				tick = Math.PI * -1;
			} else {
				tick += 0.1;
			}

		}

	}

	private void animate() {
		anim.update(10);
		hanim.update(50);
	}

	private void pollInput() {

		kb.poll();
		EventQueue queue = kb.getEventQueue();
		Event event = new Event();

		while (queue.getNextEvent(event)) {
			System.out.println(event.getComponent().getName() + " = "
					+ event.getValue());

			Key key = (Key) event.getComponent().getIdentifier();

			if (key.equals(Key.UP)) {

			} else if (key.equals(Key.DOWN)) {

				if(event.getValue() == 1.0f){
					
				} else if (event.getValue() == 0.0f){
					currentSprite = anim.getImage();
				}
				
			} else if (key.equals(Key.LEFT)) {
				if (event.getValue() == 1.0f) {
					robot.moveLeft();
				} else {
					if (robot.isMovingLeft()) {
						robot.stop();
					}
				}
			} else if (key.equals(Key.RIGHT)) {
				if (event.getValue() == 1.0f) {
					robot.moveRight();
				} else {
					if (robot.isMovingRight()) {
						robot.stop();
					}
				}
			} else if (key.equals(Key.SPACE)) {
				if (event.getValue() == 1.0f) {
					robot.jump();
				}
			} else if (key.equals(Key.LCONTROL)) {
				if (event.getValue() == 1.0f) {
					robot.shoot();
				}
			} else if (key.equals(Key.ESCAPE)) {
				if (event.getValue() == 1.0f) {
					System.exit(0);
				}
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
