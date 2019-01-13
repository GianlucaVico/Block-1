/** Created by Harry Forest, Nata Guseva */
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JLabel; 
import javax.swing.JButton; 
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

/**
 * Panel to start a new game 
 */
public class MainMenu extends JComponent{
    class StartAction implements ActionListener { 
        private ButtonGroup modes;        
        public StartAction(ButtonGroup modes) {
            this.modes = modes;
        }        
        public void actionPerformed(ActionEvent e) {    
            //graph.changeGraph(null);
            //set GraphComponent
            //set operation
            //make GameMode -> use solver from operation
            //start timer
            boolean found = false;
            int i = 0;
            Enumeration<AbstractButton> en = modes.getElements();
            while(!found && en.hasMoreElements()) {
                if(en.nextElement().isSelected()){
                    found = true;
                }else {
                    i++;
                }
            }
            mode = makeGameMode(i);
            graph.setGameMode(mode);
            graph.changeGraph(makeGraph());
            mode.start();
            graph.update();   
            graph.getOperationComponent().updateSolvers();
            graph.getOperationComponent().update();
            graph.getOperationComponent().resetBox();
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
    //private OperationComponent operation;
    private GameMode mode;     
    private JSpinner edges, nodes;
    private JTextField file;
    private JButton chooseFile;
    private PlayTimer timer;
    
    /**
     * Make a new MainMenu component
     * @param graph GraphComponent used in the game
     * @param operation Operation component used in the game
     * @param panel JPanel to add other components
     */
    public MainMenu(GraphComponent graph, OperationComponent operation, JPanel panel){                 
        this.graph = graph;        
        this.timer = new PlayTimer(false, 0);
        this.mode = makeGameMode(0);
        //this.operation = operation;
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
        
        edges = new JSpinner();    //make a spinnermodel 
        nodes = new JSpinner();
        file = new JTextField();    //change to a JFileChooser
        file.setEnabled(false);
        file.setEditable(false);
        chooseFile = new JButton("Choose");
        chooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());		
		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			file.setText(selectedFile.getAbsolutePath());
		
		}
            }
        });
        
        graphType.add(t1);        
        graphType.add(t2);               
        
        t1.addItemListener(new ChangeAction(t1, new JComponent[]{edges, nodes}));
        t2.addItemListener(new ChangeAction(t2, new JComponent[]{file}));
        
        graphPanel.add(new JLabel("Graph options"));
        graphPanel.add(t1);        
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        graphPanel.add(new JLabel("Edges"));
        graphPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        graphPanel.add(edges);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        graphPanel.add(new JLabel("Nodes"));
        graphPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        graphPanel.add(nodes);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        graphPanel.add(t2);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        graphPanel.add(file);
        graphPanel.add(chooseFile);
        graphPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                
        JButton start = new JButton("New Game");       
        start.setBounds(100, 50, 100, 50);
        start.addActionListener(new StartAction(modeGroup));
        
        panel.add(modePanel);
        panel.add(graphPanel);        
        panel.add(start);
        panel.add(timer);
    }      
    
    /**
     * Make a new GameMode
     * @param mode 0: BitterEndMode, 1: FixedTimeMode, 2: RandomOrderMode 
     * @return the GameMode
     */
    public GameMode makeGameMode(int mode) {
        GameMode m = null;       
        timer.stop();
        timer.reset(0);
        timer.setReversed(false, null);  
        switch(mode) {
            case 0:
                m = new BitterEndMode(graph);                
                break;
            case 1:
                m = new FixedTimeMode(graph);
                timer.setReversed(true, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        graph.update(); //so check gamemode ended
                    }
                });
                timer.reset(120);                
                break;
            case 2:
                m = new RandomOrderMode(graph);
                break;            
        }    
        if(m != null)
            m.setTimer(timer);
        return m;
    }
    
    /**
     * @param m use this GameMode
     */
    public void setGameMode(GameMode m){       
        this.mode = m;
    }
    
    /**
     * @return the GameMode used
     */
    public GameMode getGameMode(){
        return mode;
    }
    
    /**
     * @return make a new Graph
     */
    public Graph makeGraph() {
        Graph g;
        if(file.isEnabled()) {
            g = new Graph(file.getText().trim());
        }else {
            g = new Graph((Integer)nodes.getValue(), (Integer)edges.getValue());
        }
        return g;
    }
    
    /**
     * @return the PlayTimer used in the game
     */
    public PlayTimer getTimer() {
        return timer;
    }
    /*
    public static void main(String[] args) {  
        JFrame frame = new JFrame("MainMenu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,400);
        JPanel panel = new JPanel();
        panel.add(new MainMenu(null, null, panel));
        frame.add(panel);
        frame.setVisible(true);        
    }  */
}