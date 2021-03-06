package org.ehais.shop.model;

import java.io.Serializable;

public class HaiCartWithBLOBs extends HaiCart implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_cart.goods_attr
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    private String goodsAttr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_cart.goods_attr_json
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    private String goodsAttrJson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_cart
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_cart.goods_attr
     *
     * @return the value of hai_cart.goods_attr
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    public String getGoodsAttr() {
        return goodsAttr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_cart.goods_attr
     *
     * @param goodsAttr the value for hai_cart.goods_attr
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    public void setGoodsAttr(String goodsAttr) {
        this.goodsAttr = goodsAttr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_cart.goods_attr_json
     *
     * @return the value of hai_cart.goods_attr_json
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    public String getGoodsAttrJson() {
        return goodsAttrJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_cart.goods_attr_json
     *
     * @param goodsAttrJson the value for hai_cart.goods_attr_json
     *
     * @mbg.generated Wed Aug 30 09:48:17 CST 2017
     */
    public void setGoodsAttrJson(String goodsAttrJson) {
        this.goodsAttrJson = goodsAttrJson;
    }
}