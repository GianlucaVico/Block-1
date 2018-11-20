import javax.swing.*;
import java.awt.*;

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
        
        JButton up = new JButton("Upper Bound");
        JButton low = new JButton("Lower Bound");
        JButton ch = new JButton("Solution");
        hintsFrame.add(up);
        hintsFrame.add(low);
        hintsFrame.add(ch);
        
        
    	graphFrame.setVisible(true);
        hintsFrame.setVisible(true);
        inputFrame.setVisible(true);
        gameFrame.setVisible(true);
    }
	
    public static void main(String[] args) {
        new MainFrame();
    }
}