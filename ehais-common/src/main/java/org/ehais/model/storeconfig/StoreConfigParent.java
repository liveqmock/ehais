package org.ehais.model.storeconfig;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("parent") 
public class StoreConfigParent {
	@XStreamAsAttribute 
	private String code;
	@XStreamAsAttribute 
	private String text;
	@XStreamAsAttribute 
	private String sort;
	@XStreamAsAttribute 
	private String type;
	@XStreamAsAttribute 
	private String isvoid;
	@XStreamAsAttribute 
	private String role;
	
	private List<StoreConfigSub> item;

	public StoreConfigParent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StoreConfigParent(String code, String text, String type, String sort,
			 String isvoid,String role, List<StoreConfigSub> item) {
		super();
		this.code = code;
		this.text = text;
		this.sort = sort;
		this.type = type;
		this.isvoid = isvoid;
		this.role = role;
		this.item = item;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<StoreConfigSub> getItem() {
		return item;
	}

	public void setItem(List<StoreConfigSub> item) {
		this.item = item;
	}

	public String getIsvoid() {
		return isvoid;
	}

	public void setIsvoid(String isvoid) {
		this.isvoid = isvoid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
