package net.ogalab.util.container;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import net.ogalab.functor.Functor;
import net.ogalab.util.fundamental.Type;

public class ListUtil {	
	
	public static <T> ArrayList<Double> toDoubleList(ArrayList<T> list) {
		ArrayList<Double> result = new ArrayList<Double>();
		for (T elem : list) {
			result.add(Type.toDouble(elem));
		}
		return result;
	}
	
	public static <T> T shift(ArrayList<T> a) {
		T res = a.get(0);
		a.remove(0);
		
		return res;
	}
	
	public static <T> void unshift(ArrayList<T> a, T elem) {
		a.add(0, elem);
	}
	
	public static <T> T pop(ArrayList<T> a) {
		T res = a.get(a.size()-1);
		a.remove(a.size()-1);
		
		return res;
	}
	
	public static <T> void push(ArrayList<T> a, T elem) {
		a.add(elem);
	}
	
	public static <T> T head(ArrayList<T> a) {
		return a.get(0);
	}
	
	public static <T> T tail(ArrayList<T> a) {
		return a.get(a.size()-1);
	}
	
	
	
	public static <T> ArrayList<T> unique(ArrayList<T> a) {
		ArrayList<T> ret = new ArrayList<T>();
		for (T s : a) {
			if (s != null && !ListUtil.has(ret, s)) {
				ret.add(s);
			}
		}
		return ret;
	}

	public static <T> ArrayList<T> insert(ArrayList<T> list, T elem, int col) {
		ArrayList<T> result = new ArrayList<T>();
		for (int i=0; i<list.size(); i++) {
			if (i==col)
				result.add(elem);
			else if (i<col)
				result.add(list.get(i));
			else
				result.add(list.get(i-1));
		}
		return result;
	}
	

	public static <T extends Comparable<? super T>> ArrayList<T> sort(ArrayList<T> list) {
		ArrayList<T> result = new ArrayList<T>();
		for (T elem : list) {
			result.add(elem);
		}
		Collections.sort(result);
		return result;
	}
	
	

	public static String truncate(String str, int length) {
		if (str.length() > length) {
			return str.substring(0, length) + " ...";
		}
		else 
			return str;
	}
	
	public static <T> String makeReport(ArrayList<T> list) {
		StringBuffer buf = new StringBuffer();
		for (T item : list) {
			buf.append(truncate(item.toString(), 100) + "\n\n");
		}
		return buf.toString();
	}
	
	public static <T> ArrayList<T> insert(ArrayList<T> list1, ArrayList<T> list2, int pos) {
		ArrayList<T> ret = new ArrayList<T>();
		for (int i=0; i<pos; i++) {
			ret.add(list1.get(i));
		}
		ret.addAll(list2);
		for (int i=pos; i<list1.size(); i++) {
			ret.add(list1.get(i));
		}
		return ret;
	}
	
	
	// constructing a list.
	
	public static <T> ArrayList<T> sequence(T val, int num) {
		ArrayList<T> ret = new ArrayList<T>();
		for (int i=0; i<num; i++) 
			ret.add(val);
		
		return ret;
	}
	
	public static<T> ArrayList<T> list(T...s) {
		ArrayList<T> ret = new ArrayList<T>();
		for (T item : s) {
			ret.add(item);
		}
		return ret;
	}
	
		

	public static <T> ArrayList<T> getColumns(ArrayList<T> list, int...s) {
		ArrayList<T> ret = new ArrayList<T>();
		for (int i : s) {
			ret.add(list.get(i));
		}
		return ret;
	}
	
	public static <T> ArrayList<T> apply(ArrayList<T> list, Functor<T> f) {
		ArrayList<T> ret = new ArrayList<T>();
		for (T t : list) {
			ret.add(f.func(t));
		}
		return ret;
	}
	
	public static <T> ArrayList<T> apply(ArrayList<T> list, Functor<T> f, int j) {
		ArrayList<T> ret = new ArrayList<T>();
		for (int i=0; i<list.size(); i++) {
			if (i==j)
				ret.add(f.func(list.get(i)));
			else
				ret.add(list.get(i));
		}
		return ret;
	}
	
        
        public static <T> int[] to_int_array(ArrayList<T> list) {
		int[] ret = new int[list.size()];
		for (int i=0; i<list.size(); i++) {
			ret[i] = Type.to_int(list.get(i));
		}
		return ret;
	}
        
	public static <T> double[] to_double_array(ArrayList<T> list) {
		double[] ret = new double[list.size()];
		for (int i=0; i<list.size(); i++) {
			ret[i] = Type.to_double(list.get(i));
		}
		return ret;
	}
	
	public static <T> String[] toStringArray(ArrayList<T> list) {
		String[] ret = new String[list.size()];
		for (int i=0; i<list.size(); i++) {
			ret[i] = Type.toString(list.get(i));
		}
		return ret;		
	}
	
	public static HashSet<String> toStringHashSet(ArrayList<File> list) {
		HashSet<String> result = new HashSet<String>();
		for (File f: list) {
			result.add(f.getPath());
		}
		return result;		
	}
	
	
	public static File[] toFileArray(ArrayList<File> list) {
		File[] ret = new File[list.size()];
		for (int i=0; i<list.size(); i++) {
			ret[i] = list.get(i);
		}
		return ret;		
	}
	
	public static <T> void print(List<T> list) {
		for (T elem : list) {
			System.out.println(elem.toString());
		}
	}
	
	public static <T> ArrayList<T> repeat(T val, int n) {
		ArrayList<T> ret = new ArrayList<T>();
		for (int i=0; i<n; i++)
			ret.add(val);
		
		return ret;
	}
	
	public static ArrayList<Double> sequence(double from, double to, double delta) {
		ArrayList<Double> ret = new ArrayList<Double>();
		double val = from;

		while (val < to) {
			ret.add(val);
			val += delta;
		}
		return ret;
	}
	
	public static <T> boolean has(ArrayList<T> a, T s) {
		for (T item : a) {
			if (item.equals(s))
				return true;
		}
		return false;
	}
	
	public static void initialize(double[] a, double val) {
		for (int i=0; i<a.length; i++)
			a[i] = val;
	}
	
	public static double[] clone(double[] a) {
		double[] newArray = new double[a.length];
		
		for (int i=0; i<a.length; i++) {
			newArray[i] = a[i];
		}
		return newArray;
	}
	
	public static int[] clone(int[] a) {
		int[] newArray = new int[a.length];
		
		for (int i=0; i<a.length; i++) {
			newArray[i] = a[i];
		}
		return newArray;
	}
	
	public static <T> ArrayList<T> clone(ArrayList<T> a) {
		ArrayList<T> newList = new ArrayList<T>(a.size());
		
		for (int i=0; i<a.size(); i++) {
			newList.add(a.get(i));
		}
		return newList;
	}
	
	
		
	
	public static <T> String join(String delim, ArrayList<T> list) {
		if (list == null || list.size() == 0) 
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append(list.get(0));
		
		for (int i=1; i<list.size(); i++) {
			sb.append(delim);
			sb.append(Type.toString(list.get(i)));
		}
		
		return sb.toString();
	}
	


}
