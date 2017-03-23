package org.ehais.util;

import java.nio.charset.StandardCharsets;

import java.net.URL;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.nio.charset.StandardCharsets;



public class EHtmlUnit {

	public static String httpUnitRequest(String url) throws Exception {
		WebClient webClient = new WebClient();

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
		webClient.waitForBackgroundJavaScript(100);
		// 网页内容
//		System.out.println(htmlPage.asXml());
		String htmlContent = htmlPage.asXml();
		webClient.close();
		
		return htmlContent;
		
	}
	//changPage method writing by Stephen Zhang inspired by LGJ
	public static String changePage(String url,String page) throws Exception{
		// 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了  
	    WebClient webclient = new WebClient();  
	  
	    // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是  
	    webclient.getOptions().setCssEnabled(false);  
	    webclient.getOptions().setJavaScriptEnabled(true); 
	    // 启动客户端重定向
	 	//webclient.getOptions().setRedirectEnabled(true);
	 		
	 	//  js运行错误时，是否抛出异常
		webclient.getOptions().setThrowExceptionOnScriptError(false);
		//  设置超时
		//webclient.getOptions().setTimeout(50000);
	  
	    // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可  
	    HtmlPage htmlpage = webclient.getPage(url);  
	    //等待后台js加载完毕
	    webclient.waitForBackgroundJavaScript(100);
	    //执行翻页js函数
	    htmlpage.executeJavaScript("__doPostBack('AspNetPager1','"+page+"');");
	    
	    
	    String result = htmlpage.asXml();  
	      
	    //System.out.println(result);
	    webclient.close();
	    return result;
	}
	
	
	public static String httpUnitAjaxRequest(String url) throws Exception {
		//创建一个可执行js,css,ajax的多功能WebClient
		WebClient multiWebClient = new WebClient(BrowserVersion.CHROME);
		multiWebClient.getOptions().setJavaScriptEnabled(true);//执行JavaScript
		multiWebClient.getOptions().setCssEnabled(true);//执行css
		multiWebClient.setAjaxController(new NicelyResynchronizingAjaxController());//设置ajax代理
		
		//创建一个普通的WebClient
//		WebClient commmonWebClient = new WebClient(BrowserVersion.FIREFOX_45);
//		commmonWebClient.getOptions().setJavaScriptEnabled(false);
//		commmonWebClient.getOptions().setCssEnabled(false);
		
		//用多功能Client获取动态页面的html并执行完js后的页面
		URL dynamicUrl = new URL(url);
		HtmlPage dynamicPage = (HtmlPage) multiWebClient.getPage(dynamicUrl);
		return dynamicPage.asXml();
		//根据项目需要,使用普通Client加载首页模板(避免执行模板里面的js,这些js都是真正要浏览器查看的时候才会执行)
//		URL constantUrl = new URL("http://localhost:30010/WebSite/wwwroot/indexTemple.html");
//		HtmlPage htmlpage = (HtmlPage) multiWebClient.getPage(constantUrl);
//		HtmlElement body = htmlpage.getBody(); 
		
		/**
		 * 未详细测试的结论:getElementById一个元素只能取一次,取了之后再取就是空元素,其子也无法用getElementById取到
		 * 开始处理header
		 */
//		appendChildren(body.getElementById("_static_nav"), dynamicPage.getElementById("_static_nav"));
//		
//		//开始处理_static_leftbox
//		//处理图片滚动KSS_content
//		appendChildren(body.getElementById("KinSlideshow"), dynamicPage.getElementById("KSS_content"));
//		//处理最新电子书
//		appendChildren(body.getElementById("e_bookDiv"), dynamicPage.getElementById("e_bookDiv"));
//		
//		//取出content
//		HtmlElement content = body.getElementById("content");
//		
//		//开始处理_static_rightbox
//		content.appendChild(dynamicPage.getElementById("_static_rightbox"));
//		//添加div换行
//		DomElement clearDiv = htmlpage.createElement("div");
//		clearDiv.setAttribute("class", "clear");
//		//一个DomElement貌似只能使用一次
//		content.appendChild(clearDiv.cloneNode(true));
//		
//		//开始处理_static_bookshow
//		content.appendChild(dynamicPage.getElementById("_static_bookshow"));
//		content.appendChild(clearDiv.cloneNode(true));
//		
//		//开始处理_static_assistBox,secrecyRelevancediv,_static_optionBox
//		content.appendChild(dynamicPage.getElementById("_static_assistBox"));
//		content.appendChild(dynamicPage.getElementById("secrecyRelevancediv"));
//		content.appendChild(dynamicPage.getElementById("_static_optionBox"));
//		content.appendChild(clearDiv.cloneNode(true));
//		
//		//开始处理_static_bookShowA
//		content.appendChild(dynamicPage.getElementById("_static_bookShowA"));
//		content.appendChild(clearDiv.cloneNode(true));
//		//开始处理_static_serve
//		content.appendChild(dynamicPage.getElementById("_static_serve"));
//		
//		//开始处理footer
//		body.appendChild(clearDiv.cloneNode(true));
//		body.appendChild(dynamicPage.getElementById("_static_footer"));
		
		
	}
	
	
	
	
	//changPage method writing by Stephen Zhang inspired by LGJ
	/*public static String changePage(String url,String page) throws Exception{
		// 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了  
	    WebClient webclient = new WebClient();  
	  
	    // 这里是配置一下不加载css和javaScript,配置起来很简单，是不是  
	    webclient.getOptions().setCssEnabled(false);  
	    webclient.getOptions().setJavaScriptEnabled(true); 
	    // 启动客户端重定向
	 	//webclient.getOptions().setRedirectEnabled(true);
	 		
	 	//  js运行错误时，是否抛出异常
		webclient.getOptions().setThrowExceptionOnScriptError(false);
		//  设置超时
		//webclient.getOptions().setTimeout(50000);
	  
	    // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可  
	    HtmlPage htmlpage = webclient.getPage(url);  
	    //等待后台js加载完毕
	    webclient.waitForBackgroundJavaScript(100);
	    //执行翻页js函数
	    htmlpage.executeJavaScript("__doPostBack('AspNetPager1','"+page+"');");
	    
	    
	    String result = htmlpage.asXml();  
	      
	    //System.out.println(result);
	    webclient.close();
	    return result;
	}
	*/
	
	
	
}
