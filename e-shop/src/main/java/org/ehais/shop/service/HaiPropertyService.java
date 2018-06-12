package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiProperty;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiPropertyService extends CommonService{
	public ReturnObject<HaiProperty> property_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiProperty> property_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String propertyName) throws Exception;
	public ReturnObject<HaiProperty> property_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiProperty> property_insert_submit(HttpServletRequest request,HaiProperty model) throws Exception;
	public ReturnObject<HaiProperty> property_update(HttpServletRequest request,Integer propertyId) throws Exception;
	public ReturnObject<HaiProperty> property_update_submit(HttpServletRequest request,HaiProperty model) throws Exception;
	public ReturnObject<HaiProperty> property_info(HttpServletRequest request,Integer propertyId) throws Exception;
	public ReturnObject<HaiProperty> property_find(HttpServletRequest request,Integer propertyId) throws Exception;
	public ReturnObject<HaiProperty> property_delete(HttpServletRequest request,Integer propertyId) throws Exception;
	


	

}

