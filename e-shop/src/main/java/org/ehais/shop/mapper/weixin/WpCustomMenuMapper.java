package org.ehais.shop.mapper.weixin;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.weixin.WpCustomMenu;
import org.ehais.shop.model.weixin.WpCustomMenuExample;

public interface WpCustomMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    @Select("select count(*) from wp_custom_menu where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    @Select("select count(*) from wp_custom_menu where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    long countByExample(WpCustomMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int deleteByExample(WpCustomMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int insert(WpCustomMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int insertSelective(WpCustomMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    List<WpCustomMenu> selectByExample(WpCustomMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    WpCustomMenu selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int updateByExampleSelective(@Param("record") WpCustomMenu record, @Param("example") WpCustomMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int updateByExample(@Param("record") WpCustomMenu record, @Param("example") WpCustomMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int updateByPrimaryKeySelective(WpCustomMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_custom_menu
     *
     * @mbg.generated Fri Oct 13 14:57:30 CST 2017
     */
    int updateByPrimaryKey(WpCustomMenu record);
}