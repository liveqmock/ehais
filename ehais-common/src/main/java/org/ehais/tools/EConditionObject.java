package org.ehais.tools;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EConditionObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date startTime;//开始时间
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date endTime;//结束时间	
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	private Date startDate;//开始时间
	@DateTimeFormat( pattern = "yyyy-MM-dd" )
	private Date endDate;//结束时间
	private String keyword;//关键词
	private Integer page = 1;//页面
	private Integer rows = 20;//记录长度
	private Integer store_id;//商码
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getKeyword() {
		return keyword!=null?keyword.trim():keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getStore_id() {
		return store_id;
	}
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	public Integer getStart(){
		if(page <= 0 || page == null) page = 1;
		if(rows == null)rows = 20;
		return (page - 1) * rows;
	}

}
