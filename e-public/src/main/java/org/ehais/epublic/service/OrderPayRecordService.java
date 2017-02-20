package org.ehais.epublic.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.HaiOrderPayRecord;
import org.ehais.epublic.model.HaiOrderPayRecordAndUsers;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface OrderPayRecordService extends CommonService{
	public ReturnObject<HaiOrderPayRecord> orderpayrecord_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiOrderPayRecordAndUsers> orderpayrecord_list_json(HttpServletRequest request,Integer store_id,
			EConditionObject condition,Integer tableId,String tableName) throws Exception;
	
	
	public ReturnObject<HaiOrderPayRecordAndUsers> orderPayUsers(HttpServletRequest request, Integer wxid,String tableName,Integer tableId, Integer page,
			Integer size) throws Exception ;
	
	
	/**
	 * 完成支付更新订单状态
	 * @param OrderSn
	 * @param rm
	 * @throws Exception
	 */
	public <T> boolean successOrderPay(String OrderSn,ReturnObject<T> rm) throws Exception;
	

}

