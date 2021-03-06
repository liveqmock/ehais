package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCategoryWithBLOBs;

public interface HaiCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select * from hai_category where cat_id = ${cat_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiCategoryWithBLOBs get_hai_category_info(@Param("cat_id") Integer cat_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_category where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_category where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    long countByExample(HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByExample(HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByPrimaryKey(Integer catId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insert(HaiCategoryWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insertSelective(HaiCategoryWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiCategoryWithBLOBs> selectByExampleWithBLOBs(HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiCategory> selectByExample(HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    HaiCategoryWithBLOBs selectByPrimaryKey(Integer catId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiCategoryWithBLOBs record, @Param("example") HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") HaiCategoryWithBLOBs record, @Param("example") HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExample(@Param("record") HaiCategory record, @Param("example") HaiCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeySelective(HaiCategoryWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(HaiCategoryWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_category
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKey(HaiCategory record);
}