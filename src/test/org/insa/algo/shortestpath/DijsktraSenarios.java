package org.insa.algo.shortestpath;

import static org.junit.Assert.*;

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

public class DijsktraSenarios {

	private static Graph graph ;
	
	public static void initAll() throws IOException {

    // Visit these directory to see the list of available files on Commetud.
    String mapName = "/home/djebar/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/insa.mapgr";

    // Create a graph reader.
    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    graph=reader.read();
    
	}
	
	
	@Test
	public void	Scenario1() throws IOException { // Point de départ et d'arrivé identiques
	initAll();
	new ArcInspectorFactory();
	ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(0);
	ShortestPathData data = new ShortestPathData(graph,graph.get(344),graph.get(344),arcInspector);
	ShortestPathSolution B =new BellmanFordAlgorithm(data).run();
	ShortestPathSolution D =new DijkstraAlgorithm(data).run();
	
	assertEquals(D.getStatus(),B.getStatus());
	
	}
	

	public void	Scenario2() throws IOException { // Test des chemins en longueur avec des coordonnées de début et de fin aléatoires
	initAll();
	for (int i=0;i<100;i++)
	{
		for (int j=0; j<100;j++)
		{
			Random r1=new Random();
			Random r2=new Random();
			int nb1 = r1.nextInt(1000); 
			int nb2 = r2.nextInt(1000);
			new ArcInspectorFactory();
			ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(1);
			System.out.println("Depart : "+i+" Destination : "+j);
			ShortestPathData data = new ShortestPathData(graph,graph.get(i),graph.get(j),arcInspector);
			ShortestPathSolution B =new BellmanFordAlgorithm(data).run();
			ShortestPathSolution D =new DijkstraAlgorithm(data).run();
    		
			assertEquals(D.getStatus(),B.getStatus());
			if (D.getStatus()==Status.OPTIMAL)
    		{
				assertEquals(D.getPath().getLength(),B.getPath().getLength(),0);
    		}
		}
	}
	}
	
	@Test
	public void	Scenario3() throws IOException { // Test des chemins en temps de parcours avec des coordonnées de début et de fin aléatoires
	initAll();
	for (int i=0;i<100;i++)
	{
		for (int j=500; j<600;j++)
		{

			Random r1=new Random();
			Random r2=new Random();
			int nb1 = r1.nextInt(1000); 
			int nb2 = r2.nextInt(1000);
			new ArcInspectorFactory();
			ArcInspector arcInspector = ArcInspectorFactory.getAllFilters().get(2);
			System.out.println("Depart : "+i+" Destination : "+j);
			ShortestPathData data = new ShortestPathData(graph,graph.get(i),graph.get(j),arcInspector);
			ShortestPathSolution B =new BellmanFordAlgorithm(data).run();
			ShortestPathSolution D =new DijkstraAlgorithm(data).run();
    		
			assertEquals(D.getStatus(),B.getStatus());
			if (D.getStatus()==Status.OPTIMAL)
    		{
				assertEquals(D.getPath().getMinimumTravelTime(),B.getPath().getMinimumTravelTime(),0);
    		}
		}
	}
	}
}