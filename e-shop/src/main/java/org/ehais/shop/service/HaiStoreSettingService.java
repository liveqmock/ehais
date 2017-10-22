package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiStoreSetting;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiStoreSettingService extends CommonService{
	public ReturnObject<HaiStoreSetting> storesetting_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String storeName) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_insert_submit(HttpServletRequest request,HaiStoreSetting model) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_update(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_update_submit(HttpServletRequest request,HaiStoreSetting model) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_info(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_find(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<HaiStoreSetting> storesetting_delete(HttpServletRequest request,Integer storeId) throws Exception;
	


	

}

