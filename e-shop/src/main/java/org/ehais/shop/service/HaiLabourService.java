package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiLabour;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiLabourService extends CommonService{
	public ReturnObject<HaiLabour> labour_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiLabour> labour_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String labourName) throws Exception;
	public ReturnObject<HaiLabour> labour_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiLabour> labour_insert_submit(HttpServletRequest request,HaiLabour model) throws Exception;
	public ReturnObject<HaiLabour> labour_update(HttpServletRequest request,Integer labourId) throws Exception;
	public ReturnObject<HaiLabour> labour_update_submit(HttpServletRequest request,HaiLabour model) throws Exception;
	public ReturnObject<HaiLabour> labour_info(HttpServletRequest request,Integer labourId) throws Exception;
	public ReturnObject<HaiLabour> labour_find(HttpServletRequest request,Integer labourId) throws Exception;
	public ReturnObject<HaiLabour> labour_delete(HttpServletRequest request,Integer labourId) throws Exception;
	


	

}

