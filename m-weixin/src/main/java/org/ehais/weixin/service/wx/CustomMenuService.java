package org.ehais.weixin.service.wx;

import org.ehais.model.TreeModel;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpCustomMenu;
import org.ehais.weixin.model.WpPublic;

public interface CustomMenuService extends CommonService{
	
	public void CreateMenu(WpPublic wpPublic);
	
	
	public ReturnObject<WpCustomMenu> custom_menu_list(String token) throws Exception;
	public ReturnObject<TreeModel> custom_menu_list_json(String token,Integer page,Integer len) throws Exception;
	public ReturnObject<WpCustomMenu> custom_menu_insert(String token) throws Exception;
	public ReturnObject<WpCustomMenu> custom_menu_insert_submit(WpCustomMenu model) throws Exception;
	public ReturnObject<WpCustomMenu> custom_menu_update(String token,Integer key) throws Exception;
	public ReturnObject<WpCustomMenu> custom_menu_update_submit(WpCustomMenu model) throws Exception;
	public ReturnObject<WpCustomMenu> custom_menu_find(String token,Integer key) throws Exception;
	public ReturnObject<WpCustomMenu> custom_menu_delete(String token,Integer key) throws Exception;
	
	
	
}
