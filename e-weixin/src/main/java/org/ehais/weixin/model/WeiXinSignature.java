package org.ehais.weixin.model;

import java.io.Serializable;

public class WeiXinSignature implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1222335435L;
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	private String url;
	
	private String title;// 分享标题
	private String link;// 分享链接
	private String imgUrl;// 分享图标
	private String desc;// 分享描述
	private String type;// 分享类型,music、video或link，不填默认为link
	private String dataUrl;// 如果type是music或video，则要提供数据链接，默认为空
	


	public String getAppId() {
		return appId;
	}


	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}


	public String getNonceStr() {
		return nonceStr;
	}


	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDataUrl() {
		return dataUrl;
	}


	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	
	
}
