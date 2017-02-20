package org.ehais.weixin.model;

public class Wecome {

	private Integer type;
	private String description;
	private Integer appmsg_id;
	public Wecome(Integer type, String description, Integer appmsg_id) {
		super();
		this.type = type;
		this.description = description;
		this.appmsg_id = appmsg_id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAppmsg_id() {
		return appmsg_id;
	}
	public void setAppmsg_id(Integer appmsg_id) {
		this.appmsg_id = appmsg_id;
	}
}
