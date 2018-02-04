package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdPosition;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiAdService extends CommonService{
	public ReturnObject<HaiAd> ad_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAd> ad_list_json(HttpServletRequest request,EConditionObject condition,Integer positionId,String adName) throws Exception;
	public ReturnObject<HaiAd> ad_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAd> ad_insert_submit(HttpServletRequest request,HaiAd model) throws Exception;
	public ReturnObject<HaiAd> ad_update(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<HaiAd> ad_update_submit(HttpServletRequest request,HaiAd model) throws Exception;
	public ReturnObject<HaiAd> ad_info(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<HaiAd> ad_find(HttpServletRequest request,Integer adId) throws Exception;
	public ReturnObject<HaiAd> ad_delete(HttpServletRequest request,Integer adId) throws Exception;
	

////////////////////////////////////////////////////////////////////


	public ReturnObject<HaiAdPosition> adposition_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_list_json(HttpServletRequest request,EConditionObject condition,String positionName) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_insert_submit(HttpServletRequest request,HaiAdPosition model) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_update(HttpServletRequest request,Integer positionId) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_update_submit(HttpServletRequest request,HaiAdPosition model) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_info(HttpServletRequest request,Integer positionId) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_find(HttpServletRequest request,Integer positionId) throws Exception;
	public ReturnObject<HaiAdPosition> adposition_delete(HttpServletRequest request,Integer positionId) throws Exception;
	


	

}

