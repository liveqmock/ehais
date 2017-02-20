package org.ehais.shop.service;

import org.ehais.epublic.model.EHaiArticle;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface ArticleService extends CommonService{

	public ReturnObject<EHaiArticle> article_list_cid(Integer store_id,Integer cat_id,Integer page,Integer len) throws Exception;
	
	public ReturnObject<EHaiArticle> article_list_code(Integer store_id,String cat_code,Integer page,Integer len) throws Exception;
	
	public ReturnObject<EHaiArticle> article_info(Integer store_id,Integer article_id) throws Exception;
	
	
}
