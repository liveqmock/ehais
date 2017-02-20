package org.ehais.epublic.model;

import java.io.Serializable;

public class EHaiArticleCat implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Integer catId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_name
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String catName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_type
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Boolean catType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.keywords
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String keywords;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.cat_desc
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String catDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.sort_order
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Byte sortOrder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.show_in_nav
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Boolean showInNav;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.parent_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Short parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.user_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.store_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Integer storeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article_cat.images
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String images;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_article_cat
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.cat_id
     *
     * @return the value of hai_article_cat.cat_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Boolean getCatType() {
        return catType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.cat_type
     *
     * @param catType the value for hai_article_cat.cat_type
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setCatType(Boolean catType) {
        this.catType = catType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.keywords
     *
     * @return the value of hai_article_cat.keywords
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Byte getSortOrder() {
        return sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.sort_order
     *
     * @param sortOrder the value for hai_article_cat.sort_order
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setSortOrder(Byte sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.show_in_nav
     *
     * @return the value of hai_article_cat.show_in_nav
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Short getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article_cat.parent_id
     *
     * @param parentId the value for hai_article_cat.parent_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setParentId(Short parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.user_id
     *
     * @return the value of hai_article_cat.user_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.store_id
     *
     * @return the value of hai_article_cat.store_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article_cat.images
     *
     * @return the value of hai_article_cat.images
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
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
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setImages(String images) {
        this.images = images;
    }
}