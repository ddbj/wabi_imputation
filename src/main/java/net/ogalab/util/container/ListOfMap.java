package net.ogalab.util.container;

import java.util.*;

public class ListOfMap<K, V> extends ArrayList<TreeMap<K, V>> {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9069042231773629710L;

	public void addAll(ListOfList<V> table, K[] header) {
		
		TreeMap<K,V> map = null;
		for (ArrayList<V> row : table) {
			map = new TreeMap<K,V>();
			for (int i=0; i<header.length; i++) {
				map.put(header[i], row.get(i));
			}
			this.add(map);
		}	
	}
	
	public void addAll(ArrayList<ArrayList<V>> table, K[] header) {
		
		TreeMap<K,V> map = null;
		for (ArrayList<V> row : table) {
			map = new TreeMap<K,V>();
			for (int i=0; i<header.length; i++) {
				map.put(header[i], row.get(i));
			}
			this.add(map);
		}
	}
	
	public void print() {
		int counter = 0;
		for (TreeMap<K,V> row : this) {
			Set<K> keys = row.keySet();
			for (K k : keys) {
				System.out.println(counter + "\t" + k + "\t" + row.get(k));
			}
			counter++;
		}
		
	}

}
