package org.ehais.shop.service.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiVoteMapper;
import org.ehais.shop.model.HaiVote;
import org.ehais.shop.model.HaiVoteExample;
import org.ehais.shop.service.HaiVoteService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "vote_name不能为空")//vote_name
@NotBlank(message = "start_time不能为空")//start_time
@NotBlank(message = "end_time不能为空")//end_time
@NotBlank(message = "can_multi不能为空")//can_multi
@NotBlank(message = "vote_count不能为空")//vote_count

**/
/**

**/




@Service("haiVoteService")
public class HaiVoteServiceImpl  extends CommonServiceImpl implements HaiVoteService{
	
	@Autowired
	private HaiVoteMapper haiVoteMapper;


	public ReturnObject<HaiVote> vote_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiVote> vote_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String voteName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		
		if(StringUtils.isNotEmpty(voteName))c.andVoteNameLike("%"+voteName+"%");
		List<HaiVote> list = haiVoteMapper.selectByExample(example);
		long total = haiVoteMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiVote> vote_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiVote model = new HaiVote();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiVote> vote_insert_submit(HttpServletRequest request,HaiVote model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setUpdateDate(date);

		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		c.andVoteNameEqualTo(model.getVoteName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiVoteMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiVoteMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiVote> vote_update(HttpServletRequest request,Integer voteId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andVoteIdEqualTo(voteId);
		List<HaiVote> list = haiVoteMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiVote model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiVote> vote_update_submit(HttpServletRequest request,HaiVote model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andVoteIdEqualTo(model.getVoteId());
		c.andStoreIdEqualTo(store_id);

		long count = haiVoteMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiVote bean = haiVoteMapper.selectByPrimaryKey(model.getVoteId());

bean.setVoteName(model.getVoteName());
bean.setVoteDesc(model.getVoteDesc());
bean.setVoteContent(model.getVoteContent());
bean.setVoteIcon(model.getVoteIcon());
bean.setVotePic(model.getVotePic());
bean.setStartTime(model.getStartTime());
bean.setEndTime(model.getEndTime());
bean.setCanMulti(model.getCanMulti());
bean.setVoteCount(model.getVoteCount());
bean.setCreateDate(model.getCreateDate());
bean.setUpdateDate(model.getUpdateDate());
bean.setLastDate(model.getLastDate());


		Date date = new Date();
		model.setUpdateDate(date);

		int code = haiVoteMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiVote> vote_info(HttpServletRequest request,Integer voteId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		c.andVoteIdEqualTo(voteId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiVote> list = haiVoteMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiVote model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiVote> vote_find(HttpServletRequest request,Integer voteId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		c.andVoteIdEqualTo(voteId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiVote> list = haiVoteMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiVote model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiVote> vote_delete(HttpServletRequest request,Integer voteId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiVote> rm = new ReturnObject<HaiVote>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiVoteExample example = new HaiVoteExample();
		HaiVoteExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andVoteIdEqualTo(voteId);

		long count = haiVoteMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiVoteMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiVote model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "vote.xml",model,"hai_vote",optionMap);
		
		
		return bootStrapList;
	}













	
}











