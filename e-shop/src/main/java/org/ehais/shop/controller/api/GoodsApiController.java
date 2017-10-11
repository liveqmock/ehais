package org.ehais.shop.controller.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.shop.controller.api.include.GoodsIController;
import org.ehais.shop.mapper.HaiAttributeMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsAttrMapper;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiAttribute;
import org.ehais.shop.model.HaiAttributeExample;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCategoryWithBLOBs;
import org.ehais.shop.model.HaiGoodsAttr;
import org.ehais.shop.model.HaiGoodsAttrExample;
import org.ehais.shop.model.HaiGoodsEntity;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;


@Controller
@RequestMapping("/api")
public class GoodsApiController extends GoodsIController{

	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	@Autowired
	private HaiAttributeMapper haiAttributeMapper;
	@Autowired
	private HaiGoodsAttrMapper haiGoodsAttrMapper;
	
	@ResponseBody
	@RequestMapping("/app_goods_info")
	public String app_goods_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "user_id", required = true) Long user_id,
			@RequestParam(value = "s_encode", required = true) String s_encode,
			@RequestParam(value = "goodsId", required = true) Long goodsId){
		
		try {
			return this.writeJson(goodsService.app_goods_info(request,store_id ,user_id , s_encode, goodsId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/goods_save")
	public String goods_save(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
//			@ModelAttribute HaiGoodsWithBLOBs goods,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "json", required = true) String json){	
		
		Gson gson = new Gson();
		HaiGoodsEntity entity = gson.fromJson(json, HaiGoodsEntity.class);
		HaiGoodsWithBLOBs goods = entity.getGoods();
		List<HaiGoodsGallery> gallery = entity.getGoodsGalleryList();
		List<HaiGoodsAttr> goodsAttr = entity.getGoodsAttrList();
		
		HaiCategoryExample cateExp = new HaiCategoryExample();
		cateExp.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andCatNameEqualTo(entity.getCatName());
		HaiCategoryWithBLOBs cate = null;
		List<HaiCategoryWithBLOBs> clist = haiCategoryMapper.selectByExampleWithBLOBs(cateExp);
		if(clist == null || clist.size() == 0){
			cate = new HaiCategoryWithBLOBs();
			cate.setCatName(entity.getCatName());
			cate.setStoreId(goods.getStoreId());
			cate.setIsShow(true);
			cate.setKeywords("");
			cate.setSubCatName("");
			cate.setParentId(0);
			cate.setCatDesc("");
			cate.setSortOrder(Short.valueOf("0"));
			cate.setTemplateFile("");
			cate.setMeasureUnit("");
			cate.setShowInNav(false);
			cate.setStyle("");
			cate.setGrade(Short.valueOf("0"));
			cate.setFilterAttr("");
			cate.setStoreId(store_id);
			haiCategoryMapper.insertSelective(cate);
		}else{
			cate = clist.get(0);
		}
		
		HaiGoodsExample gExp = new HaiGoodsExample();
		gExp.createCriteria()
		.andCatIdEqualTo(cate.getCatId())
		.andStoreIdEqualTo(store_id)
		.andGoodsNameEqualTo(goods.getGoodsName())
		.andGoodsUrlEqualTo(goods.getGoodsUrl());
		
		
		goods.setStoreId(store_id);
		goods.setCatId(cate.getCatId());
		
		Date date = new Date();
		if(goods.getGoodsSn() == null)goods.setGoodsSn("");
		if(goods.getGoodsName() == null)goods.setGoodsName("");
		if(goods.getGoodsNameStyle() == null)goods.setGoodsNameStyle("");
		if(goods.getClickCount() == null)goods.setClickCount(0);
		if(goods.getBrandId() == null)goods.setBrandId(0);
		if(goods.getProviderName() == null)goods.setProviderName("");
		if(goods.getGoodsNumber() == null)goods.setGoodsNumber(0);
		if(goods.getGoodsWeight() == null)goods.setGoodsWeight(0);
		if(goods.getCostPrice() == null)goods.setCostPrice(0);
		if(goods.getPromotePrice() == null)goods.setPromotePrice(0);
		if(goods.getPromoteStartDate() == null)goods.setPromoteStartDate(date);
		if(goods.getPromoteEndDate() == null)goods.setPromoteEndDate(date);
		if(goods.getWarnNumber() == null)goods.setWarnNumber(0);
		if(goods.getKeywords() == null)goods.setKeywords("");
		if(goods.getGoodsBrief() == null)goods.setGoodsBrief("");
		if(goods.getGoodsDesc() == null)goods.setGoodsDesc("");
		if(goods.getActDesc() == null)goods.setActDesc("");
		goods.setActDesc(goods.getActDesc().length() > 100 ? goods.getActDesc().substring(0, 100) : goods.getActDesc());
		if(goods.getAppDesc() == null)goods.setAppDesc("");
		if(goods.getGoodsThumb() == null)goods.setGoodsThumb("");
		if(goods.getGoodsImg() == null)goods.setGoodsImg("");
		if(goods.getOriginalImg() == null)goods.setOriginalImg("");
		if(goods.getIsReal() == null)goods.setIsReal(Byte.valueOf("0"));
		if(goods.getExtensionCode() == null)goods.setExtensionCode("");
//		if(goods.getIsOnSale() == null)
		goods.setIsOnSale(true);
		if(goods.getIsAloneSale() == null)goods.setIsAloneSale(false);
		if(goods.getIsShipping() == null)goods.setIsShipping(false);
		if(goods.getIntegral() == null)goods.setIntegral(0);
		if(goods.getAddTime() == null)goods.setAddTime(Long.valueOf(date.getTime() / 1000).intValue());
		if(goods.getSortOrder() == null)goods.setSortOrder(0);
		if(goods.getIsDelete() == null)goods.setIsDelete(false);
		if(goods.getIsBest() == null)goods.setIsBest(false);
		if(goods.getIsNew() == null)goods.setIsNew(false);
		if(goods.getIsHot() == null)goods.setIsHot(false);
		if(goods.getIsPromote() == null)goods.setIsPromote(false);
		if(goods.getIsSpecial() == null)goods.setIsSpecial(false);
		if(goods.getBonusTypeId() == null)goods.setBonusTypeId(Byte.valueOf("0"));
		if(goods.getLastUpdate() == null)goods.setLastUpdate(Long.valueOf(date.getTime() / 1000).intValue());
		if(goods.getGoodsType() == null)goods.setGoodsType(0);
		if(goods.getSellerNote() == null)goods.setSellerNote("");
		if(goods.getGiveIntegral() == null)goods.setGiveIntegral(0);
		if(goods.getRankIntegral() == null)goods.setRankIntegral(0);
		if(goods.getBusinessId() == null)goods.setBusinessId(0);
		if(goods.getIsCheck() == null)goods.setIsCheck(false);
		if(goods.getTbIid() == null)goods.setTbIid("");
		if(goods.getTbData() == null)goods.setTbData("");
		if(goods.getIsExport() == null)goods.setIsExport(false);
		if(goods.getUserId() == null)goods.setUserId(0L);
		if(goods.getExchangerate() == null)goods.setExchangerate(BigDecimal.valueOf(0));
		if(goods.getProfit() == null)goods.setProfit(BigDecimal.valueOf(0));
		if(goods.getShipping() == null)goods.setShipping(BigDecimal.valueOf(0));
		if(goods.getProfittype() == null)goods.setProfittype(0);
		if(goods.getCurrency() == null)goods.setCurrency("");
		if(goods.getGoodsUrl() == null)goods.setGoodsUrl("");
		if(goods.getTabbox() == null)goods.setTabbox(0);
		if(goods.getCreateDate() == null)goods.setCreateDate(date);
		if(goods.getUpdateDate() == null)goods.setUpdateDate(date);
		
		
		//获取当前的属性值并加入map方便取值
		HaiAttributeExample attrExp = new HaiAttributeExample();
		attrExp.createCriteria().andStoreIdEqualTo(store_id);
		Map<String, Integer> attrMap = new HashMap<String, Integer>();
		List<HaiAttribute> attrList = haiAttributeMapper.selectByExample(attrExp);
		for (HaiAttribute haiAttribute : attrList) {
			attrMap.put(haiAttribute.getAttrName(), haiAttribute.getAttrId());
		}
		
		
		List<HaiGoodsWithBLOBs> glist = haiGoodsMapper.selectByExampleWithBLOBs(gExp);

		if(glist == null || glist.size() == 0){	///////////////////////////////////插入商品		
			haiGoodsMapper.insertSelective(goods);
			/////////////////////////////////插入相册
			for (HaiGoodsGallery g : gallery) {
				g.setGoodsId(goods.getGoodsId());
				g.setStoreId(store_id);
				g.setTableName("hai_goods_gallery");
				haiGoodsGalleryMapper.insertSelective(g);
			}
			
			
			/////////////////////插入商品属性
			for (HaiGoodsAttr ga : goodsAttr) {
				ga.setGoodsId(goods.getGoodsId());
				if(attrMap.get(ga.getAttrName())==null){
					attrMap.put(ga.getAttrName(), this.insertGoodsAttr(ga, store_id).getAttrId());
				}
				ga.setAttrId(attrMap.get(ga.getAttrName()));
				if(StringUtils.isBlank(ga.getAttrValue()))ga.setAttrValue("");
				haiGoodsAttrMapper.insertSelective(ga);
			}
			
			
			
		}else{///////////////////////////////////////////////////////更新商品
			
			
			HaiGoodsWithBLOBs g = glist.get(0);
			goods.setGoodsId(g.getGoodsId());
			haiGoodsMapper.updateByPrimaryKeyWithBLOBs(goods);
			
			///////////////////////////////////////////////////////更新相册
			
			HaiGoodsGalleryExample ggExp = new HaiGoodsGalleryExample();
			ggExp.createCriteria().andStoreIdEqualTo(store_id).andGoodsIdEqualTo(goods.getGoodsId());
			List<HaiGoodsGallery> goodsGalleryList = haiGoodsGalleryMapper.selectByExample(ggExp);
			
			//******************找出要添加的相册相片
			boolean f = true;
			for (HaiGoodsGallery gg : gallery) {
				f = true;
				for (HaiGoodsGallery haiGoodsGallery : goodsGalleryList) {
					if(gg.getImgOriginal().equals(haiGoodsGallery.getImgOriginal())){
						f = false;
						break;
					}
				}
				if(f){
					//插入操作
					gg.setGoodsId(goods.getGoodsId());
					gg.setStoreId(store_id);
					gg.setTableName("hai_goods_gallery");
					haiGoodsGalleryMapper.insertSelective(gg);
				}
				
			}
			
			
			//*****************找出要删除的相册相片
			for (HaiGoodsGallery haiGoodsGallery : goodsGalleryList) {
				f = true;
				for (HaiGoodsGallery gg : gallery) {
					if(gg.getImgOriginal().equals(haiGoodsGallery.getImgOriginal())){
						f = false;
						break;
					}
				}
				if(f){
					haiGoodsGalleryMapper.deleteByPrimaryKey(haiGoodsGallery.getImgId());
				}
			}
			
			

			//////////////////////////////////////////////////更新商品属性
			
			HaiGoodsAttrExample gaExp = new HaiGoodsAttrExample();
			gaExp.createCriteria().andGoodsIdEqualTo(goods.getGoodsId());
			List<HaiGoodsAttr> goodsAttrList = haiGoodsAttrMapper.selectByExample(gaExp);
			//*********************插入商品属性
			for (HaiGoodsAttr ga : goodsAttr) {
				f = true;
				for (HaiGoodsAttr haiGoodsAttr : goodsAttrList) {
					if((StringUtils.isNotBlank(ga.getAttrValue()) && ga.getAttrValue().equals(haiGoodsAttr.getAttrValue())) 
							|| (StringUtils.isNotBlank(ga.getAttrPic()) && ga.getAttrPic().equals(haiGoodsAttr.getAttrPic()))
							){
						f = false;
						break;
					}
				}
				if(f){
					//插入操作
					ga.setGoodsId(goods.getGoodsId());
					if(attrMap.get(ga.getAttrName())==null){
						attrMap.put(ga.getAttrName(), this.insertGoodsAttr(ga, store_id).getAttrId());
					}
					ga.setAttrId(attrMap.get(ga.getAttrName()));
					if(StringUtils.isBlank(ga.getAttrValue()))ga.setAttrValue("");
					haiGoodsAttrMapper.insertSelective(ga);
				}
			}
			
			//****************删除商品属性
			for (HaiGoodsAttr haiGoodsAttr : goodsAttrList) {
				f = true;
				for (HaiGoodsAttr ga : goodsAttr) {
					if((StringUtils.isNotBlank(ga.getAttrValue()) && ga.getAttrValue().equals(haiGoodsAttr.getAttrValue())) 
							|| (StringUtils.isNotBlank(ga.getAttrPic()) && ga.getAttrPic().equals(haiGoodsAttr.getAttrPic()))
							){
						f = false;
						break;
					}
				}
				if(f){
					//删除操作
					haiGoodsAttrMapper.deleteByPrimaryKey(haiGoodsAttr.getGoodsAttrId());
				}
			}
			
		}
		
		return "success";
	}
	
	//插入商品属性
	private HaiAttribute insertGoodsAttr(HaiGoodsAttr ga,Integer store_id){
		HaiAttribute haiAttribute = new HaiAttribute();
		haiAttribute.setAttrName(ga.getAttrName());
		haiAttribute.setStoreId(store_id);
		
		if(haiAttribute.getAttrCode() == null)haiAttribute.setAttrCode("");
		if(haiAttribute.getAttrInputType() == null)haiAttribute.setAttrInputType(false);
		if(haiAttribute.getAttrType() == null)haiAttribute.setAttrType(false);
		if(haiAttribute.getAttrValues() == null)haiAttribute.setAttrValues("");
		if(haiAttribute.getAttrIndex() == null)haiAttribute.setAttrIndex(false);
		if(haiAttribute.getSortOrder() == null)haiAttribute.setSortOrder(Byte.valueOf("0"));
		if(haiAttribute.getIsLinked() == null)haiAttribute.setIsLinked(false);
		if(haiAttribute.getAttrGroup() == null)haiAttribute.setAttrGroup(false);
		
		haiAttributeMapper.insertSelective(haiAttribute);
		return haiAttribute;
	}
			
}
