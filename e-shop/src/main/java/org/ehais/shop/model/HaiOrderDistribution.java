package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiOrderDistribution implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.order_distribution_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Long orderDistributionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.order_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Long orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.goods_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Long goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.goods_number
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Integer goodsNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.goods_price
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Integer goodsPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.user_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.distribution_level
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Integer distributionLevel;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.distribution_price
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Integer distributionPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.distribution_user_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Long distributionUserId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.add_time
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Date addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_order_distribution.current_money
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private Integer currentMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.order_distribution_id
     *
     * @return the value of hai_order_distribution.order_distribution_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Long getOrderDistributionId() {
        return orderDistributionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.order_distribution_id
     *
     * @param orderDistributionId the value for hai_order_distribution.order_distribution_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setOrderDistributionId(Long orderDistributionId) {
        this.orderDistributionId = orderDistributionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.order_id
     *
     * @return the value of hai_order_distribution.order_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.order_id
     *
     * @param orderId the value for hai_order_distribution.order_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.goods_id
     *
     * @return the value of hai_order_distribution.goods_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.goods_id
     *
     * @param goodsId the value for hai_order_distribution.goods_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.goods_number
     *
     * @return the value of hai_order_distribution.goods_number
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.goods_number
     *
     * @param goodsNumber the value for hai_order_distribution.goods_number
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.goods_price
     *
     * @return the value of hai_order_distribution.goods_price
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.goods_price
     *
     * @param goodsPrice the value for hai_order_distribution.goods_price
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.user_id
     *
     * @return the value of hai_order_distribution.user_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.user_id
     *
     * @param userId the value for hai_order_distribution.user_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.distribution_level
     *
     * @return the value of hai_order_distribution.distribution_level
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Integer getDistributionLevel() {
        return distributionLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.distribution_level
     *
     * @param distributionLevel the value for hai_order_distribution.distribution_level
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setDistributionLevel(Integer distributionLevel) {
        this.distributionLevel = distributionLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.distribution_price
     *
     * @return the value of hai_order_distribution.distribution_price
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Integer getDistributionPrice() {
        return distributionPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.distribution_price
     *
     * @param distributionPrice the value for hai_order_distribution.distribution_price
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setDistributionPrice(Integer distributionPrice) {
        this.distributionPrice = distributionPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.distribution_user_id
     *
     * @return the value of hai_order_distribution.distribution_user_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Long getDistributionUserId() {
        return distributionUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.distribution_user_id
     *
     * @param distributionUserId the value for hai_order_distribution.distribution_user_id
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setDistributionUserId(Long distributionUserId) {
        this.distributionUserId = distributionUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.add_time
     *
     * @return the value of hai_order_distribution.add_time
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.add_time
     *
     * @param addTime the value for hai_order_distribution.add_time
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_order_distribution.current_money
     *
     * @return the value of hai_order_distribution.current_money
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public Integer getCurrentMoney() {
        return currentMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_order_distribution.current_money
     *
     * @param currentMoney the value for hai_order_distribution.current_money
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    public void setCurrentMoney(Integer currentMoney) {
        this.currentMoney = currentMoney;
    }
}