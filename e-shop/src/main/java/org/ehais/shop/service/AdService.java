package org.ehais.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.EHaiAd;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface AdService extends CommonService{

	public ReturnObject<EHaiAd> hai_ad_list(Integer store_id, Integer position_id ,Integer enabled,Integer is_mobile,Integer page,Integer len)throws Exception ;
	
	public List<EHaiAd> list(HttpServletRequest request)throws Exception ;
	
}
