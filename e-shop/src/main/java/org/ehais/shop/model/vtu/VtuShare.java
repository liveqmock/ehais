package org.ehais.shop.model.vtu;

import java.io.Serializable;
import java.util.Date;

public class VtuShare implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.vtu_share_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Long vtuShareId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.vtu_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Long vtuId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.user_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.vtu_time
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private String vtuTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.vtu_pic
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private String vtuPic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.create_date
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.is_share
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Boolean isShare;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.share_time
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Date shareTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column vtu_share.share_count
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private Integer shareCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table vtu_share
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.vtu_share_id
     *
     * @return the value of vtu_share.vtu_share_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Long getVtuShareId() {
        return vtuShareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.vtu_share_id
     *
     * @param vtuShareId the value for vtu_share.vtu_share_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setVtuShareId(Long vtuShareId) {
        this.vtuShareId = vtuShareId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.vtu_id
     *
     * @return the value of vtu_share.vtu_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Long getVtuId() {
        return vtuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.vtu_id
     *
     * @param vtuId the value for vtu_share.vtu_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setVtuId(Long vtuId) {
        this.vtuId = vtuId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.user_id
     *
     * @return the value of vtu_share.user_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.user_id
     *
     * @param userId the value for vtu_share.user_id
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.vtu_time
     *
     * @return the value of vtu_share.vtu_time
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public String getVtuTime() {
        return vtuTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.vtu_time
     *
     * @param vtuTime the value for vtu_share.vtu_time
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setVtuTime(String vtuTime) {
        this.vtuTime = vtuTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.vtu_pic
     *
     * @return the value of vtu_share.vtu_pic
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public String getVtuPic() {
        return vtuPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.vtu_pic
     *
     * @param vtuPic the value for vtu_share.vtu_pic
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setVtuPic(String vtuPic) {
        this.vtuPic = vtuPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.create_date
     *
     * @return the value of vtu_share.create_date
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.create_date
     *
     * @param createDate the value for vtu_share.create_date
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.is_share
     *
     * @return the value of vtu_share.is_share
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Boolean getIsShare() {
        return isShare;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.is_share
     *
     * @param isShare the value for vtu_share.is_share
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setIsShare(Boolean isShare) {
        this.isShare = isShare;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.share_time
     *
     * @return the value of vtu_share.share_time
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Date getShareTime() {
        return shareTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.share_time
     *
     * @param shareTime the value for vtu_share.share_time
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column vtu_share.share_count
     *
     * @return the value of vtu_share.share_count
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public Integer getShareCount() {
        return shareCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column vtu_share.share_count
     *
     * @param shareCount the value for vtu_share.share_count
     *
     * @mbg.generated Sat Sep 16 10:28:10 CST 2017
     */
    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }
}