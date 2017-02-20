package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiStoreAppkeySecret;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface EStoreAppkeySecretService extends CommonService{
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_insert_submit(HttpServletRequest request,EHaiStoreAppkeySecret model) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_update(HttpServletRequest request,Integer appId) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_update_submit(HttpServletRequest request,EHaiStoreAppkeySecret model) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_find(HttpServletRequest request,Integer appId) throws Exception;
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_delete(HttpServletRequest request,Integer appId) throws Exception;
	
	

}

