package org.ehais.epublic.service.impl;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.ehais.enums.EOrderStatusEnum;
import org.ehais.epublic.mapper.weixin.WxNotifyPayMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderResultMapper;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.model.weixin.WxUnifiedorder;
import org.ehais.epublic.model.weixin.WxUnifiedorderResult;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.util.ECommon;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinUnifiedOrder;
import org.ehais.weixin.model.WeiXinUnifiedOrderResult;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信支付统计调用接口
 * 前提是所有值都需要正确输入
 * @author lgj628
 */
@Service("weiXinPayService")
public class WeiXinPayServiceImpl implements WeiXinPayService{

	@Autowired
	private WxNotifyPayMapper wxNotifyPayMapper;
	@Autowired
	private WxUnifiedorderMapper wxUnifiedorderMapper;
	@Autowired 
	private WxUnifiedorderResultMapper wxUnifiedorderResultMapper;
	@Autowired
	private EWPPublicService eWPPublicService; 
	
	@Override
	public WeiXinWCPay WeiXinPayApi(HttpServletRequest request, Integer store_id, 
			Long user_id, String openid,String orderSn,
			Integer amount, String body, String table_name, Long table_id) throws Exception {
		// TODO Auto-generated method stub
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		InetAddress address = InetAddress.getLocalHost();//获取的是本地的IP地址
		
		Date date = new Date();
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(wpPublic.getAppid());
		order.setMch_id(wpPublic.getMchId());
		order.setNonce_str(ECommon.nonceStr(32));
		order.setBody(body);
		order.setOut_trade_no(orderSn);
		order.setTotal_fee(amount);		
		order.setSpbill_create_ip(address.getHostAddress());//120.27.35.142
		order.setNotify_url(request.getScheme()+"://"+request.getServerName()+"/weixin/pay/notify_url");
		order.setTrade_type("JSAPI");
		order.setOpenid(openid);
		
		order.setSign(SignUtil.getSign(order.toMap(), wpPublic.getMchSecret()));
		
		WeiXinUnifiedOrderResult result = WeiXinUtil.WeiXinUnifiedOrderApi(order);
		
		//插入数据库
		WxUnifiedorder wxUnifiedorder = this.toWxUnifiedOrder(order);
		wxUnifiedorderMapper.insert(wxUnifiedorder);
		
		
		//插入数据库
		if(result != null){
			WxUnifiedorderResult orderResult =  toWxUnifiedorderResult(result);
			wxUnifiedorderResultMapper.insert(orderResult);
			
			WeiXinWCPay wcPay = new WeiXinWCPay();
			wcPay.setAppId(wpPublic.getAppid());
			wcPay.setTimeStamp(String.valueOf(System.currentTimeMillis()));
			wcPay.setNonceStr(ECommon.nonceStr(32));
			wcPay.setPack_age("prepay_id="+result.getPrepay_id());
			wcPay.setSignType("MD5");
			wcPay.setPaySign(SignUtil.getSign(wcPay.toMap(), wpPublic.getMchSecret()));
			
			return wcPay;
			
		}
		
		return null;
	}
	
	
	private WxUnifiedorder toWxUnifiedOrder(WeiXinUnifiedOrder model) {
		WxUnifiedorder order = new WxUnifiedorder();
		order.setAppid(model.getAppid());
		order.setMchId(model.getMch_id());
		order.setDeviceInfo(model.getDevice_info());
		order.setNonceStr(model.getNonce_str());
		order.setSign(model.getSign());
		order.setBody(model.getBody());
		order.setDetail(model.getDetail());
		order.setAttach(model.getAttach());
		order.setOutTradeNo(model.getOut_trade_no());
		order.setFeeType(model.getFee_type());
		order.setTotalFee(model.getTotal_fee());
		order.setSpbillCreateIp(model.getSpbill_create_ip());
		order.setTimeStart(model.getTime_start());
		order.setTimeExpire(model.getTime_expire());
		order.setGoodsTag(model.getGoods_tag());
		order.setNotifyUrl(model.getNotify_url());
		order.setTradeType(model.getTrade_type());
		order.setProductId(model.getProduct_id());
		order.setLimitPay(model.getLimit_pay());
		order.setOpenid(model.getOpenid());
		order.setePayStatus(EOrderStatusEnum.init.shortValue());
		return order;
	}
	
	
	private WxUnifiedorderResult toWxUnifiedorderResult(WeiXinUnifiedOrderResult model) {
		WxUnifiedorderResult order = new WxUnifiedorderResult();
//		this.wxOrderResultId = wxOrderResultId;
		order.setReturnCode(model.getReturn_code());
		order.setReturnMsg(model.getReturn_msg());
		order.setDeviceInfo(model.getDevice_info());
		order.setAppid(model.getAppid());
		order.setMchId(model.getMch_id());
		order.setNonceStr(model.getNonce_str());
		order.setSign(model.getSign());
		order.setResultCode(model.getResult_code());
		order.setPrepayId(model.getPrepay_id());
		order.setTradeType(model.getTrade_type());
		order.setErrCode(model.getErr_code());
		order.setErrCodeDes(model.getErr_code_des());
		
		return order;
	}

}
