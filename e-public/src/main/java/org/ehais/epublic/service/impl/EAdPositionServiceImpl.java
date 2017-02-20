package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.epublic.mapper.EHaiAdPositionMapper;
import org.ehais.epublic.model.EHaiAdPosition;
import org.ehais.epublic.model.EHaiAdPositionExample;
import org.ehais.epublic.service.EAdPositionService;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("eAdPositionService")
public class EAdPositionServiceImpl  extends CommonServiceImpl implements EAdPositionService{
	
	@Autowired
	private EHaiAdPositionMapper eHaiAdPositionMapper;
	
	public ReturnObject<EHaiAdPosition> adposition_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiAdPosition> adposition_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		Integer start = (page - 1 ) * len;
		
		EHaiAdPositionExample example = new EHaiAdPositionExample();
		EHaiAdPositionExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<EHaiAdPosition> list = eHaiAdPositionMapper.hai_ad_position_list_by_example(example);
		Integer total = eHaiAdPositionMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiAdPosition> adposition_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();	
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiAdPosition model = new EHaiAdPosition();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<EHaiAdPosition> adposition_insert_submit(HttpServletRequest request,EHaiAdPosition model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();

		if(model.getPositionName() == null || model.getPositionName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		EHaiAdPositionExample example = new EHaiAdPositionExample();
		EHaiAdPositionExample.Criteria c = example.createCriteria();
		c.andPositionNameEqualTo(model.getPositionName());
		c.andStoreIdEqualTo(store_id);
		int count = eHaiAdPositionMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = eHaiAdPositionMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiAdPosition> adposition_update(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiAdPosition model = eHaiAdPositionMapper.selectByPrimaryKey(Short.valueOf(positionId+""));
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiAdPosition> adposition_update_submit(HttpServletRequest request,EHaiAdPosition model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiAdPositionExample example = new EHaiAdPositionExample();
		EHaiAdPositionExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPositionIdEqualTo(model.getPositionId());
		c.andStoreIdEqualTo(store_id);

		int count = eHaiAdPositionMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = eHaiAdPositionMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiAdPosition> adposition_find(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		EHaiAdPosition model = eHaiAdPositionMapper.selectByPrimaryKey(Short.valueOf(positionId+""));
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiAdPosition> adposition_delete(HttpServletRequest request,Integer positionId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAdPosition> rm = new ReturnObject<EHaiAdPosition>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiAdPositionExample example = new EHaiAdPositionExample();
		EHaiAdPositionExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andPositionIdEqualTo(Short.valueOf(positionId+""));
		int code = eHaiAdPositionMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(EHaiAdPosition model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "positionId", "", model.getPositionId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "positionCode", "位置编码", model.getPositionCode(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "positionName", "位置名称", model.getPositionName(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "adWidth", "宽度", model.getAdWidth(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "adHeight", "高度", model.getAdHeight(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "positionDesc", "描述", model.getPositionDesc(), "请输入", "", "", null, 0));

		return bootStrapList;
	}
	
}











