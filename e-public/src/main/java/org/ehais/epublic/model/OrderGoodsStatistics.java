package org.ehais.epublic.model;

import java.io.Serializable;

public class OrderGoodsStatistics implements Serializable{

	private static final long serialVersionUID = 3315482082118911111L;
	private Integer quantity;
	private Long goodsId;
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	
	
	
	
}
