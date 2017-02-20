package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.model.TreeModel;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface EArticleCatService extends CommonService{
	public ReturnObject<EHaiArticleCat> articlecat_list(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_insert_submit(HttpServletRequest request,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_update_submit(HttpServletRequest request,EHaiArticleCat model) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<EHaiArticleCat> articlecat_delete(HttpServletRequest request,Integer id) throws Exception;
	
	
	public ReturnObject<TreeModel> articlecat_tree_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	
	public ReturnObject<EHaiArticleCat> articlecatcode(HttpServletRequest request,Integer store_id,String code) throws Exception;
	
	

}

