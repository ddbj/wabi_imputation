package net.ogalab.util.dataframe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ogalab.util.container.ListUtil;
import net.ogalab.util.fundamental.StringUtil;
import net.ogalab.util.fundamental.Type;
import net.ogalab.util.os.FileIO;

/** 
 * <ul>
 * <li>A DataFrame is a list of columns.</li>
 * <li>Each column is a list of values of same scalar type.</li>
 * </ul>
 * @author oogasawa
 *
 */
public class DataFrame extends ArrayList<ArrayList> {

	ArrayList<String>  header = null;
	ArrayList<Integer> order = null;
	
	Pattern commentPattern = Pattern.compile("^#");
	
	public void addColumn(ArrayList colList) {
		this.add(colList);
	}
	
	public void addColumn(int[] colArray) {
		this.add(toIntegerArrayList(colArray));
	}
	
	public void addColumn(double[] colArray) {
		this.add(toDoubleArrayList(colArray));
	}
	
	public void addColumn(String[] colArray) {
		this.add(toStringArrayList(colArray));
	}
	
	public void addRow(ArrayList rowList) {
		for (int i=0; i<rowList.size(); i++) {
			if (this.size() < i+1) { 
				ArrayList colList = new ArrayList();
				this.add(colList);
			}
			this.get(i).add(rowList.get(i));
		}
	}
	
	public void addRow(Object[] rowArray) {
		for (int i=0; i<rowArray.length; i++) {
			if (this.size() < i+1) { 
				ArrayList colList = new ArrayList();
				this.add(colList);
			}
			this.get(i).add(rowArray[i]);
		}
	}
	
	public ArrayList getColumn(int col) {
		return this.get(col);
	}
	
	public ArrayList getRow(int row) {
		ArrayList result = new ArrayList();
		for (int i=0; i<this.getNumOfColumns(); i++) {
			result.add(this.get(row, i));
		}
		return result;
	}
	
	public Object get(int row, int col) {
		return this.get(col).get(row);
	}
	
	public void set(int row, int col, Object val) {
		this.get(col).set(row, val);
	}
	
	public int getNumOfColumns() {
		return this.size();
	}
	
	public int getNumOfRows() {
		if (this.size() > 0) {
			return this.get(0).size();
		}
		else {
			return 0;
		}
	}
	
	public void setHeader(ArrayList<String> header) {
		this.header = header;
	}
	
	public void setHeader(String[] hArray) {
		header = new ArrayList<String>();
		for (String h : hArray) {
			header.add(h);
		}
	}
	
	public void removeHeader() {
		header = null;
	}
	
	public void readTable(String filename, boolean hasHeader) throws IOException {
		BufferedReader br = FileIO.getBufferedReader(filename);
		String line = null;
		int headerStatus = 1;
		while ((line = br.readLine()) != null) {
			Matcher m = commentPattern.matcher(line);
			if (m.find()) 
				continue; // skip a comment line.
			
			ArrayList<String> row = StringUtil.splitByTab(line);
			if (hasHeader == true && headerStatus == 1) {
				header = toStringList(row);
				headerStatus = 0;
				continue;
			}
			else {
				this.addRow(row);
			}
		}
		br.close();
	}
	
	public void readTable(String filename) throws IOException {
		readTable(filename, false);
	}
	
	
	public void print() {
		if (header != null) {
			System.out.println(ListUtil.join("\t", header));
		}
		for (int i=0; i<getNumOfRows(); i++) {
			System.out.println(ListUtil.join("\t", this.getRow(i)));
		}
	}
	
	public void writeTable(String outfile) throws IOException {
		PrintWriter pw = FileIO.getPrintWriter(outfile);
		
		if (header != null) {
			pw.println(ListUtil.join("\t", header));
		}
		for (int i=0; i<getNumOfRows(); i++) {
			pw.println(ListUtil.join("\t", this.getRow(i)));
		}
		
		pw.close();
		
	}
	
	private ArrayList<String> toStringList(ArrayList objList) {
		ArrayList<String> result = new ArrayList<String>();
		for (Object elem : objList) {
			result.add(Type.toString(elem));
		}
		return result;
	}
	
	private ArrayList<Integer> toIntegerArrayList(int[] a) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int elem : a) {
			result.add(elem);
		}
		return result;
	}
	
	private ArrayList<Double> toDoubleArrayList(double[] a) {
		ArrayList<Double> result = new ArrayList<Double>();
		for (Double elem : a) {
			result.add(elem);
		}
		return result;
	}
	
	private ArrayList<String> toStringArrayList(String[] a) {
		ArrayList<String> result = new ArrayList<String>();
		for (String elem : a) {
			result.add(elem);
		}
		return result;
	}
	
}
