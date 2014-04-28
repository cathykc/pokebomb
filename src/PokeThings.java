import java.awt.Graphics;

// object in PokeBomb - players, crates, boxes etc.
public class PokeThings {

	// position of object - upper-left corner
	public int p_x; 
	public int p_y;

	// size of object in pixels
	public int width;
	public int height;

	// velocity of object
	public int v_x;
	public int v_y;

	// maximum bounds object can be positioned
	public int max_x;
	public int max_y;

	// object constructor
	public PokeThings(int v_x, int v_y, int p_x, int p_y, 
			int width, int height, int field_width, int field_height){
		this.v_x = v_x;
		this.v_y = v_y;
		this.p_x = p_x;
		this.p_y = p_y;
		this.width = width;
		this.height = height;

		// takes into account size of object in calculating bounds
		this.max_x = field_width - width;
		this.max_y = field_height - height;

	}

	// moves object according to velocity - clips if out of bounds
	public void move(){
		p_x += v_x;
		p_y += v_y;
		clip();
	}

	// prevents object from going out of bounds
	public void clip(){
		if (p_x < 0) p_x = 0;
		else if (p_x > max_x) p_x = max_x;

		if (p_y < 0) p_y = 0;
		else if (p_y > max_y) p_y = max_y;	
	}

	// checks if object is intersecting other object
	public boolean intersects(PokeThings obj){
		return (p_x + width > obj.p_x
				&& p_y + height > obj.p_y
				&& obj.p_x + obj.width > p_x 
				&& obj.p_y + obj.height > p_y);
	}


	// checks if object is intersecting other objects
	public boolean intersects(PokeThings[][] objArr){

		boolean intersect = false;
		for (int i = 0; i < objArr.length; i++) {
			for (int j = 0; j < objArr[0].length; j++) {
				if (this.intersects(objArr[i][j])) intersect = true;
			}
		}
		return intersect;
	}


	// checks if object will intersect with other object in next step
	public boolean willIntersect(PokeThings obj){
		int next_x = p_x + v_x;
		int next_y = p_y + v_y;
		int next_obj_x = obj.p_x + obj.v_x;
		int next_obj_y = obj.p_y + obj.v_y;
		return (next_x + width >= next_obj_x
				&& next_y + height >= next_obj_y
				&& next_obj_x + obj.width >= next_x 
				&& next_obj_y + obj.height >= next_y);
	}

	// checks if object will intersect with other objects in next step
	public boolean willIntersect(PokeThings[][] objArr){

		boolean willIntersect = false;
		for (int i = 0; i < objArr.length; i++) {
			for (int j = 0; j < objArr[0].length; j++) {
				int next_x = p_x + v_x;
				int next_y = p_y + v_y;
				int next_obj_x = objArr[i][j].p_x + objArr[i][j].v_x;
				int next_obj_y = objArr[i][j].p_y + objArr[i][j].v_y;
				if (next_x + width >= next_obj_x
						&& next_y + height >= next_obj_y
						&& next_obj_x + objArr[i][j].width >= next_x 
						&& next_obj_y + objArr[i][j].height >= next_y) willIntersect = true;
			}
		}
		return willIntersect;
	}

	public Direction hitObj(PokeThings other) {
		if (this.willIntersect(other)) {
			double dx = other.p_x + other.width /2 - (p_x + width /2);
			double dy = other.p_y + other.height/2 - (p_y + height/2);

			double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy *dy)));
			double diagTheta = Math.atan2(height / 2, width / 2);

			if (theta <= diagTheta ) {
				return Direction.RIGHT;
			} else if ( theta > diagTheta && theta <= Math.PI - diagTheta ) {
				if ( dy > 0 ) {
					// Coordinate system for GUIs is switched
					return Direction.DOWN;
				} else {
					return Direction.UP;
				}
			} else {
				return Direction.LEFT;
			}
		} else {
			return null;
		}

	}


	// draw method to be implemented
	public void draw(Graphics g) {
	}

}
