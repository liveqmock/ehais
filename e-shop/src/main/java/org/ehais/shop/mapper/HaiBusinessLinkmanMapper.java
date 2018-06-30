package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiBusinessLinkman;
import org.ehais.shop.model.HaiBusinessLinkmanExample;

public interface HaiBusinessLinkmanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    @Select("select * from hai_business_linkman where business_linkman_id = ${business_linkman_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiBusinessLinkman get_hai_business_linkman_info(@Param("business_linkman_id") Integer business_linkman_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    @Select("select count(*) from hai_business_linkman where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    @Select("select count(*) from hai_business_linkman where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    long countByExample(HaiBusinessLinkmanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int deleteByExample(HaiBusinessLinkmanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int deleteByPrimaryKey(Integer businessLinkmanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int insert(HaiBusinessLinkman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int insertSelective(HaiBusinessLinkman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    List<HaiBusinessLinkman> selectByExample(HaiBusinessLinkmanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    HaiBusinessLinkman selectByPrimaryKey(Integer businessLinkmanId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiBusinessLinkman record, @Param("example") HaiBusinessLinkmanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int updateByExample(@Param("record") HaiBusinessLinkman record, @Param("example") HaiBusinessLinkmanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int updateByPrimaryKeySelective(HaiBusinessLinkman record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_business_linkman
     *
     * @mbg.generated Fri Jun 22 12:42:58 CST 2018
     */
    int updateByPrimaryKey(HaiBusinessLinkman record);
}