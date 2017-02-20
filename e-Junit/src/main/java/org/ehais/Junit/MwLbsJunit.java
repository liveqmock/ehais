package org.ehais.Junit;

import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.junit.Test;

public class MwLbsJunit {

	private String web_url = "http://shop.ehais.com";
	private String timestamp = String.valueOf(System.currentTimeMillis());
	private String appkey = "Ehasidfdf";
	private String secret = "Sewersdfser";
	private String store_id = "10";
	
	@Test
	public void lbs(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", appkey);
	    	paramsMap.put("version", "v1.0");
	    	paramsMap.put("timestamp", timestamp);
	    	paramsMap.put("store_id", store_id);
	    	
	    	paramsMap.put("phone", "13560217995");
	    	paramsMap.put("other_phone", "13560217994");
	    	paramsMap.put("latitude", "39.915291");
	    	paramsMap.put("longitude", "116.403857");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,secret);
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
			String req = EHttpClientUtil.httpPost(web_url+"/MwLbs-lbs.api", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
