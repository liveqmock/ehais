package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.WArticleGoods;

public interface WArticleGoodsMapper {
	
	/**
	 * 根据关键字标签查找关联文章与相关的商品
	 * @param store_id
	 * @param article_id
	 * @param keywordSql
	 * @param start
	 * @param len
	 * @return
	 */
	@Select("select a.article_id,a.title,a.description,a.article_images,g.goods_id "
			+ " from hai_article a "
			+ " left join hai_article_goods g "
			+ " on a.article_id = g.article_id "
			+ " where a.store_id = #{store_id} and a.article_id != #{article_id} "
			+ " and (${keywordSql}) "
			+ " order by article_id desc limit #{start},#{len}")
	@ResultMap(value = "BaseResultMap")
	public List<WArticleGoods> listRecommendArticle(
			@Param("store_id") Integer store_id,
			@Param("article_id") Integer article_id,
			@Param("keywordSql") String keywordSql,
			@Param("start") Integer start,
			@Param("len") Integer len
			);
	
	
	@ResultMap(value = "BaseResultMap")
	public List<WArticleGoods> listArticleGoods(@Param("store_id") Integer store_id,
			@Param("title") String title,
			@Param("goods_name") String goods_name,
			@Param("start") Integer start,
			@Param("len") Integer len);
	
	
	public Long countArticleGoods(@Param("store_id") Integer store_id,
			@Param("title") String title,
			@Param("goods_name") String goods_name);
	
	
}
