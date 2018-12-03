import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.Box;
import javax.swing.JLabel; 
import javax.swing.JButton; 
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.BoxLayout;
import javax.swing.JTextField;

public class MainMenu extends JComponent{
    class StartAction implements ActionListener { 
        //TODO
        public void actionPerformed(ActionEvent e) {    
            graph.changeGraph(null);
            //set GraphComponent
            //set operation
            //make GameMode -> use solver from operation
            //start timer
        }
    }
    
    class ChangeAction implements ItemListener {
        JComponent[] components;
        JRadioButton owner;
        public ChangeAction(JRadioButton owner, JComponent[] components) {
            this.components = components;
            this.owner = owner;
        }   
        
        public void itemStateChanged(ItemEvent e){                      
            for(JComponent c : components) {
                c.setEnabled(owner.isSelected());
            }
        }
    }
    
    private GraphComponent graph;
    private OperationComponent operation;
    private GameMode mode;     
     
    public MainMenu(GraphComponent graph, OperationComponent operation, JPanel panel){ 
        this.graph = graph;
        this.operation = operation;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        ButtonGroup modeGroup = new ButtonGroup();
        ButtonGroup graphType = new ButtonGroup();
        
        // Game mode option
        JPanel modePanel = new JPanel();
        modePanel.setLayout(new BoxLayout(modePanel, BoxLayout.Y_AXIS));
        
        JRadioButton b1 = new JRadioButton("The bitter end");
        JRadioButton b2 = new JRadioButton("Best upper bound in a fixed time frame");
        JRadioButton b3 = new JRadioButton("Random order");
        b1.setSelected(true);
        
        b1.setBounds(50,50,200, 75);         
        b2.setBounds(50, 150, 200, 75);        
        b3.setBounds(50, 250, 200, 75);
        
        modeGroup.add(b1);
        modeGroup.add(b2);
        modeGroup.add(b3);
        
        modePanel.add(new JLabel("Game modes"));
        modePanel.add(b1);
        modePanel.add(b2);
        modePanel.add(b3);
        modePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Graph option
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        
        JRadioButton t1 = new JRadioButton("Custom graph"); //2 spinner
        JRadioButton t2 = new JRadioButton("From file");    //a textfild
        t1.setSelected(true);
        
        JSpinner edges = new JSpinner();    //make a spinnermodel 
        JSpinner nodes = new JSpinner();
        JTextField graphFile = new JTextField();    //change to a JFileChooser
        graphFile.setEnabled(false);
        
        graphType.add(t1);        
        graphType.add(t2);               
        
        t1.addItemListener(new ChangeAction(t1, new JComponent[]{edges, nodes}));
        t2.addItemListener(new ChangeAction(t2, new JComponent[]{graphFile}));
        
        graphPanel.add(new JLabel("Graph options"));
        graphPanel.add(t1);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        graphPanel.add(edges);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        graphPanel.add(nodes);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        graphPanel.add(t2);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        graphPanel.add(graphFile);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
        JButton start = new JButton("New Game");       
        start.setBounds(100, 50, 100, 50);
        start.addActionListener(new StartAction());
        
        panel.add(modePanel);
        panel.add(graphPanel);        
        panel.add(start);
    }      
    
    public GameMode makeGameMode(int mode) {
        GameMode m = null;
        switch(mode) {
            case 0:
                m = new BitterEndMode(graph);
                //TODO set solver
                break;
            case 1:
                m = new FixedTimeMode(graph);
                break;
            case 2:
                m = new RandomOrderMode(graph);
                break;
            default:
                //TODO throw exception
                break;
        }
        return m;
    }
    
    public void setGameMode(GameMode m){
        this.mode = m;
    }
    
    public Graph makeGraph(JRadioButton isFromFile, JTextField file, JSpinner edges, JSpinner nodes) {
        Graph g;
        if(isFromFile.isSelected()) {
            g = new Graph(file.getText().trim());
        }else {
            g = new Graph((Integer)nodes.getValue(), (Integer)edges.getValue());
        }
        return g;
    }
    
    public static void main(String[] args) {  
        JFrame frame = new JFrame("MainMenu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,400);
        JPanel panel = new JPanel();
        panel.add(new MainMenu(null, null, panel));
        frame.add(panel);
        frame.setVisible(true);
        
    }  
}