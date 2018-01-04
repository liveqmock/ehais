package org.ehais.model;

import java.util.List;

public class HaiGoodsEntity {
	private String catName;
	private String categoryUrl;
	private String storeId;
	private HaiGoodsWithBLOBs goods;
	private List<HaiGoodsAttr> goodsAttrList;
	private List<HaiGoodsGallery> goodsGalleryList;
	public HaiGoodsWithBLOBs getGoods() {
		return goods;
	}
	public void setGoods(HaiGoodsWithBLOBs goods) {
		this.goods = goods;
	}
	public List<HaiGoodsAttr> getGoodsAttrList() {
		return goodsAttrList;
	}
	public void setGoodsAttrList(List<HaiGoodsAttr> goodsAttrList) {
		this.goodsAttrList = goodsAttrList;
	}
	public List<HaiGoodsGallery> getGoodsGalleryList() {
		return goodsGalleryList;
	}
	public void setGoodsGalleryList(List<HaiGoodsGallery> goodsGalleryList) {
		this.goodsGalleryList = goodsGalleryList;
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getCategoryUrl() {
		return categoryUrl;
	}
	public void setCategoryUrl(String categoryUrl) {
		this.categoryUrl = categoryUrl;
	}
	
	
}
