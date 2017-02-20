package org.ehais.epublic.model.alipay;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AlipayReturnModel implements Serializable{

	private static final long serialVersionUID = 7934146447704152420L;

	private String is_success;
	private String sign_type;
	private String sign;
	private String partnerId;
	private String charset;
	private String out_trade_no;
	private String trade_no;
	private String subject;
	private Double total_fee;
	private String exterface;
	private String payment_type;
	private String body;
	private String trade_status;
	private String notify_id;	
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date notify_time;
	private String notify_type;
	private String seller_email;
	private String buyer_email;
	private String seller_id;
	private String buyer_id;
	private String bank_seq_no;
	
	public AlipayReturnModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AlipayReturnModel(String is_success, String sign_type, String sign, String partnerId, String charset,
			String out_trade_no, String trade_no, String subject, Double total_fee, String exterface,
			String payment_type, String body, String trade_status, String notify_id, Date notify_time,
			String notify_type, String seller_email, String buyer_email, String seller_id, String buyer_id,
			String bank_seq_no) {
		super();
		this.is_success = is_success;
		this.sign_type = sign_type;
		this.sign = sign;
		this.partnerId = partnerId;
		this.charset = charset;
		this.out_trade_no = out_trade_no;
		this.trade_no = trade_no;
		this.subject = subject;
		this.total_fee = total_fee;
		this.exterface = exterface;
		this.payment_type = payment_type;
		this.body = body;
		this.trade_status = trade_status;
		this.notify_id = notify_id;
		this.notify_time = notify_time;
		this.notify_type = notify_type;
		this.seller_email = seller_email;
		this.buyer_email = buyer_email;
		this.seller_id = seller_id;
		this.buyer_id = buyer_id;
		this.bank_seq_no = bank_seq_no;
	}
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
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
	public String getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
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
	public String getExterface() {
		return exterface;
	}
	public void setExterface(String exterface) {
		this.exterface = exterface;
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
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
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
	public String getBank_seq_no() {
		return bank_seq_no;
	}
	public void setBank_seq_no(String bank_seq_no) {
		this.bank_seq_no = bank_seq_no;
	}
	
	
	
}
