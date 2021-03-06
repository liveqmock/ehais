package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;

public interface HaiUserAddressMapper {
	
	int clearUserDefault(@Param("user_id") Long user_id);

	int setUserDefault(@Param("user_id") Long user_id, @Param("address_id") Long address_id);

	
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiUserAddress> hai_user_address_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiUserAddress> hai_user_address_list_by_example(HaiUserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiUserAddress get_hai_user_address_info(@Param("user_id") Long user_id, @Param("address_id") Long address_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int countByExample(HaiUserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int deleteByExample(HaiUserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int deleteByPrimaryKey(Long addressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int insert(HaiUserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int insertSelective(HaiUserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    List<HaiUserAddress> selectByExample(HaiUserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    HaiUserAddress selectByPrimaryKey(Long addressId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiUserAddress record, @Param("example") HaiUserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int updateByExample(@Param("record") HaiUserAddress record, @Param("example") HaiUserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int updateByPrimaryKeySelective(HaiUserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_user_address
     *
     * @mbggenerated Thu Jun 16 21:27:47 CST 2016
     */
    int updateByPrimaryKey(HaiUserAddress record);
}