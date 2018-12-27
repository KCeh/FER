package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.List;

public class ClearDocumentAction implements EditAction {
	private TextEditorModel model;
	
	private List<String> previousLines;
	private Location previousCursorLocation;
	
	public ClearDocumentAction(TextEditorModel model) {
		this.model=model;
	}

	@Override
	public void executeDo() {
		previousLines = new ArrayList<String>(model.getLines());
		previousCursorLocation = new Location(model.getCursorLocation());
		
		model.getLines().clear();
		model.getLines().add("");
		model.getCursorLocation().setLocation(new Location(0,0));
		
		model.notifyCursorObservers();
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
