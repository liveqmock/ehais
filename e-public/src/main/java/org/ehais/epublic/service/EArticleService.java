package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticle;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;



public interface EArticleService extends CommonService{
	public ReturnObject<EHaiArticle> article_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,Integer store_id,boolean isCode,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,Integer store_id,Integer catId,String module,EConditionObject condition) throws Exception;
	public ReturnObject<EHaiArticle> article_insert(HttpServletRequest request,String module) throws Exception;
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,EHaiArticle model) throws Exception;
	public ReturnObject<EHaiArticle> article_update(HttpServletRequest request,String module,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> article_update_submit(HttpServletRequest request,EHaiArticle model) throws Exception;
	public ReturnObject<EHaiArticle> article_find(HttpServletRequest request,Integer articleId,String module) throws Exception;
	public ReturnObject<EHaiArticle> article_delete(HttpServletRequest request,String module,Integer articleId) throws Exception;
	
	
	

}

