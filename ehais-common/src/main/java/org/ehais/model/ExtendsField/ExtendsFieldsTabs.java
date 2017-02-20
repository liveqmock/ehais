package org.ehais.model.ExtendsField;

import java.util.List;

import org.ehais.model.BootStrapModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("tab")
public class ExtendsFieldsTabs {
	
	@XStreamAsAttribute()
	@XStreamAlias("id")
	private String id;//唯一编号
	
	@XStreamAsAttribute()
	@XStreamAlias("title")
	private String title;
	
	
	@XStreamAsAttribute()
	@XStreamAlias("active")
	private String active;
	
	
	@XStreamImplicit// 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
	private List<BootStrapModel> field;


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


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}


	public List<BootStrapModel> getField() {
		return field;
	}


	public void setField(List<BootStrapModel> field) {
		this.field = field;
	}

	

	
	
}
