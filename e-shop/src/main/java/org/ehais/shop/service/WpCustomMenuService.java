package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.weixin.WpCustomMenu;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface WpCustomMenuService extends CommonService{
	public ReturnObject<WpCustomMenu> custommenu_list(HttpServletRequest request) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_insert_submit(HttpServletRequest request,WpCustomMenu model) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_update_submit(HttpServletRequest request,WpCustomMenu model) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_info(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpCustomMenu> custommenu_delete(HttpServletRequest request,Integer id) throws Exception;
	
}

