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

public class DijkstraAStarTestGrandeCarte {

	
	private static Graph graph ;
	
	public static void initAll() throws IOException {

    // Visit these directory to see the list of available files on Commetud.
    String mapName = "/home/loic/Documents/be_graphes/MAPS/midi-pyrenees.mapgr";
    		//"/home/djebar/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/insa.mapgr"

    // Create a graph reader.
    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    graph=reader.read();
    
	}
	
	@Test
	public void	Scenario1() throws IOException { // Test des chemins en longueur avec des coordonnées de début et de fin aléatoires
	initAll();
	for (int i=0;i<5;i++)
	{
		
		
			Random r1=new Random();
			Random r2=new Random();
			int nb1 = r1.nextInt(100000); 
			int nb2 = r2.nextInt(100000);
			new ArcInspectorFactory();
			ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
			System.out.println("Depart : "+nb1+" Destination : "+nb2);
			ShortestPathData data = new ShortestPathData(graph,graph.get(nb1),graph.get(nb2),arcInspector);
			ShortestPathSolution D =new DijkstraAlgorithm(data).run();
			ShortestPathSolution A =new AStarAlgorithm(data).run();
    		
			assertEquals(D.getStatus(),A.getStatus());
			if (D.getStatus()==Status.OPTIMAL)
    		{
				assertEquals(D.getPath().getLength(),A.getPath().getLength(),10);
    		}
		
	}
	}
	
	@Test
	public void	Scenario2() throws IOException { // Test des chemins en longueur avec des coordonnées de début et de fin aléatoires
	initAll();
	for (int i=0;i<5;i++)
	{
		
		
			Random r1=new Random();
			Random r2=new Random();
			int nb1 = r1.nextInt(100000); 
			int nb2 = r2.nextInt(100000);
			new ArcInspectorFactory();
			ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(2);
			System.out.println("Depart : "+nb1+" Destination : "+nb2);
			ShortestPathData data = new ShortestPathData(graph,graph.get(nb1),graph.get(nb2),arcInspector);
			ShortestPathSolution D =new DijkstraAlgorithm(data).run();
			ShortestPathSolution A =new AStarAlgorithm(data).run();
    		
			assertEquals(D.getStatus(),A.getStatus());
			if (D.getStatus()==Status.OPTIMAL)
    		{
				assertEquals(D.getPath().getMinimumTravelTime(),A.getPath().getMinimumTravelTime(),10);
    		}
		
	}
	}
}
