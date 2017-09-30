package org.ehais.epublic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiArticleSimple;

public interface EHaiArticleMapper {
	
	
	List<EHaiArticleSimple> mySelectByExample(EHaiArticleExample example);
	
	
	/**
	 * 阅读量增加
	 * @param articleId
	 */
	@Update("update hai_article set read_count = ifnull(read_count,0) + 1 where article_id = #{articleId}")
	public void plusReadCount(@Param("articleId") Integer articleId);
	
	
	/**
	 * 根据关键字标签查找关联文章
	 * @param store_id
	 * @param keywordSql
	 * @param start
	 * @param len
	 * @return
	 */
	@Select("select * from hai_article where store_id = #{store_id} and article_id != #{article_id} and (${keywordSql}) order by article_id desc limit #{start},#{len}")
	@ResultMap(value = "BaseResultMap")
	public List<EHaiArticle> recommendArticle(@Param("store_id") Integer store_id,
			@Param("article_id") Integer article_id,
			@Param("keywordSql") String keywordSql,
			@Param("start") Integer start,
			@Param("len") Integer len);
	
	
	/**
	 * 获取随机的文章数
	 * @param store_id
	 * @return
	 */
	@Select("SELECT * FROM hai_article WHERE article_id >= ((SELECT MAX(article_id) FROM hai_article where store_id = #{store_id} )-(SELECT MIN(article_id) FROM hai_article where store_id = #{store_id})) * RAND() + (SELECT MIN(article_id) FROM hai_article where store_id = #{store_id}) and store_id = #{store_id} LIMIT 1")
	@ResultMap(value = "ResultMapWithBLOBs")
    EHaiArticle article_rand(@Param("store_id") Integer store_id);

	
	
	@ResultMap(value = "BaseResultMap")
	public List<EHaiArticle> article_list_by_catcode(
			@Param("store_id") Integer store_id,
			@Param("code") String code,
			@Param("start") Integer start,
			@Param("len") Integer len
			);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<EHaiArticle> hai_article_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<EHaiArticle> hai_article_list_by_example(EHaiArticleExample example);

    @ResultMap(value = "ResultMapWithBLOBs")
    EHaiArticle get_hai_article_info(@Param("store_id") Integer store_id, @Param("article_id") Integer article_id);

    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    long countByExample(EHaiArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int deleteByExample(EHaiArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int deleteByPrimaryKey(Integer articleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int insert(EHaiArticle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int insertSelective(EHaiArticle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    List<EHaiArticle> selectByExampleWithBLOBs(EHaiArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    List<EHaiArticle> selectByExample(EHaiArticleExample example);
    

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    EHaiArticle selectByPrimaryKey(Integer articleId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int updateByExampleSelective(@Param("record") EHaiArticle record, @Param("example") EHaiArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") EHaiArticle record, @Param("example") EHaiArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int updateByExample(@Param("record") EHaiArticle record, @Param("example") EHaiArticleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int updateByPrimaryKeySelective(EHaiArticle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(EHaiArticle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article
     *
     * @mbggenerated Wed Apr 13 17:34:01 CST 2016
     */
    int updateByPrimaryKey(EHaiArticle record);
}