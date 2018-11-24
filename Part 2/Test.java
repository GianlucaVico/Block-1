public class Test {
	//do tests here
	public static void main(String[] args) {
		Graph g = new Graph(10, 10);
		g = new Graph("C:\\Users\\admin\\Desktop\\DKE\\1.2\\Project\\Block-1\\allGraphs2018\\graph07.txt");
		//g = new Graph(20, 20);
		System.out.println(g.getSize());
		System.out.println(g.getEdges());
		System.out.println(g.isTrivial());
		System.out.println(g.getMaxDegree());
		Solver up = new UpperBound(g);
		Solver low = new LowerBound(g);
		Solver ch = new ChromaticNumber(g, low, up);
		System.out.println("Upper bound: " + up.solve());
		System.out.println("Lower bound: " + low.solve());
		System.out.println("Chromatic: " + ch.solve());
		
		System.out.println("Subs: " + g.countSubgraphs());
	}
}