package org.ehais.weixin.model;

public class JsApiTicket {
	//用于区别哪一个用户的编号
	private int id;
	private Integer errcode;
	private String errmsg;
	private Integer expires_in;
	private String ticket;
	private Long expire_time;//凭证到期时间戳
	public Integer getErrcode() {
		return errcode;
	}
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public Long getExpire_time() {
		return expire_time;
	}
	public void setExpire_time(Long expire_time) {
		this.expire_time = expire_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
