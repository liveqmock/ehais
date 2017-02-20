package org.ehais.epublic.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class EHaiArticle implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.article_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Integer articleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.cat_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Integer catId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.title
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    @NotEmpty
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.author
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String author;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.author_email
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String authorEmail;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.article_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date articleDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.keywords
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String keywords;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.article_type_code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String articleTypeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.is_open
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Boolean isOpen;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.file_url
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String fileUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.open_type
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Boolean openType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.link
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String link;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.description
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.user_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.store_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Integer storeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.sort
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Short sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.article_images
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String articleImages;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.video_url
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String videoUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.create_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.update_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private Date updateDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_article.content
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_article
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.article_id
     *
     * @return the value of hai_article.article_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.article_id
     *
     * @param articleId the value for hai_article.article_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.cat_id
     *
     * @return the value of hai_article.cat_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Integer getCatId() {
        return catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.cat_id
     *
     * @param catId the value for hai_article.cat_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.code
     *
     * @return the value of hai_article.code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.code
     *
     * @param code the value for hai_article.code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.title
     *
     * @return the value of hai_article.title
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.title
     *
     * @param title the value for hai_article.title
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.author
     *
     * @return the value of hai_article.author
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.author
     *
     * @param author the value for hai_article.author
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.author_email
     *
     * @return the value of hai_article.author_email
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getAuthorEmail() {
        return authorEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.author_email
     *
     * @param authorEmail the value for hai_article.author_email
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.article_date
     *
     * @return the value of hai_article.article_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Date getArticleDate() {
        return articleDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.article_date
     *
     * @param articleDate the value for hai_article.article_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setArticleDate(Date articleDate) {
        this.articleDate = articleDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.keywords
     *
     * @return the value of hai_article.keywords
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.keywords
     *
     * @param keywords the value for hai_article.keywords
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.article_type_code
     *
     * @return the value of hai_article.article_type_code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getArticleTypeCode() {
        return articleTypeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.article_type_code
     *
     * @param articleTypeCode the value for hai_article.article_type_code
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setArticleTypeCode(String articleTypeCode) {
        this.articleTypeCode = articleTypeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.is_open
     *
     * @return the value of hai_article.is_open
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Boolean getIsOpen() {
        return isOpen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.is_open
     *
     * @param isOpen the value for hai_article.is_open
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.file_url
     *
     * @return the value of hai_article.file_url
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.file_url
     *
     * @param fileUrl the value for hai_article.file_url
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.open_type
     *
     * @return the value of hai_article.open_type
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Boolean getOpenType() {
        return openType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.open_type
     *
     * @param openType the value for hai_article.open_type
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setOpenType(Boolean openType) {
        this.openType = openType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.link
     *
     * @return the value of hai_article.link
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getLink() {
        return link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.link
     *
     * @param link the value for hai_article.link
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.description
     *
     * @return the value of hai_article.description
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.description
     *
     * @param description the value for hai_article.description
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.user_id
     *
     * @return the value of hai_article.user_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.user_id
     *
     * @param userId the value for hai_article.user_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.store_id
     *
     * @return the value of hai_article.store_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.store_id
     *
     * @param storeId the value for hai_article.store_id
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.sort
     *
     * @return the value of hai_article.sort
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Short getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.sort
     *
     * @param sort the value for hai_article.sort
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.article_images
     *
     * @return the value of hai_article.article_images
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getArticleImages() {
        return articleImages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.article_images
     *
     * @param articleImages the value for hai_article.article_images
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setArticleImages(String articleImages) {
        this.articleImages = articleImages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.video_url
     *
     * @return the value of hai_article.video_url
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.video_url
     *
     * @param videoUrl the value for hai_article.video_url
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.create_date
     *
     * @return the value of hai_article.create_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.create_date
     *
     * @param createDate the value for hai_article.create_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.update_date
     *
     * @return the value of hai_article.update_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.update_date
     *
     * @param updateDate the value for hai_article.update_date
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_article.content
     *
     * @return the value of hai_article.content
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_article.content
     *
     * @param content the value for hai_article.content
     *
     * @mbggenerated Tue Jul 19 21:54:38 CST 2016
     */
    public void setContent(String content) {
        this.content = content;
    }
}