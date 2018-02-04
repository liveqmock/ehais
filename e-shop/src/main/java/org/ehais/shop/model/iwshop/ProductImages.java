package org.ehais.shop.model.iwshop;

import java.io.Serializable;

public class ProductImages implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_images.image_id
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    private Integer imageId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_images.product_id
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_images.image_path
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    private String imagePath;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_images.image_sort
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    private Byte imageSort;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column product_images.image_type
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    private Integer imageType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_images
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_images.image_id
     *
     * @return the value of product_images.image_id
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public Integer getImageId() {
        return imageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_images.image_id
     *
     * @param imageId the value for product_images.image_id
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_images.product_id
     *
     * @return the value of product_images.product_id
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_images.product_id
     *
     * @param productId the value for product_images.product_id
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_images.image_path
     *
     * @return the value of product_images.image_path
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_images.image_path
     *
     * @param imagePath the value for product_images.image_path
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_images.image_sort
     *
     * @return the value of product_images.image_sort
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public Byte getImageSort() {
        return imageSort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_images.image_sort
     *
     * @param imageSort the value for product_images.image_sort
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public void setImageSort(Byte imageSort) {
        this.imageSort = imageSort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column product_images.image_type
     *
     * @return the value of product_images.image_type
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public Integer getImageType() {
        return imageType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column product_images.image_type
     *
     * @param imageType the value for product_images.image_type
     *
     * @mbg.generated Thu May 04 11:24:04 CST 2017
     */
    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }
}