package org.ehais.shop.service;

import javax.servlet.http.HttpServletRequest;
import org.ehais.service.CommonService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;

public interface HaiGoodsService extends CommonService{
	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,EConditionObject condition,Integer goodsAttrId,String goodsName) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_insert_submit(HttpServletRequest request,HaiGoodsWithBLOBs model) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_update(HttpServletRequest request,Integer goodsId) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_update_submit(HttpServletRequest request,HaiGoodsWithBLOBs model) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_info(HttpServletRequest request,Integer goodsId) throws Exception;
	public ReturnObject<HaiGoodsWithBLOBs> goods_find(HttpServletRequest request,Integer goodsId) throws Exception;
	public ReturnObject<HaiGoods> goods_delete(HttpServletRequest request,Integer goodsId) throws Exception;
	

////////////////////////////////////////////////////////////////////



	public ReturnObject<HaiGoodsAttr> goodsattr_list(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_list_json(HttpServletRequest request,EConditionObject condition,String attrValue ) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_insert(HttpServletRequest request) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_insert_submit(HttpServletRequest request,HaiGoodsAttr model) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_update(HttpServletRequest request,Integer goodsAttrId) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_update_submit(HttpServletRequest request,HaiGoodsAttr model) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_info(HttpServletRequest request,Integer goodsAttrId) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_find(HttpServletRequest request,Integer goodsAttrId) throws Exception;
	public ReturnObject<HaiGoodsAttr> goodsattr_delete(HttpServletRequest request,Integer goodsAttrId) throws Exception;
	

	

}

