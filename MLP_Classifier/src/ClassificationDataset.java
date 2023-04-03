import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class ClassificationDataset {
	
	private Coordinate[] coordinates = new Coordinate[8000]; 
	
	public void initialiseDataset() {		//dhmiourgia 8000 tyxaion coordinates sto diasthma [-1,1]
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
			learnStream.println(coordinates[i].getX1() + "  " + coordinates[i].getX2() + "  " + coordinates[i].getCategory() + "  " + Arrays.toString(coordinates[i].encodeCategory())); 
		}
		learnStream.close();

		for (int i = 4000; i < 8000; i++) { 
			testStream.println(coordinates[i].getX1() + "  " + coordinates[i].getX2() + "  " + coordinates[i].getCategory() + "  " + Arrays.toString(coordinates[i].encodeCategory()));  
		}
		testStream.close();
	}
	
	public Coordinate[] getLearnCoordinates() {
		Coordinate[] learnCoordinates = new Coordinate[4000];
		File file = new File("LearningSet.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		for (int i = 0; i < learnCoordinates.length; i++) {
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] fields = line.split("  ");
				Coordinate coor = new Coordinate(Float.parseFloat(fields[0]), Float.parseFloat(fields[1]));
				learnCoordinates[i] = coor;
			}
		}
		return learnCoordinates;
	}
	
	public Coordinate[] getTestCoordinates() {
		Coordinate[] testCoordinates = new Coordinate[4000];
		File file = new File("TestSet.txt");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		for (int i = 0; i < testCoordinates.length; i++) {
			if (scan.hasNextLine()) {
				String line = scan.nextLine();
				String[] fields = line.split("  ");
				Coordinate coor = new Coordinate(Float.parseFloat(fields[0]), Float.parseFloat(fields[1]));
				testCoordinates[i] = coor;
			}
		}
		return testCoordinates;
	}
	
	
	//kaleitai mia fora gia thn dhmiourgeia ton datasets
	public static void main(String args[]) throws IOException {
		ClassificationDataset dataset = new ClassificationDataset();
		dataset.initialiseDataset();
		dataset.createSets();
		dataset.getLearnCoordinates();
		dataset.getTestCoordinates();
	}
	
}
