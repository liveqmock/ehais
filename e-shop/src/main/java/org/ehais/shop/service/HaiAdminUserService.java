package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiAdminUserService extends CommonService{
	public ReturnObject<EHaiAdminUser> adminuser_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiAdminUser> adminuser_list_json(HttpServletRequest request,EConditionObject condition,Long keySubId,String adminName) throws Exception;
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_insert_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model,String roleId) throws Exception;
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_update(HttpServletRequest request,Long adminId) throws Exception;
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_update_submit(HttpServletRequest request,EHaiAdminUserWithBLOBs model,String roleId) throws Exception;
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_info(HttpServletRequest request,Long adminId) throws Exception;
	public ReturnObject<EHaiAdminUserWithBLOBs> adminuser_find(HttpServletRequest request,Long adminId) throws Exception;
	public ReturnObject<EHaiAdminUser> adminuser_delete(HttpServletRequest request,Long adminId) throws Exception;
	


	

}

