package org.ehais.tools;

import java.util.List;

public class CriteriaObject {

	private String operator ;//eq in like
	private Integer storeId;
	private Long userId;
	private String role_type;
	private List<Integer> storeList;
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getStoreId() {
		return storeId;
	}
	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	public List<Integer> getStoreList() {
		return storeList;
	}
	public void setStoreList(List<Integer> storeList) {
		this.storeList = storeList;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRoleType() {
		return role_type;
	}
	public void setRoleType(String role_type) {
		this.role_type = role_type;
	}
	
}
