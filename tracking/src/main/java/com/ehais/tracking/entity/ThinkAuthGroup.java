package com.ehais.tracking.entity;
// default package
// Generated 2016-3-22 19:54:34 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ThinkAuthGroup generated by hbm2java
 */
@Entity
@Table(name = "think_auth_group", catalog = "tracking")
public class ThinkAuthGroup implements java.io.Serializable {

	private Integer id;
	private String title;
	private boolean status;
	private String rules;

	public ThinkAuthGroup() {
	}

	public ThinkAuthGroup(String title, boolean status, String rules) {
		this.title = title;
		this.status = status;
		this.rules = rules;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "title", nullable = false, length = 100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "status", nullable = false)
	public boolean isStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name = "rules", nullable = false, length = 80)
	public String getRules() {
		return this.rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

}
