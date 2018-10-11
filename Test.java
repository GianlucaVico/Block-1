public class Test{
	public static void main(String[] args) {
		ColEdge[] e;
		if(args.length == 0)
			e = ReadGraph.getEdges("allGraphs2018\\graph.txt");
		else
			e = ReadGraph.getEdges("allGraphs2018\\graph" + args[0] + ".txt");
		Graph g = new Graph(e, ReadGraph.edges, ReadGraph.verts);
		System.out.println("Lower bound: " + g.getBounds()[0]);
		System.out.println("Upper bound: " + g.getBounds()[1]);
		//System.out.println(g.isNullGraph());
		//System.out.println(g.isComplete());
		System.out.println("Chromatic number: " + g.getChromaticNumber());
	}
}