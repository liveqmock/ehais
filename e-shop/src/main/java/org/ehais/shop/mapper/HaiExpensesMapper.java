package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiExpenses;
import org.ehais.shop.model.HaiExpensesExample;

public interface HaiExpensesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select * from hai_expenses where expenses_id = ${expenses_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiExpenses get_hai_expenses_info(@Param("expenses_id") Integer expenses_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_expenses where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_expenses where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    long countByExample(HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByExample(HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByPrimaryKey(Integer expensesId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insert(HaiExpenses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insertSelective(HaiExpenses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiExpenses> selectByExampleWithBLOBs(HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiExpenses> selectByExample(HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    HaiExpenses selectByPrimaryKey(Integer expensesId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiExpenses record, @Param("example") HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") HaiExpenses record, @Param("example") HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExample(@Param("record") HaiExpenses record, @Param("example") HaiExpensesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeySelective(HaiExpenses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(HaiExpenses record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_expenses
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKey(HaiExpenses record);
}