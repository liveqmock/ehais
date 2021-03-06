package org.ehais.epublic.model;

import java.io.Serializable;

public class EHaiAdminUser implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.admin_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Long adminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.user_name
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.email
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.password
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.ec_salt
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String ecSalt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.add_time
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Integer addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.last_login
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Integer lastLogin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.last_ip
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String lastIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.lang_type
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String langType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.agency_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Integer agencyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.business_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Integer businessId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.store_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.project_folder
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String projectFolder;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.classify
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private String classify;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.partner_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private Integer partnerId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_admin_user
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.admin_id
     *
     * @return the value of hai_admin_user.admin_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.admin_id
     *
     * @param adminId the value for hai_admin_user.admin_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.user_name
     *
     * @return the value of hai_admin_user.user_name
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.user_name
     *
     * @param userName the value for hai_admin_user.user_name
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.email
     *
     * @return the value of hai_admin_user.email
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.email
     *
     * @param email the value for hai_admin_user.email
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.password
     *
     * @return the value of hai_admin_user.password
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.password
     *
     * @param password the value for hai_admin_user.password
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.ec_salt
     *
     * @return the value of hai_admin_user.ec_salt
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getEcSalt() {
        return ecSalt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.ec_salt
     *
     * @param ecSalt the value for hai_admin_user.ec_salt
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setEcSalt(String ecSalt) {
        this.ecSalt = ecSalt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.add_time
     *
     * @return the value of hai_admin_user.add_time
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.add_time
     *
     * @param addTime the value for hai_admin_user.add_time
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.last_login
     *
     * @return the value of hai_admin_user.last_login
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Integer getLastLogin() {
        return lastLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.last_login
     *
     * @param lastLogin the value for hai_admin_user.last_login
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setLastLogin(Integer lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.last_ip
     *
     * @return the value of hai_admin_user.last_ip
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.last_ip
     *
     * @param lastIp the value for hai_admin_user.last_ip
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.lang_type
     *
     * @return the value of hai_admin_user.lang_type
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getLangType() {
        return langType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.lang_type
     *
     * @param langType the value for hai_admin_user.lang_type
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setLangType(String langType) {
        this.langType = langType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.agency_id
     *
     * @return the value of hai_admin_user.agency_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Integer getAgencyId() {
        return agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.agency_id
     *
     * @param agencyId the value for hai_admin_user.agency_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.business_id
     *
     * @return the value of hai_admin_user.business_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.business_id
     *
     * @param businessId the value for hai_admin_user.business_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.store_id
     *
     * @return the value of hai_admin_user.store_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.store_id
     *
     * @param storeId the value for hai_admin_user.store_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.project_folder
     *
     * @return the value of hai_admin_user.project_folder
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getProjectFolder() {
        return projectFolder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.project_folder
     *
     * @param projectFolder the value for hai_admin_user.project_folder
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.classify
     *
     * @return the value of hai_admin_user.classify
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public String getClassify() {
        return classify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.classify
     *
     * @param classify the value for hai_admin_user.classify
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setClassify(String classify) {
        this.classify = classify;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.partner_id
     *
     * @return the value of hai_admin_user.partner_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public Integer getPartnerId() {
        return partnerId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.partner_id
     *
     * @param partnerId the value for hai_admin_user.partner_id
     *
     * @mbg.generated Tue Sep 12 10:45:40 CST 2017
     */
    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
}