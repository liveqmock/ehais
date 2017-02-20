package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.HaiCategoryMapper;
import org.ehais.weixin.model.HaiCategory;
import org.ehais.weixin.model.HaiCategoryExample;
import org.ehais.weixin.model.HaiCategoryWithBLOBs;
import org.ehais.weixin.service.action.HaiCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haicategoryService")
public class HaiCategoryServiceImpl  extends CommonServiceImpl implements HaiCategoryService{
	
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	
	public ReturnObject<HaiCategory> haicategory_list(HttpServletRequest request,String cat_code,Integer store_id) throws Exception{
		
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCategory> haicategory_list_json(HttpServletRequest request,String cat_code,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		Integer start = (page - 1 ) * len;
		
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		List<HaiCategory> list = haiCategoryMapper.hai_category_list_by_example(example);
		Integer total = haiCategoryMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> haicategory_insert(HttpServletRequest request,String cat_code,Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();	
		HaiCategoryWithBLOBs model = new HaiCategoryWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_insert_submit(HttpServletRequest request,String cat_code,HaiCategoryWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		int code = haiCategoryMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> haicategory_update(HttpServletRequest request,String cat_code,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		
		HaiCategoryWithBLOBs model = haiCategoryMapper.selectByPrimaryKey(Short.valueOf(key+""));
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiCategoryWithBLOBs> haicategory_update_submit(HttpServletRequest request,String cat_code,HaiCategoryWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		
		c.andCatIdEqualTo(model.getCatId());
		int code = haiCategoryMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiCategoryWithBLOBs> haicategory_find(HttpServletRequest request,String cat_code,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategoryWithBLOBs> rm = new ReturnObject<HaiCategoryWithBLOBs>();
		
		
		HaiCategoryWithBLOBs model = haiCategoryMapper.selectByPrimaryKey(Short.valueOf(key+""));
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiCategory> haicategory_delete(HttpServletRequest request,String cat_code,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCategory> rm = new ReturnObject<HaiCategory>();
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		
		c.andCatIdEqualTo(Short.valueOf(key+""));
		int code = haiCategoryMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiCategoryWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}











