package org.ehais.Junit;

import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.junit.Test;

public class TuniuJUnit {
	
	
	
	@Test
	public void getHotelStaticInfo() {
		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("id", "361840");
		paramsMap.put("checkindate", "2018-05-24");
		paramsMap.put("checkoutdate", "2018-05-25");
		
//		String res = EHttpClientUtil.httpPost("http://hotel.tuniu.com/ajax/getHotelStaticInfo", paramsMap);
		
		String res = EHttpClientUtil.httpPost("http://hotel.tuniu.com/ajax/getHotelStaticInfo", paramsMap);
		System.out.println(res);
	}

}
