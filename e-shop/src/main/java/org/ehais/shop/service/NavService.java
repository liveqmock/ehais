package org.ehais.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiNav;
import org.ehais.tools.ReturnObject;

public interface NavService extends CommonService{
	public ReturnObject<HaiNav> nav_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiNav> nav_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiNav> nav_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiNav> nav_insert_submit(HttpServletRequest request,HaiNav model) throws Exception;
	public ReturnObject<HaiNav> nav_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<HaiNav> nav_update_submit(HttpServletRequest request,HaiNav model) throws Exception;
	public ReturnObject<HaiNav> nav_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<HaiNav> nav_delete(HttpServletRequest request,Integer id) throws Exception;
	
	
	public List<HaiNav> list(HttpServletRequest request) throws Exception;
	
}

