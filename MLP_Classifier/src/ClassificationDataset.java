import java.util.Arrays;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;

public class ClassificationDataset {
	
	private Coordinate[] coordinates; 
	
	public ClassificationDataset() {		//dhmiourgia 8000 tyxaion coordinates sto diasthma [-1,1]
		coordinates = new Coordinate[8000];
		Random r = new Random();
		for (int i = 0; i < 8000; i++) {		
			float x1RandomValue = -1 + (1 + 1) * r.nextFloat();
			float x2RandomValue = -1 + (1 + 1) * r.nextFloat();
			Coordinate coor = new Coordinate(x1RandomValue, x2RandomValue);
			coordinates[i] = coor;
		}	
	}
	
	public void createSets() throws IOException {	//dhmiourgia ton arxeion mathisis kai elegxou me 4000 suntetagmenes to kathena
		PrintWriter learnStream = new PrintWriter(new java.io.FileWriter("LearningSet.txt"));
		PrintWriter testStream = new PrintWriter(new java.io.FileWriter("TestSet.txt"));
		for (int i = 0; i < 4000; i++) {			
			learnStream.println(coordinates[i].getX1() + "  " + coordinates[i].getX2() + "  " + Arrays.toString(coordinates[i].encodeCategory()) + " " + coordinates[i].getCategory()); 
		}
		learnStream.close();

		for (int i = 4000; i < 8000; i++) {
			testStream.println(coordinates[i].getX1() + "  " + coordinates[i].getX2() + "  " + Arrays.toString(coordinates[i].encodeCategory()) + " " + coordinates[i].getCategory());  
		}
		testStream.close();
	}
	
	public Coordinate[] getLearnCoordinates() {
		Coordinate[] learnCoordinates = new Coordinate[4000];
		for (int i = 0; i < learnCoordinates.length; i++) {
			learnCoordinates[i] = coordinates[i];
		}
		return learnCoordinates;
	}
	
	public Coordinate[] getTestCoordinates() {
		Coordinate[] testCoordinates = new Coordinate[4000];
		for (int i = 0; i < testCoordinates.length; i++) {
			testCoordinates[i] = coordinates[4000+i];
		}
		return testCoordinates;
	}
	
	public static void main(String args[]) throws IOException {
		ClassificationDataset dataset = new ClassificationDataset();
		dataset.createSets();
	}
	
}
