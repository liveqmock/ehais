package org.ehais.shop.controller.partner;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@EPermissionController(intro="会员管理功能",value="haiUsersController")
@Controller
@RequestMapping("/ehais")
public class  HaiUsersAdminController extends CommonController {

	private static Logger log = LoggerFactory.getLogger(HaiUsersAdminController.class);

	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiStoreMapper haiStoreMapper;
	
	@EPermissionMethod(intro="打开会员管理页面",value="haiUsersView",type=PermissionProtocol.URL)
	@RequestMapping("/manage/haiUsersView")
	public String haiUsersView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
			return "/"+this.getPartnerTheme(request)+"/users/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	

	@ResponseBody
	@EPermissionMethod(intro="返回会员管理数据",value="haiUsersListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/manage/haiUsersListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiUsersListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "userName", required = false) String userName) {
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		try{
			EHaiStoreExample example = new EHaiStoreExample();
			EHaiStoreExample.Criteria c = example.createCriteria();
			Integer partner_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_PARTNER_ID);
			c.andPartnerIdEqualTo(partner_id);
			List<EHaiStore> list = haiStoreMapper.selectByExample(example);
			List<Integer> storeIdList = new ArrayList<Integer>();
			for (EHaiStore store : list) {
				storeIdList.add(store.getStoreId());
			}
			
			EHaiUsersExample userExample = new EHaiUsersExample();
			EHaiUsersExample.Criteria uc = userExample.createCriteria();
			uc.andStoreIdIn(storeIdList);
			if(StringUtils.isNotEmpty(userName))uc.andNicknameLike("%"+userName+"%");
			List<EHaiUsers> listUser = eHaiUsersMapper.selectByExample(userExample);
			List<Long> parentIdList = new ArrayList<Long>();
			for (EHaiUsers eHaiUsers : listUser) {
				if(eHaiUsers.getParentId()!=null && eHaiUsers.getParentId() > 0)parentIdList.add(eHaiUsers.getParentId());
			}
			
			Long count = eHaiUsersMapper.countByExample(userExample);
			rm.setRows(listUser);
			rm.setTotal(count);
			
			userExample.clear();
			userExample.createCriteria().andUserIdIn(parentIdList);
			List<EHaiUsers> listUserParent = eHaiUsersMapper.selectByExample(userExample);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("listUserParent", listUserParent);
			
			rm.setMap(map);
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
			return this.errorJSON(e);
		}
		
		return this.writeJson(rm);
	}
	
	
	
}


