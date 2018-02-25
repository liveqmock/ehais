package org.ehais.Junit.shop;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.ehais.model.HaiGoodsEntity;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;

import net.sf.json.JSONObject;

public class ShopCommonReptile {
//	private static String web_url = "http://eg.ehais.com";
	private static String web_url = "http://127.0.0.1";
	public static String appkey = "Ehais";
	public static String secret = "EhaisSecret";
	
	
	public static void category_save(String store_id ,String json){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("version", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
    	
    	paramsMap.put("json", json);
    	paramsMap.put("store_id", store_id.toString());
    	
    	try {
			String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
			paramsMap.put("sign", sign);

	    	String req = EHttpClientUtil.httpPost(web_url+"/api/category_save", paramsMap);
			System.out.println(req);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
	public static void goods_save(String store_id ,HaiGoodsEntity goodsEntity){
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", appkey);
    	paramsMap.put("version", "v1.0");
    	paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
    	
    	JSONObject obj = JSONObject.fromObject(goodsEntity);
    	paramsMap.put("json", obj.toString());
    	paramsMap.put("store_id", store_id.toString());
    	
    	try {
			String sign = SignUtil.getSignWS(paramsMap,secret).toLowerCase();
			paramsMap.put("sign", sign);

	    	String req = EHttpClientUtil.httpPost(web_url+"/api/goods_save", paramsMap);
			System.out.println(req);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
	
	public static void main(String[] args) throws ParseException {
//		String money = "23,099,99";
		String money = "23099.99";
		NumberFormat nf1 = NumberFormat.getInstance();
		Object obj1  = nf1.parse(money);
		System.out.println(obj1);
	}
	
}
