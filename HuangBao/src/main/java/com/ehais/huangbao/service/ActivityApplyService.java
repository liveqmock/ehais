package com.ehais.huangbao.service;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;
import com.ehais.huangbao.model.HaiActivityApply;

public interface ActivityApplyService extends CommonService{
	
	public ReturnObject<HaiActivityApply> activityApply_list(Integer store_id,Integer actId) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_list_json(Integer store_id,Integer actId,Integer page,Integer len) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_insert(Integer store_id) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_insert_submit(HaiActivityApply model) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_update_submit(HaiActivityApply model) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<HaiActivityApply> activityApply_delete(Integer store_id,Integer key) throws Exception;
	
	

}
