package jgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private static Robot robot;
	private Image image;
	private Graphics second;
	private Image currentSprite, character, character2, character3,
			characterDown, characterJump, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	private Animation anim, hanim;

	private Controller kb;

	private List<Tile> tiles;

	public static Set<Rectangle> collisions = new HashSet<Rectangle>();

	public static Image tileOcean, tilegrassTop, tilegrassBot, tilegrassLeft,
			tilegrassRight, tileDirt;

	private List<Rectangle> colRects = new ArrayList<Rectangle>();
	private boolean displayColRects = false;

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
		tiles = new ArrayList<Tile>();

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

			tileDirt = getImage(base, "../data/tiledirt.png");
			tileOcean = getImage(base, "../data/tileocean.png");
			tilegrassTop = getImage(base, "../data/tilegrasstop.png");
			tilegrassBot = getImage(base, "../data/tilegrassbot.png");
			tilegrassLeft = getImage(base, "../data/tilegrassleft.png");
			tilegrassRight = getImage(base, "../data/tilegrassright.png");

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

		// Background
		g.drawImage(background, bg1.getScrolledX(), bg1.getScrolledY(), this);
		g.drawImage(background, bg2.getScrolledX(), bg1.getScrolledY(), this);

		// Tiles
		paintTiles(g);

		// Robot
		g.drawImage(currentSprite, robot.getCenterX() - 61,
				robot.getCenterY() - 63, this);

		// Enemies
		for (Enemy e : enemies) {
			if (e instanceof Heliboy) {
				g.drawImage(hanim.getImage(), e.getCenterX(), e.getCenterY(),
						this);
			}
		}

		// Projectiles
		for (Projectile p : robot.getProjectiles()) {
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}

		// Debug info : colision rects
		if (displayColRects) {
			drawColRects(g);
		}

	}

	private void drawColRects(Graphics g) {
		for (Rectangle r : colRects) {

			if (r == null) {
				continue;
			}

			if (collisions.contains(r)) {
				g.setColor(Color.RED);
			} else {
				g.setColor(Color.BLACK);
			}
			g.drawRect((int) r.getX(), (int) r.getY(), (int) r.getWidth(),
					(int) r.getHeight());

		}
	}

	@Override
	public void run() {

		while (!stop) {

			pollInput();

			updateTiles();

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
			Key key = (Key) event.getComponent().getIdentifier();

			if (key.equals(Key.UP)) {

			} else if (key.equals(Key.DOWN)) {

				if (event.getValue() == 1.0f) {

				} else if (event.getValue() == 0.0f) {
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
			} else if (key.equals(Key.C)) {
				if (event.getValue() == 1.0f) {
					displayColRects = !displayColRects;
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

		colRects.add(robot.footleft);
		colRects.add(robot.footright);
		colRects.add(robot.rect);
		colRects.add(robot.rect2);
		colRects.add(robot.rect3);
		colRects.add(robot.rect4);

		try {
			loadMap("../data/map1.txt");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		enemies.add(new Heliboy(340, 220));
		enemies.add(new Heliboy(700, 185));
		enemies.add(new Heliboy(850, 185));
		enemies.add(new Heliboy(980, 185));

		for (Enemy e : enemies) {
			colRects.add(e.getR());
		}

		thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String filename) throws IOException {

		List<String> lines = new ArrayList<String>();

		int width = 0;
		int height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));

		String line;

		while ((line = reader.readLine()) != null) {

			if (!line.startsWith("!")) {
				lines.add(line);
				width = Math.max(width, line.length());
			}

		}
		reader.close();

		height = lines.size();

		for (int j = 0; j < height; j++) {
			String currentLine = lines.get(j);

			for (int i = 0; i < width; i++) {

				if (i < currentLine.length()) {
					int type = Character.getNumericValue(currentLine.charAt(i));
					Tile t = new Tile(i, j, type);
					tiles.add(t);
				}

			}

		}

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

	private void updateTiles() {
		collisions.clear();

		for (Tile t : tiles)
			t.update();
	}

	private void paintTiles(Graphics g) {
		for (Tile t : tiles) {
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	public static Robot getRobot() {
		return robot;
	}

}
