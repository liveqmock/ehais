package org.ehais.Junit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexJUnit {

	@Test
	public void pat() {
		int c = 0;
		String html = "8月1日李生从银行提取备用金3000元";
		Pattern p = Pattern.compile("([\\d])月([\\d])日([\\u4e00-\\u9fa5]*)从([\\u4e00-\\u9fa5]*)提取([\\u4e00-\\u9fa5]*)([0-9]\\d*)+元$");
		Matcher m = p.matcher(html);// 开始编译
		if(m.find()) {
			c = m.groupCount();
			System.out.println(c);
			for(int i = 0 ; i <= c ; i++) {
				System.out.println(i+" = "+m.group(i));
			}
		}
		
		

		System.out.println("完成");
	}
	
	@Test
	public void p2() {
		int c = 0;
		String str = "8月8日销售给万昌公司B产品一批货款150000元增值税25500元支票已存入银行";
		Pattern p = Pattern.compile("([\\d])月([\\d])日销售给([\\u4e00-\\u9fa5]*)公司(.*)产品([\\u4e00-\\u9fa5]*)批货款([0-9]\\d*)元增值税([0-9]\\d*)元([\\u4e00-\\u9fa5]*)已存入([\\u4e00-\\u9fa5]*)");
		Matcher m = p.matcher(str);// 开始编译
		if(m.find()) {
			c = m.groupCount();
			System.out.println(c);
			for(int i = 0 ; i <= c ; i++) {
				System.out.println(i+" = "+m.group(i));
			}
		}
		
		System.out.println("完成");
	}

}
