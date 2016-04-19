package pieces;

import java.io.Serializable;

public class Stone implements Piece, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3195191174130361153L;
	private int type;
	
	public Stone() {
		type = stone;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public int compareTo(Piece o) {
		if (this.getType() == o.getType()) return 0;
		else return 1;
	}

	@Override
	public void printPiece() {
		System.out.print(" S ");
	}

	@Override
	public String getImage() {
		return "Stone.png";
	}
}
