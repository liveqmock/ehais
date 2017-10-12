package org.ehais.model;

import java.util.ArrayList;
import java.util.List;
import org.ehais.tools.CriteriaObject;

public class HaiAttributeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    protected int limitStart = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    protected int limitEnd = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public HaiAttributeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
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
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void CriteriaStoreId(Criteria c, CriteriaObject co) {
        if(co == null || co.getOperator() == null)return ;
        if(co.getOperator().equals("eq")){
            c.andStoreIdEqualTo(co.getStoreId());
        }else if(co.getOperator().equals("in")){
            c.andStoreIdIn(co.getStoreList());
        }else if(co.getOperator().equals("user_eq")){
//            c.andUserIdEqualTo(co.getUserId());
        }
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
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

        public Criteria andAttrIdIsNull() {
            addCriterion("attr_id is null");
            return (Criteria) this;
        }

        public Criteria andAttrIdIsNotNull() {
            addCriterion("attr_id is not null");
            return (Criteria) this;
        }

        public Criteria andAttrIdEqualTo(Integer value) {
            addCriterion("attr_id =", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdNotEqualTo(Integer value) {
            addCriterion("attr_id <>", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdGreaterThan(Integer value) {
            addCriterion("attr_id >", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("attr_id >=", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdLessThan(Integer value) {
            addCriterion("attr_id <", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdLessThanOrEqualTo(Integer value) {
            addCriterion("attr_id <=", value, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdIn(List<Integer> values) {
            addCriterion("attr_id in", values, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdNotIn(List<Integer> values) {
            addCriterion("attr_id not in", values, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdBetween(Integer value1, Integer value2) {
            addCriterion("attr_id between", value1, value2, "attrId");
            return (Criteria) this;
        }

        public Criteria andAttrIdNotBetween(Integer value1, Integer value2) {
            addCriterion("attr_id not between", value1, value2, "attrId");
            return (Criteria) this;
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

        public Criteria andAttrCodeIsNull() {
            addCriterion("attr_code is null");
            return (Criteria) this;
        }

        public Criteria andAttrCodeIsNotNull() {
            addCriterion("attr_code is not null");
            return (Criteria) this;
        }

        public Criteria andAttrCodeEqualTo(String value) {
            addCriterion("attr_code =", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeNotEqualTo(String value) {
            addCriterion("attr_code <>", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeGreaterThan(String value) {
            addCriterion("attr_code >", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeGreaterThanOrEqualTo(String value) {
            addCriterion("attr_code >=", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeLessThan(String value) {
            addCriterion("attr_code <", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeLessThanOrEqualTo(String value) {
            addCriterion("attr_code <=", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeLike(String value) {
            addCriterion("attr_code like", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeNotLike(String value) {
            addCriterion("attr_code not like", value, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeIn(List<String> values) {
            addCriterion("attr_code in", values, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeNotIn(List<String> values) {
            addCriterion("attr_code not in", values, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeBetween(String value1, String value2) {
            addCriterion("attr_code between", value1, value2, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrCodeNotBetween(String value1, String value2) {
            addCriterion("attr_code not between", value1, value2, "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrNameIsNull() {
            addCriterion("attr_name is null");
            return (Criteria) this;
        }

        public Criteria andAttrNameIsNotNull() {
            addCriterion("attr_name is not null");
            return (Criteria) this;
        }

        public Criteria andAttrNameEqualTo(String value) {
            addCriterion("attr_name =", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotEqualTo(String value) {
            addCriterion("attr_name <>", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameGreaterThan(String value) {
            addCriterion("attr_name >", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameGreaterThanOrEqualTo(String value) {
            addCriterion("attr_name >=", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameLessThan(String value) {
            addCriterion("attr_name <", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameLessThanOrEqualTo(String value) {
            addCriterion("attr_name <=", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameLike(String value) {
            addCriterion("attr_name like", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotLike(String value) {
            addCriterion("attr_name not like", value, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameIn(List<String> values) {
            addCriterion("attr_name in", values, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotIn(List<String> values) {
            addCriterion("attr_name not in", values, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameBetween(String value1, String value2) {
            addCriterion("attr_name between", value1, value2, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrNameNotBetween(String value1, String value2) {
            addCriterion("attr_name not between", value1, value2, "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeIsNull() {
            addCriterion("attr_input_type is null");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeIsNotNull() {
            addCriterion("attr_input_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeEqualTo(Boolean value) {
            addCriterion("attr_input_type =", value, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeNotEqualTo(Boolean value) {
            addCriterion("attr_input_type <>", value, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeGreaterThan(Boolean value) {
            addCriterion("attr_input_type >", value, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("attr_input_type >=", value, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeLessThan(Boolean value) {
            addCriterion("attr_input_type <", value, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("attr_input_type <=", value, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeIn(List<Boolean> values) {
            addCriterion("attr_input_type in", values, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeNotIn(List<Boolean> values) {
            addCriterion("attr_input_type not in", values, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_input_type between", value1, value2, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrInputTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_input_type not between", value1, value2, "attrInputType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeIsNull() {
            addCriterion("attr_type is null");
            return (Criteria) this;
        }

        public Criteria andAttrTypeIsNotNull() {
            addCriterion("attr_type is not null");
            return (Criteria) this;
        }

        public Criteria andAttrTypeEqualTo(Boolean value) {
            addCriterion("attr_type =", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotEqualTo(Boolean value) {
            addCriterion("attr_type <>", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeGreaterThan(Boolean value) {
            addCriterion("attr_type >", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("attr_type >=", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeLessThan(Boolean value) {
            addCriterion("attr_type <", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("attr_type <=", value, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeIn(List<Boolean> values) {
            addCriterion("attr_type in", values, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotIn(List<Boolean> values) {
            addCriterion("attr_type not in", values, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_type between", value1, value2, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_type not between", value1, value2, "attrType");
            return (Criteria) this;
        }

        public Criteria andAttrValuesIsNull() {
            addCriterion("attr_values is null");
            return (Criteria) this;
        }

        public Criteria andAttrValuesIsNotNull() {
            addCriterion("attr_values is not null");
            return (Criteria) this;
        }

        public Criteria andAttrValuesEqualTo(String value) {
            addCriterion("attr_values =", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesNotEqualTo(String value) {
            addCriterion("attr_values <>", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesGreaterThan(String value) {
            addCriterion("attr_values >", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesGreaterThanOrEqualTo(String value) {
            addCriterion("attr_values >=", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesLessThan(String value) {
            addCriterion("attr_values <", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesLessThanOrEqualTo(String value) {
            addCriterion("attr_values <=", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesLike(String value) {
            addCriterion("attr_values like", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesNotLike(String value) {
            addCriterion("attr_values not like", value, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesIn(List<String> values) {
            addCriterion("attr_values in", values, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesNotIn(List<String> values) {
            addCriterion("attr_values not in", values, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesBetween(String value1, String value2) {
            addCriterion("attr_values between", value1, value2, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrValuesNotBetween(String value1, String value2) {
            addCriterion("attr_values not between", value1, value2, "attrValues");
            return (Criteria) this;
        }

        public Criteria andAttrIndexIsNull() {
            addCriterion("attr_index is null");
            return (Criteria) this;
        }

        public Criteria andAttrIndexIsNotNull() {
            addCriterion("attr_index is not null");
            return (Criteria) this;
        }

        public Criteria andAttrIndexEqualTo(Boolean value) {
            addCriterion("attr_index =", value, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexNotEqualTo(Boolean value) {
            addCriterion("attr_index <>", value, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexGreaterThan(Boolean value) {
            addCriterion("attr_index >", value, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexGreaterThanOrEqualTo(Boolean value) {
            addCriterion("attr_index >=", value, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexLessThan(Boolean value) {
            addCriterion("attr_index <", value, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexLessThanOrEqualTo(Boolean value) {
            addCriterion("attr_index <=", value, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexIn(List<Boolean> values) {
            addCriterion("attr_index in", values, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexNotIn(List<Boolean> values) {
            addCriterion("attr_index not in", values, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_index between", value1, value2, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andAttrIndexNotBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_index not between", value1, value2, "attrIndex");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNull() {
            addCriterion("sort_order is null");
            return (Criteria) this;
        }

        public Criteria andSortOrderIsNotNull() {
            addCriterion("sort_order is not null");
            return (Criteria) this;
        }

        public Criteria andSortOrderEqualTo(Byte value) {
            addCriterion("sort_order =", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotEqualTo(Byte value) {
            addCriterion("sort_order <>", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThan(Byte value) {
            addCriterion("sort_order >", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderGreaterThanOrEqualTo(Byte value) {
            addCriterion("sort_order >=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThan(Byte value) {
            addCriterion("sort_order <", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderLessThanOrEqualTo(Byte value) {
            addCriterion("sort_order <=", value, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderIn(List<Byte> values) {
            addCriterion("sort_order in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotIn(List<Byte> values) {
            addCriterion("sort_order not in", values, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderBetween(Byte value1, Byte value2) {
            addCriterion("sort_order between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andSortOrderNotBetween(Byte value1, Byte value2) {
            addCriterion("sort_order not between", value1, value2, "sortOrder");
            return (Criteria) this;
        }

        public Criteria andIsLinkedIsNull() {
            addCriterion("is_linked is null");
            return (Criteria) this;
        }

        public Criteria andIsLinkedIsNotNull() {
            addCriterion("is_linked is not null");
            return (Criteria) this;
        }

        public Criteria andIsLinkedEqualTo(Boolean value) {
            addCriterion("is_linked =", value, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedNotEqualTo(Boolean value) {
            addCriterion("is_linked <>", value, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedGreaterThan(Boolean value) {
            addCriterion("is_linked >", value, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_linked >=", value, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedLessThan(Boolean value) {
            addCriterion("is_linked <", value, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_linked <=", value, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedIn(List<Boolean> values) {
            addCriterion("is_linked in", values, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedNotIn(List<Boolean> values) {
            addCriterion("is_linked not in", values, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_linked between", value1, value2, "isLinked");
            return (Criteria) this;
        }

        public Criteria andIsLinkedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_linked not between", value1, value2, "isLinked");
            return (Criteria) this;
        }

        public Criteria andAttrGroupIsNull() {
            addCriterion("attr_group is null");
            return (Criteria) this;
        }

        public Criteria andAttrGroupIsNotNull() {
            addCriterion("attr_group is not null");
            return (Criteria) this;
        }

        public Criteria andAttrGroupEqualTo(Boolean value) {
            addCriterion("attr_group =", value, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupNotEqualTo(Boolean value) {
            addCriterion("attr_group <>", value, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupGreaterThan(Boolean value) {
            addCriterion("attr_group >", value, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupGreaterThanOrEqualTo(Boolean value) {
            addCriterion("attr_group >=", value, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupLessThan(Boolean value) {
            addCriterion("attr_group <", value, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupLessThanOrEqualTo(Boolean value) {
            addCriterion("attr_group <=", value, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupIn(List<Boolean> values) {
            addCriterion("attr_group in", values, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupNotIn(List<Boolean> values) {
            addCriterion("attr_group not in", values, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_group between", value1, value2, "attrGroup");
            return (Criteria) this;
        }

        public Criteria andAttrGroupNotBetween(Boolean value1, Boolean value2) {
            addCriterion("attr_group not between", value1, value2, "attrGroup");
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

        public Criteria andAttrCodeLikeInsensitive(String value) {
            addCriterion("upper(attr_code) like", value.toUpperCase(), "attrCode");
            return (Criteria) this;
        }

        public Criteria andAttrNameLikeInsensitive(String value) {
            addCriterion("upper(attr_name) like", value.toUpperCase(), "attrName");
            return (Criteria) this;
        }

        public Criteria andAttrValuesLikeInsensitive(String value) {
            addCriterion("upper(attr_values) like", value.toUpperCase(), "attrValues");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hai_attribute
     *
     * @mbg.generated do_not_delete_during_merge Tue Oct 10 18:36:52 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table hai_attribute
     *
     * @mbg.generated Tue Oct 10 18:36:52 CST 2017
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