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
 * ThinkMenu generated by hbm2java
 */
@Entity
@Table(name = "think_menu", catalog = "tracking")
public class ThinkMenu implements java.io.Serializable {

	private Integer id;
	private int parentId;
	private String childIds;
	private String title;
	private String position;
	private String target;
	private String url;
	private String app;
	private String module;
	private String action;
	private int nodeId;
	private String accessNode;
	private byte groupId;
	private int userId;
	private String roleId;
	private short listorder;
	private byte status;

	public ThinkMenu() {
	}

	public ThinkMenu(int parentId, String childIds, String title,
			String position, String target, String url, String app,
			String module, String action, int nodeId, String accessNode,
			byte groupId, int userId, String roleId, short listorder,
			byte status) {
		this.parentId = parentId;
		this.childIds = childIds;
		this.title = title;
		this.position = position;
		this.target = target;
		this.url = url;
		this.app = app;
		this.module = module;
		this.action = action;
		this.nodeId = nodeId;
		this.accessNode = accessNode;
		this.groupId = groupId;
		this.userId = userId;
		this.roleId = roleId;
		this.listorder = listorder;
		this.status = status;
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

	@Column(name = "parent_id", nullable = false)
	public int getParentId() {
		return this.parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Column(name = "child_ids", nullable = false, length = 128)
	public String getChildIds() {
		return this.childIds;
	}

	public void setChildIds(String childIds) {
		this.childIds = childIds;
	}

	@Column(name = "title", nullable = false, length = 12)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "position", nullable = false, length = 8)
	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "target", nullable = false, length = 10)
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Column(name = "url", nullable = false, length = 512)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "app", nullable = false, length = 24)
	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Column(name = "module", nullable = false, length = 24)
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name = "action", nullable = false, length = 24)
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "node_id", nullable = false)
	public int getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "access_node", nullable = false, length = 128)
	public String getAccessNode() {
		return this.accessNode;
	}

	public void setAccessNode(String accessNode) {
		this.accessNode = accessNode;
	}

	@Column(name = "group_id", nullable = false)
	public byte getGroupId() {
		return this.groupId;
	}

	public void setGroupId(byte groupId) {
		this.groupId = groupId;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "role_id", nullable = false, length = 64)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "listorder", nullable = false)
	public short getListorder() {
		return this.listorder;
	}

	public void setListorder(short listorder) {
		this.listorder = listorder;
	}

	@Column(name = "status", nullable = false)
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

}
