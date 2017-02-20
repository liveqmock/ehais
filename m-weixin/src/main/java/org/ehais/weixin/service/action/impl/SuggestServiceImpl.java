package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.weixin.mapper.HaiSuggestMapper;
import org.ehais.weixin.model.HaiSuggest;
import org.ehais.weixin.model.HaiSuggestExample;
import org.ehais.weixin.service.action.SuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("suggestService")
public class SuggestServiceImpl extends CommonServiceImpl implements SuggestService {

	@Autowired
	private HaiSuggestMapper haiSuggestMapper;
	
	

	public ReturnObject<HaiSuggest> suggest_list(Integer store_id) throws Exception{
		
		ReturnObject<HaiSuggest> rm = new ReturnObject<HaiSuggest>();
		
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiSuggest> suggest_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSuggest> rm = new ReturnObject<HaiSuggest>();
		Integer start = (page - 1 ) * len;
		
		HaiSuggestExample example = new HaiSuggestExample();
		HaiSuggestExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		List<HaiSuggest> list = haiSuggestMapper.hai_suggest_list_by_example(example);
		Integer total = haiSuggestMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		return rm;
	}

	
	
	public ReturnObject<HaiSuggest> suggest_insert(HaiSuggest suggest)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSuggest> rm = new ReturnObject<HaiSuggest>();
		
		Date date = new Date();
		suggest.setCreateDate(date);
		haiSuggestMapper.insert(suggest);
		
		rm.setCode(1);
		rm.setMsg("提交成功，感谢您的建议");
		
		return rm;
	}

	
	public ReturnObject<HaiSuggest> suggest_find(Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiSuggest> rm = new ReturnObject<HaiSuggest>();
		
		HaiSuggest model = haiSuggestMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	
	
	private List<BootStrapModel> formatBootStrapList(HaiSuggest model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		

		bootStrapList.add(new BootStrapModel("label", "realname", "反馈者：", model.getRealname(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "mobile", "电话：", model.getMobile(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "openid", "微信ID：", model.getOpenid(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "content", "内容：", model.getContent(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("label", "createDate", "时间：", DateUtil.formatDate(model.getCreateDate(), DateUtil.FORMATSTR_2), "请输入", "", "", null, 0));

		return bootStrapList;
	}
	
	
}
