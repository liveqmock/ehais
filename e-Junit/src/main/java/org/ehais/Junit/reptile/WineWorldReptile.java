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

public class WineWorldReptile extends ArticleCommonReptile{
	private static String website = "http://www.wine-world.com";
	public static String store_id = "20";
	public static String cat_name = "健康养生";
	public static String articleSource = "红酒世界网";
	
	
	
	@Test
	public void test_list(){
		String url = "http://www.wine-world.com/topic/112849260";cat_name = "健康养生";
		url = "http://www.wine-world.com/life";cat_name="红酒生活";
		url = "http://www.wine-world.com/culture/jk";cat_name="健康品酒";
		url = "http://www.wine-world.com/culture/pz";cat_name="品种知识";
		this.list(url);
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void list(String url){
		System.out.println("=============="+url);
		try {
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
//			Elements Lbox = doc.getElementsByClass("Lbox");
			Element leftContainer = doc.getElementById("leftContainer");
			Element banner = leftContainer.getElementById("banner");
			if(banner!=null)banner.remove();
			
			Element outline = leftContainer.getElementById("outline");
			if(outline!=null)outline.remove();
			
			Element Template4 = leftContainer.getElementById("Template4");
			if(Template4!=null)Template4.remove();
			
//			Elements template = leftContainer.getElementsByClass("template");
			Elements dl = leftContainer.getElementsByTag("dl");
			
			for (Element e : dl) {
				Elements a = e.getElementsByTag("a");
				Elements img = e.getElementsByTag("img");
				new Thread(new TheadReptile(a.first().attr("href"),img.first().attr("realsrc"))).start();
				
			}
			
			Elements pages = doc.getElementsByClass("pages");
			Elements pages_a = pages.first().getElementsByTag("a");
			for (Element element : pages_a) {
				if(element.text().equals("下一页")){
					this.list(website+element.attr("href"));
				}
			}
			


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test_view(){
		String url = "http://www.wine-world.com/topic/112849260/20130815175711475";
		view(url,"");
	}
	
	public static void view(String url,String articleImages){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element leftContainer = doc.getElementById("leftContainer");
			if(leftContainer == null)return ;
			
			Element main_title = leftContainer.getElementsByTag("h1").first();
						
			Element wkBody = leftContainer.getElementsByClass("wkBody").first();
			
			Element panelSummary = wkBody.getElementById("panelSummary");
			panelSummary.getElementsByTag("span").remove();
			String description = panelSummary.text();
			panelSummary.remove();
			
			Element pnlWineVersion2 = doc.getElementById("pnlWineVersion2");
			pnlWineVersion2.remove();
			
			System.out.println(main_title.text());
			System.out.println(description);
			System.out.println(wkBody.html());
			
			
			article_save(store_id, cat_name, main_title.text(),"",articleImages, description, wkBody.html(),articleSource, url);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	class TheadReptile implements Runnable{
		
		private String link;
		private String pic;
		
		public TheadReptile(String link,String pic){
			this.link = link;
			this.pic = pic;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			WineWorldReptile.view(link,pic);
		}
		
	}
	
}
