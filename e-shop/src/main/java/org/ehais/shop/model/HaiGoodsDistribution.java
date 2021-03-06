package org.ehais.shop.model;

import java.io.Serializable;

public class HaiGoodsDistribution implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.goods_id
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Long goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.join_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Boolean joinDistribution;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.default_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Boolean defaultDistribution;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.distribution_type
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer distributionType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.distribution_percentage
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer distributionPercentage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.distribution_money
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer distributionMoney;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.first_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer firstValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.second_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer secondValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.third_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer thirdValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_goods_distribution.to_integral
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private Integer toIntegral;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_goods_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.goods_id
     *
     * @return the value of hai_goods_distribution.goods_id
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.goods_id
     *
     * @param goodsId the value for hai_goods_distribution.goods_id
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.join_distribution
     *
     * @return the value of hai_goods_distribution.join_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Boolean getJoinDistribution() {
        return joinDistribution;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.join_distribution
     *
     * @param joinDistribution the value for hai_goods_distribution.join_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setJoinDistribution(Boolean joinDistribution) {
        this.joinDistribution = joinDistribution;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.default_distribution
     *
     * @return the value of hai_goods_distribution.default_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Boolean getDefaultDistribution() {
        return defaultDistribution;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.default_distribution
     *
     * @param defaultDistribution the value for hai_goods_distribution.default_distribution
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setDefaultDistribution(Boolean defaultDistribution) {
        this.defaultDistribution = defaultDistribution;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.distribution_type
     *
     * @return the value of hai_goods_distribution.distribution_type
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getDistributionType() {
        return distributionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.distribution_type
     *
     * @param distributionType the value for hai_goods_distribution.distribution_type
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setDistributionType(Integer distributionType) {
        this.distributionType = distributionType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.distribution_percentage
     *
     * @return the value of hai_goods_distribution.distribution_percentage
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getDistributionPercentage() {
        return distributionPercentage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.distribution_percentage
     *
     * @param distributionPercentage the value for hai_goods_distribution.distribution_percentage
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setDistributionPercentage(Integer distributionPercentage) {
        this.distributionPercentage = distributionPercentage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.distribution_money
     *
     * @return the value of hai_goods_distribution.distribution_money
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getDistributionMoney() {
        return distributionMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.distribution_money
     *
     * @param distributionMoney the value for hai_goods_distribution.distribution_money
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setDistributionMoney(Integer distributionMoney) {
        this.distributionMoney = distributionMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.first_value
     *
     * @return the value of hai_goods_distribution.first_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getFirstValue() {
        return firstValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.first_value
     *
     * @param firstValue the value for hai_goods_distribution.first_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setFirstValue(Integer firstValue) {
        this.firstValue = firstValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.second_value
     *
     * @return the value of hai_goods_distribution.second_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getSecondValue() {
        return secondValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.second_value
     *
     * @param secondValue the value for hai_goods_distribution.second_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setSecondValue(Integer secondValue) {
        this.secondValue = secondValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.third_value
     *
     * @return the value of hai_goods_distribution.third_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getThirdValue() {
        return thirdValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.third_value
     *
     * @param thirdValue the value for hai_goods_distribution.third_value
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setThirdValue(Integer thirdValue) {
        this.thirdValue = thirdValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_goods_distribution.to_integral
     *
     * @return the value of hai_goods_distribution.to_integral
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public Integer getToIntegral() {
        return toIntegral;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_goods_distribution.to_integral
     *
     * @param toIntegral the value for hai_goods_distribution.to_integral
     *
     * @mbg.generated Sun Oct 22 16:31:09 CST 2017
     */
    public void setToIntegral(Integer toIntegral) {
        this.toIntegral = toIntegral;
    }
}