package org.ehais.Junit.Patents;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.ehais.util.DateUtil;
import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class PatsnapJUnit {
//	public static final String dblink = "jdbc:mysql://192.168.4.56:3306/patents?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	public static final String dblink = "jdbc:mysql://120.24.185.249:3306/patents?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";  
    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String user = "Gzepro";  
    public static final String password = "Gzepro123"; 
    public static Connection conn = null;  
    public static PreparedStatement pst = null; 
    public ResultSet ret = null;  
    
	@Test
	public void run_article_list() throws Exception{
		Class.forName(driver);//指定连接类型  
        conn = DriverManager.getConnection(dblink, user, password);//获取连接  
//        for(int i = 5 ; i <= 40 ;i ++){
//        	article_list("http://x.zhihuiya.com/wenzhang/page"+i);//
//        }
        article_list("http://x.zhihuiya.com/wenzhang/page40");//
	}
	
	public void article_list(String url){
		
		try{
			
			String htmlContent = EHttpClientUtil.methodGet(url);
    		if(htmlContent == null || htmlContent.equals(""))return;
    		Document doc = Jsoup.parse(htmlContent,"utf-8");
    		Elements catlist_li = doc.getElementsByClass("m-item-imgtext");
    		Elements list_li = catlist_li.first().getElementsByTag("li");
    		
    		for (Element element : list_li) {
    			String a = element.getElementsByTag("a").first().attr("href");
    			System.out.println("当前获取："+a);
    			
    			String thumb = element.getElementsByClass("imgtext-cover").first().getElementsByTag("img").first().attr("data-original");
    			String summary = element.getElementsByClass("imgtext-profile").text();
//    			System.out.println(thumb);
//    			System.out.println(summary);
    			article_detail(a,summary,thumb);
//    			System.out.println(element.getElementsByClass("imgtext-cover").first().html());
    			System.out.println("===================================");
    		}
    		

    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
	}
	
	
	@Test
	public void run_article_detail(){
		try {
			article_detail("http://x.zhihuiya.com/wenzhang/587","","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void article_detail(String url,String summary,String thumbnail) throws Exception{
		String htmlContent = EHttpClientUtil.methodGet(url);
		Document doc = Jsoup.parse(htmlContent,"utf-8");
		String title = doc.getElementsByClass("article-tit").text();
		
		System.out.println(title);
		
		Elements tag_keyword = doc.getElementsByClass("view-tit-tag");
		Elements tag_li = tag_keyword.first().getElementsByTag("li");
		String keywords = "";
		for (Element element : tag_li) {
			keywords+=element.text()+",";
		}
		if(keywords.length() > 0)keywords = keywords.substring(0, keywords.length() - 1);
		System.out.println(keywords);
		
		Elements image_container = doc.getElementsByClass("image-container");
		Elements img = image_container.first().getElementsByTag("img");
		String imgpath = img.attr("src");
		thumbnail = java.net.URLDecoder.decode(thumbnail, "UTF-8");
		imgpath = java.net.URLDecoder.decode(imgpath, "UTF-8");
		System.out.println(imgpath);
		
		String content = doc.getElementsByClass("article-main").html();
//		System.out.println(content);
		article_save(title,summary,thumbnail,imgpath,content);
	}
	
	
	public void article_save(String name,String summary,String thumbnail,String picture,String content) throws Exception{
		
		String find = "select * from P_INFORMATION where name= '"+name+"' and admin_id = 292";
		pst = conn.prepareStatement(find);//准备执行语句  
        ret = pst.executeQuery();
        if(ret.next())return ;//如果存在就退出
        
        
		String sql = "insert into P_INFORMATION (create_time,agencies,name,release_time,summary,thumbnail,picture,content,isenabled,browsing_times,collection_times,type,admin_id) values ("+
				"'"+DateUtil.formatDate(new Date(), DateUtil.FORMATSTR_1)+"',"+//create_time
				"'',"+//agencies
				"'"+name+"',"+//name
				"'"+DateUtil.formatDate(new Date(), DateUtil.FORMATSTR_1)+"',"+//release_time
				"'"+summary+"',"+//summary
				"'"+thumbnail+"',"+//thumbnail
				"'"+picture+"',"+//picture
				"'"+content.replaceAll("'", "\'")+"',"+//content
				"'YES',"+//isenabled
				"'0',"+//browsing_times
				"'0',"+//collection_times
				"'34',"+//type
				"'292'"+//admin_id
				")";
//System.out.println(sql);
		int result = pst.executeUpdate(sql);
		
	}
	
	
	
}
