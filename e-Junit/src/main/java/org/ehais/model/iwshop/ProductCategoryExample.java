package org.ehais.model.iwshop;

import java.util.ArrayList;
import java.util.List;
import org.ehais.tools.CriteriaObject;

public class ProductCategoryExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    protected int limitStart = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public ProductCategoryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
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
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void CriteriaStoreId(Criteria c, CriteriaObject co) {
        if(co == null || co.getOperator() == null)return ;
        
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
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

        public Criteria andCatIdIsNull() {
            addCriterion("cat_id is null");
            return (Criteria) this;
        }

        public Criteria andCatIdIsNotNull() {
            addCriterion("cat_id is not null");
            return (Criteria) this;
        }

        public Criteria andCatIdEqualTo(Integer value) {
            addCriterion("cat_id =", value, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdNotEqualTo(Integer value) {
            addCriterion("cat_id <>", value, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdGreaterThan(Integer value) {
            addCriterion("cat_id >", value, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cat_id >=", value, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdLessThan(Integer value) {
            addCriterion("cat_id <", value, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdLessThanOrEqualTo(Integer value) {
            addCriterion("cat_id <=", value, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdIn(List<Integer> values) {
            addCriterion("cat_id in", values, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdNotIn(List<Integer> values) {
            addCriterion("cat_id not in", values, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdBetween(Integer value1, Integer value2) {
            addCriterion("cat_id between", value1, value2, "catId");
            return (Criteria) this;
        }

        public Criteria andCatIdNotBetween(Integer value1, Integer value2) {
            addCriterion("cat_id not between", value1, value2, "catId");
            return (Criteria) this;
        }

        public Criteria andCatNameIsNull() {
            addCriterion("cat_name is null");
            return (Criteria) this;
        }

        public Criteria andCatNameIsNotNull() {
            addCriterion("cat_name is not null");
            return (Criteria) this;
        }

        public Criteria andCatNameEqualTo(String value) {
            addCriterion("cat_name =", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotEqualTo(String value) {
            addCriterion("cat_name <>", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameGreaterThan(String value) {
            addCriterion("cat_name >", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameGreaterThanOrEqualTo(String value) {
            addCriterion("cat_name >=", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameLessThan(String value) {
            addCriterion("cat_name <", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameLessThanOrEqualTo(String value) {
            addCriterion("cat_name <=", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameLike(String value) {
            addCriterion("cat_name like", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotLike(String value) {
            addCriterion("cat_name not like", value, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameIn(List<String> values) {
            addCriterion("cat_name in", values, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotIn(List<String> values) {
            addCriterion("cat_name not in", values, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameBetween(String value1, String value2) {
            addCriterion("cat_name between", value1, value2, "catName");
            return (Criteria) this;
        }

        public Criteria andCatNameNotBetween(String value1, String value2) {
            addCriterion("cat_name not between", value1, value2, "catName");
            return (Criteria) this;
        }

        public Criteria andCatImageIsNull() {
            addCriterion("cat_image is null");
            return (Criteria) this;
        }

        public Criteria andCatImageIsNotNull() {
            addCriterion("cat_image is not null");
            return (Criteria) this;
        }

        public Criteria andCatImageEqualTo(String value) {
            addCriterion("cat_image =", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageNotEqualTo(String value) {
            addCriterion("cat_image <>", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageGreaterThan(String value) {
            addCriterion("cat_image >", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageGreaterThanOrEqualTo(String value) {
            addCriterion("cat_image >=", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageLessThan(String value) {
            addCriterion("cat_image <", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageLessThanOrEqualTo(String value) {
            addCriterion("cat_image <=", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageLike(String value) {
            addCriterion("cat_image like", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageNotLike(String value) {
            addCriterion("cat_image not like", value, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageIn(List<String> values) {
            addCriterion("cat_image in", values, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageNotIn(List<String> values) {
            addCriterion("cat_image not in", values, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageBetween(String value1, String value2) {
            addCriterion("cat_image between", value1, value2, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatImageNotBetween(String value1, String value2) {
            addCriterion("cat_image not between", value1, value2, "catImage");
            return (Criteria) this;
        }

        public Criteria andCatParentIsNull() {
            addCriterion("cat_parent is null");
            return (Criteria) this;
        }

        public Criteria andCatParentIsNotNull() {
            addCriterion("cat_parent is not null");
            return (Criteria) this;
        }

        public Criteria andCatParentEqualTo(Integer value) {
            addCriterion("cat_parent =", value, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentNotEqualTo(Integer value) {
            addCriterion("cat_parent <>", value, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentGreaterThan(Integer value) {
            addCriterion("cat_parent >", value, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentGreaterThanOrEqualTo(Integer value) {
            addCriterion("cat_parent >=", value, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentLessThan(Integer value) {
            addCriterion("cat_parent <", value, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentLessThanOrEqualTo(Integer value) {
            addCriterion("cat_parent <=", value, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentIn(List<Integer> values) {
            addCriterion("cat_parent in", values, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentNotIn(List<Integer> values) {
            addCriterion("cat_parent not in", values, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentBetween(Integer value1, Integer value2) {
            addCriterion("cat_parent between", value1, value2, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatParentNotBetween(Integer value1, Integer value2) {
            addCriterion("cat_parent not between", value1, value2, "catParent");
            return (Criteria) this;
        }

        public Criteria andCatLevelIsNull() {
            addCriterion("cat_level is null");
            return (Criteria) this;
        }

        public Criteria andCatLevelIsNotNull() {
            addCriterion("cat_level is not null");
            return (Criteria) this;
        }

        public Criteria andCatLevelEqualTo(Integer value) {
            addCriterion("cat_level =", value, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelNotEqualTo(Integer value) {
            addCriterion("cat_level <>", value, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelGreaterThan(Integer value) {
            addCriterion("cat_level >", value, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("cat_level >=", value, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelLessThan(Integer value) {
            addCriterion("cat_level <", value, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelLessThanOrEqualTo(Integer value) {
            addCriterion("cat_level <=", value, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelIn(List<Integer> values) {
            addCriterion("cat_level in", values, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelNotIn(List<Integer> values) {
            addCriterion("cat_level not in", values, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelBetween(Integer value1, Integer value2) {
            addCriterion("cat_level between", value1, value2, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("cat_level not between", value1, value2, "catLevel");
            return (Criteria) this;
        }

        public Criteria andCatOrderIsNull() {
            addCriterion("cat_order is null");
            return (Criteria) this;
        }

        public Criteria andCatOrderIsNotNull() {
            addCriterion("cat_order is not null");
            return (Criteria) this;
        }

        public Criteria andCatOrderEqualTo(Integer value) {
            addCriterion("cat_order =", value, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderNotEqualTo(Integer value) {
            addCriterion("cat_order <>", value, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderGreaterThan(Integer value) {
            addCriterion("cat_order >", value, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("cat_order >=", value, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderLessThan(Integer value) {
            addCriterion("cat_order <", value, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderLessThanOrEqualTo(Integer value) {
            addCriterion("cat_order <=", value, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderIn(List<Integer> values) {
            addCriterion("cat_order in", values, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderNotIn(List<Integer> values) {
            addCriterion("cat_order not in", values, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderBetween(Integer value1, Integer value2) {
            addCriterion("cat_order between", value1, value2, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("cat_order not between", value1, value2, "catOrder");
            return (Criteria) this;
        }

        public Criteria andCatUrlIsNull() {
            addCriterion("cat_url is null");
            return (Criteria) this;
        }

        public Criteria andCatUrlIsNotNull() {
            addCriterion("cat_url is not null");
            return (Criteria) this;
        }

        public Criteria andCatUrlEqualTo(String value) {
            addCriterion("cat_url =", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlNotEqualTo(String value) {
            addCriterion("cat_url <>", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlGreaterThan(String value) {
            addCriterion("cat_url >", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlGreaterThanOrEqualTo(String value) {
            addCriterion("cat_url >=", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlLessThan(String value) {
            addCriterion("cat_url <", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlLessThanOrEqualTo(String value) {
            addCriterion("cat_url <=", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlLike(String value) {
            addCriterion("cat_url like", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlNotLike(String value) {
            addCriterion("cat_url not like", value, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlIn(List<String> values) {
            addCriterion("cat_url in", values, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlNotIn(List<String> values) {
            addCriterion("cat_url not in", values, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlBetween(String value1, String value2) {
            addCriterion("cat_url between", value1, value2, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatUrlNotBetween(String value1, String value2) {
            addCriterion("cat_url not between", value1, value2, "catUrl");
            return (Criteria) this;
        }

        public Criteria andCatNameLikeInsensitive(String value) {
            addCriterion("upper(cat_name) like", value.toUpperCase(), "catName");
            return (Criteria) this;
        }

        public Criteria andCatImageLikeInsensitive(String value) {
            addCriterion("upper(cat_image) like", value.toUpperCase(), "catImage");
            return (Criteria) this;
        }

        public Criteria andCatUrlLikeInsensitive(String value) {
            addCriterion("upper(cat_url) like", value.toUpperCase(), "catUrl");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table product_category
     *
     * @mbg.generated do_not_delete_during_merge Mon May 01 21:03:15 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table product_category
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
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