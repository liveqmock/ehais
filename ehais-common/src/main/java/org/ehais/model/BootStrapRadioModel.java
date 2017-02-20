package org.ehais.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("radio")
public class BootStrapRadioModel {

	private String key;
	private String value;
	public BootStrapRadioModel(String key, String value) {
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
