package org.ehais.shop.model;

import java.io.Serializable;

public class HaiBusinessWithBLOBs extends HaiBusiness implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.remark
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_business.intro
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    private String intro;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_business
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.remark
     *
     * @return the value of hai_business.remark
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.remark
     *
     * @param remark the value for hai_business.remark
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_business.intro
     *
     * @return the value of hai_business.intro
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    public String getIntro() {
        return intro;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_business.intro
     *
     * @param intro the value for hai_business.intro
     *
     * @mbg.generated Fri Jun 22 11:34:38 CST 2018
     */
    public void setIntro(String intro) {
        this.intro = intro;
    }
}