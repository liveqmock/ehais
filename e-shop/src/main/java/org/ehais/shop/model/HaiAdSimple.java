package org.ehais.shop.model;

import java.io.Serializable;
import java.util.Date;

public class HaiAdSimple implements Serializable {
    private Integer adId;
    private Integer positionId;
    private String adName;
    private String adLink;
    private String adPic;

    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_id
     *
     * @return the value of hai_ad.ad_id
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public Integer getAdId() {
        return adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_id
     *
     * @param adId the value for hai_ad.ad_id
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.position_id
     *
     * @return the value of hai_ad.position_id
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public Integer getPositionId() {
        return positionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.position_id
     *
     * @param positionId the value for hai_ad.position_id
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_name
     *
     * @return the value of hai_ad.ad_name
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public String getAdName() {
        return adName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_name
     *
     * @param adName the value for hai_ad.ad_name
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public void setAdName(String adName) {
        this.adName = adName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_link
     *
     * @return the value of hai_ad.ad_link
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public String getAdLink() {
        return adLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_link
     *
     * @param adLink the value for hai_ad.ad_link
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_ad.ad_pic
     *
     * @return the value of hai_ad.ad_pic
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public String getAdPic() {
        return adPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_ad.ad_pic
     *
     * @param adPic the value for hai_ad.ad_pic
     *
     * @mbg.generated Tue Sep 12 17:15:27 CST 2017
     */
    public void setAdPic(String adPic) {
        this.adPic = adPic;
    }


}