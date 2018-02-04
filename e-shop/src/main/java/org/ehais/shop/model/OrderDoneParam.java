package org.ehais.shop.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.ehais.model.eApi.ApiNormalParam;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderDoneParam  extends ApiNormalParam implements Serializable{

	private static final long serialVersionUID = 1811491133411842011L;

	private Long address_id;
	private Integer pay_id;
	private Integer shipping_id;
	private String postscript;//订单附言,由用户提交订单前填写 
	private String invPayee;//发票抬头,用户页面填写
	private String invContent;//发票内容,用户页面选择
	private String orderSn;
	
	@NotNull
	@NotEmpty
	private String rec_id_data;
	
	
	public Long getAddress_id() {
		return address_id;
	}
	public void setAddress_id(Long address_id) {
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
	public String getPostscript() {
		return postscript;
	}
	public void setPostscript(String postscript) {
		this.postscript = postscript;
	}
	public String getInvPayee() {
		return invPayee;
	}
	public void setInvPayee(String invPayee) {
		this.invPayee = invPayee;
	}
	public String getInvContent() {
		return invContent;
	}
	public void setInvContent(String invContent) {
		this.invContent = invContent;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
	
	
}
