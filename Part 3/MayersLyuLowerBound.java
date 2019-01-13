public class MayersLyuLowerBound implements Solver{
    //p^2 / (p^2 - 2 q)
    private Graph g;
    private int solution;
    
    public MayersLyuLowerBound(Graph g) {
        this.g = g;
        solution = -1;
    }
    
    public int solve(){
        if(solution == -1) {
            double squareSize = Math.pow(g.getSize(), 2);
            solution = (int)Math.ceil(squareSize / (squareSize - g.getEdges()));
        }
        System.out.println("--Mayer&Lyu solver: " + solution);
        return solution;
    }
    
    public Graph getGraph() { return g;}
}
