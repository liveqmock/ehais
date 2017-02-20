package org.ehais.epublic.mapper.alipay;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.ehais.epublic.model.alipay.AlipayReturnUrl;
import org.ehais.epublic.model.alipay.AlipayReturnUrlExample;

public interface AlipayReturnUrlMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    @Select("select count(*) from alipay_return_url where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    @Select("select count(*) from alipay_return_url where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int countByExample(AlipayReturnUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int deleteByExample(AlipayReturnUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int insert(AlipayReturnUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int insertSelective(AlipayReturnUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    List<AlipayReturnUrl> selectByExample(AlipayReturnUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    AlipayReturnUrl selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int updateByExampleSelective(@Param("record") AlipayReturnUrl record, @Param("example") AlipayReturnUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int updateByExample(@Param("record") AlipayReturnUrl record, @Param("example") AlipayReturnUrlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int updateByPrimaryKeySelective(AlipayReturnUrl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alipay_return_url
     *
     * @mbggenerated Tue Dec 27 10:20:44 CST 2016
     */
    int updateByPrimaryKey(AlipayReturnUrl record);
}