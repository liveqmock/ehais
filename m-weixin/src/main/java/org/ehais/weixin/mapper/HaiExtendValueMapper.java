package org.ehais.weixin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.weixin.model.HaiExtendValue;
import org.ehais.weixin.model.HaiExtendValueExample;

public interface HaiExtendValueMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiExtendValue> hai_extend_value_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    List<HaiExtendValue> hai_extend_value_list_by_example(HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiExtendValue get_hai_extend_value_info(@Param("store_id") Integer store_id, @Param("id") Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int countByExample(HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int deleteByExample(HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int insert(HaiExtendValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int insertSelective(HaiExtendValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    List<HaiExtendValue> selectByExampleWithBLOBs(HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    List<HaiExtendValue> selectByExample(HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    HaiExtendValue selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiExtendValue record, @Param("example") HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") HaiExtendValue record, @Param("example") HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int updateByExample(@Param("record") HaiExtendValue record, @Param("example") HaiExtendValueExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int updateByPrimaryKeySelective(HaiExtendValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(HaiExtendValue record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_extend_value
     *
     * @mbggenerated Fri May 06 22:58:28 CST 2016
     */
    int updateByPrimaryKey(HaiExtendValue record);
}