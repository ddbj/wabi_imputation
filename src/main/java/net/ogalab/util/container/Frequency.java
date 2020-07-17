package net.ogalab.util.container;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

public class Frequency<S> extends HashMap<S, Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2180891589810959558L;
	
	public long total() {
		long result = 0;
		Set<S> keys = this.keySet();
		for (S k : keys) {
			result += this.getFreq(k);
		}
		return result;
	}
	

	public long getFreq(S key) {
		if (this.containsKey(key)) {
			long result = this.get(key);			
			return result;
		}
		else {
			return 0L;
		}
	}
	

	public void add(S str) {
		if (containsKey(str)) {
			long count = get(str) + 1;
			put(str, count);
		}
		else {
			put(str, 1L);
		}
	}
	
	public int numOfKeys() {
		return this.entrySet().size();
	}
	
	public Frequency<Long> freqOfFreq() {
		Frequency<Long> result = new Frequency<Long>();
		Set<S> keys = this.keySet();
		for (S k : keys) {
			result.add(this.get(k));
		}
		return result;
	}
	
	public void print() {
		MapUtil.print(this);
	}
	
	public void print(PrintWriter pw) {
		MapUtil.print(this, pw);
	}
}
