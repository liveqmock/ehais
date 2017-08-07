package org.ehais.Junit;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.ehais.util.ECommon;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.EConstants;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WeiXinArticles;
import org.ehais.weixin.model.WeiXinArticlesItem;
import org.ehais.weixin.model.WeiXinImage;
import org.ehais.weixin.model.WeiXinMPNews;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.utils.WeiXinUtil;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;

public class WeiXinJunit {
	//测试帐号
//	private String weixin_appid = "wxc59fc69f80bc79aa";
//	private String weixin_appsecret = "6e8f1c801e2c73c921b6003958546784";
	//易宝帐号
	private String weixin_appid = "wx9439cbf94f9235f0";
	private String weixin_appsecret = "22fef2fa1ebd5bfc7bad9a691651a125";
	
	private String site = "http://wxdev.ehais.com";//"http://localhost:8080";
	private String timestamp = String.valueOf(System.currentTimeMillis());
	private String nonce = ECommon.nonceStr(16);
//	private String access_token = "ahRh4ZJnIPixXCvEsGHaw0sZvao_JWk_zFSNJnhnu4TerLQVgWvgCdvWzw0Hf896zeu4svZvx7uqbgXbpKpFE3lZ__P0rVDXC7-9UxPKqY1iTCI-KVlkXudYi6BGxhIiRKLhABAYRS";
	private String access_token = "L5AM0Oc3ZtXVS3j-zSq3W3Qncvxf1leobFxJgT3aLz3r3MWSxPYubSv4suO5JkvapNtvY6oCGqOAWmcR73jiCFGllDagQDIm9Gbak0-E1VBMidBqiOuvSqlY1yUJBRD1UWOjAIABDV";

	/**
	 * 微信对接的接口
	 */
	@Test
	public void sing(){
		String apiurl = "http://wxsale.9351p.com/api.php?id=8";
		String token = "fzgom2mb4civshgj1qdfallzp1o1ccwt";
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		String nonce = ECommon.nonceStr(32);
		String echostr = ECommon.nonceStr(16);
		
		System.out.println("token:"+token);
		System.out.println("nonce:"+nonce);
		System.out.println("echostr:"+echostr);
		
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("timestamp", timestamp );
		paramsMap.put("nonce", nonce);
		paramsMap.put("echostr", echostr);
		try {
			paramsMap.put("signature", WeiXinUtil.wxSignature(token, paramsMap.get("timestamp"), paramsMap.get("nonce")));
			String result = EHttpClientUtil.eHttpGet(apiurl, paramsMap);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	@Test 
	public void getAccessToken() throws Exception {
		AccessToken token = WeiXinUtil.getAccessToken(0, weixin_appid, weixin_appsecret);
		JSONObject json = JSONObject.fromObject(token);
		System.out.println(json.toString());
	}
	@Test
	public void notityxml(){
		try{
			WeiXinNotityXml notity = new WeiXinNotityXml();
			notity.setToUserName("gh_79f716790299");
			notity.setFromUserName("oeLUAs534bNj5TWs5b7giGP4sP_c");
			notity.setCreateTime(1455607270l);
			notity.setMsgType("event");
			notity.setEvent("CLICK");
			notity.setEventKey("资讯关键字");
			notity.setMsgId(6251811558179586773l);
			WeiXinImage image = new WeiXinImage();
			image.setMediaId("fffff");
			notity.setImage(image);
			
			List<WeiXinArticlesItem> articles = new ArrayList<WeiXinArticlesItem>();
			
			articles.add(new WeiXinArticlesItem("aa","ff","dd","sss"));
			articles.add(new WeiXinArticlesItem("aa2","ff2","dd2","sss2"));

			notity.setArticleCount(articles.size());
			notity.setArticles(articles);
			
			String content = WeiXinUtil.fromNotityXml(notity);
			
			content = "<xml><ToUserName><![CDATA[gh_8503541adb8f]]></ToUserName><FromUserName><![CDATA[ocvLDvvaL10c2_CTwpDqlmLO3CNk]]></FromUserName><CreateTime>1457005456</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[VIEW]]></Event><EventKey><![CDATA[http://weixin.gzgas.com/weixinpro/gowx.action?wxid=6&order_sys_code=suggest/form]]></EventKey><MenuId>405691421</MenuId></xml>";
			
			System.out.println(content);
			
			WeiXinNotityXml notity2 = WeiXinUtil.toNotityXml(content);
			System.out.println(notity2.getFromUserName()
					+"="+
			notity2.getTicket()+"="+notity2.getMsgId());
			
			String url = "http://localhost:8080/weixin/wx?id=1&signature=7e686a21a47405e0bf49191a0094fc2f47538e74&timestamp=1455606111&nonce=1118768670";//&echostr=7364046396266381279
			String req = EHttpClientUtil.httpClientRequest(url, content);
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void notityBackPay(){
		try{
//			String xml = "<xml><appid><![CDATA[wx9439cbf94f9235f0]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1245521602]]></mch_id><nonce_str><![CDATA[ani64biyvb0cj7nsro917q76uukque4e]]></nonce_str><openid><![CDATA[oT2uMs9CG4LOqo3epOZS9gJJkNwo]]></openid><out_trade_no><![CDATA[20160229143916445362]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[84CC795005F58687E6D105A8DE570572]]></sign><time_end><![CDATA[20160229143923]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[1003280855201602293619746081]]></transaction_id></xml>";
			String xml = "<xml><appid><![CDATA[wxb7e05d362dab27b1]]></appid><attach><![CDATA[weixin]]></attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1480510742]]></mch_id><nonce_str><![CDATA[djkk5c6xxo9azk756wmydiggkc1yalnn]]></nonce_str><openid><![CDATA[oiGBot1K1vYJA2DFv2B-0W2xL9O0]]></openid><out_trade_no><![CDATA[2017080121134882991501593228]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[AAC38B326AD519BBAD7813D751D1C99C]]></sign><time_end><![CDATA[20170801211353]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4001192001201708013920623277]]></transaction_id></xml>";
			
//			String url = site + "/weixin/notify_pay";
			String url = "http://www.tpshop.org/api.php/Api/DiningApi/notifyUrl";
			String req = EHttpClientUtil.httpClientRequest(url, xml);
			System.out.println("请求返回："+req);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 关注事件
	 */
	@Test
	public void subscribe(){
		
		try{
			WeiXinNotityXml notity = new WeiXinNotityXml();
			notity.setToUserName("gh_79f716790299");
			notity.setFromUserName("oeLUAs534bNj5TWs5b7giGP4sP_c");
			notity.setCreateTime(1455607270l);
			notity.setMsgType("event");
			notity.setEvent("subscribe");
			
			String content = WeiXinUtil.fromNotityXml(notity);
			String signature = WeiXinUtil.signature("ehais_wxdev",timestamp, nonce);
			
			String req = EHttpClientUtil.httpClientRequest(
					site+"/weixin/wx_6?timestamp="+timestamp+"&nonce="+nonce+"&signature="+signature, 
					content);
			System.out.println("请求返回："+req);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void ArticlePic(){
		WeiXinArticles article = new WeiXinArticles();
		article.setToUserName("sss");
		article.setFromUserName("ffffff");
		article.setCreateTime(System.currentTimeMillis());
		article.setMsgType("news");
		article.setArticleCount(2);
		
		List<WeiXinArticlesItem> Articles = new ArrayList<WeiXinArticlesItem>();
		
		Articles.add(new WeiXinArticlesItem("fuckyou", "ifuckyou", "http://www.", "http://www.baidu.com"));
		Articles.add(new WeiXinArticlesItem("fuckyou2", "ifuckyou2", "http://www.", "http://www.baidu.com"));
		
		
		article.setArticles(Articles);
		
		
		XStream xStream = new XStream();
		xStream.alias("xml",WeiXinArticles.class);
		xStream.alias("item",WeiXinArticlesItem.class);
		String content = xStream.toXML(article);
		System.out.println(content);
	}
	
	
	@Test
	public void uploadImage(){
		Map<String ,String> fileMap = new HashMap<String ,String>();
		Map<String ,String> parMap = new HashMap<String ,String>();	
		fileMap.put("media", "/Users/gzepro/Downloads/Wolf_256px_1084085_easyicon.net.png");
		
		try {
			AccessToken token = new AccessToken();//WeiXinUtil.getAccessToken(8, "wx722e9a654b5d58cc", "63be13ed69ff195b599d4ccd53b8ca93");

			token.setToken("jB0Umel_cMrsheRvPjUOFUBQz2VzQtr1TGXMGFqvKLuj224rD_L22hQRHVHJOZuCCEyUhB7p3K27KE9SK-UIyjb8s4X1F5aTANjeOjcanvmDl73fgf5J4x_-b61yEUd_BIQcADAOVD");
//			String req = EHttpClientUtil.postHttpClientFile(EConstants.upload_img.replaceAll("ACCESS_TOKEN", token.getToken()),parMap, fileMap,null);
//			System.out.println(req);
//			{"url":"http:\/\/mmbiz.qpic.cn\/mmbiz\/mX3BibrEkJQib7dpudfBBb9lmPVFFqc2MHcP4S5udtUnVd23QkOibRyAbmVJcnLHV2piaUyDlAuZicpeqGH2Kt5o0zw\/0"}

			String req = EHttpClientUtil.postHttpClientFile(
					EConstants.upload_media.replace("ACCESS_TOKEN", token.getToken()).replace("TYPE", "image"),
					parMap, fileMap,null);
			System.out.println(req);
//			{"type":"image","media_id":"Dfrvsz7yN5Ho32Avh7VKLvV2_e9dC0M63ZRngkU19IFKZ_Oxb5X-xO74c6m1wOoy","created_at":1460361822}

			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void uploadNews(){
			
		
		try {
			WeiXinMPNews mp1 = new WeiXinMPNews("test tyler f y", "Dfrvsz7yN5Ho32Avh7VKLvV2_e9dC0M63ZRngkU19IFKZ_Oxb5X-xO74c6m1wOoy", "tyler",
					"www.baidu.com", "<h1>TTyerll </h1><p>fkkuc you</p>", "图文消息的描述",
					"1");
			WeiXinMPNews mp2 = new WeiXinMPNews("test tyler f y", "Dfrvsz7yN5Ho32Avh7VKLvV2_e9dC0M63ZRngkU19IFKZ_Oxb5X-xO74c6m1wOoy", "tyler",
					"www.baidu.com", "<h1>TTyerll </h1><p>fkkuc you</p>", "图文消息的描述",
					"0");
			WeiXinMPNews mp3 = new WeiXinMPNews("test tyler f y", "Dfrvsz7yN5Ho32Avh7VKLvV2_e9dC0M63ZRngkU19IFKZ_Oxb5X-xO74c6m1wOoy", "tyler",
					"www.baidu.com", "<h1>TTyerll </h1><p>fkkuc you</p>", "图文消息的描述",
					"0");
			List<WeiXinMPNews> newsList = new ArrayList<WeiXinMPNews>();
			newsList.add(mp1);newsList.add(mp2);newsList.add(mp3);
			Map<String, List<WeiXinMPNews>> newsMap = new HashMap<String, List<WeiXinMPNews>>();
			newsMap.put("articles", newsList);
			JSONObject json = JSONObject.fromObject(newsMap);
			String content = json.toString();
			System.out.println(content);
			
			
			AccessToken token = new AccessToken();//WeiXinUtil.getAccessToken(8, "wx722e9a654b5d58cc", "63be13ed69ff195b599d4ccd53b8ca93");
			token.setToken("jB0Umel_cMrsheRvPjUOFUBQz2VzQtr1TGXMGFqvKLuj224rD_L22hQRHVHJOZuCCEyUhB7p3K27KE9SK-UIyjb8s4X1F5aTANjeOjcanvmDl73fgf5J4x_-b61yEUd_BIQcADAOVD");

			
			String req = EHttpClientUtil.httpPostEntity(
					EConstants.upload_news.replace("ACCESS_TOKEN", token.getToken()),
					content);
			System.out.println(req);
//			{"type":"news","media_id":"ShCRrjPK2nPgd6O-aO2e4qoO1omf0FX2xyZV4_lkTDNB2gxCvbkpZNxwsxifevma","created_at":1460362918}

			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void mass_sendall(){


		try {
			AccessToken token = WeiXinUtil.getAccessToken(8, "wx722e9a654b5d58cc", "63be13ed69ff195b599d4ccd53b8ca93");
//			token.setToken("jB0Umel_cMrsheRvPjUOFUBQz2VzQtr1TGXMGFqvKLuj224rD_L22hQRHVHJOZuCCEyUhB7p3K27KE9SK-UIyjb8s4X1F5aTANjeOjcanvmDl73fgf5J4x_-b61yEUd_BIQcADAOVD");

			
//			String req = WeiXinUtil.mass_sendall(token.getToken(), true, null, "mpnews", "ShCRrjPK2nPgd6O-aO2e4qoO1omf0FX2xyZV4_lkTDNB2gxCvbkpZNxwsxifevma", null);
			String req = WeiXinUtil.mass_sendall(token.getToken(), true, null, "text", "ShCRrjPK2nPgd6O-aO2e4qoO1omf0FX2xyZV4_lkTDNB2gxCvbkpZNxwsxifevma", "我要顶你个肺推送信息文档顶!--ShCRrjPK2nPgd6O-aO2e4qoO1omf0FX2xyZV4_lkTDNB2gxCvbkpZNxwsxifevma");
			System.out.println(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void wx_click_keyword(){
//		<xml><ToUserName><![CDATA[gh_8503541adb8f]]></ToUserName><FromUserName><![CDATA[ocvLDvhGspdKWCp5QJlJhG-x9pvo]]></FromUserName><CreateTime>1460345997</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[CLICK]]></Event><EventKey><![CDATA[everydaypush]]></EventKey></xml>
		try{
			WeiXinNotityXml notity = new WeiXinNotityXml();
			notity.setToUserName("gh_8503541adb8f");
			notity.setFromUserName("ocvLDvhGspdKWCp5QJlJhG-x9pvo");
			notity.setCreateTime(1455607270l);
			notity.setMsgType("event");
			notity.setEvent("CLICK");
			notity.setEventKey("everydaypush");
			
			String content = WeiXinUtil.fromNotityXml(notity);
			
			String signature = WeiXinUtil.signature("ehais_wxdev",timestamp, nonce);
			
			String req = EHttpClientUtil.httpClientRequest(
					site+"/weixin/wx8?timestamp="+timestamp+"&nonce="+nonce+"&signature="+signature, 
					content);
			System.out.println("请求返回："+req);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void qrcode() throws Exception{
//		String url = "http://weixin.qq.com/q/mUhsnGLm-4eGstRc8ma8";
//		System.out.println(url.length());
		String req = WeiXinUtil.QRcode(access_token, "QR_LIMIT_SCENE", null, null, "http://wxdev.ehais.com");
		System.out.println(req);
//		{"ticket":"gQHA7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xLzFFZ2ZqMHJsY0ljSnhKbXpnV2E4AAIEAHAgVwMEgFEBAA==","expire_seconds":86400,"url":"http:\/\/weixin.qq.com\/q\/1Egfj0rlcIcJxJmzgWa8"}
//		http://weixin.qq.com/q/1Egfj0rlcIcJxJmzgWa8
		
//		{"ticket":"gQGa7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL3hVZ3k5YmJsVTRjcVVZckhyR1M4AAIE4H0gVwMEAAAAAA==","url":"http:\/\/weixin.qq.com\/q\/xUgy9bblU4cqUYrHrGS8"}
//		http://weixin.qq.com/q/xUgy9bblU4cqUYrHrGS8
	}
	
	@Test
	public void ShortUrl() throws Exception{
//		String url = "http://weixin.qq.com/q/mUhsnGLm-4eGstRc8ma8";
//		System.out.println(url.length());
		String req = WeiXinUtil.ShortUrl(access_token,"long2short", "http://www.weixiaoqu.com/");
		System.out.println(req);
//		http://w.url.cn/s/AUTPjEd
	}
	
	@Test
	public void MerchantInfo() throws Exception{

		String req = WeiXinUtil.MerchantInfo(access_token);
		System.out.println(req);
	}
	
	@Test
	public void wxencode() throws UnsupportedEncodingException{
		String url = "https://itunes.apple.com/cn/app/bai-du-tu-shou-ji-tu-lu-xian/id452186370?mt=8";
		String eurl = java.net.URLEncoder.encode(url, "UTF8");
		System.out.println(eurl);
	}
	
	@Test
	public void long2short() throws Exception{
		JSONObject obj = new JSONObject();
		obj.put("long_url", "http://bigdata.guangdongip.gov.cn/ipc/ipcSearch.do");
		obj.put("action", "long2short");
		System.out.println(obj.toString());
		
		
		String req = EHttpClientUtil.httpPostEntity("https://api.weixin.qq.com/cgi-bin/shorturl?access_token=j1fjzDplAcIrRMYrH5L1Nand5ItguPxBYCnM9j3GCfeehJaAQrOy4HYhvJMYzRlP6ksP3xUWzplvLrGgk4cOwxtPa2r5Q95gmexWRM0idOuTNzVM6748C3bRxby8s74DDYAcAGASNP", 
				obj.toString());
		System.out.println(req);
		
		
	}
	
	@Test
	public void notityPay(){
		String notityXml = "<xml><appid><![CDATA[wxb154991e8c8b6b09]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1340156101]]></mch_id><nonce_str><![CDATA[znwrd04quv2y3no0iqiebosxiajv9ffh]]></nonce_str><openid><![CDATA[o5eMcvy2z2ocEYN7B1m13qv0xsDQ]]></openid><out_trade_no><![CDATA[201610291504462657420005400055]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[1661E218B4AE3B2619D91BB0C77ED08E]]></sign><time_end><![CDATA[20161029150452]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4003282001201610298065761657]]></transaction_id></xml>";
		try {
			WeiXinNotifyPay notifyPay = WeiXinUtil.toNotifyPayXml(notityXml);
			Map<String,Object> map = notifyPay.toMap();
			
			String msg = SignUtil.getSign(map, "03ceb55d188275e9f1ccc85eb15fd6ce");
			
			System.out.println("1661E218B4AE3B2619D91BB0C77ED08E");
			System.out.println(msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void singCode(){
		String url = "http://wxsale.9351p.com/app/index.php?id=8&code="+ECommon.nonceStr(32);
		System.out.println(url);
		try{
			String result = EHttpClientUtil.methodGet(url);
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
