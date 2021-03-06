package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiAccounting implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.accounting_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer accountingId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.accounting_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String accountingCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.accounting_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String accountingName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date lastUpdateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long updateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long lastUpdateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_accounting.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.accounting_id
     *
     * @return the value of hai_accounting.accounting_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getAccountingId() {
        return accountingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.accounting_id
     *
     * @param accountingId the value for hai_accounting.accounting_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setAccountingId(Integer accountingId) {
        this.accountingId = accountingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.accounting_code
     *
     * @return the value of hai_accounting.accounting_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getAccountingCode() {
        return accountingCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.accounting_code
     *
     * @param accountingCode the value for hai_accounting.accounting_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setAccountingCode(String accountingCode) {
        this.accountingCode = accountingCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.accounting_name
     *
     * @return the value of hai_accounting.accounting_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getAccountingName() {
        return accountingName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.accounting_name
     *
     * @param accountingName the value for hai_accounting.accounting_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setAccountingName(String accountingName) {
        this.accountingName = accountingName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.store_id
     *
     * @return the value of hai_accounting.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.store_id
     *
     * @param storeId the value for hai_accounting.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.create_date
     *
     * @return the value of hai_accounting.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.create_date
     *
     * @param createDate the value for hai_accounting.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.update_date
     *
     * @return the value of hai_accounting.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.update_date
     *
     * @param updateDate the value for hai_accounting.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.last_update_date
     *
     * @return the value of hai_accounting.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.last_update_date
     *
     * @param lastUpdateDate the value for hai_accounting.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.create_admin_id
     *
     * @return the value of hai_accounting.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.create_admin_id
     *
     * @param createAdminId the value for hai_accounting.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateAdminId(Long createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.update_admin_id
     *
     * @return the value of hai_accounting.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getUpdateAdminId() {
        return updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.update_admin_id
     *
     * @param updateAdminId the value for hai_accounting.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateAdminId(Long updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.last_update_admin_id
     *
     * @return the value of hai_accounting.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.last_update_admin_id
     *
     * @param lastUpdateAdminId the value for hai_accounting.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateAdminId(Long lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_accounting.remark
     *
     * @return the value of hai_accounting.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_accounting.remark
     *
     * @param remark the value for hai_accounting.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}