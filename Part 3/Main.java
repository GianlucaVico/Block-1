public class Main {
    private static boolean done;
    
    public static void up(int bound) {
        System.out.println("NEW BEST UPPER BOUND = " + bound);        
    }
    
    public static void low(int bound) {        
        System.out.println("NEW BEST LOWER BOUND = " + bound);
    }
    
    public static void chromatic(int chrom) {
        if(chrom != -1)
            System.out.println("CHROMATIC NUMBER = " + chrom);
        done = true;
    }
    
    public static void chromatic(int upBound, int lowBound) {
        if(lowBound == upBound) {
            chromatic(lowBound);
            done = true;
        }
    }
    public static void solve(String fileName) {
        Graph g = new Graph(fileName); 
        up(g.trivialUpperBound());
        low(g.trivialLowerBound());
        if(g.isTrivial()) {
            chromatic(g.trivialSolution());
        } else{
            Solver b = new Bipartite(g);
            if(b.solve() == 1) {
                //System.out.println("--bipartite");
                chromatic(2);
            }else {
                g.sort();
                //System.out.println("--sorted");
                UpperBound upper = new UpperBound(g);
                Solver bondy = new BondyLowerBound(g);
                Solver lower = new LowerBound(g, upper);

                up(upper.solve());
                low(bondy.solve());
                chromatic(upper.solve(), bondy.solve());
                low(lower.solve());
                chromatic(upper.solve(), lower.solve());
                if(!done) {
                    Solver exact = new ChromaticNumber(g, lower, upper, b.solve() == 1); 
                    chromatic(exact.solve());
                }
            }
        }
    }
    
    public static void main(String[] args) {        
        //eliminate useless verts
        //print trivial bounds
            //check if they are equals and if it is trivial -> if so print chromatic number
        //sort
        //find greedy upper bound        
        //find bondy lower bound
            //check if equals
        //find clique
            //check if equals
        //find chromatic number
        if(args.length == 0) {
            args = new String[20];
            for(int i = 0; i < 9; i++) {
                args[i] = "graphs/block3_2018_graph0" + (i+1) + ".txt";
            }
            for(int i = 0; i <= 10; i++) {
                args[i+9] = "graphs/block3_2018_graph" + (i+10) + ".txt";
            }
        }
        for(String s: args) {
            done = false;
            solve(s);                                    
        }    
    }
}
