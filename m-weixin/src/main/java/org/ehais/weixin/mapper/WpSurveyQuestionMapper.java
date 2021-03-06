package org.ehais.weixin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.weixin.model.WpSurveyQuestion;
import org.ehais.weixin.model.WpSurveyQuestionExample;
import org.ehais.weixin.model.WpSurveyQuestionWithBLOBs;

public interface WpSurveyQuestionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<WpSurveyQuestion> wp_survey_question_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<WpSurveyQuestion> wp_survey_question_list_by_example(WpSurveyQuestionExample example);

    @ResultMap(value = "ResultMapWithBLOBs")
    List<WpSurveyQuestionWithBLOBs> wp_survey_question_blob_list_by_example(WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int countByExample(WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int deleteByExample(WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int insert(WpSurveyQuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int insertSelective(WpSurveyQuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    List<WpSurveyQuestionWithBLOBs> selectByExampleWithBLOBs(WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    List<WpSurveyQuestion> selectByExample(WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    WpSurveyQuestionWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int updateByExampleSelective(@Param("record") WpSurveyQuestionWithBLOBs record, @Param("example") WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") WpSurveyQuestionWithBLOBs record, @Param("example") WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int updateByExample(@Param("record") WpSurveyQuestion record, @Param("example") WpSurveyQuestionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int updateByPrimaryKeySelective(WpSurveyQuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(WpSurveyQuestionWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wp_survey_question
     *
     * @mbggenerated Wed Mar 09 13:52:26 CST 2016
     */
    int updateByPrimaryKey(WpSurveyQuestion record);
}