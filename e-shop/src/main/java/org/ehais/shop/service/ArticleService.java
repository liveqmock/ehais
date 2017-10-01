package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticle;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface ArticleService extends CommonService{

	public ReturnObject<EHaiArticle> article_list_cid(Integer store_id,Integer cat_id,Integer page,Integer len) throws Exception;
	
	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,EConditionObject condition,Integer cat_id,String title) throws Exception;
	
	public ReturnObject<EHaiArticle> article_list_code(Integer store_id,String cat_code,Integer page,Integer len) throws Exception;
	
	public ReturnObject<EHaiArticle> article_info(Integer store_id,Integer article_id) throws Exception;
	
	
	public ReturnObject<EHaiArticle> article_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,EConditionObject condition) throws Exception;
	public ReturnObject<EHaiArticle> article_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,EHaiArticle model,Long goodsId) throws Exception;
	public ReturnObject<EHaiArticle> article_update(HttpServletRequest request,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> article_update_submit(HttpServletRequest request,EHaiArticle model,Long goodsId) throws Exception;
	public ReturnObject<EHaiArticle> article_info(HttpServletRequest request,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> article_find(HttpServletRequest request,Integer articleId) throws Exception;
	public ReturnObject<EHaiArticle> article_delete(HttpServletRequest request,Integer articleId) throws Exception;
	
	/**
	 * 文章的推荐与评论信息
	 * @param request
	 * @param sid
	 * @param g 1:有商品,0:无商品
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<EHaiArticle> article_extends_list_json(HttpServletRequest request,String sid,Integer g) throws Exception;
	
	
}
