package org.ehais.Junit.reptile;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHtmlUnit;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class ZhzywJunit {

	private static String website = "http://www.zhzyw.org";
	private static String web_url = "http://mg.ehais.com";
	public static String appkey = "Ehais";
	public static String secret = "EhaisSecret";
	public static String store_id = "71";
	
//	中药基础
	@Test
	public void test_zyjc(){
		String url = website+"/zycs/zyjc/index_117.html";
		this.zyjc(url);
	}
	public void zyjc(String url){
		System.out.println("======================================"+url);
		try {
			String html = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(html,"utf-8");
			Element ullist01 = doc.getElementsByClass("ullist01").first();
			Elements a = ullist01.getElementsByTag("a");
			for (Element e : a) {
				System.out.println(e.html()+"--"+e.attr("href"));
				
//				this.zyjc_detail(website+e.attr("href"));//进入明细请求
				
//				TheadZhzyw t = new TheadZhzyw(website+e.attr("href"));
//				t.run();
				
				new Thread(new TheadZhzyw(website+e.attr("href"))).start();
			}
			
			//下一页
			Element pagecontent = doc.getElementsByClass("pagecontent").first();
			Elements na = pagecontent.getElementsByTag("a");
			for (Element e : na) {
				if(e.attr("title")!=null && e.attr("title").equals("下一页")){
//					System.out.println(e.html()+"--"+e.attr("href"));
					this.zyjc(website+e.attr("href"));
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test_zyjc_detail(){
		String url = website+"/zycs/zyjc/174191313858E27HKDK2418I.html";
		url = "http://www.zhzyw.org/zycs/zyjc/089614GB7G2CAJ9KG0AK489.html";
		this.zyjc_detail(url);
	}
	public static void zyjc_detail(String url){
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+url);
		try {
//			String html = EHtmlUnit.getAjaxPage(url);
			String html = EHtmlUnit.getPage(url);
			Document doc = Jsoup.parse(html,"utf-8");
			Element left = doc.getElementById("left");
			
			Element H1 = left.getElementsByTag("h1").first();
			Element daodu = left.getElementsByClass("daodu").first();
			Element webnr = left.getElementsByClass("webnr").first();
//			System.out.println(H1.html());
//			System.out.println(daodu.html());
//			System.out.println(webnr.html());
			StringBuilder sb = new StringBuilder();
						
			Elements pagecontent = left.getElementsByClass("pagecontent");
			if(pagecontent!=null && pagecontent.size() > 0){
				Elements a = pagecontent.first().getElementsByTag("a");
				for (Element e : a) {
//					System.out.println(e.attr("title"));
					if(e.attr("title") == null || e.attr("title").equals("")){
						String subcontent = zyjc_detail_sub(website + e.attr("href"));
						sb.append(subcontent);
					}
				}
				pagecontent.remove();
			}
			
			sb.insert(0, webnr.html());
			
//			System.out.println(sb.toString());
			
			//保存
			article_save("中药基础", H1.html(), daodu.html(), sb.toString(), url);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static String zyjc_detail_sub(String url){
		
		try {
			String html = EHtmlUnit.getAjaxPage(url);
			Document doc = Jsoup.parse(html,"utf-8");
			Element left = doc.getElementById("left");
			
			Element webnr = left.getElementsByClass("webnr").first();

			Elements pagecontent = webnr.getElementsByClass("pagecontent");
			if(pagecontent!=null){
				pagecontent.remove();
			}
			
			return webnr.html();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	public static void article_save(String cat_name,String title,String description,String content,String link){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("version", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
    	
    	paramsMap.put("title", title);
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("storeId", store_id);
    	paramsMap.put("link", link);
    	paramsMap.put("description", description);
    	paramsMap.put("content", content);
    	paramsMap.put("cat_name", cat_name);
    	paramsMap.put("articleSource", "中华中药网");
    	
    	
		try {
			String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
			paramsMap.put("sign", sign);

	    	String req = EHttpClientUtil.httpPost(web_url+"/api/article_save", paramsMap);
			System.out.println(req);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
	}
	
	
	@Test
	public void test_article_save() throws UnsupportedEncodingException{
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("version", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
    	
    	paramsMap.put("title", "TTTv");
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("storeId", store_id);
    	paramsMap.put("link", "test");
    	paramsMap.put("description", "test");
    	paramsMap.put("content", "test");
    	paramsMap.put("cat_name", "中药基础");
    	paramsMap.put("articleSource", "中华中药网");
//    	paramsMap.put("author", "中药网");
//    	paramsMap.put("authorEmail", "中药网@126.com");
//    	paramsMap.put("keywords", "中药");
//    	paramsMap.put("fileUrl", "中药");
    	
    	String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
    	paramsMap.put("sign", sign);

    	String req = EHttpClientUtil.httpPost(web_url+"/api/article_save", paramsMap);
		System.out.println(req);
	}
	
	class TheadZhzyw implements Runnable{
		
		private String link;
		
		public TheadZhzyw(String link){
			this.link = link;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ZhzywJunit.zyjc_detail(link);
		}
		
	}
	
}
