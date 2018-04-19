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
        
        // Notify observers about the first event (origin processed).
     	notifyOriginProcessed(data.getOrigin());
        
        BinaryHeap<Label> tas = new BinaryHeap<Label>();
        
        tas.insert(labelList[data.getOrigin().getId()]);
        
        // Algorithme
        
        int cmp = 0;
        while(tas.size()!=0 && Label.getNbMark()<nbNodes)
        {
        	double costNodeMin=0;
        	
        	System.out.println(cmp);
        	System.out.println(tas.size());
        	cmp++;
        	
        	Label nodeMin = tas.deleteMin();
        	notifyNodeMarked(nodeMin.getNode());
        	nodeMin.setMark();
        	costNodeMin=nodeMin.getCost();
        	for ( Arc arc : nodeMin.getNode())
        	{
    			
        		int nodeSuiv = arc.getDestination().getId();
             
            	double costPre = labelList[nodeSuiv].getCost();
            	if (labelList[nodeSuiv].getMark()==false)
            	{
            				
           			labelList[nodeSuiv].setCost(ValeurMin(costPre,costNodeMin + data.getCost(arc)));
            			
           			if(labelList[nodeSuiv].getCost() != costPre ) {
           					tas.insert(labelList[nodeSuiv]);
          					labelList[nodeSuiv].setFather(nodeMin.getNode());
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
