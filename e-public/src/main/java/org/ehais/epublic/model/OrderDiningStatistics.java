package org.ehais.epublic.model;

import java.io.Serializable;

public class OrderDiningStatistics implements Serializable{

	private static final long serialVersionUID = 3315482082118917141L;
	private Integer weixinAmount;
	private Integer cashAmount;
	private Integer payTime;
	public Integer getWeixinAmount() {
		return weixinAmount;
	}
	public void setWeixinAmount(Integer weixinAmount) {
		this.weixinAmount = weixinAmount;
	}
	public Integer getCashAmount() {
		return cashAmount;
	}
	public void setCashAmount(Integer cashAmount) {
		this.cashAmount = cashAmount;
	}
	public Integer getPayTime() {
		return payTime;
	}
	public void setPayTime(Integer payTime) {
		this.payTime = payTime;
	}
	
	
	
}
