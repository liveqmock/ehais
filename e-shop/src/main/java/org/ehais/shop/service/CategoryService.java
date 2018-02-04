package org.ehais.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.model.TreeModel;
import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;



public interface CategoryService extends CommonService{
	public ReturnObject<HaiCategory> category_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCategory> category_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiCategory> category_list_json(HttpServletRequest request,EConditionObject condition) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_insert_submit(HttpServletRequest request,HaiCategoryWithBLOBs model) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_update(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_update_submit(HttpServletRequest request,HaiCategoryWithBLOBs model) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_find(HttpServletRequest request,Integer catId) throws Exception;
	public ReturnObject<HaiCategory> category_delete(HttpServletRequest request,Integer catId) throws Exception;
	
	
	public ReturnObject<HaiCategory> category_list(HttpServletRequest request,Integer store_id,Integer parent_id) throws Exception;
	public ReturnObject<HaiCategoryWithBLOBs> category_info(HttpServletRequest request,Integer store_id,Integer catId) throws Exception;
	
	
	
	//页面请求所有方法
	public List<HaiCategory> list(HttpServletRequest request) throws Exception;
	
	public List<TreeModel> categoryTreeList2(HttpServletRequest request)throws Exception;
	
}

