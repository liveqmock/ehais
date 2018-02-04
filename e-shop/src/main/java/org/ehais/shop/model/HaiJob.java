package org.ehais.shop.model;

import java.io.Serializable;

public class HaiJob implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.job_id
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private Long jobId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.position
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String position;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.province
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String province;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.workcity
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String workcity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.recruitstartday
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String recruitstartday;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.recruitendday
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String recruitendday;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.workyears
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private Integer workyears;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.minage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private Integer minage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.maxage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private Integer maxage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.phone
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String phone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.contactperson
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String contactperson;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.fax
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String fax;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.email
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.address
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String address;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.zip
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String zip;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.marriage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String marriage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.language
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String language;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.interviewplace
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String interviewplace;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.companyname
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String companyname;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.store_id
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private Integer storeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_job
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.job_id
     *
     * @return the value of hai_job.job_id
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.job_id
     *
     * @param jobId the value for hai_job.job_id
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.position
     *
     * @return the value of hai_job.position
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getPosition() {
        return position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.position
     *
     * @param position the value for hai_job.position
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.province
     *
     * @return the value of hai_job.province
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getProvince() {
        return province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.province
     *
     * @param province the value for hai_job.province
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.workcity
     *
     * @return the value of hai_job.workcity
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getWorkcity() {
        return workcity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.workcity
     *
     * @param workcity the value for hai_job.workcity
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setWorkcity(String workcity) {
        this.workcity = workcity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.recruitstartday
     *
     * @return the value of hai_job.recruitstartday
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getRecruitstartday() {
        return recruitstartday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.recruitstartday
     *
     * @param recruitstartday the value for hai_job.recruitstartday
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setRecruitstartday(String recruitstartday) {
        this.recruitstartday = recruitstartday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.recruitendday
     *
     * @return the value of hai_job.recruitendday
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getRecruitendday() {
        return recruitendday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.recruitendday
     *
     * @param recruitendday the value for hai_job.recruitendday
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setRecruitendday(String recruitendday) {
        this.recruitendday = recruitendday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.workyears
     *
     * @return the value of hai_job.workyears
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public Integer getWorkyears() {
        return workyears;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.workyears
     *
     * @param workyears the value for hai_job.workyears
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setWorkyears(Integer workyears) {
        this.workyears = workyears;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.minage
     *
     * @return the value of hai_job.minage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public Integer getMinage() {
        return minage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.minage
     *
     * @param minage the value for hai_job.minage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setMinage(Integer minage) {
        this.minage = minage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.maxage
     *
     * @return the value of hai_job.maxage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public Integer getMaxage() {
        return maxage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.maxage
     *
     * @param maxage the value for hai_job.maxage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setMaxage(Integer maxage) {
        this.maxage = maxage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.phone
     *
     * @return the value of hai_job.phone
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.phone
     *
     * @param phone the value for hai_job.phone
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.contactperson
     *
     * @return the value of hai_job.contactperson
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getContactperson() {
        return contactperson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.contactperson
     *
     * @param contactperson the value for hai_job.contactperson
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.fax
     *
     * @return the value of hai_job.fax
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getFax() {
        return fax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.fax
     *
     * @param fax the value for hai_job.fax
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.email
     *
     * @return the value of hai_job.email
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.email
     *
     * @param email the value for hai_job.email
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.address
     *
     * @return the value of hai_job.address
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.address
     *
     * @param address the value for hai_job.address
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.zip
     *
     * @return the value of hai_job.zip
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getZip() {
        return zip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.zip
     *
     * @param zip the value for hai_job.zip
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.marriage
     *
     * @return the value of hai_job.marriage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getMarriage() {
        return marriage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.marriage
     *
     * @param marriage the value for hai_job.marriage
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.language
     *
     * @return the value of hai_job.language
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getLanguage() {
        return language;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.language
     *
     * @param language the value for hai_job.language
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.interviewplace
     *
     * @return the value of hai_job.interviewplace
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getInterviewplace() {
        return interviewplace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.interviewplace
     *
     * @param interviewplace the value for hai_job.interviewplace
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setInterviewplace(String interviewplace) {
        this.interviewplace = interviewplace;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.companyname
     *
     * @return the value of hai_job.companyname
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getCompanyname() {
        return companyname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.companyname
     *
     * @param companyname the value for hai_job.companyname
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.store_id
     *
     * @return the value of hai_job.store_id
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.store_id
     *
     * @param storeId the value for hai_job.store_id
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}