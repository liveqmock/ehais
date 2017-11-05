package org.ehais.epublic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;

public interface EHaiUsersMapper {
	
	@ResultMap(value = "BaseResultMap")
    EHaiUsers login(@Param("user_name") String user_name, @Param("password") String password);

	@ResultMap(value = "BaseResultMap")
    EHaiUsers login_store(
    		@Param("user_name") String user_name, 
    		@Param("password") String password,
    		@Param("store_id") Integer store_id);

	
	public int checkUser(@Param("userName") String userName,@Param("email") String email);
	
	@Select("select * from hai_users where user_id in (${userIds})")
	@ResultMap(value = "BaseResultMap")
	public List<EHaiUsers> inUserIdList(@Param("userIds") String userIds);
	
	
	@ResultMap(value = "BaseResultMap")
	public List<EHaiUsers> selectUsersLike(@Param("store_id") Integer store_id,
			@Param("keyword") String keyword,
			@Param("orderByClause") String orderByClause,
			@Param("start") Integer start,
			@Param("len") Integer len
			);
	
	public int selectUsersLikeCount(@Param("store_id") Integer store_id,
			@Param("keyword") String keyword);
	
	
	@Select("select DISTINCT(store_id) from hai_users where ifnull(store_id,0) != 0 and openid is not NULL and (nickname is NULL or face_image is NULL)")
	public List<Integer> wxDistinctStoreId();
	
	
	@Select("select * from hai_users where user_id = #{userIds}")
	@ResultMap(value = "BaseResultMap")
	public EHaiUsers userInfo(@Param("userIds") Long userIds);
	
	@Select("select * from hai_users where openid = #{openid} limit 0,1")
	@ResultMap(value = "BaseResultMap")
	public EHaiUsers userInfoOpenId(@Param("openid") String openid);
	
	
	@Select("select * from hai_users where store_id = #{store_id} and openid = #{openid} limit 0,1")
	@ResultMap(value = "BaseResultMap")
	public EHaiUsers userInfoOpenIdStore(@Param("store_id") Integer store_id,@Param("openid") String openid);
	
	
	@Select("select * from hai_users where store_id = #{store_id} and user_name = #{user_name} limit 0,1")
	@ResultMap(value = "BaseResultMap")
	public EHaiUsers userNameByStore(@Param("store_id") Integer store_id,@Param("user_name") String user_name);
	
	
	
	public void modifyPassword(@Param("userId") Long userId,@Param("password") String password) ;
	
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<EHaiUsers> hai_users_list(@Param("store_id") Integer store_id, @Param("start") Integer start, @Param("len") Integer len);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    @ResultMap(value = "BaseResultMap")
    List<EHaiUsers> hai_users_list_by_example(EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    @ResultMap(value = "ResultMapWithBLOBs")
    EHaiUsers get_hai_users_info(@Param("store_id") Integer store_id, @Param("user_id") Long user_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    Long countByExample(EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int deleteByExample(EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int insert(EHaiUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int insertSelective(EHaiUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    List<EHaiUsers> selectByExampleWithBLOBs(EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    List<EHaiUsers> selectByExample(EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    EHaiUsers selectByPrimaryKey(Long userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int updateByExampleSelective(@Param("record") EHaiUsers record, @Param("example") EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int updateByExampleWithBLOBs(@Param("record") EHaiUsers record, @Param("example") EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int updateByExample(@Param("record") EHaiUsers record, @Param("example") EHaiUsersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int updateByPrimaryKeySelective(EHaiUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int updateByPrimaryKeyWithBLOBs(EHaiUsers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hai_users
     *
     * @mbggenerated Wed Jun 15 22:02:49 CST 2016
     */
    int updateByPrimaryKey(EHaiUsers record);
}