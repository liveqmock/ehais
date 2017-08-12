package org.ehais.shop.model.tp;

import java.io.Serializable;

public class TpAdmin implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.admin_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private Long adminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.user_name
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.email
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.password
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.ec_salt
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String ecSalt;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.add_time
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private Integer addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.last_login
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private Integer lastLogin;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.last_ip
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String lastIp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.lang_type
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String langType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.agency_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private Long agencyId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.suppliers_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private Long suppliersId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.role_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private Short roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_admin
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.admin_id
     *
     * @return the value of tp_admin.admin_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.admin_id
     *
     * @param adminId the value for tp_admin.admin_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.user_name
     *
     * @return the value of tp_admin.user_name
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.user_name
     *
     * @param userName the value for tp_admin.user_name
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.email
     *
     * @return the value of tp_admin.email
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.email
     *
     * @param email the value for tp_admin.email
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.password
     *
     * @return the value of tp_admin.password
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.password
     *
     * @param password the value for tp_admin.password
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.ec_salt
     *
     * @return the value of tp_admin.ec_salt
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getEcSalt() {
        return ecSalt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.ec_salt
     *
     * @param ecSalt the value for tp_admin.ec_salt
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setEcSalt(String ecSalt) {
        this.ecSalt = ecSalt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.add_time
     *
     * @return the value of tp_admin.add_time
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public Integer getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.add_time
     *
     * @param addTime the value for tp_admin.add_time
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.last_login
     *
     * @return the value of tp_admin.last_login
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public Integer getLastLogin() {
        return lastLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.last_login
     *
     * @param lastLogin the value for tp_admin.last_login
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setLastLogin(Integer lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.last_ip
     *
     * @return the value of tp_admin.last_ip
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getLastIp() {
        return lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.last_ip
     *
     * @param lastIp the value for tp_admin.last_ip
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.lang_type
     *
     * @return the value of tp_admin.lang_type
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getLangType() {
        return langType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.lang_type
     *
     * @param langType the value for tp_admin.lang_type
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setLangType(String langType) {
        this.langType = langType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.agency_id
     *
     * @return the value of tp_admin.agency_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public Long getAgencyId() {
        return agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.agency_id
     *
     * @param agencyId the value for tp_admin.agency_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.suppliers_id
     *
     * @return the value of tp_admin.suppliers_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public Long getSuppliersId() {
        return suppliersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.suppliers_id
     *
     * @param suppliersId the value for tp_admin.suppliers_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setSuppliersId(Long suppliersId) {
        this.suppliersId = suppliersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.role_id
     *
     * @return the value of tp_admin.role_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public Short getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.role_id
     *
     * @param roleId the value for tp_admin.role_id
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }
}