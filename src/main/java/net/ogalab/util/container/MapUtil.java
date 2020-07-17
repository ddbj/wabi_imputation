package net.ogalab.util.container;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

public class MapUtil<K,V> {
	

	public static void print(Map<?,?> m) {
		Set<?> keys = m.keySet();
		for (Object k : keys) {
			System.out.println(k.toString() + "\t" + m.get(k).toString());
		}
	}
	
	public static void print(Map<?,?> m, PrintWriter pw) {
		Set<?> keys = m.keySet();
		for (Object k : keys) {
			pw.println(k.toString() + "\t" + m.get(k).toString());
		}
	}
	
}
