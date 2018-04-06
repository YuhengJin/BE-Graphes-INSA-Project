package org.insa.algo.shortestpath;
import java.lang.Math.*;
import java.util.*;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        Iterator<Node> nodes = graph.iterator();
        Label[] labelList = new Label[nbNodes];
        
        // Initialisation
        
        Arrays.fill(labelList,new Label(Double.POSITIVE_INFINITY,0,nodes.next()));
        labelList[data.getOrigin().getId()] = new Label(0,0,data.getOrigin());
        BinaryHeap<Node> tas = new BinaryHeap<Node>();
        
        tas.insert(data.getOrigin());
        
        // Algorithme
        
        while(Label.getNbMark()<nbNodes)
        {
        	Node nodeMin = tas.deleteMin();
        	for(Label label : labelList) {
        		if(label.getNode().equals(nodeMin))
        		{
        			label.setMark();
        		}
        	}
        	for ( Arc arc : nodeMin)
        	{
        		Node nodeSuiv = arc.getDestination();
             	for(Label label : labelList) {
            		if(label.getNode().equals(nodeSuiv))
            		{
            			if (label.getMark()==false)
            			{
            				label.setCost(min(a,b));
            			}
            				
            		}
        		
        	}
        	
        	
        	
        }
        
        
        return solution;
    }

}
