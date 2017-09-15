package org.ehais.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.ehais.shop.model.PartnerStatisticsStoreCount;
import org.ehais.shop.model.PartnerStatisticsUsersCount;

public interface PartnerStatisticsMapper {

	@Select("select count(*) as count ,DATE_FORMAT(reg_time,'%Y-%m-%d') as regTime "+
	" from hai_users  "+
	" where store_id in (${store_id_s}) and IFNULL(reg_time,'')!='' " +
	" and DATE_FORMAT(reg_time,'%Y-%m-%d') >= #{start_date} and DATE_FORMAT(reg_time,'%Y-%m-%d') <= #{end_date} "+
	" group by DATE_FORMAT(reg_time,'%Y-%m-%d') "+
	" order by reg_time asc")
	@Results(value = {
			@Result(property="count", column="count"),
			@Result(property="regTime", column="regTime")
	})
	List<PartnerStatisticsUsersCount> statisticsUsers(
			@Param("store_id_s") String store_id_s,
			@Param("start_date") String start_date,
			@Param("end_date") String end_date
			) throws Exception;
	
	
	@Select("select count(*) as count ,truncate((add_time / 100),0) as addTime "+
			" from hai_store  "+
			" where store_id in (${store_id_s}) and IFNULL(add_time,0)!=0 " +
			" and truncate((add_time / 100),0) >= #{start_time} and truncate((add_time / 100),0) <= #{end_time} "+
			" group by truncate((add_time / 100),0) "+
			" order by add_time asc")
			@Results(value = {
					@Result(property="count", column="count"),
					@Result(property="addTime", column="addTime")
			})
			List<PartnerStatisticsStoreCount> statisticsStore(
					@Param("store_id_s") String store_id_s,
					@Param("start_time") Integer start_time,
					@Param("end_time") Integer end_time
					) throws Exception;
	
	
}
