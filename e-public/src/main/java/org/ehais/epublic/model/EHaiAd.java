package org.ehais.epublic.model;

import java.io.Serializable;
import java.util.Date;

public class EHaiAd implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.ad_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer adId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.position_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer positionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.media_type
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer mediaType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.ad_name
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String adName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.ad_link
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String adLink;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.ad_pic
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String adPic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.link_man
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String linkMan;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.link_email
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String linkEmail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.link_phone
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String linkPhone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.click_count
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer clickCount;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.enabled
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Boolean enabled;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.store_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.start_time
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Date startTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.end_time
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Date endTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.nav_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer navId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.sort
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer sort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.is_mobile
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer isMobile;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.agency_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer agencyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.partner_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer partnerId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.is_void
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private Integer isVoid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_ad.ad_code
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private String adCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_ad
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_id
     *
     * @return the value of hai_ad.ad_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getAdId() {
        return adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_id
     *
     * @param adId the value for hai_ad.ad_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.position_id
     *
     * @return the value of hai_ad.position_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getPositionId() {
        return positionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.position_id
     *
     * @param positionId the value for hai_ad.position_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.media_type
     *
     * @return the value of hai_ad.media_type
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getMediaType() {
        return mediaType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.media_type
     *
     * @param mediaType the value for hai_ad.media_type
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_name
     *
     * @return the value of hai_ad.ad_name
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getAdName() {
        return adName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_name
     *
     * @param adName the value for hai_ad.ad_name
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setAdName(String adName) {
        this.adName = adName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_link
     *
     * @return the value of hai_ad.ad_link
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getAdLink() {
        return adLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_link
     *
     * @param adLink the value for hai_ad.ad_link
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_pic
     *
     * @return the value of hai_ad.ad_pic
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getAdPic() {
        return adPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_pic
     *
     * @param adPic the value for hai_ad.ad_pic
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setAdPic(String adPic) {
        this.adPic = adPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.link_man
     *
     * @return the value of hai_ad.link_man
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.link_man
     *
     * @param linkMan the value for hai_ad.link_man
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.link_email
     *
     * @return the value of hai_ad.link_email
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getLinkEmail() {
        return linkEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.link_email
     *
     * @param linkEmail the value for hai_ad.link_email
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setLinkEmail(String linkEmail) {
        this.linkEmail = linkEmail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.link_phone
     *
     * @return the value of hai_ad.link_phone
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getLinkPhone() {
        return linkPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.link_phone
     *
     * @param linkPhone the value for hai_ad.link_phone
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.click_count
     *
     * @return the value of hai_ad.click_count
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getClickCount() {
        return clickCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.click_count
     *
     * @param clickCount the value for hai_ad.click_count
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.enabled
     *
     * @return the value of hai_ad.enabled
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.enabled
     *
     * @param enabled the value for hai_ad.enabled
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.store_id
     *
     * @return the value of hai_ad.store_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.store_id
     *
     * @param storeId the value for hai_ad.store_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.start_time
     *
     * @return the value of hai_ad.start_time
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.start_time
     *
     * @param startTime the value for hai_ad.start_time
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.end_time
     *
     * @return the value of hai_ad.end_time
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.end_time
     *
     * @param endTime the value for hai_ad.end_time
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.nav_id
     *
     * @return the value of hai_ad.nav_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getNavId() {
        return navId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.nav_id
     *
     * @param navId the value for hai_ad.nav_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.sort
     *
     * @return the value of hai_ad.sort
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.sort
     *
     * @param sort the value for hai_ad.sort
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.is_mobile
     *
     * @return the value of hai_ad.is_mobile
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getIsMobile() {
        return isMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.is_mobile
     *
     * @param isMobile the value for hai_ad.is_mobile
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setIsMobile(Integer isMobile) {
        this.isMobile = isMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.agency_id
     *
     * @return the value of hai_ad.agency_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getAgencyId() {
        return agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.agency_id
     *
     * @param agencyId the value for hai_ad.agency_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.partner_id
     *
     * @return the value of hai_ad.partner_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getPartnerId() {
        return partnerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.partner_id
     *
     * @param partnerId the value for hai_ad.partner_id
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.is_void
     *
     * @return the value of hai_ad.is_void
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public Integer getIsVoid() {
        return isVoid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.is_void
     *
     * @param isVoid the value for hai_ad.is_void
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setIsVoid(Integer isVoid) {
        this.isVoid = isVoid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_code
     *
     * @return the value of hai_ad.ad_code
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public String getAdCode() {
        return adCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_code
     *
     * @param adCode the value for hai_ad.ad_code
     *
     * @mbg.generated Tue Jan 23 11:10:52 CST 2018
     */
    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }
}