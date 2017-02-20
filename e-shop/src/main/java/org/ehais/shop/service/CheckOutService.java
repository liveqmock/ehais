package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.tools.ReturnObject;

public interface CheckOutService extends CommonService{

	public ReturnObject<Object> checkout(HttpServletRequest request,String recIds,Long user_id) throws Exception;
	
	public ReturnObject<HaiOrderInfo> done(HttpServletRequest request,
			String recIds,
			Short pay_id,
			Short ship_id,
			Long address_id,
			Long user_id,
			String message
			) throws Exception;
	
}
