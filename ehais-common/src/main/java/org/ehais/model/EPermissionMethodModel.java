package org.ehais.model;

public class EPermissionMethodModel {

	private String name;
	private String value;
	private String type;
	private String intro;
	private String relation;
	public EPermissionMethodModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EPermissionMethodModel(String name, String value, String type, String intro, String relation) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.intro = intro;
		this.relation = relation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
    
}
