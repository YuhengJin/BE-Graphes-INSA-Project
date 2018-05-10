package org.insa.graph;

public class Label implements Comparable<Label>{

	private double cost;
	private Node father;
	private boolean mark;
	private Node node;
	private static int nbMark=0;

	public Label(double cost, Node father, Node node) {
		this.cost = cost;
		this.father = father;
		this.node=node;
		this.mark = false;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public void setMark() {
		this.mark = true;
		nbMark++;
	}

	public void setCost(double cost)
	{
		this.cost=cost;
	}
	public double getCost()
	{
		return this.cost;
	}
	

	public void setFather(Node n)
	{
		this.father=n;
	}
	public static int getNbMark()
	{
		return nbMark;
	}
	
	public boolean getMark() 
	{
		return this.mark;
	}
	
	public Node getFather()
	{
		return this.father;
	}

	@Override
	public int compareTo(Label label) {
		if  ( this.cost < label.cost )
			return -1;
		else
		{
			if  ( this.cost == label.cost )
				return 0;
			
			else 
				return 1;
			
		
		}
	}
	
	
}



