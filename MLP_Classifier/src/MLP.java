import java.io.IOException;

public class MLP {
	
	//DEFINES
	private static final int d = 4;
	private static final int K = 3;
	private static final int H1 = 3;
	private static final int H2 = 3;
	private static final int H3 = 3;
	private static final int FUNCTION = 0;  //logistic = 0, tanh = 1, relu = 2 
	private static final String[] ACTIVATION_FUNCTIONS = {"logistic", "tanh", "relu"};
	
	public void initialiseDataset() throws IOException {
		ClassificationDataset dataset = new ClassificationDataset();
		dataset.generateCoordinates();
		dataset.createSets();
	}
	
	
}
