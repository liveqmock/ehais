package org.ehais.epublic.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.ehais.enums.OrderStatusEnum;
import org.ehais.epublic.mapper.HaiOrderPayExtendsMapper;
import org.ehais.epublic.mapper.HaiOrderPayRecordMapper;
import org.ehais.epublic.mapper.alipay.AlipayNotifyUrlMapper;
import org.ehais.epublic.mapper.alipay.AlipayReturnUrlMapper;
import org.ehais.epublic.mapper.alipay.AlipayUnifiedOrderMapper;
import org.ehais.epublic.model.alipay.AlipayNotifyModel;
import org.ehais.epublic.model.alipay.AlipayNotifyUrl;
import org.ehais.epublic.model.alipay.AlipayReturnModel;
import org.ehais.epublic.model.alipay.AlipayReturnUrl;
import org.ehais.epublic.service.AlipayService;
import org.ehais.epublic.service.OrderPayRecordService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("alipayService")
public class AlipayServiceImpl extends PayCommonServiceImpl implements AlipayService {

	@Autowired
	private AlipayReturnUrlMapper alipayReturnUrlMapper;
	@Autowired
	private AlipayNotifyUrlMapper alipayNotifyUrlMapper;
	@Autowired
	private HaiOrderPayRecordMapper haiOrderPayRecordMapper;
	@Autowired
	private HaiOrderPayExtendsMapper haiOrderPayExtendsMapper;	
	@Autowired
	private AlipayUnifiedOrderMapper alipayUnifiedOrderMapper;
	
	@Autowired
	private OrderPayRecordService orderpayrecordService;
	
	@Override
	public ReturnObject<AlipayReturnModel> ReturnUrl(HttpServletRequest request, AlipayReturnModel returnModel)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<AlipayReturnModel> rm = new ReturnObject<AlipayReturnModel>();
		rm.setCode(0);
		AlipayReturnUrl m = new AlipayReturnUrl();
		m.AlipayReturnUrlByModel(returnModel);
		alipayReturnUrlMapper.insertSelective(m);
		
		if(returnModel.getIs_success().equals("T") || returnModel.getTrade_status().equals("TRADE_FINISHED")  || returnModel.getTrade_status().equals("TRADE_SUCCESS") ){
//			HaiOrderPayRecordExample oprExample = new HaiOrderPayRecordExample();
//			HaiOrderPayRecordExample.Criteria criteria = oprExample.createCriteria();
//			criteria.andOrderSnEqualTo(returnModel.getOut_trade_no());
//			List<HaiOrderPayRecord> oprList = haiOrderPayRecordMapper.selectByExample(oprExample);
//			if(oprList == null || oprList.size() == 0){
//				rm.setMsg("不存在此订单信息");return rm;
//			}
//			HaiOrderPayRecord opr = oprList.get(0);
//			if(opr.getOrderStatus() != OrderStatusEnum.init){
//				rm.setMsg("订单信息已处理");return rm;
//			}
//			
//			opr.setOrderStatus(OrderStatusEnum.success);
//			criteria.andOrderSnEqualTo(returnModel.getOut_trade_no()).andOrderStatusEqualTo(0);
//			haiOrderPayRecordMapper.updateByExample(opr, oprExample);
			
			if(!orderpayrecordService.successOrderPay(returnModel.getOut_trade_no(), rm))return rm;
			
			alipayUnifiedOrderMapper.UpdatePayStatue(returnModel.getOut_trade_no(), OrderStatusEnum.success);
			
			rm.setMsg("OK");				
			rm.setCode(1);				
			
			
		}
		
		
		return rm;
	}

	@Override
	public ReturnObject<AlipayNotifyModel> NotifyUrl(HttpServletRequest request, AlipayNotifyModel notifyModel)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<AlipayNotifyModel> rm = new ReturnObject<AlipayNotifyModel>();
		rm.setCode(0);
		AlipayNotifyUrl m = new AlipayNotifyUrl();
		m.AlipayNotifyUrlByModel(notifyModel);
		alipayNotifyUrlMapper.insertSelective(m);
		
		if(notifyModel.getTrade_status().equals("TRADE_FINISHED")  || notifyModel.getTrade_status().equals("TRADE_SUCCESS") ){
			
			if(!orderpayrecordService.successOrderPay(notifyModel.getOut_trade_no(), rm))return rm;
			
			alipayUnifiedOrderMapper.UpdatePayStatue(notifyModel.getOut_trade_no(), OrderStatusEnum.success);
			
			rm.setMsg("OK");				
			rm.setCode(1);				
			
			
		}
		
		return rm;
	}

	
	
}
