package org.ehais.epublic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ehais.epublic.model.EHaiOrderInfo;

public interface EHaiOrderInfoMapper {

	/**
	 * 获取某订单信息
	 * @param store_id
	 * @param order_sn
	 * @param pay_status
	 * @return
	 */
	@ResultMap(value = "BaseResultMap")
	@Select("select * from hai_order_info where store_id = #{store_id} and order_sn = #{order_sn}")
	public List<EHaiOrderInfo> listOrderInfoSn(
			@Param("store_id") Integer store_id,
			@Param("order_sn") String order_sn
			) ;
	
	/**
	 * 更改支付订单信息
	 * @param store_id
	 * @param order_sn
	 * @param pay_status
	 * @return
	 */
	@Update("update hai_order_info set pay_status = #{pay_status} where store_id = #{store_id} and order_sn = #{order_sn}")
	public int updateOrderPayStatus(
			@Param("store_id") Integer store_id,
			@Param("order_sn") String order_sn,
			@Param("pay_status") Integer pay_status
			);
}
