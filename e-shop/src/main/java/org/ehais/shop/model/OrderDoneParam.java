package org.ehais.shop.model;

import java.io.Serializable;

public class OrderDoneParam implements Serializable{

	private static final long serialVersionUID = 1811491133411842011L;

	private Integer user_id;
	private Integer address_id;
	private Integer pay_id;
	private Integer shipping_id;
	private String rec_id_data;
	
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getAddress_id() {
		return address_id;
	}
	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}
	public Integer getPay_id() {
		return pay_id;
	}
	public void setPay_id(Integer pay_id) {
		this.pay_id = pay_id;
	}
	public Integer getShipping_id() {
		return shipping_id;
	}
	public void setShipping_id(Integer shipping_id) {
		this.shipping_id = shipping_id;
	}
	public String getRec_id_data() {
		return rec_id_data;
	}
	public void setRec_id_data(String rec_id_data) {
		this.rec_id_data = rec_id_data;
	}
	
	
	
}
