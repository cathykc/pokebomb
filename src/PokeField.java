import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PokeField extends JPanel {

	// state of game and class variables
	private Player mainPlayer; // the main player
	private Player secondPlayer;

	public boolean playing = false; // whether the game is running
	private JLabel status; // current status text
	private Trees[][] trees; // double array of trees
	private ArrayList<Voltorb> voltorbs; // array list of exploding voltorbs
	private ArrayList<ExplosionCenter> explosionCenters;
	private ArrayList<ExplosionHorizontal> explosionHorizontal;
	private ArrayList<ExplosionVertical> explosionVertical;
	private ArrayList<Integer> keysPressed;


	// constants
	public static final int FIELD_WIDTH = 520;
	public static final int FIELD_HEIGHT = 440;

	// grass background
	public static final String background_file = "background.png";
	private static BufferedImage background;

	// update interval for timer - milliseconds
	public static final int INTERVAL = 10;

	// constructor for playing field
	public PokeField(JLabel status) {

		// initialize explosions
		explosionCenters = new ArrayList<ExplosionCenter>();
		explosionHorizontal = new ArrayList<ExplosionHorizontal>();
		explosionVertical = new ArrayList<ExplosionVertical>();
		keysPressed = new ArrayList<Integer>();


		voltorbs = new ArrayList<Voltorb>();

		// timer - every time interval, advance step in game
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});

		// start timer
		timer.start();

		// set focus on window
		setFocusable(true);

		// add key listener to move main player and drop voltorbs
		addKeyListener(new KeyAdapter() {

			// if key pressed update velocity
			public void keyPressed(KeyEvent e) {
				if (!keysPressed.contains((Integer) e.getKeyCode())) {
					keysPressed.add((Integer) e.getKeyCode());
				}

			}

			// stop moving if key released
			public void keyReleased(KeyEvent e) {
				System.out.println(keysPressed.remove((Integer) e.getKeyCode()));

			}
		});



		// set status
		this.status = status;


		// read in background image
		try {
			if (background == null) {
				background = ImageIO.read(new File(background_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	// reset game to initial conditions
	public void reset() {

		// create main player
		mainPlayer = new Player(0,0,FIELD_WIDTH, FIELD_HEIGHT, 1);
		secondPlayer = new Player(481,401,FIELD_WIDTH, FIELD_HEIGHT, 2);

		// create array list of voltorbs
		voltorbs = new ArrayList<Voltorb>();

		explosionCenters = new ArrayList<ExplosionCenter>();	
		explosionHorizontal = new ArrayList<ExplosionHorizontal>();
		explosionVertical = new ArrayList<ExplosionVertical>();
		keysPressed = new ArrayList<Integer>();

		// initial settings for game state
		playing = true;
		status.setText("Running...");

		// create trees
		trees = new Trees[6][5];
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				trees[i][j] = new Trees ((i*2 + 1)*40, ((j*2)+1)*40);
			}
		}

		// ensure focus on window
		requestFocusInWindow();
	}

	// time step in game
	void tick() {

		// if game is playing
		if (playing) {

			if (!keysPressed.isEmpty()) {
				
				boolean mainBeenPressed = false;
				boolean secondBeenPressed = false;
				for (int key : keysPressed) {
					if (key == KeyEvent.VK_LEFT) {
						mainPlayer.v_x = -3;
						mainPlayer.setState("left");
						mainPlayer.keyPressed = true;
						mainBeenPressed = true;
					}
					else if (key == KeyEvent.VK_RIGHT) {
						mainPlayer.v_x = 3;
						mainPlayer.setState("right");
						mainPlayer.keyPressed = true;
						mainBeenPressed = true;
					}
					else if (key == KeyEvent.VK_DOWN) {
						mainPlayer.v_y = 3;
						mainPlayer.setState("front");
						mainPlayer.keyPressed = true;
						mainBeenPressed = true;
					}
					else if (key == KeyEvent.VK_UP) {
						mainPlayer.v_y = -3;
						mainPlayer.setState("back");
						mainPlayer.keyPressed = true;
						mainBeenPressed = true;
					}
					if (!mainBeenPressed) {
						mainPlayer.v_x = 0;
						mainPlayer.v_y = 0;
						mainPlayer.keyPressed = false;
					}

					if (key == KeyEvent.VK_SPACE) {
						Voltorb new_voltorb = new Voltorb (mainPlayer.p_x, mainPlayer.p_y, mainPlayer.getStrength(), 1);	
						boolean emptySpace = true;
						for (Voltorb v: voltorbs) {
							if (new_voltorb.p_x == v.p_x && new_voltorb.p_y == v.p_y) emptySpace = false;
						}
						if (emptySpace) {
							voltorbs.add(new_voltorb);
						}
					}


					if (key == KeyEvent.VK_A) {
						secondPlayer.v_x = -3;
						secondPlayer.setState("left");
						secondPlayer.keyPressed = true;
						secondBeenPressed = true;
					}
					else if (key == KeyEvent.VK_D) {
						secondPlayer.v_x = 3;
						secondPlayer.setState("right");
						secondPlayer.keyPressed = true;
						secondBeenPressed = true;
					}
					else if (key == KeyEvent.VK_S) {
						secondPlayer.v_y = 3;
						secondPlayer.setState("front");
						secondPlayer.keyPressed = true;
						secondBeenPressed = true;
					}
					else if (key == KeyEvent.VK_W) {
						secondPlayer.v_y = -3;
						secondPlayer.setState("back");
						secondPlayer.keyPressed = true;
						secondBeenPressed = true;
					}
					if (!secondBeenPressed) {
						secondPlayer.v_x = 0;
						secondPlayer.v_y = 0;
						secondPlayer.keyPressed = false;
					}

					if (key == KeyEvent.VK_SHIFT) {
						Voltorb new_voltorb = new Voltorb (secondPlayer.p_x, secondPlayer.p_y, secondPlayer.getStrength(), 2);	
						boolean emptySpace = true;
						for (Voltorb v: voltorbs) {
							if (new_voltorb.p_x == v.p_x && new_voltorb.p_y == v.p_y) emptySpace = false;
						}
						if (emptySpace) {
							voltorbs.add(new_voltorb);
						}
					}

				}
			}
			else {
				mainPlayer.v_x = 0;
				mainPlayer.v_y = 0;
				mainPlayer.keyPressed = false;
				secondPlayer.v_x = 0;
				secondPlayer.v_y = 0;
				secondPlayer.keyPressed = false;
			}

			// if not intersecting, move else check shift
			if (mainPlayer.willIntersect(trees)) {
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 5; j++) {
						Direction d = mainPlayer.hitObj(trees[i][j]);
						if (d != null) {
							if (d == Direction.LEFT || d == Direction.RIGHT) {
								if ((mainPlayer.p_y%40 > 1) && (mainPlayer.p_y%40 < 30) && (mainPlayer.p_y - mainPlayer.p_y%40 != trees[i][j].p_y)) {
									mainPlayer.p_y = mainPlayer.p_y - mainPlayer.p_y % 40 + 1;
								}
								if ((mainPlayer.p_y%40 > 30) && (mainPlayer.p_y - mainPlayer.p_y%40 == trees[i][j].p_y)) {
									mainPlayer.p_y = mainPlayer.p_y - mainPlayer.p_y % 40 + 41;
								}

							}
							else if (d == Direction.DOWN || d == Direction.UP) {
								if ((mainPlayer.p_x%40 > 1) && (mainPlayer.p_x%40 < 30) && (mainPlayer.p_x - mainPlayer.p_x%40 != trees[i][j].p_x)) {
									mainPlayer.p_x = mainPlayer.p_x - mainPlayer.p_x % 40 + 1;
								}
								if ((mainPlayer.p_x%40 > 30) && (mainPlayer.p_x - mainPlayer.p_x%40 == trees[i][j].p_x)) {
									mainPlayer.p_x = mainPlayer.p_x - mainPlayer.p_x % 40 + 41;
								}
							}

							// save time
							break;
						}
					}
				}
			}
			else {
				mainPlayer.move();
			}

			// NEED TO FIX
			if (secondPlayer.willIntersect(trees)) {
				for (int i = 0; i < 6; i++) {
					for (int j = 0; j < 5; j++) {
						Direction d = secondPlayer.hitObj(trees[i][j]);
						if (d != null) {
							if (d == Direction.LEFT || d == Direction.RIGHT) {
								if ((secondPlayer.p_y%40 > 1) && (secondPlayer.p_y%40 < 30) && (secondPlayer.p_y - secondPlayer.p_y%40 != trees[i][j].p_y)) {
									secondPlayer.p_y = secondPlayer.p_y - secondPlayer.p_y % 40 + 1;
								}
								if ((secondPlayer.p_y%40 > 30) && (secondPlayer.p_y - secondPlayer.p_y%40 == trees[i][j].p_y)) {
									secondPlayer.p_y = secondPlayer.p_y - secondPlayer.p_y % 40 + 41;
								}

							}
							else if (d == Direction.DOWN || d == Direction.UP) {
								if ((secondPlayer.p_x%40 > 1) && (secondPlayer.p_x%40 < 30) && (secondPlayer.p_x - secondPlayer.p_x%40 != trees[i][j].p_x)) {
									secondPlayer.p_x = secondPlayer.p_x - secondPlayer.p_x % 40 + 1;
								}
								if ((secondPlayer.p_x%40 > 30) && (secondPlayer.p_x - secondPlayer.p_x%40 == trees[i][j].p_x)) {
									secondPlayer.p_x = secondPlayer.p_x - secondPlayer.p_x % 40 + 41;
								}
							}

							// save time
							break;
						}
					}
				}
			}
			else {
				secondPlayer.move();
			}


			boolean IntersectExplosion1 = false;
			boolean IntersectExplosion2 = false;
			for (ExplosionCenter ec: explosionCenters) {
				if (mainPlayer.intersects(ec)) IntersectExplosion1 = true;
				if (secondPlayer.intersects(ec)) IntersectExplosion2 = true;
				ec.countingDuration();
				if (ec.getDuration() == 0) {
					explosionCenters.remove(ec);
					break;
				}
			}
			for (ExplosionHorizontal eh: explosionHorizontal) {
				if (mainPlayer.intersects(eh)) IntersectExplosion1 = true;
				if (secondPlayer.intersects(eh)) IntersectExplosion2 = true;
				eh.countingDuration();
				if (eh.getDuration() == 0) {
					explosionHorizontal.remove(eh);
					break;
				}
			}
			for (ExplosionVertical ev: explosionVertical) {
				if (mainPlayer.intersects(ev)) IntersectExplosion1 = true;
				if (secondPlayer.intersects(ev)) IntersectExplosion2 = true;
				ev.countingDuration();
				if (ev.getDuration() == 0) {
					explosionVertical.remove(ev);
					break;
				}
			}


			if (IntersectExplosion1) {
				mainPlayer.died();
				if (mainPlayer.getLives() == 0) {
					playing = false;
					status.setText("Player 2 wins!");
				}
			}

			if (IntersectExplosion2) {
				secondPlayer.died();
				if (secondPlayer.getLives() == 0) {
					playing = false;
					status.setText("Player 1 wins!");
				}
			}

			mainPlayer.hit_time_decrement();
			secondPlayer.hit_time_decrement();

			// checking if voltorbs are ready to explode
			for (Voltorb v: voltorbs) {
				v.countingDown();
				if (v.getCount()==0) {
					ExplosionCenter new_ec = new ExplosionCenter(v.p_x, v.p_y);
					explosionCenters.add(new_ec);
					for (int i = 1; i <= v.getStrength(); i++) {
						ExplosionHorizontal eh_left = new ExplosionHorizontal(new_ec.p_x - 40*i, new_ec.p_y);
						ExplosionHorizontal eh_right = new ExplosionHorizontal(new_ec.p_x + 40*i, new_ec.p_y);
						if (eh_left.intersects(trees)) {
							new_ec.leftBlocked = true;
						}
						if (!new_ec.leftBlocked) {
							explosionHorizontal.add(eh_left);
						}
						if (eh_right.intersects(trees)) {
							new_ec.rightBlocked = true;
						}
						if (!new_ec.rightBlocked) {
							explosionHorizontal.add(eh_right);
						}

						ExplosionVertical ev_top = new ExplosionVertical(new_ec.p_x, new_ec.p_y + 40*i);
						ExplosionVertical ev_bottom = new ExplosionVertical(new_ec.p_x, new_ec.p_y - 40*i);
						if (ev_top.intersects(trees)) {
							new_ec.topBlocked = true;
						}
						if (!new_ec.topBlocked) {
							explosionVertical.add(ev_top);
						}
						if (ev_bottom.intersects(trees)) {
							new_ec.bottomBlocked = true;
						}
						if (!new_ec.bottomBlocked) {
							explosionVertical.add(ev_bottom);
						}

					}
					voltorbs.remove(v);
					break;
				}
			}



			// repaint objects
			repaint();
		}
	}


	// paint function for components
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, 520, 440, null);

		for (Voltorb v: voltorbs) {
			v.draw(g);
		}

		for (ExplosionCenter ec: explosionCenters) {
			ec.draw(g);
		}

		for (ExplosionHorizontal eh: explosionHorizontal) {
			eh.draw(g);
		}

		for (ExplosionVertical ev: explosionVertical) {
			ev.draw(g);
		}


		mainPlayer.draw(g);
		secondPlayer.draw(g);

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				trees[i][j].draw(g);
			}
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(FIELD_WIDTH, FIELD_HEIGHT);
	}
}
