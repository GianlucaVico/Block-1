public class FixedTimeMode extends GameMode { //doesnt know chromatic number
    public FixedTimeMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 60;
        this.reversedTimer = true;        
    }
    
    public boolean gameEnded() {
        if(!ended){
        //every node colored
        //timer == 0
        //TODO timer check
            if((graph.notColored().size() == 0 && graph.errors().size() == 0)) {}
            }
        return ended;
    }
}
