package org.ehais.shop.model.tp;

import java.io.Serializable;

public class TpAdminWithBLOBs extends TpAdmin implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.nav_list
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String navList;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tp_admin.todolist
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private String todolist;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_admin
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.nav_list
     *
     * @return the value of tp_admin.nav_list
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getNavList() {
        return navList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.nav_list
     *
     * @param navList the value for tp_admin.nav_list
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setNavList(String navList) {
        this.navList = navList;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tp_admin.todolist
     *
     * @return the value of tp_admin.todolist
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public String getTodolist() {
        return todolist;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tp_admin.todolist
     *
     * @param todolist the value for tp_admin.todolist
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    public void setTodolist(String todolist) {
        this.todolist = todolist;
    }
}