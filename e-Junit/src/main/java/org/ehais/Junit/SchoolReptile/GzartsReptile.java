package org.ehais.Junit.SchoolReptile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.ehais.Junit.reptile.ArticleCommonReptile;
import org.ehais.util.EHtmlUnit;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.FSO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import net.sf.json.JSONObject;

public class GzartsReptile extends ArticleCommonReptile{

	private static String website = "http://lib.gzarts.edu.cn";
	public static String store_id = "78";
	public static String cat_name = "";
	public static String articleSource = "广美图书馆";
	
	
	public static final String name = "com.mysql.jdbc.Driver";  
	public static final String db = "sqlehaismall";
	public static final String table = "hai_attribute";  
	
	
	public static final String link = "jdbc:mysql://127.0.0.1:3306/"+db;      
    public static final String user = "root";  
    public static final String password = "42016048"; 
    
    public Integer pageNo = 1;
    
    
    public static Connection conn = null;  
    public static PreparedStatement pst = null; 
    public ResultSet ret = null;  
    public String br = "\r\n";
	
	@Test
	public void test_list(){
		String url = "http://lib.gzarts.edu.cn/web/guest/xwgg?p_p_id=xwgg_show&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=3&_xwgg_show_struts_action=%2Fext%2Fxwgg_show%2Fview&_xwgg_show_typeid=11";
		cat_name = "美术长廊";
		this.list(url);
		
	}
	
	public void list(String url){
		System.out.println("=============="+url);
		try {
			String content = EHtmlUnit.getPage(url);
//			System.out.println(content);
			Document doc = Jsoup.parse(content,"utf-8");
			Elements dbcol = doc.getElementsByClass("test_list");
//			Elements dbcol = doc.getElementsByTag("marquee");
			Elements as = dbcol.first().getElementsByTag("a");
			
			for (Element e : as) {
				Element a = e.getElementsByTag("a").first();
				System.out.println(a.text()+"===="+a.attr("href"));
				
				view(a.text(),(a.attr("href").indexOf("http") >= 0 ? a.attr("href")  :  website+a.attr("href")));
				
			}
			
			Elements a = doc.getElementsByTag("a");
			for (Element e : a) {
				if(e.text().equals("下一页") && !e.attr("href").equals("#")){
					this.list(e.attr("href"));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			FSO.WriteTextFile("E://gzarts/log.txt", e.getMessage());
		}
	}
	
	
	@Test
	public void test_view(){
		String url = "http://lib.gzarts.edu.cn/web/guest/bggs?p_p_id=bggs&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_count=1&_bggs_struts_action=%2Fext%2Fbggs%2Fview&_bggs_id=4&_bggs_smtalid=5";
//		
		cat_name = "开放时间";
//		view_sigle(url);
		view(url);
//		view("馆藏分布",url);
	}
	
	
	public static void view_sigle(String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element textTitle = doc.getElementsByClass("textTitle").first();
			
			System.out.println(textTitle.text());
			
			
			Elements font = doc.getElementsByClass("serverCon");
			
			article_save(store_id, cat_name, textTitle.text(),"","", "", font.get(0).html(),articleSource, "");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public static void view(String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			Element textTitle = doc.getElementsByClass("textTitle").first();
			
			System.out.println(textTitle.text());
			
			
//			Elements font = doc.getElementsByTag("font");
			
			Elements test_box = doc.getElementsByClass("test_box");
			
			
			article_save(store_id, cat_name, textTitle.text(),"","", "", test_box.html(),articleSource, "");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void view(String title ,String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			
			Elements test_box = doc.getElementsByClass("test_box");
			String description = test_box.html();
			
			Elements input_img = test_box.first().getElementsByTag("input");
			for (Element e : input_img) {
				if(e.attr("type").equals("image")){
					String src = e.attr("src");
					if(src.indexOf("http")<0){
						src = website + src;
					}
					
					String filename = src.substring(src.lastIndexOf("/")+1, src.length());
		        	System.out.println(filename);
		        	EHttpClientUtil.downloadFile(src, "e:/gzarts/2/"+filename);
		        	src = e.attr("src");
		        	description = description.replaceAll(src, "/upload/gzarts/"+filename);
					
				}
			}
			
			
			
			Elements img = test_box.first().getElementsByTag("img");
			for (Element e : img) {
				String src = e.attr("src");
				if(src.indexOf("http")<0){
					src = website + src;
				}
				
				String filename = src.substring(src.lastIndexOf("/")+1, src.length());
	        	System.out.println(filename);
	        	EHttpClientUtil.downloadFile(src, "e:/gzarts/2/"+filename);
//	        	src = e.attr("src");
//	        	description = description.replaceAll(src, "/upload/gzarts/"+filename);
				
			}
			
			
			
			article_save(store_id, cat_name, title,"","", "", description ,articleSource, "");
			
			
		}catch(Exception e){
			e.printStackTrace();
			FSO.WriteTextFile("E://gzarts/log.txt", e.getMessage());
		}
	}
	
	
	@Test
	public void test_img_list(){
		String url = "http://lib.gzarts.edu.cn/web/guest/dzfw?p_p_id=dzfw_show&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=3&_dzfw_show_struts_action=%2Fext%2Fgnhd_show%2Fxstg&_dzfw_show_method=list&_dzfw_show_id=1";
		cat_name = "新书报道";
		this.img_list(url);
		
	}
	
	public void img_list(String url){
		System.out.println("=============="+url);
		try {
			String content = EHtmlUnit.getPage(url);
//			System.out.println(content);
			Document doc = Jsoup.parse(content,"utf-8");
			Elements dbcol = doc.getElementsByClass("bottBk");
			Elements as = dbcol.first().getElementsByTag("a");
			for (Element e : as) {
				Element a = e.getElementsByTag("a").first();
				System.out.println(a.text()+"===="+a.attr("href"));
				
				img_view(a.text(),(a.attr("href").indexOf("http") >= 0 ? a.attr("href")  :  website+a.attr("href")));
				
			}
			
			Elements a = doc.getElementsByTag("a");
			for (Element e : a) {
				if(e.text().equals("下一页") && !e.attr("href").equals("#")){
					this.img_list(e.attr("href"));
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test_img_view(){
		String url = "http://lib.gzarts.edu.cn/web/guest/dzfw?p_p_id=dzfw_show&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=3&_dzfw_show_struts_action=%2Fext%2Fgnhd_show%2Fxstg&_dzfw_show_method=showone&_dzfw_show_id=1192";
//		
		cat_name = "开放时间";
//		view_sigle(url);
		img_view("",url);
//		view("馆藏分布",url);
	}
	
	public static void img_view(String title,String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try{
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			
			
//			Elements font = doc.getElementsByTag("font");
			
			Elements bottBk = doc.getElementsByClass("bottBk");
			Elements img = bottBk.first().getElementsByTag("img");
			String src = website+img.first().attr("src");
			System.out.println(website+src);
			
			
			Elements input_img = bottBk.first().getElementsByTag("input");
			for (Element e : input_img) {
				if(e.attr("type").equals("image")){
					String srcx = e.attr("src");
					if(srcx.indexOf("http")<0){
						srcx = website + srcx;
					}
					
					String filename = srcx.substring(srcx.lastIndexOf("/")+1, srcx.length());
		        	System.out.println(filename);
		        	EHttpClientUtil.downloadFile(srcx, "E:/gzarts/images/newBookCover/"+filename);
		        	srcx = e.attr("src");
					
				}
			}
			
			
			
			Elements imgx = bottBk.first().getElementsByTag("img");
			for (Element e : imgx) {
				String srcx = e.attr("src");
				if(srcx.indexOf("http")<0){
					srcx = website + srcx;
				}
				
				String filename = srcx.substring(srcx.lastIndexOf("/")+1, srcx.length());
	        	System.out.println(filename);
	        	EHttpClientUtil.downloadFile(srcx, "E:/gzarts/images/newBookCover/"+filename);
//	        	src = e.attr("src");
//	        	description = description.replaceAll(src, "/upload/gzarts/"+filename);
				
			}
			
			
//			Elements test_box = doc.getElementsByClass("bookList");
//			System.out.println(test_box);
			
			article_save(store_id, cat_name, title,src,"", "", bottBk.html(),articleSource, "");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void loadimg(){
		try {
			Class.forName(name);//指定连接类型  
	        conn = DriverManager.getConnection(link, user, password);//获取连接  
	        String sql  = "select * from hai_article where store_id = 78 and ifnull(article_thumb,'') != '' ";
	        pst = conn.prepareStatement(sql);//准备执行语句  
	        ret = pst.executeQuery();
	        while(ret.next()){
	        	String thumb = ret.getString("article_thumb");
	        	String filename = thumb.substring(thumb.lastIndexOf("/")+1, thumb.length());
	        	System.out.println(filename);
	        	EHttpClientUtil.downloadFile(thumb, "e:/gzarts/"+filename);
	        }
//	        
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void message_test(){
		String url = "http://lib.gzarts.edu.cn/web/guest/dzfw?p_p_id=dzfw_show&p_p_lifecycle=0&p_p_state=normal&p_p_mode=view&p_p_col_id=column-1&p_p_col_pos=1&p_p_col_count=3&_dzfw_show_struts_action=%2Fext%2Fgnhd_show%2Fdzly&_dzfw_show_method=list";
		message(pageNo.toString(),url);
	}
	
	public void message(String page ,String url){
		System.out.println(page+"=="+url);
		try{
			
			String content = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(content,"utf-8");
			
			Elements gzmy_table = doc.getElementsByClass("gzmy_table");
			Elements tr = gzmy_table.first().getElementsByTag("tr");
			int i = 0 ;
			String lyName = null;
			String lyTime = null;
			String subject = null;
			String icoLy = null;
			String icoHf = null;
			for (Element e : tr) {
				System.out.println(i);
				if(i % 4 == 0){
					lyName = e.text();
					if(e.getElementsByClass("lyTime")!=null){
						System.out.println(e.toString());
						lyTime = e.getElementsByClass("lyTime").first().text();
					}else{
						lyTime = "";
						System.out.println(lyName+"无时间");
					}
					
				}
				if(i%4 == 1){
					subject = e.text();
				}
				if(i%4 == 2){
					icoLy = e.text();
				}
				if(i%4 == 3){
					icoHf = e.text();
				}
				
				if(i % 4 == 3){
					Map<String, String> map = new HashMap<String, String>();
					map.put("用户时间", lyName);
					map.put("时间", lyTime);
					map.put("主题", subject);
					map.put("留言", icoLy);
					map.put("回复", icoHf);
					JSONObject json = JSONObject.fromObject(map);
					String jsonStr = json.toString();
					FSO.WriteTextFileZh("E:/gzarts/"+page.toLowerCase()+"_"+i+".json", jsonStr);
				}
				i++;
				
				if(i == 32){
					break;
				}
			}
			
			Elements a = doc.getElementsByTag("a");
			for (Element e : a) {
				if(e.text().equals("下一页") && !e.attr("href").equals("#")){
					pageNo ++;
					this.message(pageNo.toString(),e.attr("href"));
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
}
