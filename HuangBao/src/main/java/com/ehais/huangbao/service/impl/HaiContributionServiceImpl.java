package com.ehais.huangbao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.HaiCategoryMapper;
import org.ehais.weixin.model.HaiCategory;
import org.ehais.weixin.model.HaiCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.huangbao.mapper.HaiContributionMapper;
import com.ehais.huangbao.model.HaiContribution;
import com.ehais.huangbao.model.HaiContributionExample;
import com.ehais.huangbao.service.HaiContributionService;


//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )
//wxid = store_id;微信编号或暂使用商户编号

@Service("haicontributionService")
public class HaiContributionServiceImpl  extends CommonServiceImpl implements HaiContributionService{
	
	
	@Autowired
	private HaiContributionMapper haiContributionMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	
	public ReturnObject<HaiContribution> haicontribution_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiContribution> haicontribution_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer start = (page - 1 ) * len;
		
		HaiContributionExample example = new HaiContributionExample();
		HaiContributionExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(wxid);
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("con_id desc");
		List<HaiContribution> list = haiContributionMapper.hai_contribution_list_by_example(example);
		Integer total = haiContributionMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		HaiCategoryExample exampleCat = new HaiCategoryExample();
		HaiCategoryExample.Criteria cCat = exampleCat.createCriteria();
		cCat.andStoreIdEqualTo(wxid);
		List<HaiCategory> cat_list = haiCategoryMapper.selectByExample(exampleCat);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("cat_list", cat_list);
		rm.setMap(map);
		
		return rm;
	}

	public ReturnObject<HaiContribution> haicontribution_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();	
		HaiContribution model = new HaiContribution();
		model.setStoreId(wxid);
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiContribution> haicontribution_insert_submit(HttpServletRequest request,HaiContribution model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		model.setStoreId(wxid);
		int code = haiContributionMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiContribution> haicontribution_update(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		HaiContribution model = haiContributionMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiContribution> haicontribution_update_submit(HttpServletRequest request,HaiContribution model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		HaiContributionExample example = new HaiContributionExample();
		HaiContributionExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(wxid);
		c.andConIdEqualTo(model.getConId());
		int code = haiContributionMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiContribution> haicontribution_find(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		
		HaiContribution model = haiContributionMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiContribution> haicontribution_delete(HttpServletRequest request,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer wxid = (Integer)request.getSession().getAttribute(Constants.SESSION_WX_ID);
		HaiContributionExample example = new HaiContributionExample();
		HaiContributionExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(wxid);
		c.andConIdEqualTo(key);
		int code = haiContributionMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiContribution model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "conId", "", model.getConId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select", "catId", "分类", model.getCatId(), "请输入", "", "", catMap(model.getStoreId(),true), 0));
		bootStrapList.add(new BootStrapModel("text", "title", "标题", model.getTitle(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textareaV1", "description", "摘要", model.getDescription(), "请输入", "", "", null, 0));
		
		bootStrapList.add(new BootStrapModel("textarea", "content", "内容", model.getContent(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "articleImages", "", model.getArticleImages(), "请输入", "", "", null, 0));

		return bootStrapList;
	}
	
	
	private Map<String,String> catMap(Integer store_id,boolean t){
		HaiCategoryExample exampleCat = new HaiCategoryExample();
		HaiCategoryExample.Criteria cCat = exampleCat.createCriteria();
		cCat.andStoreIdEqualTo(store_id);
		if(t)cCat.andParentIdEqualTo(Short.valueOf("0"));
		List<HaiCategory> cat_list = haiCategoryMapper.selectByExample(exampleCat);
		Map<String,String> m = new HashMap<String, String>();
		
		for (HaiCategory haiCategory : cat_list) {
			m.put(String.valueOf(haiCategory.getCatId()), haiCategory.getCatName());
		}
		
		return m;
	}
	
}











