/**Created by Gianluca Vico */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

//main frame
/**
 * Graph coloring game
 */
public class ChromaticNumberGame {
    /**
     * Run the game
     */
    public static void run(String[] args) {
        JFrame frame = new JFrame("Game");       
        JPanel opPanel = new JPanel();
        JPanel menuPanel = new JPanel();
        
        //make GraphComponent
        GraphComponent gc = new GraphComponent();
        gc.update();
        //make OperationComponenet
        OperationComponent op = new OperationComponent(gc, opPanel);
        op.update();
        gc.setOperationComponent(op);
        opPanel.add(op);
          
        //make MainMenu
        MainMenu menu = new MainMenu(gc, op, menuPanel);
        op.setMainMenu(menu);
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
        gc.setDrawSize(600, 400, 25);
        gc.setGameMode(menu.getGameMode());
        constr.gridx = 2;          
        //frame.add(gc, constr);     
        frame.add(gc, 0, 1);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        run(args);
    }
}
