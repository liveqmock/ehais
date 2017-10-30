package org.ehais.model;

import java.util.List;

public class EPermissionControllerModel {

	private String className;
	private String name;
	private String value;
	private String type;
	private String intro;
	private List<EPermissionMethodModel> listMethod;
	
	public EPermissionControllerModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EPermissionControllerModel(String name, String value, String type, String intro) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
		this.intro = intro;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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

	public List<EPermissionMethodModel> getListMethod() {
		return listMethod;
	}

	public void setListMethod(List<EPermissionMethodModel> listMethod) {
		this.listMethod = listMethod;
	}
	
	
}
