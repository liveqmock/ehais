package org.ehais.epublic.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.enums.OrderStatusEnum;
import org.ehais.epublic.mapper.HaiOrderPayExtendsMapper;
import org.ehais.epublic.mapper.HaiOrderPayRecordMapper;
import org.ehais.epublic.model.HaiOrderPayRecord;
import org.ehais.epublic.model.HaiOrderPayRecordAndUsers;
import org.ehais.epublic.model.HaiOrderPayRecordExample;
import org.ehais.epublic.service.OrderPayRecordService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )




@Service("orderpayrecordService")
public class OrderPayRecordServiceImpl  extends CommonServiceImpl implements OrderPayRecordService{
	
	@Autowired
	private HaiOrderPayExtendsMapper haiOrderPayExtendsMapper;
	
	@Autowired
	private HaiOrderPayRecordMapper haiOrderPayRecordMapper;
	
	public ReturnObject<HaiOrderPayRecord> orderpayrecord_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiOrderPayRecord> rm = new ReturnObject<HaiOrderPayRecord>();
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiOrderPayRecordAndUsers> orderpayrecord_list_json(HttpServletRequest request,Integer store_id,
			EConditionObject condition,Integer tableId,String tableName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderPayRecordAndUsers> rm = new ReturnObject<HaiOrderPayRecordAndUsers>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		condition.setStore_id(store_id);
		Integer start = ((condition.getPage() != null)? ((condition.getPage() - 1 ) * condition.getLen() ) : 0 );
		
		HaiOrderPayRecordExample example = new HaiOrderPayRecordExample();
		HaiOrderPayRecordExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(condition.getLen());
		List<HaiOrderPayRecordAndUsers> list = haiOrderPayRecordMapper.hai_order_pay_record_list_by_condition(condition,tableId,tableName,1,start,condition.getLen());
		Integer total = haiOrderPayRecordMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	
	@Override
	public ReturnObject<HaiOrderPayRecordAndUsers> orderPayUsers(HttpServletRequest request, Integer wxid,String tableName,Integer tableId, Integer page,
			Integer size) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiOrderPayRecordAndUsers> rm = new ReturnObject<HaiOrderPayRecordAndUsers>();
		Integer start = (page - 1) * size;
		List<HaiOrderPayRecordAndUsers> list = haiOrderPayExtendsMapper.hai_order_pay_extends_guestbook(wxid,tableName,tableId, start, size);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	@Override
	public <T> boolean successOrderPay(String OrderSn,ReturnObject<T> rm)
			throws Exception {
		// TODO Auto-generated method stub
		
		HaiOrderPayRecordExample oprExample = new HaiOrderPayRecordExample();
		HaiOrderPayRecordExample.Criteria criteria = oprExample.createCriteria();
		criteria.andOrderSnEqualTo(OrderSn);
		List<HaiOrderPayRecord> oprList = haiOrderPayRecordMapper.selectByExample(oprExample);
		if(oprList == null || oprList.size() == 0){
			rm.setMsg("不存在此订单信息");return false;
		}
		HaiOrderPayRecord opr = oprList.get(0);
		if(opr.getOrderStatus() != OrderStatusEnum.init){
			rm.setMsg("订单信息已处理");return false;
		}
		
		opr.setOrderStatus(OrderStatusEnum.success);
		criteria.andOrderSnEqualTo(OrderSn).andOrderStatusEqualTo(0);
		haiOrderPayRecordMapper.updateByExample(opr, oprExample);
		
		return true;
	}
	
	
}











