package org.ehais.Junit.Patents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.ehais.util.EHttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HuoDongJiaJunit {

//	public static final String url = "jdbc:mysql://192.168.4.56:3306/patents?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	public static final String url = "jdbc:mysql://120.24.185.249:3306/patents?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";  
    public static final String driver = "com.mysql.jdbc.Driver";
    public static final String user = "Gzepro";  
    public static final String password = "Gzepro123"; 
    public static Connection conn = null;  
    public static PreparedStatement pst = null; 
    public ResultSet ret = null;  
	
	
	@Test
	public void run_activity_list() throws Exception{
//		Class.forName(driver);//指定连接类型  
//      conn = DriverManager.getConnection(url, user, password);//获取连接  

		activity_list("http://www.huodongjia.com/servindust/");//
	}
	
	public void activity_list(String url){
		
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
//			String htmlContent = EHttpClientUtil.methodGet(url);
			System.out.println(htmlContent);
    		if(htmlContent == null || htmlContent.equals(""))return;
    		Document doc = Jsoup.parse(htmlContent,"utf-8");
    		Elements catlist_li = doc.getElementsByClass("meeting_list");
    		
    		for (Element element : catlist_li) {
    			String a = element.getElementsByTag("a").first().attr("href");
    			System.out.println("当前获取："+a);
    			
//    			String thumb = element.getElementsByClass("imgtext-cover").first().getElementsByTag("img").first().attr("data-original");
//    			String summary = element.getElementsByClass("imgtext-profile").text();
//    			System.out.println(thumb);
//    			System.out.println(summary);
//    			article_detail(a,summary,thumb);
//    			System.out.println(element.getElementsByClass("imgtext-cover").first().html());
    			System.out.println("===================================");
    		}
    		

    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		
	}
	
}
