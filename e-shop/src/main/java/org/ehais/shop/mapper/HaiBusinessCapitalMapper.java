package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiBusinessCapital;
import org.ehais.shop.model.HaiBusinessCapitalExample;

public interface HaiBusinessCapitalMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    @Select("select * from hai_business_capital where business_capital_id = ${business_capital_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiBusinessCapital get_hai_business_capital_info(@Param("business_capital_id") Integer business_capital_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    @Select("select count(*) from hai_business_capital where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    @Select("select count(*) from hai_business_capital where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    long countByExample(HaiBusinessCapitalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int deleteByExample(HaiBusinessCapitalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int deleteByPrimaryKey(Integer businessCapitalId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int insert(HaiBusinessCapital record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int insertSelective(HaiBusinessCapital record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    List<HaiBusinessCapital> selectByExample(HaiBusinessCapitalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    HaiBusinessCapital selectByPrimaryKey(Integer businessCapitalId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiBusinessCapital record, @Param("example") HaiBusinessCapitalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int updateByExample(@Param("record") HaiBusinessCapital record, @Param("example") HaiBusinessCapitalExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int updateByPrimaryKeySelective(HaiBusinessCapital record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_capital
     *
     * @mbg.generated Fri Jul 13 15:37:15 CST 2018
     */
    int updateByPrimaryKey(HaiBusinessCapital record);
}