package Engine;

import tags.Structure;

final public class TileCoordinate implements Structure {
	public int row, col;
	
	public int hashCode() {
		//http://stackoverflow.com/questions/919612/mapping-two-integers-to-one-in-a-unique-and-deterministic-way
		//Szudzik's function:
		//a >= b ? a * a + a + b : a + b * b;  where a, b >= 0
		return row >= col ? (row * row + row + col) : (row + col * col);
	}
}
