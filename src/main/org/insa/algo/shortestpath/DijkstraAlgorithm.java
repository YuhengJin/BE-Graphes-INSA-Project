package org.insa.algo.shortestpath;
import java.util.*;


import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.ElementNotFoundException;
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
	
	public Label[] initList() // Fonction pour initialiser la liste des labels initiaux que l'on pourra redéfinir pour A*
	{
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		final int nbNodes = graph.size();
		Label[] labelList = new Label[nbNodes];
		Iterator<Node> nodes = graph.iterator();
		for ( int i=0; i<nbNodes; i++)
	    {
	        labelList[i]=new Label(Double.POSITIVE_INFINITY,null,nodes.next());
	    }
	    labelList[data.getOrigin().getId()] = new Label(0,null,data.getOrigin());
	    return labelList;    
		
		
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
      

        
        ArrayList<Node> nodesFinal = new ArrayList<Node>();
        Node nodeorigin =data.getOrigin();
        
        
        // Initialisation
        Label[] labelList=initList();
        // Notify observers about the first event (origin processed).
     	notifyOriginProcessed(data.getOrigin());
       
     	// Initialisation du tas
     	BinaryHeap<Label> tas = new BinaryHeap<Label>();
        tas.insert(labelList[data.getOrigin().getId()]);
        // On verifie que le départ ne soit pas égal à l'arrivée
        if (data.getOrigin().getId()==nodedest.getId())
        {
        	
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        	return solution;
        }
        
        // Algorithme
        int cmp = 0;
        while(tas.size()!=0)
        {
        	double costNodeMin=0;
        	
        	cmp++;
        	
        	Label nodeMin = tas.deleteMin();
        	notifyNodeMarked(nodeMin.getNode());
        	nodeMin.setMark();
        	costNodeMin=nodeMin.getCost();
        	for ( Arc arc : nodeMin.getNode())
        	{	
    			if (data.isAllowed(arc))
    			{
        			int nodeSuiv = arc.getDestination().getId();
             
            		double costPre = labelList[nodeSuiv].getCost();
            		if (labelList[nodeSuiv].getMark()==false)
            		{
            			//labelList[nodeSuiv].setCost(ValeurMin(costPre,costNodeMin + data.getCost(arc)));
            			
            			if(costPre> costNodeMin + data.getCost(arc)) 
            			{
           					//if (labelList[nodeSuiv].getstatusTas()==1)
           					//{
           						//try {tas.remove(labelList[nodeSuiv]);}
           						//catch (ElementNotFoundException e) {}
           					//}
          					labelList[nodeSuiv].setCost(costNodeMin + data.getCost(arc));
           					labelList[nodeSuiv].setFather(nodeMin.getNode());
           					tas.insert(labelList[nodeSuiv]);
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
        if (data.getMode().name()=="LENGTH") {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(graph,nodesFinal));
        }
        else {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createFastestPathFromNodes(graph,nodesFinal));
        }
     		
        
        return solution;
    }
    
        
}
