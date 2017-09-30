package org.ehais.epublic.model;

import java.io.Serializable;

public class EHaiArticleCatSimple implements Serializable {

    private Integer catId;
    private String catName;
    private Integer parentId;

    private static final long serialVersionUID = 1L;

	public Integer getCatId() {
		return catId;
	}

	public void setCatId(Integer catId) {
		this.catId = catId;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

    
}