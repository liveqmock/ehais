package org.ehais.weixin.service.order;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.OrderPayModel;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinWCPay;

public interface OrderPayWeiXinService {

	/**
	 * 订单支付提交
	 * @描述 TODO
	 * @param request
	 * @param orderPayModel
	 * @return
	 * @throws Exception
	 * @作者 lgj628
	 * @日期 2016年10月27日
	 * @返回 ReturnObject<WeiXinWCPay>
	 */
	public ReturnObject<WeiXinWCPay> order_pay_submit(HttpServletRequest request,OrderPayModel orderPayModel)throws Exception;
	
	
	/**
	 * @描述 支付回调地址
	 * @param request
	 * @param notifyPay
	 * @return
	 * @throws Exception
	 * @作者 lgj628
	 * @日期 2016年10月28日
	 * @返回 ReturnObject<WeiXinNotifyPay>
	 */
	public ReturnObject<WeiXinNotifyPay> notify_order_pay(HttpServletRequest request,WeiXinNotifyPay notifyPay)throws Exception;
	
	
}
