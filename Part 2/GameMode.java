/**
 * GameMode represent the rules of the game
 */
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
    
    /**
     * Make a custom GameMode
     * @param graph GraphComponent to check
     * @param reversed if true the PlayTimer runs reversed
     * @param changeColor if true the player can change the color of a node
     * @param initialTime set the initial value of the timer 
     * @param allowErrors if true the player can set a wrong color
     * @param autoSelect if true the next node is selected by the GraphComponent and not by the player
     * @param timer PlayTimer to use for the game
     */
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
    /**
     * Check if the game is ended and if the player won
     * @return true if the game is ended
     */
    public abstract boolean gameEnded();   //set win too 
    
    /**
     * @return true if the PlayTimer runs reversed
     */
    public boolean isTimerReversed() {
        return reversedTimer;
    }
    /**
     * @return true if the player can change the color of a node
     */
    public boolean canChangeColor() {
        return changeColor;
    }
    
    /**
     * @return true if the game is starter
     */
    public boolean isGameStarted() {
        return started;
    }    
    /**
     * Start the game
     */
    public void start() {
        started = true;
    }
    /**
     * @return true is the player won
     */
    public boolean isWinner() {
        boolean result = false;
        if(started && ended && win)
            result = true;
        return result;
    }
    
    /**
     * @return true if the player can assign wrong colors
     */
    public boolean errorAllowed() {
        return allowsErrors;
    }
    /**
     * @return true if the GraphComponent select the next node
     */
    public boolean autoSelection() {
        return autoSelect;
    }
    
    /**
     * @param t set a new PlayTimer
     */
    public void setTimer(PlayTimer t) {
        timer = t;
    }
}
