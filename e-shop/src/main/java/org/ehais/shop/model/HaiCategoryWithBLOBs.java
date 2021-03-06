package org.ehais.shop.model;

import java.io.Serializable;

public class HaiCategoryWithBLOBs extends HaiCategory implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String remark;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.filter_attr
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String filterAttr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.sub_parent_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String subParentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.brand_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private String brandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_category.remark
     *
     * @return the value of hai_category.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_category.remark
     *
     * @param remark the value for hai_category.remark
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_category.filter_attr
     *
     * @return the value of hai_category.filter_attr
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getFilterAttr() {
        return filterAttr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_category.filter_attr
     *
     * @param filterAttr the value for hai_category.filter_attr
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setFilterAttr(String filterAttr) {
        this.filterAttr = filterAttr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_category.sub_parent_id
     *
     * @return the value of hai_category.sub_parent_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getSubParentId() {
        return subParentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_category.sub_parent_id
     *
     * @param subParentId the value for hai_category.sub_parent_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setSubParentId(String subParentId) {
        this.subParentId = subParentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_category.brand_id
     *
     * @return the value of hai_category.brand_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_category.brand_id
     *
     * @param brandId the value for hai_category.brand_id
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}