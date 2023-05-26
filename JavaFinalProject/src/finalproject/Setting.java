package finalproject;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;

public class Setting extends Game {

	private JFrame frame;
	private JTextField textField;
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
	public Setting() {}
	public Setting(Game game) {
		this.game = game;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 320);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setText("원하는 자릿수를 입력하세요");
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		textField.setBounds(64, 96, 272, 51);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 205, 388, 81);
		frame.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("Initialize");
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDigit(4);
			}
		});
		
		JButton btnNewButton_2 = new JButton("Save");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String inputText = textField.getText();
                try {
                    game.setDigit(Integer.parseInt(inputText)); // 입력된 값을 정수로 변환하여 digit에 저장
                    textField.setText("저장되었습니다");
                } catch (NumberFormatException ex) {
                    textField.setText("올바른 숫자를 입력하세요");
                }
			}
		});
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JButton btnNewButton_1 = new JButton("Back to home");
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.run();
				frame.setVisible(false);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(16)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(106)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_2))
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addComponent(btnNewButton_1)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
	}
}
