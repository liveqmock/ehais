package com.ehais.tracking.service.tracking.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.model.BootStrapModel;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.MessageDao;
import com.ehais.tracking.entity.Message;
import com.ehais.tracking.service.tracking.MessageService;





//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


//////////////////////////dao dao dao dao ///////////////////////////////

@Service("messageService")
public class MessageServiceImpl  extends TrackingCommonServiceImpl implements MessageService{
	
	@Autowired
	private MessageDao messageDao;
	
	public ReturnObject<Message> message_list(HttpServletRequest request,Integer store_id) throws Exception{
		
		ReturnObject<Message> rm = new ReturnObject<Message>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Message> message_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub

		Map<String,Object> map = new HashMap<String,Object>();
		this.storeIdMap(request, map);
		ReturnObject<Message> rm = messageDao.select(Message.class, page, len, map);
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<Message> message_insert(HttpServletRequest request,Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Message> rm = new ReturnObject<Message>();	
		Message model = new Message();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<Message> message_insert_submit(HttpServletRequest request,Message model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Message> rm = new ReturnObject<Message>();
		messageDao.insert(model);
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<Message> message_update(HttpServletRequest request,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Message> rm = new ReturnObject<Message>();
		
		Message model = messageDao.findById(Message.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<Message> message_update_submit(HttpServletRequest request,Message model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Message> rm = new ReturnObject<Message>();
		messageDao.update(model);
		rm.setCode(1);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<Message> message_find(HttpServletRequest request,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Message> rm = new ReturnObject<Message>();
		
		
		Message model = messageDao.findById(Message.class, "id", key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<Message> message_delete(HttpServletRequest request,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Message> rm = new ReturnObject<Message>();
		messageDao.delete(Message.class, key);
		rm.setCode(1);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(Message model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "标题", model.getTitle(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textarea", "content", "内容", model.getContent(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "userId", "", model.getUserId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "createTime", "", model.getCreateTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "type", "", model.getType(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "status", "", model.getStatus(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "schoolId", "", model.getSchoolId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "typeId", "", model.getTypeId(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
}


