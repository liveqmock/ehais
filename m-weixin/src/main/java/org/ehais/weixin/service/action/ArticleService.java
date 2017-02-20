package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiArticle;
import org.ehais.weixin.model.HaiArticleCat;

public interface ArticleService extends CommonService{
	
	public ReturnObject<HaiArticle> article_list(HttpServletRequest request,Integer store_id,Integer cat_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiArticle> article_list_json(HttpServletRequest request,Integer store_id,Integer cat_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiArticle> article_insert(HttpServletRequest request,Integer store_id) throws Exception;
	public ReturnObject<HaiArticle> article_insert_v2(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiArticle> article_insert_submit(HttpServletRequest request,HaiArticle model) throws Exception;
	public ReturnObject<HaiArticle> article_update(HttpServletRequest request,Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiArticle> article_update_v2(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<HaiArticle> article_update_submit(HttpServletRequest request,HaiArticle model) throws Exception;
	public ReturnObject<HaiArticle> article_find(HttpServletRequest request,Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiArticle> article_delete(HttpServletRequest request,Integer store_id,Integer key) throws Exception;
	
	
	public HaiArticleCat article_cat(HttpServletRequest request,Integer key) throws Exception;
	public HaiArticle article(HttpServletRequest request,Integer key) throws Exception;
	public HaiArticle article_code(HttpServletRequest request,Integer store_id,String code) throws Exception;
	
	public ReturnObject<HaiArticle> article_code_list(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	
	public ReturnObject<HaiArticle> article_list_by_catcode(HttpServletRequest request,Integer store_id,String code,Integer page,Integer len) throws Exception;
	
	
}
