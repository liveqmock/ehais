package org.ehais.model.role;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("group")
public class RolePerson {
	@XStreamAsAttribute()
	private String RolePersonCode;
	@XStreamAsAttribute()
	private String RolePersonName;
	
	private List<String> actionCode;
	
	public String getRolePersonCode() {
		return RolePersonCode;
	}
	public void setRolePersonCode(String rolePersonCode) {
		RolePersonCode = rolePersonCode;
	}
	public String getRolePersonName() {
		return RolePersonName;
	}
	public void setRolePersonName(String rolePersonName) {
		RolePersonName = rolePersonName;
	}
	public List<String> getActionCode() {
		return actionCode;
	}
	public void setActionCode(List<String> actionCode) {
		this.actionCode = actionCode;
	}
	
	
	
}
