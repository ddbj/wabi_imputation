package net.ogalab.util.container;

import java.util.ArrayList;

public class ListOfList<T> extends ArrayList<ArrayList<T>>  {
	
	/*
	public final static int INTEGER = 1;
	public final static int DOUBLE  = 2;
	public final static int STRING  = 3;
	*/
//	protected ArrayList<ArrayList<T>> matrix = new ArrayList<ArrayList<T>>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2540944369384406090L;


	public ListOfList() { }
	
	public ListOfList(T[][] arrayOfArray) {
		for (T[] row : arrayOfArray) {
			ArrayList<T> r = ArrayUtil.toList(row);
			this.add(r);
		}
	}
	
	public ListOfList(int numRow, int numCol, T val) {
		ArrayList<T> row = null;
		for (int i=0; i<numRow; i++) {
			row = ListUtil.repeat(val, numCol);
			add(row);
		}
	}
        
	
	public static <T> boolean equal(ListOfList<T> lol1, ListOfList<T> lol2) {
		if (lol1.size() != lol2.size())
			return false;
		for (int i=0; i<lol1.size(); i++) {
			if (lol1.get(i).size() != lol2.get(i).size())
				return false;
			for (int j=0; j<lol1.get(i).size(); j++)
				if (!lol1.get(i).get(j).equals(lol2.get(i).get(j)))
					return false;
		}
		return true;
	}
	
	
	public int getWidth() {
		return get(0).size();
	}
	
	public int getHeight() {
		return size();
	}
	
	
	public void addRow(ArrayList<T> row) {
		add(row);
	}
	

	public void addRow(T...val) {
		ArrayList<T> row = new ArrayList<T>();
		for (int i=0; i<val.length; i++) {
			row.add(val[i]);
		}
		addRow(row);
	}
	

	public T get(int r, int c) {
		return get(r).get(c);
	}
	
	public void set(int r, int c, T val) {
		get(r).set(c, val);
	}
	
	public int numRows() {
		return size();
	}
	
	public int numColumns() {
		return get(0).size();
	}
	
	/*
	public void clear() {
		clear();
	}
	*/
	
	/*
	public void read(String path, int type) throws IOException {
		
		clear();
		
		BufferedReader fin = FileUtil.getBufferedReader(path);
		String line = null;
		ArrayList<String> row0  = null;
		ArrayList<T> row = null;
		while ((line = fin.readLine()) != null) {
			row0 = StringUtil.splitByTab(line);
			row = new ArrayList<T>();
			for (int i=0; i<row0.size(); i++) {
				
				if (type == ListOfList.DOUBLE) {
					row.add((T)Type.toDouble(row0.get(i)));	
				}
				else if (type == ListOfList.INTEGER) {
					row.add((T)Type.toInteger(row0.get(i)));	
				}
				else {
					row.add((T)row0.get(i));	
				}
				
			}
			add(row);
		}
	}
	*/
	
	public void addColumn(int col, ArrayList<T> list) {
		ArrayList<T> row = null;
		for (int i=0; i<numRows(); i++) {
			row = getRow(i);
			row.add(col, list.get(i));
			set(i, row);
		}
	}
	
	public ArrayList<T> getColumn(int c) {
		ArrayList<T> ret = new ArrayList<T>();
		for (int i=0; i<size(); i++) {
			ret.add( get(i).get(c) );
		}
		return ret;
	}
	
	public ArrayList<T> getRow(int r) {
		return get(r);
	}
	
	public void print() {
		for (ArrayList<T> row : this) {
			System.out.println(ListUtil.join("\t", row));
		}
	}

	
}
