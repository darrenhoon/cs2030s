interface Scalable{
	Scalable scaleBy(double factor);
}

abstract class Shape {
	abstract double getArea();
	abstract double getPerimeter();
}

class Rectangle extends Shape implements Scalable{
	private final double length;
	private final double breadth;
	Rectangle(double length, double breadth){
		this.length = length;
		this.breadth = breadth;
	}
	
	public String toString(){
		String message = String.format("Area is: %.2f, Perimeter is: %.2f",this.getArea(),this.getPerimeter();
		return message;
	}

	@Override
	public Rectangle scaleBy(double factor){
		return new Rectangle(this.length*factor, this.breadth*factor);
	}

	@Override
	public double getArea(){
		double value = this.length * this.breadth;
		return value;
	}

	@Override
	public double getPerimeter(){
		double value = this.length*2 + this.breadth*2;
		return value;
	}
}
