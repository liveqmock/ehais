package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiFavorites;
import org.ehais.shop.model.HaiGoods;
import org.ehais.tools.ReturnObject;



public interface FavoritesService extends CommonService{
	public ReturnObject<HaiFavorites> favorites_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiFavorites> favorites_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiFavorites> favorites_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiFavorites> favorites_insert_submit(HttpServletRequest request,HaiFavorites model) throws Exception;
	public ReturnObject<HaiFavorites> favorites_update(HttpServletRequest request,Long id) throws Exception;
	public ReturnObject<HaiFavorites> favorites_update_submit(HttpServletRequest request,HaiFavorites model) throws Exception;
	public ReturnObject<HaiFavorites> favorites_find(HttpServletRequest request,Long id) throws Exception;
	public ReturnObject<HaiFavorites> favorites_delete(HttpServletRequest request,Long id) throws Exception;
	
	
	public ReturnObject<HaiFavorites> favorites_add(HttpServletRequest request,Long goods_id, Long user_id,String session_shop_encode)throws Exception;
	
	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,Long user_id,Integer page,Integer len,String session_shop_encode) throws Exception;
	
}

