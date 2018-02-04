package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiPayment;
import org.ehais.tools.ReturnObject;

public interface PaymentService extends CommonService{
	public ReturnObject<HaiPayment> payment_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPayment> payment_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiPayment> payment_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPayment> payment_insert_submit(HttpServletRequest request,HaiPayment model) throws Exception;
	public ReturnObject<HaiPayment> payment_update(HttpServletRequest request,Integer payId) throws Exception;
	public ReturnObject<HaiPayment> payment_update_submit(HttpServletRequest request,HaiPayment model) throws Exception;
	public ReturnObject<HaiPayment> payment_find(HttpServletRequest request,Integer payId) throws Exception;
	public ReturnObject<HaiPayment> payment_delete(HttpServletRequest request,Integer payId) throws Exception;
	
	

}

