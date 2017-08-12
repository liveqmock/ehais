package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiAgency;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiAgencyService extends CommonService{
	public ReturnObject<HaiAgency> agency_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAgency> agency_list_json(HttpServletRequest request,EConditionObject condition,String agencyName) throws Exception;
	public ReturnObject<HaiAgency> agency_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiAgency> agency_insert_submit(HttpServletRequest request,HaiAgency model) throws Exception;
	public ReturnObject<HaiAgency> agency_update(HttpServletRequest request,Integer agencyId) throws Exception;
	public ReturnObject<HaiAgency> agency_update_submit(HttpServletRequest request,HaiAgency model) throws Exception;
	public ReturnObject<HaiAgency> agency_info(HttpServletRequest request,Integer agencyId) throws Exception;
	public ReturnObject<HaiAgency> agency_find(HttpServletRequest request,Integer agencyId) throws Exception;
	public ReturnObject<HaiAgency> agency_delete(HttpServletRequest request,Integer agencyId) throws Exception;
	
	

}

