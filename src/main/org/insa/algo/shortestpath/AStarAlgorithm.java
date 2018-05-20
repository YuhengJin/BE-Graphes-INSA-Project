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
		int Mode=0;
		if (data.getMode().equals("TIME"))
			Mode=1;
		for ( int i=0; i<nbNodes; i++)
	    {
	        labelList[i]=new LabelStar(Double.POSITIVE_INFINITY,null,nodes.next(),data.getDestination(),Mode,graph.getGraphInformation().getMaximumSpeed());
	    }
	    labelList[data.getOrigin().getId()] = new LabelStar(0,null,data.getOrigin(),data.getDestination(),Mode,graph.getGraphInformation().getMaximumSpeed());
	    return labelList;    
	}
	
	
	public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }

}
