package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiAgency;
import org.ehais.shop.model.HaiAgencyExample;

public interface HaiAgencyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    @Select("select count(*) from hai_agency where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    @Select("select count(*) from hai_agency where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    long countByExample(HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int deleteByExample(HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int deleteByPrimaryKey(Integer agencyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int insert(HaiAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int insertSelective(HaiAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    List<HaiAgency> selectByExampleWithBLOBs(HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    List<HaiAgency> selectByExample(HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    HaiAgency selectByPrimaryKey(Integer agencyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int updateByExampleSelective(@Param("record") HaiAgency record, @Param("example") HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") HaiAgency record, @Param("example") HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int updateByExample(@Param("record") HaiAgency record, @Param("example") HaiAgencyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int updateByPrimaryKeySelective(HaiAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(HaiAgency record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_agency
     *
     * @mbg.generated Sat Aug 12 22:15:37 CST 2017
     */
    int updateByPrimaryKey(HaiAgency record);
}