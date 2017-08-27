package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiRegionMapper;
import org.ehais.epublic.model.EHaiRegion;
import org.ehais.epublic.model.EHaiRegionExample;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiUserAddressMapper;
import org.ehais.shop.model.HaiUserAddress;
import org.ehais.shop.model.HaiUserAddressExample;
import org.ehais.shop.service.UserAddressService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("useraddressService")
public class UserAddressServiceImpl  extends CommonServiceImpl implements UserAddressService{
	
	@Autowired
	private HaiUserAddressMapper haiUserAddressMapper;
	@Autowired
	private EHaiRegionMapper eHaiRegionMapper;
	
	
	public ReturnObject<HaiUserAddress> useraddress_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<HaiUserAddress> list = haiUserAddressMapper.hai_user_address_list_by_example(example);
		Integer total = haiUserAddressMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUserAddress model = new HaiUserAddress();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiUserAddress> useraddress_insert_submit(HttpServletRequest request,HaiUserAddress model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();

		if(model.getAddressName() == null || model.getAddressName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andAddressNameEqualTo(model.getAddressName());
		c.andStoreIdEqualTo(store_id);
		int count = haiUserAddressMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiUserAddressMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_update(HttpServletRequest request,Long addressId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUserAddress model = haiUserAddressMapper.selectByPrimaryKey(addressId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiUserAddress> useraddress_update_submit(HttpServletRequest request,HaiUserAddress model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAddressIdEqualTo(model.getAddressId());
		c.andStoreIdEqualTo(store_id);

		int count = haiUserAddressMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiUserAddressMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_find(HttpServletRequest request,Long addressId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiUserAddress model = haiUserAddressMapper.selectByPrimaryKey(addressId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_delete(HttpServletRequest request,Long addressId,Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		c.andAddressIdEqualTo(addressId);
		int code = haiUserAddressMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg(code==1?"删除成功":"删除失败");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiUserAddress model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	public ReturnObject<HaiUserAddress> useraddress_add_submit(HttpServletRequest request, HaiUserAddress model,Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		
		
		if(user_id == null || user_id == 0){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		if(model.getConsignee() == null || model.getConsignee().equals("")){
			rm.setMsg("必填项不能为空1001");
			return rm;
		}
		if(model.getMobile() == null || model.getMobile().equals("")){
			rm.setMsg("必填项不能为空1002");
			return rm;
		}
		if(model.getAddress() == null || model.getAddress().equals("")){
			rm.setMsg("必填项不能为空1004");
			return rm;
		}
		if(model.getProvince() == null || model.getProvince() == 0){
			rm.setMsg("必填项不能为空1005");
			return rm;
		}
		if(model.getCity() == null || model.getCity() == 0){
			rm.setMsg("必填项不能为空1006");
			return rm;
		}
		
		model.setUserId(user_id);

		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andConsigneeEqualTo(model.getConsignee());
		c.andMobileEqualTo(model.getMobile());
		c.andAddressEqualTo(model.getAddress());
		c.andProvinceEqualTo(model.getProvince());
		c.andCityEqualTo(model.getCity());
		if(model.getCounty()!=null && model.getCounty()!=0)c.andCityEqualTo(model.getCity());
		if(model.getDistrict()!=null && model.getDistrict()!=0)c.andDistrictEqualTo(model.getDistrict());
		if(model.getStreet()!=null && model.getStreet()!=0)c.andStreetEqualTo(model.getStreet());
		
		c.andUserIdEqualTo(user_id);
//		
		int count = haiUserAddressMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("当前收货人地址已存在");
			return rm;
		}

		if(model.getIsDefault().equals("1")){
			haiUserAddressMapper.clearUserDefault(user_id);
		}

		model.setAddressName("");
		Date date = new Date();
		model.setAddTime(date);
		model.setUpdateTime(date);
		
		int code = haiUserAddressMapper.insertSelective(model);
		rm.setModel(model);
		rm.setCode(code);
		rm.setMsg(code == 1 ?"新增成功":"新增失败");
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_edit_submit(HttpServletRequest request, HaiUserAddress model,Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		
		
		if(user_id == null || user_id == 0){
			rm.setMsg("非客户端使用");
			return rm;
		}
		

		if(model.getConsignee() == null || model.getConsignee().equals("")){
			rm.setMsg("必填项不能为空1001");
			return rm;
		}
		if(model.getMobile() == null || model.getMobile().equals("")){
			rm.setMsg("必填项不能为空1002");
			return rm;
		}
		if(model.getAddress() == null || model.getAddress().equals("")){
			rm.setMsg("必填项不能为空1004");
			return rm;
		}
		if(model.getProvince() == null || model.getProvince() == 0){
			rm.setMsg("必填项不能为空1005");
			return rm;
		}
		if(model.getCity() == null || model.getCity() == 0){
			rm.setMsg("必填项不能为空1006");
			return rm;
		}
		
		
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		c.andAddressIdEqualTo(model.getAddressId());
		
		int count = haiUserAddressMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("不存在当前的收货人信息1007");
			return rm;
		}
		
		if(model.getIsDefault().equals("1")){
			haiUserAddressMapper.clearUserDefault(user_id);
		}
		model.setAddressName("");
		model.setUpdateTime(new Date());
		int code = haiUserAddressMapper.updateByExampleSelective(model, example);
		
		rm.setModel(model);
		rm.setCode(code);
		rm.setMsg(code == 1 ? "更新成功":"更新失败");
		
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_info(HttpServletRequest request, Long addressId,Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		rm.setCode(0);
		if(user_id == null || user_id == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		HaiUserAddress model = haiUserAddressMapper.get_hai_user_address_info(user_id, addressId);
		rm.setModel(model);
		rm.setCode(1);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Integer> regionIds = new ArrayList<Integer>();
		if(model.getCountry()!=null && model.getCountry()!=0)regionIds.add(model.getCountry());
		if(model.getCity()!=null && model.getCity()!=0)regionIds.add(model.getCity());
		if(model.getCounty()!=null && model.getCounty()!=0)regionIds.add(model.getCounty());
		if(model.getDistrict()!=null && model.getDistrict()!=0)regionIds.add(model.getDistrict());
		if(model.getProvince()!=null && model.getProvince()!=0)regionIds.add(model.getProvince());
		if(model.getStreet()!=null && model.getStreet()!=0)regionIds.add(model.getStreet());
		
		if(regionIds.size() > 0){
			EHaiRegionExample regionExample = new EHaiRegionExample();
			regionExample.createCriteria().andRegionIdIn(regionIds);
			List<EHaiRegion> region_list = eHaiRegionMapper.selectByExample(regionExample);
			map.put("region", region_list);				
		}
		
		rm.setMap(map);
		
		return rm;
	}

	public ReturnObject<HaiUserAddress> useraddress_lists(HttpServletRequest request,Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		rm.setCode(0);
		
		if(user_id == null) user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if(user_id == null || user_id == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		example.setOrderByClause("update_time desc");
		List<HaiUserAddress> list = haiUserAddressMapper.hai_user_address_list_by_example(example);
		
		List<Integer> regionids = new ArrayList<Integer>();
		for (HaiUserAddress addr : list) {
			addr.setMobile(addr.getMobile().substring(0, 3)+"****"+addr.getMobile().substring(addr.getMobile().length()-4, addr.getMobile().length()));
			if(addr.getCountry()!=null && addr.getCountry()!=0)regionids.add(addr.getCountry());
			if(addr.getProvince()!=null && addr.getProvince()!=0)regionids.add(addr.getProvince());
			if(addr.getCity()!=null && addr.getCity()!=0)regionids.add(addr.getCity());
			if(addr.getCounty()!=null && addr.getCounty()!=0)regionids.add(addr.getCounty());
			if(addr.getDistrict()!=null && addr.getDistrict()!=0)regionids.add(addr.getDistrict());
			if(addr.getStreet()!=null && addr.getStreet()!=0)regionids.add(addr.getStreet());
		}
		
		if(regionids.size() > 0){
			EHaiRegionExample rexample = new EHaiRegionExample();
			rexample.createCriteria().andRegionIdIn(regionids);
			List<EHaiRegion> region_list = eHaiRegionMapper.selectByExample(rexample);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("region", region_list);
			rm.setMap(map);
		}
		
		
		
		
		rm.setCode(1);
		rm.setRows(list);
		
		return rm;
	}

	@Override
	public ReturnObject<HaiUserAddress> useraddress_set_default(HttpServletRequest request, Long address_id,
			Long user_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		
		
		if(user_id == null || user_id == 0){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		c.andAddressIdEqualTo(address_id);
		
		int count = haiUserAddressMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("不存在当前的收货人信息1007");
			return rm;
		}
		
		haiUserAddressMapper.clearUserDefault(user_id);
		
		int code = haiUserAddressMapper.setUserDefault(user_id, address_id);
		
		rm.setCode(code);
		rm.setMsg(code == 1 ? "更新成功":"更新失败");
		
		
		
		return rm;
		
		
	}

	@Override
	public ReturnObject<HaiUserAddress> useraddress_delete_sumbit(HttpServletRequest request, Long address_id,
			Long user_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiUserAddress> rm = new ReturnObject<HaiUserAddress>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		
		
		if(user_id == null || user_id == 0){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		HaiUserAddressExample example = new HaiUserAddressExample();
		HaiUserAddressExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
		c.andAddressIdEqualTo(address_id);
		
		int count = haiUserAddressMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("不存在当前的收货人信息1007");
			return rm;
		}
		
		
		int code = haiUserAddressMapper.deleteByExample(example);
		
		rm.setCode(code);
		rm.setMsg(code == 1 ? "删除成功":"删除失败");
		
		
		
		return rm;
		
	}
	
}











