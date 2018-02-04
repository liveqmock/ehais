package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiPurchase;
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiPurchaseService extends CommonService{
	public ReturnObject<HaiPurchase> purchase_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPurchase> purchase_list_json(HttpServletRequest request,EConditionObject condition,Integer warehouseId,String purchaseNo) throws Exception;
	public ReturnObject<HaiPurchase> purchase_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiPurchase> purchase_insert_submit(HttpServletRequest request,HaiPurchase model) throws Exception;
	public ReturnObject<HaiPurchase> purchase_update(HttpServletRequest request,Integer purchaseId) throws Exception;
	public ReturnObject<HaiPurchase> purchase_update_submit(HttpServletRequest request,HaiPurchase model) throws Exception;
	public ReturnObject<HaiPurchase> purchase_info(HttpServletRequest request,Integer purchaseId) throws Exception;
	public ReturnObject<HaiPurchase> purchase_find(HttpServletRequest request,Integer purchaseId) throws Exception;
	public ReturnObject<HaiPurchase> purchase_delete(HttpServletRequest request,Integer purchaseId) throws Exception;
	

////////////////////////////////////////////////////////////////////


	public ReturnObject<HaiWarehouse> warehouse_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_list_json(HttpServletRequest request,EConditionObject condition,String warehouseName) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_insert_submit(HttpServletRequest request,HaiWarehouse model) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_update(HttpServletRequest request,Integer warehouseId) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_update_submit(HttpServletRequest request,HaiWarehouse model) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_info(HttpServletRequest request,Integer warehouseId) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_find(HttpServletRequest request,Integer warehouseId) throws Exception;
	public ReturnObject<HaiWarehouse> warehouse_delete(HttpServletRequest request,Integer warehouseId) throws Exception;
	


	

}

