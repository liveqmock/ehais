package org.ehais.Junit;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ZhaoXavNet {

	private String site = "http://zhaoxav.net";
	
	@Test
	public void xijq() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		String url = "http://zhaoxav.net/forum-75-1.html";
		url = "http://zhaoxav.net/thread-925627-1-1.html";
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
		System.out.println(htmlPage.asXml());
		String htmlContent = htmlPage.asXml();
		webClient.close();
		
		
	}
}
