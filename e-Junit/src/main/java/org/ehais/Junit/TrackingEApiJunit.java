package org.ehais.Junit;

import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.junit.Test;

public class TrackingEApiJunit {

	private String site = "http://localhost:8080";
	
	@Test
	public void Login(){
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("appkey", "GZERPO-sdwxRdsswt898");
    	paramsMap.put("version", "v1.0");
    	paramsMap.put("timestamp", System.currentTimeMillis()+"");
    	
    	paramsMap.put("username", "1");
    	paramsMap.put("password", "10");
		String req = EHttpClientUtil.httpPost(site+"/api/login?s=1", paramsMap);
		System.out.println("请求返回:"+req);
		
	}
}
