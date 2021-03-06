package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiPropertyCapital;
import org.ehais.shop.model.HaiPropertyCapitalExample;

public interface HaiPropertyCapitalMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	@Select("select * from hai_property_capital where property_capital_id = ${property_capital_id} and store_id = ${store_id} ")
	@ResultMap(value = "ResultMapWithBLOBs")
	HaiPropertyCapital get_hai_property_capital_info(@Param("property_capital_id") Integer property_capital_id,
			@Param("store_id") Integer store_id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	@Select("select count(*) from hai_property_capital where #{field} = #{value} ")
	int unique(@Param("field") String field, @Param("value") String value);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	@Select("select count(*) from hai_property_capital where #{field} = #{value} and store_id = #{store_id}")
	int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	long countByExample(HaiPropertyCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int deleteByExample(HaiPropertyCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int deleteByPrimaryKey(Integer propertyCapitalId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int insert(HaiPropertyCapital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int insertSelective(HaiPropertyCapital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	List<HaiPropertyCapital> selectByExample(HaiPropertyCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	HaiPropertyCapital selectByPrimaryKey(Integer propertyCapitalId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByExampleSelective(@Param("record") HaiPropertyCapital record,
			@Param("example") HaiPropertyCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByExample(@Param("record") HaiPropertyCapital record,
			@Param("example") HaiPropertyCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByPrimaryKeySelective(HaiPropertyCapital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_property_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByPrimaryKey(HaiPropertyCapital record);
}