package org.insa.graph;

public class Label {

	private double cost;
	private int fatherId;
	private boolean mark;
	private Node node;
	private static int nbMark=0;

	public Label(double cost, int fatherId, Node node) {
		this.cost = cost;
		this.fatherId = fatherId;
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

	public void setFather(int fatherId)
	{
		this.fatherId=fatherId;
	}
	public static int getNbMark()
	{
		return nbMark;
	}
	
	public boolean getMark() 
	{
		return this.mark;
	}
	
	
	
}



