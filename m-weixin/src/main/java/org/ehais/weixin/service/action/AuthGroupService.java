package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpAuthGroup;
import org.ehais.weixin.model.WpAuthGroupWithBLOBs;




public interface AuthGroupService extends CommonService{
	public ReturnObject<WpAuthGroup> authgroup_list(HttpServletRequest request) throws Exception;
	public ReturnObject<WpAuthGroup> authgroup_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_insert_submit(HttpServletRequest request,WpAuthGroupWithBLOBs model) throws Exception;
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_update_submit(HttpServletRequest request,WpAuthGroupWithBLOBs model) throws Exception;
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpAuthGroup> authgroup_delete(HttpServletRequest request,Integer id) throws Exception;
	
	//创建微信分组
	public ReturnObject<WpAuthGroupWithBLOBs> authgroup_wx_create(HttpServletRequest request,Integer id) throws Exception;
	

}

