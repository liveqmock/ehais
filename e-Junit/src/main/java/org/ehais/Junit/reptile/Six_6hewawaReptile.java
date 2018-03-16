package org.ehais.Junit.reptile;

import org.ehais.util.EHtmlUnit;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class Six_6hewawaReptile extends ArticleCommonReptile{
	
	String url = "https://www.6hewawa.com/2016.htm";
	
	@Test
	public void list() {
		
		try {
			String result = EHtmlUnit.getAjaxPage(url);
			System.out.println(result);
			Document doc = Jsoup.parse(result,"utf-8");
			String content = doc.getElementsByClass("content").text();
//			content = content.replaceAll("<br/>", "");
			System.out.println(content);
			String list[] = content.split("\n");
//			String list[] = content.split("<br/>");
			int i = 0;
			for (String string : list) {
				T2016(string,i);
				i++;
//				System.out.println(no+"====="+string.substring(9));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void T2018(String string,int i) {
		String no = string.substring(0,3);
		
		System.out.print(i+"["+no+"]   ");
		
		String nos = string.substring(9);
		
		String[] nt = nos.split("特：");
		
		String[] nl = nt[0].split("-");
		for (String string2 : nl) {
			System.out.print(string2.substring(0,2)+"+");
		}
		
		String t = nt[1].substring(0, 2);
		System.out.print("T:"+t);
		
		System.out.println("");
		System.out.println("--------------------------------------------------");
	}

	
	private void T2017(String string,int i) {
		if(i<73) {
			T2018(string,i);
		}else {
			T2016(string,i);
		}
	}
	
	private void T2016(String string,int i) {
		String no = string.substring(0,3);
		System.out.print(i+"["+no+"]   ");
		
		String nos = string.substring(5);
		String[] nt = nos.split("特");
		String[] nl = nt[0].split("-");
		for (String string2 : nl) {
			System.out.print(string2.substring(0,2)+"+");
		}
		
		String t = nt[1].substring(0, 2);
		System.out.print("T:"+t);
		
		System.out.println("");
		System.out.println("--------------------------------------------------");
	}
	
	
	
	
	
	
	
}
