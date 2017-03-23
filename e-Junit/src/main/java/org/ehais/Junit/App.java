package org.ehais.Junit;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.ehais.util.EncryptUtils;
import org.junit.Test;

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

}
