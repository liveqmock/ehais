package org.ehais.epublic.mapper.weixin;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.epublic.model.weixin.WxUnifiedorderResult;
import org.ehais.epublic.model.weixin.WxUnifiedorderResultExample;

public interface WxUnifiedorderResultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    @Select("select count(*) from wx_unifiedorder_result where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    @Select("select count(*) from wx_unifiedorder_result where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    long countByExample(WxUnifiedorderResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int deleteByExample(WxUnifiedorderResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int deleteByPrimaryKey(Integer wxOrderResultId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int insert(WxUnifiedorderResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int insertSelective(WxUnifiedorderResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    List<WxUnifiedorderResult> selectByExample(WxUnifiedorderResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    WxUnifiedorderResult selectByPrimaryKey(Integer wxOrderResultId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int updateByExampleSelective(@Param("record") WxUnifiedorderResult record, @Param("example") WxUnifiedorderResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int updateByExample(@Param("record") WxUnifiedorderResult record, @Param("example") WxUnifiedorderResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int updateByPrimaryKeySelective(WxUnifiedorderResult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_unifiedorder_result
     *
     * @mbg.generated Mon Aug 28 19:23:05 CST 2017
     */
    int updateByPrimaryKey(WxUnifiedorderResult record);
}