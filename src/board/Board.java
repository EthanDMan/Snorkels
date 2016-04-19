package board;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pieces.Piece;
import pieces.Snorkel;
import pieces.Stone;

public class Board implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4202858875953610434L;
	public static final int small = 7;
	public static final int med = 9;
	static final int playing = game.Game.playing;
	public static final int large = 11;
	
	private int boardSize;
	private int turn;
	private boolean gameOver = false;

	private Snorkel newestPiece;	
	
	private Piece[][] board;
	
	public Board(int size) {
		boardSize = size;
		board = new Piece[boardSize][boardSize];
		turn = 0;
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	public boolean addPiece(int colour, int x, int y) {
		if (((x < 1 || x > boardSize || y < 1 || y > boardSize) || getPiece(x, y) != null) 
			|| gameOver)
			//throw new InvalidMoveException("Illegal move");
			return false;
		board[y-1][x-1] = newestPiece = new Snorkel(colour);
		updateNeighbours(newestPiece, x, y);
		return true;
	}
	
	public Piece getPiece(int x, int y) {
		return board[y-1][x-1];
	}
	
	public boolean removePiece(int x, int y) {
		if (x < 1 || x > boardSize || y < 1 || y > boardSize) 
			//throw new InvalidMoveException("Illegal move");
			return false;
		board[y-1][x-1] = null;
		return true;
	}
	
	public boolean addStone(int x, int y) {
		if ((x < 1 || x > boardSize || y < 1 || y > boardSize) || getPiece(x, y) != null) 
			//throw new InvalidMoveException("Illegal move");
			return false;
		board[y-1][x-1] = new Stone();
		return true;
		
	}
	
	public void updateNeighbours(Snorkel s, int x, int y) {
		s.flushNeighbours();
		if (x-2 >= 0) s.addNeighbour(board[y-1][x-2]);
		if (x < boardSize) s.addNeighbour(board[y-1][x]);
		if (y-2 >= 0) s.addNeighbour(board[y-2][x-1]);
		if (y < boardSize) s.addNeighbour(board[y][x-1]);
	}
	
	public void printBoard() {
		System.out.println("Board\n");
		for (int n = 1; n <= boardSize; n++) System.out.print("  "+n+" ");
		System.out.println();
		System.out.println("-----------------------------");
		for (int i = 1; i <= boardSize; i++) {
			for (int j = 1; j <= boardSize; j++) {
				System.out.print("|");
				if (getPiece(j,i) == null) System.out.print("   ");
				else getPiece(j,i).printPiece();
			}
			System.out.print("| " + i);
			System.out.println("\n-----------------------------");
		}		
	}
	
	public int checkBoard() {
		if (newestPiece != null) if (newestPiece.blockedTubes(new ArrayList<Snorkel>()) == 4) {
			return newestPiece.selfCapture();
		}
		for (int i = 1; i <= boardSize; i++) {
			for (int j = 1; j <= boardSize; j++) {
				if (getPiece(j, i) == null) continue;
				Piece p = getPiece(j, i);
				if (p.getType() != Piece.stone) {
					updateNeighbours(((Snorkel) p), j, i);
					if (((Snorkel) p).blockedTubes(new ArrayList<Snorkel>()) == 4) {
						if (p.getType() == Piece.purple) {
							//JOptionPane.showMessageDialog(null, "Purple lose!", "Game Over!!", 
							//		JOptionPane.OK_OPTION);
							return Piece.purple;
						}
						else if (p.getType() == Piece.green) {
							//JOptionPane.showMessageDialog(null, "Green lose!", "Game Over!!", 
							//		JOptionPane.OK_OPTION);
							return Piece.green;
						}
						else if (p.getType() == Piece.orange) {
							//JOptionPane.showMessageDialog(null, "Orange lose!", "Game Over!!", 
							//		JOptionPane.OK_OPTION);
							return Piece.orange;
						}
					}
				}
			}
		}
		return playing;
	}
	
	public void reset() {
		board = new Piece[boardSize][boardSize];
	}
}
