package UserInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import board.Board;

public class SettingsPopUp extends JDialog {
	private int[] values;
	
	public SettingsPopUp(int type) {
		if (type == 0) makeNormalFrame();
		else makeNormalFrame();
		this.setVisible(true);
	}

	public void makeNormalFrame() {
	}
	
	public JPanel boardSettings() {
		JPanel panel = new JPanel(new GridLayout(0,3));
		JLabel label = new JLabel("Board Size: ");
		String[] options = {"Small", "Medium", "Large"};
		JComboBox<String> sizes = new JComboBox<String>(options);
		sizes.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sizes.getSelectedIndex();
				if (index == 0) values[0] = Board.small;
				else if (index == 1) values[0] = Board.med;
				else if (index == 3) values[0] = Board.large;
				else values[0] = 0;
			}
		});
		panel.add(label);
		panel.add(sizes);
		JLabel label2 = new JLabel("Board Size: ");
		String[] options2 = {"0", "1", "2", "3"};
		JComboBox<String> stones = new JComboBox<String>(options2);
		sizes.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sizes.getSelectedIndex();
				values[1] = index;
			}
		});
		panel.add(label2);
		panel.add(stones);
		return panel;
	}
	
	public int[] getValues() {
		return values;
	}
	
	public boolean isFinished() {
		if (values[0] != 0 && values[1] != -1) 
			return true;
		values[2] = 2;
		values[3] = 1;
		return false;
	}
}
