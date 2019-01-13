/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author admin
 */
public class ReedUpperBound implements Solver{
    private int solution;
    private Graph g;
    
    public ReedUpperBound(Graph g, Solver low) {
        solution = (int)Math.ceil(((double) g.getNodes().get(0).getDegree() + low.solve())/2);
        this.g = g;
    }
    public int solve() {System.out.println("--Reed: " + solution); return solution;}
    public Graph getGraph() {return g;}
}
