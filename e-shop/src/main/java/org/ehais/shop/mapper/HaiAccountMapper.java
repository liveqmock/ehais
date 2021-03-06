package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiAccount;
import org.ehais.shop.model.HaiAccountExample;

public interface HaiAccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select * from hai_account where account_id = ${account_id} and store_id = ${store_id} ")
    @ResultMap(value = "ResultMapWithBLOBs") //BaseResultMap  ResultMapWithBLOBs
    HaiAccount get_hai_account_info(@Param("account_id") Integer account_id, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_account where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    @Select("select count(*) from hai_account where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    long countByExample(HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByExample(HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int deleteByPrimaryKey(Integer accountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insert(HaiAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int insertSelective(HaiAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiAccount> selectByExampleWithBLOBs(HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    List<HaiAccount> selectByExample(HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    HaiAccount selectByPrimaryKey(Integer accountId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleSelective(@Param("record") HaiAccount record, @Param("example") HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExampleWithBLOBs(@Param("record") HaiAccount record, @Param("example") HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByExample(@Param("record") HaiAccount record, @Param("example") HaiAccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeySelective(HaiAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKeyWithBLOBs(HaiAccount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_account
     *
     * @mbg.generated Tue Jun 12 17:22:45 CST 2018
     */
    int updateByPrimaryKey(HaiAccount record);
}