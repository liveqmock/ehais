package org.ehais.epublic.model;

import java.io.Serializable;

public class ThinkRoleAdminKey implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column think_role_admin.admin_id
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    private Long adminId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column think_role_admin.role_id
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    private Integer roleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table think_role_admin
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column think_role_admin.admin_id
     *
     * @return the value of think_role_admin.admin_id
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    public Long getAdminId() {
        return adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column think_role_admin.admin_id
     *
     * @param adminId the value for think_role_admin.admin_id
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column think_role_admin.role_id
     *
     * @return the value of think_role_admin.role_id
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column think_role_admin.role_id
     *
     * @param roleId the value for think_role_admin.role_id
     *
     * @mbg.generated Sat Oct 28 23:35:53 CST 2017
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}