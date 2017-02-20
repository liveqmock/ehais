package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiCartWithBLOBs;

public interface HaiCartMapper {
	

    
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiCartWithBLOBs get_hai_cart_info(@Param("rec_id") Long rec_id,@Param("goods_id") Long goods_id, @Param("user_id") Long user_id, @Param("session_id") String session_id);
    
    
    int get_hai_cart_goods(@Param("goods_id") Long goods_id, @Param("user_id") Long user_id, @Param("session_id") String session_id);
    
    
    
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiCartWithBLOBs get_hai_cart_info_session(@Param("session_id") String session_id, @Param("rec_id") Long rec_id);

    
    int updateUserBySession(@Param("userId") Long userId,@Param("sessionId") String sessionId);

    
    int deleteUserBySession(@Param("userId") Long userId);

    
    int updateCartQuantity(
    		@Param("recId") Long recId,
    		@Param("goodsId") Long goodsId,
    		@Param("goodsNumber") Integer goodsNumber,
    		@Param("userId") Long userId,
    		@Param("sessionId") String sessionId
    		);

    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiCart> hai_cart_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<HaiCart> hai_cart_list_by_example(HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    HaiCart get_hai_cart_info(@Param("store_id") Integer store_id, @Param("rec_id") Long rec_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int countByExample(HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int deleteByExample(HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int deleteByPrimaryKey(Long recId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int insert(HaiCartWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int insertSelective(HaiCartWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    List<HaiCartWithBLOBs> selectByExampleWithBLOBs(HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    List<HaiCart> selectByExample(HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    HaiCartWithBLOBs selectByPrimaryKey(Long recId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int updateByExampleSelective(@Param("record") HaiCartWithBLOBs record, @Param("example") HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") HaiCartWithBLOBs record, @Param("example") HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int updateByExample(@Param("record") HaiCart record, @Param("example") HaiCartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int updateByPrimaryKeySelective(HaiCartWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(HaiCartWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_cart
     *
     * @mbggenerated Wed Jun 15 22:58:24 CST 2016
     */
    int updateByPrimaryKey(HaiCart record);
}