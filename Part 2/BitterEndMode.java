/**Created by Louis Gauthy */
/**
 * GameMode, the player has to find the chromatic number
 */
public class BitterEndMode extends GameMode {   //know chromatic number    
    /** @param graph The graph component used in the game*/
    public BitterEndMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 0;
        this.reversedTimer = false;                   
    }   
    
    /**
     * The game ends when all the nodes have a color, there are no error and the player used the minimum number of colors
     * @return true if the game ended, false if still playing
     */
    public boolean gameEnded() {    //always win
        if(!ended){
        //every node colored
        //number of color == chromatic number  
        //always win        
            if(graph.notColored().isEmpty() && graph.errors().isEmpty() && graph.getOperationComponent().countUsedColors() <= graph.getSolvers()[GraphComponent.UPPER_BOUND].solve()) {
                ended = true;
                //if(graph.chromaticNumberUsed()) {
                if(graph.getSolution() >= graph.getOperationComponent().countUsedColors()){
                    win = true;
                    timer.stop();
                }
            }
        }
        return ended;
    }
}
