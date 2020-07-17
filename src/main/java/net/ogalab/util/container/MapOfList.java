package net.ogalab.util.container;

import java.util.*;

public class MapOfList<K,T> extends HashMap<K, ArrayList<T>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2250880045941408359L;

	public void add(K key, T value) {
		
		if (!containsKey(key)) {
			put(key, new ArrayList<T>());
		}
		get(key).add(value);
	}

	public ArrayList<K> getKeys() {
		ArrayList<K> result = new ArrayList<K>();
		Set<K> keys = keySet();
		for (K k : keys)
			result.add(k);
		
		return result;
	}
	
	public ArrayList<T> getValues(K key) {
		return get(key);
	}

}
