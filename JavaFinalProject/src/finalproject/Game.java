package finalproject;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class Game {

	private JFrame frame;
	private int digit;
	
	public void setDigit(int digit) {
		this.digit = digit;
	}
	
	public int getDigit() {
		return digit;
	}

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
	public Game() {
		initialize();
		setDigit(4);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(238, 238, 238));
		frame.setBounds(100, 100, 400, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {400, 0};
		gridBagLayout.rowHeights = new int[]{65, 226, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridheight = 3;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		
		JLabel lblNewLabel = new JLabel("숫자야구");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 32));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton = new JButton("Single Play");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Singleplay singleplay = new Singleplay(Game.this);
					singleplay.run();
					frame.setVisible(false);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Multi Play");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JButton btnNewButton_2 = new JButton("Setting");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Setting setting = new Setting(Game.this);
					setting.run();
					frame.setVisible(false);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JButton btnNewButton_3 = new JButton("Exit");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
				.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
				.addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
				.addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(18)
					.addComponent(lblNewLabel)
					.addGap(36)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}
}
