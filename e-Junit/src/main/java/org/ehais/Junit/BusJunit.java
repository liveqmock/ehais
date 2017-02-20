package org.ehais.Junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ehais.util.ECommon;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinArticlesItem;
import org.ehais.weixin.model.WeiXinImage;
import org.ehais.weixin.model.WeiXinNotityXml;
import org.ehais.weixin.utils.WeiXinUtil;
import org.junit.Test;


public class BusJunit {


//	private String web_url = "http://127.0.0.1:8080/api";
	private String web_url = "http://dzbus.its-iot.com/api";
	private String timestamp = String.valueOf(System.currentTimeMillis());
	
	@Test
	public void route_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", "GZERPO-sdwxRdsswt898");
	    	paramsMap.put("version", "v1.0");
	    	paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("pageNo", "1");
	    	paramsMap.put("pageSize", "10");
//	    	paramsMap.put("userId", "1");
	    	paramsMap.put("state", "0");
	    	paramsMap.put("routeName", "");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,"PersonBusBRTsdfdiiu56755");
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.postHttpClient(web_url+"/route_list.action", paramsMap,mapHeader);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void ticket_list(){
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("appkey", "GZERPO-sdwxRdsswt898");
	    	paramsMap.put("version", "v1.0");
	    	paramsMap.put("timestamp", timestamp);
	    	
	    	paramsMap.put("pageNo", "1");
	    	paramsMap.put("pageSize", "10");
	    	paramsMap.put("userId", "301");
	    	paramsMap.put("state", "1");
	    	paramsMap.put("status", "");
	    	
	    	String sign = SignUtil.getSignWS(paramsMap,"PersonBusBRTsdfdiiu56755");
	    	paramsMap.put("sign", sign);
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.postHttpClient(web_url+"/ticket_list.action", paramsMap,mapHeader);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
