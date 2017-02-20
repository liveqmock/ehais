package com.ehais.huangbao.service;

import org.ehais.tools.ReturnObject;

import com.ehais.huangbao.model.HaiContribution;

public interface HuanBaoService extends WXPayService {

	public ReturnObject<HaiContribution> Contribution_List(Integer wxid,String cat_code,Integer page,Integer len)throws Exception;
	
	public ReturnObject<HaiContribution> Contribution_Detail(Integer wxid,Integer conId)throws Exception;
	
}
