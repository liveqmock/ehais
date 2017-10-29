package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.ThinkRole;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface ThinkRoleService extends CommonService{
	public ReturnObject<ThinkRole> role_list(HttpServletRequest request) throws Exception;
	public ReturnObject<ThinkRole> role_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String name) throws Exception;
	public ReturnObject<ThinkRole> role_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<ThinkRole> role_insert_submit(HttpServletRequest request,ThinkRole model) throws Exception;
	public ReturnObject<ThinkRole> role_update(HttpServletRequest request,Integer roleId) throws Exception;
	public ReturnObject<ThinkRole> role_update_submit(HttpServletRequest request,ThinkRole model) throws Exception;
	public ReturnObject<ThinkRole> role_info(HttpServletRequest request,Integer roleId) throws Exception;
	public ReturnObject<ThinkRole> role_find(HttpServletRequest request,Integer roleId) throws Exception;
	public ReturnObject<ThinkRole> role_delete(HttpServletRequest request,Integer roleId) throws Exception;
	


	

}

