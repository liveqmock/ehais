package org.ehais.epublic.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EWithdrawDepositStatusEnum;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.mapper.HaiWithdrawDepositMapper;
import org.ehais.epublic.mapper.weixin.WxNotifyPayMapper;
import org.ehais.epublic.mapper.weixin.WxTransfersMapper;
import org.ehais.epublic.mapper.weixin.WxTransfersResultMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderResultMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.HaiWithdrawDeposit;
import org.ehais.epublic.model.HaiWithdrawDepositExample;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.model.weixin.WxNotifyPay;
import org.ehais.epublic.model.weixin.WxTransfers;
import org.ehais.epublic.model.weixin.WxTransfersExample;
import org.ehais.epublic.model.weixin.WxTransfersResult;
import org.ehais.epublic.model.weixin.WxUnifiedorder;
import org.ehais.epublic.model.weixin.WxUnifiedorderResult;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.EncryptUtils;
import org.ehais.util.IpUtil;
import org.ehais.util.SignUtil;
import org.ehais.util.XStreamUtil;
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
//	@Autowired
//	private HaiOrderInfoMapper eHaiOrderInfoMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiStoreMapper haiStoreMapper;
	@Autowired
	private HaiWithdrawDepositMapper haiWithdrawDepositMapper;
	@Autowired
	private WxTransfersMapper wxTransfersMapper;
	@Autowired
	private WxTransfersResultMapper wxTransfersResultMapper;
	
		
	/**
	 * 微信支付需要的js参数通过此方法返回
	 */
	@Override
	public WeiXinWCPay WeiXinPayApi(HttpServletRequest request, String cid,
			String openid,
			String orderSn,
			Integer amount,
			String body,
			String table_name,
			Long table_id,
			String classify) throws Exception {
		// TODO Auto-generated method stub
		Integer store_id = SignUtil.getUriStoreId(cid);
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		Map<String,Object> map = null ;
		if(classify.equals(EOrderClassifyEnum.dining)){
			map = SignUtil.getDiningId(cid,wpPublic.getToken());
		}else if(classify.equals(EOrderClassifyEnum.shop)){
			map = SignUtil.getCid(cid, wpPublic.getToken());
		}
		
		System.out.println("用户支付IP："+IpUtil.getIpAddrV2(request));
		
//		Date date = new Date();
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(wpPublic.getAppid());
		order.setMch_id(wpPublic.getMchId());
		order.setNonce_str(ECommon.nonceStr(32));
		order.setBody(body);
		order.setOut_trade_no(orderSn);
		order.setTotal_fee(amount);		
//		order.setSpbill_create_ip(IpUtil.getIpAddrV2(request));
		order.setSpbill_create_ip("120.27.35.142");
		order.setNotify_url(request.getScheme()+"://"+request.getServerName()+"/weixin/pay/notify_url!"+cid+"&"+classify);
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
	
	
	public WxUnifiedorder toWxUnifiedOrder(WeiXinUnifiedOrder model) {
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
	
	
	public WxUnifiedorderResult toWxUnifiedorderResult(WeiXinUnifiedOrderResult model) {
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


	/**
	 * 微信支付回调函数
	 */
	@Override
	public ReturnObject<WeiXinNotifyPay> WeiXinNotifyPay(HttpServletRequest request,
			WeiXinNotifyPay notifyPay,String cid,
			String classify) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WeiXinNotifyPay> rm = new ReturnObject<WeiXinNotifyPay>();
		rm.setCode(0);
		//进行签名验证
		Integer store_id = SignUtil.getUriStoreId(cid);
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		Map<String,Object> map = null ;
		if(classify.equals(EOrderClassifyEnum.dining)){
			map = SignUtil.getDiningId(cid,wpPublic.getToken());
		}else if(classify.equals(EOrderClassifyEnum.shop)){
			map = SignUtil.getCid(cid, wpPublic.getToken());
		}
		
		String sign = SignUtil.getSign(Bean2Utils.toSignMap(notifyPay), wpPublic.getMchSecret());
		if(!sign.equals(notifyPay.getSign())){
			rm.setMsg("签名出错");return rm;
		}
		wxNotifyPayMapper.insert(this.toWxNotityPay(notifyPay));
		if(notifyPay.getReturn_code().equals("SUCCESS") && notifyPay.getReturn_code().equals("SUCCESS")){
			//查看订单是否正确
			List<HaiOrderInfoWithBLOBs> listOrder = haiOrderInfoMapper.listOrderInfoSn(store_id, notifyPay.getOut_trade_no());
			if(listOrder == null || listOrder.size() == 0){
				rm.setMsg("不存在此订单");return rm;
			}
			HaiOrderInfoWithBLOBs orderInfo = listOrder.get(0);
			if(orderInfo.getPayStatus() != EOrderStatusEnum.init){
				rm.setMsg("订单状态异常");return rm;
			}
			
			//获取流水号
			int daySerialCount = haiOrderInfoMapper.daySerialNumber(store_id);
			
			//更新订单的状态
			haiOrderInfoMapper.updateOrderPayStatus(
					store_id, 
					notifyPay.getOut_trade_no(), 
					EOrderStatusEnum.success,
					EPayStatusEnum.success,
					System.currentTimeMillis(),
					0,"微信支付",daySerialCount+1);
			
			//更新统一下单的订单信息
			wxUnifiedorderMapper.UpdatePayStatue(store_id, notifyPay.getOut_trade_no(), EOrderStatusEnum.success);
			
			//更新优惠券的使用数量
			if(orderInfo.getCouponsId() != null && orderInfo.getCouponsId() > 0){
				haiOrderInfoMapper.updateCouponsUseCount(store_id, orderInfo.getCouponsId());
			}
			
			HaiStoreStatistics storeStatistics = haiStoreStatisticsMapper.selectByPrimaryKey(store_id);
			if(storeStatistics == null){
				storeStatistics = new HaiStoreStatistics();
				storeStatistics.setStoreId(store_id);
			}
			storeStatistics.setWeixinAmount((storeStatistics.getWeixinAmount() == null ? 0 : storeStatistics.getWeixinAmount()) + notifyPay.getTotal_fee());
			storeStatistics.setWeixinQuantity((storeStatistics.getWeixinQuantity() == null ? 0 : storeStatistics.getWeixinQuantity()) + 1);
			haiStoreStatisticsMapper.updateByPrimaryKey(storeStatistics);
			
			if(orderInfo.getClassify().equals(EOrderClassifyEnum.shop)){
				//商城订单通知用户
				this.shopUserTemplateMessage(request, notifyPay, orderInfo, wpPublic, store_id);
				//商城订单通知商户
				
			}else if(orderInfo.getClassify().equals(EOrderClassifyEnum.dining)){
				EHaiStore store = eStoreService.getEStore(store_id);
				Date date = new Date();
				//通知餐饮用户
				this.diningUserTemplateMessage(request,
						wpPublic,
						store,
						notifyPay,
						orderInfo,
						date,
						map);
				
				//通知餐饮商户
				this.diningStoreTemplateMessage(request,
						wpPublic,
						store,
						notifyPay,
						orderInfo,
						date,
						map);
				
			}
			
			
			rm.setCode(1);
			rm.setMsg("SUCCESS");
			
		}
		return rm;
	}

	
	private WxNotifyPay toWxNotityPay(WeiXinNotifyPay model) {
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
			HaiOrderInfoWithBLOBs orderInfo,
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
			HaiOrderInfoWithBLOBs orderInfo,
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
			HaiOrderInfoWithBLOBs orderInfo,
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


	@Override
	public ReturnObject<Object> transfers(HttpServletRequest request, 
			String money, 
			String classify,
			String prefix_order_transfers,
			String weixin_cert_p12) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		System.out.println("money:"+money);
		//验证金额
		if(!ECommon.isMoney(money)) {
			rm.setMsg("金额类型不正确");
			return rm;
		}
		//判断身份合法性
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		
		if(user_id == null || user_id == 0 || user_id.longValue() == 0) {
			rm.setMsg("请重新登录");
			return rm;
		}
		EHaiUsers users = eHaiUsersMapper.selectByPrimaryKey(user_id);
		if(users == null ) {
			rm.setMsg("不存在此用户");
			return rm;
		}
		if(users.getUserType() == null) {
			rm.setMsg("用户无权限提现");
			return rm;
		}
		
		if(users.getStoreId() == null || users.getStoreId() == 0 || users.getStoreId().intValue() == 0) {
			rm.setMsg("非法商家，无权限提现");
			return rm;
		}
		//判断是否存在多个同商家类型，要查证
		Integer store_id = users.getStoreId();
		EHaiUsersExample ue = new EHaiUsersExample();
		ue.createCriteria().andStoreIdEqualTo(store_id).andUserTypeGreaterThan(Short.valueOf("0"));
		Long count_user = eHaiUsersMapper.countByExample(ue);
		System.out.println("count_user:"+count_user);
		if(count_user > 1) {
			rm.setMsg("商家数据有误，请微信公众号留言给管理员");
//			this.transfersErrorManagerTemplateMessage(request,users,null,money, store_id+"的商家信息存在多个管理员");
			return rm;
		}
		
		EHaiStore eStore = eStoreService.getEStore(store_id);
		if(eStore == null) {
			rm.setMsg("商家不存在001，请微信公众号留言给管理员");
			return rm;
		}
		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store_id);
		if(wpPublic == null) {
			rm.setMsg("商家配置不正确，请微信公众号留言给管理员");
			return rm;
		}
		EHaiStore store = haiStoreMapper.selectByPrimaryKey(store_id);
		if(store == null) {
			rm.setMsg("商家不存在，请微信公众号留言给管理员");
			return rm;
		}
		if(store.getBalance() == null || store.getBalance() == 0 || store.getBalance().intValue() == 0) {
			rm.setMsg("余额不足");
			return rm;
		}
		if(store.getOwnerName() == null || store.getOwnerName().equals("")) {
			rm.setMsg("用户未在平台认证，请微信公众号留言给管理员");
			return rm;
		}
		Float fMoney = Float.valueOf(money);
		Integer iMoney = Float.valueOf(fMoney.floatValue() * 100).intValue();
		if(iMoney.intValue() > store.getBalance().intValue()) {
			rm.setMsg("提现金额大于余额");
			return rm;
		}
		//判断是否存在未提现成功的操作
		HaiWithdrawDepositExample wde = new HaiWithdrawDepositExample();
		wde.createCriteria().andUserIdEqualTo(user_id).andStatusEqualTo(EWithdrawDepositStatusEnum.INIT);
		wde.or().andUserIdEqualTo(user_id).andStatusEqualTo(EWithdrawDepositStatusEnum.CONTINUED);
		Long countic = haiWithdrawDepositMapper.countByExample(wde);
		if(countic > 0) {
			rm.setMsg("提现正在进行中，不能重复提现");
			return rm;
		}
		
		Date date = new Date();
		//插入提现表
		HaiWithdrawDeposit wd = new HaiWithdrawDeposit();
		wd.setUserId(user_id);
		wd.setStoreId(store_id);
		wd.setOpenid(openid);
		wd.setRealname(users.getRealname());
		wd.setCreateDate(date);
		wd.setAmount(iMoney);
		wd.setWithdrawDesc("提现");
		wd.setClassify(classify);
		wd.setStatus(EWithdrawDepositStatusEnum.INIT);
		
		int i = haiWithdrawDepositMapper.insert(wd);
		if(i != 1) {
			rm.setMsg("提现操作有误，请微信留言给管理员");
			return rm;
		}
		
		
		WxTransfers wxt = new WxTransfers();
		
		wxt.setMchAppid(wpPublic.getAppid());
		wxt.setMchid(wpPublic.getMchId());
		wxt.setDeviceInfo(null);
		wxt.setNonceStr(ECommon.nonceStr(32).toUpperCase());
		String orderSn = prefix_order_transfers + DateUtil.formatDate(date, DateUtil.FORMATSTR_4) + users.getUserId() + ECommon.nonceInt(4);
		wxt.setPartnerTradeNo(orderSn);
		wxt.setOpenid(openid);
		wxt.setCheckName("FORCE_CHECK");
		wxt.setReUserName(store.getOwnerName());
		wxt.setAmount(iMoney);
		wxt.setDesc(store.getStoreName()+"提现");
		String ip = IpUtil.getIpAddrV2(request);
		if(ip.indexOf(",") > 0) {
			String[] ips = ip.split(",");
			ip = ips[0];
		}
		
		wxt.setSpbillCreateIp(ip);
		wxt.setWdId(wd.getWdId());
		wxt.setCreateDate(date);
		String sign = this.signTransfers(wxt, wpPublic);
		wxt.setSign(sign);
		
		
		i = wxTransfersMapper.insert(wxt);
		
		String content = XStreamUtil.toXml(wxt);
		
		System.out.println(content);
		String result = WeiXinUtil.transfers(content,weixin_cert_p12,wpPublic.getMchId());
		System.out.println("result:"+result);
		WxTransfersResult wxtr = XStreamUtil.toBean(result, WxTransfersResult.class);
		wxtr.setWdId(wd.getWdId());
		wxtr.setTransfersId(wxt.getTransfersId());
		i = wxTransfersResultMapper.insert(wxtr);
		
		if(wxtr.getReturnCode().toUpperCase().equals("FAIL")) {
			wd.setStatus(EWithdrawDepositStatusEnum.INVALID);
			wd.setIsSuccess(false);
			wd.setReturnMessage("return:"+wxtr.getReturnMsg());
			wd.setSuccessDate(date);
			haiWithdrawDepositMapper.updateByPrimaryKey(wd);
			rm.setMsg("提现返回失败，请微信留言给管理员");
			this.transfersErrorManagerTemplateMessage(users,store,money,wpPublic, "客户提现失败，客户编号："+store_id+"，提现金额："+money+"；return:"+wxtr.getReturnMsg());
			return rm;
		}
		
		if(wxtr.getResultCode().toUpperCase().equals("FAIL")) {
			if(wxtr.getErrCode().toUpperCase().equals("NOTENOUGH")) {
				wd.setStatus(EWithdrawDepositStatusEnum.CONTINUED);
				rm.setMsg("提现过程处理中，请耐心等待2~4个小时");
				
				//更新余额，余额不足，同样先扣除余额
//				store.setBalance(store.getBalance() - iMoney);
//				haiStoreMapper.updateByPrimaryKey(store);
			}else {
				wd.setStatus(EWithdrawDepositStatusEnum.INVALID);
				rm.setMsg("提现失败提示："+wxtr.getErrCodeDes());
			}			
			wd.setIsSuccess(false);
			wd.setReturnMessage("result:"+wxtr.getErrCode()+"|"+wxtr.getErrCodeDes());
			wd.setSuccessDate(date);
			haiWithdrawDepositMapper.updateByPrimaryKey(wd);
			this.transfersErrorManagerTemplateMessage(users,store,money,wpPublic, "客户提现失败，客户编号："+store_id+"，提现金额："+money+"；return:"+wxtr.getErrCode()+"|"+wxtr.getErrCodeDes());
			
			return rm;
		}
		
		
		wd.setStatus(EWithdrawDepositStatusEnum.SUCCESS);
		wd.setIsSuccess(true);
		wd.setReturnMessage("result:"+wxtr.getPartnerTradeNo()+"|"+wxtr.getPaymentNo()+"|"+wxtr.getPaymentTime());
		wd.setSuccessDate(date);
		haiWithdrawDepositMapper.updateByPrimaryKey(wd);
		
		//更新余额
		store.setBalance(store.getBalance() - iMoney);
		haiStoreMapper.updateByPrimaryKey(store);
		
		
		rm.setCode(1);
		rm.setMsg("提现过程处理中，请耐心等待2~4个小时");
		return rm ;
	}

	
	@Override
	public ReturnObject<Object> transfersTask(String weixin_cert_p12) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		HaiWithdrawDepositExample wde = new HaiWithdrawDepositExample();
		wde.createCriteria().andStatusEqualTo(EWithdrawDepositStatusEnum.CONTINUED);
		List<HaiWithdrawDeposit> list = haiWithdrawDepositMapper.selectByExample(wde);
		if(list == null || list.size() == 0)return rm;
		List<Integer> wdids = new ArrayList<Integer>();
		List<Long> userIds = new ArrayList<Long>();
		List<Integer> storeIds = new ArrayList<Integer>();
		for (HaiWithdrawDeposit haiWithdrawDeposit : list) {
			wdids.add(haiWithdrawDeposit.getWdId());
			userIds.add(haiWithdrawDeposit.getUserId());
			storeIds.add(haiWithdrawDeposit.getStoreId());
		}
		if(wdids.size() == 0)return rm;
		
		
		EHaiUsersExample exampleUsers = new EHaiUsersExample();
		exampleUsers.createCriteria().andUserIdIn(userIds);
		List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(exampleUsers);
		
		
		EHaiStoreExample exampleStore = new EHaiStoreExample();
		exampleStore.createCriteria().andStoreIdIn(storeIds);
		List<EHaiStore> listStore = haiStoreMapper.selectByExample(exampleStore);
		
		
		WxTransfersExample wte = new WxTransfersExample();
		wte.createCriteria().andWdIdIn(wdids);
		List<WxTransfers> listWxTransfers = wxTransfersMapper.selectByExample(wte);
		for (WxTransfers wxTransfers : listWxTransfers) {
			System.out.println("提现计划任务："+wxTransfers.getTransfersId());
			String content = XStreamUtil.toXml(wxTransfers);
			
			String result = WeiXinUtil.transfers(content,weixin_cert_p12,wxTransfers.getMchid());
			
			WxTransfersResult wxtr = XStreamUtil.toBean(result, WxTransfersResult.class);
			wxtr.setWdId(wxTransfers.getWdId());
			wxTransfersResultMapper.insert(wxtr);
			
			
			if(wxtr.getReturnCode() != null && wxtr.getResultCode()!=null && wxtr.getReturnCode().toUpperCase().equals("SUCCESS") && wxtr.getResultCode().toUpperCase().equals("SUCCESS")) {
				for (HaiWithdrawDeposit wd : list) {
					if(wxTransfers.getWdId().intValue() == wd.getWdId().intValue()) {
						Date date = new Date();
						wd.setStatus(EWithdrawDepositStatusEnum.SUCCESS);
						wd.setIsSuccess(true);
						wd.setReturnMessage("result:"+wxtr.getErrCode()+"|"+wxtr.getErrCodeDes());
						wd.setSuccessDate(date);
						haiWithdrawDepositMapper.updateByPrimaryKey(wd);
						
						for (EHaiUsers users : listUsers) {
							if(wd.getUserId().longValue() == users.getUserId().longValue()) {
								for (EHaiStore store : listStore) {
									if(wd.getStoreId().intValue() == store.getStoreId().intValue()) {
										//更新余额
										store.setBalance(store.getBalance() - wd.getAmount());
										haiStoreMapper.updateByPrimaryKey(store);
										break;
									}
								}
								break;
							}
						}
						
						
					}
				}
				
			}else {
				for (HaiWithdrawDeposit wd : list) {
					
					for (EHaiUsers users : listUsers) {
						if(wd.getUserId().longValue() == users.getUserId().longValue()) {
							for (EHaiStore store : listStore) {
								if(wd.getStoreId().intValue() == store.getStoreId().intValue()) {
									WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(store.getStoreId());
									if(wxtr.getReturnCode().toUpperCase().equals("FAIL")) {
										this.transfersErrorManagerTemplateMessage(users,store,wd.getAmount().toString(),wpPublic, "计划任务：客户提现失败，客户编号："+store.getStoreId()+"，提现金额："+wd.getAmount()+"；return:"+wxtr.getReturnMsg());
									}
									
									if(wxtr.getResultCode().toUpperCase().equals("FAIL")) {
										this.transfersErrorManagerTemplateMessage(users,store,wd.getAmount().toString(),wpPublic, "计划任务：客户提现失败，客户编号："+store.getStoreId()+"，提现金额："+wd.getAmount()+"；return:"+wxtr.getErrCode()+"|"+wxtr.getErrCodeDes());
									}
									
									break;
								}
							}
						}
						break;
					}
					
					
				}
				
			}
			
		}
		
		return rm;
	}
	
	
	/**
	 * 提现提示信息，待完善
	 * @param request
	 * @param wp
	 * @param store
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	private String transfersErrorManagerTemplateMessage(
			EHaiUsers users,
			EHaiStore store,
			String money,
			WpPublicWithBLOBs wpPublic,
			String msg) throws Exception{
		WeiXinTemplateMessage templateStore = new WeiXinTemplateMessage();
		templateStore.setTemplate_id("_LGbFNlNyA7z9_RnwUCfY4jqwSvcS2XGICvKid9cgZ8");//订单支付成功通知
		
		templateStore.setTouser("oiGBot1K1vYJA2DFv2B-0W2xL9O0");//错误通知指定Tyler接收处理
//		templateStore.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_store_order_detail!"+SignUtil.setOid(Integer.valueOf(map.get("store_id").toString()), orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), notifyPay.getOpenid(), wp.getToken()));
		templateStore.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateStore = new HashMap<String,Object>();
        
		Map<String,String> firstStore = new HashMap<String,String>();
		firstStore.put("value", "提现通知");
		firstStore.put("color", "#173177");
		mapTemplateStore.put("first", firstStore);
		
		Map<String,String> keyword1Store = new HashMap<String,String>();
		keyword1Store.put("value", users.getRealname());
		keyword1Store.put("color", "#173177");
		mapTemplateStore.put("keyword1", keyword1Store);
		
		Map<String,String> keyword2Store = new HashMap<String,String>();
		keyword2Store.put("value", (store == null) ? (users.getUserName() + users.getRealname()) :  store.getStoreName());
		keyword2Store.put("color", "#173177");
		mapTemplateStore.put("keyword2", keyword2Store);
		
		Date date = new Date();
		Map<String,String> keyword3Store = new HashMap<String,String>();
		keyword3Store.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_1));
		keyword3Store.put("color", "#173177");
		mapTemplateStore.put("keyword3", keyword3Store);
		
	
		Map<String,String> remarkStore = new HashMap<String,String>();
		remarkStore.put("value", msg);
		remarkStore.put("color", "#173177");
		mapTemplateStore.put("remark", remarkStore);
		
		templateStore.setData(mapTemplateStore);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(Integer.valueOf(users.getStoreId()), wpPublic.getAppid(), wpPublic.getSecret()).getAccess_token(), templateStore);
		
		
	}
	
	
	
	private String signTransfers(WxTransfers transfers,WpPublicWithBLOBs wpPublic) throws Exception {
		StringBuilder sb = new StringBuilder();
//		spbillCreateIp=127.0.0.1&key=8cc41d74df2d897ad9085f32388f0a5e
		sb.append("amount="+transfers.getAmount()+"&");
		sb.append("check_name="+transfers.getCheckName()+"&");
		sb.append("desc="+transfers.getDesc()+"&");
		sb.append("mch_appid="+transfers.getMchAppid()+"&");
		sb.append("mchid="+transfers.getMchid()+"&");
		sb.append("nonce_str="+transfers.getNonceStr()+"&");
		sb.append("openid="+transfers.getOpenid()+"&");
		sb.append("partner_trade_no="+transfers.getPartnerTradeNo()+"&");
		sb.append("re_user_name="+transfers.getReUserName()+"&");
		sb.append("spbill_create_ip="+transfers.getSpbillCreateIp()+"&");
		sb.append("key="+wpPublic.getMchSecret());
		System.out.println(sb.toString());
		return EncryptUtils.md5(sb.toString()).toUpperCase();
	}



	

}
