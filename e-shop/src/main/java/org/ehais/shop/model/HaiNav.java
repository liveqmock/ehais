package org.ehais.shop.model;

import java.io.Serializable;

public class HaiNav implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.name
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.intro
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String intro;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.is_valid
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Boolean isValid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.sort
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.opennew
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Byte opennew;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.url
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String url;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.classify
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String classify;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.store_id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.is_mobile
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Boolean isMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.className
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String classname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.parent_id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private Integer parentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.thumb
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String thumb;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_nav.image
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_nav
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.id
     *
     * @return the value of hai_nav.id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.id
     *
     * @param id the value for hai_nav.id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.name
     *
     * @return the value of hai_nav.name
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.name
     *
     * @param name the value for hai_nav.name
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.intro
     *
     * @return the value of hai_nav.intro
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.intro
     *
     * @param intro the value for hai_nav.intro
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.is_valid
     *
     * @return the value of hai_nav.is_valid
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Boolean getIsValid() {
        return isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.is_valid
     *
     * @param isValid the value for hai_nav.is_valid
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.sort
     *
     * @return the value of hai_nav.sort
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.sort
     *
     * @param sort the value for hai_nav.sort
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.opennew
     *
     * @return the value of hai_nav.opennew
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Byte getOpennew() {
        return opennew;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.opennew
     *
     * @param opennew the value for hai_nav.opennew
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setOpennew(Byte opennew) {
        this.opennew = opennew;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.url
     *
     * @return the value of hai_nav.url
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.url
     *
     * @param url the value for hai_nav.url
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.classify
     *
     * @return the value of hai_nav.classify
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getClassify() {
        return classify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.classify
     *
     * @param classify the value for hai_nav.classify
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setClassify(String classify) {
        this.classify = classify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.store_id
     *
     * @return the value of hai_nav.store_id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.store_id
     *
     * @param storeId the value for hai_nav.store_id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.is_mobile
     *
     * @return the value of hai_nav.is_mobile
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Boolean getIsMobile() {
        return isMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.is_mobile
     *
     * @param isMobile the value for hai_nav.is_mobile
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setIsMobile(Boolean isMobile) {
        this.isMobile = isMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.className
     *
     * @return the value of hai_nav.className
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getClassname() {
        return classname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.className
     *
     * @param classname the value for hai_nav.className
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.parent_id
     *
     * @return the value of hai_nav.parent_id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.parent_id
     *
     * @param parentId the value for hai_nav.parent_id
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.thumb
     *
     * @return the value of hai_nav.thumb
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.thumb
     *
     * @param thumb the value for hai_nav.thumb
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_nav.image
     *
     * @return the value of hai_nav.image
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_nav.image
     *
     * @param image the value for hai_nav.image
     *
     * @mbg.generated Sat Nov 11 21:29:38 CST 2017
     */
    public void setImage(String image) {
        this.image = image;
    }
}