package org.ehais.Junit.SchoolReptile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ehais.util.EHtmlUnit;
import org.junit.Test;

public class GuangMeiReptileJunit {

	@Test
	public void info() throws Exception {
		String content = EHtmlUnit.getPage("http://127.0.0.1:8082/guangmei/info.html");
		String pattern = "<font[\\s][^>]*?>.*?</font>";

		boolean isMatch = Pattern.matches(pattern, content);

		Pattern p = Pattern.compile(pattern);
		System.out.println(isMatch);

		Matcher m = p.matcher(content);// 开始编译
		while (m.find()) {
			System.out.println(m.group(0));// 获取被匹配的部分
		}
		
	}

}
