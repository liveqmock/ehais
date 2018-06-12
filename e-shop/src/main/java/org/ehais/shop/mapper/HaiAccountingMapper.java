package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiAccounting;
import org.ehais.shop.model.HaiAccountingExample;

public interface HaiAccountingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select * from hai_accounting where accounting_id = ${accounting_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiAccounting get_hai_accounting_info(@Param("accounting_id") Integer accounting_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_accounting where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_accounting where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    long countByExample(HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByExample(HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByPrimaryKey(Integer accountingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insert(HaiAccounting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insertSelective(HaiAccounting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiAccounting> selectByExampleWithBLOBs(HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiAccounting> selectByExample(HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    HaiAccounting selectByPrimaryKey(Integer accountingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiAccounting record, @Param("example") HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") HaiAccounting record, @Param("example") HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExample(@Param("record") HaiAccounting record, @Param("example") HaiAccountingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeySelective(HaiAccounting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(HaiAccounting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_accounting
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKey(HaiAccounting record);
}