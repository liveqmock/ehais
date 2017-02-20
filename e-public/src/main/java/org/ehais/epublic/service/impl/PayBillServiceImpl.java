package org.ehais.epublic.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.mapper.HaiOrderPayExtendsMapper;
import org.ehais.epublic.mapper.HaiOrderPayRecordMapper;
import org.ehais.epublic.model.HaiOrderPayExtendsWithBLOBs;
import org.ehais.epublic.model.HaiOrderPayRecordExample;
import org.ehais.epublic.model.HaiOrderPayRecordWithBLOBs;
import org.ehais.epublic.model.OrderPayModel;
import org.ehais.epublic.model.paybill.PayBillRequest;
import org.ehais.epublic.service.PayBillService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("payBillService")
public class PayBillServiceImpl implements PayBillService{

	@Autowired
	private HaiOrderPayRecordMapper haiOrderPayRecordMapper;
	@Autowired
	private HaiOrderPayExtendsMapper haiOrderPayExtendsMapper;	
	
	
	@Override
	public ReturnObject<PayBillRequest> PayBillRequestForm(HttpServletRequest request,
			PayBillRequest model,
			OrderPayModel orderPayModel)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<PayBillRequest> rm = new ReturnObject<PayBillRequest>();
		rm.setCode(0);
		if(orderPayModel.getStore_id() == null || orderPayModel.getStore_id() == 0){
			rm.setMsg("项目应用编号错误11001");return rm;
		}
		if(orderPayModel.getRealname() == null || orderPayModel.getRealname().equals("")){
			rm.setMsg("用户名称错误11002");return rm;
		}
		if( orderPayModel.getMobile() == null || orderPayModel.getMobile().equals("")){
			rm.setMsg("手机号码错误11003");return rm;
		}
		if( orderPayModel.getEmail() == null || orderPayModel.getEmail().equals("")){
			rm.setMsg("邮箱错误11003");return rm;
		}
		if( orderPayModel.getOrderPayTableId() == null || orderPayModel.getOrderPayTableId() == 0){
			rm.setMsg("支付项目编号错误11003");return rm;
		}
		if( orderPayModel.getOrderPayTableName() == null || orderPayModel.getOrderPayTableName().equals("")){
			rm.setMsg("支付项目名称错误11003");return rm;
		}
		
		Date date = new Date();
		String orderSn = DateUtil.formatDate(date, DateUtil.FORMATSTR_4)+ECommon.nonceInt(6)+"000"+orderPayModel.getStore_id()+"000"+ECommon.nonceInt(4);
		String merchant_url = request.getScheme()+"://"+request.getServerName()+"/PayBill/paybill_merchant_url";
		
		Double amountf = Double.valueOf(orderPayModel.getAmount()) * 100d;
		int amountI = amountf.intValue();
		
		
		
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
		orderPay.setOrderPaySource("paybill");
		orderPay.setOrderSn(orderSn);
		orderPay.setOrderStatus(0);		
		orderPay.setStoreId(orderPayModel.getWxid());
		orderPay.setTableId(orderPayModel.getOrderPayTableId());
		orderPay.setTableName(orderPayModel.getOrderPayTableName());
		orderPay.setUpdateDate(date);
		orderPay.setWxid(orderPayModel.getWxid());
		orderPay.setOrderBody(orderPayModel.getOrderBody());
		
		haiOrderPayRecordMapper.insert(orderPay);		
		
		
		//保存订单扩展记录
		HaiOrderPayExtendsWithBLOBs orderPayExtends = new HaiOrderPayExtendsWithBLOBs();
		orderPayExtends.setAnonymous(orderPayModel.getAnonymous());
		orderPayExtends.setCreatedate(date);
		orderPayExtends.setFace("");//网银支付头像
		orderPayExtends.setGuestbook(orderPayModel.getGuestbook());
		orderPayExtends.setMobile(orderPayModel.getMobile());
		orderPayExtends.setRealname(orderPayModel.getRealname());
		orderPayExtends.setStoreId(orderPayModel.getWxid());
		orderPayExtends.setOrderBody(orderPayModel.getOrderBody());
		
		
		orderPayExtends.setOrderPayId(orderPay.getOrderPayId());		
		haiOrderPayExtendsMapper.insert(orderPayExtends);
		
			
		model.setAmount(orderPayModel.getAmount());
		model.setOrderid(orderSn);
		model.setMerchant_url(merchant_url);
		model.setPname(orderPayModel.getRealname());
		model.setCommodity_info(orderPayModel.getOrderBody());
		model.setPemail(orderPayModel.getEmail());
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

}
