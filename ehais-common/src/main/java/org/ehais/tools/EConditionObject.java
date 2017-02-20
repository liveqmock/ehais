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
	private Date startDate;//开始时间
	@DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
	private Date endDate;//结束时间	
	private String keyword;//关键词
	private Integer page;//页面
	private Integer len;//记录长度
	private Integer store_id;//商码
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
		return keyword;
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
	public Integer getLen() {
		return len;
	}
	public void setLen(Integer len) {
		this.len = len;
	}
	public Integer getStore_id() {
		return store_id;
	}
	public void setStore_id(Integer store_id) {
		this.store_id = store_id;
	}
	
	
	

}
