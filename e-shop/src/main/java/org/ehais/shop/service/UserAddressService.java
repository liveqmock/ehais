package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.tools.ReturnObject;

public interface UserAddressService extends CommonService{
	
	
	public ReturnObject<HaiUserAddress> useraddress_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_insert_submit(HttpServletRequest request,HaiUserAddress model) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_update(HttpServletRequest request,Long addressId) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_update_submit(HttpServletRequest request,HaiUserAddress model) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_find(HttpServletRequest request,Long addressId) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_delete(HttpServletRequest request,Long addressId,Long user_id) throws Exception;
	
	
	public ReturnObject<HaiUserAddress> useraddress_add_submit(HttpServletRequest request,HaiUserAddress model,Long user_id) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_edit_submit(HttpServletRequest request,HaiUserAddress model,Long user_id) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_info(HttpServletRequest request,Long addressId,Long user_id) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_lists(HttpServletRequest request,Long user_id) throws Exception;
	
	public ReturnObject<HaiUserAddress> useraddress_set_default(HttpServletRequest request,Long address_id,Long user_id) throws Exception;
	public ReturnObject<HaiUserAddress> useraddress_delete_sumbit(HttpServletRequest request,Long address_id,Long user_id) throws Exception;
	
}

