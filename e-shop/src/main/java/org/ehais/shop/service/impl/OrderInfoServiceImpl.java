package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;
import org.ehais.shop.model.HaiOrderInfo;
import org.ehais.shop.model.HaiOrderInfoExample;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("orderinfoService")
public class OrderInfoServiceImpl  extends CommonServiceImpl implements OrderInfoService{
	
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		example.setStart(start);
//		example.setLen(len);
		List<HaiOrderInfo> list = haiOrderInfoMapper.hai_order_info_list_by_example(example);
		Integer total = haiOrderInfoMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();	
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiOrderInfo model = new HaiOrderInfo();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiOrderInfo> orderinfo_insert_submit(HttpServletRequest request,HaiOrderInfo model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();

		if(model.getOrderSn() == null || model.getOrderSn().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andOrderSnEqualTo(model.getOrderSn());
		c.andStoreIdEqualTo(store_id);
		int count = haiOrderInfoMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiOrderInfoMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_update(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiOrderInfo model = haiOrderInfoMapper.selectByPrimaryKey(orderId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiOrderInfo> orderinfo_update_submit(HttpServletRequest request,HaiOrderInfo model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(model.getOrderId());
		c.andStoreIdEqualTo(store_id);

		int count = haiOrderInfoMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiOrderInfoMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_find(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		HaiOrderInfo model = haiOrderInfoMapper.selectByPrimaryKey(orderId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiOrderInfo> orderinfo_delete(HttpServletRequest request,Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andOrderIdEqualTo(orderId);
		int code = haiOrderInfoMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiOrderInfo model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<HaiOrderInfo> orderinfo_list(HttpServletRequest request, Long user_id,Integer page,Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		HaiOrderInfoExample.Criteria c = example.createCriteria();
		c.andUserIdEqualTo(user_id);
//		example.setStart(start);
//		example.setLen(len);
		List<HaiOrderInfo> list = haiOrderInfoMapper.hai_order_info_list_by_example(example);
		Integer total = haiOrderInfoMapper.countByExample(example);
		
		List<Long> orderIds = new ArrayList<Long>();
		for (HaiOrderInfo haiOrderInfo : list) {
			orderIds.add(haiOrderInfo.getOrderId());
		}
		
		HaiOrderGoodsExample exampleGoods = new HaiOrderGoodsExample();
		HaiOrderGoodsExample.Criteria cg = exampleGoods.createCriteria();
		cg.andOrderIdIn(orderIds);
		List<HaiOrderGoods> orderGoodsList = haiOrderGoodsMapper.hai_order_goods_list_by_example(exampleGoods);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("order_goods_list", orderGoodsList);
		rm.setMap(map);
		
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	@Override
	public ReturnObject<HaiOrderInfo> orderinfo_info(HttpServletRequest request, Long user_id, Long orderId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderInfo> rm = new ReturnObject<HaiOrderInfo>();
		
		HaiOrderInfo model = haiOrderInfoMapper.get_hai_order_info_info(user_id, orderId);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
}











