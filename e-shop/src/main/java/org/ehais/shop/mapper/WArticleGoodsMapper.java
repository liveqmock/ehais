package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.WArticleGoods;

public interface WArticleGoodsMapper {
	
	
	
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
