package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.tp.TpDiningCategory;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface TpDiningCategoryService extends CommonService{
	public ReturnObject<TpDiningCategory> diningcategory_list(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_list_json(HttpServletRequest request,EConditionObject condition) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_insert_submit(HttpServletRequest request,TpDiningCategory model) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_update(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_update_submit(HttpServletRequest request,TpDiningCategory model) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_info(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_find(HttpServletRequest request,Integer id) throws Exception;
	public ReturnObject<TpDiningCategory> diningcategory_delete(HttpServletRequest request,Integer id) throws Exception;
	
	

}

