package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiOrderDistribution;
import org.ehais.shop.model.HaiOrderDistributionExample;

public interface HaiOrderDistributionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    @Select("select count(*) from hai_order_distribution where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    @Select("select count(*) from hai_order_distribution where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    long countByExample(HaiOrderDistributionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int deleteByExample(HaiOrderDistributionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int deleteByPrimaryKey(Long orderDistributionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int insert(HaiOrderDistribution record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int insertSelective(HaiOrderDistribution record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    List<HaiOrderDistribution> selectByExample(HaiOrderDistributionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    HaiOrderDistribution selectByPrimaryKey(Long orderDistributionId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int updateByExampleSelective(@Param("record") HaiOrderDistribution record, @Param("example") HaiOrderDistributionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int updateByExample(@Param("record") HaiOrderDistribution record, @Param("example") HaiOrderDistributionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int updateByPrimaryKeySelective(HaiOrderDistribution record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_distribution
     *
     * @mbg.generated Wed Oct 25 12:01:51 CST 2017
     */
    int updateByPrimaryKey(HaiOrderDistribution record);
}