package net.ogalab.util.container;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import net.ogalab.util.fundamental.StringUtil;
import net.ogalab.util.os.FileIO;

public class DuplicableKeyHashMap<K,V> implements Cloneable, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5657223371908240636L;
	TreeMap<K,ArrayList<V>> entity = new TreeMap<K, ArrayList<V>>();
	
	public ArrayList<V> get(Object key) {
		return entity.get(key);
	}

	public void put(K key, V value) {
		
		if (entity.containsKey(key)) {
			entity.get(key).add(value);
		}
		else {
			ArrayList<V> valList = new ArrayList<V>();
			valList.add(value);
			entity.put(key, valList);
		}
		
	}

	
	public void save(String filename, String delim) throws IOException {
		PrintWriter pw = FileIO.getPrintWriter(filename);

		Set<K> keys = entity.keySet();
		for (K k : keys) {
			String str = ListUtil.join(delim, entity.get(k));
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
	
	public void clear() {
		entity.clear();
	}


	public boolean containsKey(Object key) {
		return entity.containsKey(key);
	}


	@SuppressWarnings("unchecked")
	public boolean containsValue(Object val) {
		Set<?> s = keySet();
		Iterator<?> sIter = s.iterator();
		K key = null;
		ArrayList<V> valList = null;
		while (sIter.hasNext()) {
			key = (K) sIter.next();
			valList = entity.get(key);
			for (int i=0; i<valList.size(); i++) {
				if (valList.get(i).equals(val))
					return true;
			}
		}
		return false;
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
	
	@SuppressWarnings("unchecked")
	public int numOfValueColumns() {
		Set<?> ks = keySet();
		Iterator<?> iter = ks.iterator();
		K        key  = (K) iter.next();
		String   val  = entity.get(key).get(0).toString();
		ArrayList<String> cols = StringUtil.splitByTab(val);
		
		return cols.size();
	}
	
	
	public void print() {
		Set<K> keys = entity.keySet();
		for (K k : keys) {
			for (V v : entity.get(k)) {
				System.out.println(k + "\t" + v);				
			}
		}		
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
