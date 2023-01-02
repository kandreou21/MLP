public class ActivationFunction{
	
	private final int type;
	
	public ActivationFunction(int type) {
		this.type = type;
		if (this.type > 2 || this.type < 0) {
			System.out.println("You gave wrong type of activation function - Exiting...");
			System.exit(0);
		} 
	}
	
	public float activate(float value) {
		if (type == 0) { 					// logistic/sigmoid
			return (float) (1 / (1 + (Math.exp(-value))));
		} 
		else if (type == 1) { 				//tanh
			return (float) Math.tanh(value);
		} 
		else { 								//relu
			return (float) Math.max(0, value);
		}
	}
	
	public float gradient(float value) {	//pairnei san parametro thn timh pou ypologistike apo thn activate() 	
		if (type == 0) {					//logistic/sigmoid
			return  (float) (value - Math.pow(value, 2));
		}
		else if (type == 1) {  				//tanh
			return (float) (1 - Math.pow(value, 2));
		} 
		else {								//relu
			if (value == 0) {
				return 0;
			}
			else {
				return 1;
			}
		}
	}
}