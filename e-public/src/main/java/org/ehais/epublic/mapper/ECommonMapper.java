package org.ehais.epublic.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ehais.model.TreeModel;


public interface ECommonMapper {

	@Select("select ${id} as id ,${title} as title,ifnull(${parent_id},0) as parent_id from ${tableName} where store_id = #{store_id} ")
	@Results(value = 
			{ 
			@Result(property = "id", column = "id"), 
			@Result(property = "title", column = "title"), 
			@Result(property = "parent_id", column = "parent_id")
			}
	)
    public List<TreeModel> commonTreeData(@Param("tableName") String tableName,
    		@Param("id") String id,
    		@Param("title") String title, 
    		@Param("parent_id") String parent_id, 
    		@Param("store_id") Integer store_id
    		);

	
	@Select("select ${id} as id ,${title} as title,ifnull(${parent_id},0) as parent_id from ${tableName} where store_id = #{store_id} and module = #{module}")
	@Results(value = 
			{ 
			@Result(property = "id", column = "id"), 
			@Result(property = "title", column = "title"), 
			@Result(property = "parent_id", column = "parent_id")
			}
	)
    public List<TreeModel> commonModuleTreeData(@Param("tableName") String tableName,
    		@Param("id") String id,
    		@Param("title") String title, 
    		@Param("parent_id") String parent_id, 
    		@Param("store_id") Integer store_id,
    		@Param("module") String module
    		);
	
	
	@Select("select ${id} as id ,${title} as title,ifnull(${parent_id},0) as parent_id from ${tableName} where store_id = #{store_id} and module = #{module} and classify = #{classify}")
	@Results(value = 
			{ 
			@Result(property = "id", column = "id"), 
			@Result(property = "title", column = "title"), 
			@Result(property = "parent_id", column = "parent_id")
			}
	)
    public List<TreeModel> commonModuleClassifyTreeData(@Param("tableName") String tableName,
    		@Param("id") String id,
    		@Param("title") String title, 
    		@Param("parent_id") String parent_id, 
    		@Param("store_id") Integer store_id,
    		@Param("module") String module,
    		@Param("classify") String classify
    		);
	
	
	
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
