package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.service.CommonService;
import org.ehais.shop.model.tp.TpDiningTable;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface TpDiningTableService extends CommonService{
	
	public ReturnObject<TpDiningTable> diningtable_list(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_list_json(HttpServletRequest request,EConditionObject condition,String tablename) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_insert_submit(HttpServletRequest request,TpDiningTable model) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_update(HttpServletRequest request,Long dtId) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_update_submit(HttpServletRequest request,TpDiningTable model) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_info(HttpServletRequest request,Long dtId) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_find(HttpServletRequest request,Long dtId) throws Exception;
	public ReturnObject<TpDiningTable> diningtable_delete(HttpServletRequest request,Long dtId) throws Exception;
	
	/**
	 * 导出二维码图片
	 * @param request
	 * @param dtId
	 * @return
	 * @throws Exception
	 */
	public void diningtable_export(HttpServletRequest request,HttpServletResponse response,Long dtId,Integer download,Integer preview) throws Exception;
	

	

}

