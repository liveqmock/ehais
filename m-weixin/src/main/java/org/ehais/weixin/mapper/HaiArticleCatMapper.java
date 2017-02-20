package org.ehais.weixin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.weixin.model.HaiArticleCat;
import org.ehais.weixin.model.HaiArticleCatExample;

public interface HaiArticleCatMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	@ResultMap(value = "BaseResultMap")
	List<HaiArticleCat> hai_article_cat_list(
			@Param("store_id") Integer store_id, @Param("start") Integer start,
			@Param("len") Integer len);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	@ResultMap(value = "BaseResultMap")
	List<HaiArticleCat> hai_article_cat_list_by_example(
			HaiArticleCatExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int countByExample(HaiArticleCatExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int deleteByExample(HaiArticleCatExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int deleteByPrimaryKey(Short catId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int insert(HaiArticleCat record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int insertSelective(HaiArticleCat record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	List<HaiArticleCat> selectByExample(HaiArticleCatExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	HaiArticleCat selectByPrimaryKey(Short catId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int updateByExampleSelective(@Param("record") HaiArticleCat record,
			@Param("example") HaiArticleCatExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int updateByExample(@Param("record") HaiArticleCat record,
			@Param("example") HaiArticleCatExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int updateByPrimaryKeySelective(HaiArticleCat record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table hai_article_cat
	 * @mbggenerated  Tue Apr 05 18:30:54 CST 2016
	 */
	int updateByPrimaryKey(HaiArticleCat record);
}