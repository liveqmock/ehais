package com.ehais.huangbao.model;

import java.io.Serializable;

public class HaiActivityType implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hai_activity_type.act_type_id
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	private Integer actTypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hai_activity_type.title
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	private String title;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hai_activity_type.wxid
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	private Integer wxid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column hai_activity_type.content
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	private String content;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table hai_activity_type
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hai_activity_type.act_type_id
	 * @return  the value of hai_activity_type.act_type_id
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public Integer getActTypeId() {
		return actTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hai_activity_type.act_type_id
	 * @param actTypeId  the value for hai_activity_type.act_type_id
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public void setActTypeId(Integer actTypeId) {
		this.actTypeId = actTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hai_activity_type.title
	 * @return  the value of hai_activity_type.title
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hai_activity_type.title
	 * @param title  the value for hai_activity_type.title
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hai_activity_type.wxid
	 * @return  the value of hai_activity_type.wxid
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public Integer getWxid() {
		return wxid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hai_activity_type.wxid
	 * @param wxid  the value for hai_activity_type.wxid
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public void setWxid(Integer wxid) {
		this.wxid = wxid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column hai_activity_type.content
	 * @return  the value of hai_activity_type.content
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public String getContent() {
		return content;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column hai_activity_type.content
	 * @param content  the value for hai_activity_type.content
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	public void setContent(String content) {
		this.content = content;
	}
}