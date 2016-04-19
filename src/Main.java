import java.util.Scanner;

import UserInterface.GUI;
import game.Game;
import game.SpeedGame;


public class Main {
	static Scanner scan;

	public static void main(String[] args) {
		System.out.println("Snorkels");
		new GUI();
		while (true) {
			scan = new Scanner(System.in);
			System.out.println("New Game (speed/yes/no)?");
			String input = scan.next();
			if (input.equalsIgnoreCase("speed")) 
				//new SpeedGame();
			if (input.equalsIgnoreCase("yes"));
				//new Game();
			else if (input.equalsIgnoreCase("no")) break;
			scan.close();
		}
	}

}
