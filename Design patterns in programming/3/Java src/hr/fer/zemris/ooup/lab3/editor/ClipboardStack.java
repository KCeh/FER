package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab3.editor.observer.ClipboardObserver;

public class ClipboardStack {
	private Stack<String> texts;
	private List<ClipboardObserver> clipboardObservers;
	
	public ClipboardStack(){
		texts=new Stack<String>();
		clipboardObservers = new ArrayList<ClipboardObserver> ();
	}
	
	public void addText(String text){
		texts.push(text);
		notifyClipboardObservers();
	}
	
	public String removeText(){
		String text = texts.pop();
		notifyClipboardObservers();
		return text;
	}
	
	public String getText(){
		return texts.peek();
	}
	
	public boolean hasText(){
		return !texts.isEmpty();
	}
	
	public void removeAll(){
		texts.clear();
		notifyClipboardObservers();
	}
	
	public void addClipboardObserver(ClipboardObserver observer) {
		clipboardObservers.add(observer);
	}
	
	public void removeClipobardObserver(ClipboardObserver observer) {
		clipboardObservers.remove(observer);
	}
	
	private void notifyClipboardObservers() {
		for (ClipboardObserver clipboardObserver : clipboardObservers) 
			clipboardObserver.updateClipboard();
	}
	
	
}
