package org.ehais.shop.mapper.tp;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.tp.TpDiningOrder;
import org.ehais.shop.model.tp.TpDiningOrderExample;

public interface TpDiningOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    @Select("select count(*) from tp_dining_order where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    @Select("select count(*) from tp_dining_order where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    long countByExample(TpDiningOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int deleteByExample(TpDiningOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int insert(TpDiningOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int insertSelective(TpDiningOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    List<TpDiningOrder> selectByExample(TpDiningOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    TpDiningOrder selectByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int updateByExampleSelective(@Param("record") TpDiningOrder record, @Param("example") TpDiningOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int updateByExample(@Param("record") TpDiningOrder record, @Param("example") TpDiningOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int updateByPrimaryKeySelective(TpDiningOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_dining_order
     *
     * @mbg.generated Mon Aug 07 17:17:29 CST 2017
     */
    int updateByPrimaryKey(TpDiningOrder record);
}