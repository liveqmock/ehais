package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiBusiness;
import org.ehais.shop.model.HaiBusinessWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiBusinessService extends CommonService{
	public ReturnObject<HaiBusiness> business_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBusiness> business_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String businessName) throws Exception;
	public ReturnObject<HaiBusinessWithBLOBs> business_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBusinessWithBLOBs> business_insert_submit(HttpServletRequest request,HaiBusinessWithBLOBs model,String classify,String linkManJson) throws Exception;
	public ReturnObject<HaiBusinessWithBLOBs> business_update(HttpServletRequest request,Integer businessId) throws Exception;
	public ReturnObject<HaiBusinessWithBLOBs> business_update_submit(HttpServletRequest request,HaiBusinessWithBLOBs model,String classify,String linkManJson) throws Exception;
	public ReturnObject<HaiBusinessWithBLOBs> business_info(HttpServletRequest request,Integer businessId) throws Exception;
	public ReturnObject<HaiBusinessWithBLOBs> business_find(HttpServletRequest request,Integer businessId) throws Exception;
	public ReturnObject<HaiBusiness> business_delete(HttpServletRequest request,Integer businessId) throws Exception;
	


	

}

