package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinWCPay;

/**
 * 微信支付统计调用接口
 * 前提是所有值都需要正确输入
 * @author lgj628
 */
public interface WeiXinPayService {

	/**
	 * 微信支付需要的js参数通过此方法返回
	 * @param request
	 * @param store_id
	 * @param user_id
	 * @param openid
	 * @param orderSn
	 * @param amount
	 * @param body
	 * @param table_name
	 * @param table_id
	 * @return
	 * @throws Exception
	 */
	public WeiXinWCPay WeiXinPayApi(HttpServletRequest request,
			String cid,
			String openid,
			String orderSn,
			Integer amount,
			String body,
			String table_name,
			Long table_id,
			String classify			
			) throws Exception;
	
	/**
	 * 微信支付回调方法
	 * @param request
	 * @param notify
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<WeiXinNotifyPay> WeiXinNotifyPay(HttpServletRequest request,
			WeiXinNotifyPay notifyPay,
			String cid,
			String classify) throws Exception;
	
}
