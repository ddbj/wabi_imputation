package net.ogalab.util.exception;

public class RuntimeExceptionUtil {
	
	public static void invoke(Exception e, String msg) {
		//e.printStackTrace();
		throw new RuntimeException(msg + getStackTraceString(e));
	}

	public static void invoke(String msg) {
		throw new RuntimeException(msg);
	}
	
	public static void invoke(Exception e) {
		throw new RuntimeException("Runtime Exception." + getStackTraceString(e));
	}
	
	
	public static String getStackTraceString(Exception e) {
		StringBuilder b = new StringBuilder();
		StackTraceElement[] traceArray = e.getStackTrace();
		b.append("\n");
		for (StackTraceElement elem : traceArray) {
			b.append( elem.toString() + "\n");
		}
		return b.toString();
	}
	
}
