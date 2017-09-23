package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartWithBLOBs;
import org.ehais.tools.ReturnObject;

public interface CartService extends CommonService{
	public ReturnObject<HaiCart> cart_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCart> cart_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_insert_submit(HttpServletRequest request,HaiCartWithBLOBs model) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_update(HttpServletRequest request,Long recId) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_update_submit(HttpServletRequest request,HaiCartWithBLOBs model) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_find(HttpServletRequest request,Long recId) throws Exception;
	public ReturnObject<HaiCart> cart_delete(HttpServletRequest request,Long recId) throws Exception;
	
	
	public ReturnObject<HaiCart> cart_quantity(HttpServletRequest request,Long user_id,String session_shop_encode) throws Exception;
	public ReturnObject<HaiCart> cart_list(HttpServletRequest request,Long user_id,String session_shop_encode) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_info(HttpServletRequest request, Long rec_id,Long goods_id, Long user_id,  String session_id) throws Exception;
//	public ReturnObject<HaiCart> cart_list_session(HttpServletRequest request,String sessioin_id) throws Exception;
//	public ReturnObject<HaiCart> cart_info_session(HttpServletRequest request,Long recId,String sessioin_id) throws Exception;
	/**
	 * 
	 * @param request
	 * @param goods_id
	 * @param store_id
	 * @param quantity
	 * @param user_id
	 * @param session_shop_encode
	 * @param parent_user_id
	 * @return
	 * @throws Exception
	 */
	public ReturnObject<HaiCartWithBLOBs> cart_add_submit(HttpServletRequest request,Long goods_id,Integer store_id,Integer quantity,Long user_id,String session_shop_encode,Long parent_user_id,Integer agency_id,Integer article_id) throws Exception;
	public ReturnObject<HaiCartWithBLOBs> cart_edit_quantity(HttpServletRequest request,Long recId,Long goods_id,Integer store_id,Integer quantity,Long user_id,String session_shop_encode) throws Exception;
	public ReturnObject<HaiCart> cart_delete(HttpServletRequest request,String recIds,Long user_id) throws Exception;
	public ReturnObject<HaiCart> cart_clear(HttpServletRequest request,Long user_id) throws Exception;
	public ReturnObject<HaiCart> cart_update_user_session(HttpServletRequest request,Long user_id,String session_id) throws Exception;
	
	
}

