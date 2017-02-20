package org.ehais.Junit;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Enumeration;

import org.ehais.model.ExtendsFieldsGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 获取javabean的属性与类型
 * 
 * @author gzepro
 *
 */
public class ModelPropertyJunit {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		PropertyDescriptor[] props = null;
		ExtendsFieldsGroup group = new ExtendsFieldsGroup();
		group.setCode("SSSS");
		props = Introspector.getBeanInfo(group.getClass(), Object.class).getPropertyDescriptors();
		
		if (props != null) {
			for (int i = 0; i < props.length; i++) {
				String aa = props[i].getName();// 获取bean中的属性
//				Enumeration<String> e = props[i].attributeNames();
				Object object = props[i].getPropertyType();// 获取属性的类型
				Method method = props[i].getReadMethod();
				
				Object value = method.invoke(group, new Object[] {});
				System.out.println(aa + "  ==  " + object + ";;;" + method.getName() + " -- " + value);
			}
		}

		Field[] fields = group.getClass().getDeclaredFields();
		Method[] methods = group.getClass().getDeclaredMethods();

		System.out.println("--------------- 属性如下  -----------------");
		for (Field field : fields) {
			int mod = field.getModifiers();
			System.out.println(Modifier.toString(mod) // 取得修饰符
					+ " " + field.getType().getName() // 取得类型名
					+ " " + field.getName() + " " ); // 取得属性名 //+ field.get(group)
			
		}
//		System.out.println("--------------- 方法如下 -----------------");
//		for (Method method : methods) {
//
//			StringBuffer methodBuffer = new StringBuffer();
//			int mod = method.getModifiers();
//			methodBuffer.append(Modifier.toString(mod)) // 取得修饰符
//					.append(" ").append(method.getReturnType().getName()) // 取得返回值类型
//					.append(" ").append(method.getName()) // 取得方法名
//					.append("(");
//			for (Class ss : method.getParameterTypes()) {
//				methodBuffer.append(ss.getName()).append(","); // 取得参数
//			}
//			if (methodBuffer.lastIndexOf(",") >= 0)
//				methodBuffer.deleteCharAt(methodBuffer.lastIndexOf(",")); // 去掉最后一个","
//			methodBuffer.append(")");
//			System.out.println(methodBuffer.toString()); // 打印输出
//		}

	}

	public static void getClassInfo(Object obj) throws Exception {
		Class clas = Class.forName("com.landray.kmss.km.mdm.maindata.jdbc.tools.TestBean");
		if (!(clas.isInstance(obj))) {
			System.out.println("传入的java实例与配置的java对象类型不符！");
			return;
		}
		Field[] fields = clas.getDeclaredFields();
		Method[] methods = clas.getDeclaredMethods();
		System.out.println("--------------- 属性如下  -----------------");
		for (Field field : fields) {
			int mod = field.getModifiers();
			System.out.println(Modifier.toString(mod) // 取得修饰符
					+ " " + field.getType().getName() // 取得类型名
					+ " " + field.getName() + " " + field.get(obj)); // 取得属性名
		}
		System.out.println("--------------- 方法如下 -----------------");
		for (Method method : methods) {

			StringBuffer methodBuffer = new StringBuffer();
			int mod = method.getModifiers();
			methodBuffer.append(Modifier.toString(mod)) // 取得修饰符
					.append(" ").append(method.getReturnType().getName()) // 取得返回值类型
					.append(" ").append(method.getName()) // 取得方法名
					.append("(");
			for (Class ss : method.getParameterTypes()) {
				methodBuffer.append(ss.getName()).append(","); // 取得参数
			}
			if (methodBuffer.lastIndexOf(",") >= 0)
				methodBuffer.deleteCharAt(methodBuffer.lastIndexOf(",")); // 去掉最后一个","
			methodBuffer.append(")");
			System.out.println(methodBuffer.toString()); // 打印输出
		}

	}

}
