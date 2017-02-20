package org.ehais.weixin.model;

public class WeiXinMenu {

	private String type;
	private String name;
	private String key;
	private String url;
	private String media_id;
	
	
	
	public WeiXinMenu(String type, String name, String key, String url,
			String media_id) {
		super();
		this.type = type;
		this.name = name;
		this.key = key;
		this.url = url;
		this.media_id = media_id;
	}
	public WeiXinMenu() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	
	
}
