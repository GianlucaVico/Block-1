public class FixedTimeMode extends GameMode { //doesnt know chromatic number      
    public FixedTimeMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 60;
        this.reversedTimer = true;        
    }
    
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
