package net.ogalab.util.container;

import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListOfMapUtil {
	
	public static ListOfMap<String,String> filterOut(ListOfMap<String,String> atags, Pattern p, String col) {
		ListOfMap<String,String> result = new ListOfMap<String,String>();
		
		for (TreeMap<String,String> row : atags) {
			Matcher m = p.matcher(row.get(col));
			if (!m.find()) {
				result.add(row);
			}
		}
		return result;
	}

}
