package org.ehais.weixin.service.action;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.WpKeyword;

public interface KeywordService extends CommonService{
	public ReturnObject<WpKeyword> keyword_list(HttpServletRequest request) throws Exception;
	public ReturnObject<WpKeyword> keyword_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<WpKeyword> keyword_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<WpKeyword> keyword_insert_submit(HttpServletRequest request,WpKeyword model) throws Exception;
	public ReturnObject<WpKeyword> keyword_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpKeyword> keyword_update_submit(HttpServletRequest request,WpKeyword model) throws Exception;
	public ReturnObject<WpKeyword> keyword_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<WpKeyword> keyword_delete(HttpServletRequest request,Integer id) throws Exception;
	
	

}

