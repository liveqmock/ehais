package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.shop.model.WOrderGoodsAction;

public interface WOrderGoodsActionMapper {

	@ResultMap(value = "BaseResultMap")
	public List<WOrderGoodsAction> listOrderGoods(@Param("store_id") Integer store_id,
			@Param("start_time") Integer start_time,
			@Param("end_time") Integer end_time,
			@Param("start") Integer start,
			@Param("len") Integer len);
	
	
	public Long countOrderGoods(@Param("store_id") Integer store_id,
			@Param("start_time") Integer start_time,
			@Param("end_time") Integer end_time);
	
	
	
}
