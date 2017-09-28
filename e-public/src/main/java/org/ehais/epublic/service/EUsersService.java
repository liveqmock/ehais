package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiUsers;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;



public interface EUsersService extends CommonService{
	public ReturnObject<EHaiUsers> users_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiUsers> users_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiUsers> users_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiUsers> users_insert_submit(HttpServletRequest request,EHaiUsers model) throws Exception;
	public ReturnObject<EHaiUsers> users_update(HttpServletRequest request,Long user_id) throws Exception;
	public ReturnObject<EHaiUsers> users_update_submit(HttpServletRequest request,EHaiUsers model) throws Exception;
	public ReturnObject<EHaiUsers> users_find(HttpServletRequest request,Long user_id) throws Exception;
	public ReturnObject<EHaiUsers> users_delete(HttpServletRequest request,Long user_id) throws Exception;
	
	public ReturnObject<EHaiUsers> login(HttpServletRequest request,String username,String password,String source) throws Exception;
	
	public ReturnObject<EHaiUsers> register(HttpServletRequest request,String username,String password,String confirmPassword,String source) throws Exception;
	public ReturnObject<EHaiUsers> logout(HttpServletRequest request,String source) throws Exception;
	
	public ReturnObject<EHaiUsers> users_info_edit(HttpServletRequest request,EHaiUsers model,Long user_id,String token) throws Exception;
	
	public ReturnObject<EHaiUsers> wx_user_save(HttpServletRequest request,
			Integer wxid,//store_id
			String email,
			String userName, 
			String password,
			String nickname,
			String realname,
			Integer sex,
			Integer subscribe,
			String openid,
			String city,
			String country,
			String province,
			String language,
			String headimgurl,
			Long subscribe_time,
			String unionid,
			String remark,
			Integer groupid) throws Exception;
	
	
	
	public ReturnObject<EHaiUsers> wx_user_save(HttpServletRequest request,Long userId) throws Exception;
	public ReturnObject<EHaiUsers> wx_user_save(HttpServletRequest request,String openId) throws Exception;
	public ReturnObject<EHaiUsers> wx_user_save(HttpServletRequest request,EHaiUsers user) throws Exception;

	
	public ReturnObject<EHaiUsers> fans_list(HttpServletRequest request,Long userId,EConditionObject condition,String nickname) throws Exception;
	
	
}

