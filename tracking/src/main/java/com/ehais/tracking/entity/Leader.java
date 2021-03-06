package com.ehais.tracking.entity;
// default package
// Generated 2016-3-24 22:59:07 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Leader generated by hbm2java
 */
@Entity
@Table(name = "leader", catalog = "tracking")
public class Leader implements java.io.Serializable {

	private Integer leaderId;
	private String leaderName;
	private String password;
	private String mobile;
	private String email;
	private String address;

	public Leader() {
	}

	public Leader(String leaderName, String password, String mobile,
			String email, String address) {
		this.leaderName = leaderName;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
		this.address = address;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "leader_id", unique = true, nullable = false)
	public Integer getLeaderId() {
		return this.leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	@Column(name = "leader_name", length = 20)
	public String getLeaderName() {
		return this.leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Column(name = "password", length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "mobile", length = 15)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email", length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
