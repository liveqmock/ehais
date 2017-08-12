package org.ehais.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface ArticleCatService extends CommonService{

	public ReturnObject<EHaiArticleCat> article_cat_parent_list(Integer store_id,Integer parent_id)throws Exception;
	
	public List<EHaiArticleCat> articleCatList(Integer store_id , Integer parent_id) throws Exception;
	
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,EHaiArticle model) throws Exception;
	
	
	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,EConditionObject condition) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,Integer catId) throws Exception;
	
	
}
