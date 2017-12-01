package org.ehais.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiNavMapper;
import org.ehais.shop.model.HaiNav;
import org.ehais.shop.model.HaiNavExample;
import org.ehais.shop.service.NavService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("navService")
public class NavServiceImpl  extends CommonServiceImpl implements NavService{
	
	@Autowired
	private HaiNavMapper haiNavMapper;
	
	public ReturnObject<HaiNav> nav_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiNav> nav_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<HaiNav> list = haiNavMapper.selectByExample(example);
		Integer total = haiNavMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiNav> nav_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNav model = new HaiNav();
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiNav> nav_insert_submit(HttpServletRequest request,HaiNav model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();

		if(model.getName() == null || model.getName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		c.andNameEqualTo(model.getName());
		c.andStoreIdEqualTo(store_id);
		int count = haiNavMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiNavMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiNav> nav_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNav model = haiNavMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiNav> nav_update_submit(HttpServletRequest request,HaiNav model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(model.getId());
		c.andStoreIdEqualTo(store_id);

		int count = haiNavMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiNavMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiNav> nav_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiNav model = haiNavMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiNav> nav_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiNav> rm = new ReturnObject<HaiNav>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andIdEqualTo(id);
		int code = haiNavMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiNav model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "nav.xml",model,"hai_nav",optionMap);
		
		
		return bootStrapList;
	}

	@Override
	public List<HaiNav> list(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		HaiNavExample example = new HaiNavExample();
		HaiNavExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		example.setOrderByClause("sort asc");
		List<HaiNav> list = haiNavMapper.selectByExample(example);
		
		return list;
	}
	
}











