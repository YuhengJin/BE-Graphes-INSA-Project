package org.insa.algo.shortestpath;
import java.util.*;


import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.EmptyPriorityQueueException;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	
	ShortestPathSolution solution = null;
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
        Node nodedest = data.getDestination();
        Graph graph = data.getGraph();
        final int nbNodes = graph.size();
        Iterator<Node> nodes = graph.iterator();
        Label[] labelList = new Label[nbNodes];
        
        ArrayList<Node> nodesFinal = new ArrayList<Node>();
        Node nodeorigin =data.getOrigin();
        
        
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
        while(tas.size()!=0)
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
    			if (data.isAllowed(arc)==true )
    			{
        			int nodeSuiv = arc.getDestination().getId();
             
            		double costPre = labelList[nodeSuiv].getCost();
            		if (labelList[nodeSuiv].getMark()==false)
            		{
            			labelList[nodeSuiv].setCost(ValeurMin(costPre,costNodeMin + data.getCost(arc)));
            			
            			if(labelList[nodeSuiv].getCost() != costPre ) 
            			{
           					tas.insert(labelList[nodeSuiv]);
          					labelList[nodeSuiv].setFather(nodeMin.getNode());
          					notifyNodeReached(arc.getDestination());
          				}
        			}
            				
           		}
	        	}
        	if (nodeMin.getNode().compareTo(nodedest)==0)
        	{
        		break;
        	}
	        		
        	}
        
        System.out.println(" -----FIN-BOUCLE------ ");
        
 
        int currentNode = data.getDestination().getId();
        // Recouvrement du chemin
        while(labelList[currentNode].getFather()!=null)
        {
 
        			nodesFinal.add(labelList[currentNode].getNode());
        			currentNode=labelList[currentNode].getFather().getId();
        	
        }
        if (labelList[currentNode].getNode().equals(data.getOrigin())==false)
        {
        	
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        	return solution;
        }
        // The destination has been found, notify the observers.
        notifyDestinationReached(data.getDestination());
        nodesFinal.add(nodeorigin);
        Collections.reverse(nodesFinal);
        tas=null;
        // Create the final solution.
        if (data.getMode().equals("LENGTH")) {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(graph,nodesFinal));
        }else {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createFastestPathFromNodes(graph,nodesFinal));
        }
     		
        
        return solution;
    }
    
        
}
