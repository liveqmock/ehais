package org.ehais.shop.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.ehais.enums.EUniqueEnum;
import org.ehais.epublic.validator.EUnique;
import org.hibernate.validator.constraints.NotEmpty;

public class HaiBrand implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.brand_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private Integer brandId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.brand_name
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    @NotNull(message = "品牌名称不能为空")
    @NotEmpty(message = "品牌名称不能为空")
    @EUnique(message="品牌名称重复",tableName="hai_brand",fieldName="brand_name",actionType=EUniqueEnum.insert)
    private String brandName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.store_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.brand_logo
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    @NotNull(message = "品牌LOGO不能为空")
    @NotEmpty(message = "品牌LOGO不能为空")
    private String brandLogo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.site_url
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    @NotNull(message = "品牌网址不能为空")
    @NotEmpty(message = "品牌网址不能为空")
    private String siteUrl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.sort_order
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private Short sortOrder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.is_show
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private Boolean isShow;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.cat_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private Integer catId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.recommend
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private String recommend;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_brand.brand_desc
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private String brandDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_brand
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.brand_id
     *
     * @return the value of hai_brand.brand_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.brand_id
     *
     * @param brandId the value for hai_brand.brand_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.brand_name
     *
     * @return the value of hai_brand.brand_name
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.brand_name
     *
     * @param brandName the value for hai_brand.brand_name
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.store_id
     *
     * @return the value of hai_brand.store_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.store_id
     *
     * @param storeId the value for hai_brand.store_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.brand_logo
     *
     * @return the value of hai_brand.brand_logo
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public String getBrandLogo() {
        return brandLogo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.brand_logo
     *
     * @param brandLogo the value for hai_brand.brand_logo
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.site_url
     *
     * @return the value of hai_brand.site_url
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public String getSiteUrl() {
        return siteUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.site_url
     *
     * @param siteUrl the value for hai_brand.site_url
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.sort_order
     *
     * @return the value of hai_brand.sort_order
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public Short getSortOrder() {
        return sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.sort_order
     *
     * @param sortOrder the value for hai_brand.sort_order
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setSortOrder(Short sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.is_show
     *
     * @return the value of hai_brand.is_show
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public Boolean getIsShow() {
        return isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.is_show
     *
     * @param isShow the value for hai_brand.is_show
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.cat_id
     *
     * @return the value of hai_brand.cat_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public Integer getCatId() {
        return catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.cat_id
     *
     * @param catId the value for hai_brand.cat_id
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.recommend
     *
     * @return the value of hai_brand.recommend
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.recommend
     *
     * @param recommend the value for hai_brand.recommend
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_brand.brand_desc
     *
     * @return the value of hai_brand.brand_desc
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public String getBrandDesc() {
        return brandDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_brand.brand_desc
     *
     * @param brandDesc the value for hai_brand.brand_desc
     *
     * @mbg.generated Sun Dec 17 10:40:33 CST 2017
     */
    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }
}