package org.ehais.project.model;

import java.util.Map;

public class ApiModel {
	
	private String appkey;
	private String secret;
	private String ver;
	private String timestamp;
	private String sign;
	
	public void toMap(Map<String,Object> map){
		map.put("appkey", appkey);		
		map.put("ver", ver);
		map.put("timestamp", timestamp);
	}
	
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	


}
