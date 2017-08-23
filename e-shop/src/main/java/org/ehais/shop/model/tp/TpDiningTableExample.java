package org.ehais.shop.model.tp;

import java.util.ArrayList;
import java.util.List;
import org.ehais.tools.CriteriaObject;

public class TpDiningTableExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    protected int limitStart = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public TpDiningTableExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
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
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }



    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
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

        public Criteria andDtIdIsNull() {
            addCriterion("dt_id is null");
            return (Criteria) this;
        }

        public Criteria andDtIdIsNotNull() {
            addCriterion("dt_id is not null");
            return (Criteria) this;
        }

        public Criteria andDtIdEqualTo(Long value) {
            addCriterion("dt_id =", value, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdNotEqualTo(Long value) {
            addCriterion("dt_id <>", value, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdGreaterThan(Long value) {
            addCriterion("dt_id >", value, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dt_id >=", value, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdLessThan(Long value) {
            addCriterion("dt_id <", value, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdLessThanOrEqualTo(Long value) {
            addCriterion("dt_id <=", value, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdIn(List<Long> values) {
            addCriterion("dt_id in", values, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdNotIn(List<Long> values) {
            addCriterion("dt_id not in", values, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdBetween(Long value1, Long value2) {
            addCriterion("dt_id between", value1, value2, "dtId");
            return (Criteria) this;
        }

        public Criteria andDtIdNotBetween(Long value1, Long value2) {
            addCriterion("dt_id not between", value1, value2, "dtId");
            return (Criteria) this;
        }

        public Criteria andTablenameIsNull() {
            addCriterion("tablename is null");
            return (Criteria) this;
        }

        public Criteria andTablenameIsNotNull() {
            addCriterion("tablename is not null");
            return (Criteria) this;
        }

        public Criteria andTablenameEqualTo(String value) {
            addCriterion("tablename =", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotEqualTo(String value) {
            addCriterion("tablename <>", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThan(String value) {
            addCriterion("tablename >", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameGreaterThanOrEqualTo(String value) {
            addCriterion("tablename >=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThan(String value) {
            addCriterion("tablename <", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLessThanOrEqualTo(String value) {
            addCriterion("tablename <=", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameLike(String value) {
            addCriterion("tablename like", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotLike(String value) {
            addCriterion("tablename not like", value, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameIn(List<String> values) {
            addCriterion("tablename in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotIn(List<String> values) {
            addCriterion("tablename not in", values, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameBetween(String value1, String value2) {
            addCriterion("tablename between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andTablenameNotBetween(String value1, String value2) {
            addCriterion("tablename not between", value1, value2, "tablename");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdIsNull() {
            addCriterion("suppliers_id is null");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdIsNotNull() {
            addCriterion("suppliers_id is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdEqualTo(Long value) {
            addCriterion("suppliers_id =", value, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdNotEqualTo(Long value) {
            addCriterion("suppliers_id <>", value, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdGreaterThan(Long value) {
            addCriterion("suppliers_id >", value, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdGreaterThanOrEqualTo(Long value) {
            addCriterion("suppliers_id >=", value, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdLessThan(Long value) {
            addCriterion("suppliers_id <", value, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdLessThanOrEqualTo(Long value) {
            addCriterion("suppliers_id <=", value, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdIn(List<Long> values) {
            addCriterion("suppliers_id in", values, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdNotIn(List<Long> values) {
            addCriterion("suppliers_id not in", values, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdBetween(Long value1, Long value2) {
            addCriterion("suppliers_id between", value1, value2, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andSuppliersIdNotBetween(Long value1, Long value2) {
            addCriterion("suppliers_id not between", value1, value2, "suppliersId");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNull() {
            addCriterion("is_valid is null");
            return (Criteria) this;
        }

        public Criteria andIsValidIsNotNull() {
            addCriterion("is_valid is not null");
            return (Criteria) this;
        }

        public Criteria andIsValidEqualTo(Short value) {
            addCriterion("is_valid =", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotEqualTo(Short value) {
            addCriterion("is_valid <>", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThan(Short value) {
            addCriterion("is_valid >", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidGreaterThanOrEqualTo(Short value) {
            addCriterion("is_valid >=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThan(Short value) {
            addCriterion("is_valid <", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidLessThanOrEqualTo(Short value) {
            addCriterion("is_valid <=", value, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidIn(List<Short> values) {
            addCriterion("is_valid in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotIn(List<Short> values) {
            addCriterion("is_valid not in", values, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidBetween(Short value1, Short value2) {
            addCriterion("is_valid between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andIsValidNotBetween(Short value1, Short value2) {
            addCriterion("is_valid not between", value1, value2, "isValid");
            return (Criteria) this;
        }

        public Criteria andTablenameLikeInsensitive(String value) {
            addCriterion("upper(tablename) like", value.toUpperCase(), "tablename");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tp_dining_table
     *
     * @mbg.generated do_not_delete_during_merge Fri Aug 18 14:09:36 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table tp_dining_table
     *
     * @mbg.generated Fri Aug 18 14:09:36 CST 2017
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