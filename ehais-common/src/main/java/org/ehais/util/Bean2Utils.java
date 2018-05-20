package org.ehais.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.ehais.annotation.EColumn;
import org.ehais.model.Menu;



/**
 * 多种model与map互转对象
 * @author tyler
 *
 */
public class Bean2Utils {

	public static <T> Map<String,Object> printEntity(T obj){
                
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
                    System.out.println(key +" == "+ value);
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        } 
        
        return map;
    }
	
	
    public static <T> Map<String,Object> toMap(T t){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
            	field.setAccessible(true);
            	Object obj = field.get(t);
                map.put(field.getName(), obj); 
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    public static <T> Map<String,String> toMapString(T t){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
            	field.setAccessible(true);
            	Object obj = field.get(t);
                if(obj!=null)map.put(field.getName(), obj.toString()); 
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    
    public static <T> Map<String,Object> toSignMap(T t){
    	 Map<String,Object> map = new HashMap<String, Object>();
         Field[] fields = t.getClass().getDeclaredFields();
         for (Field field : fields) {
        	 if(field.getName().equals("sign"))continue;
        	 if(field.getName().toLowerCase().equals("serialversionuid"))continue;
             try {
             	field.setAccessible(true);
             	Object obj = field.get(t);
                 if(obj!=null){
                	 map.put(field.getName(), obj.toString()); 
                 }
             } catch (IllegalArgumentException e) {
                 e.printStackTrace();
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
         }
         return map;
    }
    
    /**
     * 直接返回httpclient post的可用参数
     * @param t
     * @return
     */
    public static <T> List<NameValuePair> toNameValuePairList(T t){
    	List<NameValuePair> reqEntity = new ArrayList<NameValuePair>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
            	field.setAccessible(true);
            	Object obj = field.get(t);
                if(obj!=null)reqEntity.add(new BasicNameValuePair(field.getName(), obj.toString())); 
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return reqEntity;
    }
    
    public static <T> Map<String,String> toMapInterfaceString(T t){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
            	field.setAccessible(true);
            	Object obj = field.get(t);
            	EColumn column = field.getAnnotation(EColumn.class);
                if(obj!=null && column != null){                	
                	map.put(column.name(), obj.toString()); 
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    
    public static <T> Map<String,Object> beanToMap(T obj){
    	if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        } 
        return map;
    }
    

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean  
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {  
        if (map == null || obj == null) {  
            return;  
        }  
        try {  
            BeanUtils.populate(obj, map);  
        } catch (Exception e) {  
            System.out.println("transMap2Bean2 Error " + e);  
        }  
    }  
  
    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean  
    public static void transMap2Bean(Map<String, Object> map, Object obj) {  
  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                if (map.containsKey(key)) {  
                    Object value = map.get(key);  
                    // 得到property对应的setter方法  
                    Method setter = property.getWriteMethod();  
                    setter.invoke(obj, value);  
                }  
  
            }  
  
        } catch (Exception e) {  
            System.out.println("transMap2Bean Error " + e);  
        }  
  
        return;  
  
    }  
  
    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map  
    public static Map<String, Object> transBean2Map(Object obj) {  
  
        if(obj == null){  
            return null;  
        }          
        Map<String, Object> map = new HashMap<String, Object>();  
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String key = property.getName();  
  
                // 过滤class属性  
                if (!key.equals("class")) {  
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();  
                    Object value = getter.invoke(obj);  
  
                    map.put(key, value);  
                }  
  
            }  
        } catch (Exception e) {  
            System.out.println("transBean2Map Error " + e);  
        }  
  
        return map;  
  
    }  
    
    public static Object mapToBean(Map<String, Object> map, Class cls) throws Exception
    {
        Object object = cls.newInstance();
        for (String key : map.keySet()){
            Field temFiels = cls.getDeclaredField(key);
            temFiels.setAccessible(true);
            temFiels.set(object, map.get(key));
        }
        return object;
    }
    
    /**
     * 遍历打印查看map
     * @param map
     */
    public static void printMap(Map<String,Object> map){
    	if(map == null){
    		System.out.println("map is null");
    		return ;
    	}
    	for (Map.Entry<String,Object> m : map.entrySet()) {
			System.out.println(m.getKey()+"=="+m.getValue());
		}
    }
    
    
    
    public static Object convertBean2Bean(Object from, Object to) {   
        try {   
            BeanInfo beanInfo = Introspector.getBeanInfo(to.getClass());   
            PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();   
  
            for (PropertyDescriptor p : ps) {   
               Method getMethod = p.getReadMethod();   
               Method setMethod = p.getWriteMethod();   
               if (getMethod != null && setMethod != null) {   
                   try {   
                      Object result = getMethod.invoke(from);   
                      setMethod.invoke(to, result);   
                   } catch (Exception e) {   
                      // 如果from没有此属性的get方法，跳过   
                      continue;   
                   }   
               }   
            }   
        } catch (Exception e) {   
           e.printStackTrace();   
        }   
  
        return to;   
    }
    
    public static Object coverBean2Bean(Object from, Object to) {   
        Class fClass = from.getClass();   
        Class tClass = to.getClass();   
        // 拿to所有属性（如果有继承，父类属性拿不到）   
        Field[] cFields = tClass.getDeclaredFields();   
        try {   
            for (Field field : cFields) {   
               String cKey = field.getName();   
               // 确定第一个字母大写   
               cKey = cKey.substring(0, 1).toUpperCase() + cKey.substring(1);   
  
               Method fMethod;   
               Object fValue;   
               try {   
                    fMethod = fClass.getMethod("get" + cKey);// public方法   
                    fValue = fMethod.invoke(from);// 取getfKey的值   
               } catch (Exception e) {   
                 // 如果from没有此属性的get方法，跳过   
                 continue;   
               }   
  
                try {   
                    // 用fMethod.getReturnType()，而不用fValue.getClass()   
                    // 为了保证get方法时，参数类型是基本类型而传入对象时会找不到方法   
                    Method cMethod = tClass.getMethod("set" + cKey, fMethod.getReturnType());   
                    cMethod.invoke(to, fValue);   
                } catch (Exception e) {   
                    // 如果to没有此属性set方法，跳过   
                    continue;   
                }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
        return to;   
    } 
    
    public static void main(String[] args) {
    	Menu menu = new Menu();
    	menu.setId("xdf");
    	menu.setTitle("wq lsdf");
    	
//    	Map<String,Object> map = Bean2Utils.beanToMap(menu);
    	Map<String,Object> map = Bean2Utils.toMap(menu);
    	
    	for (Map.Entry<String,Object> m : map.entrySet()) {
			System.out.println(m.getKey()+"=="+m.getValue());
		}
// 
//        Map<String, Object> mp = new HashMap<String, Object>();  
//        mp.put("name", "Mike");  
//        mp.put("age", 25);  
//        mp.put("mN", "male");  
//  
//        // 将map转换为bean  
//        transMap2Bean2(mp, person);  
//  
//        System.out.println("--- transMap2Bean Map Info: ");  
//        for (Map.Entry<String, Object> entry : mp.entrySet()) {  
//            System.out.println(entry.getKey() + ": " + entry.getValue());  
//        }  
//  
//        System.out.println("--- Bean Info: ");  
//        System.out.println("name: " + person.getName());  
//        System.out.println("age: " + person.getAge());  
//        System.out.println("mN: " + person.getmN());  
//  
//        // 将javaBean 转换为map  
//        Map<String, Object> map = transBean2Map(person);  
//  
//        System.out.println("--- transBean2Map Map Info: ");  
//        for (Map.Entry<String, Object> entry : map.entrySet()) {  
//            System.out.println(entry.getKey() + ": " + entry.getValue());  
//        }  
  
    }  
  
    
}
