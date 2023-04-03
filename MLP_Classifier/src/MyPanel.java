import java.awt.*;

import javax.swing.JPanel;

public class MyPanel extends JPanel {

	public MyPanel() {
		this.setPreferredSize(new Dimension(1000, 1000));
	}
	
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		
		for (int i = 0; i < MLP.testCoordinates.length; i++) {
		
			if(MLP.testCoordinates[i].getCategory().equals("C1")) { 
				g2D.setPaint(Color.GREEN);
			}
			else if(MLP.testCoordinates[i].getCategory().equals("C2")) { 
				g2D.setPaint(Color.MAGENTA);
			}
			else{
				g2D.setPaint(Color.RED);
			}
			g2D.drawString(MLP.printAtPanel[i], 500*(MLP.testCoordinates[i].getX1()+1), 500*(MLP.testCoordinates[i].getX2()+1));
		}
	}
}
