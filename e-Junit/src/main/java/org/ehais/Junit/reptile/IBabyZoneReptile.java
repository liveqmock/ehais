package org.ehais.Junit.reptile;

import org.ehais.util.EHtmlUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class IBabyZoneReptile extends ArticleCommonReptile{
	private static String website = "http://yuer.ibabyzone.cn";
	public static String store_id = "9";
	public static String cat_name = "宝宝成长指标";
	public static String articleSource = "宝宝地带";
	
	
	
	@Test
	public void test_list(){
		String url = "http://yuer.ibabyzone.cn/weekly/";cat_name = "宝宝地带";
		this.list(url);
		
//		try {
//			Thread.sleep(1000000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	public void list(String url){
		System.out.println("=============="+url);
		try {
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Elements main = doc.getElementsByClass("main");
			Elements block = main.first().getElementsByClass("block");
			Elements table = block.first().select(">table");
			for (Element e : table) {
				Element td = e.getElementsByTag("td").first();
				System.out.println(td.text()+"/////////////////////////");
				
				Elements stable = e.getElementsByTag("table");
				Elements a = stable.first().getElementsByTag("a");
				int i =  0;
				for (Element element : a) {
					if(i>0){
						System.out.println("====="+element.text()+"----"+element.attr("href"));
						view(website+element.attr("href"));
					}
					i++;
					
				}
			}
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_view(){
		String url = "http://yuer.ibabyzone.cn/weekly/1.shtml";
		view(url);
	}
	
	public static void view(String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element blueintro = doc.getElementsByClass("blueintro").first();
			
			Elements guide = blueintro.getElementsByClass("guide");
			guide.remove();
			Element main_title = blueintro.getElementsByClass("title_bg").first();
			String title = main_title.text();
			main_title.remove();
			
//			Elements p = blueintro.getElementsByTag("p");
//			p.last().remove();
			
			String description = blueintro.text();
			
			
			System.out.println(title);
			System.out.println(description);
			
			
			article_save(store_id, cat_name, main_title.text(),"","", description, description,articleSource, url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
//	class TheadReptile implements Runnable{
//		
//		private String link;
//		private String pic;
//		
//		public TheadReptile(String link,String pic){
//			this.link = link;
//			this.pic = pic;
//		}
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			WineWorldReptile.view(link,pic);
//		}
//		
//	}
	
}

