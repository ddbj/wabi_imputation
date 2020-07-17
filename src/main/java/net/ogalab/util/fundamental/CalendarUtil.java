package net.ogalab.util.fundamental;

import java.util.Calendar;

public class CalendarUtil {
	
	final static public int UNDERSCORE = 1;      // 2010_10_01_00_10_59
	final static public int STANDARD   = 2;      // 2010-10-01 00:10:59
	final static public int TIME       = 3;      // 00:10:59(2010-10-11)
	final static public int NOT_ZERO_PADDED = 4; // 2010-10-1
	final static public int STANDARD_DATE = 5;   // 2010-10-01
	
	public static String todaysDate() {
		Calendar current = Calendar.getInstance();
		return String.format("%1$tF", current);
	}
	
	public static String dateDir() {
		Calendar c = Calendar.getInstance();
		return String.format("%1$tY/%1$tm/%1$td/", c);
	}
	
	public static String yesterdaysDate() {
		Calendar d = Calendar.getInstance();
		d.add(Calendar.DATE, -1);
		return String.format("%1$tF", d);
	}	
	
	public static String addDate(int i) {
		Calendar d = Calendar.getInstance();
		d.add(Calendar.DATE, i);
		return String.format("%1$tF", d);
	}	
	
	public static String now() {
		return currentTime();
	}
	
	public static String currentTime() {
		Calendar current = Calendar.getInstance();
		return format(current, STANDARD);
	}
	
	
	public static String currentTime(int format) {
		Calendar current = Calendar.getInstance();

		return format(current, format);
	}
	
	public static String format(Calendar c, int format) {
		
		String str = null;
		if (format == UNDERSCORE)
			str = String.format("%1$tY_%1$tm_%1$td_%1$tH_%1$tM_%1$tS", c);
		else if (format == STANDARD)
			str = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", c);
		else if (format == TIME)
			str = String.format("%1$tH:%1$tM:%1$tS(%1$tY-%1$tm-%1$td)", c);
		else if (format == STANDARD_DATE)
			str = String.format("%1$tY-%1$tm-%1$td9" +
					"", c);
		
		return str;
	}
	
	public static String Time(long millisec) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millisec);
		
		return format(c, STANDARD);
	}
	
}
