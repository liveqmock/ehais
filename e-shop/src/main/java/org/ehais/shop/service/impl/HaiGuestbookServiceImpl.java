package org.ehais.shop.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiGuestbookMapper;
import org.ehais.shop.model.HaiGuestbook;
import org.ehais.shop.model.HaiGuestbookExample;
import org.ehais.shop.model.HaiGuestbookWithBLOBs;
import org.ehais.shop.service.HaiGuestbookService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**

**/
/**

**/




@Service("haiGuestbookService")
public class HaiGuestbookServiceImpl  extends CommonServiceImpl implements HaiGuestbookService{
	
	@Autowired
	private HaiGuestbookMapper haiGuestbookMapper;
	@Autowired
	private EHaiUsersMapper haiUsersMapper;

	public ReturnObject<HaiGuestbook> guestbook_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiGuestbook> rm = new ReturnObject<HaiGuestbook>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiGuestbook> guestbook_list_json(HttpServletRequest request,EConditionObject condition,Integer keySubId,String title) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbook> rm = new ReturnObject<HaiGuestbook>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("update_date desc");
		
		if(StringUtils.isNotEmpty(title))c.andTitleLike("%"+title+"%");
		List<HaiGuestbook> list = haiGuestbookMapper.selectByExample(example);
		long total = haiGuestbookMapper.countByExample(example);

		List<Long> usersId = new ArrayList<Long>();
		for (HaiGuestbook haiForum : list) {
			usersId.add(haiForum.getUserId());
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(usersId.size() > 0){
			EHaiUsersExample userExample = new EHaiUsersExample();
			userExample.createCriteria().andUserIdIn(usersId);
			List<EHaiUsers> listUser = haiUsersMapper.selectByExample(userExample);
			map.put("listUser", listUser);
		}

		
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbookWithBLOBs> rm = new ReturnObject<HaiGuestbookWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGuestbookWithBLOBs model = new HaiGuestbookWithBLOBs();


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_insert_submit(HttpServletRequest request,HaiGuestbookWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbookWithBLOBs> rm = new ReturnObject<HaiGuestbookWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setUpdateDate(date);

		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiGuestbookMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiGuestbookMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_update(HttpServletRequest request,Integer guestbookId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbookWithBLOBs> rm = new ReturnObject<HaiGuestbookWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGuestbookIdEqualTo(guestbookId);
		List<HaiGuestbookWithBLOBs> list = haiGuestbookMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGuestbookWithBLOBs model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_update_submit(HttpServletRequest request,HaiGuestbookWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbookWithBLOBs> rm = new ReturnObject<HaiGuestbookWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGuestbookIdEqualTo(model.getGuestbookId());
		c.andStoreIdEqualTo(store_id);

		long count = haiGuestbookMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiGuestbookWithBLOBs bean = haiGuestbookMapper.selectByPrimaryKey(model.getGuestbookId());

bean.setTitle(model.getTitle());
bean.setModule(model.getModule());
bean.setContent(model.getContent());
bean.setGuest(model.getGuest());
bean.setSex(model.getSex());
bean.setCompany(model.getCompany());
bean.setAddress(model.getAddress());
bean.setPostcode(model.getPostcode());
bean.setTelphone(model.getTelphone());
bean.setFax(model.getFax());
bean.setMobile(model.getMobile());
bean.setEmail(model.getEmail());
bean.setStatus(model.getStatus());
bean.setResultMessage(model.getResultMessage());
bean.setCreateDate(model.getCreateDate());
bean.setUpdateDate(model.getUpdateDate());
bean.setUserId(model.getUserId());


		Date date = new Date();
		model.setUpdateDate(date);

		int code = haiGuestbookMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_info(HttpServletRequest request,Integer guestbookId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbookWithBLOBs> rm = new ReturnObject<HaiGuestbookWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		c.andGuestbookIdEqualTo(guestbookId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGuestbookWithBLOBs> list = haiGuestbookMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGuestbookWithBLOBs model = list.get(0);

		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiGuestbookWithBLOBs> guestbook_find(HttpServletRequest request,Integer guestbookId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbookWithBLOBs> rm = new ReturnObject<HaiGuestbookWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		c.andGuestbookIdEqualTo(guestbookId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGuestbookWithBLOBs> list = haiGuestbookMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGuestbookWithBLOBs model = list.get(0);
		
		Map<String, Object> map = new HashMap<String, Object>();
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiGuestbook> guestbook_delete(HttpServletRequest request,Integer guestbookId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGuestbook> rm = new ReturnObject<HaiGuestbook>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGuestbookExample example = new HaiGuestbookExample();
		HaiGuestbookExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGuestbookIdEqualTo(guestbookId);

		long count = haiGuestbookMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiGuestbookMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiGuestbookWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "guestbook.xml",model,"hai_guestbook",optionMap);
		
		
		return bootStrapList;
	}













	
}











