package org.ehais.shop.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.tools.ReturnObject;



public interface OrderInfoService extends CommonService{
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_insert_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_update(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_update_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_find(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_delete(HttpServletRequest request,Long orderId) throws Exception;
	
	
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request,Long user_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_info(HttpServletRequest request,Long user_id,Long orderId) throws Exception;
	

	/**
	 * 下订单时设置的默认值
	 * @param orderInfo
	 */
	public void setDefaultOrder(HaiOrderInfoWithBLOBs orderInfo,Date date,Integer store_id) ;
	
}

