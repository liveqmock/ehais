package org.ehais.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsWithBLOBs;

public interface HaiGoodsMapper {
	
	
	@ResultMap(value = "BaseResultMap")
	List<HaiGoods> hai_goods_list_by_favorites(@Param("user_id") Long user_id, @Param("start") Integer start, @Param("len") Integer len);


    @ResultMap(value = "appResultMap")
    HaiGoodsWithBLOBs get_app_goods(@Param("store_id") Integer store_id,@Param("goods_id") Long goods_id);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiGoods> hai_goods_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiGoods> hai_goods_list_by_example(HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiGoodsWithBLOBs get_hai_goods_info(@Param("store_id") Integer store_id, @Param("goods_id") Long goods_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    long countByExample(HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int deleteByExample(HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int deleteByPrimaryKey(Long goodsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int insert(HaiGoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int insertSelective(HaiGoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    List<HaiGoodsWithBLOBs> selectByExampleWithBLOBs(HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    List<HaiGoods> selectByExample(HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    HaiGoodsWithBLOBs selectByPrimaryKey(Long goodsId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiGoodsWithBLOBs record, @Param("example") HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") HaiGoodsWithBLOBs record, @Param("example") HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int updateByExample(@Param("record") HaiGoods record, @Param("example") HaiGoodsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int updateByPrimaryKeySelective(HaiGoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(HaiGoodsWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_goods
     *
     * @mbggenerated Wed Jun 15 21:25:50 CST 2016
     */
    int updateByPrimaryKey(HaiGoods record);
}