package hr.fer.zemris.ooup.lab3.editor.plugins;

import java.util.List;

import javax.swing.JOptionPane;

import hr.fer.zemris.ooup.lab3.editor.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.UndoManager;

public class Statistics implements Plugin {

	@Override
	public String getName() {
		return "Statistics";
	}

	@Override
	public String getDescription() {
		return "Counts number of lines, words and letters in document";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
		List<String> lines = model.getLines();
		
		int numberOfLines=0;
		int numberOfWords=0;
		int numberOfLetters=0;
		
		if(lines!=null){
			numberOfLines=lines.size();
			String[] words;
			for(String line:lines){
				words=line.split("\\s+");
				for(String word: words){
					if(!word.isEmpty()){
						numberOfWords++;
						numberOfLetters+=word.length();
					}
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "Number of lines: "+numberOfLines+
				"\nNumber of words: "+numberOfWords+"\nNumber of letters: "+numberOfLetters, "Stats", JOptionPane.INFORMATION_MESSAGE);
	}

}
