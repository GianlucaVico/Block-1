public class FixedTimeMode extends GameMode { //doesnt know chromatic number
    private PlayTimer timer;
    
    public FixedTimeMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 60;
        this.reversedTimer = true;        
    }
    public void setTimer(PlayTimer timer) {
        this.timer = timer;
    }
    
    public boolean gameEnded() {
        if(!ended){
        //timer == 0
        //TODO timer check to set end        
            win = (graph.notColored().isEmpty() && graph.errors().isEmpty());  
            if(win || timer.getTime() == 0) {
                ended = true;
            }
        }
        return ended;
    }
}
