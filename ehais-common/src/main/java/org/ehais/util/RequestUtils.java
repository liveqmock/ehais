package org.ehais.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam( HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	
	public static void printAllRequestParam(HttpServletRequest request){
		
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);				
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				System.out.println("ParameterNames数据的键=="+en+"     值==="+value);
				
			}
		}
	}
	
	
	
	/**
	 * 效验参数的sql注入安全性，ture即安全
	 * @param str
	 * @return
	 */
	public static boolean sqlValidate(String str) {  
        str = str.toLowerCase();//统一转为小写  
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +  
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +  
                "table|from|grant|use|group_concat|column_name|" +  
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +  
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加  
        String[] badStrs = badStr.split("\\|");  
        for (int i = 0; i < badStrs.length; i++) {  
            if (str.indexOf(badStrs[i]) >= 0) {  
                return false;  
            }  
        }  
        return true;  
    } 
	
}
