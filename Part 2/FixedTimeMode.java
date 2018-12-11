/**
 * GameMode, the player has to color the graph within 2 minutes
 */
public class FixedTimeMode extends GameMode { //doesnt know chromatic number      
    /** @param graph The graph component used in the game*/
    public FixedTimeMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 60;
        this.reversedTimer = true;        
    }
    
    /**
     * The game ends when all the nodes have a color and there are no errors or when the timer is 0. The player wins if the game ends before the timer.
     * @return 
     */
    public boolean gameEnded() {
        if(!ended){
        //timer == 0                  
            win = (graph.notColored().isEmpty() && graph.errors().isEmpty());  
            if(win || timer.getTime() <= 0) {
                ended = true;
                timer.stop();
            }
        }
        return ended;
    }
}
