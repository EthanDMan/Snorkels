package pieces;

public interface Piece extends Comparable<Piece> {
	static final int stone = 3;
	static final int purple = 0;
	static final int green = 1;
	static final int orange = 2;
	
	int getType();
	String getImage();
	void printPiece();
}
