package org.ehais.model;

import java.io.Serializable;

public class HaiCategoryWithBLOBs extends HaiCategory implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.filter_attr
     *
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
     */
    protected String filterAttr;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.sub_parent_id
     *
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
     */
    protected String subParentId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_category.brand_id
     *
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
     */
    protected String brandId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_category
     *
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
     */
    protected static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_category.filter_attr
     *
     * @return the value of hai_category.filter_attr
     *
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
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
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
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
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
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
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
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
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
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
     * @mbg.generated Tue Oct 10 14:29:58 CST 2017
     */
    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }
}