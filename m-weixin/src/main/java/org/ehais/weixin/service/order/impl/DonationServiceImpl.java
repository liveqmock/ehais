package org.ehais.weixin.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.HaiOrderPayExtendsMapper;
import org.ehais.epublic.model.HaiOrderPayRecordAndUsers;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.HaiDonationMapper;
import org.ehais.weixin.model.HaiDonation;
import org.ehais.weixin.model.HaiDonationExample;
import org.ehais.weixin.service.order.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("donationService")
public class DonationServiceImpl  extends CommonServiceImpl implements DonationService{
	
	@Autowired
	private HaiDonationMapper haiDonationMapper;
	@Autowired
	private HaiOrderPayExtendsMapper haiOrderPayExtendsMapper;
	
	public ReturnObject<HaiDonation> donation_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiDonation> donation_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiDonationExample example = new HaiDonationExample();
		HaiDonationExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		example.setOrderByClause("sort asc");
		List<HaiDonation> list = haiDonationMapper.hai_donation_list_by_example(example);
		Integer total = haiDonationMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiDonation> donation_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiDonation model = new HaiDonation();
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiDonation> donation_insert_submit(HttpServletRequest request,HaiDonation model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();

		if(model.getDonationName() == null || model.getDonationName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiDonationExample example = new HaiDonationExample();
		HaiDonationExample.Criteria c = example.createCriteria();
		c.andDonationNameEqualTo(model.getDonationName());
		c.andStoreIdEqualTo(store_id);
		int count = haiDonationMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}

		Date date = new Date();
		model.setCreateDate(date);
		model.setUpdateDate(date);
		model.setTotalAmount(0);
		model.setTotalQuantity(0);

		int code = haiDonationMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiDonation> donation_update(HttpServletRequest request,Integer donationId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiDonation model = haiDonationMapper.selectByPrimaryKey(donationId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiDonation> donation_update_submit(HttpServletRequest request,HaiDonation model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiDonationExample example = new HaiDonationExample();
		HaiDonationExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andDonationIdEqualTo(model.getDonationId());
		c.andStoreIdEqualTo(store_id);

		int count = haiDonationMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiDonationMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiDonation> donation_find(HttpServletRequest request,Integer donationId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiDonation model = haiDonationMapper.selectByPrimaryKey(donationId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiDonation> donation_delete(HttpServletRequest request,Integer donationId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiDonationExample example = new HaiDonationExample();
		HaiDonationExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andDonationIdEqualTo(donationId);
		int code = haiDonationMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiDonation model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "donation.xml",model,"hai_donation",optionMap);
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<HaiDonation> donation_detail(HttpServletRequest request ,Integer store_id, Integer donationId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiDonation> rm = new ReturnObject<HaiDonation>();
		if(store_id == null || store_id == 0) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiDonation model = haiDonationMapper.selectByPrimaryKey(donationId);
		
		List<HaiOrderPayRecordAndUsers> list = haiOrderPayExtendsMapper.hai_order_pay_extends_guestbook(store_id,"donation",donationId, 0, 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", list);
		
		rm.setMap(map);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
}











