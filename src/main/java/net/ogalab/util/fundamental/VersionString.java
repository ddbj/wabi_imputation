package net.ogalab.util.fundamental;

import java.util.ArrayList;
import java.util.Collections;

import net.ogalab.util.container.ListUtil;

public class VersionString {
	
	public static void main(String[] args) {
		ArrayList<String> v = new ArrayList<String>();
		v.add("2.0a");
		v.add("1.0.0");
		v.add("10.0");
		ArrayList<String> w = sort(v);
		ListUtil.print(w);
	}
	
	
	/** Sort version strings.
	 * 
	 * @param vList A list of version numbers (such as "10.00", "0.0.1", and so on.)
	 * @return A sorted list of the given version numbers.
	 */
	public static ArrayList<String> sort(ArrayList<String> vList) {
		Collections.sort(vList, new VersionStringComparator());
		return vList;
	}

}
