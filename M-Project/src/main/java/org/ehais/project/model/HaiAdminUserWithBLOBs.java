package org.ehais.project.model;

public class HaiAdminUserWithBLOBs extends HaiAdminUser {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.action_list
     *
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
     */
    private String actionList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.nav_list
     *
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
     */
    private String navList;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hai_admin_user.todolist
     *
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
     */
    private String todolist;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hai_admin_user.action_list
     *
     * @return the value of hai_admin_user.action_list
     *
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
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
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
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
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
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
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
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
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
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
     * @mbggenerated Tue Dec 29 17:17:57 CST 2015
     */
    public void setTodolist(String todolist) {
        this.todolist = todolist;
    }
}