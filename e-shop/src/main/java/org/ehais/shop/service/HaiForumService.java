package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiForum;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiForumService extends CommonService{
	public ReturnObject<HaiForum> forum_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiForum> forum_list_json(HttpServletRequest request,EConditionObject condition,String tablename) throws Exception;
	public ReturnObject<HaiForum> forum_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiForum> forum_insert_submit(HttpServletRequest request,HaiForum model) throws Exception;
	public ReturnObject<HaiForum> forum_update(HttpServletRequest request,Long forumId) throws Exception;
	public ReturnObject<HaiForum> forum_update_submit(HttpServletRequest request,HaiForum model) throws Exception;
	public ReturnObject<HaiForum> forum_info(HttpServletRequest request,Long forumId) throws Exception;
	public ReturnObject<HaiForum> forum_find(HttpServletRequest request,Long forumId) throws Exception;
	public ReturnObject<HaiForum> forum_delete(HttpServletRequest request,Long forumId) throws Exception;
	


	

}

