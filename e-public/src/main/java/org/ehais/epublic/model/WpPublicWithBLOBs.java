package org.ehais.epublic.model;

import java.io.Serializable;

public class WpPublicWithBLOBs extends WpPublic implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wp_public.addon_config
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    private String addonConfig;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column wp_public.addon_status
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    private String addonStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wp_public.addon_config
     *
     * @return the value of wp_public.addon_config
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    public String getAddonConfig() {
        return addonConfig;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wp_public.addon_config
     *
     * @param addonConfig the value for wp_public.addon_config
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    public void setAddonConfig(String addonConfig) {
        this.addonConfig = addonConfig;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column wp_public.addon_status
     *
     * @return the value of wp_public.addon_status
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    public String getAddonStatus() {
        return addonStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column wp_public.addon_status
     *
     * @param addonStatus the value for wp_public.addon_status
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    public void setAddonStatus(String addonStatus) {
        this.addonStatus = addonStatus;
    }
}