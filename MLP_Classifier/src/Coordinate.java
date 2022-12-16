public class Coordinate {
	
	private float x1;
	private float x2;
	private String category;
	
	public Coordinate(float x1, float x2) {
		this.x1 = x1;
		this.x2 = x2;
		if (((Math.pow(x1 - 0.5, 2)) + (Math.pow(x2 - 0.5, 2))) < 0.2 && x2 > 0.5) {		//1
			category = "C1";
		}
		else if (((Math.pow(x1 - 0.5, 2)) + (Math.pow(x2 - 0.5, 2))) < 0.2 && x2 < 0.5) {	//2
			category = "C1";
		}
		else if (((Math.pow(x1 + 0.5, 2)) + (Math.pow(x2 + 0.5, 2))) < 0.2 && x2 > -0.5) {	//3
			category = "C1";
		}
		else if (((Math.pow(x1 + 0.5, 2)) + (Math.pow(x2 + 0.5, 2))) < 0.2 && x2 < -0.5) {	//4
			category = "C2";
		}
		else if (((Math.pow(x1 - 0.5, 2)) + (Math.pow(x2 + 0.5, 2))) < 0.2 && x2 > -0.5) {	//5
			category = "C1";
		}
		else if (((Math.pow(x1 - 0.5, 2)) + (Math.pow(x2 + 0.5, 2))) < 0.2 && x2 < -0.5) {	//6
			category = "C2";
		}
		else if (((Math.pow(x1 + 0.5, 2)) + (Math.pow(x2 - 0.5, 2))) < 0.2 && x2 > 0.5) {	//7
			category = "C1";
		}	
		else if (((Math.pow(x1 + 0.5, 2)) + (Math.pow(x2 - 0.5, 2))) < 0.2 && x2 < 0.5) {	//8
			category = "C2";
		}	
		else {																				//9
			category = "C3";
		}
	}
	
	public float getX1() {
		return x1;
	}
	
	public float getX2() {
		return x2;
	}

	public String getCategory() {
		return category;
	}
}
