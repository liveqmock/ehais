package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiAccountCapital;
import org.ehais.shop.model.HaiAccountCapitalExample;

public interface HaiAccountCapitalMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	@Select("select * from hai_account_capital where account_capital_id = ${account_capital_id} and store_id = ${store_id} ")
	@ResultMap(value = "ResultMapWithBLOBs")
	HaiAccountCapital get_hai_account_capital_info(@Param("account_capital_id") Integer account_capital_id,
			@Param("store_id") Integer store_id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	@Select("select count(*) from hai_account_capital where #{field} = #{value} ")
	int unique(@Param("field") String field, @Param("value") String value);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	@Select("select count(*) from hai_account_capital where #{field} = #{value} and store_id = #{store_id}")
	int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	long countByExample(HaiAccountCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int deleteByExample(HaiAccountCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int deleteByPrimaryKey(Integer accountCapitalId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int insert(HaiAccountCapital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int insertSelective(HaiAccountCapital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	List<HaiAccountCapital> selectByExample(HaiAccountCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	HaiAccountCapital selectByPrimaryKey(Integer accountCapitalId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByExampleSelective(@Param("record") HaiAccountCapital record,
			@Param("example") HaiAccountCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByExample(@Param("record") HaiAccountCapital record, @Param("example") HaiAccountCapitalExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByPrimaryKeySelective(HaiAccountCapital record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_account_capital
	 * @mbg.generated  Fri Jul 13 15:37:15 CST 2018
	 */
	int updateByPrimaryKey(HaiAccountCapital record);
}