package org.ehais.Junit.SchoolReptile.wyu;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Jybxyktq extends ArticleCommonReptile{
	private static String website = "http://jpkc.wyu.edu.cn/jybxyktq";
	public static String store_id = "5002";
	public static String cat_name = "";
	public static String articleSource = "家用冰箱与空调";
	
	@Test
    public void nav(){
    	try{
    		Document doc = Jsoup.connect(website+"/index.asp").get();
    		Element nav = doc.getElementById("navigation");
    		Elements li1 = nav.select(">li");
    		for (Element l : li1) {
				Element a = l.select(">a").first();
				System.out.println(a.text());
				if(a.attr("href").indexOf("about")>-1){
					this.about(website+"/"+a.attr("href"),a.text(),"");
				}
				Elements ll = l.select(">ul").select(">li");
				for (Element e : ll) {
					Element aa = e.select(">a").first();
					System.out.println("===="+aa.text());
					if(aa.attr("href").indexOf("about")>-1){
						this.about(website+"/"+aa.attr("href"),aa.text(),a.text());
					}else if(aa.attr("href").indexOf("news")>-1){
						this.news(website+"/"+aa.attr("href"),aa.text(),a.text());
					}
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	@Test
	public void tabout(){
		String url = "http://jpkc.wyu.edu.cn/jybxyktq/about.asp?classid=178&navi_id=211";
		this.about(url, "", "");
	}
	
	
	public void about(String url,String cat_name,String parent_cat_name){
    	try{
    		Document doc = Jsoup.connect(url).get();
    		Elements table = doc.getElementsByTag("table");
    		for (Element e : table) {
				String width = e.attr("width");
				if(width.equals("685")){
					String content = e.getElementsByClass("he").html();
//					System.out.println(content);
		    		article_save(store_id, cat_name, cat_name, "", "", "", content,articleSource, url, parent_cat_name,"");
				}
			}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	@Test
	public void tnews(){
//		String url = "http://jpkc.wyu.edu.cn/jybxyktq/news.asp?classid=86&navi_id=195";
		String url = "http://jpkc.wyu.edu.cn/jybxyktq/newsshow.asp?Id=1028&classid=86&navi_id=195";
		this.newsinfo(url, "", "");
	}
	
	
	public void news(String url,String cat_name,String parent_cat_name){
    	try{
    		Document doc = Jsoup.connect(url).get();
    		Elements table = doc.getElementsByTag("table");
    		for (Element e : table) {
				String width = e.attr("width");
				if(width.equals("685")){
					Element t = e.getElementsByTag("table").first();
					Elements a = t.getElementsByTag("a");
					for (Element el : a) {
						if(el.attr("href").indexOf("newsshow") > -1){
							newsinfo(website+"/"+el.attr("href"),cat_name,parent_cat_name);
						}
						
					}
				}
			}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	
	public void newsinfo(String url,String cat_name,String parent_cat_name){
    	try{
    		
    		Document doc = Jsoup.connect(url).get();
    		Elements table = doc.getElementsByTag("table");
    		for (Element e : table) {
				String width = e.attr("width");
				if(width.equals("685")){
					
					Elements ts = e.getElementsByTag("table");
					if(ts!=null){
						for (Element es : ts) {
							String widths = es.attr("width");
							if(widths.equals("685")){
								System.out.println(es.html());
								String title = es.getElementsByClass("he").html();
								article_save(store_id, cat_name, title, "", "", "", es.html(),articleSource, url, parent_cat_name,null);
								
							}
						}
					}
					
				}
			}
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	
}
