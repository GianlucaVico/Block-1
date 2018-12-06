public class RandomOrderMode extends GameMode{  
    public RandomOrderMode(GraphComponent graph) {
        super(graph);
        this.changeColor = false;
        this.initialTime = 0;
        this.reversedTimer = false; 
        this.allowsErrors = false;
        this.autoSelect = true;
    }
    
    public boolean gameEnded() {
        //always win
        if(!ended) {
            if(graph.notColored().isEmpty() && graph.errors().isEmpty()) {
                ended = true;
                win = true;
            }
        }        
        return ended;
    }    
}
