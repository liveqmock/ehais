package org.ehais.Junit.Patents;

import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.junit.Test;

public class PatentsJUnit {
//	fmzl_ft@CN106034362A
//	http://search.guangdongip.gov.cn/search/doDetailSearch
//	pid
	
	@Test
	public void doDetailSearch(){
		String url = "http://search.guangdongip.gov.cn/search/doDetailSearch";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("pid", "fmzl_ft@CN106034362A");
		String req = EHttpClientUtil.httpPost(url, paramsMap);
		System.out.println(req);
	}
	
	
}
