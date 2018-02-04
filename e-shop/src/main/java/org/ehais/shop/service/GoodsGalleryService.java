package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.tools.ReturnObject;


public interface GoodsGalleryService extends CommonService{

	//获取相册列表
	public ReturnObject<HaiGoodsGallery> goods_gallery_list(HttpServletRequest request,Long goods_id,Integer store_id) throws Exception;
	//获取相册图片
	public ReturnObject<HaiGoodsGallery> goods_gallery_find(HttpServletRequest request,Long img_id,Long goods_id,Integer store_id) throws Exception;
	//新增相册
	public ReturnObject<HaiGoodsGallery> goods_gallery_insert(HttpServletRequest request,HaiGoodsGallery model) throws Exception;
	//编辑相册
	public ReturnObject<HaiGoodsGallery> goods_gallery_update(HttpServletRequest request,HaiGoodsGallery model) throws Exception;
	//删除相册
	public ReturnObject<HaiGoodsGallery> goods_gallery_delete(HttpServletRequest request,Long img_id,Long goods_id,Integer store_id) throws Exception;
	
}
