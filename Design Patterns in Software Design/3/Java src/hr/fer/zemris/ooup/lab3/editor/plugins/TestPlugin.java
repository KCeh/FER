package hr.fer.zemris.ooup.lab3.editor.plugins;

import javax.swing.JOptionPane;

import hr.fer.zemris.ooup.lab3.editor.ClipboardStack;
import hr.fer.zemris.ooup.lab3.editor.TextEditorModel;
import hr.fer.zemris.ooup.lab3.editor.UndoManager;

public class TestPlugin implements Plugin{

	@Override
	public String getName() {
		return "TEST";
	}

	@Override
	public String getDescription() {
		return "TEST";
	}

	@Override
	public void execute(TextEditorModel model, UndoManager undoManager, ClipboardStack clipboardStack) {
		JOptionPane.showMessageDialog(null, "Testing...", "Test", JOptionPane.INFORMATION_MESSAGE);
	}

}
