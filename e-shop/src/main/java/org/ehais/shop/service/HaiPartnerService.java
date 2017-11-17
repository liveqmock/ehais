package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.HaiPartner;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiPartnerService extends CommonService{
	public ReturnObject<HaiPartner> partner_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPartner> partner_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String partnerName) throws Exception;
	public ReturnObject<HaiPartner> partner_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPartner> partner_insert_submit(HttpServletRequest request,HaiPartner model) throws Exception;
	public ReturnObject<HaiPartner> partner_update(HttpServletRequest request,Integer partnerId) throws Exception;
	public ReturnObject<HaiPartner> partner_update_submit(HttpServletRequest request,HaiPartner model) throws Exception;
	public ReturnObject<HaiPartner> partner_info(HttpServletRequest request,Integer partnerId) throws Exception;
	public ReturnObject<HaiPartner> partner_find(HttpServletRequest request,Integer partnerId) throws Exception;
	public ReturnObject<HaiPartner> partner_delete(HttpServletRequest request,Integer partnerId) throws Exception;
	


	

}

