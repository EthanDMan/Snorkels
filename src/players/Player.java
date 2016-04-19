package players;

public interface Player {
	static final int purple = 0;
	static final int green = 1;
	static final int orange = 2;
	
	String takeTurn();
	String getName();
	int getColour();
}
