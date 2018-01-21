package org.ehais.Junit.SchoolReptile;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.ehais.util.EHtmlUnit;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class GcpReptile extends ArticleCommonReptile{

	private static String website = "http://vod.gcp.edu.cn/";
	public static String store_id = "1";
	public static String cat_name = "其它视频";
	public static String articleSource = "城职院视频";
	
	
	@Test
	public  void list(){
		this.list("http://vod.gcp.edu.cn/List/8");
	}
	
	private void list(String url){
		try {
			String content = EHtmlUnit.getPage(url);
			
			Document doc = Jsoup.parse(content,"utf-8");
			Element news_list_con = doc.getElementsByClass("news_list_con").first();
			Element ul = news_list_con.getElementsByTag("ul").first();
			Elements li = news_list_con.getElementsByTag("li");
			for (Element element : li) {
				Element a = element.getElementsByTag("a").first();
//				System.out.println(element.text()+"--"+a.attr("href"));
				if(a.attr("href").indexOf("Video") > 0){
					this.video(website + a.attr("href"));
				}else if(a.attr("href").indexOf("Video") > 0){
					this.view(website+a.attr("href"));					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void video(){
		String url = "";
		this.video("http://vod.gcp.edu.cn/Video/6/674");
	}
	
	public void video(String url){
		System.out.println(url);
		try {
			String content = EHtmlUnit.getPage(url);
			System.out.println(content);
			Document doc = Jsoup.parse(content,"utf-8");
			Element show_bottom = doc.getElementsByClass("show_bottom").first();
			Elements p = show_bottom.getElementsByTag("p");
			for (Element element : p) {
				
				Element a = element.getElementsByTag("a").first();
//				System.out.println(element.text()+"--"+a.attr("href"));
				this.view(website+a.attr("href"));
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void view(){
		String url = "http://vod.gcp.edu.cn/View/1/1";
		this.view(url);
	}
	
	public void view(String url){
		System.out.println(url);
		try {
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element show_main = doc.getElementsByClass("show_main").first();
			Element h4 = show_main.getElementsByTag("h4").first();
			String title = h4.text();
			
			System.out.println(title);
			Element show_con = show_main.getElementsByClass("show_con").first();
			Element object = show_con.getElementsByTag("object").first();
			Elements param = object.getElementsByTag("param");
			String videoUrl = "";
			for (Element element : param) {
				if(element.attr("name").equals("URL") || element.attr("name").equals("flashvars")){
					videoUrl = element.attr("value");
					if(videoUrl.indexOf("&src=")>=0)videoUrl = videoUrl.replace("&src=", "");
					System.out.println(element.attr("value"));
				}
			}
			
			String filename = videoUrl.substring(videoUrl.lastIndexOf("/")+1, videoUrl.length());
        	System.out.println("filename:"+filename);
        	
//			EHttpClientUtil.downloadFile(videoUrl, "e:/temp/gcp/"+filename);
			
			video_save(store_id, cat_name, title,videoUrl ,articleSource, url);
			
			
			System.out.println("======================");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
