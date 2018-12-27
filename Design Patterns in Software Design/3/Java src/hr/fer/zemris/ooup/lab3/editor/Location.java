package hr.fer.zemris.ooup.lab3.editor;

public class Location {
	private int x;
	private int y;

	public Location(){
		x=0;
		y=0;
	}
	
	public Location(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	public Location(Location l){
		x=l.x;
		y=l.y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setLocation(Location location){
		this.x=location.getX();
		this.y=location.getY();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Location) {
			Location location = (Location)obj;
			if(this.x == location.getX() && this.y == location.getY()) {
				return true;
			}
		}
		return false;
	}
}
