package org.ehais.epublic.model;

import java.io.Serializable;
import java.util.Date;

public class OrderDiningStatistics implements Serializable{

	private static final long serialVersionUID = 3315482082118917141L;
	private Integer weixinAmount;
	private Integer cashAmount;
	private Date payTime;
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
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	
	
}
