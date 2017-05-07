package org.ehais.shop.model;

import java.io.Serializable;

public class HaiJobWithBLOBs extends HaiJob implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.job_requirement
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String jobRequirement;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.job_responsibility
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String jobResponsibility;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_job.notice
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private String notice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_job
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.job_requirement
     *
     * @return the value of hai_job.job_requirement
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getJobRequirement() {
        return jobRequirement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.job_requirement
     *
     * @param jobRequirement the value for hai_job.job_requirement
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.job_responsibility
     *
     * @return the value of hai_job.job_responsibility
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getJobResponsibility() {
        return jobResponsibility;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.job_responsibility
     *
     * @param jobResponsibility the value for hai_job.job_responsibility
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setJobResponsibility(String jobResponsibility) {
        this.jobResponsibility = jobResponsibility;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_job.notice
     *
     * @return the value of hai_job.notice
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public String getNotice() {
        return notice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_job.notice
     *
     * @param notice the value for hai_job.notice
     *
     * @mbg.generated Sun Apr 30 13:10:28 CST 2017
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }
}