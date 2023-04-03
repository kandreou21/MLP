//4316 Konstantinos Andreou
//4416 Ioannis Manos
//4463 Miltiadis Papatheodoropoulos
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MLP {
	
	//DEFINES
	private static final int d = 2; 		//arithmos eisodon
	private static final int K = 3;			//arithmos kathgorion(3 eksodoi opoia einai pio konta sto 1 se authn thn kathgoria anhkei)
	private static final int H1 = 10;		//arithmoi neuronon se kathe krummeno epipedo 5 
	private static final int H2 = 10;		
	private static final int H3 = 10;		
	private static final int FUNCTION = 1;  //logistic/sigmoid = 0, tanh = 1, relu = 2  gia ta krummena
	private static final int[] neuronsInLayers = {d, H1, H2, H3, K}; 
	private static final int B = 40;  		
	
	private ActivationFunction function = new ActivationFunction(FUNCTION);   //initialise activation function
	private ActivationFunction outputFunction = new ActivationFunction(0);   //initialise sigmoid activation function for output layer 
	private float learningRate;
	private float terminationThreshold; 	
	private static Layer[] layers = new Layer[5];				//thesi 0 neurones eisodou, thesi 1,2,3 hidden layer, thesi 4 output layer
	private static int epoch = 0;								
	private static ClassificationDataset set = new ClassificationDataset();
	private static Coordinate[] learnCoordinates = set.getLearnCoordinates();	//paradeigmata mathisis
	public static Coordinate[] testCoordinates = set.getTestCoordinates();		//paradeigmata elegxou
	private static float prevLearningError = 1;												
	private static float currLearningError;
	public static String[] printAtPanel = new String[4000];
 	
	public MLP(int flag) {
		
		if (flag == 1){													
			Scanner num = new Scanner(System.in).useLocale(Locale.US);	//useLocale(US) gia na dinetai input px 0.1 kai oxi 0,1
			
			System.out.print("Give the learning rate(e.g 0.01):");
			learningRate = num.nextFloat();
		
			System.out.print("Give the termination threshold(e.g 0.00001):");
			terminationThreshold = num.nextFloat();		
		}
		
		for (int i = 0; i < layers.length; i++) {
			layers[i] = new Layer(neuronsInLayers[i]);	//initialise neurons in each layer
			if (i == 0) {								
				layers[i].setNeurons(0); 				//proto layer oi neurones den exoun barh 
			}
			else {
				layers[i].setNeurons(layers[i-1].getLength());	//epomena layers exoun tosa barh oso kai to plithos ton neuronon sto prohgoumeno layer
			}
		}
	}
	
	public float[] forward_pass(float[] input) {											//ypologismos timis kathe neurona kai eksodou MLP gia eisodo thn parametro input
		float u;
		for (int i = 0; i < d; i++) {
			layers[0].getNeurons()[i].setValue(input[i]);									//thetoume times stous neurones tou input layer 	
			//System.out.println("input[" + i + "]:" + layers[0].getNeurons()[i].getValue());
		}
		
		for (int i = 1; i < layers.length; i++) {											//gia kathe layer(apo hidden kai meta)
			for (int j = 0; j < layers[i].getLength(); j++) {								//gia kathe neurona sto layer
				u = 0;																		//eisodos neurona
				for (int k = 0; k < layers[i-1].getLength(); k++)	{						//gia kathe neurona sto prohgoumeno layer
					u += layers[i-1].getNeurons()[k].getValue() * layers[i].getNeurons()[j].getWeights()[k]; //eksodos = value tou prohgoumenou neurona * weight tou epomenou 
				}	
				u += layers[i].getNeurons()[j].getBias();									//prosthesi ths poloshs
				if (i != 4) {
					layers[i].getNeurons()[j].setValue(function.activate(u));				//ypologismos eksodou neurona
				}
				else {
					layers[i].getNeurons()[j].setValue(outputFunction.activate(u));			//ypologismos eksodou neurona gia output layer(sigmoid function)
				}
			}
		}
		
		float[] output = new float[3];
		for (int i = 0; i < layers[4].getLength(); i++) {									//get MLP's output
			output[i] = layers[4].getNeurons()[i].getValue();
			//System.out.println("output[" + i + "]:" + output[i]);
		};
		return output;
	}
	
	public float backprop(float[] input, float[] output) {									//output = example's output (t1,t2,t3)
		float[] mlpOutput = forward_pass(input);											
		float delta = 0;
		float error = 0; 
			
		for (int i = 0; i < layers[4].getLength(); i++) {									//gia kathe neurona tou output layer
			delta = outputFunction.gradient(layers[4].getNeurons()[i].getValue()) * (mlpOutput[i] - output[i]);	//delta tou neurona = gradient(eksodou) + sfalma(o-t)
			layers[4].getNeurons()[i].setDelta(delta);
		}
		
		for (int i = 3; i > 0; i--) {														//gia kathe layer apo to teleutaio krummeno mexri to proto krummeno
			for (int j = 0; j < layers[i].getLength(); j++) {								//gia kathe neurona tou layer
				error = 0;
				for (int k = 0; k < layers[i+1].getLength(); k++) {							//gia kathe neurona tou epomenou layer
					error += layers[i+1].getNeurons()[k].getDelta() * layers[i+1].getNeurons()[k].getWeights()[j];	  
				}
				delta = error * function.gradient(layers[i].getNeurons()[j].getValue());	//ypologizei ta delta kathe neurona sta hidden layers
				layers[i].getNeurons()[j].setDelta(delta);											
			}
		}
		//ypologismos ton merikon paragogon tou sfalmatos os pros bari/poloseis
		for (int i = 4; i > 0; i--) {														//gia kathe layer(apo to output mexri to proto krummeno)
			for (int j = 0; j < layers[i].getLength(); j++) {								//gia kathe neurona tou layer(apo output mexri proto krummeno)
				float[] errorGradients = new float[layers[i-1].getLength()+1];				//errorGradients plithos = plithos neuronon sto prohgoumeno layer + 1 gia thn polosi to arxikopoio me ta weights
				errorGradients[0] = layers[i].getNeurons()[j].getDelta();   				//errorGradients[0] = delta 
				for (int k = 0; k < layers[i-1].getLength(); k++) {							//gia kathe neurona tou layer(apo teleutaio krummeno mexri to input layer)
					errorGradients[k+1] = layers[i].getNeurons()[j].getDelta() * layers[i-1].getNeurons()[k].getValue() ;	//dE/dwij = deltai * yj 
				}
				layers[i].getNeurons()[j].setErrorGradients(errorGradients);				//h setErrorGradients prosthetei tis kainouries paragogous sfalmatos
			}
		}
		//sfalma ekpaideusis (ypologizetai sto telos kathe epoxhs)
		error = 0;
		for (int i = 0; i < mlpOutput.length; i++) {
			error += (float) Math.pow(output[i] - mlpOutput[i], 2);
		}
		error = error/2;	//error = 1/2[Sum(ti-oi)^2)]
		return error;
	}
	
	public void updateWeights() {															//gradient descent
		for (int i = 1; i < layers.length; i++) {											//gia kathe layer ektos tou input opou den enhmeronontai ta bari
			for (int j = 0; j < layers[i].getLength(); j++) {								//gia kathe neurona tou layer
				float[] updatedWeights = new float[layers[i-1].getLength()];				//weights[] size = neurones sto prohgoumeno layer
				float updatedBias = layers[i].getNeurons()[j].getBias() - (learningRate * layers[i].getNeurons()[j].getErrorGradients()[0]);
				layers[i].getNeurons()[j].setBias(updatedBias);								//update bias
				for (int k = 1; k < layers[i].getNeurons()[j].getErrorGradients().length; k++) {
					updatedWeights[k-1] = layers[i].getNeurons()[j].getWeights()[k-1] - (learningRate * layers[i].getNeurons()[j].getErrorGradients()[k]);																
				}
				layers[i].getNeurons()[j].setWeights(updatedWeights);						//update weights
			}
		}
		resetErrorGradients();
	}
	
	public boolean checkTermination(float prevEpochError, float currEpochError) {
		if (Math.abs(prevEpochError - currEpochError) < terminationThreshold) {
			return true;
		}
		return false;
	}
	
	public static void main(String args[]) throws IOException {
		MLP mlp = new MLP(1);
		
		while (true) {
			float errorSum = 0;																		//its used to count error in each epoch
			for (int i = 0; i < learnCoordinates.length; i+=B) {
				for (int j = i; j < i+B; j++) {
					float[] input = {learnCoordinates[j].getX1(), learnCoordinates[j].getX2()};		//input
					float[] output = learnCoordinates[j].encodeCategory();							//expected category output
					float error = mlp.backprop(input, output);	
					errorSum += error;
				}
				mlp.updateWeights(); 
			}
			System.out.println("epoch: " + epoch);
			currLearningError = errorSum / learnCoordinates.length;	
			System.out.println("Learning Error: " + currLearningError);	
			epoch++;
			if (epoch > 700) {
				if (mlp.checkTermination(prevLearningError, currLearningError) == true) {
					mlp.saveWeights();
					System.out.println("Training stopped at epoch: " + (epoch-1));
					//check regularization using test set
					System.out.println("Generalization ability(Percentage of correct answers at test set): " + mlp.computeGeneralizationAbility() + "%");
					new MyFrame();
					break;
				}
				prevLearningError = currLearningError; 
			}
		}
	}
	
	//meta thn ektelesh ths ekpaideushs meso ths apo pano main mporeite na treksete mono thn parakato main 
	//h opoia fortonei ta vari pou apothikeutikan sth mathisi kai ypologizei thn genikeutiki ikanothta   
	/*
	public static void main(String args[]) throws IOException {
		MLP mlp = new MLP(0);
		
		mlp.loadWeights();
		System.out.println("Generalization ability(Percentage of correct answers at test set): " + mlp.computeGeneralizationAbility() + "%");
		new MyFrame();
	}
	*/
	public float computeGeneralizationAbility() {
		float hits = 0;
		Coordinate coor = new Coordinate();
		
		for (int i = 0; i < testCoordinates.length; i++) {
			float[] input = {testCoordinates[i].getX1(), testCoordinates[i].getX2()};
			float[] output = forward_pass(input);
			if (testCoordinates[i].getCategory().equals(coor.decodeCategory(output))) {
				hits++;
				printAtPanel[i] = "+";
			}
			else {
				printAtPanel[i] = "-";
			}
		}
		return hits * 100 / testCoordinates.length;		//percentage
	}
	
	public void resetErrorGradients() {
		for (int i = 1; i < layers.length; i++) {											//gia kathe layer ektos tou input opou den enhmeronontai ta bari
			for (int j = 0; j < layers[i].getLength(); j++) {								//gia kathe neurona tou layer					
				layers[i].getNeurons()[j].clearErrorGradients();
			}
		}
	}
	
	public void saveWeights() throws IOException {
		PrintWriter weightsStream = new PrintWriter(new java.io.FileWriter("Weights.txt"));
		for (int i = 1; i < layers.length; i++) {													//gia kathe layer											
			for (int j = 0; j < layers[i].getLength(); j++) {										//gia kathe neurona tou layer		
				weightsStream.print("Layer = " + i + " Neuron = " + j + " : " + layers[i].getNeurons()[j].getBias() + " ");	//printare to bias tou neuona
				for (int k = 0; k < layers[i].getNeurons()[j].getWeights().length; k++) {			//gia kathe weight tou neurona
					weightsStream.print(layers[i].getNeurons()[j].getWeights()[k] + " ");			//printare to
					
				}
				weightsStream.println();
			}
		}
		weightsStream.close();
	}
	
	public void loadWeights() throws IOException {										//load Weights from Weights.txt to use on test set 
		File file = new File("Weights.txt");
		Scanner scan = new Scanner(file);
		
		for (int i = 1; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getLength(); j++) {
				if (scan.hasNextLine()) {
					String line = scan.nextLine();
					line = line.split(": ")[1];
					String[] weights = line.split(" ");
					//System.out.println("weights: " + Arrays.toString(weights));
					String bias = weights[0].split(" ")[0];
					//System.out.println("bias: " + bias);
					layers[i].getNeurons()[j].setBias(Float.parseFloat(bias));
					float[] weightsToStore = new float[layers[i].getNeurons()[j].getWeights().length];
					for (int k = 1; k < weights.length; k++) {
						weightsToStore[k-1] = Float.parseFloat(weights[k]);
					}
					//System.out.println(Arrays.toString(weightsToStore));
					layers[i].getNeurons()[j].setWeights(weightsToStore);
				}
			}
		}
	}
	 
}
