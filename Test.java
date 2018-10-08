public class Test{
	public static void main(String[] args) {
		ColEdge[] e = ReadGraph.getEdges("allGraphs2018\\graph01.txt");
		Graph g = new Graph(e, ReadGraph.edges, ReadGraph.verts);
		System.out.println(g.getBounds()[0]);
		System.out.println(g.getBounds()[1]);
		System.out.println(g.isNullGraph());
		System.out.println(g.isComplete());
	}
}