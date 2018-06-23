package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.List;

public class DeleteRangeAction implements EditAction {
	private TextEditorModel model;
	
	private LocationRange selectionRange;
	private List<String> lines;
	private Location cursorLocation;
	
	private LocationRange previousSelectionRange;
	private List<String> previousLines;
	private Location previousCursorLocation;

	public DeleteRangeAction(TextEditorModel model) {
		this.model=model;
	}

	@Override
	public void executeDo() {
		selectionRange=model.getSelectionRange();
		lines=model.getLines();
		cursorLocation=model.getCursorLocation();
		previousLines = new ArrayList<String>(lines);
		previousCursorLocation = new Location(cursorLocation);
		previousSelectionRange = new LocationRange(model.getSelectionRange());

		Location start = selectionRange.getStart();
		Location end = selectionRange.getEnd();
		int numbOfLines= end.getY()-start.getY();
		
		if(cursorLocation.equals(end)) {
			cursorLocation.setLocation(start);
		}
		
		if(numbOfLines>0){
			int i=start.getY()+1;
			while(numbOfLines>1){
				lines.remove(i);
				numbOfLines--;
			}
			String newLine=lines.get(start.getY()).substring(0, start.getX()) + lines.get(start.getY() + 1).substring(end.getX());
			lines.set(start.getY(), newLine);
			lines.remove(start.getY()+1);
		}else{
			StringBuilder sb = new StringBuilder(lines.get(start.getY()));
			sb.delete(start.getX(), end.getX());
			lines.set(start.getY(), sb.toString());
		}
		
	
		
		model.notifyCursorObservers();
		model.setSelectionRange(null);
		model.notifyTextObservers();
	}

	@Override
	public void executeUndo() {
		model.setLines(previousLines);
		model.setCursorLocation(previousCursorLocation);
		model.setSelectionRange(previousSelectionRange);
		model.notifyCursorObservers();
		model.notifyTextObservers();
	}

}
