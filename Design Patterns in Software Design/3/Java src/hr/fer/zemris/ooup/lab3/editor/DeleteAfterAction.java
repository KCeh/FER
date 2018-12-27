package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.List;

public class DeleteAfterAction implements EditAction {
private TextEditorModel model;
	
	private List<String> lines;
	private Location cursorLocation;
	
	private List<String> previousLines;
	private Location previousCursorLocation;

	public DeleteAfterAction(TextEditorModel model) {
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
		if(x==lines.get(y).length()){
			if(lines.size()<=1){
				return;
			}else if(y<lines.size()-1){
				lines.set(y, lines.get(y)+lines.get(y+1));
				lines.remove(lines.get(y+1));
			}
		}else{
			StringBuilder sb = new StringBuilder(lines.get(y));
			sb.deleteCharAt(x);
			lines.set(y, sb.toString());
		}
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
