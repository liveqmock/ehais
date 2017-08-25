package org.ehais.Junit;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.sf.json.JSONObject;

public class XIAAVArticleJUnit {
	
	public String website = "http://xav6.com/";
	public String appkey = "ifuckehais";
	public String secret = "ufuckehais";
	public String store_id = "8";
	
	public static void main(String[] args) {
		XIAAVArticleJUnit xiaav = new XIAAVArticleJUnit();
		for(int i = 1 ; i <= 8000 ; i++){
			xiaav.article_list(xiaav.website + "forum-41-"+i+".html");
		}
	}
	
	@Test
	public void article_run() {
		for(int i = 1 ; i <= 8000 ; i++){
			this.article_list(website + "forum-41-"+i+".html");
		}
		
	}
	
	
	public void article_list(String url) {
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
			webClient.waitForBackgroundJavaScript(100);
			// 网页内容
//			System.out.println(htmlPage.asXml());
			String htmlContent = htmlPage.asXml();
			webClient.close();
			
			Document doc = Jsoup.parse(htmlContent,"utf-8");
			
			Element threadlisttableid  = doc.getElementById("threadlisttableid");
//			Elements tbody = threadlisttableid.getElementsByTag("tbody");
			Elements th = threadlisttableid.getElementsByTag("th");
			for (Element element : th) {
				Element newth = element.getElementsByClass("new").first();
				if(newth == null)continue;
				Elements a = newth.getElementsByTag("a");
				for (Element element2 : a) {
					String href = element2.attr("href");
					if(href.indexOf(".html") > 0 && element2.text().length() > 1){
						System.out.println(element2.text() + " == " + href);
						this.article_info(element2.text() , website + href);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void article_info_run(){
		article_info("fuckyou","http://soxav.gq/thread-968581-1-1.html");
	}
	
	
	public void article_info(String title , String url){
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
//			System.out.println(htmlPage.asXml());
			String htmlContent = htmlPage.asXml();
			webClient.close();
			
			Document doc = Jsoup.parse(htmlContent,"utf-8");
			Elements pcb = doc.getElementsByClass("t_fsz");
			Elements ptd = pcb.get(0).getElementsByTag("td");
//			System.out.println(pcb.get(0).html());
//			for (Element element : pcb) {
//				System.out.println(element.html());
//				System.out.println("//////////////////////////////////////////////////////////");
//			}
			
//			Element pcb  = doc.getElementById("postmessage_12404687");
//			System.out.println(ptd.html());
			this.saveArticle(title, url, ptd.html(), store_id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private String saveArticle(String title ,String link,String content,String store_id) throws UnsupportedEncodingException{
		String cat_id = "0";
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("ver", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
    	
    	paramsMap.put("title", title);
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("link", link);
    	paramsMap.put("description", title);
    	paramsMap.put("content", content);
    	
    	String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
    	paramsMap.put("sign", sign);
    	
    	String web_url = "http://shop.ehais.com";
    	String req = EHttpClientUtil.httpPost(web_url+"/Article-save_weburl.api", paramsMap);
		System.out.println(req);
		
		JSONObject json = JSONObject.fromObject(req);
//		cat_id = String.valueOf(json.getJSONObject("model").getInt("cat_id"));
		System.out.println(json.toString());
		return json.toString();
	}
	
}
