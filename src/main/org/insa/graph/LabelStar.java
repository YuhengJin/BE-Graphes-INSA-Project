package org.insa.graph;

public class LabelStar extends Label {

	private double coutStar;
	private static Node dest;
	private static double speed;
	private static int mode;
	
	public LabelStar(double cost, Node father,Node node) {

		super(cost,father,node);
		this.coutStar=0;
		
	}
	
	public static void setSpeed(double vit)
	{
		speed=vit;
	}
	
	public static void setDest(Node desti)
	{
		dest=desti;
	}
	
	public static void setMode(int type)
	{
		mode=type;
	}
	
	public double getTotalCost()
	{
		if (mode==-1 && this.coutStar==0)
		{
			this.coutStar = this.getNode().getPoint().distanceTo(dest.getPoint())/(speed/3.6);
			double cost = this.getCost() + this.coutStar;
			return cost;
			
		}
		if (mode==-2 &&  this.coutStar==0)
		{
			this.coutStar = this.getNode().getPoint().distanceTo(dest.getPoint());
			double cost = this.getCost() + this.coutStar;
			return cost;
			
		}
		double cost= this.getCost() + this.coutStar;
		return cost;
	}
}
