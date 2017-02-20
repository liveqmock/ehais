package org.ehais.model.role;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("group")
public class RoleController {
	
	@XStreamAlias("controller")
	private String controller;
	
	@XStreamAlias("controllerName")
	private String controllerName;
	
	@XStreamImplicit
	@XStreamAlias("roleAction")
	private List<RoleMethod> roleAction;
	
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public List<RoleMethod> getRoleAction() {
		return roleAction;
	}
	public void setRoleAction(List<RoleMethod> roleAction) {
		this.roleAction = roleAction;
	}
	
	
	
}
