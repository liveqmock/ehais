package org.ehais.shop.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsDistribution;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;



public interface GoodsService extends CommonService{
	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,EConditionObject condition,Integer cat_id , String goods_name) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_insert_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> ehais_goods_insert_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery,HaiGoodsDistribution goodsDistribution) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_update(HttpServletRequest request,Long goodsId) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_update_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> ehais_goods_update_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery,HaiGoodsDistribution goodsDistribution) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_find(HttpServletRequest request,Long goodsId) throws Exception;
	public ReturnObject<HaiGoods> goods_delete(HttpServletRequest request,Long goodsId) throws Exception;
	
	
	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request,Integer store_id,Integer catId,Integer page, Integer len) throws Exception;
	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request,String cid,Integer store_id,Integer catId,Integer page, Integer len) throws Exception;
	
	public ReturnObject<HaiGoodsWithBLOBs> goods_info(HttpServletRequest request,Integer store_id,Long goodsId) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> app_goods_info(HttpServletRequest request, Integer store_id,Long user_id,String session_shop_encode ,Long goodsId) throws Exception;
	
	public List<HaiGoods> list(HttpServletRequest request,Integer catId,Integer page, Integer len) throws Exception;
	public HaiGoods info(HttpServletRequest request,Long goodsId) throws Exception;
	public List<HaiGoodsGallery> galleryList(HttpServletRequest request,Long goodsId) throws Exception;
	
	
	/**
	 * 处理商品三级分销链接地址
	 * @param wp
	 * @param store_id
	 * @param user_id
	 * @param map
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> dealGoods(WpPublicWithBLOBs wp ,Integer store_id,Long user_id ,Map<String,Object> map,HaiGoods goods) throws Exception;
	
}

