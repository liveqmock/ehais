package org.ehais.Junit;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.ehais.epublic.model.unionpay.UnionpayRcvResponse;
import org.ehais.util.Bean2Utils;
import org.ehais.util.EHttpClientUtil;
import org.junit.Test;

public class UnionPayJUnit {

	@Test
	public void backRcv(){
		UnionpayRcvResponse resp = new UnionpayRcvResponse();
		resp.setTxnType("01");
		resp.setRespCode("00");
		resp.setCurrencyCode("156");
		resp.setMerId("802440083980503");
		resp.setSettleDate("0210");
		resp.setTxnSubType("01");
		resp.setVersion("5.0.0");
		resp.setTxnAmt("1");
		resp.setSignMethod("01");
		resp.setCertId("69597475696");
		resp.setSettleAmt("1");
		resp.setTraceTime("0210150720");
		resp.setEncoding("UTF-8");
		resp.setSettleCurrencyCode("156");
		resp.setBizType("000201");
		resp.setRespMsg("success");
		resp.setTraceNo("219240");
		resp.setQueryId("201702101507202192408");
		resp.setSignature("oPdxbOm4ZFx/EiL0r94JfoGLwVjujfq5hWcDKAaeepUfqLPRpSMZcbQTj2mCkl3mUBl9cy2aaz+JmTldNxrqvfwL3FWNijyJDIHWK7PTUDuYGpde1I7KYnfXxJbM1yZoxpJ798ypEG9oXZzEzGyXCxLl9Em1L5ptYhtItEyQAkCgPeUMlWhDQiOBogNo48IxAY1L6WmDV106x8EvZc0SkHmMQaqP86uaiYhq0crV4P1DhSbrfBA96ETXnY7CMAeXlwJasszAQyxMwY78+6mufZiQ2ltLvxg7mHAHUANcjNeZHTEPXmj8hfX9fGZVVcshZumgAcZ3EZdH6JgyXuNJHA==");
		resp.setOrderId("20170210150720110536000540008488");
		resp.setTxnTime("20170210150720");
		resp.setAccessType("0");
		
		List<NameValuePair> reqEntity = Bean2Utils.toNameValuePairList(resp);
		System.out.println(resp.getSignature().length());
		try {
			
			String rep = EHttpClientUtil.PostHttpClientService("http://localhost:8087/unionpay/FrontRcvResponse", reqEntity);
			System.out.println(rep);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
