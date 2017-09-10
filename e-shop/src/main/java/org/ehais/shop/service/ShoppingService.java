package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.tools.ReturnObject;

public interface ShoppingService extends CommonService{
	
public ReturnObject<Object> checkout(HttpServletRequest request,String recIds,Long user_id) throws Exception;
	
	public ReturnObject<HaiOrderInfo> done(HttpServletRequest request,
			String recIds,
			Integer pay_id,
			Integer ship_id,
			Long address_id,
			Long user_id,
			String message
			) throws Exception;
	
	public ReturnObject<OrderDoneParam> OrderDone(HttpServletRequest request,OrderDoneParam order_done) throws Exception;
	
	
	public ReturnObject<OrderDoneParam> WeixinOrderDone(HttpServletRequest request,OrderDoneParam order_done,String sid) throws Exception;
	

}

