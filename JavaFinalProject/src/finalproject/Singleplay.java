package finalproject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class Singleplay extends Game {

	private JFrame frame;
	private Game game;

	/**
	 * Launch the application.
	 */
	public void run() {
		try {
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Singleplay() {}
	public Singleplay(Game game) {
		setDigit(game.getDigit());
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("New label");
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		lblNewLabel.setText(Integer.toString(getDigit()));
	}

}
