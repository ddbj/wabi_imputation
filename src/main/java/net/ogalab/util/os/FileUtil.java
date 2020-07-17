package net.ogalab.util.os;

import java.io.File;
import java.util.ArrayList;

public class FileUtil {
	
	

	public static ArrayList<String> parsePathString(String path) {
		File f = new File(path);
		String name = f.getName();
		String dir  = f.getParent();
		
		ArrayList<String> result = new ArrayList<String>();
		result.add(name);
		result.add(dir);
		
		return result;
	}

	
	
	public static boolean exists(String path) {
		File fObj = new File(path);
		return fObj.exists();
	}
	
	
	public static void mkdir(String dir) {
		File fObj = new File(dir);
		if (!fObj.exists())
			fObj.mkdir();
	}
	
	public static void mkdirs(String dir) {
		File fObj = new File(dir);
		if (!fObj.exists())
			fObj.mkdirs();
	}
	
	public static void rmDirs(String dir) {
		File fObj = new File(dir);
		DeleteDir.deleteDirectory(fObj);
	}
	
	public static String getHomeDir() {
		return System.getenv("HOME") + File.separator;
	}


}
