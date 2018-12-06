import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

class PlayTimer extends JPanel {
    JLabel label;
    Timer timer;
    int seconds;
    
    class CountUp implements ActionListener {
        public void actionPerformed(ActionEvent e) {    	
            seconds++;                         
            label.setText("Time:  " + getStringTime()); 
        }        
    }
    class CountDown implements ActionListener {
        private ActionListener timeOut;
        public CountDown(ActionListener action) {
            timeOut = action;
        }
        
        public void actionPerformed(ActionEvent e) {
            seconds--;
            if (seconds > -1) {
                label.setText("Time:  " + getStringTime()); 
            } else {
                //((Timer) (e.getSource())).stop();      
                stop();
                if(timeOut != null) {
                    timeOut.actionPerformed(new ActionEvent(timer, seconds, "TimeOut"));
                }
            }
        }
    }
    
    public PlayTimer(boolean rev, int initial) { 
        seconds = initial;
        label = new JLabel("...");
        setLayout(new GridBagLayout());
        add(label);
        setReversed(rev, null);
        timer.setInitialDelay(0);
    } 
    
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
    
    public void reset(int seconds) {
        this.seconds = seconds;
    }
    
    public void setReversed(boolean rev, ActionListener action) {
        if(rev)
            timer = new Timer(1000, new CountDown(action));
        else
            timer = new Timer(1000, new CountUp());
    }    
    public void start() {
        timer.start();
    }
    
    public void stop() {
        timer.stop();
        label.setText("...");
    }
    
    public boolean isRunning() {
        return timer.isRunning();
    }
    
    public int getTime() {
        return seconds;
    }
    
    public String getStringTime() {
        return seconds / 60 + " : " + seconds % 60;
    }
    
    public static void main(String[] args) {
        JFrame frame=new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new PlayTimer(false, 0));
        frame.pack();
                  
        // Setting Frame size. This is the window size
        frame.setSize(300,400);  
        frame.setTitle("Menu");
        frame.setLayout(null);  
        frame.setVisible(true);  
                  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
}