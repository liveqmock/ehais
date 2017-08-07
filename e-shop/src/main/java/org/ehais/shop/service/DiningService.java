package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.tp.TpDining;
import org.ehais.shop.model.tp.TpDiningWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface DiningService extends CommonService{
	public ReturnObject<TpDining> dining_list(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDining> dining_list_json(HttpServletRequest request,EConditionObject condition) throws Exception;
	public ReturnObject<TpDiningWithBLOBs> dining_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningWithBLOBs> dining_insert_submit(HttpServletRequest request,TpDiningWithBLOBs model) throws Exception;
	public ReturnObject<TpDiningWithBLOBs> dining_update(HttpServletRequest request,Long goodsId) throws Exception;
	public ReturnObject<TpDiningWithBLOBs> dining_update_submit(HttpServletRequest request,TpDiningWithBLOBs model) throws Exception;
	public ReturnObject<TpDiningWithBLOBs> dining_info(HttpServletRequest request,Long goodsId) throws Exception;
	public ReturnObject<TpDiningWithBLOBs> dining_find(HttpServletRequest request,Long goodsId) throws Exception;
	public ReturnObject<TpDining> dining_delete(HttpServletRequest request,Long goodsId) throws Exception;
	
	

}

