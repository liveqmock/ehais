package com.ehais.tracking.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users", catalog = "tracking")
public class Users implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -352212026872199994L;
	private Integer id;
	private String login_name;
	private String login_pwd;
	private Integer status;
	private Integer user_type;
	private Integer user_type_id;
	private Date create_time;
	private Integer role_id;
	private String lastLoginIP;
	private Integer loginNumber;
	private Date lastLoginTime;
	private String openid;
	

	
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Users(Integer id, String login_name, String login_pwd, Integer status, Integer user_type,
			Integer user_type_id, Date create_time, Integer role_id, String lastLoginIP, Integer loginNumber,
			Date lastLoginTime, String openid) {
		super();
		this.id = id;
		this.login_name = login_name;
		this.login_pwd = login_pwd;
		this.status = status;
		this.user_type = user_type;
		this.user_type_id = user_type_id;
		this.create_time = create_time;
		this.role_id = role_id;
		this.lastLoginIP = lastLoginIP;
		this.loginNumber = loginNumber;
		this.lastLoginTime = lastLoginTime;
		this.openid = openid;
	}
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "login_name", length = 50)
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	@Column(name = "login_pwd", length = 64)
	public String getLogin_pwd() {
		return login_pwd;
	}
	public void setLogin_pwd(String login_pwd) {
		this.login_pwd = login_pwd;
	}
	
	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "user_type")
	public Integer getUser_type() {
		return user_type;
	}
	public void setUser_type(Integer user_type) {
		this.user_type = user_type;
	}
	@Column(name = "user_type_id")
	public Integer getUser_type_id() {
		return user_type_id;
	}
	public void setUser_type_id(Integer user_type_id) {
		this.user_type_id = user_type_id;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", length = 19)
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	@Column(name = "role_id")
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
	@Column(name = "lastLoginIP", length = 30)
	public String getLastLoginIP() {
		return lastLoginIP;
	}
	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}
	@Column(name = "loginNumber")
	public Integer getLoginNumber() {
		return loginNumber;
	}
	public void setLoginNumber(Integer loginNumber) {
		this.loginNumber = loginNumber;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastLoginTime", length = 19)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Column(name = "openid",length=200)
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
}
