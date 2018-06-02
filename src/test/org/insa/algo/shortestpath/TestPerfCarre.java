package org.insa.algo.shortestpath;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.junit.Test;

public class TestPerfCarre {

	
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
        Chrono chronoD1 = new Chrono();
        chronoD1.start(); // démarrage du chrono 
		ShortestPathSolution Dl1 =new DijkstraAlgorithm(data1).run();
        chronoD1.stop(); // arrêt
		long dureeD1=chronoD1.getDureeMicro(); // ajout du résultat en millisecondes
		System.out.println("Duree :"+dureeD1);
		
		System.out.println("Astar");
        Chrono chronoA1 = new Chrono();
        chronoA1.start(); // démarrage du chrono 
		ShortestPathSolution Al1 =new AStarAlgorithm(data1).run();
        chronoA1.stop(); // arrêt
		long dureeA1=chronoA1.getDureeMicro(); // ajout du résultat en millisecondes
		System.out.println("Duree :"+dureeA1);
		
		System.out.println("TEMPS sur "+ map);
		
		ArcInspector arcInspector2 = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data2 = new ShortestPathData(graph,graph.get(debut),graph.get(fin),arcInspector2);
		

		System.out.println("Dijkstra");      
        Chrono chronoD2 = new Chrono();
        chronoD2.start(); // démarrage du chrono 
		ShortestPathSolution Dl2 =new DijkstraAlgorithm(data2).run();
        chronoD2.stop(); // arrêt
		long dureeD2=chronoD2.getDureeMicro(); // ajout du résultat en millisecondes
		System.out.println("Duree :"+dureeD2);
		
		System.out.println("Astar");
        Chrono chronoA2 = new Chrono();
        chronoA2.start(); // démarrage du chrono
		ShortestPathSolution Al2 =new AStarAlgorithm(data2).run();
        chronoA2.stop(); // arrêt
		long dureeA2=chronoA2.getDureeMicro(); // ajout du résultat en millisecondes
		System.out.println("Duree :"+dureeA2);

		}
	
	
	@Test
	public void	carre() throws IOException { // Test des chemins en longueur avec des coordonnées de début et de fin aléatoires
	Stats(53304,92727,"carre-dense");
	}
}
