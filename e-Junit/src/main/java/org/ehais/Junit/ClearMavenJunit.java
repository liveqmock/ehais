package org.ehais.Junit;

import java.io.File;

public class ClearMavenJunit {

	
	public static void main(String[] args) {

		
		delMaven(new File("~/Users/ellenfeng/.m2/repository"));
		
		

	}
	
	/**
	 * 删除目录中的.svn目录
	 * 
	 * @param f
	 */
	public static void delMaven(File f) {
	    File[] files = f.listFiles();
	    if (files != null)
	        for (File file : files)	    
	            if (file.getName().lastIndexOf(".lastUpdated") == 0)
	                del(file);
	            else
	            	delMaven(file);
	}
	
	
	public static void delBuild(File f) {
	    File[] files = f.listFiles();
	    if (files != null)
	        for (File file : files)	    
	            if ("build".equals(file.getName()))
	                del(file);
	            else
	            	delMaven(file);
	}
	
	/**
	 * 删除文件或目录
	 */
	public static void del(File file) {
	    File[] files = file.listFiles();
	    if (files != null)
	        for (File f : files)
	            del(f);
	    System.out.println("delete file name:"+file.getPath());
	    file.delete();
	    
	}
}
