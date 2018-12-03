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
        
        frame.setLayout(new GridLayout(1,3));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        
        frame.add(opPanel);
        frame.add(menuPanel);
        gc.setDrawSize(500, 500);
        frame.add(gc);
        frame.setVisible(true);
    }
}
