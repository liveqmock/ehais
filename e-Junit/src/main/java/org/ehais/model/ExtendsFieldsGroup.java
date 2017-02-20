package org.ehais.model;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("group")
public class ExtendsFieldsGroup {
	
	@XStreamAsAttribute()
	@XStreamAlias("code")
	private String code;
	@XStreamAsAttribute()
	@XStreamAlias("table")
	private String table;
	
	@XStreamImplicit// 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
	private List<BootStrapModel> field;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<BootStrapModel> getField() {
		return field;
	}

	public void setField(List<BootStrapModel> field) {
		this.field = field;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	
	
	
	
}
