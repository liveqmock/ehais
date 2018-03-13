package org.ehais.Junit.reptile;

import org.ehais.util.EHtmlUnit;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class Six_6hewawaReptile extends ArticleCommonReptile{
	
	String url = "https://www.6hewawa.com/2018.htm";
	
	@Test
	public void list() {
		
		try {
			String result = EHtmlUnit.getAjaxPage(url);
//			System.out.println(content);
			Document doc = Jsoup.parse(result,"utf-8");
			String content = doc.getElementsByClass("content").text();
//			System.out.println(content);
			String list[] = content.split("\n");
			
			for (String string : list) {
				System.out.println(string.substring(0,3)+"====="+string.substring(9));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
