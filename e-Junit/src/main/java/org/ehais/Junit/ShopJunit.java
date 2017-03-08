package org.ehais.Junit;

import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.junit.Test;

public class ShopJunit {

	private String web_url = "http://localhost:80";
//	private String web_url = "http://shop.ehais.com";
	private String timestamp = String.valueOf(System.currentTimeMillis());
	private String appkey = "ifuckehais";
	private String secret = "ufuckehais";
	
	@Test
	public void ad_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey);
	    	paramsMap.put("version", "v1.0");
	    	paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("page", "1");
	    	paramsMap.put("len", "10");
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("position_id", "1");
	    	paramsMap.put("enabled", "1");
	    	paramsMap.put("is_mobile", "0");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/adlist", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void admin_login(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("username", "admin");
	    	paramsMap.put("password", "admin888");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/admin_login", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void article_cat_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("parent_id", "0");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/article_cat_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void article_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("cat_code", "aaa");
	    	paramsMap.put("page", "1");
	    	paramsMap.put("len", "10");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/article_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void article_info(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("article_id", "37");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/article_info", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void region_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("parent_id", "1");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/region_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void region_id_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("region_ids", "1,4,6,7,8");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/region_id_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void useraddress_lists(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("user_id", "1");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/useraddress_lists", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void useraddress_info(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("addressId", "7");
	    	paramsMap.put("user_id", "1");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/useraddress_info", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
	@Test
	public void useraddress_delete(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("addressId", "8");
	    	paramsMap.put("user_id", "1");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/useraddress_delete", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void category_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "2");
	    	paramsMap.put("parent_id", "0");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/category_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void category_info(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("catId", "61");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/category_info", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void goods_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("catId", "2");
	    	paramsMap.put("page", "1");
	    	paramsMap.put("len", "20");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/goods_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void goods_info(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("goodsId", "6");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/goods_info", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void orderinfo_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("user_id", "1");
	    	paramsMap.put("page", "1");
	    	paramsMap.put("len", "20");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/orderinfo_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void orderinfo_info(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("user_id", "1");
	    	paramsMap.put("orderId", "2");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/orderinfo_info", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void payment_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
//	    	paramsMap.put("user_id", "1");
//	    	paramsMap.put("page", "1");
//	    	paramsMap.put("len", "20");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/payment_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void shipping_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
//	    	paramsMap.put("user_id", "1");
//	    	paramsMap.put("page", "1");
//	    	paramsMap.put("len", "20");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/shipping_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void CacheClear(){
		try{
			timestamp = String.valueOf(System.currentTimeMillis() / 1000);
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("ver", "v1.0"); paramsMap.put("timestamp", timestamp);
			paramsMap.put("store_id", "9001"); 
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign.toLowerCase());
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/Cache-Clear.api", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

	@Test
	public void cart_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey); paramsMap.put("version", "v1.0"); paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("store_id", "1");
	    	paramsMap.put("user_id", "1");
	    	paramsMap.put("s_encode", "8aa927807029b0bc73720c43733130f9s");
	    	
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/api/cart_list", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
