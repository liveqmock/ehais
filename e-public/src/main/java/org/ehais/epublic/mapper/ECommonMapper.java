package org.ehais.epublic.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface ECommonMapper {

	@Select("select count(*) from ${tableName} where ${field} = #{value} ")
    public int commonUnique(@Param("tableName") String tableName,@Param("field") String field, @Param("value") String value);

	@Select("select count(*) from ${tableName} where ${field} = #{value} and store_id = #{store_id}")
    public int commonUniqueStore(@Param("tableName") String tableName,@Param("field") String field, @Param("value") String value, @Param("store_id") Integer store_id);

	
}
