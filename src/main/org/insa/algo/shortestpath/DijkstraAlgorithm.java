package org.insa.algo.shortestpath;
import java.lang.Math.*;
import java.util.*;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.EmptyPriorityQueueException;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	
	public double ValeurMin (double a, double b)  {
		if(a<=b) {
			return a;
		}else {
			return b;
		}
	}

    public DijkstraAlgorithm(ShortestPathData data) throws EmptyPriorityQueueException {
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
        for ( int i=0; i<nbNodes; i++)
        {
        labelList[i]=new Label(Double.POSITIVE_INFINITY,null,nodes.next());
        }
        labelList[data.getOrigin().getId()] = new Label(0,null,data.getOrigin());
        BinaryHeap<Node> tas = new BinaryHeap<Node>();
        
        tas.insert(data.getOrigin());
        
        // Algorithme
        
        int cmp = 0;
        while(tas.size()!=0)
        {
        	double costNodeMin=0;
        	
        	System.out.println(cmp);
        	System.out.println(tas.size());
        	cmp++;
        	
        	Node nodeMin = tas.deleteMin();
        	notifyNodeMarked(nodeMin);
        	for(Label label : labelList) {
        		if(label.getNode().equals(nodeMin))
        		{
        			label.setMark();
        			costNodeMin=label.getCost();
        			break;
        		}
        	}
        	for ( Arc arc : nodeMin)
        	{
    			
        		Node nodeSuiv = arc.getDestination();
             	for(Label label : labelList) {
            		if(label.getNode().equals(nodeSuiv))
            		{	double costPre = label.getCost();
            			if (label.getMark()==false)
            			{
            				
            				label.setCost(ValeurMin(costPre,costNodeMin + data.getCost(arc)));
            			}
            			if(label.getCost() != costPre ) {
            				tas.insert(label.getNode());
            				label.setFather(nodeMin);
            			}

            			
            			}
            				
            		}
        		
        	}
        	
        	
        	
        }
        // Recouvrement du chemin
        Node nodedest = data.getDestination();
        Node currentNode = nodedest ;
        ArrayList<Node> nodesFinal = new ArrayList<Node>();
        Node nodeorigin =data.getOrigin();
        
        while(currentNode.equals(nodeorigin)==false)
        {
        	for(Label label : labelList) {
        		if(label.getNode().equals(currentNode))
        		{
        			nodesFinal.add(currentNode);
        			currentNode=label.getFather();
        			break;
        		}
        	}
        }
        nodesFinal.add(nodeorigin);
        Collections.reverse(nodesFinal);
        // Create the final solution.
     	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(graph,nodesFinal));
     		
        
        return solution;
    }

}
