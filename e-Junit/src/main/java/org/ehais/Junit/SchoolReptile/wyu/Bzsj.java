package org.ehais.Junit.SchoolReptile.wyu;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class Bzsj extends ArticleCommonReptile {

	private static String website = "http://kc.wyu.cn/bzsj";
	public static String store_id = "5006";
	public static String cat_name = "教学团队";
	public static String articleSource = "包装设计";
	
	
	@Test
	public void listjunit(){
		this.list(website+"/List.asp?ID=1");
	}
	
	
	private void list(String url){
		System.out.println(url);
		try {
			String html = EHttpClientUtil.methodGet(url);
			System.out.println(html);
//			Document doc = Jsoup.connect(url).get();
//			System.out.println(doc.html());
			System.out.println("success");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
