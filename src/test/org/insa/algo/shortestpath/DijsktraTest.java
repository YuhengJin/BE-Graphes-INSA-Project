package org.insa.algo.shortestpath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;
import org.insa.graph.RoadInformation;
import org.insa.graph.RoadInformation.RoadType;
import org.junit.BeforeClass;

public class DijsktraTest {
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, b2d, b2e, c2a, c2b, c2f, f2e, e2f, e2d, e2c;

    // Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath;
	
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
        b2d = Node.linkNodes(nodes[0], nodes[3], 4, speed10, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);

        emptyPath = new Path(graph, new ArrayList<Arc>());
        singleNodePath = new Path(graph, nodes[1]);
        shortPath = new Path(graph, Arrays.asList(new Arc[] { a2b, b2c, c2d_1 }));
        longPath = new Path(graph, Arrays.asList(new Arc[] { a2b, b2c, c2d_1, d2e }));
        loopPath = new Path(graph, Arrays.asList(new Arc[] { a2b, b2c, c2d_1, d2a }));
        longLoopPath = new Path(graph,
                Arrays.asList(new Arc[] { a2b, b2c, c2d_1, d2a, a2c, c2d_3, d2a, a2b, b2c }));
        invalidPath = new Path(graph, Arrays.asList(new Arc[] { a2b, c2d_1, d2e }));

    }
    
    
    
    
    
    
	public static void main(String[] args) {
		
	}

}
