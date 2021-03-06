package org.ehais.weixin.model;

import java.io.Serializable;
import java.util.Date;

public class HaiDonation implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.donation_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Integer donationId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.donation_name
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private String donationName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.donation_type_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Integer donationTypeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.picture
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private String picture;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.intro
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private String intro;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.sort
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.store_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.create_date
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.update_date
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.total_amount
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Integer totalAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.total_quantity
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Integer totalQuantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.min_amount
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private Long minAmount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.amount_level
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private String amountLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_donation.content
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_donation
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.donation_id
     *
     * @return the value of hai_donation.donation_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Integer getDonationId() {
        return donationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.donation_id
     *
     * @param donationId the value for hai_donation.donation_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setDonationId(Integer donationId) {
        this.donationId = donationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.donation_name
     *
     * @return the value of hai_donation.donation_name
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public String getDonationName() {
        return donationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.donation_name
     *
     * @param donationName the value for hai_donation.donation_name
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setDonationName(String donationName) {
        this.donationName = donationName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.donation_type_id
     *
     * @return the value of hai_donation.donation_type_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Integer getDonationTypeId() {
        return donationTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.donation_type_id
     *
     * @param donationTypeId the value for hai_donation.donation_type_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setDonationTypeId(Integer donationTypeId) {
        this.donationTypeId = donationTypeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.picture
     *
     * @return the value of hai_donation.picture
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public String getPicture() {
        return picture;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.picture
     *
     * @param picture the value for hai_donation.picture
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.intro
     *
     * @return the value of hai_donation.intro
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.intro
     *
     * @param intro the value for hai_donation.intro
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.sort
     *
     * @return the value of hai_donation.sort
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.sort
     *
     * @param sort the value for hai_donation.sort
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.store_id
     *
     * @return the value of hai_donation.store_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.store_id
     *
     * @param storeId the value for hai_donation.store_id
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.create_date
     *
     * @return the value of hai_donation.create_date
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.create_date
     *
     * @param createDate the value for hai_donation.create_date
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.update_date
     *
     * @return the value of hai_donation.update_date
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.update_date
     *
     * @param updateDate the value for hai_donation.update_date
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.total_amount
     *
     * @return the value of hai_donation.total_amount
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.total_amount
     *
     * @param totalAmount the value for hai_donation.total_amount
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.total_quantity
     *
     * @return the value of hai_donation.total_quantity
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.total_quantity
     *
     * @param totalQuantity the value for hai_donation.total_quantity
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.min_amount
     *
     * @return the value of hai_donation.min_amount
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public Long getMinAmount() {
        return minAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.min_amount
     *
     * @param minAmount the value for hai_donation.min_amount
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setMinAmount(Long minAmount) {
        this.minAmount = minAmount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.amount_level
     *
     * @return the value of hai_donation.amount_level
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public String getAmountLevel() {
        return amountLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.amount_level
     *
     * @param amountLevel the value for hai_donation.amount_level
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setAmountLevel(String amountLevel) {
        this.amountLevel = amountLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_donation.content
     *
     * @return the value of hai_donation.content
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_donation.content
     *
     * @param content the value for hai_donation.content
     *
     * @mbg.generated Thu Oct 12 22:24:47 CST 2017
     */
    public void setContent(String content) {
        this.content = content;
    }
}