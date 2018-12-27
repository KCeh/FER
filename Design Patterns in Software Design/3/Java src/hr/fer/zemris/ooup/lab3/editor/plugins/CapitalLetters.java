package hr.fer.zemris.ooup.lab3.editor.plugins;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.ooup.lab3.editor.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.EditAction;
import hr.fer.zemris.ooup.lab3.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.UndoManager;

public class CapitalLetters implements Plugin, EditAction{
	
	private List<String> previousLines;
	private TextEditorModel model;

	@Override
	public String getName() {
		return "Capital letters";
	}

	@Override
	public String getDescription() {
		return "Make starting letter in each word uppercase";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
		this.model=model;
		executeDo();
		undoManager.push(this);
		
		
	}

	@Override
	public void executeDo() {
		List<String> lines = model.getLines();
		previousLines = new LinkedList<String>(lines);
		
		String[] words;
		String line;
		String upper;
		String rest;
		
		for(int i = 0; i<lines.size();i++){
			words=lines.get(i).split("\\s+");
			line="";
			for(String word: words){
				if(!word.isEmpty()){
					upper=word.substring(0, 1).toUpperCase();
					rest=word.substring(1, word.length());
					line+=upper+rest+" ";
				}
			}
			line.trim();
			lines.set(i, line);
		}
		model.notifyTextObservers();
	}

	@Override
	public void executeUndo() {
		model.setLines(previousLines);
		model.notifyTextObservers();
		
	}
}

