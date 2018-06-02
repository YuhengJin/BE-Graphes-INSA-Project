package org.insa.algo.shortestpath;

import java.util.Iterator;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	public LabelStar[] initList() // Redefinition de la fonction contenue dans la classe Dijkstra
	{
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		final int nbNodes = graph.size();
		LabelStar[] labelList = new LabelStar[nbNodes];
		Iterator<Node> nodes = graph.iterator();
		LabelStar.setDest(data.getDestination());
		LabelStar.setSpeed(graph.getGraphInformation().getMaximumSpeed());
		if (data.getMode().name()=="TIME")
		{
			LabelStar.setMode(-1);
			
		}
		else 
		{
			LabelStar.setMode(-2);
		}	
		for ( int i=0; i<nbNodes; i++)
	    {
		Node currentNode = nodes.next();
	    labelList[i]=new LabelStar(Double.POSITIVE_INFINITY,null,currentNode);
	    }
		Node debut = data.getOrigin();
		labelList[data.getOrigin().getId()] = new LabelStar(0,null,debut);
		
	    return labelList;    
	}
	
	
	public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
