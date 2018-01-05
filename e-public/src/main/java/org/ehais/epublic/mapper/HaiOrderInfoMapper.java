package org.ehais.epublic.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.OrderGoodsDaySaleStatistics;
import org.ehais.epublic.model.OrderGoodsStatistics;
import org.ehais.epublic.model.OrderStatus;
import org.ehais.epublic.model.OrderStoreStatistics;

public interface HaiOrderInfoMapper {
	
	
	@Update("update hai_coupons set use_count = ifnull(use_count,0) + 1 where is_valid = 1 and store_id = #{store_id} and coupons_id = #{coupons_id} ")
	int updateCouponsUseCount(@Param("store_id") Integer store_id,
			@Param("coupons_id") Integer coupons_id);
	
	
	//查找某时间段所有订单
	@Select("select order_id "+
			" from hai_order_info where store_id = #{store_id} and order_status = 1 " + 
			" and pay_time >= #{start_time} and pay_time < #{end_time} ")
	List<Long> order_id_paytime_record(
			@Param("store_id") Integer store_id,
			@Param("start_time") Integer start_time,
			@Param("end_time") Integer end_time
			);
	
	
	//统计某时间段所有商品的售销量
	@Select("select goods_id,SUM(goods_number) as quantity " + 
	" from hai_order_goods where order_id in (${order_ids}) " + 
	" GROUP BY goods_id order by quantity desc ")
	@Results(value = {
			@Result(property="goodsId", column="goods_id"),
			@Result(property="quantity", column="quantity")
	})
	List<OrderGoodsStatistics> order_goods_statistics(@Param("order_ids") String order_ids);
	
	
	//统计某商品在某时间段的销售量
	@Select("select SUM(g.goods_number) as quantity,DATE_FORMAT(FROM_UNIXTIME(o.pay_time),'%Y-%m-%d') as sale_date " + 
			" from hai_order_info o left join hai_order_goods g on o.order_id = g.order_id " + 
			" where o.order_status = 1 and g.goods_id = #{goods_id} and o.store_id = #{store_id} " +
			" and o.pay_time >= #{start_time} and o.pay_time < #{end_time} "+
			" group by DATE_FORMAT(FROM_UNIXTIME(o.pay_time),'%Y-%m-%d')")
	@Results(value = {
			@Result(property="saleDate", column="sale_date"),
			@Result(property="quantity", column="quantity")
	})
	List<OrderGoodsDaySaleStatistics> order_goods_day_sale_statistics(
			@Param("store_id") Integer store_id,
			@Param("start_time") Integer start_time,
			@Param("end_time") Integer end_time,
			@Param("goods_id") Long goods_id);
	
	
	
	/**
	 * 获取某订单信息
	 * @param store_id
	 * @param order_sn
	 * @param pay_status
	 * @return
	 */
	@ResultMap(value = "ResultMapWithBLOBs")
	@Select("select * from hai_order_info where store_id = #{store_id} and order_sn = #{order_sn}")
	public List<HaiOrderInfoWithBLOBs> listOrderInfoSn(
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
	@Update("update hai_order_info set order_status = #{order_status},"
			+ " pay_status = #{pay_status} , "
			+ " pay_time = #{pay_time},"
			+ " pay_id = #{pay_id},"
			+ " pay_name = #{pay_name} ,"
			+ " day_serial_number = #{day_serial_number} "
			+ " where store_id = #{store_id} and order_sn = #{order_sn}")
	public int updateOrderPayStatus(
			@Param("store_id") Integer store_id,
			@Param("order_sn") String order_sn,
			@Param("order_status") Integer order_status,
			@Param("pay_status") Integer pay_status,
			@Param("pay_time") Long pay_time,
			@Param("pay_id") Integer pay_id,
			@Param("pay_name") String pay_name,
			@Param("day_serial_number") Integer day_serial_number
			);
	
	
	//每天流水号数量
	@Select("select count(*) from hai_order_info where "
			+ " date_format(add_time,'%Y-%m-%d') = date_format(now(),'%Y-%m-%d') "
			+ " and order_status = 1 "
			+ " and store_id = #{store_id} ")
	int daySerialNumber(@Param("store_id") Integer store_id);
		
		
	
	@Select("select  "+
			" count(CASE WHEN order_status = 4 THEN 1 ELSE NULL END) as returned, "+//退货
			" count(CASE WHEN order_status = 1 and shipping_status = 1 THEN 1 ELSE NULL END) as shipments, "+//已发货
			" count(CASE WHEN pay_status = 0 THEN 1 ELSE NULL END) as payWaiting "+//待付款
			" from hai_order_info "+
			" where user_id = #{user_id}")
	public Map<String,Integer> order_statistics(@Param("user_id") Long user_id);
	
//	@Select("select "+
//			" sum(case when pay_name='微信支付' then order_amount end) as weixin_amount, "+
//			" sum(case when pay_name='现金支付' then order_amount end) as cash_amount, "+
//			" truncate((pay_time / 100),0) as pay_time "+
//			" from hai_order_info where store_id = #{store_id} and order_status = 1 " + 
//			" and pay_time >= #{start_time} and pay_time < #{end_time} " + 
//			" GROUP BY truncate((pay_time / 100),0)")
	@Select("select "+
			" sum(case when pay_name='微信支付' then order_amount end) as weixin_amount, "+
			" sum(case when pay_name='现金支付' then order_amount end) as cash_amount, "+
			" DATE_FORMAT(FROM_UNIXTIME(pay_time),'%Y-%m-%d') as pay_time "+
			" from hai_order_info where store_id = #{store_id} and order_status = 1 " + 
			" and pay_time >= #{start_time} and pay_time < #{end_time} " + 
			" GROUP BY DATE_FORMAT(FROM_UNIXTIME(pay_time),'%Y-%m-%d')")
	@Results(value = {
			@Result(property="weixinAmount", column="weixin_amount"),
			@Result(property="cashAmount", column="cash_amount"),
			@Result(property="payTime", column="pay_time")
	})
	List<OrderStoreStatistics> order_dining_statistics(
			@Param("store_id") Integer store_id,
			@Param("start_time") Integer start_time,
			@Param("end_time") Integer end_time
			);
	

	@Select(
			"select count(*) as count , 'order_0' as ostatus from hai_order_info where order_status = 0 and user_id = #{user_id} "+
			" union " +
			" select count(*) as count , 'order_1' as ostatus from hai_order_info where order_status = 1 and user_id = #{user_id}  " +
			" union " +
			" select count(*) as count , 'shipping_0' as ostatus from hai_order_info where order_status = 1 and shipping_status = 0 and user_id = #{user_id}  "+
			" union " +
			" select count(*) as count , 'shipping_1' as ostatus from hai_order_info where order_status = 1 and shipping_status = 1 and user_id = #{user_id}  "+
			" union " +
			" select count(*) as count , 'shipping_2' as ostatus from hai_order_info where order_status = 1 and shipping_status = 2 and user_id = #{user_id}  "+
			" union " +
			" select count(*) as count , 'pay_0' as ostatus from hai_order_info where pay_status = 0 and user_id = #{user_id} " +
			" union " +
			" select count(*) as count , 'pay_1' as ostatus from hai_order_info where pay_status = 1 and user_id = #{user_id} " +
			" union " +
			" select count(*) as count , 'pay_2' as ostatus from hai_order_info where pay_status = 2 and user_id = #{user_id} " 
					)
	@Results(value = {
			@Result(property="count", column="count"),
			@Result(property="ostatus", column="ostatus")
	})
	List<OrderStatus> statistics_member_order(@Param("user_id") Long user_id);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbggenerated Sat Apr 16 01:14:43 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiOrderInfo> hai_order_info_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbggenerated Sat Apr 16 01:14:43 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiOrderInfo> hai_order_info_list_by_example(HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbggenerated Sat Apr 16 01:14:43 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiOrderInfo get_hai_order_info_info(@Param("user_id") Long user_id, @Param("order_id") Long order_id);

    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    @Select("select count(*) from hai_order_info where #{field} = #{value} ")
    int unique(@Param("field") String field, @Param("value") String value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    @Select("select count(*) from hai_order_info where #{field} = #{value} and store_id = #{store_id}")
    int uniqueStore(@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    long countByExample(HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int deleteByExample(HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int insert(HaiOrderInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int insertSelective(HaiOrderInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    List<HaiOrderInfoWithBLOBs> selectByExampleWithBLOBs(HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    List<HaiOrderInfo> selectByExample(HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    HaiOrderInfoWithBLOBs selectByPrimaryKey(Long orderId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int updateByExampleSelective(@Param("record") HaiOrderInfoWithBLOBs record, @Param("example") HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int updateByExampleWithBLOBs(@Param("record") HaiOrderInfoWithBLOBs record, @Param("example") HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int updateByExample(@Param("record") HaiOrderInfo record, @Param("example") HaiOrderInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int updateByPrimaryKeySelective(HaiOrderInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int updateByPrimaryKeyWithBLOBs(HaiOrderInfoWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_info
     *
     * @mbg.generated Fri Oct 13 20:53:03 CST 2017
     */
    int updateByPrimaryKey(HaiOrderInfo record);
}