package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiBusiness;
import org.ehais.shop.model.HaiBusinessExample;
import org.ehais.shop.model.HaiBusinessWithBLOBs;

public interface HaiBusinessMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select * from hai_business where business_id = ${business_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiBusinessWithBLOBs get_hai_business_info(@Param("business_id") Integer business_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_business where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_business where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    long countByExample(HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByExample(HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByPrimaryKey(Integer businessId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insert(HaiBusinessWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insertSelective(HaiBusinessWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiBusinessWithBLOBs> selectByExampleWithBLOBs(HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiBusiness> selectByExample(HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    HaiBusinessWithBLOBs selectByPrimaryKey(Integer businessId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiBusinessWithBLOBs record, @Param("example") HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") HaiBusinessWithBLOBs record, @Param("example") HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExample(@Param("record") HaiBusiness record, @Param("example") HaiBusinessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeySelective(HaiBusinessWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(HaiBusinessWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKey(HaiBusiness record);
}