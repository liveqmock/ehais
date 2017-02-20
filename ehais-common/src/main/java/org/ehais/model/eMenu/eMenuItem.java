package org.ehais.model.eMenu;

public class eMenuItem {
	private String id;
	private String title;
	private String className;
	private String url;
	private String module;
	private String iconClassName;
	private String[] roles;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public String getIconClassName() {
		return iconClassName;
	}
	public void setIconClassName(String iconClassName) {
		this.iconClassName = iconClassName;
	}
	
	
	
}
