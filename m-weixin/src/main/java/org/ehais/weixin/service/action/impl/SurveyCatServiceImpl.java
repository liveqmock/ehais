package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.WpSurveyCatMapper;
import org.ehais.weixin.model.WpSurveyCat;
import org.ehais.weixin.model.WpSurveyCatExample;
import org.ehais.weixin.service.action.SurveyCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("surveyCatService")
public class SurveyCatServiceImpl   extends CommonServiceImpl implements SurveyCatService{
	
	@Autowired
	private WpSurveyCatMapper wpSurveyCatMapper;
	
	public ReturnObject<WpSurveyCat> survey_cat_list(Integer store_id) throws Exception{
		
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpSurveyCat> survey_cat_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		Integer start = (page - 1 ) * len;
		
		WpSurveyCatExample example = new WpSurveyCatExample();
		WpSurveyCatExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		List<WpSurveyCat> list = wpSurveyCatMapper.wp_survey_cat_list_by_example(example);
		Integer total = wpSurveyCatMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpSurveyCat> survey_cat_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();	
		WpSurveyCat model = new WpSurveyCat();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpSurveyCat> survey_cat_insert_submit(WpSurveyCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		int code = wpSurveyCatMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpSurveyCat> survey_cat_update(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		
		WpSurveyCat model = wpSurveyCatMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpSurveyCat> survey_cat_update_submit(WpSurveyCat model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		WpSurveyCatExample example = new WpSurveyCatExample();
		WpSurveyCatExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(model.getStoreId());
//		c.andKeyIdEqualTo(model.getKeyId());
		int code = wpSurveyCatMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpSurveyCat> survey_cat_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		
		
		WpSurveyCat model = wpSurveyCatMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpSurveyCat> survey_cat_delete(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpSurveyCat> rm = new ReturnObject<WpSurveyCat>();
		WpSurveyCatExample example = new WpSurveyCatExample();
		WpSurveyCatExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
//		c.andKeyIdEqualTo(key);
		int code = wpSurveyCatMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(WpSurveyCat model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "surCatId", "", model.getSurCatId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "", model.getTitle(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("checkbox", "wxid", "", model.getWxid(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("checkbox", "isVoid", "", model.getIsVoid(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("checkbox", "orderSort", "", model.getOrderSort(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
}
