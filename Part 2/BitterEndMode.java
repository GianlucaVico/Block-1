public class BitterEndMode extends GameMode {   //know chromatic number    
    public BitterEndMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 0;
        this.reversedTimer = false;                   
    }   
    
    public boolean gameEnded() {    //always win
        if(!ended){
        //every node colored
        //number of color == chromatic number        
            if(graph.notColored().size() == 0 && graph.errors().size() == 0 && graph.chromaticNumberUsed()) {
                ended = true;
                win = true;
            }
        }
        return ended;
    }
}
