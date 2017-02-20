package org.ehais.model.eMenu;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Parent")
public class eParent {
	private String id;
	private String title;
	private String className;
	private String url;
	private String module;
	private String iconClassName;
	private String[] roles;
	private List<eMenuItem> MenuItems;
	
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
	public String[] getRoles() {
		return roles;
	}
	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	public List<eMenuItem> getMenuItems() {
		return MenuItems;
	}
	public void setMenuItems(List<eMenuItem> menuItems) {
		MenuItems = menuItems;
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
	public String getIconClassName() {
		return iconClassName;
	}
	public void setIconClassName(String iconClassName) {
		this.iconClassName = iconClassName;
	}

	
}
