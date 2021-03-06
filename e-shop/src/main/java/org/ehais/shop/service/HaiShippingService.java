package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiShippingService extends CommonService{
	public ReturnObject<HaiShipping> shipping_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiShipping> shipping_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String shippingName) throws Exception;
	public ReturnObject<HaiShippingWithBLOBs> shipping_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiShippingWithBLOBs> shipping_insert_submit(HttpServletRequest request,HaiShippingWithBLOBs model) throws Exception;
	public ReturnObject<HaiShippingWithBLOBs> shipping_update(HttpServletRequest request,Integer shippingId) throws Exception;
	public ReturnObject<HaiShippingWithBLOBs> shipping_update_submit(HttpServletRequest request,HaiShippingWithBLOBs model) throws Exception;
	public ReturnObject<HaiShippingWithBLOBs> shipping_info(HttpServletRequest request,Integer shippingId) throws Exception;
	public ReturnObject<HaiShippingWithBLOBs> shipping_find(HttpServletRequest request,Integer shippingId) throws Exception;
	public ReturnObject<HaiShipping> shipping_delete(HttpServletRequest request,Integer shippingId) throws Exception;
	

	

}

