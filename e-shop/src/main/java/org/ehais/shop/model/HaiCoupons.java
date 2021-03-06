package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class HaiCoupons implements Serializable {
	
	
	private Integer couponsQuantity;
	
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.coupons_id
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer couponsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.coupons_name
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    @NotBlank(message = "优惠券名称不能为空")
    private String couponsName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.quota
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer quota;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.discounts
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer discounts;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.coupons_type
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private String couponsType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.scope
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private String scope;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.start_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date startDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.end_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.is_valid
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer isValid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.store_id
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.receive_count
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer receiveCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.use_count
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Integer useCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.create_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_coupons.update_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_coupons
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.coupons_id
     *
     * @return the value of hai_coupons.coupons_id
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getCouponsId() {
        return couponsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.coupons_id
     *
     * @param couponsId the value for hai_coupons.coupons_id
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setCouponsId(Integer couponsId) {
        this.couponsId = couponsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.coupons_name
     *
     * @return the value of hai_coupons.coupons_name
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public String getCouponsName() {
        return couponsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.coupons_name
     *
     * @param couponsName the value for hai_coupons.coupons_name
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setCouponsName(String couponsName) {
        this.couponsName = couponsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.quota
     *
     * @return the value of hai_coupons.quota
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getQuota() {
        return quota;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.quota
     *
     * @param quota the value for hai_coupons.quota
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.discounts
     *
     * @return the value of hai_coupons.discounts
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getDiscounts() {
        return discounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.discounts
     *
     * @param discounts the value for hai_coupons.discounts
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setDiscounts(Integer discounts) {
        this.discounts = discounts;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.coupons_type
     *
     * @return the value of hai_coupons.coupons_type
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public String getCouponsType() {
        return couponsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.coupons_type
     *
     * @param couponsType the value for hai_coupons.coupons_type
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setCouponsType(String couponsType) {
        this.couponsType = couponsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.scope
     *
     * @return the value of hai_coupons.scope
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public String getScope() {
        return scope;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.scope
     *
     * @param scope the value for hai_coupons.scope
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.start_date
     *
     * @return the value of hai_coupons.start_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.start_date
     *
     * @param startDate the value for hai_coupons.start_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.end_date
     *
     * @return the value of hai_coupons.end_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.end_date
     *
     * @param endDate the value for hai_coupons.end_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.is_valid
     *
     * @return the value of hai_coupons.is_valid
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.is_valid
     *
     * @param isValid the value for hai_coupons.is_valid
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.store_id
     *
     * @return the value of hai_coupons.store_id
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.store_id
     *
     * @param storeId the value for hai_coupons.store_id
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.receive_count
     *
     * @return the value of hai_coupons.receive_count
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getReceiveCount() {
        return receiveCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.receive_count
     *
     * @param receiveCount the value for hai_coupons.receive_count
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setReceiveCount(Integer receiveCount) {
        this.receiveCount = receiveCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.use_count
     *
     * @return the value of hai_coupons.use_count
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Integer getUseCount() {
        return useCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.use_count
     *
     * @param useCount the value for hai_coupons.use_count
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.create_date
     *
     * @return the value of hai_coupons.create_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.create_date
     *
     * @param createDate the value for hai_coupons.create_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_coupons.update_date
     *
     * @return the value of hai_coupons.update_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_coupons.update_date
     *
     * @param updateDate the value for hai_coupons.update_date
     *
     * @mbg.generated Tue Nov 14 22:28:51 CST 2017
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public Integer getCouponsQuantity() {
		return couponsQuantity;
	}

	public void setCouponsQuantity(Integer couponsQuantity) {
		this.couponsQuantity = couponsQuantity;
	}
    
    
}