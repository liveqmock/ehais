package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.HaiShopConfig;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface ShopConfigService extends CommonService{
	public ReturnObject<HaiShopConfig> shopconfig_update_submit(HttpServletRequest request,boolean setEmptyValue) throws Exception;
	public ReturnObject<HaiShopConfig> shopconfig_find(HttpServletRequest request,String store_config_path) throws Exception;
	public ReturnObject<HaiShopConfig> shopconfig_info(HttpServletRequest request) throws Exception;
	
	

}

