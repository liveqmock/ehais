package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.weixin.model.WeiXinWCPay;

/**
 * 微信支付统计调用接口
 * 前提是所有值都需要正确输入
 * @author lgj628
 */
public interface WeiXinPayService {


	public WeiXinWCPay WeiXinPayApi(HttpServletRequest request,
			Integer store_id,
			Long user_id,
			String openid,
			String orderSn,
			Integer amount,
			String body,
			String table_name,
			Long table_id
			
			) throws Exception;
	
	
	
}
