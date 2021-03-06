package org.ehais.Junit;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.ehais.util.SignUtil;
import org.junit.Test;

public class DiningJUnit {

	@Test
	public void dining_manage_login() throws UnsupportedEncodingException{
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("appkey", "Ehais");
		paramsMap.put("version", "v1");
		paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		paramsMap.put("appkey", "Ehais");
		
		paramsMap.put("username", "dining");
		paramsMap.put("password", "123456ok");
		String sign = SignUtil.getSignWS(paramsMap, "EhaisSecret");
		paramsMap.put("sign", sign);
//		String req = EHttpClientUtil.httpPost("http://127.0.0.1/api/dining_manage_login", paramsMap);
		String req = EHttpClientUtil.httpPost("http://mg.ehais.com/api/dining_manage_login", paramsMap);
		System.out.println(req);
	}
	
	@Test
	public void dinint_order_list() throws UnsupportedEncodingException{
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("appkey", "Ehais");
		paramsMap.put("version", "v1");
		paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		paramsMap.put("appkey", "Ehais");
		paramsMap.put("store_id", "58");
		paramsMap.put("token", "89s5ik8bltru08jt");
		String sign = SignUtil.getSignWS(paramsMap, "EhaisSecret");
		paramsMap.put("sign", sign);
		String req = EHttpClientUtil.httpPost("http://127.0.0.1/api/dining_order_list", paramsMap);
		System.out.println(req);
	}
	
	
	@Test
	public void ar_write_code() throws UnsupportedEncodingException{
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("appkey", "Ehais");
		paramsMap.put("version", "v1");
		paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		paramsMap.put("appkey", "Ehais");
		paramsMap.put("code", "v002");
		String sign = SignUtil.getSignWS(paramsMap, "EhaisSecret");
		paramsMap.put("sign", sign);
//		String req = EHttpClientUtil.httpPost("http://127.0.0.1/api/dining_manage_login", paramsMap);
		String req = EHttpClientUtil.httpPost("http://127.0.0.1:800/api/write_code", paramsMap);
		System.out.println("req:"+req);
	}
	
	
	@Test
	public void ar_read_code() throws UnsupportedEncodingException{
		Map<String,String> paramsMap = new HashMap<String,String>();
		paramsMap.put("appkey", "Ehais");
		paramsMap.put("version", "v1");
		paramsMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		paramsMap.put("appkey", "Ehais");
		String sign = SignUtil.getSignWS(paramsMap, "EhaisSecret");
		paramsMap.put("sign", sign);
//		String req = EHttpClientUtil.httpPost("http://127.0.0.1/api/dining_manage_login", paramsMap);
		String req = EHttpClientUtil.httpPost("http://127.0.0.1:800/api/read_code", paramsMap);
		System.out.println("req:"+req);
	}
	
	
}
