package org.ehais.shop.controller.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.mapper.HaiForumMapper;
import org.ehais.shop.mapper.HaiForumUserMapper;
import org.ehais.shop.model.HaiForum;
import org.ehais.shop.model.HaiForumExample;
import org.ehais.shop.model.HaiForumUser;
import org.ehais.tools.ReturnObject;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class ForumWSController extends CommonController{

	@Autowired
	private HaiForumMapper haiForumMapper;
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	private HaiForumUserMapper haiForumUserMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	
	@ResponseBody
	@RequestMapping(value="/ws/writeArticleForum",method=RequestMethod.POST)
	public String writeArticleForum(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sid", required = true) String sid,
			@RequestParam(value = "content", required = true) String content
			){
		ReturnObject<HaiForum> rm = new ReturnObject<HaiForum>();
		rm.setCode(0);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if(user_id == null || user_id == 0){
			rm.setMsg("未登录");
			return this.writeJsonObject(rm);
		}
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			rm.setMsg("商码不正确");
			return this.writeJson(rm);
		}
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getSid(sid,wp.getToken());
			if(map == null){
				rm.setMsg("参数不正确");
				return this.writeJson(rm);
			}
			if(user_id.longValue() != Long.parseLong(map.get("userId").toString())){
				rm.setMsg("用户不正确");
				return this.writeJson(rm);
			}
			HaiForumExample exp = new HaiForumExample();
			HaiForumExample.Criteria c = exp.createCriteria();
			c.andUserIdEqualTo(Long.valueOf(map.get("userId").toString()))
			.andStoreIdEqualTo(Integer.valueOf(map.get("store_id").toString()))
			.andTableIdEqualTo(Long.valueOf(map.get("articleId").toString()))
			.andTableNameEqualTo("hai_article");
			
			Long count = haiForumMapper.countByExample(exp);
			if(count > 5){
				rm.setMsg("超过评论次数");
				return this.writeJson(rm);
			}
			
			c.andContentEqualTo(content.trim());
			count = haiForumMapper.countByExample(exp);
			if(count > 0){
				rm.setMsg("评论已提交，无需要重复提交");
				return this.writeJson(rm);
			}
			
			HaiForum forum = new HaiForum();
			forum.setStoreId(Integer.valueOf(map.get("store_id").toString()));
			forum.setContent(content.trim());
			forum.setCreateDate(new Date());
			forum.setParentForumId(0l);
			forum.setTableId(Long.valueOf(map.get("articleId").toString()));
			forum.setTableName("hai_article");
			forum.setUserId(Long.valueOf(map.get("userId").toString()));
			haiForumMapper.insert(forum);
			
			rm.setCode(1);
			rm.setMsg("留言提交成功");
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
			
		return null;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/ws/listArticleForum",method=RequestMethod.POST)
	public String listArticleForum(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sid", required = true) String sid
			){
		ReturnObject<HaiForumUser> rm = new ReturnObject<HaiForumUser>();
		rm.setCode(0);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if(user_id == null || user_id == 0){
			rm.setMsg("未登录");
			return this.writeJsonObject(rm);
		}
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			rm.setMsg("商码不正确");
			return this.writeJson(rm);
		}
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getSid(sid,wp.getToken());
			if(map == null){
				rm.setMsg("参数不正确");
				return this.writeJson(rm);
			}
			if(user_id.longValue() != Long.parseLong(map.get("userId").toString())){
				rm.setMsg("用户不正确");
				return this.writeJson(rm);
			}
			
			
			List<HaiForumUser> listForum = haiForumUserMapper.listForumUser(Integer.valueOf(map.get("store_id").toString()), 
					Long.valueOf(map.get("articleId").toString()), 
					"hai_article");
			
			rm.setCode(1);
			rm.setRows(listForum);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
			
		return null;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/ws/writeOrderForum",method=RequestMethod.POST)
	public String writeOrderForum(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "orderId", required = true) Long orderId,
			@RequestParam(value = "content", required = true) String content
			){
		
		ReturnObject<HaiForumUser> rm = new ReturnObject<HaiForumUser>();
		rm.setCode(0);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if(user_id == null || user_id == 0){
			rm.setMsg("未登录");
			return this.writeJsonObject(rm);
		}
		
		HaiForumExample exp = new HaiForumExample();
		HaiForumExample.Criteria c = exp.createCriteria();
		c.andUserIdEqualTo(user_id)
		.andTableIdEqualTo(orderId)
		.andTableNameEqualTo("hai_order_info");
		
		Long count = haiForumMapper.countByExample(exp);
		if(count > 5){
			rm.setMsg("超过评论次数");
			return this.writeJson(rm);
		}
		
		c.andContentEqualTo(content.trim());
		count = haiForumMapper.countByExample(exp);
		if(count > 0){
			rm.setMsg("评论已提交，无需要重复提交");
			return this.writeJson(rm);
		}
		
		HaiOrderInfoWithBLOBs order = haiOrderInfoMapper.selectByPrimaryKey(orderId);
		if(order == null){
			rm.setMsg("无此订单");
			return this.writeJson(rm);
		}
		
		HaiForum forum = new HaiForum();
//		forum.setStoreId(Integer.valueOf(map.get("store_id").toString()));
		forum.setContent(content.trim());
		forum.setCreateDate(new Date());
		forum.setParentForumId(0l);
		forum.setTableId(orderId);
		forum.setTableName("hai_order_info");
		forum.setUserId(user_id);
		haiForumMapper.insert(forum);
		
		order.setHasForum(true);
		haiOrderInfoMapper.updateByPrimaryKeySelective(order);
		
		rm.setCode(1);
		rm.setMsg("留言提交成功");
		return this.writeJson(rm);
		
	}
	
}
