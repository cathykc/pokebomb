import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExplosionVertical extends PokeThings {
	
	private int duration;
	public static final int SIZE = 40;
	public static final String img_file = "explosion_vertical.png";
	private static BufferedImage img;
	
	public ExplosionVertical (int x, int y) {
		super(0, 0, x - x%40, y - y%40, SIZE, SIZE, 520, 440);
		duration = 70;
		
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