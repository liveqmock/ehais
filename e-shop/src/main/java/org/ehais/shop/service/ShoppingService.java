package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.OrderDoneParam;
import org.ehais.tools.ReturnObject;

public interface ShoppingService extends CommonService{
	
	public ReturnObject<OrderDoneParam> OrderDone(HttpServletRequest request,OrderDoneParam order_done) throws Exception;
	

}

