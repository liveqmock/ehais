package org.ehais.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiBrand;
import org.ehais.tools.ReturnObject;



public interface BrandService extends CommonService{
	public ReturnObject<HaiBrand> brand_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBrand> brand_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiBrand> brand_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiBrand> brand_insert_submit(HttpServletRequest request,HaiBrand model) throws Exception;
	public ReturnObject<HaiBrand> brand_update(HttpServletRequest request,Integer brandId) throws Exception;
	public ReturnObject<HaiBrand> brand_update_submit(HttpServletRequest request,HaiBrand model) throws Exception;
	public ReturnObject<HaiBrand> brand_find(HttpServletRequest request,Integer brandId) throws Exception;
	public ReturnObject<HaiBrand> brand_delete(HttpServletRequest request,Integer brandId) throws Exception;
	
	
	public List<HaiBrand> list(HttpServletRequest request) throws Exception;
	
}

