package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiAd;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;



public interface EAdService extends CommonService{
	public ReturnObject<EHaiAd> ad_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiAd> ad_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiAd> ad_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiAd> ad_insert_submit(HttpServletRequest request,EHaiAd model) throws Exception;
	public ReturnObject<EHaiAd> ad_update(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<EHaiAd> ad_update_submit(HttpServletRequest request,EHaiAd model) throws Exception;
	public ReturnObject<EHaiAd> ad_find(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<EHaiAd> ad_delete(HttpServletRequest request,Integer adId) throws Exception;
	
	

}

