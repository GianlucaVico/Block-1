import javax.swing.JFrame;
import javax.swing.JButton;

public class OperationTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame("OperationComponent");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		OperationComponent oc = new OperationComponent(frame);
		frame.add(oc);
		//frame.add(new JButton("Test"));
		frame.setVisible(true);
	}
}