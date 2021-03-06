package org.ehais.shop.mapper.iwshop;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.iwshop.ProductsInfo;
import org.ehais.shop.model.iwshop.ProductsInfoExample;
import org.ehais.shop.model.iwshop.ProductsInfoWithBLOBs;

public interface ProductsInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    @Select("select count(*) from products_info where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    @Select("select count(*) from products_info where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    long countByExample(ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int deleteByExample(ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int deleteByPrimaryKey(Integer productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int insert(ProductsInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int insertSelective(ProductsInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    List<ProductsInfoWithBLOBs> selectByExampleWithBLOBs(ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    List<ProductsInfo> selectByExample(ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    ProductsInfoWithBLOBs selectByPrimaryKey(Integer productId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int updateByExampleSelective(@Param("record") ProductsInfoWithBLOBs record, @Param("example") ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") ProductsInfoWithBLOBs record, @Param("example") ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int updateByExample(@Param("record") ProductsInfo record, @Param("example") ProductsInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int updateByPrimaryKeySelective(ProductsInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(ProductsInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table products_info
     *
     * @mbg.generated Mon May 01 21:03:15 CST 2017
     */
    int updateByPrimaryKey(ProductsInfo record);
}