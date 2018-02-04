package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;

public interface HaiOrderGoodsMapper {
	
	int insertBatch(List<HaiOrderGoods> orderGoodsList)throws Exception;
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiOrderGoods> hai_order_goods_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiOrderGoods> hai_order_goods_list_by_example(HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiOrderGoods get_hai_order_goods_info(@Param("store_id") Integer store_id, @Param("rec_id") Long rec_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int countByExample(HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int deleteByExample(HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int deleteByPrimaryKey(Long recId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int insert(HaiOrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int insertSelective(HaiOrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    List<HaiOrderGoods> selectByExampleWithBLOBs(HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    List<HaiOrderGoods> selectByExample(HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    HaiOrderGoods selectByPrimaryKey(Long recId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiOrderGoods record, @Param("example") HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") HaiOrderGoods record, @Param("example") HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int updateByExample(@Param("record") HaiOrderGoods record, @Param("example") HaiOrderGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int updateByPrimaryKeySelective(HaiOrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(HaiOrderGoods record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_order_goods
     *
     * @mbggenerated Thu Jun 16 21:09:26 CST 2016
     */
    int updateByPrimaryKey(HaiOrderGoods record);
}