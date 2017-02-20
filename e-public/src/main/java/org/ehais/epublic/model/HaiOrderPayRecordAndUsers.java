package org.ehais.epublic.model;

import java.util.Date;

public class HaiOrderPayRecordAndUsers extends HaiOrderPayRecord{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long orderPayExtendsId;
	private String userName;
	private String nickName;
	private String mobile;
	private String realname;
	private String openid;
	private String faceImage;
	private String guestbook;
	private Integer anonymous;
	private String tableNameValue;
    private String remark;
    private String orderBody;
	
	public String getFaceImage() {
		return faceImage;
	}
	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}
	public String getGuestbook() {
		return guestbook;
	}
	public void setGuestbook(String guestbook) {
		this.guestbook = guestbook;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getOrderPayExtendsId() {
		return orderPayExtendsId;
	}
	public void setOrderPayExtendsId(Long orderPayExtendsId) {
		this.orderPayExtendsId = orderPayExtendsId;
	}
	public Integer getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(Integer anonymous) {
		this.anonymous = anonymous;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getTableNameValue() {
		return tableNameValue;
	}
	public void setTableNameValue(String tableNameValue) {
		this.tableNameValue = tableNameValue;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderBody() {
		return orderBody;
	}
	public void setOrderBody(String orderBody) {
		this.orderBody = orderBody;
	}
	
	
	
}
