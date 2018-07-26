package org.ehais.Junit;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ehais.util.DateUtil;
import org.ehais.util.EncryptUtils;
import org.junit.Test;

import net.sf.json.JSONArray;

/**
 * Hello world!
 *	反映射，变量转变函数
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		int a1 = 0;
		int a2 = 0;
		for (int i = 0; i < 100; i++) {
			a1 = (8 * i) % 24;
			a2 = (3 * i) % 24;
			if (a1 == a2) {
				System.out.println(i + "秒");
			}
		}
	}

	public void main_app() {
		System.out.println("Hello World!");
		int a1 = 0;
		int a2 = 0;
		for (int i = 0; i < 100; i++) {
			a1 = (8 * i) % 24;
			a2 = (3 * i) % 24;
			if (a1 == a2) {
				System.out.println(i + "秒");
			}
		}
	}

	public void foo(String name) {
		System.out.println("Hello, " + name);
	}

	@Test
	public void test() throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			InstantiationException {
		Class<?> clz = Class.forName("org.ehais.Junit.App");
		Object o = clz.newInstance();
		Method m = clz.getMethod("foo", String.class);
		for (int i = 0; i < 16; i++) {
			m.invoke(o, Integer.toString(i));
		}

	}
	
	@Test
	public void ecshopmd5() throws UnsupportedEncodingException{
		String pwd = "admin888";
		pwd = EncryptUtils.md5(pwd);
		System.out.println(pwd);
		pwd = EncryptUtils.md5(pwd+"2294");
		System.out.println(pwd);
	}
	
	@Test
	public void tmain() {
		for(int i = 0 ; i < 211 ;i ++){
			System.out.println("gData.add(new GoodsBean(\"梦之炫N件套"+String.format("%03d", i)+"\",\"201605320"+String.format("%03d", i) +"\",R.drawable.goods"+(i%10)+",0));");
		}
	}

	@Test
	public void arr(){
		List<List<String>> list = new ArrayList<List<String>>();
		for(Integer i = 0 ; i < 3 ; i++){
			List<String> l = new ArrayList<String>();
			l.add(i.toString());
			l.add(i+"==");
			list.add(l);
		}
		JSONArray arr = JSONArray.fromObject(list);
		System.out.println(arr.toString());
	}
	
	@Test
	public void jtype(){
		Integer fee = 1;
		long l = fee.longValue() / 100;
		float f = fee.floatValue() /100;
		double d = fee.doubleValue() / 100;
		
		System.out.println(l);
		System.out.println(f);
		System.out.println(d);
		
		System.out.println(String.format("%.2f", f));
		System.out.println(String.format("%.2f", d));
		System.out.println(String.format("%.2f", fee.doubleValue() / 100));
	}
	
	@Test
	public void timestamp(){
		String date = "2017-10-20 00:00:00";
		Date d = DateUtil.formatDate(date, DateUtil.FORMATSTR_2);
		System.out.println(d.getTime());
		
		date = "2017-10-20 23:59:59";
		d = DateUtil.formatDate(date, DateUtil.FORMATSTR_2);
		System.out.println(d.getTime());
		
		
		date = "2017-10-19 00:00:00";
		d = DateUtil.formatDate(date, DateUtil.FORMATSTR_2);
		System.out.println(d.getTime());
		
		date = "2017-10-18 00:00:00";
		d = DateUtil.formatDate(date, DateUtil.FORMATSTR_2);
		System.out.println(d.getTime());
		
		
	}
	
	@Test
	public void listtostring(){
		List<String> list = new ArrayList<String>();
		list.add("AA");
//		list.add("BB");
//		list.add("CC");
//		list.add("DD");
//		list.add("EE");
//		list.add("FF");
		
		String str = StringUtils.join(list.toArray(), ","); 
		System.out.println(str);
		
		List<String> result = Arrays.asList(StringUtils.split(str,",")); 
		for (String string : result) {
			System.out.println(string);
		}
		
		JSONArray arr = JSONArray.fromObject(list);
		System.out.println(arr.toString());
		
		JSONArray arr2 = JSONArray.fromObject(arr.toString());
		System.out.println(arr2.toString());
	}
	
	@Test
	public void longstr() {
		String s = "76.01";
		Double l = Double.valueOf(s);
		System.out.println(l);
	}
	
	@Test
	public void date_t() {
		try {
			Date date = new Date();
			System.out.println(date);
			System.out.println(DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
			Date d = DateUtil.addSecond(date, 1);
			System.out.println(DateUtil.formatDate(d, DateUtil.FORMATSTR_2));
			
			Date da = DateUtil.addDate(date, 1);
			System.out.println(DateUtil.formatDate(da, DateUtil.FORMATSTR_2));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
