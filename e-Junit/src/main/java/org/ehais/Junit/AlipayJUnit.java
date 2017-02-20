package org.ehais.Junit;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ehais.util.EHttpClientUtil;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

public class AlipayJUnit {

	@Test
	public void return_url(){
		String url = "http://localhost:8087/alipay/return_url";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("is_success", "T");
		paramsMap.put("sign_type", "MD5");
		paramsMap.put("sign", "4588e649235c03b305504fdec7c03303");
		paramsMap.put("partnerId", "2088101011044223");
		paramsMap.put("charset", "UTF-8");
		paramsMap.put("out_trade_no", "9787191563543236");
		paramsMap.put("trade_no", "2011061301644666");
		paramsMap.put("subject", "捐赠捐赠捐赠捐赠捐赠捐赠捐赠捐赠捐赠");
		paramsMap.put("total_fee", "10.00");
		paramsMap.put("exterface", "create_donate_trade_p");
		paramsMap.put("payment_type", "4");
		paramsMap.put("body", "捐款10元");
		paramsMap.put("trade_status", "TRADE_FINISHED");
		paramsMap.put("notify_id", "RqPnCoPT3K9%2Fvwbh3I7xtEuqRSQ65%2F%2BTwEqimZ9fcsnUfSnAocDs0HBmN%2FAwjRLjEbTK");
		paramsMap.put("notify_time", "2016-12-30 10:08:20");
		paramsMap.put("notify_type", "trade_status_sync");
		paramsMap.put("seller_email", "ali091433@alitest.com");
		paramsMap.put("buyer_email", "tttest123@alitest.com");
		paramsMap.put("seller_id", "2088101011044223");
		paramsMap.put("buyer_id", "2088102012340663");
		paramsMap.put("bank_seq_no", "9220031730");
		
		
		String res = EHttpClientUtil.httpPost(url, paramsMap);
		System.out.println(res);
	}
	
	
	@Test
	public void notify_url(){
		String url = "http://localhost:8087/alipay/notify_url";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("notify_time", "2016-12-30 10:08:20");
		paramsMap.put("notify_type", "trade_status_sync");
		paramsMap.put("notify_id", "70fec0c2730b27528665af4517c27b95");
		paramsMap.put("sign_type", "DSA");
		paramsMap.put("sign", "_p_w_l_h_j0b_gd_aejia7n_ko4_m%2Fu_w_jd3_nx_s_k_mxus9_hoxg_y_r_lunli_pmma29_t_q==");
		paramsMap.put("out_trade_no", "3618810634349901");
		paramsMap.put("trade_no", "2008102203208746");
		paramsMap.put("subject", "phone手机");
		paramsMap.put("total_fee", "10.00");
		paramsMap.put("payment_type", "1");
		paramsMap.put("body", "phone手机heollo");
		paramsMap.put("trade_status", "TRADE_FINISHED");
		paramsMap.put("gmt_create", "2012-12-22 20:49:31");
		paramsMap.put("gmt_payment", "2012-12-22 20:49:31");
		paramsMap.put("gmt_close", "2012-12-22 20:49:31");
		paramsMap.put("seller_email", "chao.chenc1@alipay.com");
		paramsMap.put("buyer_email", "13758698870");
		paramsMap.put("seller_id", "2088002007018916");
		paramsMap.put("buyer_id", "2088002007013600");
		paramsMap.put("price", "10.00");
		paramsMap.put("quantity", "3");
		paramsMap.put("discount", "-10");
		paramsMap.put("is_total_fee_adjus", "N");
		paramsMap.put("use_coupon", "N");
		
		
		
		String res = EHttpClientUtil.httpPost(url, paramsMap);
		System.out.println(res);
	}
	
}
