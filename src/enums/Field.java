package enums;

public enum Field {
	x(0,23),
	y(0,24);
	
	int min;
	int max;
	
	Field(int min, int max){
		this.min = min;
		this.max = max;		
	}
	
	int getMin(){
		return min;
	}
	
	int getMax(){
		return max;
	}
	
	public boolean isValidField(int x,int y){
		if (x < Field.x.getMin() || Field.x.getMax() < x ||
			y < Field.y.getMin() || Field.y.getMax() < y) return false;
		return true;
	}
	
	public static boolean isValidX(int x){
		if (x < Field.x.getMin() || Field.x.getMax() < x) return false;
		return true;
	}
	public static boolean isValidY(int y){
		if (y < Field.y.getMin() || Field.y.getMax() < y) return false;
		return true;
	}
}
