package game;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pieces.Piece;
import pieces.Snorkel;
import players.AIPlayer;
import players.HumanPlayer;
import players.Player;
import board.Board;


public class Game extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5759682107659638537L;
	public static final int playing = 1;
	static final int draw = 0;
	static final int gameOver = -1;
	
	protected Board board;
	protected JButton[][] buttons;
	protected Settings settings;
	private File saveFile;
	
	protected int turn;
	private static int currentState;
	private Stack<String> prevMoves;
	
	protected ArrayList<Player> players;
	protected int currentPlayer;
	
	public Game() {
		this.setPreferredSize(new Dimension(64*7, 64*7));
		setEnabled(false);
		setVisible(true);
		prevMoves = new Stack<String>();
	}
	
	public Game(Settings settings) {		
		prevMoves = new Stack<String>();
		currentPlayer = 0;
		this.settings = settings;
		players = new ArrayList<Player>(settings.getNumberOfPlayers());
		turn = 0;
		try {
			saveFile = new File("Snorkels.sav");
			if (!saveFile.exists()) {
					saveFile.createNewFile();
			}
		} catch (Exception e) {
			System.out.println("IO error");
		}
		board = new Board(settings.getBoardSize());
		this.setLayout(new GridLayout(settings.getBoardSize(), settings.getBoardSize()));
		buttons = new JButton[settings.getBoardSize()][settings.getBoardSize()];
		for (int i = 0; i < settings.getBoardSize(); i++) {
			for (int j = 0; j < settings.getBoardSize(); j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].setSize(64, 64);
				buttons[i][j].setIcon(null);
				this.add(buttons[i][j]);
			}
		}
		for (int i = 0; i < settings.getNumberOfStones();) {
			Random randGen = new Random();
			int x = (randGen.nextInt() % settings.getBoardSize()) + 1;
			int y = (randGen.nextInt() % settings.getBoardSize()) + 1;
			if (board.addStone(x, y)) {
				buttons[y-1][x-1].setIcon(new ImageIcon(board.getPiece(x, y).getImage()));
				i++;
			}
		}
		addHumanPlayer();
		for (int i = 1; i < settings.getNumberOfPlayers();) {
			String[] options = {"Human", "Computer"};
			int choice = JOptionPane.showOptionDialog(null, "Human or AI?", ("Create Player "+i), 
					JOptionPane.OK_OPTION, JOptionPane.OK_OPTION, null, options, options[1]);
			if (choice == 0) {
				addHumanPlayer();
				i++;
			}
			else if (choice == 1) {
				addAIPlayer();
				i++;
			}
		}
	}
	
/*	public String getInput() {
		String input;
		while (true) {
			input = players.get(currentPlayer).takeTurn();
			if (input.isEmpty()) continue;
			switch(input) {
				case "options": 
					options();
					return "";
				case "settings": 
					settings();
					return "";
				case "quit": 
					saveAndQuit();
					return "";
				case "load":
					loadGame();
					return "";
				case "save":
					saveGame();
					return "";
				case "reset":
					reset();
					return "";
				case "undo":
					undo();
					return "";
			}
			break;
		}
		return input;
	}*/

	public void startGame() {
		System.out.println("Starting Game!");
		board.printBoard();
		currentState = playing;
		for (int i = 0; i < settings.getBoardSize(); i++) {
			for (int j = 0; j < settings.getBoardSize(); j++) {
				buttons[i][j].setActionCommand((j+1)+" "+(i+1));
				buttons[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String pos = e.getActionCommand();
						String[] co_ordinates = pos.split("\\s+");
						int x = Integer.parseInt(co_ordinates[0]);
						int y = Integer.parseInt(co_ordinates[1]);
						nextMove(x, y);
					}
				});
			}
		}
		this.setVisible(true);
		this.setBackground(Color.ORANGE);
	}
	
	public boolean nextMove(int x, int y) {
		if (board.addPiece(players.get(currentPlayer).getColour(), x, y) && currentState == playing) {
			prevMoves.push(x+" "+y);
			turn++;
			buttons[y-1][x-1].setIcon(new ImageIcon(board.getPiece(x, y).getImage()));
			if (board.checkBoard() != playing && players.size() > board.checkBoard()) 
				players.remove(board.checkBoard());
			currentPlayer = turn % players.size();
			if (players.size() == 1) endGame();
			else if (players.get(currentPlayer).getClass() == AIPlayer.class) {
				String pos = players.get(currentPlayer).takeTurn();
				String[] co_ordinates = pos.split("\\s+");
				int nextX = Integer.parseInt(co_ordinates[0]);
				int nextY = Integer.parseInt(co_ordinates[1]);
				nextMove(nextX, nextY);
			}
			return true;
		}	
		return false;
	}
/*
	@SuppressWarnings("unchecked")
	public void loadGame() {
		try {
			FileInputStream destination = new FileInputStream(saveFile);
			ObjectInputStream load = new ObjectInputStream(destination);
			board = (Board) load.readObject();
			players = (ArrayList<Player>) load.readObject();
			turn = load.readInt();
			settings = (Settings) load.readObject();
			load.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("load successfull");
		startGame();
	}
*/	
	public boolean saveGame() {
		try {
			FileOutputStream destination = new FileOutputStream(saveFile);
			ObjectOutputStream save = new ObjectOutputStream(destination);
			save.writeObject(this);
			save.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("Save successful");
		return true;
	}

	public void endGame() {
		if (currentState == draw) {
			System.out.println("A draw, really?");
		}
		else {
			JOptionPane.showMessageDialog(null, players.get(0).getName() + " wins!", "Game Over!!", 
					JOptionPane.OK_OPTION);
		}
		setVisible(false);
		currentState = gameOver;
	}
	
	public void addHumanPlayer() {
		if (players.size() < 3) {
			players.add(new HumanPlayer(players.size(), ("Player "+(players.size()+1))));
		}
		else System.out.println("Too many cooks spoil the broth");
	}
	
	public void addAIPlayer() {
		if (players.size() < 3) {
			players.add(new AIPlayer(players.size(), 
					("player"+(players.size()+1)), board, settings.getDifficulty(), settings.getNumberOfPlayers()));
		}
	}
	
	public void reset() {
		currentPlayer = 0;
		turn = 0;
		if (board != null) board.reset();
	}
	
	public void undo() {
		System.out.println("Undo");
		if (!prevMoves.isEmpty() && currentState == playing) {
			do {
				String pos = prevMoves.pop();
				String[] co_ordinates = pos.split("\\s+");
				int x = Integer.parseInt(co_ordinates[0]);
				int y = Integer.parseInt(co_ordinates[1]);
				if (board.removePiece(x, y)) buttons[y-1][x-1].setIcon(null);
				--turn;
				currentPlayer = turn % players.size();
			} while (players.get(currentPlayer).getClass() == AIPlayer.class);
		}
	}

	public void saveAndQuit() {
		System.out.println("Quitting");
		saveGame();
		endGame();
		//System.exit(quit);
	}
}
