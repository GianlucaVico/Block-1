public class Test{
	public static void main(String[] args) {
		if(args.length != 0 && args[0].equals("all")){
			for(int j = 0; j < 2; j++){
				for(int i = 1; i <= 9; i++){		
					System.out.println("Graph" + j + i);
					ColEdge[] e = ReadGraph.getEdges("allGraphs2018\\graph" + j + i + ".txt");
					Graph g = new Graph(e, ReadGraph.edges, ReadGraph.verts);
					System.out.println("Lower bound: " + g.getBounds()[0]);
					System.out.println("Upper bound: " + g.getBounds()[1]);
					System.out.println("Chromatic number: " + g.getChromaticNumber());
				}
			}
			System.out.println("Graph20");
			ColEdge[] e = ReadGraph.getEdges("allGraphs2018\\graph" + 20 + ".txt");
			Graph g = new Graph(e, ReadGraph.edges, ReadGraph.verts);
			System.out.println("Lower bound: " + g.getBounds()[0]);
			System.out.println("Upper bound: " + g.getBounds()[1]);
			System.out.println("Chromatic number: " + g.getChromaticNumber());	
		}else{
			ColEdge[] e;
			if(args.length == 0)
				e = ReadGraph.getEdges("allGraphs2018\\graph.txt");
			else
				e = ReadGraph.getEdges("allGraphs2018\\graph" + args[0] + ".txt");
			Graph g = new Graph(e, ReadGraph.edges, ReadGraph.verts);
			System.out.println("Lower bound: " + g.getBounds()[0]);
			System.out.println("Upper bound: " + g.getBounds()[1]);
			System.out.println("Chromatic number: " + g.getChromaticNumber());
		}
	}
}