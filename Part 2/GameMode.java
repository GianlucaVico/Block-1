public abstract class GameMode {
    protected boolean reversedTimer;    //timer from 0 to infine or from 60sec to 0 
    protected boolean changeColor;
    protected boolean started;
    protected int initialTime;
    protected GraphComponent graph;
    protected boolean win;
    protected boolean ended;
    protected boolean allowsErrors;
    protected boolean autoSelect;
    protected PlayTimer timer;
    
    public GameMode(GraphComponent graph, boolean reversed, boolean changeColor, int initialTime, boolean allowErrors, boolean autoSelect, PlayTimer timer) {
        this.graph = graph;
        this.reversedTimer = reversed;
        this.changeColor = changeColor;
        this.initialTime = initialTime;
        this.started = false;
        this.allowsErrors = allowErrors;
        this.autoSelect = autoSelect;
        this.timer = timer;
    }
    public GameMode(GraphComponent g) {
        graph = g;
        started = false;
        allowsErrors = true;
        autoSelect = false;
    }
    
    //don't check correct colors -> done by graph component
    //check if correct solution and graph full colored - no erros from graph component
    public abstract boolean gameEnded();   //set win too 
    
    public boolean isTimerReversed() {
        return reversedTimer;
    }
    
    public boolean canChangeColor() {
        return changeColor;
    }
    
    public boolean isGameStarted() {
        return started;
    }    
    
    public void start() {
        started = true;
    }
    
    public boolean isWinner() {
        boolean result = false;
        if(started && ended && win)
            result = true;
        return result;
    }
    
    public boolean errorAllowed() {
        return allowsErrors;
    }
    
    public boolean autoSelection() {
        return autoSelect;
    }
    public void setTimer(PlayTimer t) {
        timer = t;
    }
}
