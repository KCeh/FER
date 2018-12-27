package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.ooup.lab3.editor.observer.StackObserver;

public class UndoManager {
	private Stack<EditAction> undoStack;
	private Stack<EditAction> redoStack;
	
	private static UndoManager instance;
	
	private List<StackObserver> undoStackObservers;
	private List<StackObserver> redoStackObservers;
	
	private UndoManager(){
		undoStack = new Stack<>();
		redoStack = new Stack<>();
		undoStackObservers = new ArrayList<StackObserver>();
		redoStackObservers = new ArrayList<StackObserver>();
	}
	
	public static UndoManager getInstance(){
		if(instance==null)
			instance=new UndoManager();
		return instance;
	}
	
	public void undo(){
		if(!undoStack.isEmpty()){
			EditAction action = undoStack.pop();
			action.executeUndo();
			if(undoStack.isEmpty())
				notifyUndoStackObservers(true);
			
			redoStack.push(action);
			notifyRedoStackObservers(false);
			return;
		}
		notifyUndoStackObservers(true);
	}
	
	public void redo(){
		if(!redoStack.isEmpty()){
			EditAction action = redoStack.pop();
			action.executeDo();
			if(redoStack.isEmpty())
				notifyRedoStackObservers(true);
			
			undoStack.push(action);
			notifyUndoStackObservers(false);
			return;
		}
		notifyRedoStackObservers(true);
	}
	
	public void push(EditAction c){
		redoStack.clear();
		undoStack.push(c);
		notifyUndoStackObservers(false);
		notifyRedoStackObservers(true);
	}
	
	public void addUndoStackObserver(StackObserver observer) {
		undoStackObservers.add(observer);
	}
	
	public void removeUndoStackObserver(StackObserver observer) {
		undoStackObservers.remove(observer);
	}
	
	private void notifyUndoStackObservers(boolean empty) {
		for (StackObserver observer : undoStackObservers) {
			observer.updateStackStatus(empty);
		}
	}
	
	public void addRedoStackObserver(StackObserver observer) {	
		redoStackObservers.add(observer);
	}
	
	public void removeRedoStackObserver(StackObserver observer) {
		redoStackObservers.remove(observer);
	}
	
	private void notifyRedoStackObservers(boolean empty) {
		for (StackObserver observer : redoStackObservers) {
			observer.updateStackStatus(empty);
		}
	}
	
}
