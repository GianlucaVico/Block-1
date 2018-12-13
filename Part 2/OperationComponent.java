import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.*;

/**
 * Panel for the action that can be performed during the game
 * - Change the color of a node
 * - Get some hints
 */
public class OperationComponent extends JComponent{
    class Item {
        public int count;
        public Color color; 
        public String name;
        public Item(String name, Color c) {	
            count = 0;
            this.name = name;
            this.color = c;			
        }

        public String toString() {			
            return "Color: " + color.getRed() + " " + color.getGreen() + " " + color.getBlue();
        }		
    }

    class Hints implements ActionListener {
        private JLabel label;
        private String textBefore;
        private String textAfter;
        private Solver solver;

        public Hints(JLabel out, String textBefore, String textAfter, Solver solver){
            this.label = out;
            this.textBefore = textBefore;
            this.textAfter = textAfter;
            this.solver = solver;
        }

        public void actionPerformed(ActionEvent event) {
            int hint = solver.solve();
            if(hint != -1)
                label.setText(textBefore + " " + solver.solve() + " " + textAfter);
            else
                label.setText("I can't help you :'(");
        }
    }

    class UpdateColor implements ActionListener {
        public void actionPerformed(ActionEvent event) {            
            if(!menu.getTimer().isRunning()) {
                menu.getTimer().start();
                menu.getGameMode().start();
            }            
            setColor();
            graphComponent.update();    //this check also if the game is ended
        }
    }

    class ItemRenderer extends JLabel implements ListCellRenderer {
        public ItemRenderer() {		
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);			
        }
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            //if(graphComponent.getSelectedNode() != null){
                Item i = (Item)value;
                setBackground(i.color);
                if(value == box.getSelectedItem()){				
                    setText("Color " + box.getSelectedIndex());				
                    box.setBackground(i.color);
                }else if(index == 0){
                    this.setForeground(Color.WHITE);
                    setText("- - -");
                    //this.setForeground(Color.BLACK);
                }else {
                    setText("Color " + index);
                }			            
            return this;
        }
    }

    private GraphComponent graphComponent;
    private MainMenu menu;
    private JComboBox<Item> box;
    private JPanel panel;
    private JLabel selectedName;
    private JLabel hint;    

    private JButton low, up, ch, bestNode, bestColor;   //to make graph changeable
    //private Solver lowS, upS, chS, bnS, bcS;   //to make graph changeable
    
    /**
     * Make a new OperationComponent
     * @param graphComponent GraphComponent of the game
     * @param panel panel used to add other components
     */
    public OperationComponent(GraphComponent graphComponent, JPanel panel) {
        this.panel = panel;
        this.graphComponent = graphComponent;
        //colors = new LinkedList<Item>();        

        selectedName = new JLabel("- - -");
        hint = new JLabel();
        ItemRenderer renderer = new ItemRenderer();
        box = new JComboBox<Item>();
        box.setRenderer(renderer);	

        box.addItem(new Item("No color", Color.BLACK));	//test
        box.addItem(new Item("Test", Color.RED));	//test
        box.addItem(new Item("Test", Color.BLUE));	//test
        
        JButton set = new JButton("Set Color");
        set.addActionListener(new UpdateColor());
        
        low = new JButton("Lower bound");		
        up = new JButton("Upper bound");		
        ch = new JButton("Chromatic number");           
        bestNode = new JButton("Easiest node"); 	
        bestColor = new JButton("Color hint");          
        
        //add listener
            //button listener -> display hints of label
        //make solvers (some solvers need other solver to work)
        updateSolvers();
        //combo -> update counter/operation panel 
        //box.addActionListener(new UpdateColor());
        
        //add to panel
        this.panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = .5;        
        JComponent[] toAdd = new JComponent[]{selectedName, box, set, new JLabel("Hints"), low, up, ch, bestNode, bestColor, hint};
        for(int i = 0; i < toAdd.length; i++) {
            c.gridy = i;
            this.panel.add(toAdd[i], c);
        }
    }

    /**
     * @return the next color to add to the list
     */
    private Color makeNextColor() {
        Color lastColor = box.getItemAt(box.getItemCount() - 1).color;
        float[] hsb = Color.RGBtoHSB(lastColor.getRed(), lastColor.getGreen(), lastColor.getBlue(), null);        
        //every turn reduce saturation
        hsb[0] +=  22F/360F;
        hsb[1] -= (float)Math.ceil(hsb[0])/10;
        if(hsb[0] > 1)
            hsb[0]--;
        
        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }

    /**
     * Change the color of the selected node
     */
    public void setColor() {
        Node selected = graphComponent.getSelectedNode();        
        if(selected != null){// && !(selected.getColor() != - 1 && menu.getGameMode().canChangeColor())) {
            if(selected.getColor() == - 1 || menu.getGameMode().canChangeColor()){
                if(selected.getColor() != - 1) {                
                    box.getItemAt(selected.getColor() + 1).count--; //colors from -1
                }
                int tmp = selected.getColor(); //if there are errors
                selected.setColor(box.getSelectedIndex() -1);

                if(!menu.getGameMode().errorAllowed() && graphComponent.errors().size() != 0){  //if error come back
                    selected.setColor(tmp);

                }else{
                    ((Item)box.getSelectedItem()).count++;              //if no errors update counter
                }
            }
        }
        updateBox();          
    }
        
    /**
     * Update the information displayed
     */
    public void update() {
        Node selected = graphComponent.getSelectedNode();
        if(selected != null) {                                 
            selectedName.setText("Node " + selected.getId());
        }else {
            selectedName.setText("- - -");
        }
        updateBox();
        //check winning condition
    }
    
    /**
     * Update color list
     */
    private void updateBox() {
        int length = box.getItemCount();
        if(box.getItemAt(length - 1).count != 0) {
            box.addItem(new Item("Make name", makeNextColor()));
        }else if (length >= 3 && box.getItemAt(length -2).count == 0) {
            box.removeItemAt(length -1);
        }
    }
    
    /**
     * Reset color list
     */
    public void resetBox() {
        int length = box.getItemCount();
        while(length > 2) {
            box.removeItemAt(length -1);
            length = box.getItemCount();
        }
    }
    
    /**
     * set the new solver from the GraphComponent
     */
    public void updateSolvers() {        
        Solver[] s = this.graphComponent.getSolvers();        
        resetHintButton(low);
        resetHintButton(up);
        resetHintButton(ch);
        resetHintButton(bestNode);
        resetHintButton(bestColor);
        
        low.addActionListener(new Hints(hint, "You need at least", "colors", s[GraphComponent.LOWER_BOUND]));
        up.addActionListener(new Hints(hint, "You need at most", "colors", s[GraphComponent.UPPER_BOUND]));
        ch.addActionListener(new Hints(hint, "You need", "colors", s[GraphComponent.EXACT]));
        bestNode.addActionListener(new Hints(hint, "You should try to solve Node", "", s[GraphComponent.BEST_NODE]));
        bestColor.addActionListener(new Hints(hint, "For this node I suggest Color", "", s[GraphComponent.BEST_COLOR]));
    }    
    
    /**
     * Remove old listener
     * @param c JButton to reset
     */
    private void resetHintButton(JButton c) {       
        for(ActionListener l: c.getActionListeners()) {
            c.removeActionListener(l);
        }
    }
    
    /**
     * @param menu MainMenu of the game
     */
    public void setMainMenu(MainMenu menu) {
        this.menu = menu;
    }
    
    /**
     * Get colors by index
     * @param i index
     * @return color of index i
     */
    public Color getColor(int i) {
        Color c = Color.BLACK;
        if (i < box.getItemCount()-1 && i >= 0) {
            c = ((Item)box.getItemAt(i+1)).color;
        }
        return c;
    }
    
    /**
     * @return number of color that can be used
     */
    public int countAvailableColors() {
        return box.getItemCount() - 2;  //first and last not used
    }
    
    /**
     * @return number of color used
     */
    public int countUsedColors() {
        int i = 1;
        int count = 0;
        while(i < box.getItemCount()) {
            if(box.getItemAt(i).count > 0)
                count++;
            i++;
        }
        return count;
    }
    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("OperationComponent");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(150, 500);		
        JPanel panel = new JPanel();
        OperationComponent oc = new OperationComponent(new GraphComponent(), panel);	
        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setSize(50, 500);
        panel.add(oc);

        frame.add(panel);
        frame.setVisible(true);
    }*/
}