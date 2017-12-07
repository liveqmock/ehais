package org.ehais.Junit.SchoolReptile.wyu;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.ehais.util.EHtmlUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Tztl extends ArticleCommonReptile{
	private static String website = "http://jpkc.wyu.edu.cn/tztl";
	public static String store_id = "5001";
	public static String cat_name = "";
	public static String articleSource = "五邑土质学土力学";
	
    
    @Test
    public void nav(){
    	try{
    		Document doc = Jsoup.connect(website+"/index.aspx").get();
    		Element nav = doc.getElementsByClass("nav").first();
    		Elements li1 = nav.select(">li");
    		for (Element l : li1) {
				Element a = l.select(">a").first();
				System.out.println(a.text());
				Elements ll = l.select(">ul").select(">li");
				for (Element e : ll) {
					Element aa = e.select(">a").first();
					System.out.println("===="+aa.text());
					if(aa.attr("href").indexOf("base")>-1){
						this.base(website+"/"+aa.attr("href"),aa.text(),a.text());
					}else if(aa.attr("href").indexOf("news")>-1){
						this.news(website+"/"+aa.attr("href"),aa.text(),a.text());
					}
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    public void base(String url,String cat_name,String parent_cat_name){
    	try{
    		Document doc = Jsoup.connect(url).get();
    		String content = doc.getElementsByClass("about").html();
    		article_save(store_id, cat_name, cat_name, "", "", "", content,articleSource, url, parent_cat_name,"");
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public void news(String url,String cat_name,String parent_cat_name){
    	try{
    		Document doc = Jsoup.connect(url).get();
    		Element news_list = doc.getElementsByClass("news_list").first();
    		Elements li = news_list.select(">li");
    		for (Element e : li) {
				Element a = e.getElementsByTag("a").first();
				newsinfo(website+"/"+a.attr("href"),cat_name,parent_cat_name);
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    public void newsinfo(String url,String cat_name,String parent_cat_name){
    	try{
    		Document doc = Jsoup.connect(url).get();
    		Element about = doc.getElementsByClass("about").first();
    		Element etitle = about.getElementsByClass("ab_title").first();
    		Element etime = about.getElementsByClass("ab_time").first();
    		Element news_foot = about.getElementsByClass("news_foot").first();
    		Element back = about.getElementsByClass("back").first();
    		String title = etitle.text();
    		String date = etime.text().replace("发布时间：", "");
    		etitle.remove();
    		etime.remove();
    		news_foot.remove();
    		back.remove();
    		String content = about.html();
    		article_save(store_id, cat_name, title, "", "", "", content,articleSource, url, parent_cat_name,date);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
}
