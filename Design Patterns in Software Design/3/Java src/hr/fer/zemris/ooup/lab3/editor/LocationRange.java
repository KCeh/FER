package hr.fer.zemris.ooup.lab3.editor;

public class LocationRange {
	private Location start;
	private Location end;
	
	public LocationRange(){
		start=new Location();
		end=new Location();
	}
	
	public LocationRange(LocationRange range){
		start=new Location(range.getStart());
		end=new Location(range.getEnd());
	}
	
	public Location getStart() {
		return start;
	}
	
	public void setStart(Location start) {
		this.start = start;
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
	}
	
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("START: ");
		sb.append(start.getX());
		sb.append(" ");
		sb.append(start.getY());
		
		sb.append(" END: ");
		sb.append(end.getX());
		sb.append(" ");
		sb.append(end.getY());
		
		return sb.toString();
	}
	
}
