package com.ehais.tracking.service.tracking;

import javax.servlet.http.HttpServletRequest;

import org.ehais.service.CommonService;
import org.ehais.tools.ReturnObject;

import com.ehais.tracking.entity.Message;





public interface MessageService extends CommonService{
	public ReturnObject<Message> message_list(HttpServletRequest request,Integer store_id) throws Exception;
	public ReturnObject<Message> message_list_json(HttpServletRequest request,Integer store_id,Integer page,Integer len) throws Exception;
	public ReturnObject<Message> message_insert(HttpServletRequest request,Integer store_id) throws Exception;
	public ReturnObject<Message> message_insert_submit(HttpServletRequest request,Message model) throws Exception;
	public ReturnObject<Message> message_update(HttpServletRequest request,Integer store_id,Integer key) throws Exception;
	public ReturnObject<Message> message_update_submit(HttpServletRequest request,Message model) throws Exception;
	public ReturnObject<Message> message_find(HttpServletRequest request,Integer store_id,Integer key) throws Exception;
	public ReturnObject<Message> message_delete(HttpServletRequest request,Integer store_id,Integer key) throws Exception;
	
	

}


