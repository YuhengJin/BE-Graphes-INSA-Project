package org.insa.algo.shortestpath;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.algo.ArcInspector;
import org.insa.algo.ArcInspectorFactory;
import org.insa.graph.Graph;
import org.insa.graph.Path;
import org.insa.graph.io.BinaryGraphReader;
import org.insa.graph.io.GraphReader;
import org.insa.graph.io.PathReader;
import org.insa.graphics.drawing.Drawing;
import org.insa.graphics.drawing.components.BasicDrawing;

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
	public static ShortestPathSolution SolutionPb1(int debut1,int debut2,int fin,String map) throws IOException { // Point de départ et d'arrivé identiques
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
		ShortestPathSolution minFinal = null;
		if (D1.getPath().getMinimumTravelTime()<D2.getPath().getMinimumTravelTime()) { court = D1; plusLong=D2; debut=debut2; }
		else { court = D2; plusLong=D1; debut=debut1;}
		double tempsCourt=0;
		double temps1=court.getPath().getMinimumTravelTime();
		double Min = D1.getPath().getMinimumTravelTime() + D2.getPath().getMinimumTravelTime();
		for (int i=0; i<court.getPath().getGraph().size() ; i++)
		{
			
			ShortestPathData dataCourt = new ShortestPathData(graph,graph.get(debut),graph.get(court.getPath().getGraph().get(i).getId()),arcInspector1);
			ShortestPathSolution solutionCourt =new DijkstraAlgorithm(dataCourt).run();
			tempsCourt = (solutionCourt.getPath().getMinimumTravelTime() + temps1);
			if (tempsCourt<Min) 
			{
				minFinal=solutionCourt;
				Min=tempsCourt;
				
			}
			
		}
		
		return minFinal ;
		
	}
	
	public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
    	ShortestPathSolution sol = SolutionPb1(50,70, 120,"insa");

        // TODO: Read the graph.
        Graph graph = sol.getPath().getGraph();

        // Create the drawing:
        Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.

        // TODO: Create a PathReader.
        PathReader pathReader = null;

        // TODO: Read the path.
        Path path = sol.getPath();

        // TODO: Draw the path.

    }
}
