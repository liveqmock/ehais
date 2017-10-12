package org.ehais.weixin.service.action.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.WpKeywordMapper;
import org.ehais.weixin.model.WpKeyword;
import org.ehais.weixin.model.WpKeywordExample;
import org.ehais.weixin.service.action.KeywordService;
import org.ehais.weixin.service.wx.impl.WeiXinCommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("keywordService")
public class KeywordServiceImpl  extends WeiXinCommonServiceImpl implements KeywordService{
	
	@Autowired
	private WpKeywordMapper wpKeywordMapper;
	
	public ReturnObject<WpKeyword> keyword_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<WpKeyword> keyword_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		if(store_id == null)store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		Integer start = (page - 1 ) * len;
		
		WpKeywordExample example = new WpKeywordExample();
		WpKeywordExample.Criteria c = example.createCriteria();
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		c.andTokenEqualTo(wp.getToken());
		example.setStart(start);
		example.setLen(len);
		List<WpKeyword> list = wpKeywordMapper.wp_keyword_list_by_example(example);
		Integer total = wpKeywordMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<WpKeyword> keyword_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		WpKeyword model = new WpKeyword();
		model.setId(0);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpKeyword> keyword_insert_submit(HttpServletRequest request,WpKeyword model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		rm.setCode(0);
		if(model.getKeyword() == null || model.getKeyword().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}

		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		model.setToken(wp.getToken());
		model.setAddon("");
		model.setAimId(0);
		model.setCtime(Long.valueOf(System.currentTimeMillis()).intValue());
		model.setKeywordLength(0);
		model.setKeywordType((byte) 0);
		model.setExtraText("article_mult");
		model.setExtraInt(0);
		model.setRequestCount(0);

		WpKeywordExample example = new WpKeywordExample();
		WpKeywordExample.Criteria c = example.createCriteria();
		c.andKeywordEqualTo(model.getKeyword());
		c.andTokenEqualTo(wp.getToken());
		int count = wpKeywordMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = wpKeywordMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<WpKeyword> keyword_update(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		WpKeyword model = wpKeywordMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<WpKeyword> keyword_update_submit(HttpServletRequest request,WpKeyword model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		WpKeywordExample example = new WpKeywordExample();
		WpKeywordExample.Criteria c = example.createCriteria();
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		c.andIdEqualTo(model.getId());
		c.andTokenEqualTo(wp.getToken());

		int count = wpKeywordMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = wpKeywordMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<WpKeyword> keyword_find(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		
		WpKeyword model = wpKeywordMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpKeyword> keyword_delete(HttpServletRequest request,Integer id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpKeyword> rm = new ReturnObject<WpKeyword>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		WpKeywordExample example = new WpKeywordExample();
		WpKeywordExample.Criteria c = example.createCriteria();
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		c.andTokenEqualTo(wp.getToken());
		c.andIdEqualTo(id);
		int code = wpKeywordMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request ,WpKeyword model) throws Exception{
		
//		特殊情况使用
//		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));
//		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "keyword.xml",model,"wp_keyword",optionMap);
		
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "keyword", "微信关键字", model.getKeyword(), "请输入微信关键字", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "token", "", model.getToken(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "addon", "", model.getAddon(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "aimId", "", model.getAimId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "cTime", "", model.getCtime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "keywordLength", "", model.getKeywordLength(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "keywordType", "", model.getKeywordType(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("textarea", "extraText", "", model.getExtraText(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "extraInt", "", model.getExtraInt(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "requestCount", "", model.getRequestCount(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "multIds", "", model.getMultIds(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
}











