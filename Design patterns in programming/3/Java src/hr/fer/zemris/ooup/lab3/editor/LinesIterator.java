package hr.fer.zemris.ooup.lab3.editor;

import java.util.List;
import java.util.NoSuchElementException;

//korisit javin iterator???
public class LinesIterator implements Iterator<String> {
	private List<String> lines;
	private int index;
	private int end;

	public LinesIterator(List<String> lines) {
		this.lines = lines;
		index = 0;
		end = lines.size();
	}

	public LinesIterator(List<String> lines, int beginIndex, int endIndex) {
		this.lines = lines;
		index = beginIndex;
		end = endIndex;
	}

	@Override
	public String next() {
		if (hasNext()) {
			return lines.get(index++);
		} else {
			throw new NoSuchElementException();
		}
	}

	@Override
	public boolean hasNext() {
		if(index<end)
			return true;
		return false;
	}




}
