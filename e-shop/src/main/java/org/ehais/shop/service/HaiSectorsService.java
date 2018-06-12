package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiSectors;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiSectorsService extends CommonService{
	public ReturnObject<HaiSectors> sectors_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiSectors> sectors_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String sectorsName) throws Exception;
	public ReturnObject<HaiSectors> sectors_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiSectors> sectors_insert_submit(HttpServletRequest request,HaiSectors model) throws Exception;
	public ReturnObject<HaiSectors> sectors_update(HttpServletRequest request,Integer sectorsId) throws Exception;
	public ReturnObject<HaiSectors> sectors_update_submit(HttpServletRequest request,HaiSectors model) throws Exception;
	public ReturnObject<HaiSectors> sectors_info(HttpServletRequest request,Integer sectorsId) throws Exception;
	public ReturnObject<HaiSectors> sectors_find(HttpServletRequest request,Integer sectorsId) throws Exception;
	public ReturnObject<HaiSectors> sectors_delete(HttpServletRequest request,Integer sectorsId) throws Exception;
	


	

}

