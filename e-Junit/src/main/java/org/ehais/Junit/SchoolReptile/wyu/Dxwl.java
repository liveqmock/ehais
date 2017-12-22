package org.ehais.Junit.SchoolReptile.wyu;

import java.util.Iterator;
import java.util.Vector;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Dxwl extends ArticleCommonReptile {

	private static String website = "http://jpkc.wyu.edu.cn";
	public static String store_id = "5005";
	public static String cat_name = "";
	public static String articleSource = "大学物理";
	
	@Test
	public void listjunit(){
		this.cat(website+"/news/1803.html");
		
//		this.list(website+"/news/1823.html");
		
//		this.detail(website+"/news/show-5031.html");
	}
	
	private void cat(String url){
		try{
			Document doc = Jsoup.connect(url).get();
			Element ny_left_content = doc.getElementsByClass("ny-left-content").first();

//			Queue<Object> queue  = new LinkedList<>();
//			
//			HashSet books = new HashSet();
			
			Vector<Object> stack = new Vector<Object>();
			
			Elements a = ny_left_content.getElementsByTag("a");
			for (Element e : a) {
				stack.add(website+e.attr("href"));
			}
			
//			Iterator iterator = stack.iterator();  
//	        while (iterator.hasNext()) {  
//	            System.out.println(iterator.next());              
//	        } 
	        Object aa = null;
	        while(( aa = stack.lastElement()) !=null)
	        {
	        System.out.println(aa);
	        this.list(aa.toString());
	        stack.removeElement(aa);
	        //break;
	        }
	        
			
			
//			for (Element e : li) {
//				Element a = e.select(">a").first();
//				System.out.println(a.text()+"----"+website+a.attr("href"));
//				
//				Element ul1 = e.select(">ul").first();
//				Elements li1 = ul1.select(">li");
//				for (Element e1 : li1) {
//					Element a1 = e1.select(">a").first();
//					System.out.println("===="+a1.text()+"----"+website+a1.attr("href"));
//				}
//				
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	private void list(String url){
		try{
			Document doc = Jsoup.connect(url).get();
			Element ny_right_title = doc.getElementsByClass("ny-right-title").first();
			Elements at = ny_right_title.select(">a");
			String pcat = "";
			String scat = "";
			String tcat = "";
			int i = 0;
			for (Element et : at) {
				if (i == 2)pcat = et.text();
				if (i == 3)scat = et.text();
				if (i > 3)tcat += et.text();
				i++;
			}
			
			
			Element ny_right_content = doc.getElementsByClass("ny-right-content").first();
			Element ul = ny_right_content.select(">ul").first();
			Elements li = ul.select(">li");
			for (Element e : li) {
				Element a = e.select(">a").first();
				System.out.println(tcat+a.text()+"----"+website+a.attr("href"));
				
				this.detail(website+a.attr("href"), pcat, scat, tcat+a.text());
				
			}
			
			Element manu = doc.getElementsByClass("manu").first();
			Elements a_manu = manu.getElementsByTag("a");
			for (Element ea : a_manu) {
				if(ea.text().indexOf("下一页") >= 0){
					System.out.println("下一页:"+website+ea.attr("href"));
					this.list(website+ea.attr("href"));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void detail(String url,String pcat,String scat,String title){
		System.out.println("大学物理详情..."+url);
		try{
			String content = EHttpClientUtil.methodGet(url);
			Document doc = Jsoup.parse(content);
			Element ny_content = doc.getElementsByClass("right-main-show").first();
			System.out.println(ny_content.toString());
			
			article_save(store_id, scat, title, "", "", "", ny_content.toString(),articleSource, url, pcat,null);
    		
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
