package net.ogalab.util.container;

import java.util.HashMap;

public class StrHashMap extends HashMap<String, String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7386303013284721066L;

	
	public void append(String key, String val) {
		if (this.containsKey(key)) {
			String val0 = this.get(key);
			this.put(key, val0 + val);
		}
		else {
			this.put(key, val);
		}
	}
	
}
