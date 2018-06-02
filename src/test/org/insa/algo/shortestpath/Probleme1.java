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
		int debutprincipal=0;
		int debut=0;
		int minFinal1= 0;
		int minFinal2= 0;
		int minFinal= 0;
		
		// A la première version de l'algorithme, l'algorithme cherchait à connecter le chemin le plus Long au plus Court en temps.
		// Maintenant on ne soucie pas de la longueur, on teste toutes les possibilités de connexion entre les deux chemins optimaux pour être sur
		// de ne manquer aucune solution. ( Je laisse le if du dessous au cas ou je voudrai revenir à l'ancienne version de l'algorithme ).
		if (D1.getPath().getMinimumTravelTime()<D2.getPath().getMinimumTravelTime()) { court = D1; plusLong=D2; debut=debut2; debutprincipal=debut1; }
		else { court = D2; plusLong=D1; debut=debut1; debutprincipal=debut2;}
		
		double tempsCourt=0;
		double tempsLong=0;
		double temps1=court.getPath().getMinimumTravelTime();
		double temps2=plusLong.getPath().getMinimumTravelTime();
		double Min = D1.getPath().getMinimumTravelTime() + D2.getPath().getMinimumTravelTime();
		double MinCourt=Min;
		double MinLong=Min;
		List<Arc> arcsCourt = court.getPath().getArcs();
		List<Arc> arcsLong = plusLong.getPath().getArcs();
		for (int i=0; i<arcsCourt.size() ; i++)
		{
			
			ShortestPathData dataCourt = new ShortestPathData(graph,graph.get(debut),arcsCourt.get(i).getDestination(),arcInspector1);
			ShortestPathSolution solutionCourt =new DijkstraAlgorithm(dataCourt).run();
			tempsCourt = (solutionCourt.getPath().getMinimumTravelTime() + temps1);
			if (tempsCourt<MinCourt) 
			{
				minFinal1=arcsCourt.get(i).getDestination().getId();
				MinCourt=tempsCourt;
				
			}
			
		}
		for (int j=0; j<arcsLong.size() ; j++)
		{
			
			ShortestPathData dataLong = new ShortestPathData(graph,graph.get(debutprincipal),arcsLong.get(j).getDestination(),arcInspector1);
			ShortestPathSolution solutionLong =new DijkstraAlgorithm(dataLong).run();
			tempsLong = (solutionLong.getPath().getMinimumTravelTime() + temps2);
			if (tempsLong<MinLong) 
			{
				minFinal2=arcsLong.get(j).getDestination().getId();
				MinLong=tempsCourt;
				
			}
			
		}
		if (MinCourt==Min && MinLong==Min)
		{
			System.out.println("Solution non blabla");
		}
		else
		{
			if (MinLong>MinCourt)
			{
				Min=MinCourt;
				minFinal=minFinal1;
			}
			else
			{
				Min=MinLong;
				minFinal=minFinal2;
			}
			System.out.println("U1 part de :"+ debut1 + " U2 part de : " + debut2);
			System.out.println("Chemin principal au départ de "+ debutprincipal);
			System.out.println("Le point de rencontre est "+ minFinal);
			System.out.println("La destination est : " +fin);
			System.out.println("Duree d'utilisation des véhicules : " +Min);
		}
	}
	
	//Au vue des différents tests, il semble que la solution optimale soit toujours de connecter le chemin le plus
	//long en temps au chemein le plus court en temps. Si c'est bien le cas, une de mes boucles for ne sert à rien.
	@Test
	public void	trouveSolution() throws IOException {
	SolutionPb1(87114,72579,14166,"haute-garonne");	// 73071
		
	}

}
