package game;

import java.io.Serializable;

import players.Player;

public class Settings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4390316689459579337L;
	private int numberOfPlayers;
	private int numberOfStones;
	private int boardSize;
	//private int highScore;
	private int difficulty;
	private int scoreLimit;
	private int timeLimit;
	
	public Settings(int size, int nOS,int nOP, int diff) {
		boardSize = size;
		numberOfStones = nOS;
		numberOfPlayers = nOP;
		difficulty = diff;
		scoreLimit = 1;
	}
	
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	public int getNumberOfStones() {
		return numberOfStones;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public boolean setTimeLimit(int limit) {
		if (limit >= 10 && limit <= 20) {
			timeLimit = limit;
			return true;
		}
		return false;
	}
}
