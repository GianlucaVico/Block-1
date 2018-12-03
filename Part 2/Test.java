public class Test {
	//do tests here
	public static void main(String[] args) {
		Graph g = new Graph(10, 10);
		g = new Graph("C:\\Users\\admin\\Desktop\\DKE\\1.2\\Project\\Block-1\\allGraphs2018\\graph.txt");
                g = new Graph(30, 30);
		for(Node n: g.getNodes()){
                    for(Node c: n.getChildren()) {
                        if(n.getId() < c.getId())
                            System.out.println(n.getId() + " " + c.getId());
                    }
                }
                System.out.println(g.getSize());
		System.out.println(g.getEdges());
		System.out.println(g.isTrivial());
		System.out.println(g.getMaxDegree());
		Solver up = new UpperBound(g);
		Solver low = new LowerBound(g);
		Solver ch = new ChromaticNumber(g, low, up);
                System.out.println("Subs: " + g.countSubgraphs());
		System.out.println("Upper bound: " + up.solve());
		System.out.println("Lower bound: " + low.solve());
		System.out.println("Chromatic: " + ch.solve());				
	}
}