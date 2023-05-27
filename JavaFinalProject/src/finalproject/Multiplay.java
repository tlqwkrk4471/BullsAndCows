package finalproject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Multiplay extends Play {

	private JFrame frame;
	private JLabel turnLabel;
	private JLabel[] playerResultLabel;
	private JPasswordField[] playerTextField;
	private static Object lock = new Object();
	private Game game;
	private Player[] player;

	/**
	 * Launch the application.
	 */
	public void run() {
		try {
			frame.setVisible(true);
			multiplayThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public Multiplay(Game game) {
		this.game = game;
		setDigit(game.getDigit());
		setTurn(0);
		
		player = new Player[2];
		player[0] = new Player(getDigit());
		player[1] = new Player(getDigit());
		
		initialize();
	}
	
	Thread multiplayThread = new Thread(new Runnable() {
		public void run() {
			
			appendLog("Game Start!\n");
			for(int p = 0; p < 2; p++) {
				appendLog("Enter Player" + (p+1) + "'s number.");
				if(p == 0) {
					playerTextField[0].setEditable(true);
					playerTextField[1].setEditable(false);
				}
				else {
					playerTextField[0].setEditable(false);
					playerTextField[1].setEditable(true);
				}
				
				synchronized(lock) {
		            try {
		                lock.wait(); // 버튼이 눌리기를 기다림
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
				
				String input = String.valueOf(playerTextField[p].getPassword());
				String[] numbers = input.split(" ");
				for(int i = 0; i < getDigit(); i++) {
					player[p].setNumber(i, Integer.parseInt(numbers[i]));
				}
				playerTextField[p].setText("");
				appendLog("Player" + (p+1) + "'s number is saved.\n");
			}
			
			while(true) {
				setTurn(getTurn()+1);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				turnLabel.setText("Turn: " + getTurn());
				appendLog("--------------Turn: " + getTurn() + "--------------");
				
				playerTextField[0].setEchoChar('\u0000');
				playerTextField[0].setForeground(Color.GRAY);
				playerTextField[0].setText("Deduce number.");
				playerTextField[0].setEditable(true);
				playerTextField[1].setEditable(false);
				
				synchronized(lock) {
		            try {
		                lock.wait(); // 버튼이 눌리기를 기다림
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
				
				int askNumber[] = new int[getDigit()];
				String string_askNumber;
				String[] numbers = new String[getDigit()];
				string_askNumber = String.valueOf(playerTextField[0].getPassword());
				playerTextField[0].setText("");
				numbers = string_askNumber.split(" ");
				for(int i = 0; i < getDigit(); i++) {
            		askNumber[i] = Integer.parseInt(numbers[i]);
            	}
				
				appendLog("Player1: " + string_askNumber + "\n");
				int[] calculateResult = calculateResult(askNumber, player[1].getArray());
				
				playerResultLabel[0].setText("calculating...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				String resultString = calculateResult[0] + " Strike " + calculateResult[1] + " Ball";
				if(calculateResult[0] == getDigit()) {
					playerResultLabel[0].setForeground(Color.RED);
					playerResultLabel[0].setText("You Win!\n");
					playerResultLabel[1].setForeground(Color.RED);
					playerResultLabel[1].setText("You Lose!\n");
					appendLog("Player1 Win!\n");
					return;
				}
				else if(calculateResult[0] == 0 && calculateResult[1] == 0) resultString = "OUT!";
				playerResultLabel[0].setText(resultString);
				appendLog(resultString + "\n");
				
				playerTextField[1].setEchoChar('\u0000');
				playerTextField[1].setForeground(Color.GRAY);
				playerTextField[1].setText("Deduce number.");
				playerTextField[0].setEditable(false);
				playerTextField[1].setEditable(true);
				
				synchronized(lock) {
		            try {
		                lock.wait(); // 버튼이 눌리기를 기다림
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
				
				string_askNumber = String.valueOf(playerTextField[1].getPassword());
				playerTextField[1].setText("");
				numbers = string_askNumber.split(" ");
				for(int i = 0; i < getDigit(); i++) {
            		askNumber[i] = Integer.parseInt(numbers[i]);
            	}
				
				appendLog("Player2: " + string_askNumber + "\n");
				calculateResult = calculateResult(askNumber, player[0].getArray());
				
				playerResultLabel[1].setText("calculating...");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				resultString = calculateResult[0] + " Strike " + calculateResult[1] + " Ball";
				if(calculateResult[0] == getDigit()) {
					playerResultLabel[0].setForeground(Color.RED);
					playerResultLabel[0].setText("You Lose!\n");
					playerResultLabel[1].setForeground(Color.RED);
					playerResultLabel[1].setText("You Win!\n");
					appendLog("Player2 Win!\n");
					return;
				}
				else if(calculateResult[0] == 0 && calculateResult[1] == 0) resultString = "OUT!";
				playerResultLabel[1].setText(resultString);
				appendLog(resultString + "\n");
			}
		}
    });
	
	public boolean isInteger(String input) {
	    return input.matches("-?\\d+");
	}
	
	public boolean checkValid(String[] numbers) {
		if(numbers.length != getDigit()) {
        	appendLog("You should enter " + getDigit() + " numbers.");
        	return false;
        }
		for(int i = 0; i < getDigit(); i++) {
			if(!isInteger(numbers[i])) {
				appendLog("You should enter " + getDigit() + " numbers.");
            	return false;
			}
        	if(Integer.parseInt(numbers[i])/10 > 0) {
        		appendLog("You should enter " + getDigit() + " numbers.");
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
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		turnLabel = new JLabel("Turn: 0");
		turnLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		turnLabel.setBounds(6, 6, 375, 16);
		panel.add(turnLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Player1");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel_1.setBounds(42, 50, 107, 30);
		panel.add(lblNewLabel_1);
		
		playerTextField = new JPasswordField[2];
		
		playerTextField[0] = new JPasswordField();
		playerTextField[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(String.valueOf(playerTextField[0].getPassword()).equals("Deduce number.") || String.valueOf(playerTextField[0].getPassword()).equals("ex: 0 1 2 3")) {
					playerTextField[0].setForeground(Color.BLACK);
					playerTextField[0].setText("");
					if(getTurn() == 0) playerTextField[0].setEchoChar('\u2022');
					else playerTextField[0].setEchoChar('\u0000');
				}
			}
		});
		playerTextField[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerTextField[0].setForeground(Color.BLACK);
				playerTextField[0].setText("");
				if(getTurn() == 0) playerTextField[0].setEchoChar('\u2022');
				else playerTextField[0].setEchoChar('\u0000');
			}
		});
		playerTextField[0].setEchoChar('\u0000'); // 표시하지 않음
		playerTextField[0].setText("ex: 0 1 2 3");
		playerTextField[0].setForeground(Color.GRAY);
		playerTextField[0].setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		playerTextField[0].setColumns(10);
		playerTextField[0].setBounds(161, 44, 216, 45);
		playerTextField[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = String.valueOf(playerTextField[0].getPassword());
                String[] numbers = input.split(" ");
                if(!checkValid(numbers)) return;
                
                synchronized(lock) {
                	lock.notify(); // 대기 중인 스레드 깨우기
                }
            }
        });
		panel.add(playerTextField[0]);
		
		playerTextField[1] = new JPasswordField();
		playerTextField[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(String.valueOf(playerTextField[1].getPassword()).equals("Deduce number.") || String.valueOf(playerTextField[1].getPassword()).equals("ex: 0 1 2 3")) {
					playerTextField[1].setForeground(Color.BLACK);
					playerTextField[1].setText("");
					if(getTurn() == 0) playerTextField[1].setEchoChar('\u2022');
					else playerTextField[1].setEchoChar('\u0000');
				}
			}
		});
		playerTextField[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				playerTextField[1].setForeground(Color.BLACK);
				playerTextField[1].setText("");
				if(getTurn() == 0) playerTextField[1].setEchoChar('\u2022');
				else playerTextField[1].setEchoChar('\u0000');
			}
		});
		playerTextField[1].setForeground(Color.GRAY);
		playerTextField[1].setEchoChar('\u0000'); // 표시하지 않음
		playerTextField[1].setText("ex: 0 1 2 3");
		playerTextField[1].setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		playerTextField[1].setColumns(10);
		playerTextField[1].setBounds(161, 173, 216, 45);
		playerTextField[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = String.valueOf(playerTextField[1].getPassword());
                String[] numbers = input.split(" ");
                if(!checkValid(numbers)) return;
                
                synchronized(lock) {
                	lock.notify(); // 대기 중인 스레드 깨우기
                }
            }
        });
		panel.add(playerTextField[1]);
		
		JLabel lblNewLabel_2 = new JLabel("Player2");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		lblNewLabel_2.setBounds(42, 180, 107, 30);
		panel.add(lblNewLabel_2);
		
		playerResultLabel = new JLabel[2];
		playerResultLabel[0] = new JLabel("");
		playerResultLabel[0].setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		playerResultLabel[0].setBounds(165, 94, 216, 45);
		panel.add(playerResultLabel[0]);
		
		playerResultLabel[1] = new JLabel("");
		playerResultLabel[1].setFont(new Font("Lucida Grande", Font.PLAIN, 24));
		playerResultLabel[1].setBounds(165, 223, 216, 45);
		panel.add(playerResultLabel[1]);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(42, 315, 388, 81);
		panel.add(panel_1);
		
		JButton btnNewButton = new JButton("New game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Multiplay newMultiplay = new Multiplay(game);
				newMultiplay.run();
				frame.setVisible(false);
				return;
			}
		});
		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		
		JButton btnNewButton_2 = new JButton("Help");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JOptionPane.showMessageDialog(null, getHelpMessage(), "숫자 야구 도움말", JOptionPane.INFORMATION_MESSAGE);
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
