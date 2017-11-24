package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiStore;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface HaiStoreService extends CommonService{
	public ReturnObject<EHaiStore> store_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiStore> store_list_json(HttpServletRequest request,EConditionObject condition,Integer partnerId,String storeName) throws Exception;
	public ReturnObject<EHaiStore> store_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiStore> store_insert_submit(HttpServletRequest request,EHaiStore model) throws Exception;
	public ReturnObject<EHaiStore> store_partner_update(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_partner_update_submit(HttpServletRequest request,EHaiStore model) throws Exception;
	public ReturnObject<EHaiStore> store_update(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_update_submit(HttpServletRequest request,EHaiStore model) throws Exception;
	public ReturnObject<EHaiStore> store_info(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_find(HttpServletRequest request,Integer storeId) throws Exception;
	public ReturnObject<EHaiStore> store_delete(HttpServletRequest request,Integer storeId) throws Exception;
	
	public ReturnObject<EHaiStore> store_cache(HttpServletRequest request,Integer storeId) throws Exception;
	
	/** 商户、餐饮 注册 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<EHaiStore> store_register(
			HttpServletRequest request,
			String pid	,
			String username,
			String password,
			String confirmPassword,
			String store_name,
			String contacts,
			String mobile,
			String address,
			String classify,
			String weixin_token,
			Short UserType
			) throws Exception;
	
	

}

