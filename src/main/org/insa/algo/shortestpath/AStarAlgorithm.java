package org.insa.algo.shortestpath;

import java.util.Iterator;

import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Graph;
import org.insa.graph.Label;
import org.insa.graph.LabelStar;
import org.insa.graph.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	public LabelStar[] initList() // Fonction pour initialiser la liste des labels initiaux que l'on pourra redéfinir pour A*
	{
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		final int nbNodes = graph.size();
		LabelStar[] labelList = new LabelStar[nbNodes];
		Iterator<Node> nodes = graph.iterator();
		int speed=graph.getGraphInformation().getMaximumSpeed();
		if (data.getMode().name()=="TIME")
		{
			for ( int i=0; i<nbNodes; i++)
	    	{
			Node currentNode = nodes.next();
	        labelList[i]=new LabelStar(Double.POSITIVE_INFINITY,null,currentNode,(currentNode.getPoint().distanceTo(data.getDestination().getPoint())/(speed/3.6)));
	    	}
			Node debut = data.getOrigin();
			labelList[data.getOrigin().getId()] = new LabelStar(0,null,debut,debut.getPoint().distanceTo(data.getDestination().getPoint())/(speed/3.6));
		}
		else
		{
			
			for ( int i=0; i<nbNodes; i++)
	    	{
			Node currentNode = nodes.next();
	        labelList[i]=new LabelStar(Double.POSITIVE_INFINITY,null,currentNode,(currentNode.getPoint().distanceTo(data.getDestination().getPoint())));
	    	}
		}
		
		Node debut = data.getOrigin();
		labelList[data.getOrigin().getId()] = new LabelStar(0,null,debut,debut.getPoint().distanceTo(data.getDestination().getPoint()));
	    return labelList;    
	}
	
	
	public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
