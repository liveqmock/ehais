package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.model.HaiPaymentExample;
import org.ehais.shop.model.HaiPaymentWithBLOBs;

public interface HaiPaymentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiPayment> hai_payment_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiPayment> hai_payment_list_by_example(HaiPaymentExample example);

    @ResultMap(value = "ResultMapWithBLOBs")
    HaiPaymentWithBLOBs get_hai_payment_info(@Param("store_id") Integer store_id, @Param("pay_id") Byte pay_id);

    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int countByExample(HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int deleteByExample(HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int deleteByPrimaryKey(Byte payId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int insert(HaiPaymentWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int insertSelective(HaiPaymentWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    List<HaiPaymentWithBLOBs> selectByExampleWithBLOBs(HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    List<HaiPayment> selectByExample(HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    HaiPaymentWithBLOBs selectByPrimaryKey(Byte payId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiPaymentWithBLOBs record, @Param("example") HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") HaiPaymentWithBLOBs record, @Param("example") HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByExample(@Param("record") HaiPayment record, @Param("example") HaiPaymentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByPrimaryKeySelective(HaiPaymentWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(HaiPaymentWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_payment
     *
     * @mbggenerated Fri Apr 15 14:59:22 CST 2016
     */
    int updateByPrimaryKey(HaiPayment record);
}