package finalproject;

import java.util.Arrays;

import javax.swing.JTextArea;

public class Play extends Game {
	private int turn;
	protected JTextArea logTextArea;
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void appendLog(String log) {
		logTextArea.append(log + "\n");
	}
	
	public int[] calculateResult(int[] ask, int[] target) {
		int[] result = new int[2];
		// result[0] = # strikes
		// result[1] = # balls
		Arrays.fill(result, 0);
		
		for(int i = 0; i < getDigit(); i++) {
			if(ask[i] == target[i]) result[0]++;
			for(int j = 0; j < getDigit(); j++) {
				if(i == j) continue;
				if(ask[i] == target[j]) result[1]++;
			}
		}
		
		return result;
	}
	
	public void startPlay() {}
	
}
