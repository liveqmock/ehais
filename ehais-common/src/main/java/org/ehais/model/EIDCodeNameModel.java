package org.ehais.model;

import java.io.Serializable;

/**
 * 适用简码控件
 * @author Administrator
 *
 */
public class EIDCodeNameModel implements Serializable {

	private static final long serialVersionUID = -7218760067987578679L;
	
	private String id;
	private String code;
	private String name;
	private Integer store_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStore_id() {
		return store_id;
	}
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	
	
	
}
