package org.ehais.weixin.service.wx;

import org.ehais.epublic.model.WpPublic;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

public interface PublicService extends CommonService {

	public ReturnObject<WpPublic> public_list(Long store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<WpPublic> public_insert(Integer store_id) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_insert_submit(WpPublicWithBLOBs model) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_update(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpPublicWithBLOBs> public_update_submit(WpPublicWithBLOBs model) throws Exception;
	public ReturnObject<WpPublic> public_find(Integer store_id,Integer key) throws Exception;
	public ReturnObject<WpPublic> public_find(Integer key) throws Exception;
	public ReturnObject<WpPublic> public_delete(Long uid,Integer key) throws Exception;
	
	//获取用户的微信配置信息，如果没有微信号存在，则给他自动添加一条
	public ReturnObject<WpPublicWithBLOBs> public_by_user(Long user_id)throws Exception;
}
