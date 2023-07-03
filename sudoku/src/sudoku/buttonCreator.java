package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class buttonCreator {
	private JButton button;
	
	public JButton createButton() {
		button = new JButton("Surrender");
		return button;
	}
	public JButton getButton() {
		return button;
	}
	public JButton hint() {
		button = new JButton("Hint");
		return button;
	}
	public JButton getHint() {
		return button;
	}
}
