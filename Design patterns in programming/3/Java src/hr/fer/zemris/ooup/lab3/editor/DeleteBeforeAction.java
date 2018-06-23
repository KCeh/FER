package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.List;

public class DeleteBeforeAction implements EditAction {
	private TextEditorModel model;
	
	private List<String> lines;
	private Location cursorLocation;
	
	private List<String> previousLines;
	private Location previousCursorLocation;

	public DeleteBeforeAction(TextEditorModel model) {
		this.model=model;
	}

	@Override
	public void executeDo() {
		lines=model.getLines();
		cursorLocation=model.getCursorLocation();
		previousLines = new ArrayList<String>(lines);
		previousCursorLocation = new Location(cursorLocation);
		
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		if(x==0){
			if(y==0){
				return;
			}else{
				lines.set(y-1, lines.get(y-1)+lines.get(y));
				lines.remove(y);
			}
		}else{
			StringBuilder sb = new StringBuilder(lines.get(y));
			sb.deleteCharAt(x-1);
			lines.set(y, sb.toString());
		}
		model.moveCursorLeft();
		model.notifyTextObservers();

	}

	@Override
	public void executeUndo() {
		model.setLines(previousLines);
		model.setCursorLocation(previousCursorLocation);
		model.notifyCursorObservers();
		model.notifyTextObservers();

	}

}
