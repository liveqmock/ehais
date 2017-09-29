package org.ehais.shop.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiForumMapper;
import org.ehais.shop.model.HaiForum;
import org.ehais.shop.model.HaiForumExample;
import org.ehais.shop.service.HaiForumService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("haiForumService")
public class HaiForumServiceImpl  extends CommonServiceImpl implements HaiForumService{
	
	@Autowired
	private HaiForumMapper haiForumMapper;
	@Autowired
	private EHaiUsersMapper haiUsersMapper;
	@Autowired
	private EHaiArticleMapper haiArticleMapper;
	
	public ReturnObject<HaiForum> forum_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiForum> forum_list_json(HttpServletRequest request,EConditionObject condition,String tablename) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);

		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		c.andStoreIdEqualTo(store_id);
		c.andTableNameEqualTo(tablename);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("forum_id desc");
		List<HaiForum> list = haiForumMapper.selectByExample(example);
		long total = haiForumMapper.countByExample(example);
		
		List<Long> usersId = new ArrayList<Long>();
		List<Integer> articlesId = new ArrayList<Integer>();
		for (HaiForum haiForum : list) {
			usersId.add(haiForum.getUserId());
			articlesId.add(haiForum.getTableId().intValue());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(usersId.size() > 0){
			EHaiUsersExample userExample = new EHaiUsersExample();
			userExample.createCriteria().andUserIdIn(usersId);
			List<EHaiUsers> listUser = haiUsersMapper.selectByExample(userExample);
			map.put("listUser", listUser);
		}
		
		if(articlesId.size() > 0){
			EHaiArticleExample articleExample = new EHaiArticleExample();
			articleExample.createCriteria().andArticleIdIn(articlesId);
			List<EHaiArticle> listArticle = haiArticleMapper.selectByExample(articleExample);
			map.put("listArticle", listArticle);
		}
		
		rm.setMap(map);
		

		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiForum> forum_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiForum model = new HaiForum();


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiForum> forum_insert_submit(HttpServletRequest request,HaiForum model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);

		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		long count = haiForumMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiForumMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiForum> forum_update(HttpServletRequest request,Long forumId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		c.andForumIdEqualTo(forumId);
		c.andStoreIdEqualTo(store_id);
		List<HaiForum> list = haiForumMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiForum model = list.get(0);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiForum> forum_update_submit(HttpServletRequest request,HaiForum model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andForumIdEqualTo(model.getForumId());

		long count = haiForumMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiForum bean = haiForumMapper.selectByPrimaryKey(model.getForumId());

		bean.setUserId(model.getUserId());
		bean.setTableName(model.getTableName());
		bean.setTableId(model.getTableId());
		bean.setContent(model.getContent());
		bean.setCreateDate(model.getCreateDate());
		bean.setStoreId(model.getStoreId());
		bean.setParentForumId(model.getParentForumId());


		int code = haiForumMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiForum> forum_info(HttpServletRequest request,Long forumId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		c.andForumIdEqualTo(forumId);
		c.andStoreIdEqualTo(store_id);
		List<HaiForum> list = haiForumMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiForum model = list.get(0);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiForum> forum_find(HttpServletRequest request,Long forumId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		c.andForumIdEqualTo(forumId);
		c.andStoreIdEqualTo(store_id);
		List<HaiForum> list = haiForumMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiForum model = list.get(0);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiForum> forum_delete(HttpServletRequest request,Long forumId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiForumExample example = new HaiForumExample();
		HaiForumExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andForumIdEqualTo(forumId);

		long count = haiForumMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiForumMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiForum model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "forum.xml",model,"hai_forum",optionMap);
		
		
		return bootStrapList;
	}




	
}











