package org.insa.algo.shortestpath;

import java.io.IOException;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class DijsktraTest {
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, b2d, b2e, b2f,c2a, c2b, c2f, f2e, e2f, e2d, e2c;

    // Some paths...
    private static ArrayList<ShortestPathSolution> listeD = new ArrayList<ShortestPathSolution>();
    private static ArrayList<ShortestPathSolution> listeB = new ArrayList<ShortestPathSolution>();
	
    @BeforeClass
    public static void initAll() throws IOException {

        // 10 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");
        //speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 7, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 8, speed10, null); 
        b2d = Node.linkNodes(nodes[1], nodes[3], 4, speed10, null);
        b2e = Node.linkNodes(nodes[1], nodes[4], 1, speed10, null);
        b2f = Node.linkNodes(nodes[1], nodes[5], 5, speed10, null);
        c2a = Node.linkNodes(nodes[2], nodes[0], 7, speed10, null);
        c2b = Node.linkNodes(nodes[2], nodes[1], 2, speed10, null);
        c2f = Node.linkNodes(nodes[2], nodes[5], 2, speed10, null);
        f2e = Node.linkNodes(nodes[5], nodes[4], 3, speed10, null);
        e2f = Node.linkNodes(nodes[4], nodes[5], 3, speed10, null);
        e2d = Node.linkNodes(nodes[4], nodes[3], 2, speed10, null);
        e2c = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
       
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        for (Node nodeact : nodes )
        {
        	for (Node nodeact2 : nodes )
        		if (nodeact2.getId()!=nodeact.getId()) 
        		{
        			new ArcInspectorFactory();
					ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        			ShortestPathData data= new ShortestPathData(graph,nodeact,nodeact2, arcInspector);
        			listeD.add( new DijkstraAlgorithm(data).doRun());
        		}
        }
        
        for (Node nodeact : nodes )
        {
        	for (Node nodeact2 : nodes )
        		if (nodeact2.getId()!=nodeact.getId()) 
        		{

        			new ArcInspectorFactory();
					ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
        			ShortestPathData data= new ShortestPathData(graph,nodeact,nodeact2, arcInspector);
        			listeB.add(new BellmanFordAlgorithm(data).doRun());
        		}
        }
        
    }
    
    @Test // Comparaison de toutes les solutions trouvées par Dijkstra avec celles de Bellman-Ford
    public void ComparaisonBD() {
    	System.out.println(listeD.size());
    	int cpt=1;
    	for (int i=0; i<listeD.size(); i++)
    	{
    		if ((i)%5==0)
    		{
    			System.out.print("x"+cpt + ": ");
    		}
    		assertEquals(listeD.get(i).getStatus(),listeB.get(i).getStatus());
    		if (listeD.get(i).getStatus()==Status.OPTIMAL)
    		{
    			assertEquals(listeD.get(i).getPath().getLength(),listeB.get(i).getPath().getLength(),0);
    			System.out.print(" "+listeD.get(i).getPath().getLength()+ " ");
    		}
    		if ((i+1)%5==0)
    		{
    			System.out.println();
    			cpt++;
    		}
    	}
    }
    
    
}
