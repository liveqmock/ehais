package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface WpPublicService extends CommonService{
	public ReturnObject<WpPublic> public_list(HttpServletRequest request) throws Exception;
	public ReturnObject<WpPublic> public_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String publicName) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_insert_submit(HttpServletRequest request,WpPublicWithBLOBs model) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_update_submit(HttpServletRequest request,WpPublicWithBLOBs model) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_info(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpPublic> public_delete(HttpServletRequest request,Integer id) throws Exception;
	

	
	public ReturnObject<WpPublicWithBLOBs> public_detail(HttpServletRequest request) throws Exception;
	
	

}

