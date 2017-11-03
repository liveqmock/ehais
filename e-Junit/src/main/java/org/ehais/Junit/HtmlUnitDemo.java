package org.ehais.Junit;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.sf.json.JSONObject;

public class HtmlUnitDemo {

	private String appkey = "ifuckehais";
	private String secret = "ufuckehais";
	
	
	@Test
	public void weburl() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		String url = "http://image.baidu.com/i?tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&fm=result&fr=&sf=1&fmq=1400328281672_R&pv=&ic=0&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&istype=2&ie=utf-8&word=html";
		url = "https://s.taobao.com/search?q=%E7%81%AF&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.50862.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20160804&cps=yes&ppath=1626518%3A43762";
//		url = "http://zhaoxav.net/thread-925667-1-1.html";
//		url = "http://localhost:8080/index";
		url = "https://www.tmall.com/?spm=a220m.1000858.a2226n0.1.dpooN3";
		url = "http://news.baidu.com/ns?cl=2&rn=20&tn=news&word=http%3A%2F%2Fwww.qq.com";
		
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
		
//		Document doc = Jsoup.parse(htmlContent, "");
//		Elements eleImg = doc.getElementsByTag("img");
//		for (Element element : eleImg) {
//			System.out.println("图片："+element.attr("src"));
//		}
		
	}
	
	@Test
	public void category() throws IOException{
		File input = new File("D:/workspace_luna/ehais/e-shop/src/main/webapp/WEB-INF/view/web/tianmao/index_content.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://localhost/");
		
//		System.out.println(doc.html());
//		Elements eleCategoryTabContent = doc.getElementsByClass("category-tab-content");
//		System.out.println(eleCategoryTabContent.size());
		
		Elements j_MenuNav = doc.getElementsByClass("j_MenuNav");
		Elements j_CategoryMenuPannel = doc.getElementsByClass("j_CategoryMenuPannel");
//		System.out.println(j_CategoryMenuPannel.size());
		
		int i = 0;
		for (Element element : j_MenuNav) {
			
			String cat_id = "0";
			String cat_name = element.getElementsByTag("a").text().trim().replaceAll(" ", "");
//			System.out.println("|"+cat_name+"|");
			cat_id = this.saveCategory(cat_id, cat_name, "3");
//			System.out.println("cat_id:"+cat_id);
//			if(true)continue;
//			if(true)break;
			
			Element elementcate = j_CategoryMenuPannel.get(i);
			Elements hotwordline = elementcate.getElementsByClass("hot-word-line");
			for (Element element2 : hotwordline) {
				Elements titleText = element2.getElementsByClass("title-text");
				System.out.println("  ===   |"+titleText.text()+"|");
				String cat_name2 = titleText.text().trim().replaceAll(" ", "");
				String cat_id2 = this.saveCategory(cat_id, cat_name2, "3");
//				System.out.print("           ");
				Elements a = element2.getElementsByClass("line-con").first().getElementsByTag("a");
				for (Element element3 : a) {
					System.out.print(" |"+element3.text().trim().replaceAll(" ", "")+"|");
					String cat_name3 = element3.text().trim().replaceAll(" ", "");
					String cat_id3 = this.saveCategory(cat_id2, cat_name3, "3");
				}
//				System.out.println("**********************");
			}
			
			
			i++;
		}
		
	}
	
	
	private String saveCategory(String parent_id ,String cat_name,String store_id) throws UnsupportedEncodingException{
		String cat_id = "0";
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("ver", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
    	
    	paramsMap.put("cat_name", cat_name);
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("parent_id", parent_id);
    	
    	String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
    	paramsMap.put("sign", sign);
    	
    	String web_url = "http://www.ehais.org";
    	String req = EHttpClientUtil.httpPost(web_url+"/Category-InsertApi.api", paramsMap);
		System.out.println(req);
		
		JSONObject json = JSONObject.fromObject(req);
		cat_id = String.valueOf(json.getJSONObject("model").getInt("cat_id"));
		return cat_id;
	}
	
	
	
	@Test
	public void brand() throws IOException{
		File input = new File("D:/workspace_luna/ehais/e-shop/src/main/webapp/WEB-INF/view/web/tianmao/index_content.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://localhost/");
		
		
		Elements j_MenuNav = doc.getElementsByClass("j_MenuNav");
		Elements j_CategoryMenuPannel = doc.getElementsByClass("j_CategoryMenuPannel");
//		System.out.println(j_CategoryMenuPannel.size());
		
		int i = 0;
		for (Element element : j_MenuNav) {
			
			String cat_id = "0";
			String cat_name = element.getElementsByTag("a").text().trim().replaceAll(" ", "");
			System.out.println("|"+cat_name+"|");
			cat_id = this.saveCategory(cat_id, cat_name, "3");

			Element elementcate = j_CategoryMenuPannel.get(i);
			Element sublogocon = elementcate.getElementsByClass("sub-logo-con").first();
			Elements logoa = sublogocon.getElementsByTag("a");
			int j = 0 ;
			for (Element element2 : logoa) {
				
				Elements imgEle = element2.getElementsByTag("img");
				if(imgEle == null)continue;
				String brand_logo = imgEle.first().attr("src").toString();
				String href = element2.attr("href").toString();
				brand_logo = brand_logo.indexOf("http:")>=0?brand_logo:"http:"+brand_logo;
				href = href.indexOf("http:")>=0?href:"http:"+href;
				
				this.saveBrand(cat_id, cat_name+"_"+i+"_"+j, brand_logo, href,null, "3");
				j++;
			}
			
			System.out.println("=============================");
			
			i++;
		}
		
	}
	
	@Test
	public void newHotBrandItemBody() throws IOException{
		File input = new File("D:/workspace_luna/ehais/e-shop/src/main/webapp/WEB-INF/view/web/tianmao/index_content.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://localhost/");
		
		
		Element j_newHotBrandItemBody = doc.getElementsByClass("j_newHotBrandItemBody").first();
		Elements branditem = j_newHotBrandItemBody.getElementsByClass("brand-item");
		int i = 0;
		for (Element element : branditem) {
			String brand_logo = element.getElementsByTag("img").first().attr("src").toString();
			String href = element.getElementsByTag("a").first().attr("href").toString();
			
			brand_logo = brand_logo.indexOf("http:")>=0?brand_logo:"http:"+brand_logo;
			href = href.indexOf("http:")>=0?href:"http:"+href;
			
			this.saveBrand(null, "最佳品牌"+"_"+i, brand_logo, href,"2", "3");
			i++;
			System.out.println(brand_logo+"  ==  "+href);
		}
	}
	
	private String saveBrand(String cat_id,String brand_name,String brand_logo,String site_url,String recommend,String store_id) throws UnsupportedEncodingException{
		String brand_id = "0";
		

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("ver", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
    	
    	paramsMap.put("brand_name", brand_name);
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("brand_logo", brand_logo);
    	paramsMap.put("site_url", site_url);
    	paramsMap.put("cat_id", cat_id);
    	paramsMap.put("recommend", recommend);
    	
    	String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
    	paramsMap.put("sign", sign);
    	
    	String web_url = "http://www.ehais.org";
    	String req = EHttpClientUtil.httpPost(web_url+"/Brand-InsertApi.api", paramsMap);
		System.out.println(req);
		
		JSONObject json = JSONObject.fromObject(req);
		brand_id = String.valueOf(json.getJSONObject("model").getInt("brand_id"));
		
		return brand_id;
	}
	
	
	@Test
	public void goods() throws IOException{
		File input = new File("D:/workspace_luna/ehais/e-shop/src/main/webapp/WEB-INF/view/web/tianmao/index_content.html");
		Document doc = Jsoup.parse(input, "UTF-8", "http://localhost/");
		
		Elements goodslist = doc.getElementsByClass("wonderful-item");
		
		int i = 0;
		for (Element element : goodslist) {
			String img = element.getElementsByTag("img").first().attr("src").toString();
			img = (img.indexOf("http:")>=0)?img:"http:"+img;
			String goodsName = element.getElementsByTag("em").first().text();
			String price = element.getElementsByClass("mui-price-integer").first().text();
			
			System.out.println(i+"---"+goodsName+" -- "+price);
			this.saveGoods(goodsName, price, img, "3");
			i++;
		}
		
	}
	
	
	private String saveGoods(String goods_name,String shop_price,String goods_img,String store_id) throws UnsupportedEncodingException{
		String goods_id = "0";
		

		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("ver", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
    	
    	paramsMap.put("goods_name", goods_name);
    	paramsMap.put("store_id", store_id);
    	paramsMap.put("shop_price", shop_price);
    	paramsMap.put("goods_img", goods_img);
    	paramsMap.put("goods_thumb", goods_img);
    	paramsMap.put("original_img", goods_img);
    	
    	String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
    	paramsMap.put("sign", sign);
    	
    	String web_url = "http://www.ehais.org";
    	String req = EHttpClientUtil.httpPost(web_url+"/Goods-Admin_Goods_SaveApi.api", paramsMap);
		System.out.println(req);
		
//		JSONObject json = new JSONObject(req);
//		goods_id = String.valueOf(json.getJSONObject("model").getInt("goods_id"));
		
		return goods_id;
	}
	
	
	@Test
    public void getAjaxPage() throws Exception{  
        WebClient webClient = new WebClient(BrowserVersion.CHROME);  
        webClient.getOptions().setJavaScriptEnabled(true);  
        webClient.getOptions().setCssEnabled(false);  
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
        webClient.getOptions().setTimeout(Integer.MAX_VALUE);  
        webClient.getOptions().setThrowExceptionOnScriptError(false);  
//        HtmlPage rootPage = webClient.getPage("http://127.0.0.1/admin.html");  
        HtmlPage rootPage = webClient.getPage("http://127.0.0.1/ehais/adminlogin");  
     
        System.out.println(rootPage.asXml());  
    } 
	
	
	@Test
	public void sinalogin() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(true);    //默认执行js，如果不执行js，则可能会登录失败，因为用户名密码框需要js来绘制。
        client.getOptions().setCssEnabled(false);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        client.getOptions().setThrowExceptionOnScriptError(false);        

        HtmlPage page = client.getPage("http://www.epshw.com/Default.php");
        //System.out.println(page.asText());
        HtmlForm form = page.getFormByName("lsform"); 
        //登录
        HtmlInput ln = page.getHtmlElementById("ls_username");
        HtmlInput pwd = page.getHtmlElementById("ls_password");
        HtmlInput btnSubmit = null;//page.getFirstByXPath("");

        ln.setAttribute("value", "lgj628");
        pwd.setAttribute("value", "628218");
        
        DomNodeList<DomElement> domElements=page.getElementsByTagName("input");
        for(DomElement temp:domElements){

        	if(temp.getAttribute("class").equals("comeing_lbt")){

        		btnSubmit = (HtmlInput) temp;
        		
        	}

        }
        
        
        HtmlPage page2 = btnSubmit.click();
        //登录完成，现在可以爬取任意你想要的页面了。
        System.out.println("\n\n\n");
        System.out.println(page2.asText());

        HtmlPage page3 = client.getPage("http://weibo.com/friends?leftnav=1&wvr=5&isfriends=1&step=2");
        System.out.println(" : " + page3.asXml());
        
        client.closeAllWindows();
	}

    
    
}
