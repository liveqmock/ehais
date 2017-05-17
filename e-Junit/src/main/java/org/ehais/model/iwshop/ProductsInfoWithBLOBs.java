package org.ehais.model.iwshop;

import java.io.Serializable;

public class ProductsInfoWithBLOBs extends ProductsInfo implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column products_info.product_desc
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    private String productDesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column products_info.product_subtitle
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    private String productSubtitle;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column products_info.product_desc
     *
     * @return the value of products_info.product_desc
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column products_info.product_desc
     *
     * @param productDesc the value for products_info.product_desc
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column products_info.product_subtitle
     *
     * @return the value of products_info.product_subtitle
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public String getProductSubtitle() {
        return productSubtitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column products_info.product_subtitle
     *
     * @param productSubtitle the value for products_info.product_subtitle
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }
}