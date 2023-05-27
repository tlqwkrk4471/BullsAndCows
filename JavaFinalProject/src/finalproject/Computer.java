package finalproject;

import java.util.Arrays;
import java.util.Random;

public class Computer extends GameObject {
	
	private Boolean[] candidates;
	
	public Computer() {}
	public Computer(int digit) {
		super(digit);
		candidates = new Boolean[(int)Math.pow(10, digit)];
		Arrays.fill(candidates, true);
		
		Random random = new Random();
		Boolean overlap = false;
		for(int i = 0; i < digit; i++) {
			int randomNum = random.nextInt(10);
			for(int j = 0; j < i; j++) {
				if(randomNum == getNumber(j)) overlap = true;
			}
			if(overlap) {
				overlap = false;
				i--;
				continue;
			}
			else setNumber(i, randomNum);
		}
	}
	
	public int[] askNumber(int digit, int turn) {
		int[] askNumber = new int[digit];
		
		if(turn == 1) {
			Random random = new Random();
			Boolean overlap = false;
			for(int i = 0; i < digit; i++) {
				int randomNum = random.nextInt(10);
				for(int j = 0; j < i; j++) {
					if(randomNum == askNumber[j]) overlap = true;
				}
				if(overlap) {
					overlap = false;
					i--;
					continue;
				}
				else askNumber[i] = randomNum;
			}
		}
		else {
			int num = 0;
			while(true) {
				if(!candidates[num]) {
					num++;
					continue;
				}
				
				// candidates에 존재하면 중복된 숫자가 있는지 확인
				int clone = num;
				for(int i = digit; i > 0; i--) {
					askNumber[digit-i] = clone / (int)Math.pow(10, i-1);
					clone -= askNumber[digit-i] * (int)Math.pow(10, i-1);
				}
				
				Boolean overlap = false;
				for(int i = 0; i < digit; i++) {
					for(int j = 0; j < i; j++) {
						if(askNumber[i] == askNumber[j]) overlap = true;
					}
				}
				if(overlap) num++;
				else break;
			}
		}
		return askNumber;
	}
	
	public int[] calculateResult(int digit, int[] ask, int[] target) {
		int[] result = new int[2];
		// result[0] = # strikes
		// result[1] = # balls
		Arrays.fill(result, 0);
		
		for(int i = 0; i < digit; i++) {
			if(ask[i] == target[i]) result[0]++;
			for(int j = 0; j < digit; j++) {
				if(i == j) continue;
				if(ask[i] == target[j]) result[1]++;
			}
		}
		
		return result;
	}
	
	public void calculateCandidates(int digit, int[] askNumber, int[] calculateResult) {
		for(int i = 0; i < (int)Math.pow(10, digit); i++)  {
			int[] targetNumber = new int[digit];
			int clone = i;
			for(int j = digit; j > 0; j--) {
				targetNumber[digit-j] = clone / (int)Math.pow(10, j-1);
				clone -= targetNumber[digit-j] * (int)Math.pow(10, j-1);
			}
			int[] candidateResult = calculateResult(digit, askNumber, targetNumber);
			if(!(candidateResult[0] == calculateResult[0] && candidateResult[1] == calculateResult[1])) {
				candidates[i] = false;
			}
		}
	}
	
}
