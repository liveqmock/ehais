package org.ehais.Junit;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestParam;


public class RetailerJunit {

//	String webSite = "http://topzh.9351p.com";
	String webSite = "http://192.168.1.222:48080";
//	String webSite = "http://localhost:8080";
	/**
	 * 首页广告
	 */
	@Test
	public void indexApi(){
		String api = webSite+"/api/home_adJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
	/**
	 *会员注册
	 */
	
	@Test
	public void memder_submit(){
		String api = webSite+"/api/memder_submit";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("phone", "1383838438");
		paramsMap.put("name", "麻麻tyler");
		paramsMap.put("userId", "1");
		paramsMap.put("expRegisterType", "APP");
		paramsMap.put("babyStatus", "0");
		paramsMap.put("babyDate", "2017-04-23");
		paramsMap.put("babySex", "1");
		paramsMap.put("babyName", "宝宝tyler");
		paramsMap.put("memberTab", "mama");
		
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
	
	@Test
	public void findMamCousumeRecordJson() throws ParseException{
		String api = "http://localhost/api/findMamCousumeRecordJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("page", "1");
		paramsMap.put("memberId", "1");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		System.out.println(result);
		
	}
	

	/**
	 * 活动
	 * @throws ParseException
	 */
	@Test
	public void findActivityJson() throws ParseException{
		String api = "http://localhost/api/findActivityJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("page", "1");
		paramsMap.put("memberId", "1");
		paramsMap.put("state", "notJoin");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
	
	/**
	 * 获取验证
	 * @throws ParseException
	 */
	@Test
	public void get_phone_code() throws ParseException{
		String api = "http://localhost/api/get_phone_code.api";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("mobile", "13640777196");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
	/**
	 * 代金券
	 * @throws ParseException
	 */
	@Test
	public void findMamCouponJson() throws ParseException{
		String api = "http://localhost/api/findMamCouponJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("memberId", "1");
		paramsMap.put("page", "1");
		paramsMap.put("state", "notUsed");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
	/**
	 * 优惠卷详细信息
	 * @throws ParseException
	 */
	@Test
	public void getMamCoupon() throws ParseException{
		String api = "http://localhost/api/getMamCoupon.api";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("couponId", "1");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}

	/**
	 * 消费流水
	 * @throws ParseException
	 */
	@Test
	public void findCasPosSaleJson() throws ParseException{
		String api = "http://localhost/api/findCasPosSaleJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("memberId", "31");
		paramsMap.put("page", "1");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}

	
	/**
	 * 消费流水
	 * @throws ParseException
	 */
	@Test
	public void getCasPosSaleJson() throws ParseException{
		String api = "http://localhost/api/getCasPosSaleJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("posSaleId", "1");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
	/**
	 * 积分
	 * @throws ParseException
	 */
	@Test
	public void findMemCardPointLogJson() throws ParseException{
		String api = webSite+"/api/findMemCardPointLogJson";		
		Map<String, String> paramsMap = new HashMap<String, String>();
		
		paramsMap.put("state", "1");
		paramsMap.put("memberId", "31");
		String result = EHttpClientUtil.httpPost(api, paramsMap);
		System.out.println(result);
		
	}
	
}
