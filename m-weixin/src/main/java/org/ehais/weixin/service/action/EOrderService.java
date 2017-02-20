package org.ehais.weixin.service.action;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.model.EOrder;



public interface EOrderService extends CommonService{
	public ReturnObject<EOrder> e_order_list(Integer wxid) throws Exception;
	public ReturnObject<EOrder> e_order_list_json(Integer wxid,Integer page,Integer len) throws Exception;
	public ReturnObject<EOrder> e_order_insert(Integer wxid) throws Exception;
	public ReturnObject<EOrder> e_order_insert_submit(EOrder model) throws Exception;
	public ReturnObject<EOrder> e_order_update(Integer wxid,Integer key) throws Exception;
	public ReturnObject<EOrder> e_order_update_submit(EOrder model) throws Exception;
	public ReturnObject<EOrder> e_order_find(Integer wxid,Integer key) throws Exception;
	public ReturnObject<EOrder> e_order_delete(Integer wxid,Integer key) throws Exception;
	
	

}
