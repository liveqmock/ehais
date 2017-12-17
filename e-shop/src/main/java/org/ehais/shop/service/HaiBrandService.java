package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiBrand;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiBrandService extends CommonService{
	public ReturnObject<HaiBrand> brand_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBrand> brand_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String brandName) throws Exception;
	public ReturnObject<HaiBrand> brand_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBrand> brand_insert_submit(HttpServletRequest request,HaiBrand model) throws Exception;
	public ReturnObject<HaiBrand> brand_update(HttpServletRequest request,Integer brandId) throws Exception;
	public ReturnObject<HaiBrand> brand_update_submit(HttpServletRequest request,HaiBrand model) throws Exception;
	public ReturnObject<HaiBrand> brand_info(HttpServletRequest request,Integer brandId) throws Exception;
	public ReturnObject<HaiBrand> brand_find(HttpServletRequest request,Integer brandId) throws Exception;
	public ReturnObject<HaiBrand> brand_delete(HttpServletRequest request,Integer brandId) throws Exception;
	


	

}

