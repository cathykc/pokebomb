import java.awt.Graphics;

// counters in PokeBomb - lives, timer?
public class PokeCounters {

	// position of object - upper-left corner
	private int x; 
	private int y;
	private String label;
	private int counter;

	// counter constructor
	public PokeCounters(int x, int y, String label, int init_counter){
		this.x = x;
		this. y = y;
		this.label = label;
		this.counter = init_counter;
	}

	// get counter
	public int getCounter() {
		return counter;
	}

	// increment counter
	public void increment() {
		counter = counter + 1;
	}

	// decrement counter
	public void decrement() {
		if (counter > 0) {
			counter = counter - 1;
		}
	}

	// get x position
	public int getX() {
		return x;
	}

	// get y position
	public int getY() {
		return y;
	}

	// get label
	public String getLabel() {
		return label;
	}

	// draw method to be implemented
	public void draw(Graphics g) {
	}

}
