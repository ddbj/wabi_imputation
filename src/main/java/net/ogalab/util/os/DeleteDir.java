package net.ogalab.util.os;

import java.io.File;

class DeleteDir {
	
	static String dir = "/tmp/testdb";
	
  public static void main(String args[]) {
    deleteDirectory(new File(dir));
  }

  /** Delete a directory recursively.
   * 
   * @param path
   * @return
   */
  static public boolean deleteDirectory(File path) {
    if( path.exists() ) {
      File[] files = path.listFiles();
      for(int i=0; i<files.length; i++) {
         if(files[i].isDirectory()) {
           deleteDirectory(files[i]);
         }
         else {
           files[i].delete();
         }
      }
    }
    return( path.delete() );
  }
}