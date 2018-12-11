/**
 * GameMode, the user has to color a graph following the given order of nodes 
 */
public class RandomOrderMode extends GameMode{  
    /**
     * Make a RandomOrderMode.
     * @param graph GraphComponent of the game
     */
    public RandomOrderMode(GraphComponent graph) {
        super(graph);
        this.changeColor = false;
        this.initialTime = 0;
        this.reversedTimer = false; 
        this.allowsErrors = false;
        this.autoSelect = true;
    }
    
    /**
     * The game ends when all the node have a color
     * @return true if the game is ended
     */
    public boolean gameEnded() {
        //always win
        if(!ended) {
            if(graph.notColored().isEmpty() && graph.errors().isEmpty()) {
                ended = true;
                timer.stop();
                win = true;                
            }
        }        
        return ended;
    }    
}
