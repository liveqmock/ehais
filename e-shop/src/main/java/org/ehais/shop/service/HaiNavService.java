package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiNav;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiNavService extends CommonService{
	public ReturnObject<HaiNav> nav_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiNav> nav_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String name) throws Exception;
	public ReturnObject<HaiNav> nav_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiNav> nav_insert_submit(HttpServletRequest request,HaiNav model) throws Exception;
	public ReturnObject<HaiNav> nav_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<HaiNav> nav_update_submit(HttpServletRequest request,HaiNav model) throws Exception;
	public ReturnObject<HaiNav> nav_info(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<HaiNav> nav_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<HaiNav> nav_delete(HttpServletRequest request,Integer id) throws Exception;
	


	

}

