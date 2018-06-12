package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiCategoryService extends CommonService{
	public ReturnObject<HaiCategory> category_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCategory> category_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String catName) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_insert_submit(HttpServletRequest request,HaiCategoryWithBLOBs model) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_update(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_update_submit(HttpServletRequest request,HaiCategoryWithBLOBs model) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_info(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_find(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiCategory> category_delete(HttpServletRequest request,Integer catId) throws Exception;
	


	

}

