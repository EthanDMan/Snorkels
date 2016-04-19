package pieces;
import java.io.Serializable;
import java.util.ArrayList;


public class Snorkel implements Piece, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 780490978119845679L;
	private int type;
	private String image;
	private ArrayList<Piece> neighbours;
	
	public Snorkel(int type) {
		this.type = type;
		image = "Snorkel"+type+".png";
		neighbours = new ArrayList<Piece>(4);
	}
	@Override
	public int getType() {
		return type;
	}
	
	public void flushNeighbours() {
		neighbours = new ArrayList<Piece>(4);
	}

	public ArrayList<Piece> getNeighbours() {
		return neighbours;
	}
	
	public int selfCapture() {
		for (Piece neighbour : neighbours) {
			if (neighbour.getType() == stone) continue;
			else if (((Snorkel) neighbour).getType() != this.getType()) {
				if (((Snorkel) neighbour).blockedTubes(new ArrayList<Snorkel>()) == 4) {
					return neighbour.getType();
				}
			}
		}
		return this.getType();
	}
/*
	public boolean isCaptured(ArrayList<Snorkel> visited) {
		visited.add(this);
		for (Piece neighbour : neighbours) {
			if (neighbour == null) {
				return false;
			}
			else if (this.getType() == neighbour.getType() && !(visited.contains(neighbour))) {
			    if (!((Snorkel) neighbour).isCaptured(visited)) {
			    	return false;
			    }
			}
		}
		return true;
	}
*/
	public boolean addNeighbour(Piece neighbour) {
		if (neighbours.size() >= 4) return false;
		try {
			neighbours.add(neighbour);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public int blockedTubes(ArrayList<Snorkel> visited) {
		visited.add(this);
		int blocked = 0;
		for (Piece p : neighbours) {
			if (p == null) continue;
			else if (p.getType() != this.type) ++blocked;
			else if (!visited.contains(p)) {
				if (((Snorkel) p).blockedTubes(visited) == 3) ++blocked;
			}
		}
		if (neighbours.size() < 4) blocked += (4-neighbours.size());
		return blocked;
	}

	@Override
	public int compareTo(Piece p) {
		if (this.getType() == p.getType()) return 0;
		else return 1;
	}

	@Override
	public void printPiece() {
		if (this.getType() == purple)
			System.out.print(" P ");
		else if (this.getType() == green)
			System.out.print(" G ");
		else if (this.getType() == orange)
			System.out.print(" O ");
	}
	@Override
	public String getImage() {
		return image;
	}
}
