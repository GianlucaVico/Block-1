/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class EdwardsElphickLowerBound implements Solver{
    //n / (n - sqrt((sum 1, n: (d(i))^2 )/n)))
    private Graph g;
    private int solution;
    
    public EdwardsElphickLowerBound(Graph g) {
        solution = -1;
        this.g = g;
    }
    
    public int solve(){ 
        if(solution == -1) {
            double degreeSum = 0;
            for(Node n: g.getNodes()) {
                degreeSum += Math.pow(n.getDegree(), 2);
            }
            solution = (int)Math.ceil(g.getSize() / (g.getSize() - Math.sqrt(degreeSum/g.getSize())));
        }
        System.out.println("--Edwards&Elphick solver: " + solution);
        return solution;
    }
    public Graph getGraph() { return g;}
}
