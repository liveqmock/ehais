package org.ehais.epublic.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface ECommonMapper {

	@Select("select count(*) from ${tableName} where ${field} = #{value} ")
    public int commonUnique(@Param("tableName") String tableName,@Param("field") String field, @Param("value") String value);

	@Select("select count(*) from ${tableName} where ${field} = #{value} and store_id = #{store_id}")
    public int commonUniqueStore(@Param("tableName") String tableName,@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

	@Update("update ${tableName} set ${setField} = #{setValue} where ${field} = #{value} and store_id = #{store_id}")
	public int commonUpdateBooleanValue(@Param("tableName") String tableName,
			@Param("setField") String setField, 
			@Param("setValue") String setValue,
			@Param("field") String field, 
			@Param("value") String value, 
			@Param("store_id") Integer store_id
			);
	
	
	@Update("update ${tableName} set ${setField} = #{setValue} , ${now_date} = now() where ${field} = #{value} and store_id = #{store_id}")
	public int commonUpdateBooleanValueAndNowDate(@Param("tableName") String tableName,
			@Param("setField") String setField, 
			@Param("setValue") String setValue,
			@Param("field") String field, 
			@Param("value") String value, 
			@Param("now_date") String now_date,
			@Param("store_id") Integer store_id
			);

	
}
