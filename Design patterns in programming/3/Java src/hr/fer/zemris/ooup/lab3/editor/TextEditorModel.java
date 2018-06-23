package hr.fer.zemris.ooup.lab3.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.ooup.lab3.editor.observer.CursorObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.TextObserver;

public class TextEditorModel {
	private List<String> lines;
	private LocationRange selectionRange;
	private Location cursorLocation;
	
	private List<CursorObserver> cursorObservers;
	private List<TextObserver> textObservers;
	
	private UndoManager undoManager; 
	
	public TextEditorModel(String text){
		lines=new LinkedList<String>(Arrays.asList(text.split("\n")));
		cursorLocation=new Location();
		cursorObservers=new ArrayList<CursorObserver>();
		textObservers=new ArrayList<TextObserver>();
		undoManager=UndoManager.getInstance();
	}

	public List<String> getLines() {
		return lines;
	}
	
	public String getLine(int index){
		return lines.get(index);
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public Location getCursorLocation() {
		return cursorLocation;
	}

	public void setCursorLocation(Location cursorLocation) {
		this.cursorLocation = cursorLocation;
	}
	
	public Iterator<String> allLines(){
		return new LinesIterator(lines);	
	}
	
	public Iterator<String> linesRange(int index1, int index2){
		return new LinesIterator(lines, index1, index2);
	}
	
	public void addCursorObserver(CursorObserver observer) {
		cursorObservers.add(observer);
	}
	
	public void removeCursorObserver(CursorObserver observer) {
		cursorObservers.remove(observer);
	}
	
	public void moveCursorLeft() {
		int x=cursorLocation.getX();
		if(x==0){
			int y=cursorLocation.getY();
			if(y!=0){
				cursorLocation.setX(lines.get(y-1).length());
				cursorLocation.setY(y-1);
			}else{
				return;
			}
		}
		else{
			cursorLocation.setX(cursorLocation.getX()-1);
		}
		notifyCursorObservers();
	}
	
	public void moveCursorRight() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		
		if(x==lines.get(y).length()){
			if(y!=lines.size()-1){
				cursorLocation.setX(0);
				cursorLocation.setY(y+1);
			}
			else{
				return;
			}
		}
		else{
			cursorLocation.setX(cursorLocation.getX()+1);
		}
		notifyCursorObservers();
	}
	
	public void moveCursorUp() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		if(y==0){
			cursorLocation.setX(0);
			notifyCursorObservers();
			return;
		}
		
		cursorLocation.setY(cursorLocation.getY()-1);
		String line=lines.get(y-1);
		if(x>line.length())
			cursorLocation.setX(line.length());
		notifyCursorObservers();
	}
	
	public void moveCursorDown() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		if(y==lines.size()-1){
			cursorLocation.setX(lines.get(y).length());
			notifyCursorObservers();
			return;
		}
		
		cursorLocation.setY(cursorLocation.getY()+1);
		String line=lines.get(y+1);
		if(x>line.length())
			cursorLocation.setX(line.length());
		notifyCursorObservers();
	}

	public void notifyCursorObservers() {
		for(CursorObserver observer:cursorObservers)
			observer.updateCursorLocation(cursorLocation);	
	}
	
	public void addTextObserver(TextObserver observer){
		textObservers.add(observer);
	}
	
	public void removeTextObserver(TextObserver observer){
		textObservers.remove(observer);
	}
	
	public void notifyTextObservers(){
		for(TextObserver observer:textObservers)
			observer.updateText();
	}
	
	public void deleteBefore(){
		EditAction action = new DeleteBeforeAction(this);
		action.executeDo();
		undoManager.push(action);
	}
	
	public void deleteAfter(){
		EditAction action = new DeleteAfterAction(this);
		action.executeDo();
		undoManager.push(action);
	}
	
	public void deleteRange(){
		EditAction action = new DeleteRangeAction(this);
		if(selectionRange==null) return;
		action.executeDo();
		undoManager.push(action);
	}
	
	public LocationRange getSelectionRange(){
		return selectionRange;
	}
	
	public void setSelectionRange(LocationRange range){
		selectionRange=range;
	}
	
	public void SelectionLeft() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		
		if(x==0){
			if(y==0){
				return;
			}else{
				//System.out.println("LEFT");
				if(selectionRange==null){
					selectionRange=new LocationRange();
					selectionRange.setEnd(new Location(x, y));
					moveCursorLeft();
					selectionRange.setStart(new Location(cursorLocation.getX(), cursorLocation.getY()));
				}else{
					moveCursorLeft();
					selectionRange.setStart(new Location(cursorLocation.getX(), cursorLocation.getY()));
				}
			}
		}else{
			if(selectionRange==null){//provjeri
				selectionRange=new LocationRange();
				selectionRange.setStart(new Location(x-1, y));
				selectionRange.setEnd(new Location(x, y));
			}else{
				selectionRange.setStart(new Location(x-1,y));

			}
			moveCursorLeft();
		}
		//debug
		//System.out.println(selectionRange);
		
		notifyTextObservers();
		
	}
	
	public void SelectionRight() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		
		if(x==lines.get(y).length()){
			if(y==lines.size()-1){
				return;
			}else{
				if(selectionRange==null){
					selectionRange=new LocationRange();
					selectionRange.setStart(new Location(x, y));
					moveCursorRight();
					selectionRange.setEnd(new Location(cursorLocation.getX(), cursorLocation.getY()));
				}else{
					moveCursorRight();
					selectionRange.setEnd(new Location(cursorLocation.getX(), cursorLocation.getY()));
				}
			}
		}else{
			if(selectionRange==null){//provjeri
				selectionRange=new LocationRange();
				selectionRange.setStart(new Location(x, y));
				selectionRange.setEnd(new Location(x+1, y));
			}else{
				selectionRange.setEnd(new Location(x+1,y));

			}
			moveCursorRight();
		}
		//debug
		//System.out.println(selectionRange);
		
		notifyTextObservers();
	}
	
	public void SelectionUp() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		
		//if(y==0) return;
		
		if(selectionRange==null){
			selectionRange=new LocationRange();
			selectionRange.setEnd(new Location(x, y));
			moveCursorUp();
			selectionRange.setStart(new Location(cursorLocation.getX(), cursorLocation.getY()));
		}else{
			moveCursorUp();
			selectionRange.setStart(new Location(cursorLocation.getX(), cursorLocation.getY()));
		}
		//debug
		//System.out.println(selectionRange);
		notifyTextObservers();
		
	}

	public void SelectionDown() {
		int x=cursorLocation.getX();
		int y=cursorLocation.getY();
		
		if(selectionRange==null){
			selectionRange=new LocationRange();
			selectionRange.setStart(new Location(x, y));
			moveCursorDown();
			selectionRange.setEnd(new Location(cursorLocation.getX(), cursorLocation.getY()));
		}else{
			moveCursorDown();
			selectionRange.setEnd(new Location(cursorLocation.getX(), cursorLocation.getY()));
		}
		//debug
		//System.out.println(selectionRange);
		notifyTextObservers();
	}
	
	public void insert(char c){
		EditAction action = new InsertTextAction(this, c);
		action.executeDo();
		undoManager.push(action);
	}
	
	public void insert(String text){ 
		EditAction action = new InsertTextAction(this, text);
		action.executeDo();
		undoManager.push(action);
	}
	
	//za akcije
	public String getSelectedText(){ 
		if(selectionRange==null) return "";
		
		Location start = selectionRange.getStart();
		Location end = selectionRange.getEnd();
		int numbOfLines=end.getY()-start.getY();
		
		if(numbOfLines==0){
			return lines.get(start.getY()).substring(start.getX(),end.getX());
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(lines.get(start.getY()).substring(start.getX())).append(System.lineSeparator());
		for (int i = start.getY() + 1; i < end.getY(); i++) {
			sb.append(lines.get(i)).append(System.lineSeparator());
		}
			
		sb.append(lines.get(end.getY()).substring(0, end.getX()));
			
		return sb.toString();
	}
	
	public void clearText() {
		EditAction action = new ClearDocumentAction(this);
		action.executeDo();
		undoManager.push(action);
	}

	public void moveCursorToStartOfDocumetn() {
		cursorLocation.setLocation(new Location(0,0));
		notifyCursorObservers();
	}

	public void moveCursorToEndOfDocument() {
		int size=lines.size();
		cursorLocation.setLocation(new Location(lines.get(size-1).length(),size-1));
		notifyCursorObservers();
	}

}
