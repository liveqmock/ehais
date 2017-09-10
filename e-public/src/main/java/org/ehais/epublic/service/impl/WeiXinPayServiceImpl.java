package org.ehais.epublic.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.epublic.mapper.EHaiOrderInfoMapper;
import org.ehais.epublic.mapper.weixin.WxNotifyPayMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderResultMapper;
import org.ehais.epublic.model.EHaiOrderInfo;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.model.weixin.WxNotifyPay;
import org.ehais.epublic.model.weixin.WxUnifiedorder;
import org.ehais.epublic.model.weixin.WxUnifiedorderResult;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.IpUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinTemplateMessage;
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
	@Autowired
	private EHaiOrderInfoMapper eHaiOrderInfoMapper;
	@Autowired
	private EStoreService eStoreService;
		
	@Override
	public WeiXinWCPay WeiXinPayApi(HttpServletRequest request, String sid,
			String openid,
			String orderSn,
			Integer amount,
			String body,
			String table_name,
			Long table_id,
			String classify) throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = SignUtil.getUriStoreId(sid);
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		Map<String,Object> map = SignUtil.getDiningId(sid,wpPublic.getToken());
		
//		Date date = new Date();
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(wpPublic.getAppid());
		order.setMch_id(wpPublic.getMchId());
		order.setNonce_str(ECommon.nonceStr(32));
		order.setBody(body);
		order.setOut_trade_no(orderSn);
		order.setTotal_fee(amount);		
		order.setSpbill_create_ip(IpUtil.getIpAddrV2(request));
		order.setNotify_url(request.getScheme()+"://"+request.getServerName()+"/weixin/pay/notify_url!"+sid+"&"+classify);
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


	@Override
	public ReturnObject<WeiXinNotifyPay> WeiXinNotifyPay(HttpServletRequest request,
			WeiXinNotifyPay notifyPay,String sid,
			String classify) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WeiXinNotifyPay> rm = new ReturnObject<WeiXinNotifyPay>();
		rm.setCode(0);
		//进行签名验证
		Integer store_id = SignUtil.getUriStoreId(sid);
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		Map<String,Object> map = SignUtil.getDiningId(sid,wpPublic.getToken());
		
		String sign = SignUtil.getSign(Bean2Utils.toSignMap(notifyPay), wpPublic.getMchSecret());
		if(!sign.equals(notifyPay.getSign())){
			rm.setMsg("签名出错");return rm;
		}
		wxNotifyPayMapper.insert(this.toWxNotityPay(notifyPay));
		if(notifyPay.getReturn_code().equals("SUCCESS") && notifyPay.getReturn_code().equals("SUCCESS")){
			//查看订单是否正确
			List<EHaiOrderInfo> listOrder = eHaiOrderInfoMapper.listOrderInfoSn(store_id, notifyPay.getOut_trade_no());
			if(listOrder == null || listOrder.size() == 0){
				rm.setMsg("不存在此订单");return rm;
			}
			EHaiOrderInfo orderInfo = listOrder.get(0);
			if(orderInfo.getPayStatus() != EOrderStatusEnum.init){
				rm.setMsg("订单状态异常");return rm;
			}
			
			eHaiOrderInfoMapper.updateOrderPayStatus(store_id, notifyPay.getOut_trade_no(), EOrderStatusEnum.success);
			
			wxUnifiedorderMapper.UpdatePayStatue(store_id, notifyPay.getOut_trade_no(), EOrderStatusEnum.success);
			
			if(orderInfo.getClassify().equals(EOrderClassifyEnum.shop)){
				//商城订单通知用户
				this.shopUserTemplateMessage(request, notifyPay, orderInfo, wpPublic, store_id);
				//商城订单通知商户
				
			}else if(orderInfo.getClassify().equals(EOrderClassifyEnum.dining)){
				
			}
			
			
			rm.setCode(1);
			rm.setMsg("SUCCESS");
			
		}
		return rm;
	}

	
	public WxNotifyPay toWxNotityPay(WeiXinNotifyPay model) {
		WxNotifyPay p = new WxNotifyPay();
		p.setReturnCode(model.getReturn_code());
		p.setReturnMsg(model.getReturn_msg());
		p.setAppid(model.getAppid());
		p.setMchId(model.getMch_id());
		p.setDeviceInfo(model.getDevice_info());
		p.setNonceStr(model.getNonce_str());
		p.setSign(model.getSign());		
		p.setResultCode(model.getResult_code());
		p.setErrCode(model.getErr_code());
		p.setErrCodeDes(model.getErr_code_des());
		p.setOpenid(model.getOpenid());
		p.setIsSubscribe(model.getIs_subscribe());
		p.setTradeType(model.getTrade_type());
		p.setBankType(model.getBank_type());
		p.setTotalFee(model.getTotal_fee());
		p.setFeeType(model.getFee_type());
		p.setCashFee(model.getCash_fee());
		p.setCashFeeType(model.getCash_fee_type());
		p.setCouponFee(model.getCoupon_fee());
		p.setCouponCount(model.getCoupon_count());
		p.setCouponId1(model.getCoupon_id_1());
		p.setCouponFee1(model.getCoupon_fee_1());
		p.setTransactionId(model.getTransaction_id());
		p.setOutTradeNo(model.getOut_trade_no());
		p.setAttach(model.getAttach());
		p.setTimeEnd(model.getTime_end());
		
		return p;
	}
	
	/**
	 * 商城订单通知用户
	 * @throws Exception 
	 */
	private void shopUserTemplateMessage(HttpServletRequest request,
			WeiXinNotifyPay notifyPay,
			EHaiOrderInfo orderInfo,
			WpPublicWithBLOBs wpPublic,
			Integer store_id) throws Exception{
		WeiXinTemplateMessage template = new WeiXinTemplateMessage();
		template.setTemplate_id("DfQe86d1O0RUunKp5ZIRI-nMkPMyArRUdJV4XVi5vVs");//订单支付成功通知
		template.setTouser(notifyPay.getOpenid());
		template.setUrl(request.getScheme()+"://"+request.getServerName()+"/wx_order_detail!"+SignUtil.setOid(store_id, orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), notifyPay.getOpenid(), wpPublic.getToken()));
		template.setTopcolor("#FF0000");
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,String> first = new HashMap<String,String>();
		first.put("value", "您好，您的订单支付成功");
		first.put("color", "#173177");
		map.put("first", first);
		
		//商品名称
		Map<String,String> keyword1 = new HashMap<String,String>();
		keyword1.put("value", orderInfo.getGoodsDesc());
		keyword1.put("color", "#173177");
		map.put("keyword1", keyword1);
		
		//订单编号
		Map<String,String> keyword2 = new HashMap<String,String>();
		keyword2.put("value", notifyPay.getOut_trade_no());
		keyword2.put("color", "#173177");
		map.put("keyword2", keyword2);
		

		//支付金额
		Map<String,String> keyword3 = new HashMap<String,String>();
		keyword3.put("value", String.format("%.2f", notifyPay.getTotal_fee().doubleValue() / 100));
		keyword3.put("color", "#173177");
		map.put("keyword3", keyword3);
		
		Map<String,String> remark = new HashMap<String,String>();
		remark.put("value", "感谢你的光临!");
		remark.put("color", "#173177");
		map.put("remark", remark);
		
		template.setData(map);
		
		String template_result = WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wpPublic.getAppid(), wpPublic.getSecret()).getAccess_token(), template);
		System.out.println(template_result);
	}


	/**
	 * 点餐模式给用户发送消息
	 * @param request
	 * @param notifyPay
	 * @param orderInfo
	 * @param wpPublic
	 * @param store_id
	 * @throws Exception 
	 */
	private void diningUserTemplateMessage(HttpServletRequest request,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			WeiXinNotifyPay notifyPay,
			EHaiOrderInfo orderInfo,
			Date date,
			Map<String,Object> map) throws Exception{
		
		WeiXinTemplateMessage template = new WeiXinTemplateMessage();
		template.setTemplate_id("LFdLrMKmvqCgJ3sbIB2cbaZsEChQmFwfnvpn1VrbhOI");//订单支付成功通知
		template.setTouser(notifyPay.getOpenid());
		template.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_user_order_detail!"+SignUtil.setOid(Integer.valueOf(map.get("store_id").toString()), orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), notifyPay.getOpenid(), wp.getToken()));
		template.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateUser = new HashMap<String,Object>();
		
		Map<String,String> first = new HashMap<String,String>();
		first.put("value", "陛下，您在"+map.get("tableNo").toString()+"【餐桌/房间】下的订单已成功交结掌柜，请等待送餐！");
		first.put("color", "#173177");
		mapTemplateUser.put("first", first);
		
		Map<String,String> keyword1 = new HashMap<String,String>();
		keyword1.put("value", store.getStoreName());
		keyword1.put("color", "#173177");
		mapTemplateUser.put("keyword1", keyword1);
		
		Map<String,String> keyword2 = new HashMap<String,String>();
		keyword2.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword2.put("color", "#173177");
		mapTemplateUser.put("keyword2", keyword2);
		
		Map<String,String> keyword3 = new HashMap<String,String>();
		keyword3.put("value", String.format("%.2f", orderInfo.getGoodsAmount().doubleValue() / 100));
		keyword3.put("color", "#173177");
		mapTemplateUser.put("keyword3", keyword3);
		
		Map<String,String> remark = new HashMap<String,String>();
		remark.put("value", "陛下，您点了"+orderInfo.getGoodsDesc()+"，掌柜已吩咐厨房开火了，请稍等！");
		remark.put("color", "#173177");
		mapTemplateUser.put("remark", remark);
		
		
		template.setData(mapTemplateUser);
		
		String template_result = WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret()).getAccess_token(), template);
		System.out.println(template_result);
		
	}
	
	/**
	 * 点餐模式给商户发送消息
	 */
	private String diningStoreTemplateMessage(HttpServletRequest request,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			WeiXinNotifyPay notifyPay,
			EHaiOrderInfo orderInfo,
			Date date,
			Map<String,Object> map) throws Exception{
		
		WeiXinTemplateMessage templateStore = new WeiXinTemplateMessage();
		templateStore.setTemplate_id("Vlmhl4el_dW2Zcq_5cf9KkgRlDx7XI5G_XLuJQ4f2gM");//订单支付成功通知
		templateStore.setTouser(notifyPay.getOpenid());
		templateStore.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_store_order_detail!"+SignUtil.setOid(Integer.valueOf(map.get("store_id").toString()), orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), notifyPay.getOpenid(), wp.getToken()));
		templateStore.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateStore = new HashMap<String,Object>();
        
		Map<String,String> firstStore = new HashMap<String,String>();
		firstStore.put("value", map.get("tableNo").toString() + "餐台/房的客官已下了订单");
		firstStore.put("color", "#173177");
		mapTemplateStore.put("first", firstStore);
		
		Map<String,String> keyword1Store = new HashMap<String,String>();
		keyword1Store.put("value", orderInfo.getOrderSn());
		keyword1Store.put("color", "#173177");
		mapTemplateStore.put("keyword1", keyword1Store);
		
		Map<String,String> keyword2Store = new HashMap<String,String>();
		keyword2Store.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword2Store.put("color", "#173177");
		mapTemplateStore.put("keyword2", keyword2Store);
		
		Map<String,String> keyword3Store = new HashMap<String,String>();
		keyword3Store.put("value", String.format("%.2f", orderInfo.getGoodsAmount().doubleValue() / 100));
		keyword3Store.put("color", "#173177");
		mapTemplateStore.put("keyword3", keyword3Store);
		
		Map<String,String> keyword4Store = new HashMap<String,String>();
		keyword4Store.put("value", "现金付款");
		keyword4Store.put("color", "#173177");
		mapTemplateStore.put("keyword4", keyword4Store);
		
		Map<String,String> remarkStore = new HashMap<String,String>();
		remarkStore.put("value", "客官点了"+orderInfo.getGoodsDesc()+"，掌柜您可以为客官出菜了！");
		remarkStore.put("color", "#173177");
		mapTemplateStore.put("remark", remarkStore);
		
		templateStore.setData(mapTemplateStore);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret()).getAccess_token(), templateStore);
		
		
	}
	
	

}
