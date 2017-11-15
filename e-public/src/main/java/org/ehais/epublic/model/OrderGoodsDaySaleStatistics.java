package org.ehais.epublic.model;

import java.io.Serializable;
import java.util.Date;

public class OrderGoodsDaySaleStatistics implements Serializable{

	private static final long serialVersionUID = 3315482082118921111L;
	private Integer quantity;
	private Date saleDate;
	
	
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	
	
	
	
	
}
