import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Voltorb extends PokeThings {

	public static final int SIZE = 20;
	private static BufferedImage img;
	private static BufferedImage img2;
	private int playTime;
	private int playStep;
	private int playerNo;


	private int countdown;
	private int strength;

	public Voltorb (int x, int y, int strength, int playerNo) {
		super(0, 0, (x+12) - (x+12)%40 + 10, (y+12) - (y+12)%40 + 10, SIZE, SIZE, 520, 440);
		countdown = 150;
		this.strength = strength;
		this.playerNo = playerNo;

		try {
			if (img == null) {
				img = ImageIO.read(new File("voltorb.png"));
				img2 = ImageIO.read(new File("electrode.png"));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	public void draw(Graphics g) {
		playTime++;
		if(playTime % 5 == 0) playStep++;
		if (playerNo == 1) {
			if (playStep % 2 == 0) {
				g.drawImage(img, p_x, p_y, width, height, null);
			}
			else g.drawImage(img, p_x-2, p_y-2, width+4, height+4, null);
		}
		else if (playerNo == 2) {
			if (playStep % 2 == 0) {
				g.drawImage(img2, p_x, p_y, width, height, null);
			}
			else g.drawImage(img2, p_x-2, p_y-2, width+4, height+4, null);
		}
	}



	public void countingDown () {
		if (countdown > 0) {
			countdown = countdown - 1;
		}
		else {
			countdown = 0;
		}
	}

	public int getCount () {
		return countdown;
	}

	public int getStrength() {
		return strength;
	}
}