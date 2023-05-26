package finalproject;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Singleplay extends Play {

	private JFrame frame;
	private Game game;
	private JLabel turnLabel;
	private JLabel playerResultLabel;
	private JLabel computerResultLabel;
	private JTextField playerTextField;
	private JTextField computerTextField;
	private static Object lock = new Object();
	Player player;
	Computer computer;

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
	public Singleplay(Game game) {
		this.game = game;
		setDigit(game.getDigit());
		setTurn(0);
		
		player = new Player(getDigit());
		computer = new Computer(getDigit());
		
		initialize(); // set player's number
		
		appendLog("Game Start!\n");
		appendLog("Enter your number.");
	}
	
	public String Array2String(int askNumber[]) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < getDigit(); i++) {
			sb.append(askNumber[i]);
			if (i != getDigit() - 1) sb.append(" ");
		}
		String string_askNumber = sb.toString();
		return string_askNumber;
	}
	
	Thread runThread = new Thread(new Runnable() {
		public void run() {
			while(true) {
				setTurn(getTurn()+1);
				
				int askNumber[] = computer.askNumber(getDigit(), getTurn());
				String string_askNumber = Array2String(askNumber);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				computerTextField.setText(string_askNumber);
				
				turnLabel.setText("Turn: " + getTurn());
				appendLog("--------------Turn: " + getTurn() + "--------------");
				appendLog("Computer: " + string_askNumber + "\n");
				int[] calculateResult = calculateResult(askNumber, player.getArray());
				String resultString = calculateResult[0] + " Strike " + calculateResult[1] + " Ball";
				if(calculateResult[0] == getDigit()) {
					playerResultLabel.setForeground(Color.RED);
					playerResultLabel.setText("You Lose!\n");
					computerResultLabel.setText("");
					appendLog("You Lose!\n");
					return;
				}
				else if(calculateResult[0] == 0 && calculateResult[1] == 0) resultString = "OUT!";
				computerResultLabel.setText(resultString);
				appendLog(resultString + "\n");
				
				computer.calculateCandidates(getDigit(), askNumber, calculateResult);
				
				synchronized(lock) {
		            try {
		                lock.wait(); // 버튼이 눌리기를 기다림
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
				
				string_askNumber = playerTextField.getText();
				String[] numbers = string_askNumber.split(" ");
				for(int i = 0; i < getDigit(); i++) {
            		askNumber[i] = Integer.parseInt(numbers[i]);
            	}
				
				appendLog("Player  : " + string_askNumber + "\n");
				calculateResult = calculateResult(askNumber, computer.getArray());
				resultString = calculateResult[0] + " Strike " + calculateResult[1] + " Ball";
				if(calculateResult[0] == getDigit()) {
					playerResultLabel.setForeground(Color.RED);
					playerResultLabel.setText("You Win!\n");
					computerResultLabel.setText("");
					appendLog("You Win!\n");
					return;
				}
				else if(calculateResult[0] == 0 && calculateResult[1] == 0) resultString = "OUT!";
				playerResultLabel.setText(resultString);
				appendLog(resultString + "\n");
			}
		}
    });
	
	public Boolean checkValid(String[] numbers) {
		if(numbers.length != getDigit()) {
        	appendLog("You should enter " + getDigit() + " digits.");
        	return false;
        }
		for(int i = 0; i < getDigit(); i++) {
        	if(Integer.parseInt(numbers[i])/10 > 0) {
        		appendLog("You should enter " + getDigit() + " digits.");
            	return false;
        	}
        }
		for(int i = 0; i < getDigit(); i++) {
        	for(int j = 0; j < i; j++) {
        		if(numbers[i].equals(numbers[j])) {
        			appendLog("Duplicate numbers are not allowed.");
        			return false;
        		}
        	}
        }
		return true;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 452);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 6, 475, 412);
		frame.getContentPane().add(panel);
		
		playerTextField = new JTextField();
		playerTextField.setForeground(Color.GRAY);
		playerTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		playerTextField.setText("ex: 0 1 2 3");
		playerTextField.setBounds(161, 44, 216, 45);
		playerTextField.setColumns(10);
		playerTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = playerTextField.getText();
                String[] numbers = input.split(" ");
                if(!checkValid(numbers)) return;
                
                if(getTurn() == 0) {
                	for(int i = 0; i < getDigit(); i++) {
                		player.setNumber(i, Integer.parseInt(numbers[i]));
                	}
                	appendLog("Your number is " + input + ".\n");
                	playerTextField.setText("");
                
                	runThread.start();
                }
                else {
                	synchronized(lock) {
                        lock.notify(); // 대기 중인 스레드 깨우기
                    }
                }
            }
        });
		
		JLabel lblNewLabel_1 = new JLabel("Player");
		lblNewLabel_1.setBounds(80, 50, 69, 30);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		
		JLabel lblNewLabel_1_1 = new JLabel("Computer");
		lblNewLabel_1_1.setBounds(33, 180, 116, 30);
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(42, 315, 388, 81);
		
		JButton btnNewButton = new JButton("New game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JButton btnNewButton_2 = new JButton("Help");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JButton btnNewButton_1 = new JButton("Back to home");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.run();
				frame.setVisible(false);
			}
		});
		btnNewButton_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 388, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(16)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(106)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 81, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_2))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnNewButton_1)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(null);
		
		turnLabel = new JLabel("New label");
		turnLabel.setBounds(6, 6, 375, 16);
		panel.add(turnLabel);
		turnLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		turnLabel.setText("Turn: " + getTurn());
		panel.add(lblNewLabel_1_1);
		panel.add(lblNewLabel_1);
		panel.add(playerTextField);
		
		computerTextField = new JTextField();
		computerTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		computerTextField.setColumns(10);
		computerTextField.setBounds(161, 173, 216, 45);
		computerTextField.setEditable(false);
		panel.add(computerTextField);
		
		playerResultLabel = new JLabel("");
		playerResultLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		playerResultLabel.setBounds(165, 94, 216, 45);
		panel.add(playerResultLabel);
		
		computerResultLabel = new JLabel("");
		computerResultLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		computerResultLabel.setBounds(165, 223, 216, 45);
		panel.add(computerResultLabel);
		panel.add(panel_1);
		
		logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		logTextArea.setBounds(474, 9, 220, 409);
		logTextArea.setEditable(false);
		frame.getContentPane().add(logTextArea);
		
		JScrollPane scrollPane = new JScrollPane(logTextArea);
		scrollPane.setBounds(477, 6, 317, 412);
		frame.getContentPane().add(scrollPane);
	}
}
