package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiOrderInfoService extends CommonService{
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_list_json(HttpServletRequest request,EConditionObject condition,Long recId,String orderName) throws Exception;
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_insert_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model) throws Exception;
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_update(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_update_submit(HttpServletRequest request,HaiOrderInfoWithBLOBs model) throws Exception;
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_info(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<HaiOrderInfoWithBLOBs> orderinfo_find(HttpServletRequest request,Long orderId) throws Exception;
	public ReturnObject<HaiOrderInfo> orderinfo_delete(HttpServletRequest request,Long orderId) throws Exception;
	

////////////////////////////////////////////////////////////////////



	public ReturnObject<HaiOrderGoods> ordergoods_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_list_json(HttpServletRequest request,EConditionObject condition,String goodsName) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_insert_submit(HttpServletRequest request,HaiOrderGoods model) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_update(HttpServletRequest request,Long recId) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_update_submit(HttpServletRequest request,HaiOrderGoods model) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_info(HttpServletRequest request,Long recId) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_find(HttpServletRequest request,Long recId) throws Exception;
	public ReturnObject<HaiOrderGoods> ordergoods_delete(HttpServletRequest request,Long recId) throws Exception;
	

	

}

