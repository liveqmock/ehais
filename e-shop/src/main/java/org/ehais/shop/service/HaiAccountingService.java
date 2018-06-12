package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiAccounting;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiAccountingService extends CommonService{
	public ReturnObject<HaiAccounting> accounting_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAccounting> accounting_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String accountingName) throws Exception;
	public ReturnObject<HaiAccounting> accounting_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAccounting> accounting_insert_submit(HttpServletRequest request,HaiAccounting model) throws Exception;
	public ReturnObject<HaiAccounting> accounting_update(HttpServletRequest request,Integer accountingId) throws Exception;
	public ReturnObject<HaiAccounting> accounting_update_submit(HttpServletRequest request,HaiAccounting model) throws Exception;
	public ReturnObject<HaiAccounting> accounting_info(HttpServletRequest request,Integer accountingId) throws Exception;
	public ReturnObject<HaiAccounting> accounting_find(HttpServletRequest request,Integer accountingId) throws Exception;
	public ReturnObject<HaiAccounting> accounting_delete(HttpServletRequest request,Integer accountingId) throws Exception;
	


	

}

