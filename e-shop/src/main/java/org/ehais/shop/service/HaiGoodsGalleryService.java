package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiGoodsGalleryService extends CommonService{
	public ReturnObject<HaiGoodsGallery> goodsgallery_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String tableName) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_insert_submit(HttpServletRequest request,HaiGoodsGallery model) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_update(HttpServletRequest request,Integer imgId) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_update_submit(HttpServletRequest request,HaiGoodsGallery model) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_info(HttpServletRequest request,Integer imgId) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_find(HttpServletRequest request,Integer imgId) throws Exception;
	public ReturnObject<HaiGoodsGallery> goodsgallery_delete(HttpServletRequest request,Integer imgId) throws Exception;
	


	

}

