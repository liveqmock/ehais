package org.ehais.model.role;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("action")
public class RoleMethod {
	
	
	private String action;//函数名
	private String actionUrl;//请求地址
	private String actionCode;//函数编码
	private String actionName;//显示的中文名称
	private String actionDesc;//描述
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionUrl() {
		return actionUrl;
	}
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}
	
	
	
	
}
