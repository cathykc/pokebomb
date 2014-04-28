import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExplosionCenter extends PokeThings {

	private int duration;
	public static final int SIZE = 40;	// switch to see if explosion has encountered obstacle (does not go past)
	public static final String img_file = "explosion_center.png";
	private static BufferedImage img;

	// switches to see if arms of explosion are blocked - can't go past obstacles
	public boolean leftBlocked;
	public boolean rightBlocked;
	public boolean topBlocked;
	public boolean bottomBlocked;

	public ExplosionCenter (int x, int y) {
		super(0, 0, x - x%40, y - y%40, SIZE, SIZE, 520, 440);
		duration = 70;
		leftBlocked = false;
		rightBlocked = false;
		topBlocked = false;
		bottomBlocked = false;
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}

	}

	public void countingDuration () {
		if (duration > 0) {
			duration = duration - 1;
		}
		else {
			duration = 0;
		}
	}

	public int getDuration () {
		return duration;
	}

	public void draw(Graphics g) {
		g.drawImage(img, p_x, p_y, width, height, null);
	}


}