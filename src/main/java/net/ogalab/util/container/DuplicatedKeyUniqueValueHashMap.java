package net.ogalab.util.container;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import net.ogalab.util.os.FileIO;

public class DuplicatedKeyUniqueValueHashMap <K,V> implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5657223371908240636L;
	HashMap<K,TreeSet<V>> entity = new HashMap<K, TreeSet<V>>();
	

	
	public void save(String filename, String delim) throws IOException {
		PrintWriter pw = FileIO.getPrintWriter(filename);

		Set<K> keys = entity.keySet();
		for (K k : keys) {
			Object[] array = entity.get(k).toArray();
			String[] s = ArrayUtil.toStringArray(array);
			String str = ArrayUtil.join(delim, ArrayUtil.sort(s));
			pw.println(k + "\t" + str);
		}
		pw.close();
	}

	public void save(String filename) throws IOException {
		PrintWriter pw = FileIO.getPrintWriter(filename);
		Set<K> keys = entity.keySet();
		for (K k : keys) {
			for (V v : entity.get(k)) {
				pw.println(k + "\t" + v);				
			}
		}	
		pw.close();
	}
	
	
	public void put(K key, V value) {
		
		if (entity.containsKey(key)) {
			entity.get(key).add(value);
		}
		else {
			TreeSet<V> valList = new TreeSet<V>();
			valList.add(value);
			entity.put(key, valList);
		}
		
	}


	public void clear() {
		entity.clear();
	}


	public boolean containsKey(Object key) {
		return entity.containsKey(key);
	}


	@SuppressWarnings("unchecked")
	public boolean containsValue(Object val) {

		Iterator<?> sIter = keySet().iterator();
		K key = null;
		TreeSet<V> valSet = null;
		while (sIter.hasNext()) {
			key = (K) sIter.next();
			valSet = entity.get(key);
			if (valSet.contains(val)) {
				return true;
			}
		}
		return false;
	}


	public TreeSet<V> get(Object key) {
		return entity.get(key);
	}
	
	public ArrayList<V> getValueList(Object key) {
		ArrayList<V> result = new ArrayList<V>();
		TreeSet<V> set = get(key);
		for (V val : set) {
			result.add(val);
		}
		return result;
	}

	public boolean isEmpty() {
		return entity.isEmpty();
	}

	public Set<?> keySet() {
		return entity.keySet();
	}

	public Object remove(K key) {
		return entity.remove(key);
	}

	public int size() {
		return entity.size();
	}

	/*
	public <V> Collection<V> values() {
		ArrayList<V> valList = new ArrayList<V>();
		Iterator<K>  iter    = keySet().iterator();
		K key = null;
		while (iter.hasNext()) {
			key = iter.next();
			valList.addAll(entity.get(key));
		}
		return valList;
	}
	*/
	

}
