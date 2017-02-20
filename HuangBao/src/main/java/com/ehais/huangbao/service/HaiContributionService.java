package com.ehais.huangbao.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.huangbao.model.HaiContribution;



//捐款
public interface HaiContributionService extends CommonService{
	public ReturnObject<HaiContribution> haicontribution_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_insert_submit(HttpServletRequest request,HaiContribution model) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_update(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_update_submit(HttpServletRequest request,HaiContribution model) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_find(HttpServletRequest request,Integer key) throws Exception;
	public ReturnObject<HaiContribution> haicontribution_delete(HttpServletRequest request,Integer key) throws Exception;
	
	

}

