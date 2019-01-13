public class BondyLowerBound implements Solver{
    private Graph g;
    private int solution;
    private int sigmaJ, sumJ, sumI, k;
    private boolean done;
    
    public BondyLowerBound(Graph g) {
        this.g = g;
        solution = -1;
        sumI = 0;
        sumJ = 0;
        sigmaJ = 0;
        k = 0;
        solution = -1;      
        done = false;
    }
    
    public int solve(){
        while(!done) {
            solution = nextSolution();
        }
        return solution;
    }
    
    public int nextSolution() {
        int next = 0;
        if(done) {
            next = solution;
        }else {
            int tmp = nextSigmaJ();
            if((sumJ + tmp) < g.getSize() && tmp != 0) {
                k++;
                next = k; 
            }else
                next = solution;
        }
        return next;
    }
    
    public Graph getGraph() { return g;}
    
    private int nextSigmaJ() {
        if((sumI + 1) >= g.getSize()) {
            done = true;
            return 0;
        }else{            
            sigmaJ = g.getSize() - g.getNodes().get(sumI + 1).getDegree();
            sumI += sigmaJ;
            return sigmaJ;
        }
    }    
}
