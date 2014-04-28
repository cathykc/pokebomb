import java.awt.Color;
import java.awt.Graphics;

public class LifeCounter extends PokeCounters {
	
	// constructor for life counter
	public LifeCounter(int player) {
		super((player - 1)*100, 10, "Player " + player + " Lives: ", 3);
	}
	
	// draw counter
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString(this.getLabel() + this.getCounter(), this.getX(), this.getY());
	}
}