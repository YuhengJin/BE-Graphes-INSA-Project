package org.insa.algo.shortestpath;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.components.BasicDrawing;
import org.junit.Test;

public class Probleme1 {

	
	
	private static Graph graph ;
	
	public static void initAll(String map) throws IOException {

    // Visit these directory to see the list of available files on Commetud.
    String mapName ="/home/loic/Documents/be_graphes/MAPS/"+map+".mapgr"; // "/home/djebar/Bureau/commetud/3emeAnneMIC/Graphes-et-Algorithmes/Maps/insa.mapgr";

    // Create a graph reader.
    GraphReader reader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));
    graph=reader.read();
    
	}
	
	@SuppressWarnings("deprecation")
	public static void SolutionPb1(int debut1,int debut2,int fin,String map) throws IOException { // Point de départ et d'arrivé identiques
		initAll(map);
		new ArcInspectorFactory();
		
		ArcInspector arcInspector1 = ArcInspectorFactory.getAllFilters().get(2);
		ShortestPathData data1 = new ShortestPathData(graph,graph.get(debut1),graph.get(fin),arcInspector1);
		ShortestPathData data2 = new ShortestPathData(graph,graph.get(debut2),graph.get(fin),arcInspector1);
		
		
		ShortestPathSolution D1 =new DijkstraAlgorithm(data1).run();
		ShortestPathSolution D2 =new DijkstraAlgorithm(data2).run();
		ShortestPathSolution court = null;
		ShortestPathSolution plusLong = null;
		int debut=0;
		int minFinal = 0;
		if (D1.getPath().getMinimumTravelTime()<D2.getPath().getMinimumTravelTime()) { court = D1; plusLong=D2; debut=debut2; }
		else { court = D2; plusLong=D1; debut=debut1;}
		double tempsCourt=0;
		double temps1=court.getPath().getMinimumTravelTime();
		double Min = D1.getPath().getMinimumTravelTime() + D2.getPath().getMinimumTravelTime();
		List<Arc> arcsCourt = court.getPath().getArcs();
		for (int i=0; i<arcsCourt.size() ; i++)
		{
			
			ShortestPathData dataCourt = new ShortestPathData(graph,graph.get(debut),arcsCourt.get(i).getDestination(),arcInspector1);
			ShortestPathSolution solutionCourt =new DijkstraAlgorithm(dataCourt).run();
			tempsCourt = (solutionCourt.getPath().getMinimumTravelTime() + temps1);
			if (tempsCourt<Min) 
			{
				minFinal=arcsCourt.get(i).getDestination().getId();
				Min=tempsCourt;
				
			}
			
		}
		if (Min==D1.getPath().getMinimumTravelTime() + D2.getPath().getMinimumTravelTime())
		{
			System.out.println("Solution non blabla");
		}
		else
		{
			System.out.println("U1 part de :"+ debut1 + " U2 part de : " + debut2);
			System.out.println("Chemin principal au départ de "+ debut);
			System.out.println("Le point de rencontre est "+ minFinal);
			System.out.println("La destination est : " +fin);
		}
	}
	
	@Test
	public void	trouveSolution() throws IOException {
	SolutionPb1(79911,110866,90028,"haute-garonne");	// 73071
		
	}

}
