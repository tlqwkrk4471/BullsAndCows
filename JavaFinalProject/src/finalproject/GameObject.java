package finalproject;

public class GameObject {
	
	private int[] number;
	
	public GameObject() {}
	public GameObject(int digit) {
		number = new int[digit];
	}
	
	public void setNumber(int idx, int num) {
		this.number[idx] = num;
	}
	
	public int getNumber(int idx) {
		return number[idx];
	}
	
	public int[] getArray( ) {
		return number;
	}
	
}
