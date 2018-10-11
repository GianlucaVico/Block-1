import java.io.*;
import java.util.*;
class ColEdge
{
int u;
int v;
}

public class method2 {
	public final static boolean DEBUG = false;
	
	public final static String COMMENT = "//";
	
	public static void run( String graphName )
		{
		/*if( args.length < 1 )
			{
			System.out.println("Error! No filename specified.");
			System.exit(0);
			}*/
		String inputfile = graphName;
		
		boolean seen[] = null;
		
		//! n is the number of vertices in the graph
		int n = -1;
		
		//! m is the number of edges in the graph
		int m = -1;
		
		//! e will contain the edges of the graph
		ColEdge e[] = null;
		
		try 	{ 
		    	FileReader fr = new FileReader(inputfile);
		        BufferedReader br = new BufferedReader(fr);

		        String record = new String();
				
				//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
				//! These comments are only allowed at the top of the file.
				
				//! -----------------------------------------
		        while ((record = br.readLine()) != null)
					{
					if( record.startsWith("//") ) continue;
					break; // Saw a line that did not start with a comment -- time to start reading the data in!
					}

				if( record.startsWith("VERTICES = ") )
					{
					n = Integer.parseInt( record.substring(11) );					
					if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
					}

				seen = new boolean[n+1];	
					
				record = br.readLine();
				
				if( record.startsWith("EDGES = ") )
					{
					m = Integer.parseInt( record.substring(8) );					
					if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
					}

				e = new ColEdge[m];	
											
				for( int d=0; d<m; d++)
					{
					if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
					record = br.readLine();
					String data[] = record.split(" ");
					if( data.length != 2 )
							{
							System.out.println("Error! Malformed edge line: "+record);
							System.exit(0);
							}
					e[d] = new ColEdge();
					
					e[d].u = Integer.parseInt(data[0]);
					e[d].v = Integer.parseInt(data[1]);

					seen[ e[d].u ] = true;
					seen[ e[d].v ] = true;
					
					if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
			
					}
								
				String surplus = br.readLine();
				if( surplus != null )
					{
					if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
					}
				
				}
		catch (IOException ex)
			{ 
	        // catch possible io errors from readLine()
		    System.out.println("Error! Problem reading file "+inputfile);
			System.exit(0);
			}

		for( int x=1; x<=n; x++ )
			{
			if( seen[x] == false )
				{
				if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
				}
			}

		//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
		//! e[1] will be the second edge...
		//! (and so on)
		//! e[m-1] will be the last edge
		//! 
		//! there will be n vertices in the graph, numbered 1 to n

		//! INSERT YOUR CODE HERE!			
	
	boolean arrayRef [][]= new boolean[1500][100];
	for(int a=0; a<1500; a++) {
		for(int b=0 ; b<100; b++) {
			arrayRef[a][b]=true;
		}
	}
	int arrayIn[][]= new int [e.length][2]; // {{1,2},{1,8},{1,9},{9,3},{9,8},{8,10},{10,2},{10,1},{8,3},{8,7},{8,2},{1,3},{2,3},{3,7},{7,2},{3,4},{4,5},{4,6},{5,6}};
	int lg = 0;
	for(int t=0; t<e.length; t++) {
		arrayIn[t][0]=e[t].u;
		arrayIn[t][1]=e[t].v;
		lg++;
	}
	int children[]= new int[1000];
	int lengthchil=0;
	int chrom=1;

     for(int i=0; i<lg ; i++) {
		//start
		if(i==0) {
			int first = arrayIn[0][0];
			int newArray[][]=new int[100][2];
			int lengthnew=0;
			for(int j=0; j<lg ; j++) {
				//look for every links with the first node
				if(arrayIn[j][0]==first || arrayIn[j][1]==first) {
					newArray[lengthnew][0]=arrayIn[j][0];
					newArray[lengthnew][1]=arrayIn[j][1];
					arrayIn[j][0]=0;
					arrayIn[j][1]=0;
					lengthnew++;
				}
			}
				//assign colours for start
				for(int k=0; k<lengthnew; k++) {
					if(newArray[k][0]!=first) {
						arrayRef[newArray[k][0]][0]=false;
						children[lengthchil]=newArray[k][0];
						lengthchil++;
						chrom++;
					}
					if (newArray[k][1]!=first) {
						arrayRef[newArray[k][1]][0]=false;
						children[lengthchil]=newArray[k][1];
						lengthchil++;
						if(chrom==1) {
							chrom++;
						}
					}
				}
			
		/*System.out.println("arrayRef start");
		for(int l=0; l<20 ;l++) {
			for(int m=0; m<4;m++) {
				System.out.print(arrayRef[l][m]+", ");
			}
			System.out.println();
		}*/
		}
		else {
			//main algorithm
		
			while(lengthchil!=0) {
				
				//System.out.println("chidren");
				/*for (int c =0; c<lengthchil; c++) {
					System.out.println(children[c]);
				}*/
				
				int first = children[lengthchil-1];
				
				//System.out.println("first "+first);
				
				children[lengthchil]=0;
				lengthchil--;
				//System.out.println("chidren");
				/*for (int c =0; c<lengthchil; c++) {
					System.out.println(children[c]);
				}*/
				
				int [] chilchil= new int [100];
				int lengthchilchil=0;
				int newArray[][]=new int[100][2];
				int lengthnew=0;
				for(int j=0; j<lg ; j++) {
					//look for every links with the first node
					if(arrayIn[j][0]==first || arrayIn[j][1]==first) {
						newArray[lengthnew][0]=arrayIn[j][0];
						newArray[lengthnew][1]=arrayIn[j][1];
						arrayIn[j][0]=0;
						arrayIn[j][1]=0;
						lengthnew++;
					}
				}
				//System.out.println("newArray");
				/*for(int l=0; l<lg ;l++) {
					for(int m=0; m<2;m++) {
						System.out.print(newArray[l][m]+", ");
					}
					System.out.println();
				}*/
				
				//assigning colours
				for(int k=0; k<lengthnew; k++) {
					//System.out.println("start k "+k);
					//case if compared number is not in the arrayRef
					if(newArray[k][0]!=first) {
					//System.out.println("0k "+k);
					//System.out.println("newArray[k][0] "+newArray[k][0]);
							int countfirst=0;
							int countk=0;
							int d=0;
							//System.out.println("first "+first);
							while(arrayRef[first][d]==false) {
							countfirst++;
							d++;
							}
							d=0;
							while(arrayRef[newArray[k][0]][d]==false) {
								countk++;
								d++;
								}
						//	System.out.println("countfirst "+countfirst);
							//System.out.println("countk "+countk);
							//colours does not match
							if(countk==countfirst) {
								if ((countk+1)==chrom) {
									//System.out.println("chrom1 "+chrom);
									chrom++;
									//System.out.println("chrom2 "+chrom);
								}
								arrayRef[newArray[k][0]][countk]=false;
								chilchil[lengthchilchil]=newArray[k][0];
								//System.out.println("newArray[k][0]1 "+newArray[k][0]);
								lengthchilchil++;
								//System.out.println("if1 ref");
								/*for(int l=0; l<20 ;l++) {
									for(int m=0; m<4;m++) {
										System.out.print(arrayRef[l][m]+", ");
									}
									System.out.println();
								}*/
							}else {
								chilchil[lengthchilchil]=newArray[k][0];
								//System.out.println("newArray[k][0]2 "+newArray[k][0]);
								lengthchilchil++;
								//System.out.println("else1 ref");
								/*for(int l=0; l<20 ;l++) {
									for(int m=0; m<4;m++) {
										System.out.print(arrayRef[l][m]+", ");
									}
									System.out.println();
								}*/
							}
					}
							if(newArray[k][1]!=first) {
								//System.out.println("1k "+k);
								//System.out.println("newArray[k][1] "+newArray[k][1]);
										int countfirst=0;
										int countk=0;
										int d=0;
									//	System.out.println("first "+first);
										while(arrayRef[first][d]==false) {
										countfirst++;
										d++;
										}
										d=0;
										while(arrayRef[newArray[k][1]][d]==false) {
											countk++;
											d++;
											}
										//System.out.println("countfirst "+countfirst);
										//System.out.println("countk "+countk);
										//colours does not match
										if(countk==countfirst) {
											if ((countk+1)==chrom) {
												//System.out.println("chrom1 "+chrom);
												chrom++;
												//System.out.println("chrom2 "+chrom);
											}
											arrayRef[newArray[k][1]][countk]=false;
											chilchil[lengthchilchil]=newArray[k][1];
											//System.out.println("newArray[k][1]1 "+newArray[k][1]);
											lengthchilchil++;
											//System.out.println("if 2 ref");
											/*for(int l=0; l<20 ;l++) {
												for(int m=0; m<4;m++) {
													System.out.print(arrayRef[l][m]+", ");
												}
												System.out.println();
											}*/
										}else {
											chilchil[lengthchilchil]=newArray[k][1];
											//System.out.println("newArray[k][1]2 "+newArray[k][1]);
											lengthchilchil++;
											//System.out.println("else 2 ref");
											/*for(int l=0; l<20 ;l++) {
												for(int m=0; m<4;m++) {
													System.out.print(arrayRef[l][m]+", ");
												}
												System.out.println();
											}*/
										}
							}

					}
							/*System.out.println("arrayRef if 2");
							for(int l=0; l<20 ;l++) {
								for(int m=0; m<4;m++) {
									System.out.print(arrayRef[l][m]+", ");
								}
								System.out.println();
							}
			
				System.out.println("chidren 1");
				for (int c =0; c<lengthchil; c++) {
					System.out.println(children[c]);
				}*/
				for(int z=0; z<lengthchilchil; z++) {
					children[lengthchil]=chilchil[z];
					lengthchil++;
				}
				lengthchilchil=0;
				/*System.out.println("chidren 2");
				for (int c =0; c<lengthchil; c++) {
					System.out.println(children[c]);
				}
			}*/
		}
     }
		
	
/*for (int c =0; c<lengthchil; c++) {
	System.out.println(children[c]);
}*/
     }
System.out.println("final in");
/*	for(int l=0; l<lg ;l++) {
		for(int w=0; w<2;w++) {
			System.out.print(arrayIn[l][w]+", ");
		}
		System.out.println();
	}
	System.out.println("final ref");
	int colour=1;
	for(int l=1; l<100 ;l++) {
		for(int y=0; y<50;y++) {
			if (arrayRef[l][y]==false) {
				colour++;
			}
		}
		System.out.print("number "+l);
		System.out.println(" is colour "+colour);
		colour=1;
	}*/
	System.out.println("The chromatic number is: "+chrom);
	}
	
}