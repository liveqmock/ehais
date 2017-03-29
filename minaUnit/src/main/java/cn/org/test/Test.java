package cn.org.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ct =	new ClassPathXmlApplicationContext("applicationContext-mina.xml");
	}

}
