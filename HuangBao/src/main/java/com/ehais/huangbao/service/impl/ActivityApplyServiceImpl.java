package com.ehais.huangbao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import com.ehais.huangbao.mapper.HaiActivityApplyMapper;
import com.ehais.huangbao.model.HaiActivityApply;
import com.ehais.huangbao.model.HaiActivityApplyExample;
import com.ehais.huangbao.service.ActivityApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("activityApplyService")
public class ActivityApplyServiceImpl  extends CommonServiceImpl implements ActivityApplyService{
	
	@Autowired
	private HaiActivityApplyMapper haiActivityApplyMapper;
	
	public ReturnObject<HaiActivityApply> activityApply_list(Integer store_id,Integer actId) throws Exception{
		
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiActivityApply> activityApply_list_json(Integer store_id,Integer actId,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		Integer start = (page - 1 ) * len;
		
		HaiActivityApplyExample example = new HaiActivityApplyExample();
		HaiActivityApplyExample.Criteria c = example.createCriteria();
		c.andActIdEqualTo(actId);
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("create_date desc");
		List<HaiActivityApply> list = haiActivityApplyMapper.hai_activity_apply_list_by_example(example);
		Integer total = haiActivityApplyMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		return rm;
	}

	public ReturnObject<HaiActivityApply> activityApply_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		HaiActivityApply model = new HaiActivityApply();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiActivityApply> activityApply_insert_submit(HaiActivityApply model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		int code = haiActivityApplyMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiActivityApply> activityApply_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		
		HaiActivityApply model = haiActivityApplyMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiActivityApply> activityApply_update_submit(HaiActivityApply model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		HaiActivityApplyExample example = new HaiActivityApplyExample();
		HaiActivityApplyExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(model.getStoreId());
//		c.andKeyIdEqualTo(model.getKeyId());
		int code = haiActivityApplyMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiActivityApply> activityApply_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		
		
		HaiActivityApply model = haiActivityApplyMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiActivityApply> activityApply_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiActivityApply> rm = new ReturnObject<HaiActivityApply>();
		HaiActivityApplyExample example = new HaiActivityApplyExample();
		HaiActivityApplyExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
//		c.andKeyIdEqualTo(key);
		int code = haiActivityApplyMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiActivityApply model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "applyId", "", model.getApplyId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "actId", "", model.getActId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "realname", "", model.getRealname(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "mobile", "", model.getMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "openid", "", model.getOpenid(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "wxid", "", model.getWxid(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "createDate", "报名时间", model.getCreateDate(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
}
