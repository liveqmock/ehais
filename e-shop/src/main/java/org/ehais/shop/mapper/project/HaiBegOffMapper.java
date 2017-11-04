package org.ehais.shop.mapper.project;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.project.HaiBegOff;
import org.ehais.shop.model.project.HaiBegOffExample;

public interface HaiBegOffMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    @Select("select count(*) from hai_beg_off where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    @Select("select count(*) from hai_beg_off where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    long countByExample(HaiBegOffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int deleteByExample(HaiBegOffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int deleteByPrimaryKey(Integer begoffId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int insert(HaiBegOff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int insertSelective(HaiBegOff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    List<HaiBegOff> selectByExample(HaiBegOffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    HaiBegOff selectByPrimaryKey(Integer begoffId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int updateByExampleSelective(@Param("record") HaiBegOff record, @Param("example") HaiBegOffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int updateByExample(@Param("record") HaiBegOff record, @Param("example") HaiBegOffExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int updateByPrimaryKeySelective(HaiBegOff record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_beg_off
     *
     * @mbg.generated Sat Nov 04 17:37:13 CST 2017
     */
    int updateByPrimaryKey(HaiBegOff record);
}