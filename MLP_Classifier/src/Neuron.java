import java.util.Random;

public class Neuron {

	private float value;
	private float bias; 
	private float delta;
	private float[] weights; 		//bari pou pernei apo tous neurones tou prohgoumenou epipedou
	private float[] errorGradients;	//tha krataei tis merikes paragogous ton baron kai polosis 
	
	public Neuron(int weightsLength) { //mikos baron = neurones sto prohgoumeno epipedo
		Random r = new Random();
		this.value = 0;			
		this.delta = 0;
		this.bias = -1 + (1 + 1) * r.nextFloat();
		weights = new float[weightsLength];
		errorGradients = new float[weightsLength+1]; //+1 gia to bias (thesi 0 bias kai epomenes ton baron)
		for (int i = 0; i < weightsLength; i++) {
			this.weights[i] = -1 + (1 + 1) * r.nextFloat();
		}
		
		/*
		for (int i = 0; i < weightsLength; i++) {	//gia na do ta tyxaia bari otan ta dhmiourgo
			System.out.println("weights[" + i + "]:" + weights[i]);
			System.out.println("ErrorGradients[" + i + "]:" + errorGradients[i]);
		}
		System.out.println("bias:" + bias);
		System.out.println("value:" + value);
		System.out.println("delta:" + delta);	
		*/
	}

	public float getValue() {
		return value;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	public float[] getWeights() {
		return weights;
	}
	
	public void setWeights(float[] weights) {
		this.weights = weights;
	}
	
	public float getDelta() {
		return delta;
	}
	
	public void setDelta(float delta) {
		this.delta = delta;
	}
	
	public float getBias() {
		return bias;
	}
	
	public void setBias(float bias) {
		this.bias = bias;
	}
	
	public float[] getErrorGradients() {
		return errorGradients;
	}
	
	public void setErrorGradients(float[] errorGradients) {
		for (int i = 0; i < this.errorGradients.length; i++) {
			this.errorGradients[i] += errorGradients[i];
		}
	}
	
	public void clearErrorGradients() {							
		for (int i = 0; i < this.errorGradients.length; i++) {
			this.errorGradients[i] = 0;
		}
	}
	
}
