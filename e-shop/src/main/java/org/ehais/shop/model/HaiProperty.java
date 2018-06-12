package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiProperty implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.property_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer propertyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.property_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String propertyCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.property_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String propertyName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date lastUpdateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long updateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long lastUpdateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_property.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_property
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.property_id
     *
     * @return the value of hai_property.property_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getPropertyId() {
        return propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.property_id
     *
     * @param propertyId the value for hai_property.property_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.property_code
     *
     * @return the value of hai_property.property_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPropertyCode() {
        return propertyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.property_code
     *
     * @param propertyCode the value for hai_property.property_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.property_name
     *
     * @return the value of hai_property.property_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.property_name
     *
     * @param propertyName the value for hai_property.property_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.store_id
     *
     * @return the value of hai_property.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.store_id
     *
     * @param storeId the value for hai_property.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.create_date
     *
     * @return the value of hai_property.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.create_date
     *
     * @param createDate the value for hai_property.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.update_date
     *
     * @return the value of hai_property.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.update_date
     *
     * @param updateDate the value for hai_property.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.last_update_date
     *
     * @return the value of hai_property.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.last_update_date
     *
     * @param lastUpdateDate the value for hai_property.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.create_admin_id
     *
     * @return the value of hai_property.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.create_admin_id
     *
     * @param createAdminId the value for hai_property.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateAdminId(Long createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.update_admin_id
     *
     * @return the value of hai_property.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getUpdateAdminId() {
        return updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.update_admin_id
     *
     * @param updateAdminId the value for hai_property.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateAdminId(Long updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.last_update_admin_id
     *
     * @return the value of hai_property.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.last_update_admin_id
     *
     * @param lastUpdateAdminId the value for hai_property.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateAdminId(Long lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_property.remark
     *
     * @return the value of hai_property.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_property.remark
     *
     * @param remark the value for hai_property.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}