package org.ehais.util;

import java.util.Map;

/**
 * 遍历打印map对象
 * @author lgj628
 *
 */
public class EachMapUtils {

	public static void printMapObject(Map<String,Object> map){
		for (Map.Entry<String,Object> entry : map.entrySet()) {  			  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  		  
		} 
	}
	
	public static void printMapString(Map<String,String> map){
		for (Map.Entry<String,String> entry : map.entrySet()) {  			  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  		  
		} 
	}
	
	public static void printMapInteger(Map<String,Integer> map){
		for (Map.Entry<String,Integer> entry : map.entrySet()) {  			  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  		  
		} 
	}
}
