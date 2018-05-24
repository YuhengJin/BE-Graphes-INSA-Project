package org.insa.graph;

public class LabelStar extends Label {

	private Node destination;
	private int Mode;
	private int Speed;
	
	public LabelStar(double cost, Node father, Node node,Node destination,int Mode,int Speed) {

		super(cost,father,node);
		this.destination=destination;	
		this.Mode = Mode;
		this.Speed=Speed;
	}
	
	public double getTotalCost()
	{
		if ( this.Mode ==0)
		{
		double cost = this.getCost() + this.getNode().getPoint().distanceTo(destination.getPoint());
		return cost;
		}
		else
		{
		if (this.Speed!=-1)
			{
			double cost = this.getCost() + (this.getNode().getPoint().distanceTo(destination.getPoint())/this.Speed/3.6);
			return cost;
			}
		else
			{
			return this.getCost();
			}
		}
	}
}
