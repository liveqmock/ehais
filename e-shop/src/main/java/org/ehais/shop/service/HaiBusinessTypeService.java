package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiBusinessType;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiBusinessTypeService extends CommonService{
	public ReturnObject<HaiBusinessType> businesstype_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String businessTypeName) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_insert_submit(HttpServletRequest request,HaiBusinessType model) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_update(HttpServletRequest request,Integer businessTypeId) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_update_submit(HttpServletRequest request,HaiBusinessType model) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_info(HttpServletRequest request,Integer businessTypeId) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_find(HttpServletRequest request,Integer businessTypeId) throws Exception;
	public ReturnObject<HaiBusinessType> businesstype_delete(HttpServletRequest request,Integer businessTypeId) throws Exception;
	


	

}

