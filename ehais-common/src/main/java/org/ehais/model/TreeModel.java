package org.ehais.model;

public class TreeModel {

	private Integer id ;
	private String title;
	private Integer parent_id;
	private Integer level;
	private String code;
	private String extend;
	public TreeModel(Integer id, String title, Integer parent_id,
			Integer level, String code, String extend) {
		super();
		this.id = id;
		this.title = title;
		this.parent_id = parent_id;
		this.level = level;
		this.extend = extend;
		this.code = code;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getParent_id() {
		return parent_id;
	}
	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
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
	
	
}
