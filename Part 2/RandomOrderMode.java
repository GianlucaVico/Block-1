public class RandomOrderMode extends GameMode{  
    public RandomOrderMode(GraphComponent graph) {
        super(graph);
        this.changeColor = false;
        this.initialTime = 0;
        this.reversedTimer = false;        
    }
    
    public boolean gameEnded() {
        if(!ended) {
            if(graph.notColored().size() == 0 && graph.errors().size() == 0) {}
        }        
        return ended;
    }
}
