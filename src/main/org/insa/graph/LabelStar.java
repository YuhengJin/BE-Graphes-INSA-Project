package org.insa.graph;

public class LabelStar extends Label {

	private double coutStar;
	
	public LabelStar(double cost, Node father,Node node,double coutStar) {

		super(cost,father,node);
		this.coutStar=coutStar;
		
	}
	
	public double getTotalCost()
	{
		double cost = this.getCost() + coutStar;
		return cost;
	}
}
