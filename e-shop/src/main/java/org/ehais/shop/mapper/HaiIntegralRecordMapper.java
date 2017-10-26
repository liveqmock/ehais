package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiIntegralRecord;
import org.ehais.shop.model.HaiIntegralRecordExample;

public interface HaiIntegralRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    @Select("select count(*) from hai_integral_record where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    @Select("select count(*) from hai_integral_record where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    long countByExample(HaiIntegralRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int deleteByExample(HaiIntegralRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int deleteByPrimaryKey(Long integralId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int insert(HaiIntegralRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int insertSelective(HaiIntegralRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    List<HaiIntegralRecord> selectByExample(HaiIntegralRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    HaiIntegralRecord selectByPrimaryKey(Long integralId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int updateByExampleSelective(@Param("record") HaiIntegralRecord record, @Param("example") HaiIntegralRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int updateByExample(@Param("record") HaiIntegralRecord record, @Param("example") HaiIntegralRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int updateByPrimaryKeySelective(HaiIntegralRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_integral_record
     *
     * @mbg.generated Tue Oct 24 23:07:59 CST 2017
     */
    int updateByPrimaryKey(HaiIntegralRecord record);
}