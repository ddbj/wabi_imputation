/*
 * Created on May 24, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package net.ogalab.util.fundamental;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


/**
 * @author oogasawa
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class Type {
	
	public static final String NA = "\\N";

	public static void main(String[] argv) {
		System.out.println("Byte.MAX_VALUE    : " + Byte.MAX_VALUE);   //(1)
		System.out.println("Short.MAX_VALUE   : " +Short.MAX_VALUE);   //(2)
		System.out.println("Integer.MAX_VALUE : " +Integer.MAX_VALUE);   //(3)
		System.out.println("Long.MAX_VALUE    : " +Long.MAX_VALUE);    //(4)
		System.out.println("Float.MAX_VALUE   : " +Float.MAX_VALUE);   //(5)
		System.out.println("Double.MAX_VALUE  : " +Double.MAX_VALUE);   //(6)
	}

	///////////////////////////////////////////////////////////////////////


	public static Integer toInteger(boolean b) {
		if (b)
//			return new Integer(1);
			return Integer.valueOf(1);
		else
//			return new Integer(0);
			return Integer.valueOf(0);
	}

	public static Integer toInteger(int n) {
//		return new Integer(n);
		return Integer.valueOf(n);
	}

	
	public static Integer toInteger(long n) {
//		Long l = new Long(n);
		Long l = Long.valueOf(n);
		return l.intValue();
	}
	
	public static Integer toInteger(double r) {
//		Double rD = new Double(r);
		Double rD = Double.valueOf(r);
		return rD.intValue();
	}

	public static Integer toInteger(Integer n) {
		if (n==null)
//			return new Integer(0);
			return Integer.valueOf(0);
		else
			return n;
	}

	
	public static Integer toInteger(Long n) {
		if (n == null)
//			return new Integer(0);
			return Integer.valueOf(0);
		else
			return n.intValue();
	}
	

	public static Integer toInteger(Double rD) {
		if (rD == null) 
//			return new Integer(0);
			return Integer.valueOf(0);
		else
			return rD.intValue();
	}

	public static Integer toInteger(String s) {
		Integer ret = null;
		try {
			if (s == null || s.length() == 0) {
//				ret = new Integer(0);
				ret = Integer.valueOf(0);
			}
			else {
				ret = Integer.valueOf(s);
			}
		}
		catch (NumberFormatException e) {
			ret = Double.valueOf(s).intValue();
		}
		return ret;
	}

	public static Integer toInteger(char ch) {
		return toInteger(String.valueOf(ch));
	}

	
	public static Integer toInteger(Object o) {
		Integer ret = null;

		if (o == null) {
			ret = toInteger(0);
		}      
		else if (isInteger(o)) {
			ret = (Integer)o;
		}
		else if (isLong(o)) {
			Long l = (Long)o;
			ret = l.intValue();
		}
		else if (isDouble(o)) {
			Double d = (Double)o;
			ret = d.intValue();
		}
		else if (isString(o)) {
			ret = toInteger((String)o);
		}

		return ret;
	}

	
	//////////////////////////////////////////////////////////////////////
	
	public static Long toLong(boolean b) {
		if (b)
//			return new Long(1);
			return Long.valueOf(1);
		else
//			return new Long(0);
			return Long.valueOf(0);
	}


	public static Long toLong(int n) {
//		return new Long(n);
		return Long.valueOf(n);
	}

	public static Long toLong(long n) {
//		return new Long(n);
		return Long.valueOf(n);
	}
	
	public static Long toLong(double r) {
//		Double rD = new Double(r);
		Double rD = Double.valueOf(r);
		return rD.longValue();
	}

	public static Long toLong(Double rD) {
		if (rD == null) {
//			return new Long(0);
			return Long.valueOf(0);
		}

		return rD.longValue();
	}


	public static Long toLong(Object o) {
		Long ret = null;

		if (o == null) {
			ret = toLong(0);
		}      
		else if (isInteger(o)) {
			ret = toLong((Integer)o);
		}
		else if (isLong(o)) {
			ret = (Long)o;
		}
		else if (isDouble(o)) {
			ret = toLong((Double)o);
		}
		else if (isString(o)) {
			ret = toLong((String)o);
		}

		return ret;
	}

	
	public static Long toLong(String s) {
		Long ret = null;
		try {
			if (s == null || s.length() == 0) {
//				ret = new Long(0);
				ret = Long.valueOf(0);
			}
			else {
				ret = Long.valueOf(s);
			}
		}
		catch (NumberFormatException e) {
			ret = toLong(Double.valueOf(s));
		}
		return ret;
	}

	
	public static Long toLong(StringBuffer s) {
		if (s == null)
//			return new Long(0);
			return Long.valueOf(0);
		
		return toLong(s.toString());
	}


	public static Long toLong(char ch) {
		return toLong( String.valueOf(ch) );
	}


	//////////////////////////////////////////////////////////////////////////
	
	public static Double toDouble(boolean b) {
		if (b) {
//			return new Double(1.0);
			return Double.valueOf(1.0);
		}
		else {
//			return new Double(0.0);
			return Double.valueOf(0.0);
		}
	}

	public static Double toDouble(char ch) {
		return toDouble( String.valueOf(ch) );
	}

	public static Double toDouble(String s) {
		if (s == null || s.length() == 0) {
//			return new Double(0.0);
			return Double.valueOf(0.0);
		}
		else {
			return Double.valueOf(s);
		}
	}


	public static Double toDouble(StringBuffer s) {
		if (s == null) 
//			return new Double(0.0);
			return Double.valueOf(0.0);

		return Double.valueOf(s.toString());
	}


	public static Double toDouble(int n) {
//		return new Double(n);
		return Double.valueOf(n);
	}

	public static Double toDouble(Integer n) {
		if (n == null)
//			return new Double(0.0);
			return Double.valueOf(0.0);

		return n.doubleValue();
	}

	
	public static Double toDouble(long n) {
//		return new Double(n);
		return Double.valueOf(n);
	}
	
	public static Double toDouble(Long n) {
		if (n==null)
//			return new Double(0);
			return Double.valueOf(0);
		
		return n.doubleValue();
	}
	
	public static Double toDouble(double r) {
		return r;
	}

	public static Double toDouble(Double r) {
		return r;
	}
	
	public static Double toDouble(Object o) {

		Double ret = null;

		if (o == null ) {
//			ret = new Double(0.0);
			ret = Double.valueOf(0.0);
		}
		else if (isInteger(o)) {
			ret = toDouble((Integer)o);
		}
		else if (isLong(o)) {
			ret = toDouble((Long)o);
		}
		else if (isDouble(o)) {
			ret = (Double)o;
		}
		else if (isString(o)) {
			ret = toDouble((String)o);
		}

		return ret;

	}

	////////////////////////////////////////////////////////////////////////
	
	// -----------------------
	public static String toString(boolean b) {
		if (b)
			return new String("1");
		else
			return new String("0");
	}


	public static String toString(char ch) {
		return String.valueOf(ch);
	}

	public static String toString(String s) {
	        return s;
	}

	
	public static String toString(int n) {
		return String.valueOf(n);
	}


	public static String toString(long n) {
		return String.valueOf(n);
	}

	
	public static String toString(Integer n) {
		if (n == null) {
			return new String("0");
		}

		return String.valueOf(n);
	}


	public static String toString(int n, int nDigits) {
		StringBuffer format = new StringBuffer("%0");
		format.append(Type.toString(nDigits));
		format.append("d");
		return String.format(format.toString(), n);
	}

	public static String toString(int n, String format) {
		return String.format(format, n);
	}

	public static String toString(double r) {
		DoubleToString a = new DoubleToString();
		StringBuffer   s = new StringBuffer();
		a.append(s, r);
		return s.toString();
	}

	public static String toString(double r, String formatStr) {
		NumberFormat form = new DecimalFormat(formatStr);
		return form.format(r);
	}


	public static String toString(Double rD) {
		if (rD == null) {
			return new String("0.0");
		}
		else {
			return toString(rD.doubleValue());
		}
	}


	public static String toString(Object o) {
		if (o == null) {
			return new String("");
		}
		else {
			return o.toString();
		}

	}



	
	////////////////////////////////////////////////////////////////////
	// Comparing functions
	///////////////////////////////////////////////////////////////////
	
	public static boolean eq(Object val1, Object val2) {
		if (Type.isInteger(val1) || Type.isDouble(val1)) {
			double v1 = Type.to_double(val1);
			double v2 = Type.to_double(val2);
			return v1 == v2;
		}
		else if (Type.isString(val1)) {
			return val1.equals(val2);
		}
		return false;
	}


	public static boolean gt(Object val1, Object val2) {
		if (Type.isInteger(val1) || Type.isDouble(val1)) {
			double v1 = Type.to_double(val1);
			double v2 = Type.to_double(val2);
			return v1 > v2;
		}
		else if (Type.isString(val1)) {
			String v1 = Type.toString(val1);
			String v2 = Type.toString(val2);
			return v1.compareTo(v2) > 0;
		}
		return false;
	}


	public static boolean lt(Object val1, Object val2) {
		if (Type.isInteger(val1) || Type.isDouble(val1)) {
			double v1 = Type.to_double(val1);
			double v2 = Type.to_double(val2);
			return v1 < v2;
		}
		else if (Type.isString(val1)) {
			String v1 = Type.toString(val1);
			String v2 = Type.toString(val2);
			return v1.compareTo(v2) < 0;
		}
		return false;
	}

	////////////////////////////////////////////////////////////////////
	// Type checking functions
	///////////////////////////////////////////////////////////////////

	public static boolean isInteger(Object o) {
		if (o == null) {
			return false;
		}

		String  cl = o.getClass().getName();
		boolean ret = false;
		if (cl.compareTo("java.lang.Integer") == 0) {
			ret = true;
		}
		return ret;
	}
	
	

	public static boolean isLong(Object o) {
		if (o == null) {
			return false;
		}

		String  cl = o.getClass().getName();
		boolean ret = false;
		if (cl.compareTo("java.lang.Long") == 0) {
			ret = true;
		}
		return ret;
	}

	public static boolean isDouble(Object o) {
		if (o == null) {
			return false;
		}

		String  cl = o.getClass().getName();
		boolean ret = false;
		if (cl.equals("java.lang.Double")) {
			ret = true;
		}
		return ret;

	}

	public static boolean isString(Object o) {
		if (o == null) {
			return false;
		}

		String  cl = o.getClass().getName();
		boolean ret = false;
		if (cl.equals("java.lang.String")) {
			ret = true;
		}
		return ret;

	}

	public static boolean isStringBuffer(Object o) {
		if (o == null) {
			return false;
		}

		String  cl = o.getClass().getName();
		boolean ret = false;
		if (cl.equals("java.lang.StringBuffer")) {
			ret = true;
		}
		return ret;

	}


	public static boolean isBigDecimal(Object o) {
		if (o == null) {
			return false;
		}

		String  cl = o.getClass().getName();
		boolean ret = false;
		if (cl.equals("java.math.BigDecimal")) {
			ret = true;
		}
		return ret;

	}

	////////////////////////////////////////////////////////////////////
	// String type checking functions
	///////////////////////////////////////////////////////////////////

	public static boolean isStringOfDouble(String s) {

		boolean ret = false;
		try {
			Double.parseDouble(s);
			ret = true;
		}
		catch (NumberFormatException ex) {
			ret = false;
		}
		return ret;
	}

	public static boolean isStringOfInteger(String s) {


		boolean ret = false;
		try {
			Integer.parseInt(s);
			ret = true;
		}
		catch (NumberFormatException ex) {
			ret = false;
		}
		return ret;
	}

	public static boolean isStringBufferOfDouble(StringBuffer s) {
		boolean ret = false;
		try {
			Double.parseDouble(s.toString());
			ret = true;
		}
		catch (NumberFormatException ex) {
			ret = false;
		}
		return ret;
	}

	public static boolean isStringOfInteger(StringBuffer s) {
		boolean ret = false;
		try {
			Integer.parseInt(s.toString());
			ret = true;
		}
		catch (NumberFormatException ex) {
			ret = false;
		}
		return ret;
	}


	public static boolean isEmptyString(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isEmptyString(Object obj) {

		String str = null;
		if (obj == null) {
			return true;
		}
		else {
			str = toString(obj);
			return isEmptyString(str);
		}

	}

	///////////////////////////////////////////////////////////////
	
	public static char to_char(boolean b) {
		if (b) 
			return 1;
		else
			return 0;
	}

	public static char to_char(int n) {
		return (char)n;
	}

	public static char to_char(Integer nI) {
		return (char)nI.intValue();
	}

	public static char to_char(String s) {
		return (char)to_int(s);
	}

	public static char to_char(StringBuffer s) {
		return (char)to_int(s);
	}

	public static char to_char(Object o) {
		return (char)to_int(o);
	}

	
	
	///////////////////////////////////////////////////////////////
	
	public static int to_int(boolean b) {
		if (b) 
			return 1;
		else
			return 0;
	}


	public static int to_int(int n) {
		return n;
	}

	public static int to_int(Integer nI) {
		return nI.intValue();
	}

	public static int to_int(double r) {
//		Double rD = new Double(r);
		Double rD = Double.valueOf(r);
		return rD.intValue();
	}

	public static int to_int(Double rD) {
		if (rD == null) {
			return 0;
		}

		return rD.intValue();
	}

	public static int to_int(String s) {
		if (s == null) {
			return 0;
		}
		return Integer.valueOf(s).intValue();
	}

	public static int to_int(StringBuffer s) {
		if (s == null) {
			return 0;
		}

		return to_int(s.toString());
	}

	public static int to_int(char ch) {
		return to_int(String.valueOf(ch));
	}


	public static int to_int(Object o) {
		if (o == null) {
			return 0;
		}

		return to_int(toInteger(o));
	}

	
	

	/////////////////////////////////////////////////////////////////////
	
	public static long to_long(boolean b) {
		if (b) 
			return 1;
		else
			return 0;
	}

	public static long to_long(char ch) {
		return to_long( String.valueOf(ch) );
	}


	public static long to_long(int n) {
		return n;
	}

	public static long to_long(Integer nI) {
		return nI.longValue();
	}

	public static long to_long(double r) {
		return toDouble(r).longValue();
	}

	public static long to_long(Double rD) {
		if (rD == null) {
			return 0;
		}

		return rD.longValue();
	}

	public static long to_long(String s) {
		if (s == null) {
			return 0;
		}

		return to_long(toInteger(s));
	}

	public static long to_long(StringBuffer s) {
		if (s == null) {
			return 0;
		}

		return to_long(toInteger(s.toString()));
	}


	public static long to_long(Object o) {
		if (o == null) {
			return 0;
		}

		return to_long(toInteger(o));
	}


	// -----------------------
	public static float to_float(boolean b) {
		if (b)
			return 1.0f;
		else
			return 0.0f;
	}

	public static float to_float(char ch) {
		return to_float(String.valueOf(ch));
	}

	public static float to_float(String ds) {
		if (ds == null || ds.length() == 0) {
			return 0.0f;
		}
		else {
			return Float.parseFloat(ds);
		}
	}


	public static float to_float(StringBuffer ds) {
		if (ds == null) {
			return 0.0f;
		}
		return Float.parseFloat(ds.toString());
	}



	public static float to_float(int n) {
		return toInteger(n).floatValue();
	}

	public static float to_float(Integer n) {
		if (n == null) {
			return 0.0f;
		}

		return n.floatValue();
	}

	public static float to_float(double r) {
		return (float)r;
	}

	public static float to_float(Double rD) {
		if (rD == null) {
			return 0.0f;
		}

		return rD.floatValue();
	}

	public static float to_float(Object o) {
		if (o == null) {
			return 0.0f;
		}

		return to_float(toDouble(o));
	}


	// -----------------------
	public static double to_double(boolean b) {
		if (b)
			return 1.0;
		else
			return 0.0;
	}

	public static double to_double(char ch) {
		return to_double( String.valueOf(ch) );
	}

	public static double to_double(String ds) {
		if (ds == null || ds.length() == 0) {
			return 0.0;
		}
		else {
			return Double.parseDouble(ds);
		}
	}


	public static double to_double(StringBuffer ds) {
		if (ds == null) {
			return 0.0;
		}
		return Double.parseDouble(ds.toString());
	}



	public static double to_double(int n) {
		return toInteger(n).doubleValue();
	}

	public static double to_double(Integer n) {
		if (n == null) {
			return 0.0;
		}

		return n.doubleValue();
	}

	public static double to_double(double r) {
		return r;
	}

	public static double to_double(Double rD) {
		if (rD == null) {
			return 0.0;
		}

		return rD.doubleValue();
	}

	public static double to_double(Object o) {
		if (o == null) {
			return 0.0;
		}

		return to_double(toDouble(o));
	}



	public static StringBuffer toStringBuffer(char ch) {
		return new StringBuffer(Type.toString(ch));
	}

	public static StringBuffer toStringBuffer(String s) {
		if (s == null) {
			return new StringBuffer("");
		}
		return new StringBuffer(s);
	}


	public static StringBuffer toStringBuffer(int n) {
		return new StringBuffer(Type.toString(n));
	}

	public static StringBuffer toStringBuffer(Integer n) {
		if (n == null) {
			return new StringBuffer("0");
		}

		return new StringBuffer(Type.toString(n));
	}

	public static StringBuffer toStringBuffer(double r) {
		return new StringBuffer(Type.toString(r));
	}


	public static StringBuffer toStringBuffer(Double rD) {
		if (rD == null) {
			return new StringBuffer("0.0");
		}

		return new StringBuffer(Type.toString(rD));
	}


	public static StringBuffer toStringBuffer(Object o) {
		if (o == null) {
			return new StringBuffer("");
		}

		return new StringBuffer(Type.toString(o));
	}




	// -----------------------    
	/*   
    public static String toString(int n, int digit) {
        String ret = new String();
        if (n>=0) {
            String s = String.valueOf(n);
            int    d = digit - s.length();
            if (d > 0) {
                for (int i=0; i<d; i++) {
                    ret += "0";
                }
                ret += s;
            }
            else {
                ret = s;
            }
        }
        else {
            int    a = Math.abs(n);
            String s = String.valueOf(a);
            int    d = digit - s.length() - 1;
            if (d > 0) {
                ret += "-";
                for (int i=0; i<d; i++) {
                    ret += "0";
                }
                ret += s;
            }
            else {
                ret = String.valueOf(n);
            }
        }
        return ret;    
    }
	 */    

	// -----------------------
	public static ArrayList<Integer> toIntegerList(int[] array) {
		ArrayList<Integer> list = new ArrayList<Integer>(array.length);
		for (int i=0; i<array.length; i++) {
			list.add(array[i]);
		}    	
		return list;
	}



	public static <T> int[] to_int_array(ArrayList<T> lst) {

		int[] ret = new int[lst.size()];
		for (int i=0; i<lst.size(); i++) {
			ret[i] = to_int( toInteger(lst.get(i)) );
		}
		return ret;
	}
	

	public static <T> double[] to_double_array(ArrayList<T> lst) {

		double[] ret = new double[lst.size()];
		for (int i=0; i<lst.size(); i++) {
			ret[i] = to_double( toDouble(lst.get(i)) );
		}
		return ret;
	}



	public static <T> String[] toStringArray(ArrayList<T> lst) {
		String[] ret = new String[lst.size()];
		for (int i=0; i<lst.size(); i++) {
			ret[i] = toString(lst.get(i));
		}
		return ret;
	}

	// -----------------------


	public static ArrayList<String> toStringList(File[] f) {
		ArrayList<String> ret = new ArrayList<String>(f.length);
		for (int i=0; i<f.length; i++) {
			ret.set(i, f[i].getPath());
		}
		return ret;		
	}
	

	public static ArrayList<String> toStringList(String[] a) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i=0; i<a.length; i++) {
			ret.add(a[i]);
		}
		return ret;
	}

	public static ArrayList<String> toStringList(int[] a) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i=0; i<a.length; i++) {
			ret.add( toString(a[i]) );
		}
		return ret;
	}

	public static ArrayList<String> toStringList(double[] a) {
		ArrayList<String> ret = new ArrayList<String>();
		for (int i=0; i<a.length; i++) {
			ret.add( toString(a[i]) );
		}
		return ret;
	}

	public static String[] toArray(ArrayList<String> list) {
		String[] ret = new String[list.size()];
		for (int i=0; i<list.size(); i++) {
			ret[i] = list.get(i);
		}
		return ret;
	}

}
