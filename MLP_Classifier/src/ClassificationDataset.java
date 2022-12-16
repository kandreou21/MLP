import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;

public class ClassificationDataset {
	
	private ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>(); 
	
	public void generateCoordinates() {		//dhmiourgia 8000 tyxaion coordinates sto diasthma [-1,1]
		Random r = new Random();
		for (int i = 0; i < 8000; i++) {		
			float x1RandomValue = -1 + (1 + 1) * r.nextFloat();
			float x2RandomValue = -1 + (1 + 1) * r.nextFloat();
			Coordinate coor = new Coordinate(x1RandomValue, x2RandomValue);
			coordinates.add(coor);
		}	
	}
	
	public void createSets() throws IOException {	//dhmiourgia ton arxeion mathisis kai elegxou me 4000 suntetagmenes to kathena
		PrintWriter learnStream = new PrintWriter(new java.io.FileWriter("LearningSet.txt"));
		PrintWriter testStream = new PrintWriter(new java.io.FileWriter("TestSet.txt"));
		for (int i = 0; i < 4000; i++) {			
			learnStream.println(coordinates.get(i).getX1() + "  " + coordinates.get(i).getX2() + "  " + coordinates.get(i).getCategory()); 
		}
		learnStream.close();
		for (int i = 4000; i < 8000; i++) {
			testStream.println(coordinates.get(i).getX1() + "  " + coordinates.get(i).getX2() + "  " + coordinates.get(i).getCategory());  
		}
		testStream.close();
	}
	
	
	public static void main(String args[]) throws IOException {
		ClassificationDataset dataset = new ClassificationDataset();
		dataset.generateCoordinates();
		dataset.createSets();
	}
	
}
