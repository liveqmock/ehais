package org.ehais.shop.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.ehais.shop.model.HaiForumUser;

public interface HaiForumUserMapper {
    
	
	/**
	 * 返回论坛的用户与内容
	 * @param forum_id
	 * @param table_id
	 * @param table_name
	 * @return
	 */
	@Select("select f.forum_id,f.content,u.nickname,u.face_image from hai_forum f left join hai_users u on f.user_id = u.user_id where f.store_id = #{store_id} and f.table_id=#{table_id} and f.table_name = #{table_name} order by f.forum_id desc")
	@Options(useCache = true, flushCache = false, timeout = 10000)  
    @Results(value = {  
            @Result(id = true, property = "forumId", column = "forum_id", javaType = Long.class, jdbcType = JdbcType.BIGINT),  
            @Result(property = "content", column = "content", javaType = String.class, jdbcType = JdbcType.VARCHAR) ,
            @Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.VARCHAR), 
            @Result(property = "faceImage", column = "face_image", javaType = String.class, jdbcType = JdbcType.VARCHAR) 
    })
	public List<HaiForumUser> listForumUser(
			@Param("store_id") Integer store_id,
			@Param("table_id") Long table_id,
			@Param("table_name") String table_name
			);
}