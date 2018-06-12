package org.ehais.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HaiBusiness implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.business_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer businessId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.business_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String businessCode;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.user_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.business_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String businessName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.sectors_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer sectorsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.legal_person
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String legalPerson;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.linkman
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String linkman;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.tel
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String tel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.fax
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String fax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.email
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.address
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.longitude
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private BigDecimal longitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.latitude
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private BigDecimal latitude;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.is_void
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String isVoid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.logo
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String logo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.website
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String website;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.classify
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String classify;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.pinyin
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String pinyin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.country
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer country;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.province
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer province;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.city
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer city;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.county
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer county;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.district
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer district;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.street
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Integer street;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Date lastUpdateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long createAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long updateAdminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private Long lastUpdateAdminId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.business_id
     *
     * @return the value of hai_business.business_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.business_id
     *
     * @param businessId the value for hai_business.business_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.business_code
     *
     * @return the value of hai_business.business_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.business_code
     *
     * @param businessCode the value for hai_business.business_code
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.user_id
     *
     * @return the value of hai_business.user_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.user_id
     *
     * @param userId the value for hai_business.user_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.business_name
     *
     * @return the value of hai_business.business_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.business_name
     *
     * @param businessName the value for hai_business.business_name
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.sectors_id
     *
     * @return the value of hai_business.sectors_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getSectorsId() {
        return sectorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.sectors_id
     *
     * @param sectorsId the value for hai_business.sectors_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setSectorsId(Integer sectorsId) {
        this.sectorsId = sectorsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.legal_person
     *
     * @return the value of hai_business.legal_person
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.legal_person
     *
     * @param legalPerson the value for hai_business.legal_person
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.linkman
     *
     * @return the value of hai_business.linkman
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getLinkman() {
        return linkman;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.linkman
     *
     * @param linkman the value for hai_business.linkman
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.tel
     *
     * @return the value of hai_business.tel
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.tel
     *
     * @param tel the value for hai_business.tel
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.fax
     *
     * @return the value of hai_business.fax
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getFax() {
        return fax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.fax
     *
     * @param fax the value for hai_business.fax
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.email
     *
     * @return the value of hai_business.email
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.email
     *
     * @param email the value for hai_business.email
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.address
     *
     * @return the value of hai_business.address
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.address
     *
     * @param address the value for hai_business.address
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.longitude
     *
     * @return the value of hai_business.longitude
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.longitude
     *
     * @param longitude the value for hai_business.longitude
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.latitude
     *
     * @return the value of hai_business.latitude
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.latitude
     *
     * @param latitude the value for hai_business.latitude
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.is_void
     *
     * @return the value of hai_business.is_void
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getIsVoid() {
        return isVoid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.is_void
     *
     * @param isVoid the value for hai_business.is_void
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setIsVoid(String isVoid) {
        this.isVoid = isVoid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.logo
     *
     * @return the value of hai_business.logo
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getLogo() {
        return logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.logo
     *
     * @param logo the value for hai_business.logo
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.website
     *
     * @return the value of hai_business.website
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getWebsite() {
        return website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.website
     *
     * @param website the value for hai_business.website
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.store_id
     *
     * @return the value of hai_business.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.store_id
     *
     * @param storeId the value for hai_business.store_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.classify
     *
     * @return the value of hai_business.classify
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getClassify() {
        return classify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.classify
     *
     * @param classify the value for hai_business.classify
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setClassify(String classify) {
        this.classify = classify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.pinyin
     *
     * @return the value of hai_business.pinyin
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.pinyin
     *
     * @param pinyin the value for hai_business.pinyin
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.country
     *
     * @return the value of hai_business.country
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.country
     *
     * @param country the value for hai_business.country
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCountry(Integer country) {
        this.country = country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.province
     *
     * @return the value of hai_business.province
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.province
     *
     * @param province the value for hai_business.province
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.city
     *
     * @return the value of hai_business.city
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getCity() {
        return city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.city
     *
     * @param city the value for hai_business.city
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.county
     *
     * @return the value of hai_business.county
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getCounty() {
        return county;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.county
     *
     * @param county the value for hai_business.county
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCounty(Integer county) {
        this.county = county;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.district
     *
     * @return the value of hai_business.district
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getDistrict() {
        return district;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.district
     *
     * @param district the value for hai_business.district
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setDistrict(Integer district) {
        this.district = district;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.street
     *
     * @return the value of hai_business.street
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Integer getStreet() {
        return street;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.street
     *
     * @param street the value for hai_business.street
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setStreet(Integer street) {
        this.street = street;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.create_date
     *
     * @return the value of hai_business.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.create_date
     *
     * @param createDate the value for hai_business.create_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.update_date
     *
     * @return the value of hai_business.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.update_date
     *
     * @param updateDate the value for hai_business.update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.last_update_date
     *
     * @return the value of hai_business.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.last_update_date
     *
     * @param lastUpdateDate the value for hai_business.last_update_date
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.create_admin_id
     *
     * @return the value of hai_business.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getCreateAdminId() {
        return createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.create_admin_id
     *
     * @param createAdminId the value for hai_business.create_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setCreateAdminId(Long createAdminId) {
        this.createAdminId = createAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.update_admin_id
     *
     * @return the value of hai_business.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getUpdateAdminId() {
        return updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.update_admin_id
     *
     * @param updateAdminId the value for hai_business.update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setUpdateAdminId(Long updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.last_update_admin_id
     *
     * @return the value of hai_business.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Long getLastUpdateAdminId() {
        return lastUpdateAdminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.last_update_admin_id
     *
     * @param lastUpdateAdminId the value for hai_business.last_update_admin_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLastUpdateAdminId(Long lastUpdateAdminId) {
        this.lastUpdateAdminId = lastUpdateAdminId;
    }
}