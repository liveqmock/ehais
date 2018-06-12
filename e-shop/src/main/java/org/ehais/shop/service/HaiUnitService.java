package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiUnit;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiUnitService extends CommonService{
	public ReturnObject<HaiUnit> unit_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiUnit> unit_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String unitName) throws Exception;
	public ReturnObject<HaiUnit> unit_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiUnit> unit_insert_submit(HttpServletRequest request,HaiUnit model) throws Exception;
	public ReturnObject<HaiUnit> unit_update(HttpServletRequest request,Integer unitId) throws Exception;
	public ReturnObject<HaiUnit> unit_update_submit(HttpServletRequest request,HaiUnit model) throws Exception;
	public ReturnObject<HaiUnit> unit_info(HttpServletRequest request,Integer unitId) throws Exception;
	public ReturnObject<HaiUnit> unit_find(HttpServletRequest request,Integer unitId) throws Exception;
	public ReturnObject<HaiUnit> unit_delete(HttpServletRequest request,Integer unitId) throws Exception;
	


	

}

