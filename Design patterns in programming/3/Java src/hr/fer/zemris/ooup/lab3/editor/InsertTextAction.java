package hr.fer.zemris.ooup.lab3.editor;

import java.util.LinkedList;
import java.util.List;

public class InsertTextAction implements EditAction{
	private TextEditorModel model;
	
	private LocationRange selectionRange;
	private List<String> lines;
	private Location cursorLocation;
	
	private LocationRange previousSelectionRange;
	private List<String> previousLines;
	private Location previousCursorLocation;
	
	private char c;
	private String text;
	
	public InsertTextAction(TextEditorModel model, char c){
		this(model);
		this.c=c;
	}
	
	public InsertTextAction(TextEditorModel model, String text){
		this(model);
		this.text=text;
	}
	
	public InsertTextAction(TextEditorModel model) {
		this.model=model;
	}


	@Override
	public void executeDo() {
		selectionRange=model.getSelectionRange();
		lines=model.getLines();
		cursorLocation=model.getCursorLocation();
		
		
		/*
		 * moze bolje da detaljnije pamti stanje
		 */
		previousLines = new LinkedList<String>(lines);
		previousCursorLocation = new Location(cursorLocation);
		if(selectionRange!=null)
			previousSelectionRange = new LocationRange(model.getSelectionRange());
		
		if(selectionRange!=null){
			model.deleteRange();
		}
		
		if(text == null){
			if(String.valueOf(c).equals("\n")){
				//System.out.println("INSERT ENTER");
				List<String> newLines=new LinkedList<String>();
				Iterator<String> iterator = model.allLines();
				
				int i=0;
				String line;
				while(iterator.hasNext()){
					line=lines.get(i);
					
					if(i==cursorLocation.getY()){
						newLines.add(line.substring(0,cursorLocation.getX()));
						newLines.add(line.substring(cursorLocation.getX()));
						line = iterator.next();
						i++;
						continue;
					}
					
					newLines.add(line);
					i++;
					line = iterator.next();
				}
				
				model.setLines(newLines);
				cursorLocation.setX(0);
				cursorLocation.setY(cursorLocation.getY()+1);
			}else{
				StringBuilder sb = new StringBuilder(lines.get(cursorLocation.getY()));
				sb.insert(cursorLocation.getX(), c);
				lines.set(cursorLocation.getY(), sb.toString());
				model.moveCursorRight();
			}
		}else{
			int x = cursorLocation.getX();
			int y = cursorLocation.getY();
			
			String[] newText = text.split(System.lineSeparator());
			String line = lines.get(y);
			String beforeInsert = line.substring(0, x);
			String afterInsert = line.substring(x);
			
			if(newText.length>1){
				lines.set(y, beforeInsert+newText[0]);
				
				List<String> newLines=new LinkedList<String>();
				Iterator<String> iterator = model.allLines();
				
				int i=0;
				while(iterator.hasNext()){
					line=lines.get(i);
					
					if(i==y){
						newLines.add(line);
						
						for(int j=1; j<newText.length-1;j++){
							newLines.add(newText[j]);
						}
						
						newLines.add(newText[newText.length-1]+afterInsert);
						line = iterator.next();
						i++;
						continue;
					}
					
					newLines.add(line);
					i++;
					line = iterator.next();
				}
				
				model.setLines(newLines);
				cursorLocation.setX(newText[newText.length-1].length());
				cursorLocation.setY(cursorLocation.getY()+newText.length-1);
			}else{
				lines.set(y, beforeInsert+newText[0]+afterInsert);
				cursorLocation.setX(x+newText[0].length());
			}
		}
		
		model.notifyCursorObservers();
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
