package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticle;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface IXiangmengService extends CommonService{
	public ReturnObject<EHaiArticle> xiangmeng_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_insert_submit(HttpServletRequest request,EHaiArticle model) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_update(HttpServletRequest request,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_update_submit(HttpServletRequest request,EHaiArticle model) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_info(HttpServletRequest request,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_find(HttpServletRequest request,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> xiangmeng_delete(HttpServletRequest request,Integer articleId) throws Exception;

	

}

