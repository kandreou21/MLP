public class Layer {

	private Neuron[] neurons;
	private int length;
	
	public Layer(int length) {
		this.length = length;
		this.neurons = new Neuron[length];
	}
	
	public void setNeurons(int neuronsInPrevLayer) {
		for (int i = 0; i < length; i++) {
			neurons[i] = new Neuron(neuronsInPrevLayer);
		}
	}
	
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	public int getLength() {
		return length;
	}
	
}
