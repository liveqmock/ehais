package org.ehais.shop.mapper.tp;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.tp.TpSuppliers;
import org.ehais.shop.model.tp.TpSuppliersExample;

public interface TpSuppliersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    @Select("select count(*) from tp_suppliers where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    @Select("select count(*) from tp_suppliers where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    long countByExample(TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int deleteByExample(TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int deleteByPrimaryKey(Long suppliersId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int insert(TpSuppliers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int insertSelective(TpSuppliers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    List<TpSuppliers> selectByExampleWithBLOBs(TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    List<TpSuppliers> selectByExample(TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    TpSuppliers selectByPrimaryKey(Long suppliersId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int updateByExampleSelective(@Param("record") TpSuppliers record, @Param("example") TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") TpSuppliers record, @Param("example") TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int updateByExample(@Param("record") TpSuppliers record, @Param("example") TpSuppliersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int updateByPrimaryKeySelective(TpSuppliers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(TpSuppliers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tp_suppliers
     *
     * @mbg.generated Sun Aug 06 21:16:43 CST 2017
     */
    int updateByPrimaryKey(TpSuppliers record);
}