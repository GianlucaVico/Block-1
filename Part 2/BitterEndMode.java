public class BitterEndMode extends GameMode {   //know chromatic number
    private Solver solution;
    
    public BitterEndMode(GraphComponent graph) {
        super(graph);
        this.changeColor = true;
        this.initialTime = 0;
        this.reversedTimer = false;                
        this.solution = new ChromaticNumber(graph.getGraph(), new LowerBound(graph.getGraph()), new UpperBound((Graph)graph.getGraph().clone()));   //TODO reuse other solvers
    }   
    
    public boolean gameEnded() {
        if(!ended){
        //every node colored
        //number of color == chromatic number
        //TODO color check 
        if(graph.notColored().size() == 0 && graph.errors().size() == 0) {}
        }
        return ended;
    }
    public void setSolever(Solver solver) {
        this.solution = solver;
    }
}
