package org.ehais.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("checkbox")
public class BootStrapCheckBoxModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2986692407110537298L;
	private String key;
	private String value;
	public BootStrapCheckBoxModel(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
