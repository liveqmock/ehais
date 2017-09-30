package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiPayment;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiPaymentService extends CommonService{
	public ReturnObject<HaiPayment> payment_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPayment> payment_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String payName) throws Exception;
	public ReturnObject<HaiPayment> payment_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPayment> payment_insert_submit(HttpServletRequest request,HaiPayment model) throws Exception;
	public ReturnObject<HaiPayment> payment_update(HttpServletRequest request,Integer payId) throws Exception;
	public ReturnObject<HaiPayment> payment_update_submit(HttpServletRequest request,HaiPayment model) throws Exception;
	public ReturnObject<HaiPayment> payment_info(HttpServletRequest request,Integer payId) throws Exception;
	public ReturnObject<HaiPayment> payment_find(HttpServletRequest request,Integer payId) throws Exception;
	public ReturnObject<HaiPayment> payment_delete(HttpServletRequest request,Integer payId) throws Exception;
	

	

}

