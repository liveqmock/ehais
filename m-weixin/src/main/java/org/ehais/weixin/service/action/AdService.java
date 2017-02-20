package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiAd;



public interface AdService extends CommonService{
	public ReturnObject<HaiAd> ad_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAd> ad_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiAd> ad_list_json(Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiAd> ad_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAd> ad_insert_submit(HttpServletRequest request,HaiAd model) throws Exception;
	public ReturnObject<HaiAd> ad_update(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<HaiAd> ad_update_submit(HttpServletRequest request,HaiAd model) throws Exception;
	public ReturnObject<HaiAd> ad_find(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<HaiAd> ad_delete(HttpServletRequest request,Integer adId) throws Exception;
	
	

}

