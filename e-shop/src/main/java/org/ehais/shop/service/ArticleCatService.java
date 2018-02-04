package org.ehais.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface ArticleCatService extends CommonService{

	public ReturnObject<EHaiArticleCat> article_cat_parent_list(String moduleEnum,Integer store_id,Integer parent_id)throws Exception;
	
	public List<EHaiArticleCat> articleCatList(String moduleEnum,Integer store_id , Integer parent_id) throws Exception;
	
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,String moduleEnum,EHaiArticle model) throws Exception;
	
	
	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request,String moduleEnum) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,String moduleEnum,EConditionObject condition) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request,String moduleEnum) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,String moduleEnum,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,String moduleEnum,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,String moduleEnum,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,String moduleEnum) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_info(HttpServletRequest request,String moduleEnum,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,String moduleEnum,Integer catId) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,String moduleEnum,Integer catId) throws Exception;
	
	
	//兼容插入与更新的方法
	public EHaiArticleCat articleCatSave(EHaiArticleCat cate,String parent_cat_name,Integer store_id) throws Exception;
	
	
}
