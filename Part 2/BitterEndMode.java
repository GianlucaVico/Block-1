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
        //always win        
            if(graph.notColored().isEmpty() && graph.errors().isEmpty() && graph.getOperationComponent().countUsedColors() <= graph.getSolvers()[GraphComponent.UPPER_BOUND].solve()) {
                ended = true;
                if(graph.chromaticNumberUsed()) {
                    win = true;
                    timer.stop();
                }
            }
        }
        return ended;
    }
}
