package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.tp.TpDiningOrder;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface TpDiningOrderService extends CommonService{
	public ReturnObject<TpDiningOrder> diningorder_list(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_list_json(HttpServletRequest request,EConditionObject condition,String orderSn,String goodsDesc,String consignee,String mobile,String address,Integer orderStatus) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_insert_submit(HttpServletRequest request,TpDiningOrder model) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_update(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_update_submit(HttpServletRequest request,TpDiningOrder model) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_info(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_find(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<TpDiningOrder> diningorder_delete(HttpServletRequest request,Long orderId) throws Exception;
	
	

}

