package org.ehais.epublic.model.alipay;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AlipayNotifyModel implements Serializable{

	private static final long serialVersionUID = 7104643322197360534L;
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date notify_time;
	private String notify_type;
	private String notify_id;
	private String sign_type;
	private String sign;
	private String out_trade_no ;
	private String trade_no;
	private String subject;
	private Double total_fee;
	private String payment_type;
	private String body;
	private String trade_status;
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date gmt_create;
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date gmt_payment;
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date gmt_close;
	private String seller_email;
	private String buyer_email;
	private String seller_id;
	private String buyer_id;
	private Double price;
	private Integer quantity;
	private Float discount;
	private String is_total_fee_adjus ;
	private String use_coupon;
	public AlipayNotifyModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AlipayNotifyModel(Date notify_time, String notify_type, String notify_id, String sign_type, String sign,
			String out_trade_no, String trade_no, String subject, Double total_fee, String payment_type, String body,
			String trade_status, Date gmt_create, Date gmt_payment, Date gmt_close, String seller_email,
			String buyer_email, String seller_id, String buyer_id, Double price, Integer quantity, Float discount,
			String is_total_fee_adjus, String use_coupon) {
		super();
		this.notify_time = notify_time;
		this.notify_type = notify_type;
		this.notify_id = notify_id;
		this.sign_type = sign_type;
		this.sign = sign;
		this.out_trade_no = out_trade_no;
		this.trade_no = trade_no;
		this.subject = subject;
		this.total_fee = total_fee;
		this.payment_type = payment_type;
		this.body = body;
		this.trade_status = trade_status;
		this.gmt_create = gmt_create;
		this.gmt_payment = gmt_payment;
		this.gmt_close = gmt_close;
		this.seller_email = seller_email;
		this.buyer_email = buyer_email;
		this.seller_id = seller_id;
		this.buyer_id = buyer_id;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
		this.is_total_fee_adjus = is_total_fee_adjus;
		this.use_coupon = use_coupon;
	}
	public Date getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(Date notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public Date getGmt_payment() {
		return gmt_payment;
	}
	public void setGmt_payment(Date gmt_payment) {
		this.gmt_payment = gmt_payment;
	}
	public Date getGmt_close() {
		return gmt_close;
	}
	public void setGmt_close(Date gmt_close) {
		this.gmt_close = gmt_close;
	}
	public String getSeller_email() {
		return seller_email;
	}
	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}
	public String getBuyer_email() {
		return buyer_email;
	}
	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public String getIs_total_fee_adjus() {
		return is_total_fee_adjus;
	}
	public void setIs_total_fee_adjus(String is_total_fee_adjus) {
		this.is_total_fee_adjus = is_total_fee_adjus;
	}
	public String getUse_coupon() {
		return use_coupon;
	}
	public void setUse_coupon(String use_coupon) {
		this.use_coupon = use_coupon;
	}
	
	
}
