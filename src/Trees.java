import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Trees extends PokeThings {

	public static final int SIZE = 40;
	public static final String img_file = "tree.png";
	private static BufferedImage img;

	public Trees(int x, int y) {
		super(0, 0, x, y, SIZE, SIZE, 520, 440);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, p_x, p_y, width, height, null);
	}


}