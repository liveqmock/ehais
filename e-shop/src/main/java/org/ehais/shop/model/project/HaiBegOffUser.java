package org.ehais.shop.model.project;

import java.util.Date;

import org.ehais.annotation.ExcelVOAttribute;

public class HaiBegOffUser extends HaiBegOff {
	
	
	private static final long serialVersionUID = 6735850220335484452L;
	
	@ExcelVOAttribute(name = "学号", column = "A", isExport = true) 
	private String username;	
	@ExcelVOAttribute(name = "姓名", column = "B", isExport = true)
	private String realname;	
	@ExcelVOAttribute(name = "请假日期", column = "E", isExport = true)
    private String begOffDate;
	@ExcelVOAttribute(name = "班级", column = "F", isExport = true)
	private String className;//question
	@ExcelVOAttribute(name = "班主任", column = "G", isExport = true)
	private String teacherName;
	@ExcelVOAttribute(name = "部长", column = "H", isExport = true)
	private String departmentName;
	@ExcelVOAttribute(name = "学工处", column = "I", isExport = true)
	private String leaderName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getBegOffDate() {
		return begOffDate;
	}

	public void setBegOffDate(String begOffDate) {
		this.begOffDate = begOffDate;
	}
	
	
	
}