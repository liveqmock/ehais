package org.ehais.Junit.SchoolReptile.wyu;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Fzdnhh extends ArticleCommonReptile{

	private static String website = "http://demo.ltpower.net";
	public static String store_id = "5004";
	public static String cat_name = "";
	public static String articleSource = "服装";
	
	
	@Test
    public void nav(){
    	try{
    		Document doc = Jsoup.connect(website+"/web/fzdnhh/index.html").get();
    		Element nav = doc.getElementById("nav");
    		Elements li1 = nav.select(">li");
    		for (Element l : li1) {
				Element a = l.select(">a").first();
				System.out.println(a.text());
				this.news(website+"/"+a.attr("href"),a.text(),"");
				Elements ll = l.select(">ul").select(">li");
				for (Element e : ll) {
					Element aa = e.select(">a").first();
					System.out.println("===="+aa.text());
//					if(aa.attr("href").indexOf("about")>-1){
//						this.about(website+"/"+aa.attr("href"),aa.text(),a.text());
//					}else if(aa.attr("href").indexOf("news")>-1){
						this.news(website+"/"+aa.attr("href"),aa.text(),a.text());
//					}
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
//	@Test
//	public void tabout(){
//		String url = "http://jpkc.wyu.edu.cn/jybxyktq/about.asp?classid=178&navi_id=211";
//		this.about(url, "", "");
//	}
//	
//	
//	public void about(String url,String cat_name,String parent_cat_name){
//    	try{
//    		Document doc = Jsoup.connect(url).get();
//    		Elements table = doc.getElementsByTag("table");
//    		for (Element e : table) {
//				String width = e.attr("width");
//				if(width.equals("685")){
//					String content = e.getElementsByClass("he").html();
////					System.out.println(content);
//		    		article_save(store_id, cat_name, cat_name, "", "", "", content,articleSource, url, parent_cat_name,"");
//				}
//			}
//    		
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    }
	
	@Test
	public void tnews(){
		String url = "http://demo.ltpower.net/web/fzdnhh/news/show-4253.html";
		this.newsinfo(url, "", "");
	}
	
	
	public void news(String url,String cat_name,String parent_cat_name){
    	try{
    		Document doc = Jsoup.connect(url).get();
    		Element xtable = doc.getElementsByClass("ny-right-content").first();
    		Elements table = xtable.getElementsByTag("li");
    		for (Element e : table) {
    			
    			Elements a = e.getElementsByTag("a");
//    			System.out.println(website+"/"+a.attr("href"));
    			newsinfo(website+"/"+a.attr("href"),cat_name,parent_cat_name);
    			
			}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	
	public void newsinfo(String url,String cat_name,String parent_cat_name){
    	try{
    		
    		
    		System.out.println(Jsoup.connect(url).get().html());
    		
    		
//    		Document doc = Jsoup.connect(url).get();
    		
//    		Elements table = doc.getElementsByTag("table");
//    		String title = doc.getElementsByClass("right-main-title").text();
//    		String date = doc.getElementsByClass("right-sub-title").first().getElementsByTag("span").first().text();
//    		Element c = doc.getElementsByClass("right-main-show").first();
//    		Element manu = c.getElementsByClass("manu").first();
//    		if(manu!=null)manu.remove();
//    		String content = c.html();
//    		
////    		System.out.println(title);
////    		System.out.println(date);
////    		System.out.println(content);
//    		article_save(store_id, cat_name, title, "", "", "", content,articleSource, url, parent_cat_name,null);
    		
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	
	
}
