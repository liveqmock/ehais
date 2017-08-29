package org.ehais.weixin.service.order.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderPayExtendsMapper;
import org.ehais.epublic.mapper.HaiOrderPayRecordMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.HaiOrderPayExtendsWithBLOBs;
import org.ehais.epublic.model.HaiOrderPayRecord;
import org.ehais.epublic.model.HaiOrderPayRecordExample;
import org.ehais.epublic.model.HaiOrderPayRecordWithBLOBs;
import org.ehais.epublic.model.OrderPayModel;
import org.ehais.epublic.model.WpPublic;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.SignUtil;
import org.ehais.weixin.mapper.WxNotityPayMapper;
import org.ehais.weixin.mapper.WxUnifiedorderMapper;
import org.ehais.weixin.mapper.WxUnifiedorderResultMapper;
import org.ehais.weixin.model.WeiXinNotifyPay;
import org.ehais.weixin.model.WeiXinUnifiedOrder;
import org.ehais.weixin.model.WeiXinUnifiedOrderResult;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.model.WxNotityPay;
import org.ehais.weixin.model.WxUnifiedorder;
import org.ehais.weixin.model.WxUnifiedorderResult;
import org.ehais.weixin.service.order.OrderPayWeiXinService;
import org.ehais.weixin.service.wx.WeiXinService;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderPayWeiXinService")
public class OrderPayWeiXinServiceImpl implements OrderPayWeiXinService {

	@Autowired
	private WeiXinService weiXinService;
	@Autowired
	private EHaiUsersMapper haiUsersMapper;
	@Autowired
	private HaiOrderPayRecordMapper haiOrderPayRecordMapper;
	@Autowired
	private HaiOrderPayExtendsMapper haiOrderPayExtendsMapper;	
	@Autowired
	private WxUnifiedorderMapper wxUnifiedorderMapper;
	@Autowired 
	private WxUnifiedorderResultMapper wxUnifiedorderResultMapper;
	@Autowired
	private WxNotityPayMapper wxNotityPayMapper;
	
	@Override
	public ReturnObject<WeiXinWCPay> order_pay_submit(HttpServletRequest request, OrderPayModel orderPayModel)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WeiXinWCPay> rm = new ReturnObject<WeiXinWCPay>();
		rm.setCode(0);
		if(orderPayModel.getWxid() == null || orderPayModel.getWxid() == 0){
			rm.setMsg("微信应用编号错误11001");return rm;
		}
		if(orderPayModel.getRealname() == null || orderPayModel.getRealname().equals("")){
			rm.setMsg("姓名不能为空11002");return rm;
		}
//		if( orderPayModel.getMobile() == null || orderPayModel.getMobile().equals("")){
//			rm.setMsg("手机号码错误11003");return rm;
//		}
		if( orderPayModel.getOrderPayTableId() == null || orderPayModel.getOrderPayTableId() == 0){
			rm.setMsg("支付项目编号错误11003");return rm;
		}
		if( orderPayModel.getOrderPayTableName() == null || orderPayModel.getOrderPayTableName().equals("")){
			rm.setMsg("支付项目名称错误11003");return rm;
		}
		
		
		String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		if( openid == null || openid.equals("")){
			rm.setMsg("登录错误11005");return rm;
		}
		
		Long userId = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if( userId == null || userId == 0){
			rm.setMsg("登录错误11006");return rm;
		}
		
		WpPublic wpPublic = weiXinService.getWpPublic(orderPayModel.getWxid());
		if( wpPublic == null){
			rm.setMsg("微信信息设置错误1004");return rm;
		}
		
		//判断用户是否存在
		EHaiUsers users = haiUsersMapper.selectByPrimaryKey(userId);
		if(users == null){
			rm.setMsg("用户信息错误1008");return rm;
		}
		
		Date date = new Date();
		String orderSn = DateUtil.formatDate(date, DateUtil.FORMATSTR_4)+ECommon.nonceInt(6)+"000"+orderPayModel.getWxid()+"000"+users.getUserId();
		String notify_url = request.getScheme()+"://"+request.getServerName()+"/weixin/pay/notify_order_pay";
		
		Double amountf = Double.valueOf(orderPayModel.getAmount()) * 100f;
		int amountI = amountf.intValue();
		
//		判断此订单是否存在订单支付表
		HaiOrderPayRecordExample oprExample = new HaiOrderPayRecordExample();
		oprExample.createCriteria().andOrderSnEqualTo(orderSn);
		int c = haiOrderPayRecordMapper.countByExample(oprExample);
		if(c>0){
			rm.setMsg("订单信息重复1009，请重新提交");return rm;
		}
		//保存订单记录
		HaiOrderPayRecordWithBLOBs orderPay = new HaiOrderPayRecordWithBLOBs();
		orderPay.setAmount(amountI);
		orderPay.setCreateDate(date);
		orderPay.setOrderPaySource("weixin");
		orderPay.setOrderSn(orderSn);
		orderPay.setOrderStatus(0);		
		orderPay.setStoreId(orderPayModel.getWxid());
		orderPay.setTableId(orderPayModel.getOrderPayTableId());
		orderPay.setTableName(orderPayModel.getOrderPayTableName());
		orderPay.setUserId(userId);
		orderPay.setUpdateDate(date);
		orderPay.setWxid(orderPayModel.getWxid());
		orderPay.setOrderBody(orderPayModel.getOrderBody());
		
		
		
		//保存订单扩展记录
		HaiOrderPayExtendsWithBLOBs orderPayExtends = new HaiOrderPayExtendsWithBLOBs();
		orderPayExtends.setAnonymous(orderPayModel.getAnonymous());
		orderPayExtends.setCreatedate(date);
		orderPayExtends.setFace(users.getFaceImage());
		orderPayExtends.setGuestbook(orderPayModel.getGuestbook());
		orderPayExtends.setMobile(orderPayModel.getMobile());
		orderPayExtends.setOpenid(openid);		
		orderPayExtends.setRealname(orderPayModel.getRealname());
		orderPayExtends.setStoreId(orderPayModel.getWxid());
		orderPayExtends.setOrderBody(orderPayModel.getOrderBody());
		
		
		
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(wpPublic.getAppid());
		order.setMch_id(wpPublic.getMchId());
		order.setNonce_str(ECommon.nonceStr(32));
		order.setBody(orderPayModel.getOrderBody());
		order.setOut_trade_no(orderSn);
		order.setTotal_fee(amountI);
//		order.setSpbill_create_ip(IpUtil.getIpAddrV2(request));
		order.setSpbill_create_ip("120.27.35.142");//120.27.35.142
		order.setNotify_url(notify_url);
		order.setTrade_type(orderPayModel.getTrade_type());
		order.setOpenid(openid);
		
		order.setSign(SignUtil.getSign(order.toMap(), wpPublic.getMchSecret()));
		
		
//		String postDataXML = WeiXinUtil.fromUnifiedOrderXml(order);
//        System.out.println(postDataXML);
        
		WeiXinUnifiedOrderResult result = WeiXinUtil.WeiXinUnifiedOrderApi(order);
		
		//插入数据库
		wxUnifiedorderMapper.insert(new WxUnifiedorder(order));
		//插入数据库
		if(result != null){
			wxUnifiedorderResultMapper.insert(new WxUnifiedorderResult(result));
			orderPay.setPrepayId(result.getPrepay_id());
			orderPayExtends.setPrepayId(result.getPrepay_id());
		}else{
			System.out.println("微信返回空错误");
		}
		
//		if(!result.getReturn_code().equals("SUCCESS") || !result.getReturn_msg().equals("OK")){
//			eOrderMapper.insert(eOrder);//插入数据库
//			rm.setMsg(result.getReturn_msg());
//			return rm;
//		}
		
		
		haiOrderPayRecordMapper.insert(orderPay);		
		orderPayExtends.setOrderPayId(orderPay.getOrderPayId());		
		haiOrderPayExtendsMapper.insert(orderPayExtends);
		
		//插入数据库
//		eOrder.setPrepayId(result.getPrepay_id());
//		eOrderMapper.insert(eOrder);
				
		WeiXinWCPay wcPay = new WeiXinWCPay();
		wcPay.setAppId(wpPublic.getAppid());
		wcPay.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		wcPay.setNonceStr(ECommon.nonceStr(32));
		wcPay.setPack_age("prepay_id="+result.getPrepay_id());
		wcPay.setSignType("MD5");
		wcPay.setPaySign(SignUtil.getSign(wcPay.toMap(), wpPublic.getMchSecret()));
		
		rm.setCode(1);
		rm.setModel(wcPay);
		return rm;
		
	}

	@Override
	public ReturnObject<WeiXinNotifyPay> notify_order_pay(HttpServletRequest request, WeiXinNotifyPay notifyPay)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WeiXinNotifyPay> rm = new ReturnObject<WeiXinNotifyPay>();
		rm.setCode(0);
		//先存入库，后续再处理
		wxNotityPayMapper.insert(new WxNotityPay(notifyPay));
		
		try{
			if(notifyPay.getReturn_code().equals("SUCCESS") && notifyPay.getReturn_code().equals("SUCCESS")){
				
				HaiOrderPayRecordExample oprExample = new HaiOrderPayRecordExample();
				HaiOrderPayRecordExample.Criteria criteria = oprExample.createCriteria();
				criteria.andOrderSnEqualTo(notifyPay.getOut_trade_no());
				
				List<HaiOrderPayRecord> oprList = haiOrderPayRecordMapper.selectByExample(oprExample);
				if(oprList == null || oprList.size() == 0){
					rm.setMsg("不存在此订单信息");return rm;
				}
				HaiOrderPayRecord opr = oprList.get(0);
				if(opr.getOrderStatus() != 0){
					rm.setMsg("订单信息不正确");return rm;
				}
				
				opr.setOrderStatus(1);
				criteria.andOrderSnEqualTo(notifyPay.getOut_trade_no()).andOrderStatusEqualTo(0);
				haiOrderPayRecordMapper.updateByExample(opr, oprExample);
				
				wxUnifiedorderMapper.UpdatePayStatue(notifyPay.getOut_trade_no(), 1);
				
				rm.setMsg("OK");				
				rm.setCode(1);				
				notifyPay.setReturn_code("SUCCESS");notifyPay.setReturn_msg(rm.getMsg());
				rm.setModel(notifyPay);
				
			}else{
				rm.setMsg("不存在此订单");
				notifyPay.setReturn_code("FAIL");notifyPay.setReturn_msg(rm.getMsg());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return rm;
	}

}
