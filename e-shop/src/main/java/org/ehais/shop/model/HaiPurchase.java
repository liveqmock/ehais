package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class HaiPurchase implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.purchase_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Integer purchaseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.purchase_no
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    @NotBlank(message = "进货单不能为空")
    private String purchaseNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.order_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Long orderId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.order_sn_rmk
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private String orderSnRmk;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.goods_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Long goodsId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.goods_name_rmk
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private String goodsNameRmk;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.purchase_time
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date purchaseTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.quantity
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.warehouse_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Integer warehouseId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.store_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Integer storeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.create_date
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Date createDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.update_date
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private Date updateDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_purchase.remark
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_purchase
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.purchase_id
     *
     * @return the value of hai_purchase.purchase_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Integer getPurchaseId() {
        return purchaseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.purchase_id
     *
     * @param purchaseId the value for hai_purchase.purchase_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.purchase_no
     *
     * @return the value of hai_purchase.purchase_no
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public String getPurchaseNo() {
        return purchaseNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.purchase_no
     *
     * @param purchaseNo the value for hai_purchase.purchase_no
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.order_id
     *
     * @return the value of hai_purchase.order_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.order_id
     *
     * @param orderId the value for hai_purchase.order_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.order_sn_rmk
     *
     * @return the value of hai_purchase.order_sn_rmk
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public String getOrderSnRmk() {
        return orderSnRmk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.order_sn_rmk
     *
     * @param orderSnRmk the value for hai_purchase.order_sn_rmk
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setOrderSnRmk(String orderSnRmk) {
        this.orderSnRmk = orderSnRmk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.goods_id
     *
     * @return the value of hai_purchase.goods_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.goods_id
     *
     * @param goodsId the value for hai_purchase.goods_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.goods_name_rmk
     *
     * @return the value of hai_purchase.goods_name_rmk
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public String getGoodsNameRmk() {
        return goodsNameRmk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.goods_name_rmk
     *
     * @param goodsNameRmk the value for hai_purchase.goods_name_rmk
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setGoodsNameRmk(String goodsNameRmk) {
        this.goodsNameRmk = goodsNameRmk;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.purchase_time
     *
     * @return the value of hai_purchase.purchase_time
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Date getPurchaseTime() {
        return purchaseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.purchase_time
     *
     * @param purchaseTime the value for hai_purchase.purchase_time
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.quantity
     *
     * @return the value of hai_purchase.quantity
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.quantity
     *
     * @param quantity the value for hai_purchase.quantity
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.warehouse_id
     *
     * @return the value of hai_purchase.warehouse_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.warehouse_id
     *
     * @param warehouseId the value for hai_purchase.warehouse_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.store_id
     *
     * @return the value of hai_purchase.store_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.store_id
     *
     * @param storeId the value for hai_purchase.store_id
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.create_date
     *
     * @return the value of hai_purchase.create_date
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.create_date
     *
     * @param createDate the value for hai_purchase.create_date
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.update_date
     *
     * @return the value of hai_purchase.update_date
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.update_date
     *
     * @param updateDate the value for hai_purchase.update_date
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_purchase.remark
     *
     * @return the value of hai_purchase.remark
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_purchase.remark
     *
     * @param remark the value for hai_purchase.remark
     *
     * @mbg.generated Sat Oct 21 21:16:32 CST 2017
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}