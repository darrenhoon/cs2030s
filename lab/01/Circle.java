class Circle{
	Point centre;
	private final double radius;

	Circle(Point p, double radius){
		this.centre = p;
		this.radius = radius;
	}
	boolean contains(Point q){
		return this.centre.distbet(q)<=this.radius;
	}



	public String toString(){
		return "circle of radius " + this.radius+ " centered at " +this.centre;
	}
	int countCoverage(Point[] arr){
		int counter = 0;
		for(Point p: arr){
			if(this.contains(p)){
				counter++;
			}
		}
		return counter;
	}


}

