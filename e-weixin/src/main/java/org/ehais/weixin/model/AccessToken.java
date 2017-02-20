package org.ehais.weixin.model;

public class AccessToken {
	//用于区别哪一个用户的编号
	private int id;
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;
	//凭证到期时间戳
	private Long expire_time;
	
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
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
