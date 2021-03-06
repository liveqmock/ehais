package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiExpenses implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.expenses_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer expensesId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.expenses_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String expensesCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.expenses_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String expensesName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date lastUpdateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long updateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long lastUpdateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_expenses.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.expenses_id
     *
     * @return the value of hai_expenses.expenses_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getExpensesId() {
        return expensesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.expenses_id
     *
     * @param expensesId the value for hai_expenses.expenses_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setExpensesId(Integer expensesId) {
        this.expensesId = expensesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.expenses_code
     *
     * @return the value of hai_expenses.expenses_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getExpensesCode() {
        return expensesCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.expenses_code
     *
     * @param expensesCode the value for hai_expenses.expenses_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setExpensesCode(String expensesCode) {
        this.expensesCode = expensesCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.expenses_name
     *
     * @return the value of hai_expenses.expenses_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getExpensesName() {
        return expensesName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.expenses_name
     *
     * @param expensesName the value for hai_expenses.expenses_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setExpensesName(String expensesName) {
        this.expensesName = expensesName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.store_id
     *
     * @return the value of hai_expenses.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.store_id
     *
     * @param storeId the value for hai_expenses.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.create_date
     *
     * @return the value of hai_expenses.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.create_date
     *
     * @param createDate the value for hai_expenses.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.update_date
     *
     * @return the value of hai_expenses.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.update_date
     *
     * @param updateDate the value for hai_expenses.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.last_update_date
     *
     * @return the value of hai_expenses.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.last_update_date
     *
     * @param lastUpdateDate the value for hai_expenses.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.create_admin_id
     *
     * @return the value of hai_expenses.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.create_admin_id
     *
     * @param createAdminId the value for hai_expenses.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateAdminId(Long createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.update_admin_id
     *
     * @return the value of hai_expenses.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getUpdateAdminId() {
        return updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.update_admin_id
     *
     * @param updateAdminId the value for hai_expenses.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateAdminId(Long updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.last_update_admin_id
     *
     * @return the value of hai_expenses.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.last_update_admin_id
     *
     * @param lastUpdateAdminId the value for hai_expenses.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateAdminId(Long lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_expenses.remark
     *
     * @return the value of hai_expenses.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_expenses.remark
     *
     * @param remark the value for hai_expenses.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}