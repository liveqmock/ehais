package org.ehais.weixin.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
public class WeiXinArticlesItem {

	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	public WeiXinArticlesItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WeiXinArticlesItem(String title, String description, String picUrl,
			String url) {
		super();
		Title = title;
		Description = description;
		PicUrl = picUrl;
		Url = url;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
