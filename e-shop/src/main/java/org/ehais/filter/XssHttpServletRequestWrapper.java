package org.ehais.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {  
   public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
       super(servletRequest);
   }
   public String[] getParameterValues(String parameter) {
     String[] values = super.getParameterValues(parameter);
     if (values==null)  {
        return null;
     }
     
     int count = values.length;
     String[] encodedValues = new String[count];
     for (int i = 0; i < count; i++) {
         encodedValues[i] = cleanXSS(values[i]);
     }
     return encodedValues;
   }
   public String getParameter(String parameter) {
         String value = super.getParameter(parameter);
         if (value == null) {
         	return null;
         }
         
         return cleanXSS(value);
   }
   public String getHeader(String name) {
       String value = super.getHeader(name);
       if (value == null)
           return null;
       
       return cleanXSS(value);
   }
   private String cleanXSS(String value) {
               //You'll need to remove the spaces from the html entities below
       value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
       value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
       value = value.replaceAll("'", "& #39;");
       value = value.replaceAll("eval\\((.*)\\)", "");
       value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
       value = value.replaceAll("script", "");
       
       return value;
   }


//   private String cleanSQL(String value) {
////	   return StringEscapeUtils.escapeEcmaScript(value);
//	   return StringEscapeUtils.escapeSql(value);
//   }
   
   
// 效验
	protected static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		str.replaceAll("--","——");
		str.replaceAll("'","’"); 
		String badStr = "'|or |exec|execute|insert|select|delete|update|master|truncate|javascript|count(*)|"
				+ "declare|create|" + "grant|script|iframe" + "|--";// 过滤掉的sql关键字，可以手动添加
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (str.indexOf(badStrs[i].toLowerCase()) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	

} 
