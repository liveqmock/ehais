package org.ehais.model.ExtendsField;

import java.util.List;

import org.ehais.model.BootStrapModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("group")
public class ExtendsFieldsGroup {
	
	@XStreamAsAttribute()
	@XStreamAlias("id")
	private String id;//唯一编号
	
	//不同的帐号存在不同的角色，通过角色与不同的编码获取自己对应的文章自定义控件框
	@XStreamAsAttribute()
	@XStreamAlias("code")
	private String code;//模板编码，对应文章分类编码
	
	//适用admin_user表登录的用户
	@XStreamAsAttribute()
	@XStreamAlias("role")
	private String role;//角色权限，重新配置
	
	//适用admin表登录用户
	@XStreamAsAttribute()
	@XStreamAlias("manager")
	private String manager;//用户名，如果角色为空，则读取用户名，是指定用户才使用的此自定义字段
	
	@XStreamAsAttribute()
	@XStreamAlias("table")
	private String table;//对应表名
	
	
	@XStreamAsAttribute()
	@XStreamAlias("extend")
	private String extend;//继承上级id
	
	
	@XStreamImplicit
	private List<ExtendsFieldsTabs> tab;

	
	@XStreamImplicit// 隐式集合，加上这个注解可以去掉book集合最外面的<list></list>这样的标记
	private List<BootStrapModel> field;
	

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public List<ExtendsFieldsTabs> getTab() {
		return tab;
	}

	public void setTab(List<ExtendsFieldsTabs> tab) {
		this.tab = tab;
	}


	
	
}
