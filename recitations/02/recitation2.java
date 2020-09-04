public class Point{
	private final double x;
	private final double y;

	public Point(double x, double y){
		this.x=x;
		this.y=y;
	}
}

public class Circle{
	final Point centre;
	final int radius;

	public Circle(Point p, int radius){
		this.centre =p;
		this.radius = radius;
	}

	@Override
	public boolean equals(Object obj){
		System.out.println("equals(Object) called! ");
		if(obj==this){
			System.out.println("Same object!");
			return true;
		}
		if(obj instanceof Circle){
			System.out.println("object is an instance of Circle called!");
			Circle circle = (Circle) obj;
			return (circle.centre.equals(centre) && circle.radius==radius);
		}
		else{
			System.out.println("input parameter is not an object or is not equal at all! >:(");
			return false;
		}
	}
	public boolean equals(Circle circle){
		System.out.println("equals(Circle) called");
		return (circle.centre.equals(this.centre) && circle.radius==this.radius);
	}
}

public class Rectangle{
	private double height;
	private double base;
	private double area;
	private double perimeter;

	public Rectangle(double height,double base){
		this.height=height;
		this.base=base;
		this.area = height*base;
		this.perimeter = 2*height + 2*base;
	}

	public String toString(){
		String message = String.format("height is: %.2f and width is: %.2f",this.height,this.base);
		return message;
	}

	public Rectangle scaleBy(double factor){
		return new Rectangle(this.height*factor,this.base*factor);
	}

	public Rectangle setHeight(double height){
		return new Square(height);
	}
}

class Square extends Rectangle{
	Square(double length){
		super(length,length);
	}

	public Square setHeight(double height){
		return new Square(height);
	}
}
