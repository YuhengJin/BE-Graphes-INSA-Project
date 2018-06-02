package org.insa.algo.shortestpath;
import java.util.*;


import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.ElementNotFoundException;
import org.insa.algo.utils.EmptyPriorityQueueException;
import org.insa.graph.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	

	
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

    @SuppressWarnings("deprecation")
	@Override
    protected ShortestPathSolution doRun() {
    	
    	//**INITIALISATION**//
        ShortestPathData data = getInputData();
        Node nodedest = data.getDestination();
        Graph graph = data.getGraph();
        int maxTas=0; 									      // Nb d'éléments max dans le tas
        int nbVisite=0;                                      //Nb de nodes visités
    	ShortestPathSolution solution = null;               // Création de la structure pour retourner la solution finale
        ArrayList<Node> nodesFinal = new ArrayList<Node>();//Création de la liste qui contiendra les nodes du chemin final trouvé
        Node nodeorigin =data.getOrigin();
        
        
        // Initialisation de la liste de label
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
        
        // ** ALGORITHME ** //
        while(tas.size()!=0)
        {
        	double costNodeMin=0;
        	if (tas.size()>maxTas) maxTas=tas.size();
        	
        	Label nodeMin = tas.deleteMin();
        	notifyNodeMarked(nodeMin.getNode());
        	nodeMin.setMark();
        	costNodeMin=nodeMin.getCost();
        	for ( Arc arc : nodeMin.getNode())
        	{	
    			nbVisite++;
        		if (data.isAllowed(arc)) //On verifie que l'on a bien le droit d'utiliser l'arc
    			{
        			int nodeSuiv = arc.getDestination().getId();
             
            		double costPre = labelList[nodeSuiv].getCost();
            		if (labelList[nodeSuiv].getMark()==false) //On verifie le node a été marqué ou non
            		{
            			
            			if(costPre> costNodeMin + data.getCost(arc)) 
            			{
          					labelList[nodeSuiv].setCost(costNodeMin + data.getCost(arc)); //On actualise le coût
           					labelList[nodeSuiv].setFather(nodeMin.getNode());			 // On met le père à jour
           					tas.insert(labelList[nodeSuiv]);							// On insert dans le tas
           					notifyNodeReached(arc.getDestination());	
           					
          				}
        			}
            				
           		}
	        	}
        	if (nodeMin.getNode().compareTo(nodedest)==0) //Si le dernier node visité est la destination on stoppe l'algo 
        	{
        		break;
        	}
	        		
        	}
        
        // Recouvrement du chemin
        int currentNode =nodedest.getId();
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
        
        nodesFinal.add(nodeorigin); // On ajoute l'origine à notre liste finale
        Collections.reverse(nodesFinal); // On inverse la liste
        // Renvoie des stats
        
        //System.out.println("TasMax :"+ maxTas);					//Pour tester les performances
        //System.out.println("Nb nodes visités : "+ nbVisite);	//Pour tester les performances
        // Create the final solution.
        if (data.getMode().name()=="LENGTH") { 
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createShortestPathFromNodes(graph,nodesFinal));
        }
        else {
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, Path.createFastestPathFromNodes(graph,nodesFinal));
        }
     	// On construit la solution en fonction du mode	
        
        return solution;
    }
    
        
}
