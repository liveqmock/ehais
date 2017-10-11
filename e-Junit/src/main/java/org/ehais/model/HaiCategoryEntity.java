package org.ehais.model;

import java.util.List;

public class HaiCategoryEntity extends HaiCategoryWithBLOBs{

	/**
	 * 
	 */
	private static final long serialVersionUID = 109788L;
	private List<HaiCategoryEntity> children;
	public List<HaiCategoryEntity> getChildren() {
		return children;
	}
	public void setChildren(List<HaiCategoryEntity> children) {
		this.children = children;
	}
	
	
	
}
