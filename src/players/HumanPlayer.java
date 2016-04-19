package players;

import java.io.Serializable;
import java.util.Scanner;

public class HumanPlayer implements Player, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 589405022240220191L;
	private int colour;
	private String name;
	private transient Scanner input;
	
	public HumanPlayer(int colour, String name) {
		
		this.colour = colour;
		this.name = name;
	}

	@Override
	public String takeTurn() {
		input = new Scanner(System.in);
		String move = "";
		move = input.nextLine();
		return move;
	}

	@Override
	public int getColour() {
		return colour;
	}
	

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public boolean changeName(String newName) {
		try {
			this.name = newName;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
