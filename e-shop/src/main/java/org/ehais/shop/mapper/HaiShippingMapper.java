package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiShipping;
import org.ehais.shop.model.HaiShippingExample;
import org.ehais.shop.model.HaiShippingWithBLOBs;

public interface HaiShippingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiShipping> hai_shipping_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiShipping> hai_shipping_list_by_example(HaiShippingExample example);
    
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiShippingWithBLOBs get_hai_shipping_info(@Param("store_id") Integer store_id, @Param("shipping_id") Integer shipping_id);

    @Select("select * from hai_shipping where (store_id = #{store_id} and shipping_id = #{shipping_id}) or (store_id = 0 and shipping_id = #{shipping_id} )")
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiShippingWithBLOBs web_shipping_info(@Param("store_id") Integer store_id, @Param("shipping_id") Integer shipping_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int countByExample(HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int deleteByExample(HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int deleteByPrimaryKey(Integer shippingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int insert(HaiShippingWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int insertSelective(HaiShippingWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    List<HaiShippingWithBLOBs> selectByExampleWithBLOBs(HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    List<HaiShipping> selectByExample(HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    HaiShippingWithBLOBs selectByPrimaryKey(Integer shippingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiShippingWithBLOBs record, @Param("example") HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") HaiShippingWithBLOBs record, @Param("example") HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByExample(@Param("record") HaiShipping record, @Param("example") HaiShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByPrimaryKeySelective(HaiShippingWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(HaiShippingWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_shipping
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByPrimaryKey(HaiShipping record);
}