package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.model.TreeModel;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.HaiArticleCat;



public interface ArticleCatService extends CommonService{
	public ReturnObject<HaiArticleCat> articlecat_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_insert_submit(HttpServletRequest request,HaiArticleCat model) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_update(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_update_submit(HttpServletRequest request,HaiArticleCat model) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_find(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiArticleCat> articlecat_delete(HttpServletRequest request,Integer catId) throws Exception;
	
	public ReturnObject<TreeModel> articlecat_tree_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	
	public ReturnObject<HaiArticleCat> articlecatcode(HttpServletRequest request,Integer store_id,String code) throws Exception;
	
	
}

