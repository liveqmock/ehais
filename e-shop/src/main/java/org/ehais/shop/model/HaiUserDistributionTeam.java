package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

import org.ehais.epublic.model.EHaiUsers;

public class HaiUserDistributionTeam extends HaiUsersRelation implements Serializable{

	private String userName;
	private String nickname;
	private String faceImage;
	private Date regTime;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4555172619698321186L;

	
	//从会员关系表转
	public void byUsersRelation(HaiUsersRelation u){
		this.setUserId(u.getUserId());
		this.setFirstUserId(u.getFirstUserId());
		this.setSecondUserId(u.getSecondUserId());
		this.setThirdUserId(u.getThirdUserId());
	}
	
	//从会员表转过
	public void byUsersModel(EHaiUsers u){
		this.setUserName(u.getUserName());
		this.setNickname(u.getNickname());
		this.setFaceImage(u.getFaceImage());
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getFaceImage() {
		return faceImage;
	}


	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	
	
	
	
	
}
