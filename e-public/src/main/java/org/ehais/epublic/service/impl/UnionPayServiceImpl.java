package org.ehais.epublic.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.mapper.HaiOrderPayExtendsMapper;
import org.ehais.epublic.mapper.HaiOrderPayRecordMapper;
import org.ehais.epublic.mapper.unionpay.UnionpayRcvResponseMapper;
import org.ehais.epublic.mapper.unionpay.UnionpayUnifiedOrderMapper;
import org.ehais.epublic.model.unionpay.UnionpayRcvResponse;
import org.ehais.epublic.service.OrderPayRecordService;
import org.ehais.epublic.service.UnionPayService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("unionPayService")
public class UnionPayServiceImpl implements UnionPayService {

	@Autowired
	private UnionpayUnifiedOrderMapper unionpayUnifiedOrderMapper;
	@Autowired
	private UnionpayRcvResponseMapper unionpayRcvResponseMapper;
	@Autowired
	private HaiOrderPayRecordMapper haiOrderPayRecordMapper;
	@Autowired
	private HaiOrderPayExtendsMapper haiOrderPayExtendsMapper;	
	
	@Autowired
	private OrderPayRecordService orderpayrecordService;
	

	@Override
	public ReturnObject<Object> BackRcvResponse(HttpServletRequest request,UnionpayRcvResponse unionpayRcvResponse)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		
		unionpayRcvResponseMapper.insertSelective(unionpayRcvResponse);		
		
		if(unionpayRcvResponse.getRespCode().equals("00")){
			if(!orderpayrecordService.successOrderPay(unionpayRcvResponse.getOrderId(), rm))return rm;
		}
		
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<Object> FrontRcvResponse(HttpServletRequest request,UnionpayRcvResponse unionpayRcvResponse)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		unionpayRcvResponseMapper.insertSelective(unionpayRcvResponse);
		if(unionpayRcvResponse.getRespCode().equals("00")){
			if(!orderpayrecordService.successOrderPay(unionpayRcvResponse.getOrderId(), rm))return rm;
		}
		
		rm.setCode(1);
		return rm;
	}

	
}
