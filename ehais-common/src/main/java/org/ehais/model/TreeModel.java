package org.ehais.model;

import java.io.Serializable;

public class TreeModel implements Serializable{

	private static final long serialVersionUID = -1719470479981558801L;
	private Integer id ;
	private String title;
	private Integer parent_id;
	private Integer level;
	private String code;
	private String extend;
	private String module;
	private String classify;
	
	public TreeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TreeModel(Integer id,String code, String title, Integer parent_id) {
		super();
		this.id = id;
		this.title = title;
		this.parent_id = parent_id;
		this.code = code;
	}
	public TreeModel(Integer id, String title, Integer parent_id,String module,String classify) {
		super();
		this.id = id;
		this.title = title;
		this.parent_id = parent_id;
		this.module = module;
		this.classify = classify;
	}
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
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getClassify() {
		return classify;
	}
	public void setClassify(String classify) {
		this.classify = classify;
	}
	
	
}
