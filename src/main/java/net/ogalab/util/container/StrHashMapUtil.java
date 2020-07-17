package net.ogalab.util.container;

import net.ogalab.util.fundamental.Type;

public class StrHashMapUtil {
	
	public static StrHashMap copy(StrHashMap map, String[] keys) {
		StrHashMap result = new StrHashMap();
		
		for (String k : keys) {
			if (map.containsKey(k)) {
				result.put(k, map.get(k));
			}
			else {
				result.put(k, Type.NA);
			}
		}
		
		return result;
	}

}
