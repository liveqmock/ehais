package com.ehais.tracking.service.tracking;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.HaiAdminUser;


public interface HaiAdminUserService extends CommonService{
	public ReturnObject<HaiAdminUser> admin_user_list() throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_list_json(Integer page,Integer len) throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_insert() throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_insert_submit(HaiAdminUser model) throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_update(Integer key) throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_update_submit(HaiAdminUser model) throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_find(Integer key) throws Exception;
	public ReturnObject<HaiAdminUser> admin_user_delete(Integer key) throws Exception;
	
	public ReturnObject<HaiAdminUser> admin_login(String username,String password) throws Exception;
	
	

}

