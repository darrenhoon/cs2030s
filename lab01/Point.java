class Point{
	double x;
	double y;

	public boolean equals(Object obj){
		if (this==obj){return true;}
		else if(obj instanceof Point){
			Point p = (Point) obj;
			return (p.x == this.x) && (p.y ==this.y);
		}
		else{return false;}
	}

	Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	double getX(){return this.x;}
	double getY(){return this.y;}

	public String toString(){
        String message = String.format("point (%.3f, %.3f)", this.x,this.y);
        return message;
		}

	double distbet(Point q){
		double dx = this.x - q.x;
		double dy = this.y - q.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	Point midPoint(Point q){
		double mid_x = (this.x + q.getX())/2;
		double mid_y = (this.y + q.getY())/2;
		Point mid = new Point(mid_x,mid_y);
		return mid;
	}
	double angleTo(Point q){
		double opp = Math.abs(q.getY() - this.y);
		double adj = Math.abs(q.getX() - this.x);
		double theta = Math.atan(opp/adj);
        if(Double.isNaN(theta)){return 0.0;}
		if(q.getY()<this.y){
			if(q.getX()<this.x){
				theta = -(Math.PI-theta);
			}
			else{
				theta = -theta;
			}
		}
		else{
			if(q.getX()<this.x){
				theta = Math.PI- theta;
			}
		}
		return theta;
	}
	Point moveTo(double theta, double d){
		double centre_x = this.x + d*Math.cos(theta);
		double centre_y = this.y + d*Math.sin(theta);
		Point centre = new Point(centre_x, centre_y);
		return centre;
	}
}
