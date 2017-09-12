package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiStore;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiStoreService extends CommonService{
	public ReturnObject<EHaiStore> store_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiStore> store_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String storeName) throws Exception;
	public ReturnObject<EHaiStore> store_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiStore> store_insert_submit(HttpServletRequest request,EHaiStore model) throws Exception;
	public ReturnObject<EHaiStore> store_update(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_update_submit(HttpServletRequest request,EHaiStore model) throws Exception;
	public ReturnObject<EHaiStore> store_info(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_find(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_delete(HttpServletRequest request,Integer storeId) throws Exception;
	

	

}

