package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiWarehouseService extends CommonService{
	public ReturnObject<HaiWarehouse> warehouse_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String warehouseName) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_insert_submit(HttpServletRequest request,HaiWarehouse model) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_update(HttpServletRequest request,Integer warehouseId) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_update_submit(HttpServletRequest request,HaiWarehouse model) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_info(HttpServletRequest request,Integer warehouseId) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_find(HttpServletRequest request,Integer warehouseId) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_delete(HttpServletRequest request,Integer warehouseId) throws Exception;
	


	

}

