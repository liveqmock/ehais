package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiActivity;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiActivityService extends CommonService{
	public ReturnObject<HaiActivity> activity_list(HttpServletRequest request,String module) throws Exception;
	public ReturnObject<HaiActivity> activity_list_json(HttpServletRequest request,String module,EConditionObject condition,Integer catId,String activityName) throws Exception;
	public ReturnObject<HaiActivity> activity_insert(HttpServletRequest request,String module) throws Exception;
	public ReturnObject<HaiActivity> activity_insert_submit(HttpServletRequest request,String module,HaiActivity model) throws Exception;
	public ReturnObject<HaiActivity> activity_update(HttpServletRequest request,String module,Integer activityId) throws Exception;
	public ReturnObject<HaiActivity> activity_update_submit(HttpServletRequest request,String module,HaiActivity model) throws Exception;
	public ReturnObject<HaiActivity> activity_info(HttpServletRequest request,String module,Integer activityId) throws Exception;
	public ReturnObject<HaiActivity> activity_find(HttpServletRequest request,String module,Integer activityId) throws Exception;
	public ReturnObject<HaiActivity> activity_delete(HttpServletRequest request,String module,Integer activityId) throws Exception;
	

////////////////////////////////////////////////////////////////////


	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request,String module) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,String module,EConditionObject condition,String catName) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request,String module) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,String module,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,String module,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,String module,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,String module,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,String module,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,String module,Integer catId) throws Exception;
	


	

}

