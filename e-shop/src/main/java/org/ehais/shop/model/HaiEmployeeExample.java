package org.ehais.shop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.ehais.tools.CriteriaObject;

public class HaiEmployeeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected int limitStart = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public HaiEmployeeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void CriteriaStoreId(Criteria c, CriteriaObject co) {
        if(co == null || co.getOperator() == null)return ;
        if(co.getOperator().equals("eq")){
            c.andStoreIdEqualTo(co.getStoreId());
        }else if(co.getOperator().equals("in")){
            c.andStoreIdIn(co.getStoreList());
        }else if(co.getOperator().equals("user_eq")){
            //c.andUserIdEqualTo(co.getUserId());
        }
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andEmployeeIdIsNull() {
            addCriterion("employee_id is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIsNotNull() {
            addCriterion("employee_id is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdEqualTo(Integer value) {
            addCriterion("employee_id =", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotEqualTo(Integer value) {
            addCriterion("employee_id <>", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThan(Integer value) {
            addCriterion("employee_id >", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("employee_id >=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThan(Integer value) {
            addCriterion("employee_id <", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdLessThanOrEqualTo(Integer value) {
            addCriterion("employee_id <=", value, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdIn(List<Integer> values) {
            addCriterion("employee_id in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotIn(List<Integer> values) {
            addCriterion("employee_id not in", values, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdBetween(Integer value1, Integer value2) {
            addCriterion("employee_id between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("employee_id not between", value1, value2, "employeeId");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeIsNull() {
            addCriterion("employee_code is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeIsNotNull() {
            addCriterion("employee_code is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeEqualTo(String value) {
            addCriterion("employee_code =", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeNotEqualTo(String value) {
            addCriterion("employee_code <>", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeGreaterThan(String value) {
            addCriterion("employee_code >", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("employee_code >=", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeLessThan(String value) {
            addCriterion("employee_code <", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeLessThanOrEqualTo(String value) {
            addCriterion("employee_code <=", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeLike(String value) {
            addCriterion("employee_code like", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeNotLike(String value) {
            addCriterion("employee_code not like", value, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeIn(List<String> values) {
            addCriterion("employee_code in", values, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeNotIn(List<String> values) {
            addCriterion("employee_code not in", values, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeBetween(String value1, String value2) {
            addCriterion("employee_code between", value1, value2, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeNotBetween(String value1, String value2) {
            addCriterion("employee_code not between", value1, value2, "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIsNull() {
            addCriterion("employee_name is null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIsNotNull() {
            addCriterion("employee_name is not null");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameEqualTo(String value) {
            addCriterion("employee_name =", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotEqualTo(String value) {
            addCriterion("employee_name <>", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameGreaterThan(String value) {
            addCriterion("employee_name >", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("employee_name >=", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLessThan(String value) {
            addCriterion("employee_name <", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLessThanOrEqualTo(String value) {
            addCriterion("employee_name <=", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLike(String value) {
            addCriterion("employee_name like", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotLike(String value) {
            addCriterion("employee_name not like", value, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameIn(List<String> values) {
            addCriterion("employee_name in", values, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotIn(List<String> values) {
            addCriterion("employee_name not in", values, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameBetween(String value1, String value2) {
            addCriterion("employee_name between", value1, value2, "employeeName");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameNotBetween(String value1, String value2) {
            addCriterion("employee_name not between", value1, value2, "employeeName");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNull() {
            addCriterion("phone is null");
            return (Criteria) this;
        }

        public Criteria andPhoneIsNotNull() {
            addCriterion("phone is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneEqualTo(String value) {
            addCriterion("phone =", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotEqualTo(String value) {
            addCriterion("phone <>", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThan(String value) {
            addCriterion("phone >", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("phone >=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThan(String value) {
            addCriterion("phone <", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLessThanOrEqualTo(String value) {
            addCriterion("phone <=", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneLike(String value) {
            addCriterion("phone like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotLike(String value) {
            addCriterion("phone not like", value, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneIn(List<String> values) {
            addCriterion("phone in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotIn(List<String> values) {
            addCriterion("phone not in", values, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneBetween(String value1, String value2) {
            addCriterion("phone between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andPhoneNotBetween(String value1, String value2) {
            addCriterion("phone not between", value1, value2, "phone");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNull() {
            addCriterion("store_id is null");
            return (Criteria) this;
        }

        public Criteria andStoreIdIsNotNull() {
            addCriterion("store_id is not null");
            return (Criteria) this;
        }

        public Criteria andStoreIdEqualTo(Integer value) {
            addCriterion("store_id =", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotEqualTo(Integer value) {
            addCriterion("store_id <>", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThan(Integer value) {
            addCriterion("store_id >", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("store_id >=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThan(Integer value) {
            addCriterion("store_id <", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdLessThanOrEqualTo(Integer value) {
            addCriterion("store_id <=", value, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdIn(List<Integer> values) {
            addCriterion("store_id in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotIn(List<Integer> values) {
            addCriterion("store_id not in", values, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdBetween(Integer value1, Integer value2) {
            addCriterion("store_id between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andStoreIdNotBetween(Integer value1, Integer value2) {
            addCriterion("store_id not between", value1, value2, "storeId");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("update_date is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("update_date is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("update_date =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("update_date <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("update_date >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("update_date >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("update_date <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("update_date <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("update_date in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("update_date not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("update_date between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("update_date not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNull() {
            addCriterion("last_update_date is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIsNotNull() {
            addCriterion("last_update_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateEqualTo(Date value) {
            addCriterion("last_update_date =", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotEqualTo(Date value) {
            addCriterion("last_update_date <>", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThan(Date value) {
            addCriterion("last_update_date >", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_date >=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThan(Date value) {
            addCriterion("last_update_date <", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("last_update_date <=", value, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateIn(List<Date> values) {
            addCriterion("last_update_date in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotIn(List<Date> values) {
            addCriterion("last_update_date not in", values, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateBetween(Date value1, Date value2) {
            addCriterion("last_update_date between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("last_update_date not between", value1, value2, "lastUpdateDate");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdIsNull() {
            addCriterion("create_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdIsNotNull() {
            addCriterion("create_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdEqualTo(Long value) {
            addCriterion("create_admin_id =", value, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdNotEqualTo(Long value) {
            addCriterion("create_admin_id <>", value, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdGreaterThan(Long value) {
            addCriterion("create_admin_id >", value, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("create_admin_id >=", value, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdLessThan(Long value) {
            addCriterion("create_admin_id <", value, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("create_admin_id <=", value, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdIn(List<Long> values) {
            addCriterion("create_admin_id in", values, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdNotIn(List<Long> values) {
            addCriterion("create_admin_id not in", values, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdBetween(Long value1, Long value2) {
            addCriterion("create_admin_id between", value1, value2, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andCreateAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("create_admin_id not between", value1, value2, "createAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdIsNull() {
            addCriterion("update_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdIsNotNull() {
            addCriterion("update_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdEqualTo(Long value) {
            addCriterion("update_admin_id =", value, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdNotEqualTo(Long value) {
            addCriterion("update_admin_id <>", value, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdGreaterThan(Long value) {
            addCriterion("update_admin_id >", value, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("update_admin_id >=", value, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdLessThan(Long value) {
            addCriterion("update_admin_id <", value, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("update_admin_id <=", value, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdIn(List<Long> values) {
            addCriterion("update_admin_id in", values, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdNotIn(List<Long> values) {
            addCriterion("update_admin_id not in", values, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdBetween(Long value1, Long value2) {
            addCriterion("update_admin_id between", value1, value2, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andUpdateAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("update_admin_id not between", value1, value2, "updateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdIsNull() {
            addCriterion("last_update_admin_id is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdIsNotNull() {
            addCriterion("last_update_admin_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdEqualTo(Long value) {
            addCriterion("last_update_admin_id =", value, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdNotEqualTo(Long value) {
            addCriterion("last_update_admin_id <>", value, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdGreaterThan(Long value) {
            addCriterion("last_update_admin_id >", value, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdGreaterThanOrEqualTo(Long value) {
            addCriterion("last_update_admin_id >=", value, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdLessThan(Long value) {
            addCriterion("last_update_admin_id <", value, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdLessThanOrEqualTo(Long value) {
            addCriterion("last_update_admin_id <=", value, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdIn(List<Long> values) {
            addCriterion("last_update_admin_id in", values, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdNotIn(List<Long> values) {
            addCriterion("last_update_admin_id not in", values, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdBetween(Long value1, Long value2) {
            addCriterion("last_update_admin_id between", value1, value2, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andLastUpdateAdminIdNotBetween(Long value1, Long value2) {
            addCriterion("last_update_admin_id not between", value1, value2, "lastUpdateAdminId");
            return (Criteria) this;
        }

        public Criteria andEmployeeCodeLikeInsensitive(String value) {
            addCriterion("upper(employee_code) like", value.toUpperCase(), "employeeCode");
            return (Criteria) this;
        }

        public Criteria andEmployeeNameLikeInsensitive(String value) {
            addCriterion("upper(employee_name) like", value.toUpperCase(), "employeeName");
            return (Criteria) this;
        }

        public Criteria andPhoneLikeInsensitive(String value) {
            addCriterion("upper(phone) like", value.toUpperCase(), "phone");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hai_employee
     *
     * @mbg.generated do_not_delete_during_merge Tue Jun 12 17:22:45 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hai_employee
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}