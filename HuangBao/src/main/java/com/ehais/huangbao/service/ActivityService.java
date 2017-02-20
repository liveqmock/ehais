package com.ehais.huangbao.service;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import com.ehais.huangbao.model.HaiActivity;
import com.ehais.huangbao.model.HaiActivityApply;
import com.ehais.huangbao.model.HaiActivityWithBLOBs;

public interface ActivityService extends CommonService {

	
	

	
	
	public ReturnObject<HaiActivity> activity_list(Integer store_id) throws Exception;
	public ReturnObject<HaiActivity> activity_list_json(Integer store_id,Integer page,Integer len )throws Exception;
	public ReturnObject<HaiActivityWithBLOBs> activity_insert(Integer store_id) throws Exception;
	public ReturnObject<HaiActivityWithBLOBs> activity_insert_submit(HaiActivityWithBLOBs model) throws Exception;
	public ReturnObject<HaiActivityWithBLOBs> activity_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiActivityWithBLOBs> activity_update_submit(HaiActivityWithBLOBs model) throws Exception;
	public ReturnObject<HaiActivityWithBLOBs> activity_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiActivity> activity_delete(Integer store_id,Integer key) throws Exception;
	
	
	
	public ReturnObject<HaiActivityWithBLOBs> activity_info(Integer wxid,Integer act_id )throws Exception;
	
	public ReturnObject<HaiActivityApply> apply(Integer wxid,Integer act_id,String realname,String mobile,String openid,String company,String remark)throws Exception;
	
	
	
	
}
