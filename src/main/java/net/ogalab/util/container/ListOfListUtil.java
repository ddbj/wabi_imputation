package net.ogalab.util.container;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ogalab.util.fundamental.StringUtil;
import net.ogalab.util.fundamental.Type;
import net.ogalab.util.os.FileIO;

public class ListOfListUtil {
	
	public static <T> double[][] to_double2D(ListOfList<T> matrix) {
		double[][] result = new double[matrix.numRows()][matrix.numColumns()];
		
		for (int i=0; i<matrix.numRows(); i++) {
			for (int j=0; j<matrix.numColumns(); j++) {
				result[i][j] = Type.to_double(matrix.get(i,j));
			}
		}
		return result;
	}
	
	
	public static ListOfList<String> filterOut(ListOfList<String> atags, Pattern p, int col) {
		ListOfList<String> result = new ListOfList<String>();
		
		for (ArrayList<String> row : atags) {
			Matcher m = p.matcher(row.get(col));
			if (!m.find()) {
				result.add(row);
			}
		}
		return result;
	}
	
	public static <T> void clear(ListOfList<T> table) {
		for (ArrayList<T> row : table) {
			row.clear();
		}
	}
	
	public static <T> void print(ListOfList<T> table) {
		for (ArrayList<T> row : table) {
			System.out.println(ListUtil.join("\t", row));
		}
	}
	
	public static <T> HashMap<T,T> getHashMap(ListOfList<T> table, int keyCol, int valueCol) {
		HashMap<T,T> result = new HashMap<T,T>();
		for (ArrayList<T> row : table) {
			result.put(row.get(keyCol), row.get(valueCol));
		}
		
		return result;
	}

	public static <T> HashSet<T> getHashSetOfAColumn(ListOfList<T> table, int col) {
		HashSet<T> result = new HashSet<T>();
		for (ArrayList<T> row : table) {
			result.add(row.get(col));
		}
		
		return result;
	}
	
	public static ListOfList<String> readFile(String filename) throws IOException {
		ListOfList<String> result = new ListOfList<String>();
		BufferedReader br = null;

		br = FileIO.getBufferedReader(filename);
		String line = null;
		while ((line = br.readLine()) != null) {
			ArrayList<String> row = StringUtil.splitByTab(line);
			result.add(row);
		}

		br.close();
		return result;
	}
	
	public static ListOfList<String> readTable(String filename) throws IOException {
		ListOfList<String> result = new ListOfList<String>();
		BufferedReader br = null;

		br = FileIO.getBufferedReader(filename);
		String line = null;
		while ((line = br.readLine()) != null) {
			// Ignore comment lines.
			if (line.startsWith("#")) 
				continue;
			
			ArrayList<String> row = StringUtil.splitByTab(line);
			result.add(row);
		}

		br.close();
		return result;
	}
	
	
	
	
	public static int numOfColumns(String filename) throws IOException {
		
		BufferedReader br = FileIO.getBufferedReader(filename);
		String line = null;
		int    counter = 0;
		//int    maxCount = 10;
		int    maxNumOfCol = 0;
		while ((line = br.readLine()) != null) {
			if (++counter > 10)
				break;
			ArrayList<String> cols = StringUtil.splitByTab(line);
			if (cols.size() > maxNumOfCol)
				maxNumOfCol = cols.size();
		}
		
		br.close();
		return maxNumOfCol;
		
	}
	
}
