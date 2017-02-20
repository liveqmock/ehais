package org.ehais.util;

import java.util.HashMap;
import java.util.Map;



public class StringUtil {

	public static boolean NullAndEqual(String str){
		return str == null && str.equals("");
	}
	
	public static boolean NullOrEqual(String str){
		return str == null || str.equals("");
	}
	
	public static boolean NotNullAndEqual(String str){
		return str != null && !str.equals("");
	}
	
	public static boolean isEqual(String strOne,String strTwo){
		String one = strOne == null ? "" : strOne;
		String two = strTwo == null ? "" : strTwo;
		if(one.equals(two)){
			return true;
		}else{
			return false;
		}		
	}
	
	
	/**首字母小写
	 * @param str
	 * @return
	 */
	public static String firstLowerCase(String str){
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}
	
	/**
	 * 首字母大写
	 * @param str
	 * @return
	 */
	public static String firstUpperCase(String str){
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	
	/*第一种方法 
    获取abc字符串在整个字符串中出现的次数。 
    "wabcerabctyabcuiabcabcqq" 
    */  
    public static int getCount(String str,String sub)  
    {  
        int index = 0;  
        int count = 0;  
        while((index = str.indexOf(sub,index))!=-1)  
        {  
      
            index = index + sub.length();  
            count++;  
        }  
        return count;  
    }  
          /*第二种方法*/  
    public static int getCount2(String str,String sub)  
    {  
        int index = 0;  
        int count = 0;  
  
        while((index=str.indexOf(sub))!=-1)  
        {  
            str = str.substring(index+sub.length());  
            count++;  
        }  
        return count;  
    }
    
    
	public static String fieldUpperCase(String str){
		int count = getCount2(str,"_");
		for(int i = 0 ; i < count ; i++){
			int in = str.indexOf("_");
			str = str.substring(0,in)+(str.length() > (in+1)?firstUpperCase(str.substring(in+1,str.length())):"");
		}
		return str;
	}
	
	public static Map<String,String> dbType(String field,String type,String fKey){
		Map<String,String> map = new HashMap<String,String>();
		map.put("format", "");
		map.put("fkey", "");
		if(fKey!=null && !fKey.equals("") && fKey.toUpperCase().equals("PRI")){
			map.put("fkey", "@Id	\r\n	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)");
		}
		if(type.indexOf("int") == 0){
			map.put("column", "@Column(name = \""+field+"\")");
			map.put("type", "Integer");
		}else if(type.indexOf("varchar") == 0){
			map.put("column", "@Column(name = \""+field+"\",length = "+type.replace("varchar", "").replace("(", "").replace(")", "")+")");
			map.put("type", "String");
		}else if(type.indexOf("text") == 0){
			map.put("column", "@Column(name = \""+field+"\", length = 65535)");
			map.put("type", "String");
		}else if(type.indexOf("datetime") == 0){
			map.put("column", "@Temporal(TemporalType.TIMESTAMP)\r\n	@Column(name = \""+field+"\", length = 19)");
			map.put("format", "@DateTimeFormat( pattern = \"yyyy-MM-dd\" )");
			map.put("type", "Date");
		}
		
		return map;
	}
	
}
