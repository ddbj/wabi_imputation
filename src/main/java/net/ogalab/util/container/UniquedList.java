package net.ogalab.util.container;

import java.util.ArrayList;

public class UniquedList {
	
	ArrayList<String> entity = new ArrayList<String>();

	public void add(String s) {
		if (!has(s))
			entity.add(s);
	}
	
	public void addAll(ArrayList<String> list) {
		for (String s : list) {
			add(s);
		}
	}
	
	public boolean has(String s) {
		for (String item : entity) {
			if (item.equals(s))
				return true;
		}
		return false;
	}
	
	public String toString() {
		return ListUtil.join("\n", entity);
	}
}
