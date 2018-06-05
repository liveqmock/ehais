package org.ehais.epublic.mapper.weixin;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.epublic.model.weixin.WxTransfers;
import org.ehais.epublic.model.weixin.WxTransfersExample;

public interface WxTransfersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    @Select("select count(*) from wx_transfers where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    @Select("select count(*) from wx_transfers where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    long countByExample(WxTransfersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int deleteByExample(WxTransfersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int deleteByPrimaryKey(Integer transfersId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int insert(WxTransfers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int insertSelective(WxTransfers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    List<WxTransfers> selectByExample(WxTransfersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    WxTransfers selectByPrimaryKey(Integer transfersId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int updateByExampleSelective(@Param("record") WxTransfers record, @Param("example") WxTransfersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int updateByExample(@Param("record") WxTransfers record, @Param("example") WxTransfersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int updateByPrimaryKeySelective(WxTransfers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wx_transfers
     *
     * @mbg.generated Mon May 28 09:27:35 CST 2018
     */
    int updateByPrimaryKey(WxTransfers record);
}