package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiArticleGoodsKey;

public interface HaiArticleGoodsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    @Select("select count(*) from hai_article_goods where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    @Select("select count(*) from hai_article_goods where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    long countByExample(HaiArticleGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int deleteByExample(HaiArticleGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int deleteByPrimaryKey(HaiArticleGoodsKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int insert(HaiArticleGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int insertSelective(HaiArticleGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    List<HaiArticleGoods> selectByExample(HaiArticleGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    HaiArticleGoods selectByPrimaryKey(HaiArticleGoodsKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int updateByExampleSelective(@Param("record") HaiArticleGoods record, @Param("example") HaiArticleGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int updateByExample(@Param("record") HaiArticleGoods record, @Param("example") HaiArticleGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int updateByPrimaryKeySelective(HaiArticleGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_article_goods
     *
     * @mbg.generated Thu Aug 24 10:32:21 CST 2017
     */
    int updateByPrimaryKey(HaiArticleGoods record);
}