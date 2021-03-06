package com.ehais.huangbao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import com.ehais.huangbao.model.HaiActivityApply;
import com.ehais.huangbao.model.HaiActivityApplyExample;

public interface HaiActivityApplyMapper {

	@ResultMap(value = "BaseResultMap")
    List<HaiActivityApply> hai_activity_apply_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    @ResultMap(value = "BaseResultMap")
    List<HaiActivityApply> hai_activity_apply_list_by_example(HaiActivityApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int countByExample(HaiActivityApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int deleteByExample(HaiActivityApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int deleteByPrimaryKey(Integer applyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int insert(HaiActivityApply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int insertSelective(HaiActivityApply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	List<HaiActivityApply> selectByExample(HaiActivityApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	HaiActivityApply selectByPrimaryKey(Integer applyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int updateByExampleSelective(@Param("record") HaiActivityApply record,
			@Param("example") HaiActivityApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int updateByExample(@Param("record") HaiActivityApply record,
			@Param("example") HaiActivityApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int updateByPrimaryKeySelective(HaiActivityApply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_activity_apply
	 * @mbggenerated  Thu Mar 03 14:01:53 CST 2016
	 */
	int updateByPrimaryKey(HaiActivityApply record);
}