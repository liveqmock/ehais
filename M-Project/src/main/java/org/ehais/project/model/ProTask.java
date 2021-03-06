package org.ehais.project.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ProTask {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.task_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Integer taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.task_title
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected String taskTitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.pro_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Integer proId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.plan_start_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    protected Date planStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.plan_end_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    protected Date planEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.truth_start_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    protected Date truthStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.truth_end_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    protected Date truthEndDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.user_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.dispatch_user_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Integer dispatchUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.progress
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Integer progress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.create_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pro_task.update_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    protected Date updateDate;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.task_id
     *
     * @return the value of pro_task.task_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.task_id
     *
     * @param taskId the value for pro_task.task_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.task_title
     *
     * @return the value of pro_task.task_title
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.task_title
     *
     * @param taskTitle the value for pro_task.task_title
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.pro_id
     *
     * @return the value of pro_task.pro_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Integer getProId() {
        return proId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.pro_id
     *
     * @param proId the value for pro_task.pro_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setProId(Integer proId) {
        this.proId = proId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.plan_start_date
     *
     * @return the value of pro_task.plan_start_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Date getPlanStartDate() {
        return planStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.plan_start_date
     *
     * @param planStartDate the value for pro_task.plan_start_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.plan_end_date
     *
     * @return the value of pro_task.plan_end_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Date getPlanEndDate() {
        return planEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.plan_end_date
     *
     * @param planEndDate the value for pro_task.plan_end_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.truth_start_date
     *
     * @return the value of pro_task.truth_start_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Date getTruthStartDate() {
        return truthStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.truth_start_date
     *
     * @param truthStartDate the value for pro_task.truth_start_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setTruthStartDate(Date truthStartDate) {
        this.truthStartDate = truthStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.truth_end_date
     *
     * @return the value of pro_task.truth_end_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Date getTruthEndDate() {
        return truthEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.truth_end_date
     *
     * @param truthEndDate the value for pro_task.truth_end_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setTruthEndDate(Date truthEndDate) {
        this.truthEndDate = truthEndDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.user_id
     *
     * @return the value of pro_task.user_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.user_id
     *
     * @param userId the value for pro_task.user_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.dispatch_user_id
     *
     * @return the value of pro_task.dispatch_user_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Integer getDispatchUserId() {
        return dispatchUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.dispatch_user_id
     *
     * @param dispatchUserId the value for pro_task.dispatch_user_id
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setDispatchUserId(Integer dispatchUserId) {
        this.dispatchUserId = dispatchUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.progress
     *
     * @return the value of pro_task.progress
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.progress
     *
     * @param progress the value for pro_task.progress
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.create_date
     *
     * @return the value of pro_task.create_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.create_date
     *
     * @param createDate the value for pro_task.create_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pro_task.update_date
     *
     * @return the value of pro_task.update_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pro_task.update_date
     *
     * @param updateDate the value for pro_task.update_date
     *
     * @mbggenerated Thu Dec 31 11:00:58 CST 2015
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}