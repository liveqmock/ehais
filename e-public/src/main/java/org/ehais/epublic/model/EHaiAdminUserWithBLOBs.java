package org.ehais.epublic.model;

import java.io.Serializable;

public class EHaiAdminUserWithBLOBs extends EHaiAdminUser implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.action_list
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    private String actionList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.nav_list
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    private String navList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.todolist
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    private String todolist;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_admin_user
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.action_list
     *
     * @return the value of hai_admin_user.action_list
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    public String getActionList() {
        return actionList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.action_list
     *
     * @param actionList the value for hai_admin_user.action_list
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    public void setActionList(String actionList) {
        this.actionList = actionList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.nav_list
     *
     * @return the value of hai_admin_user.nav_list
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    public String getNavList() {
        return navList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.nav_list
     *
     * @param navList the value for hai_admin_user.nav_list
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    public void setNavList(String navList) {
        this.navList = navList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.todolist
     *
     * @return the value of hai_admin_user.todolist
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    public String getTodolist() {
        return todolist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hai_admin_user.todolist
     *
     * @param todolist the value for hai_admin_user.todolist
     *
     * @mbggenerated Fri Apr 01 13:54:05 CST 2016
     */
    public void setTodolist(String todolist) {
        this.todolist = todolist;
    }
}