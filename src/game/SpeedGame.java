package game;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import players.HumanPlayer;

public class SpeedGame extends Game {
	
	public SpeedGame(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5723550093129176096L;
	Timer timer = new Timer();
	int timeLimit;
	/*
	public SpeedGame() {
		super();	
	}

	@Override
	public void startGame() {
		currentState = playing;
		String input;
		int x, y;
		while (currentState == playing) {
			timer = new Timer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					timeUp();
					System.out.println("Time's up!!!");
					endGame();
					return;
				}}, 1000*settings.getTimeLimit());
			System.out.println("Current turn: " + turn +
					" " + players.get(currentPlayer) + "'s move");
			if (players.get(currentPlayer).getClass() == HumanPlayer.class) 
				System.out.println("Please, Type Command or Enter Move (collum row): ");
			try {
				input = getInput();
				String[] co_ordinates = input.split("\\s+");
				x = Integer.parseInt(co_ordinates[0]);
				y = Integer.parseInt(co_ordinates[1]);
			} catch (Exception e) {
				System.out.println("Input Error");
				continue;
			}
			if (!board.nextMove(/*turn,*/ /*x, y)) continue;
			turn++;
			timer.cancel();
			currentState = board.checkBoard();
			currentPlayer = (currentPlayer + 1) % settings.getNumberOfPlayers();
		}
		endGame();
	}
	
	@Override
	public void setUp() {
		String input;
		super.setUp();
		while (true) {
			System.out.println("Set time limit (10-20seconds): ");
			input = getInput();
			reader = new Scanner(input);
			if (reader.hasNextInt()) {
				timeLimit = reader.nextInt();
				if (settings.setTimeLimit(timeLimit)) break;
			}
		}
	}
	
	public int timeUp() {
		return currentPlayer;
	}*/
}
