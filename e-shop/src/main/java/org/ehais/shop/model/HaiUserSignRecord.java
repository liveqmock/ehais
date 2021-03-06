package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiUserSignRecord implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_user_sign_record.sign_id
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    private Long signId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_user_sign_record.user_id
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_user_sign_record.sign_time
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    private Date signTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_user_sign_record.sign_count
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    private Integer signCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_user_sign_record
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_user_sign_record.sign_id
     *
     * @return the value of hai_user_sign_record.sign_id
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public Long getSignId() {
        return signId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_user_sign_record.sign_id
     *
     * @param signId the value for hai_user_sign_record.sign_id
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public void setSignId(Long signId) {
        this.signId = signId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_user_sign_record.user_id
     *
     * @return the value of hai_user_sign_record.user_id
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_user_sign_record.user_id
     *
     * @param userId the value for hai_user_sign_record.user_id
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_user_sign_record.sign_time
     *
     * @return the value of hai_user_sign_record.sign_time
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public Date getSignTime() {
        return signTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_user_sign_record.sign_time
     *
     * @param signTime the value for hai_user_sign_record.sign_time
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_user_sign_record.sign_count
     *
     * @return the value of hai_user_sign_record.sign_count
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public Integer getSignCount() {
        return signCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_user_sign_record.sign_count
     *
     * @param signCount the value for hai_user_sign_record.sign_count
     *
     * @mbg.generated Thu Oct 12 08:17:20 CST 2017
     */
    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }
}