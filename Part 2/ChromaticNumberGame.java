import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

//main frame
public class ChromaticNumberGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Game");       
        JPanel opPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        
        //make GraphComponent
        GraphComponent gc = new GraphComponent();
        
        //make OperationComponenet
        OperationComponent op = new OperationComponent(gc, opPanel);
        gc.setOperationComponent(op);
        opPanel.add(op);
        
        //make MainMenu
        MainMenu menu = new MainMenu(gc, op, menuPanel);
        menuPanel.add(menu);
                     
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);        
        
        //frame.setLayout(new GridBagLayout());  
        JPanel left = new JPanel();
        left.setLayout(new GridBagLayout());
        frame.setLayout(new GridLayout(1,3));
        GridBagConstraints constr = new GridBagConstraints();
        
        constr.gridx = 0;
        constr.gridy = 0;                 
        left.add(menuPanel, constr);
        
        constr.gridx = 1;        
        left.add(opPanel, constr);
        frame.add(left, 0,0);
        gc.setDrawSize(600, 400);        
        constr.gridx = 2;          
        //frame.add(gc, constr);     
        frame.add(gc, 0, 1);
        frame.setVisible(true);
    }
}
