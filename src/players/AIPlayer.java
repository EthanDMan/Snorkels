package players;

import java.io.Serializable;
import java.util.ArrayList;

import board.Board;
import pieces.Piece;
import pieces.Snorkel;

public class AIPlayer implements Player, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2045867894403611316L;
	static final int easy = 1;
	static final int hard = 2;
	static final int insane = 3;
	
	private int colour;
	private int nOP;
	private String name;
	private static int depth;
	private int curDepth;
	private Board board;
	
	public AIPlayer(int colour, String name, Board board, int level, int nOP) {
		this.colour = colour;
		this.name = name;
		this.board = board;
		this.nOP = nOP;
		curDepth = 0;
		setDifficulty(level);
	}

	@Override
	public String takeTurn() {
		curDepth = 0;
		return getBestMove(colour);
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
	
	public boolean setDifficulty(int level) {
		try {
			if (level == easy) depth = 1;
			if (level == hard) depth = 2;
			if (level == insane) depth = 3;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean changeName(String newName) {
		try {
			this.name = newName;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	int moveValue(int colour, int x, int y) {
		int score = 0;
		if (board.addPiece(colour, x, y)) {
			Snorkel piece = (Snorkel) board.getPiece(x, y);
			if (piece.blockedTubes(new ArrayList<Snorkel>()) == 4) score -= 80;
			for (Piece neighbour : piece.getNeighbours()) {
				if (neighbour == null) ++score;
				else if (neighbour.getType() == Piece.stone) score -= 1;
				else if (neighbour.getType() == colour) {
					if (((Snorkel) neighbour).blockedTubes(new ArrayList<Snorkel>()) == 3) score += 60;
					else score += (((Snorkel) neighbour).blockedTubes(new ArrayList<Snorkel>()) 
							- piece.blockedTubes(new ArrayList<Snorkel>()));
				}
				else {
					if (((Snorkel) neighbour).blockedTubes(new ArrayList<Snorkel>()) == 3) 
						score += 100;
					else score += (((Snorkel) neighbour).blockedTubes(new ArrayList<Snorkel>()) 
							- piece.blockedTubes(new ArrayList<Snorkel>())) + 1;
					score++;
				}
			}
			if (++curDepth < depth) {
				int nextColour = (colour + 1) % nOP;
				String nextPlayerMove = getBestMove(nextColour);
				String[] co_ordinates = nextPlayerMove.split("\\s+");
				int nextX = Integer.parseInt(co_ordinates[0]);
				int nextY = Integer.parseInt(co_ordinates[1]);
				score = score - moveValue(nextColour, nextX, nextY);
				--curDepth;
			}
			board.removePiece(x, y);
		}
		return score;
	}
	
	private String getBestMove(int playerType) {
		int bestScore = 0;
		int curVal;
		String bestMove = "";
		for (int i = 1; i <= board.getBoardSize(); i++) {
			for (int j = 1; j <= board.getBoardSize(); j++) {
				if (board.getPiece(j, i) == null) {
					if (bestScore <= 4) {
						curVal = moveValue(playerType, j, i);
						if (curVal >= bestScore) {
							bestScore = curVal;
							bestMove = (j)+" "+(i);
						}
					}
				}
				else if (board.getPiece(j, i).getType() < 3) {
					if (j < board.getBoardSize() && board.getPiece((j+1), i) == null) {
						curVal = moveValue(playerType, j+1, i);
						if (curVal > bestScore) {
							bestScore = curVal;
							bestMove = (j+1)+" "+(i);
						}
					}
					if (j > 1 && board.getPiece((j-1), i) == null) {
						curVal = moveValue(playerType, j-1, i);
						if (curVal > bestScore) {
							bestScore = curVal;
							bestMove = (j-1)+" "+(i);
						}
					}
					if (i < board.getBoardSize() && board.getPiece(j, (i+1)) == null) {
						curVal = moveValue(playerType, j, i+1);
						if (curVal > bestScore) {
							bestScore = curVal;
							bestMove = (j)+" "+(i+1);
						}
					}
					if (i > 1 && board.getPiece((j), (i-1)) == null) {
						curVal = moveValue(playerType, j, i-1);
						if (curVal > bestScore) {
							bestScore = curVal;
							bestMove = (j)+" "+(i-1);
						}
					}
				}
			}
		}
		board.printBoard();
		return bestMove;
	}
}
