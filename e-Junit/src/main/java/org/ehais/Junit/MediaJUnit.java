package org.ehais.Junit;

import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.junit.Test;

public class MediaJUnit {
	
	private String web_url = "http://localhost";
	
	@Test
	public void edit() {
		try{
			Map<String, String> paramsMap = new HashMap<String, String>();
	    	
	    	paramsMap.put("articleId", "1");
	    	
	    	Map<String,String> mapHeader = new HashMap<String, String>();
	    	mapHeader.put("User-Agent", "gzepro");
			String req = EHttpClientUtil.httpPost(web_url+"/mediaArticleEditDetail", paramsMap);
			
			System.out.println("请求返回："+req);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
