package org.insa.algo.shortestpath;

import static org.junit.Assert.assertEquals;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.algo.AbstractSolution.Status;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class TestPerf2 {

	
	
	
private static Graph graph ;
	
	public static void initAll(String map) throws IOException {

    // Visit these directory to see the list of available files on Commetud.
    String mapName ="/home/loic/Documents/be_graphes/MAPS/"+map+".mapgr"; // "/home/djebar/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/insa.mapgr";

    // Create a graph reader.
    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    graph=reader.read();
    
	}
	
	public void	Stats(int debut, int fin, String map) throws IOException { // Point de départ et d'arrivé identiques
		initAll(map);
		new ArcInspectorFactory();
		ArcInspector arcInspector1 = ArcInspectorFactory.getAllFilters().get(0);
		ShortestPathData data1 = new ShortestPathData(graph,graph.get(debut),graph.get(fin),arcInspector1);
		
		
		System.out.println("DISTANCE sur "+ map);
		
		System.out.println("Dijkstra");
		ShortestPathSolution Dl =new DijkstraAlgorithm(data1).run();
		System.out.println("Astar");
		ShortestPathSolution Al =new AStarAlgorithm(data1).run();
		
		System.out.println("TEMPS sur "+ map);
		
		ArcInspector arcInspector2 = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data2 = new ShortestPathData(graph,graph.get(debut),graph.get(fin),arcInspector2);
		
		System.out.println("Dijkstra");
		ShortestPathSolution Dt =new DijkstraAlgorithm(data2).run();
		System.out.println("Astar");
		ShortestPathSolution At =new AStarAlgorithm(data2).run();
		}
	
	@Test
	public void	TestTasEtNodesVisites() throws IOException { // Test des chemins en longueur avec des coordonnées de début et de fin aléatoires
	String map = "haute-garonne";
	Stats(18255,98597,map);	
	map = "midi-pyrenees";
	Stats(548469,241004,map);
	map = "belgium";
	Stats(182987,354969,map);
	}
	
	
	
	
}
