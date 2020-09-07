abstract class Shape {
	abstract double getArea();
	abstract double getPerimeter();

	@Override	
	public String toString(){
		String message = String.format("Area is: %.2f, Perimeter is: %.2f",this.getArea(),this.getPerimeter());
		return message;
	}
	
}
