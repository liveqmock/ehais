package org.ehais.weixin.model;

import java.io.Serializable;

public class AccessToken implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4918649896332871797L;
	//用于区别哪一个用户的编号
	private int id;
	// 获取到的凭证
	private String access_token;
	// 凭证有效时间，单位：秒
	private int expires_in;
	//凭证到期时间戳
	private Long expire_time;
	
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
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
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
	
}
