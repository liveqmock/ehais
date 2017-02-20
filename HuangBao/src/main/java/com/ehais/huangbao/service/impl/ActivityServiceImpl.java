package com.ehais.huangbao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import com.ehais.huangbao.mapper.HaiActivityApplyMapper;
import com.ehais.huangbao.mapper.HaiActivityMapper;
import com.ehais.huangbao.model.HaiActivity;
import com.ehais.huangbao.model.HaiActivityApply;
import com.ehais.huangbao.model.HaiActivityApplyExample;
import com.ehais.huangbao.model.HaiActivityExample;
import com.ehais.huangbao.model.HaiActivityWithBLOBs;
import com.ehais.huangbao.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("activityService")
public class ActivityServiceImpl extends CommonServiceImpl implements ActivityService {

//	@Autowired
//	private HaiActivityMapper haiActivityMapper;
	@Autowired
	private HaiActivityApplyMapper haiActivityApplyMapper;


	@Autowired
	private HaiActivityMapper activityMapper;
	
	public ReturnObject<HaiActivity> activity_list(Integer store_id) throws Exception{
		
		ReturnObject<HaiActivity> ro = new ReturnObject<HaiActivity>();
		
		
		ro.setCode(1);
		return ro;
	}

	public ReturnObject<HaiActivity> activity_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> ro = new ReturnObject<HaiActivity>();
		Integer start = (page - 1 ) * len;
		
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		c.andWxidEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("act_id desc");
		List<HaiActivity> list = activityMapper.hai_activity_list_by_example(example);
		Integer total = activityMapper.countByExample(example);
		ro.setCode(1);
		ro.setRows(list);
		ro.setTotal(total);
		
		return ro;
	}

	public ReturnObject<HaiActivityWithBLOBs> activity_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityWithBLOBs> ro = new ReturnObject<HaiActivityWithBLOBs>();
		HaiActivityWithBLOBs model = new HaiActivityWithBLOBs();
		
		ro.setBootStrapList(this.formatBootStrapList(model));
		
		ro.setCode(1);
		return ro;
	}
	
	public ReturnObject<HaiActivityWithBLOBs> activity_insert_submit(HaiActivityWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityWithBLOBs> ro = new ReturnObject<HaiActivityWithBLOBs>();
		int code = activityMapper.insertSelective(model);
		ro.setCode(code);
		ro.setMsg("添加成功");
		return ro;
	}

	public ReturnObject<HaiActivityWithBLOBs> activity_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityWithBLOBs> ro = new ReturnObject<HaiActivityWithBLOBs>();

		HaiActivityWithBLOBs model = activityMapper.selectByPrimaryKey(key);
		ro.setBootStrapList(this.formatBootStrapList(model));
		
		ro.setCode(1);
		ro.setModel(model);
		return ro;
	}
	
	public ReturnObject<HaiActivityWithBLOBs> activity_update_submit(HaiActivityWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityWithBLOBs> ro = new ReturnObject<HaiActivityWithBLOBs>();
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(model.getStoreId());
		c.andActIdEqualTo(model.getActId());
		int code = activityMapper.updateByExampleSelective(model, example);
		ro.setCode(code);
		ro.setMsg("编辑成功");
		return ro;
	}

	public ReturnObject<HaiActivityWithBLOBs> activity_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityWithBLOBs> ro = new ReturnObject<HaiActivityWithBLOBs>();

		
		HaiActivityWithBLOBs model = activityMapper.selectByPrimaryKey(key);
		ro.setBootStrapList(this.formatBootStrapList(model));
		
		ro.setCode(1);
		ro.setModel(model);
		return ro;
	}

	public ReturnObject<HaiActivity> activity_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivity> ro = new ReturnObject<HaiActivity>();
		HaiActivityExample example = new HaiActivityExample();
		HaiActivityExample.Criteria c = example.createCriteria();
		c.andActIdEqualTo(key);
		int code = activityMapper.deleteByExample(example);
		ro.setCode(code);
		ro.setMsg("删除成功");
		return ro;
	}
	

	public ReturnObject<HaiActivityWithBLOBs> activity_info(Integer wxid, Integer act_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityWithBLOBs> ro = new ReturnObject<HaiActivityWithBLOBs>();
		
		HaiActivityWithBLOBs model = activityMapper.selectByPrimaryKey(act_id);
		
		ro.setModel(model);
		return ro;
	}

	public ReturnObject<HaiActivityApply> apply(Integer wxid, Integer act_id,
			String realname, String mobile, String openid,String company,String remark) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> ro = new ReturnObject<HaiActivityApply>();
		ro.setCode(0);
		
		HaiActivityApplyExample example = new HaiActivityApplyExample();
		HaiActivityApplyExample.Criteria c = example.createCriteria();
		c.andActIdEqualTo(act_id);
		c.andOpenidEqualTo(openid);
		int i = haiActivityApplyMapper.countByExample(example);
		if(i > 0){
			ro.setMsg("已报名了，不需要重复报名！");
			return ro;
		}
		
		HaiActivityApply record = new HaiActivityApply();
		Date date = new Date();
		
		record.setActId(act_id);
		record.setMobile(mobile);
		record.setRealname(realname);
		record.setWxid(wxid);
		record.setOpenid(openid);		
		record.setCreateDate(date);
		record.setCompany(company);
		record.setRemark(remark);
		
		haiActivityApplyMapper.insert(record);
		
		ro.setCode(1);
		ro.setModel(record);
		ro.setMsg("报名成功");
		
		return ro;
	}
	
	
	private List<BootStrapModel> formatBootStrapList(HaiActivityWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "actId", "", model.getActId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "标题", model.getTitle(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("hidden", "wxid", "", model.getWxid(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textareaV1", "intro", "摘要", model.getIntro(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textarea", "content", "内容", model.getContent(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "act_type_id", "", model.getActTypeId(), "请输入", "", "", null, 0));
		
//		bootStrapList.add(new BootStrapModel("checkbox", "apply_count", "", model.getApplyCount(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "startDate", "开始日期", model.getStartDate(), "请输入开始时间", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "endDate", "结束日期", model.getEndDate(), "请输入截止时间", "", "", null, 0));
		
		bootStrapList.add(new BootStrapModel("images", "activityPic", "图片", model.getActivityPic(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
	
	
}
