package org.ehais.shop.model;

import java.io.Serializable;

public class HaiDiningPrintTime implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_dining_print_time.store_id
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_dining_print_time.print_time
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    private Long printTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_dining_print_time
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_dining_print_time.store_id
     *
     * @return the value of hai_dining_print_time.store_id
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_dining_print_time.store_id
     *
     * @param storeId the value for hai_dining_print_time.store_id
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_dining_print_time.print_time
     *
     * @return the value of hai_dining_print_time.print_time
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    public Long getPrintTime() {
        return printTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_dining_print_time.print_time
     *
     * @param printTime the value for hai_dining_print_time.print_time
     *
     * @mbg.generated Thu Jan 04 23:31:41 CST 2018
     */
    public void setPrintTime(Long printTime) {
        this.printTime = printTime;
    }
}