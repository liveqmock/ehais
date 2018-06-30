package org.ehais.shop.model;

public class HaiOrderGoodsExtends extends HaiOrderGoods{

	private static final long serialVersionUID = 5261070316372660492L;
	
	private String goodsCode;
	private String goodsName;
	private String unitCode;
	private String unitName;
	private Integer saleAmount;


	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public Integer getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(Integer saleAmount) {
		this.saleAmount = saleAmount;
	}
	
}
