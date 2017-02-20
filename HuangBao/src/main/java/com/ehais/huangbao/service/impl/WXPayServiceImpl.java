package com.ehais.huangbao.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.SignUtil;
import org.ehais.weixin.mapper.EOrderMapper;
import org.ehais.weixin.mapper.WxUnifiedorderMapper;
import org.ehais.weixin.mapper.WxUnifiedorderResultMapper;
import org.ehais.weixin.model.EOrder;
import org.ehais.weixin.model.EOrderExample;
import org.ehais.weixin.model.WeiXinUnifiedOrder;
import org.ehais.weixin.model.WeiXinUnifiedOrderResult;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.model.WxUnifiedorder;
import org.ehais.weixin.model.WxUnifiedorderResult;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.huangbao.mapper.HaiContributionMapper;
import com.ehais.huangbao.model.HaiContribution;
import com.ehais.huangbao.service.WXPayService;

@Service("wxPayService")
public class WXPayServiceImpl extends CommonServiceImpl implements WXPayService {

	@Autowired
	private EOrderMapper eOrderMapper;
	@Autowired
	private WxUnifiedorderMapper wxUnifiedorderMapper;
	@Autowired 
	private WxUnifiedorderResultMapper wxUnifiedorderResultMapper;
	@Autowired
	private HaiContributionMapper haiContributionMapper;

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
			) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WeiXinWCPay> ro = new ReturnObject<WeiXinWCPay>();
		ro.setCode(0);
		Date date = new Date();
		String orderSn = DateUtil.formatDate(date, DateUtil.FORMATSTR_4)+ECommon.nonceInt(6);
		HaiContribution contribution = haiContributionMapper.selectByPrimaryKey(conId);
		String orderBody = contribution.getTitle();//ResourceUtil.getProValue(order_sys_code);
//		System.out.println("orderBody:"+orderBody);
		String notify_url = request.getScheme()+"://"+request.getServerName()+"/weixin/notify_pay";
//		String notify_url = "http://wxdev.ehais.com/weixin/notify_pay";

		Float amountf = Float.valueOf(amount) * 100f;
		int amountI = amountf.intValue();
		
		EOrder eOrder = new EOrder();
		eOrder.setWxid(wxid);
		eOrder.setAmount(amountI);
		eOrder.setRealname(realname);
		eOrder.setMobile(mobile);
		eOrder.setePayStatus(Short.valueOf("0"));
		eOrder.setOpenid(openid);
		eOrder.setOrderSysCode(conId+"");
		eOrder.setOrderBody(orderBody);
		eOrder.setRemark(remark);
		
		eOrder.setCreatedate(date);
		eOrder.setOrderSn(orderSn);
		
		
		
		
		WeiXinUnifiedOrder order = new WeiXinUnifiedOrder();
		order.setAppid(wpPublic.getPayAppid());
		order.setMch_id(wpPublic.getMchId());
		order.setNonce_str(ECommon.nonceStr(32));
		order.setBody(orderBody);
		order.setOut_trade_no(orderSn);
		order.setTotal_fee(amountI);
//		order.setSpbill_create_ip(IpUtil.getIpAddrV2(request));
		order.setSpbill_create_ip("121.201.3.4");//120.27.35.142
		order.setNotify_url(notify_url);
		order.setTrade_type(trade_type);
		order.setOpenid(openid);
		
		order.setSign(SignUtil.getSign(order.toMap(), wpPublic.getMchSecret()));
		
		
		
		String postDataXML = WeiXinUtil.fromUnifiedOrderXml(order);
        System.out.println(postDataXML);
        
		WeiXinUnifiedOrderResult result = WeiXinUtil.WeiXinUnifiedOrderApi(order);
		
		//插入数据库
		wxUnifiedorderMapper.insert(new WxUnifiedorder(order));
		//插入数据库
		wxUnifiedorderResultMapper.insert(new WxUnifiedorderResult(result));
		
		if(!result.getReturn_code().equals("SUCCESS") || !result.getReturn_msg().equals("OK")){
			eOrderMapper.insert(eOrder);//插入数据库
			ro.setMsg(result.getReturn_msg());
			return ro;
		}
		
		//插入数据库
		eOrder.setPrepayId(result.getPrepay_id());
		eOrderMapper.insert(eOrder);
				
		WeiXinWCPay wcPay = new WeiXinWCPay();
		wcPay.setAppId(wpPublic.getPayAppid());
		wcPay.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		wcPay.setNonceStr(ECommon.nonceStr(32));
		wcPay.setPack_age("prepay_id="+result.getPrepay_id());
		wcPay.setSignType("MD5");
		wcPay.setPaySign(SignUtil.getSign(wcPay.toMap(), wpPublic.getMchSecret()));
		
		ro.setCode(1);
		ro.setModel(wcPay);
		return ro;
	}

	public ReturnObject<WeiXinWCPay> UnifiedOrderByOpenId(String openid,WpPublic wpPublic)
			throws Exception {
		// TODO Auto-generated method stub
		
		ReturnObject<WeiXinWCPay> ro = new ReturnObject<WeiXinWCPay>();
		
		WeiXinWCPay wcPay = new WeiXinWCPay();
		wcPay.setAppId(wpPublic.getPayAppid());
		wcPay.setTimeStamp(String.valueOf(System.currentTimeMillis()));
		wcPay.setNonceStr(ECommon.nonceStr(32));
		wcPay.setPack_age("prepay_id=wx20160227120408d57725e7d20014313605");
		wcPay.setSignType("MD5");
		wcPay.setPaySign(SignUtil.getSign(wcPay.toMap(), wpPublic.getMchSecret()));
		
		ro.setCode(1);
		ro.setModel(wcPay);
		return ro;
	}

	public ReturnObject<EOrder> eOrderList(String openid, String orderSysCode,
			Integer ePayStatus, Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> ro = new ReturnObject<EOrder>();
		Integer start = (page - 1) * len;
		List<EOrder> list = eOrderMapper.e_order_list(openid, orderSysCode, ePayStatus, start, len);
		ro.setRows(list);
		return ro;
	}

	public ReturnObject<EOrder> eOrderList(List<String> order_sys_code_list,
			Integer ePayStatus, Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> ro = new ReturnObject<EOrder>();
		Integer start = (page - 1) * len;
		List<EOrder> list = eOrderMapper.e_order_sys_code_list(StringUtils.join(order_sys_code_list.toArray(), ","), ePayStatus, start, len);
		ro.setRows(list);
		return ro;
	}

	public ReturnObject<EOrder> eOrderListWXID(Integer wxid,
			Integer ePayStatus, Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EOrder> rm = new ReturnObject<EOrder>();
		Integer start = (page - 1 ) * len;
		
		EOrderExample example = new EOrderExample();
		EOrderExample.Criteria c = example.createCriteria();
		c.andWxidEqualTo(wxid);
		c.andEPayStatusEqualTo(Short.valueOf("1"));
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("createdate desc");
		List<EOrder> list = eOrderMapper.e_order_list_by_example(example);
		Integer total = eOrderMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
}
