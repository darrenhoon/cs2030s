Circle createUnitCircle(Point p, Point q) {
	Point mp = p.midPoint(q);
	double theta =p.angleTo(q);
	double length_pm = p.distbet(mp);
	double length_mc = Math.sqrt(1 - length_pm*length_pm);
	Point centre = mp.moveTo(theta + Math.PI/2, length_mc);
	Circle c = new Circle(centre,1);
	return c;
}

int findMaxDiscCoverage(Point[] points){
	int maxDiscCoverage = 0;
	int i;
	for(i=0; i<points.length -1;i++){
		for(int j=i+1;j<points.length;j++){
			int num_points = (createUnitCircle(points[i], points[j])).countCoverage(points);
			if (num_points>maxDiscCoverage){maxDiscCoverage = num_points;
			}
		}
	}
	return maxDiscCoverage;
}

