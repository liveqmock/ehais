package org.ehais.epublic.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;

public interface WpPublicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    @Select("select count(*) from wp_public where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    @Select("select count(*) from wp_public where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    long countByExample(WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int deleteByExample(WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int insert(WpPublicWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int insertSelective(WpPublicWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    List<WpPublicWithBLOBs> selectByExampleWithBLOBs(WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    List<WpPublic> selectByExample(WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    WpPublicWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int updateByExampleSelective(@Param("record") WpPublicWithBLOBs record, @Param("example") WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") WpPublicWithBLOBs record, @Param("example") WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int updateByExample(@Param("record") WpPublic record, @Param("example") WpPublicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int updateByPrimaryKeySelective(WpPublicWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(WpPublicWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_public
     *
     * @mbg.generated Mon Aug 28 07:11:28 CST 2017
     */
    int updateByPrimaryKey(WpPublic record);
}