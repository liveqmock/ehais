package com.ehais.huangbao.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.EOrder;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.model.WpPublic;

public interface WXPayService extends CommonService {

	public ReturnObject<WeiXinWCPay> UnifiedOrder(
			Integer wxid,
			String openid,
			String amount,
			String realname,
			String mobile,
			String remark,
			Integer conId,
			String trade_type,
			WpPublic wpPublic,
			HttpServletRequest request
			)throws Exception;
	
	public ReturnObject<WeiXinWCPay> UnifiedOrderByOpenId(
			String openid,WpPublic wpPublic
			)throws Exception;
	
	
	
	
	/**获取订单的信息
	 * 比如支付记录
	 * @param openid
	 * @param orderSysCode
	 * @param ePayStatus
	 * @param page
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<EOrder> eOrderList(String openid,String orderSysCode,Integer ePayStatus,Integer page,Integer len)throws Exception;
	
	
	/**根据订单类型获取记录信息
	 * @param orderSysCode
	 * @param ePayStatus
	 * @param page
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<EOrder> eOrderList(List<String> order_sys_code_list,Integer ePayStatus,Integer page,Integer len)throws Exception;
	
	
	public ReturnObject<EOrder> eOrderListWXID(Integer wxid ,Integer ePayStatus,Integer page,Integer len)throws Exception;
	
	
}
