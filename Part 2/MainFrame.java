import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class MainFrame {
    public MainFrame() {
        JFrame graphFrame = new JFrame();	//disply graph
        JFrame hintsFrame = new JFrame();	//hints buttons
        JFrame inputFrame = new JFrame();	//set color
        JFrame gameFrame  = new JFrame();	//game settings, timer 
	
    	graphFrame.setTitle("Graph");
    	hintsFrame.setTitle("Hints");
    	inputFrame.setTitle("Play");
    	gameFrame.setTitle("Settings");
    	
        graphFrame.setSize(500, 500);
        hintsFrame.setSize(200, 100);
        inputFrame.setSize(200,100);
        gameFrame.setSize(100, 400);
        
        graphFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        hintsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inputFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JPanel pan = new JPanel();
        JButton up = new JButton("Upper Bound");
        JButton low = new JButton("Lower Bound");
        JButton ch = new JButton("Solution");
        pan.add(up);
        pan.add(low);
        pan.add(ch);
        hintsFrame.add(pan);
        graphFrame.add(new circle());
    	graphFrame.setVisible(true);
        hintsFrame.setVisible(true);
        inputFrame.setVisible(true);
        gameFrame.setVisible(true);
        //JColorChooser.showDialog(graphFrame,"Choose",Color.CYAN);
    }
	
    public static void main(String[] args) {
        new MainFrame();
    }
}

class circle extends JComponent{
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D.Double e = new Ellipse2D.Double(20, 20, 10, 10);
		g2.draw(e);
	}
	
}