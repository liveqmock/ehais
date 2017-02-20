package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiUsers;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;





public interface HaiUsersService extends CommonService{
	public ReturnObject<EHaiUsers> HaiUsers_list(HttpServletRequest request,Integer store_id) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_insert(HttpServletRequest request,Integer store_id) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_insert_submit(HttpServletRequest request,EHaiUsers model) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_update(HttpServletRequest request,Integer store_id,Long key) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_update_submit(HttpServletRequest request,EHaiUsers model) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_find(HttpServletRequest request,Integer store_id,Long key) throws Exception;
	public ReturnObject<EHaiUsers> HaiUsers_delete(HttpServletRequest request,Integer store_id,Long key) throws Exception;
	
	public ReturnObject<EHaiUsers> modifyPassword(HttpServletRequest request,Integer store_id) throws Exception;
	public ReturnObject<EHaiUsers> modifyPasswordSubmit(HttpServletRequest request,String old_password,String new_password,String confirm_password) throws Exception;
}

