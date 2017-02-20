package org.ehais.Junit.jia;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.ehais.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ZgjjJunit {
	public static final String url = "jdbc:mysql://120.27.35.142:3306/jiaword?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";  
    public static final String driver = "com.mysql.jdbc.Driver";  
    public static final String user = "lgj628";  
    public static final String password = "42016048"; 
    public static Connection conn = null;  
    public static PreparedStatement pst = null; 
    public ResultSet ret = null;  
    
    
    public static void main(String[] args) {
    	ZgjjJunit zj = new ZgjjJunit();
    	try {
			Class.forName(driver);//指定连接类型  
	        conn = DriverManager.getConnection(url, user, password);//获取连接  
	        
	        zj.article_list("http://www.zgjj.cn/news/list-91-36.html");
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    @Test
    public void lists(){
    	try {
			Class.forName(driver);//指定连接类型  
	        conn = DriverManager.getConnection(url, user, password);//获取连接  
	        
	        article_list("http://www.zgjj.cn/news/list-91.html");
		} catch (Exception  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void article_list(String url)  {
    	try{

        	final WebClient webClient = new WebClient();

    		// 1 启动JS
    		webClient.getOptions().setJavaScriptEnabled(true);
    		// 2 禁用Css，可避免自动二次请求CSS进行渲染
    		webClient.getOptions().setCssEnabled(false);
    		// 3 启动客户端重定向
    		webClient.getOptions().setRedirectEnabled(true);

    		// 4 js运行错误时，是否抛出异常
    		webClient.getOptions().setThrowExceptionOnScriptError(false);
    		// 5 设置超时
    		webClient.getOptions().setTimeout(50000);
    		
    		HtmlPage htmlPage = webClient.getPage(url);
    		// 等待JS驱动dom完成获得还原后的网页
    		webClient.waitForBackgroundJavaScript(10000);
    		// 网页内容
//    		System.out.println(htmlPage.asXml());
    		String htmlContent = htmlPage.asXml();
    		webClient.close();
    		if(htmlContent == null || htmlContent.equals(""))return;
    		Document doc = Jsoup.parse(htmlContent,"utf-8");
    		Elements catlist_li = doc.getElementsByClass("catlist_li");
    		for (Element element : catlist_li) {
    			String a = element.getElementsByTag("a").first().attr("href");
    			System.out.println("当前获取："+a);
    			this.article_info(a);
    		}
    		
    		Element pages = doc.getElementsByClass("pages").first();
    		Elements pages_a = pages.getElementsByTag("a");
    		for (Element element : pages_a) {
    			if(element.attr("title") != null && element.attr("title").length() > 0 && element.attr("title").equals("下一页")){
    				System.out.println("下一页："+element.attr("href"));
    				article_list(element.attr("href"));
    			}
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    }
    
	@Test
	public void info(){
		
		try {
			if(conn == null){
				Class.forName(driver);//指定连接类型  
		        conn = DriverManager.getConnection(url, user, password);//获取连接  
			}
			
			article_info("http://www.zgjj.cn/news/show-10596.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void article_info(String source_link ) throws FailingHttpStatusCodeException, MalformedURLException, IOException, SQLException{
		try{

			final WebClient webClient = new WebClient();

			// 1 启动JS
			webClient.getOptions().setJavaScriptEnabled(true);
			// 2 禁用Css，可避免自动二次请求CSS进行渲染
			webClient.getOptions().setCssEnabled(false);
			// 3 启动客户端重定向
			webClient.getOptions().setRedirectEnabled(true);

			// 4 js运行错误时，是否抛出异常
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			// 5 设置超时
			webClient.getOptions().setTimeout(50000);
			
			HtmlPage htmlPage = webClient.getPage(source_link);
			// 等待JS驱动dom完成获得还原后的网页
			webClient.waitForBackgroundJavaScript(10000);
			// 网页内容
//			System.out.println(htmlPage.asXml());
			String htmlContent = htmlPage.asXml();
			webClient.close();
			
			Document doc = Jsoup.parse(htmlContent,"utf-8");
			Elements pcb = doc.getElementsByClass("title");
			String title = "";
			for (Element element : pcb) {
				title = element.text();
			}
			String content = doc.getElementById("article").html();
			System.out.println(content);
			
			String sql = "select * from jia_posts where post_title = '"+title.trim()+"'";
			pst = conn.prepareStatement(sql);//准备执行语句  
	        ret = pst.executeQuery();
			
	        Date date = new Date();
	        int result = 0;
			if(!ret.next()){

				sql = "insert into jia_posts (post_author,post_date,post_date_gmt,post_content,post_title,post_excerpt,post_status,comment_status,ping_status,post_password,post_name,to_ping,pinged,post_modified,post_modified_gmt,post_content_filtered,post_parent,guid,menu_order,post_type,post_mime_type,comment_count,source_link) values ("+
//						"'"+ID+"',"+//ID+
						"'1',"+//post_author+
						"'"+DateUtil.formatDate(date, DateUtil.FORMATSTR_2)+"',"+//post_date+
						"'"+DateUtil.formatDate(date, DateUtil.FORMATSTR_2)+"',"+//post_date_gmt+
						"'"+content.replaceAll("'", "\'")+"',"+//post_content+
						"'"+title+"',"+//post_title+
						"'',"+//post_excerpt+
						"'publish',"+//post_status+
						"'open',"+//comment_status+
						"'open',"+//ping_status+
						"'',"+//post_password+
						"'"+URLEncoder.encode(title, "UTF-8")+"',"+//post_name+
						"'',"+//to_ping+
						"'',"+//pinged+
						"'"+DateUtil.formatDate(date, DateUtil.FORMATSTR_2)+"',"+//post_modified+
						"'"+DateUtil.formatDate(date, DateUtil.FORMATSTR_2)+"',"+//post_modified_gmt+
						"'',"+//post_content_filtered+
						"'0',"+//post_parent+
						"'',"+//guid+
						"'0',"+//menu_order+
						"'post',"+//post_type+
						"'',"+//post_mime_type+
						"'0',"+//comment_count+
						"'"+source_link+"'"+//source_link
						")";
				result = pst.executeUpdate(sql);
			}
			
		}catch(Exception e){
    		e.printStackTrace();
    	}
		
		
	}
	
	
	
	
}
