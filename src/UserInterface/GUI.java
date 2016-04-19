package UserInterface;

import game.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import players.Player;
import board.Board;

public class GUI {
	
	private JFrame frame;
	private Game curGame;
	private String userText;
	
	public GUI() {
		curGame = new Game();
		makeFrame();
	}
	
	private void makeFrame() {
		frame = new JFrame("Snorkels!");
		JPanel toolbar = makeToolbar();
		JPanel messageBar = makeMessageBar();
		
		frame.add(toolbar, BorderLayout.LINE_START);
		frame.add(curGame, BorderLayout.CENTER);
		frame.add(messageBar, BorderLayout.SOUTH);
		makeMenuBar(frame);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.requestFocusInWindow();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
        frame.setVisible(true);
	}
	
	private JPanel makeToolbar() {
		JPanel toolbar = new JPanel(new GridLayout(0, 1));
		
		JButton undo = new JButton("undo");
        undo.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { curGame.undo(); }
                           });
        undo.setSize(new Dimension(80, 20));
        toolbar.add(undo);
        
        JButton largerButton = new JButton("reset");
        largerButton.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) { curGame.reset(); }
                           });
        largerButton.setSize(new Dimension(80, 20));
        toolbar.add(largerButton);
		return toolbar;
	}
	
	private JPanel makeMessageBar() {
		JPanel messageBar = new JPanel(new BorderLayout());
		JLabel message = new JLabel("Type Here!!!");
		JTextField input = new JTextField();
		input.requestFocusInWindow();
		input.getDocument().addDocumentListener(new DocumentListener() {			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}		
			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);			
			}		
			@Override
			public void changedUpdate(DocumentEvent e) {
				try {
					userText = input.getText();
					if (userText.isEmpty() || userText.length() != 3) return;
					String[] co_ordinates = userText.split("\\s+");
					int x = Integer.parseInt(co_ordinates[0]);
					int y = Integer.parseInt(co_ordinates[1]);
					curGame.nextMove(x, y);
				} catch (Exception exception) {
					System.out.println(exception);
				}
			}
		});
		messageBar.add(message, BorderLayout.NORTH);
		messageBar.add(input, BorderLayout.CENTER);
		return messageBar;
	}
	
	private void makeMenuBar(JFrame frame) {
		final int SHORTCUT_MASK =
	            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
		JMenuBar menubar = new JMenuBar();
		JMenu menu, subMenu;
		JMenuItem item;	
		//Create NewGame option
		menu = new JMenu("Options");
		subMenu = new JMenu("New Game...");
		item = new JMenuItem("Normal");
		item.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Normal Game
				makeNewGame();
			}
		});
		subMenu.add(item);
		item = new JMenuItem("Speed");
		item.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, KeyEvent.ALT_MASK));
		item.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Speed Game
			}
		});
		subMenu.add(item);
		menu.add(subMenu);
		menu.addSeparator();
		item = new JMenuItem("Save");
		item.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Save
				if (curGame != null) curGame.saveGame();
			}
		});
		menu.add(item);
		item = new JMenuItem("Load");
		item.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Load
				File saveFile = new File("Snorkels.sav");
				if (saveFile.exists()) {
					try {
						FileInputStream destination = new FileInputStream(saveFile);
						ObjectInputStream load = new ObjectInputStream(destination);
						curGame.setVisible(false);
						frame.remove(curGame);
						curGame = (Game) load.readObject();
						load.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("load successfull");
					frame.add(curGame, BorderLayout.CENTER);
					curGame.setEnabled(true);
					curGame.startGame();
				}
			}
		});
		menu.add(item);
		menu.addSeparator();
		menubar.add(menu);
		menu = new JMenu("Settings");
		item = new JCheckBoxMenuItem("Mute");
		item.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_M, SHORTCUT_MASK));
		item.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Mute		
			}
		});
		//Initialise Muted
		item.doClick();
		menu.add(item);
		menubar.add(menu);
		frame.setJMenuBar(menubar);
	}
	
	private void toggleMusic() {
		
	}
	
	private void makeNewGame() {
		SettingsPopUp popup = new SettingsPopUp(0);
		while (popup != null) {
			//if (popup.isFinished()) {
				int[] v = popup.getValues();
				frame.remove(curGame);
				Settings settings = new Settings(v[0], v[1], v[2], v[3]);
				frame.setSize(settings.getBoardSize()*64+90, settings.getBoardSize()*64+60);
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		        frame.setLocation(d.width/2 - frame.getWidth()/2, d.height/2 - frame.getHeight()/2);
				curGame = new Game(settings);
				frame.add(curGame, BorderLayout.CENTER);
				curGame.setVisible(true);
				break;
			//}
		}
		String userInput = JOptionPane.showInputDialog("Please enter ...");
		//Integer userInpu = (Integer) JOptionPane.showInputDialog(null, "choose 2", "title 2", 
		//		JOptionPane.OK_CANCEL_OPTION, null, new int[] {2,3}, 2);
		curGame.setVisible(false);
		frame.remove(curGame);
		Settings settings = new Settings(Board.small, 3, 3, 2);
		curGame = new Game(settings);
		frame.add(curGame, BorderLayout.CENTER);
		curGame.setEnabled(true);
		curGame.startGame();
	}
}
