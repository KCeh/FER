package hr.fer.zemris.ooup.lab3.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import hr.fer.zemris.ooup.lab3.editor.observer.ClipboardObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.CursorObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.StackObserver;
import hr.fer.zemris.ooup.lab3.editor.observer.TextObserver;
import hr.fer.zemris.ooup.lab3.editor.plugins.Plugin;

public class TextEditor extends JFrame{
	
	private static final int MARGIN = 4;
	private static short draw=0;
	
	private Path openedFile;
	private StatusLabel statusLabel;
	
	private static final long serialVersionUID = 1L;
	JPanel editorPanel = new EditorPanel();
	
	private TextEditorModel model;
	private ClipboardStack clipboardStack;
	private UndoManager undoManager;

	public TextEditor(){
		super("Text editor");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		model=new TextEditorModel("proba\n123\nproba2");
		clipboardStack=new ClipboardStack();
		undoManager=UndoManager.getInstance();
		addObservers();
		createActions();
		createMenuBar();
		createToolbar();
		initRestOfGUI();
		pack();
		setSize(800,600);
		setLocationRelativeTo(null);
		
	}

	private void addObservers() {
		int delay=500;
		ActionListener refresh=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editorPanel.revalidate();
				editorPanel.repaint();
			}

		} ;
		
		new Timer(delay, refresh).start();
		
		
		editorPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(e.isShiftDown()){
						model.SelectionLeft();
					}else{
						model.moveCursorLeft();
						model.setSelectionRange(null);	
					}
					break;

				case KeyEvent.VK_RIGHT:
					if(e.isShiftDown()){
						model.SelectionRight();
					}else{
						model.moveCursorRight();
						model.setSelectionRange(null);
					}
					break;
					
				case KeyEvent.VK_UP:
					if(e.isShiftDown()){
						model.SelectionUp();
					}else{
						model.moveCursorUp();
						model.setSelectionRange(null);
					}
					break;
					
				case KeyEvent.VK_DOWN:
					if(e.isShiftDown()){
						model.SelectionDown();
					}else{
						model.moveCursorDown();
						model.setSelectionRange(null);
					}
					break;
					
				case KeyEvent.VK_BACK_SPACE:
					LocationRange selection = model.getSelectionRange();
					if(selection!=null){
						model.deleteRange();
					}else{
						model.deleteBefore();
					}
					break;
					
				case KeyEvent.VK_DELETE:
					LocationRange selection2 = model.getSelectionRange();
					if(selection2!=null){
						model.deleteRange();
					}else{
						model.deleteAfter();
					}
					break;
				//privremeno
				/*	
				case KeyEvent.VK_C:
					if(e.isControlDown()){
						copy.
					}
					break;*/
					
				default:
					int code =e.getKeyCode();
					if(code!=KeyEvent.VK_SHIFT && code!=KeyEvent.VK_ALT && code!=KeyEvent.VK_ALT_GRAPH && code != KeyEvent.VK_ESCAPE
							&& !e.isControlDown() && code!=KeyEvent.VK_CAPS_LOCK)
						model.insert(e.getKeyChar());
					break;
				}
			}
		});
		
		model.addCursorObserver(new CursorObserver(){
			@Override
			public void updateCursorLocation(Location loc) {
				editorPanel.revalidate();
				editorPanel.repaint();
			}
			
		});
		
		model.addTextObserver(new TextObserver() {
			@Override
			public void updateText() {
				editorPanel.revalidate();
				editorPanel.repaint();
			}
		});
		
		
	}
	
	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.add(new JMenuItem(open));
		file.add(new JMenuItem(save));
		file.addSeparator();
		file.add(new JMenuItem(exit));
		
		menuBar.add(file);
		
		JMenu edit = new JMenu("Edit");
		edit.add(new JMenuItem(undo));
		edit.add(new JMenuItem(redo));
		edit.add(new JMenuItem(cut));
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(paste));
		edit.add(new JMenuItem(pasteAndRemove));
		edit.add(new JMenuItem(deleteSelection));
		edit.add(new JMenuItem(clearDocument));
		
		menuBar.add(edit);
		
		JMenu move = new JMenu("Move");
		move.add(new JMenuItem(cursorToStart));
		move.add(new JMenuItem(cursorToEnd));
		
		menuBar.add(move);
		
		List<Plugin> plugins = null;
		try {
			plugins = loadPlugins();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(plugins==null){
			setJMenuBar(menuBar);
			return;
		}
		
		if(!plugins.isEmpty()) {
			JMenu pluginsMenu = new JMenu("Plugins");
			for (Plugin plugin : plugins) {
				
				Action action = new AbstractAction() {

					private static final long serialVersionUID = 1L;

					@Override
					public void actionPerformed(ActionEvent e) {
						plugin.execute(model, undoManager, clipboardStack);
					}
				};
				
				action.putValue(Action.NAME, plugin.getName());
				action.putValue(Action.SHORT_DESCRIPTION, plugin.getDescription());
				
				pluginsMenu.add(new JMenuItem(action));
			}
			menuBar.add(pluginsMenu);
		}
				
		setJMenuBar(menuBar);
	}
	
	
	// varijanta sa citanjem .class datoteka
	@SuppressWarnings("unchecked")
	private List<Plugin> loadPlugins() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		String dir=Plugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File pluginsDirectory = new File(dir+"/hr/fer/zemris/ooup/lab3/editor/plugins");
		if(!pluginsDirectory.isDirectory()) {
			return new ArrayList<Plugin>();
		}
		
		File[] files = pluginsDirectory.listFiles();
		List<Plugin> plugins = new ArrayList<>();
		Class<Plugin> clazz = null;
		String name;
		String [] pathName;
		
		for(File file:files){
			if(!file.getName().endsWith(".class")) continue;
			pathName=file.getName().split("/");
			name=pathName[pathName.length-1];
			name=name.substring(0, name.length() - 6);
			if(name.equals("Plugin")) continue;
			clazz = (Class<Plugin>)Class.forName("hr.fer.zemris.ooup.lab3.editor.plugins."+name);
			Plugin plugin = (Plugin)clazz.newInstance();
			
			plugins.add(plugin);
		}
		
		return plugins;
	}
	
	
	//varijanta s citanjem .JAR datoteka
	/*
	@SuppressWarnings("unchecked")
	private List<Plugin> loadPlugins() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		ClassLoader loader = Plugin.class.getClassLoader();
		String dir=Plugin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		File pluginsDirectory = new File(dir+"/plugins");
		if(!pluginsDirectory.isDirectory()) {
			return new ArrayList<Plugin>();
		}
		
		File[] files = pluginsDirectory.listFiles();
		List<Plugin> plugins = new ArrayList<>();
		
		for(File file: files){
			if(!file.getName().endsWith(".jar")) continue;
			URLClassLoader newClassLoader = new URLClassLoader(
					new URL[] {
						file.toURI().toURL()
					}, loader);
			
			JarFile jarFile = new JarFile(file);
			Enumeration<JarEntry> entries = jarFile.entries();
			
			JarEntry entry=null;
			while(entries.hasMoreElements()){
				entry = entries.nextElement();
				if(!entry.getName().endsWith(".class")) continue;
				
				
				String className = entry.getName().substring(0, entry.getName().length() - 6);
				className = className.replace('/', '.');
				plugins.add(((Class<Plugin>)newClassLoader.loadClass(className)).newInstance());
			}
			
			jarFile.close();
			newClassLoader.close();
		}
		
		return plugins;
	}*/
	
	private void createToolbar() {
		JToolBar toolBar = new JToolBar();
		
		toolBar.add(new JButton(undo));
		toolBar.add(new JButton(redo));
		toolBar.add(new JButton(cut));
		toolBar.add(new JButton(copy));
		toolBar.add(new JButton(paste));
		
		toolBar.setFloatable(false);
		add(toolBar, BorderLayout.NORTH);
	}
	
	private Action copy = new AbstractAction(){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			LocationRange selection = model.getSelectionRange();
			if(selection!=null){
				clipboardStack.addText(model.getSelectedText());
			}
		}
		
	};
	
	private Action cut = new AbstractAction(){

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//debug
			//System.out.println("cut");
			LocationRange selection = model.getSelectionRange();
			if(selection!=null){
				clipboardStack.addText(model.getSelectedText());
				model.deleteRange();
			}
		}
		
	};
	
	private Action paste = new PasteAction();
	
	private Action pasteAndRemove = new PasteAndRemoveAction();
	
	class PasteAction extends AbstractAction implements ClipboardObserver {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.insert(clipboardStack.getText());
		}

		@Override
		public void updateClipboard() {
			if(clipboardStack.hasText()) {
				paste.setEnabled(true);
			} else {
				paste.setEnabled(false);
			}
		}
	}
	
	class PasteAndRemoveAction extends AbstractAction implements ClipboardObserver {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.insert(clipboardStack.removeText());
		}

		@Override
		public void updateClipboard() {
			if(clipboardStack.hasText()) {
				pasteAndRemove.setEnabled(true);
			} else {
				pasteAndRemove.setEnabled(false);
			}
		}	
	}
	
	private UndoAction undo = new UndoAction();
	private RedoAction redo = new RedoAction();
	
	class UndoAction extends AbstractAction implements StackObserver {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			undoManager.undo();
		}

		@Override
		public void updateStackStatus(boolean empty) {
			undo.setEnabled(!empty);
		}
		
	}
	
	class RedoAction extends AbstractAction implements StackObserver {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			undoManager.redo();
		}

		@Override
		public void updateStackStatus(boolean empty) {
			redo.setEnabled(!empty);
		}

		
	}
	
	private Action deleteSelection = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.deleteRange();
		}
	};
	
	private Action clearDocument = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.clearText();
		}
	};
	
	private Action cursorToStart = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.moveCursorToStartOfDocumetn();
		}
	};
	
	private Action cursorToEnd = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			model.moveCursorToEndOfDocument();
		}
	};
	
	private Action open = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File file = fc.getSelectedFile();
			Path filePath = file.toPath();
			
			if(!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(TextEditor.this, "Sorry can't open " + file.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			byte[] bytes = null;
			try {
				bytes = Files.readAllBytes(filePath);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(TextEditor.this, "While reading file " + file.getName() + ":" + e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			model = new TextEditorModel(new String(bytes, StandardCharsets.UTF_8));
			
			model.addCursorObserver(new CursorObserver(){
				@Override
				public void updateCursorLocation(Location loc) {
					editorPanel.revalidate();
					editorPanel.repaint();
				}
				
			});
			
			model.addTextObserver(new TextObserver() {
				@Override
				public void updateText() {
					editorPanel.revalidate();
					editorPanel.repaint();
				}
			});
			
			model.addCursorObserver(statusLabel);
			model.addTextObserver(statusLabel);
			
			openedFile = filePath;
		}
	};
	
	private Action save = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if(openedFile == null) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save file");
				if(fileChooser.showSaveDialog(TextEditor.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(TextEditor.this, "Can't save changes", "WARNING", JOptionPane.WARNING_MESSAGE);
					return;
				}
				openedFile = fileChooser.getSelectedFile().toPath();
			}
			
			StringBuilder sb = new StringBuilder();
			Iterator<String> iterator = model.allLines();
			while(iterator.hasNext()) {
				sb.append(iterator.next()).append(System.lineSeparator());
			}
			
			byte[] text = sb.toString().getBytes(StandardCharsets.UTF_8);
			
			try {
				Files.write(openedFile, text);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(TextEditor.this, "While writing to file " + openedFile.toFile().getName() + ":" + e1.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(TextEditor.this, "File saved !", "Info", JOptionPane.INFORMATION_MESSAGE);
		}
	};
	
	private Action exit = new AbstractAction() {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};
	
	private void createActions() {
		copy.putValue(Action.NAME, "Copy");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.setEnabled(false);
		
		cut.putValue(Action.NAME, "Cut");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		cut.setEnabled(false);
		
		paste.putValue(Action.NAME, "Paste");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		paste.setEnabled(false);
		clipboardStack.addClipboardObserver((ClipboardObserver) paste);
		
		pasteAndRemove.putValue(Action.NAME, "Paste and Remove");
		pasteAndRemove.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift V"));
		pasteAndRemove.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		pasteAndRemove.setEnabled(false);
		clipboardStack.addClipboardObserver((ClipboardObserver) pasteAndRemove);
		
		undo.putValue(Action.NAME, "Undo");
		undo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Z"));
		undo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Z);
		undo.setEnabled(false);
		undoManager.addUndoStackObserver(undo);
		
		redo.putValue(Action.NAME, "Redo");
		redo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Y"));
		redo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y);
		redo.setEnabled(false);
		undoManager.addRedoStackObserver(redo);
		
		deleteSelection.putValue(Action.NAME, "Delete selection");
		deleteSelection.setEnabled(false);
		
		clearDocument.putValue(Action.NAME, "Clear document");
		
		cursorToStart.putValue(Action.NAME, "Cursor to document start");
		
		cursorToEnd.putValue(Action.NAME, "Cursor to document end");
		
		open.putValue(Action.NAME, "Open");
		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		open.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		
		save.putValue(Action.NAME, "Save");
		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		save.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		exit.putValue(Action.NAME, "Exit");
	}

	private void initRestOfGUI() {
		editorPanel.setPreferredSize(new Dimension(700, 600));
		editorPanel.setBackground(Color.WHITE);
		editorPanel.setFont(new Font("Arial", Font.PLAIN ,16));
	
		JScrollPane scrollPane;
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(editorPanel);
        
		add(scrollPane, BorderLayout.CENTER);
		
		statusLabel = new StatusLabel();
		model.addCursorObserver(statusLabel);
		model.addTextObserver(statusLabel);
		add(statusLabel, BorderLayout.SOUTH);
	}
	
	class StatusLabel extends JLabel implements CursorObserver, TextObserver  {

		private static final long serialVersionUID = 1L;
		
		Location cursorLocation;
		int numberOfLines;
		
		public StatusLabel() {
			numberOfLines = model.getLines().size();
			cursorLocation = model.getCursorLocation();
			setText("Current line " + (cursorLocation.getY() + 1) + ", Current column "  + (cursorLocation.getX() + 1) + ", Total number of lines " + numberOfLines);
		}

		@Override
		public void updateText() {
			numberOfLines = model.getLines().size();
			cursorLocation = model.getCursorLocation();
			setText("Current line " + (cursorLocation.getY() + 1) + ", Current column "  + (cursorLocation.getX() + 1) + ", Total number of lines " + numberOfLines);
		}

		@Override
		public void updateCursorLocation(Location location) {
			numberOfLines = model.getLines().size();
			cursorLocation = location;
			setText("Current line " + (cursorLocation.getY() + 1) + ", Current column "  + (cursorLocation.getX() + 1) + ", Total number of lines " + numberOfLines);
		}
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(()->{
			new TextEditor().setVisible(true);
		});
	}
	
	class EditorPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		public EditorPanel(){
			this.setFocusable(true);
			this.requestFocusInWindow();
		}
		
		@Override 
		public void paintComponent(Graphics g)
	    {
			super.paintComponent(g);
	        Dimension d = this.getSize();
	        Graphics2D graphics = (Graphics2D)g;
			FontMetrics fm = graphics.getFontMetrics();
			
			RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			graphics.addRenderingHints(renderHints);
			
			LocationRange selection = model.getSelectionRange();
			
			if(selection==null){
				copy.setEnabled(false);
				cut.setEnabled(false);
			}
			
			if(selection != null){
				deleteSelection.setEnabled(true);
				graphics.setColor(Color.GRAY);
				Rectangle2D rect = null;
				String line = null;
				
				Location start = selection.getStart();
				Location end = selection.getEnd();
				int numbOfLines=end.getY()-start.getY();
				
				//System.out.println("COPY and CUT enabled");
				copy.setEnabled(true);
				cut.setEnabled(true);

				if(numbOfLines == 0) {
					line = model.getLine(start.getY());
					rect = fm.getStringBounds(line.substring(start.getX(), end.getX()), graphics);
					graphics.fillRect(MARGIN + fm.stringWidth(line.substring(0, start.getX())), 3 + start.getY() * fm.getHeight(), (int)rect.getWidth(), fm.getHeight());
				} else {
					line = model.getLine(start.getY());
					rect = fm.getStringBounds(line.substring(start.getX()), graphics);
					graphics.fillRect(MARGIN + fm.stringWidth(line.substring(0, start.getX())), 3 + start.getY() * fm.getHeight(), (int)rect.getWidth(), fm.getHeight());
					for (int i = start.getY() + 1; i < end.getY(); i++) {
						line = model.getLine(i);
						rect = fm.getStringBounds(line, graphics);
						graphics.fillRect(MARGIN, 3 + i * fm.getHeight(), (int)rect.getWidth(), fm.getHeight());
					}
					
					//last line
					line = model.getLine(end.getY());
					rect = fm.getStringBounds(line.substring(0, end.getX()), graphics);
					graphics.fillRect(MARGIN, 3 + end.getY() * fm.getHeight(), (int)rect.getWidth(), fm.getHeight());
				}
				
				graphics.setColor(Color.BLACK);
			}
	        
			Iterator<String> iterator=model.allLines();
			int i=0;
			String line;
			while(iterator.hasNext()){
				i++;
				line = iterator.next();
				graphics.drawString(line, MARGIN, i*fm.getHeight());
			}
			
			if(draw%2==0){
				draw=1;
				int height=fm.getHeight();
				Location cursorLocation = model.getCursorLocation();	
				
				if(model.getLines().isEmpty()){
					line="";
				}else{
					line = model.getLine(cursorLocation.getY()).substring(0,cursorLocation.getX());
				}
								
				int x1=MARGIN+fm.stringWidth(line);
				int y1=cursorLocation.getY()*height;
				graphics.drawLine(x1, y1, x1, y1+height);
				
			}else{
				draw=0;
			}
			
	    }
	}

}
