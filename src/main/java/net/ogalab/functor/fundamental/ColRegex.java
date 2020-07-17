package net.ogalab.functor.fundamental;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ogalab.functor.Predicate;
import net.ogalab.util.fundamental.StringUtil;

public class ColRegex implements Predicate<String> {
	
	int     column = 0;
	Pattern pattern = null;
	
	public ColRegex(int col, Pattern p) {
		column = col;
		pattern = p;
	}

	public boolean is(String line) {
		ArrayList<String> a = StringUtil.splitByTab(line);
		
		Matcher m = pattern.matcher(a.get(column));
		return m.find();
	}
	
	

}
