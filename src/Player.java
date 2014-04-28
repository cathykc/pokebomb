import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// the main player! controlled by arrow keys
public class Player extends PokeThings {

	// constants
	public static final int SIZE = 38;
	public static final int INIT_V_X = 0;
	public static final int INIT_V_Y = 0;


	private String state;
	// image files
	private BufferedImage front1;
	private BufferedImage front2;
	private BufferedImage back1;
	private BufferedImage back2;
	private BufferedImage left1;
	private BufferedImage left2;
	private BufferedImage right1;
	private BufferedImage right2;

	public boolean keyPressed;


	// class variables for player
	private LifeCounter lifeCounter;
	private int strength;
	private boolean hit;
	private int hit_time;
	private int playTime;
	private int playStep;

	// player constructor
	public Player(int init_x, int init_y, int fieldWidth, int fieldHeight, int playerNumber) {
		super(INIT_V_X, INIT_V_Y, init_x, init_y, SIZE, SIZE, fieldWidth,
				fieldHeight);
		playTime = 0;
		lifeCounter = new LifeCounter(playerNumber);
		strength = 2;
		hit = false;
		hit_time = 0;
		state = "front";
		keyPressed = false;
		if (playerNumber == 1) {
			try {
				front1 = ImageIO.read(new File("red_front1.png"));
				front2 = ImageIO.read(new File("red_front2.png"));
				back1 = ImageIO.read(new File("red_back1.png"));
				back2 = ImageIO.read(new File("red_back2.png"));
				left1 = ImageIO.read(new File("red_left1.png"));
				left2 = ImageIO.read(new File("red_left2.png"));
				right1 = ImageIO.read(new File("red_right1.png"));
				right2 = ImageIO.read(new File("red_right2.png"));
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		}
		
		if (playerNumber == 2) {
			try {
				front1 = ImageIO.read(new File("gary_front1.png"));
				front2 = ImageIO.read(new File("gary_front2.png"));
				back1 = ImageIO.read(new File("gary_back1.png"));
				back2 = ImageIO.read(new File("gary_back2.png"));
				left1 = ImageIO.read(new File("gary_left1.png"));
				left2 = ImageIO.read(new File("gary_left2.png"));
				right1 = ImageIO.read(new File("gary_right1.png"));
				right2 = ImageIO.read(new File("gary_right2.png"));
			} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
			}
		}
	}

	// set image
	public void setState(String state) {
		this.state = state;
	}

	// draw function
	@Override
	public void draw(Graphics g) {
		playTime++;
		
		if (playTime % 5 == 0) playStep ++;
		
		int x = p_x;
		int y = p_y;
		int w = width;
		int h = height;
		
		if (hit_time > 0 && playStep%2 == 0) {
			x = x + 5;
			y = y + 5;
			w = w - 10;
			h = h - 10;
		}
		
		if (state == "front") {
			if (keyPressed) {
				if (playStep % 2 == 0) g.drawImage(front1, x, y, w, h, null);
				else g.drawImage(front2, x, y, w, h, null);
			}
			else g.drawImage(front1, x, y, w, h, null);
		}
		if (state == "back") {
			if (keyPressed) {
				if (playStep % 2 == 0) g.drawImage(back1, x, y, w, h, null);
				else g.drawImage(back2, x, y, w, h, null);
			}
			else g.drawImage(back1, x, y, w, h, null);
		}
		if (state == "left") {
			if (keyPressed) {
				if (playStep % 2 == 0) g.drawImage(left1, x, y, w, h, null);
				else g.drawImage(left2,x, y, w, h, null);
			}
			else g.drawImage(left1, x, y, w, h, null);;
		}
		if (state == "right") {
			if (keyPressed) {
				if (playStep % 2 == 0) g.drawImage(right1, x, y, w, h, null);
				else g.drawImage(right2, x, y, w, h, null);
			}
			else g.drawImage(right1, x, y, w, h, null);
		}
		lifeCounter.draw(g);
	}

	// decrease life by one
	public void died() {
		if (!hit) {
			lifeCounter.decrement();
		}
		hit = true;
		hit_time = 50;
	}

	public int getLives() {
		return lifeCounter.getCounter();
	}

	public void hit_time_decrement () {
		if (hit_time > 0) {
			hit_time = hit_time - 1;
		}
		if (hit_time == 0) {
			hit = false;
		}
	}

	// get strength of voltorb
	public int getStrength() {
		return strength;
	}

	// level up strength
	public void strengthLevelUp() {
		if (strength < 12) {
			strength = strength + 2;
		}
	}


}
