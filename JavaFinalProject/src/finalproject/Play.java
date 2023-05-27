package finalproject;

import java.util.Arrays;

import javax.swing.JTextArea;

public class Play extends Game {
	private int turn;
	protected JTextArea logTextArea;
	private String helpMessage = "<html><b>숫자 야구 도움말</b><br><br>"
            + "숫자 야구는 상대방의 비밀 숫자를 맞추는 게임입니다.<br>"
            + "게임은 다음과 같은 단계로 진행됩니다:<br><br>"
            + "1. 게임 시작 시 비밀 숫자를 생성합니다.<br>"
            + "2. 상대방이 추측한 숫자를 입력 받습니다.<br>"
            + "3. 비밀 숫자와 상대방의 추측 숫자를 비교하여 결과를 피드백합니다.<br>"
            + "4. 상대방이 비밀 숫자를 맞출 때까지 2~3단계를 반복합니다.<br><br>"
            + "게임의 결과 피드백은 다음과 같이 이루어집니다:<br><br>"
            + "1. 숫자와 위치가 모두 일치하는 경우 '스트라이크'입니다.<br>"
            + "2. 숫자는 일치하지만 위치가 다른 경우 '볼'입니다.<br>"
            + "3. 숫자와 위치 모두 일치하지 않는 경우 '아웃'입니다.<br><br>"
            + "상대방이 비밀 숫자를 맞추면 게임이 종료됩니다.<br>"
            + "숫자 야구를 즐겁게 플레이하세요!</html>";
	
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public String getHelpMessage() {
		return helpMessage;
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
