package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiPayment implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer payId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String payCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String payName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_fee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String payFee;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_desc
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String payDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_order
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer payOrder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.pay_config
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String payConfig;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.enabled
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Boolean enabled;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.is_default
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Boolean isDefault;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.is_cod
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Boolean isCod;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.is_online
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Boolean isOnline;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.className
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String classname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date lastUpdateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long updateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_payment.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long lastUpdateAdminId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_payment
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_id
     *
     * @return the value of hai_payment.pay_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getPayId() {
        return payId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_id
     *
     * @param payId the value for hai_payment.pay_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_code
     *
     * @return the value of hai_payment.pay_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPayCode() {
        return payCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_code
     *
     * @param payCode the value for hai_payment.pay_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_name
     *
     * @return the value of hai_payment.pay_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPayName() {
        return payName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_name
     *
     * @param payName the value for hai_payment.pay_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayName(String payName) {
        this.payName = payName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_fee
     *
     * @return the value of hai_payment.pay_fee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPayFee() {
        return payFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_fee
     *
     * @param payFee the value for hai_payment.pay_fee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_desc
     *
     * @return the value of hai_payment.pay_desc
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPayDesc() {
        return payDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_desc
     *
     * @param payDesc the value for hai_payment.pay_desc
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayDesc(String payDesc) {
        this.payDesc = payDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_order
     *
     * @return the value of hai_payment.pay_order
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getPayOrder() {
        return payOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_order
     *
     * @param payOrder the value for hai_payment.pay_order
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayOrder(Integer payOrder) {
        this.payOrder = payOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.pay_config
     *
     * @return the value of hai_payment.pay_config
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPayConfig() {
        return payConfig;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.pay_config
     *
     * @param payConfig the value for hai_payment.pay_config
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPayConfig(String payConfig) {
        this.payConfig = payConfig;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.enabled
     *
     * @return the value of hai_payment.enabled
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.enabled
     *
     * @param enabled the value for hai_payment.enabled
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.is_default
     *
     * @return the value of hai_payment.is_default
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.is_default
     *
     * @param isDefault the value for hai_payment.is_default
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.is_cod
     *
     * @return the value of hai_payment.is_cod
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Boolean getIsCod() {
        return isCod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.is_cod
     *
     * @param isCod the value for hai_payment.is_cod
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.is_online
     *
     * @return the value of hai_payment.is_online
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Boolean getIsOnline() {
        return isOnline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.is_online
     *
     * @param isOnline the value for hai_payment.is_online
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.className
     *
     * @return the value of hai_payment.className
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getClassname() {
        return classname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.className
     *
     * @param classname the value for hai_payment.className
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.store_id
     *
     * @return the value of hai_payment.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.store_id
     *
     * @param storeId the value for hai_payment.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.create_date
     *
     * @return the value of hai_payment.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.create_date
     *
     * @param createDate the value for hai_payment.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.update_date
     *
     * @return the value of hai_payment.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.update_date
     *
     * @param updateDate the value for hai_payment.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.last_update_date
     *
     * @return the value of hai_payment.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.last_update_date
     *
     * @param lastUpdateDate the value for hai_payment.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.create_admin_id
     *
     * @return the value of hai_payment.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.create_admin_id
     *
     * @param createAdminId the value for hai_payment.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateAdminId(Long createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.update_admin_id
     *
     * @return the value of hai_payment.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getUpdateAdminId() {
        return updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.update_admin_id
     *
     * @param updateAdminId the value for hai_payment.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateAdminId(Long updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_payment.last_update_admin_id
     *
     * @return the value of hai_payment.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_payment.last_update_admin_id
     *
     * @param lastUpdateAdminId the value for hai_payment.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateAdminId(Long lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }
}