package org.ehais.util;

import java.util.Map;

/**
 * 遍历打印map对象
 * @author lgj628
 *
 */
public class EachMapUtils {

	public static void printMap(Map<String,Object> map){
		for (Map.Entry<String,Object> entry : map.entrySet()) {  			  
		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  		  
		} 
	}
}
