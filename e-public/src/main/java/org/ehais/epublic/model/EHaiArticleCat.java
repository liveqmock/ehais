package org.ehais.epublic.model;

import java.io.Serializable;

public class EHaiArticleCat implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Integer catId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_name
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private String catName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_type
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Integer catType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.module
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private String module;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.keywords
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private String keywords;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_desc
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private String catDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.sort_order
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Integer sortOrder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.show_in_nav
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Boolean showInNav;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.parent_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Integer parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.store_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.code
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private String code;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.user_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.images
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private String images;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.is_valid
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private Boolean isValid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_article_cat
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.cat_id
     *
     * @return the value of hai_article_cat.cat_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Integer getCatId() {
        return catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.cat_id
     *
     * @param catId the value for hai_article_cat.cat_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.cat_name
     *
     * @return the value of hai_article_cat.cat_name
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public String getCatName() {
        return catName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.cat_name
     *
     * @param catName the value for hai_article_cat.cat_name
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.cat_type
     *
     * @return the value of hai_article_cat.cat_type
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Integer getCatType() {
        return catType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.cat_type
     *
     * @param catType the value for hai_article_cat.cat_type
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setCatType(Integer catType) {
        this.catType = catType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.module
     *
     * @return the value of hai_article_cat.module
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public String getModule() {
        return module;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.module
     *
     * @param module the value for hai_article_cat.module
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.keywords
     *
     * @return the value of hai_article_cat.keywords
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.keywords
     *
     * @param keywords the value for hai_article_cat.keywords
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.cat_desc
     *
     * @return the value of hai_article_cat.cat_desc
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public String getCatDesc() {
        return catDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.cat_desc
     *
     * @param catDesc the value for hai_article_cat.cat_desc
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.sort_order
     *
     * @return the value of hai_article_cat.sort_order
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.sort_order
     *
     * @param sortOrder the value for hai_article_cat.sort_order
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.show_in_nav
     *
     * @return the value of hai_article_cat.show_in_nav
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Boolean getShowInNav() {
        return showInNav;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.show_in_nav
     *
     * @param showInNav the value for hai_article_cat.show_in_nav
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setShowInNav(Boolean showInNav) {
        this.showInNav = showInNav;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.parent_id
     *
     * @return the value of hai_article_cat.parent_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.parent_id
     *
     * @param parentId the value for hai_article_cat.parent_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.store_id
     *
     * @return the value of hai_article_cat.store_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.store_id
     *
     * @param storeId the value for hai_article_cat.store_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.code
     *
     * @return the value of hai_article_cat.code
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.code
     *
     * @param code the value for hai_article_cat.code
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.user_id
     *
     * @return the value of hai_article_cat.user_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.user_id
     *
     * @param userId the value for hai_article_cat.user_id
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.images
     *
     * @return the value of hai_article_cat.images
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public String getImages() {
        return images;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.images
     *
     * @param images the value for hai_article_cat.images
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setImages(String images) {
        this.images = images;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.is_valid
     *
     * @return the value of hai_article_cat.is_valid
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public Boolean getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.is_valid
     *
     * @param isValid the value for hai_article_cat.is_valid
     *
     * @mbg.generated Sat Dec 30 17:07:21 CST 2017
     */
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}