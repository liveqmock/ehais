package org.ehais.Junit.reptile;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.ehais.util.EHtmlUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Tea368Reptile extends ArticleCommonReptile {
	private static String website = "http://www.368tea.com";
	public static String store_id = "23";
	public static String cat_name = "茶叶知识";
	public static String articleSource = "雅茗居";
	
	
	@Test
	public void test_list(){
		String url = "http://www.368tea.com/html/178/index.htm";
		this.list(url);
	}
	
	public void list(String url){
		System.out.println("=============="+url);
		try {
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element list_article = doc.getElementById("list_article");
			Elements a = list_article.getElementsByTag("a");
			Set<String> set = new HashSet<String>();
			
			for (Element e : a) {
				set.add(e.attr("href"));
			}
			
			for (String string : set) {
				System.out.println(string);
				
				view(string);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_view(){
		String url = "http://www.368tea.com/html/294-57/57055.htm";
		view(url);
	}
	
	public static void view(String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element view_article = doc.getElementById("view_article");
			if(view_article == null)return ;
			
			Element main_title = view_article.getElementsByClass("main_title").first();
			System.out.println(main_title.text());
			
			Element read_tpc = doc.getElementById("read_tpc");
			read_tpc.getElementsByTag("script").remove();
			read_tpc.getElementsByTag("br").remove();			
			read_tpc.getElementsByTag("a").attr("href", "javascript:;");			
			read_tpc.getElementById("showinfo").remove();
			
			Elements div= read_tpc.getElementsByTag("div");
			for (Element element : div) {
				if(StringUtils.isBlank(element.text()))element.remove();
			}
			
			System.out.println(read_tpc.html());
			
			article_save(store_id, cat_name, main_title.text(),"","", "", read_tpc.html(),articleSource, url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	class TheadReptile implements Runnable{
		
		private String link;
		
		public TheadReptile(String link){
			this.link = link;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Tea368Reptile.view(link);
		}
		
	}


	
}
