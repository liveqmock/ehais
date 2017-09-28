package org.ehais.shop.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.EHaiRegion;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.shop.controller.api.include.UserIController;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/ws")
public class UserWSController extends UserIController {

	
	@ResponseBody
	@RequestMapping("/user_info")
	public String user_info(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_find(request, user_id);
			List<Integer> regionList = new ArrayList<Integer>();
//			StringUtils.join(regionList.iterator(), ",");
			EHaiUsers model = rm.getModel() ;
			if(model.getCity()!=null)regionList.add(model.getCity());
			if(model.getCountry()!=null)regionList.add(model.getCountry());
			if(model.getCounty()!=null)regionList.add(model.getCounty());
			if(model.getDistrict()!=null)regionList.add(model.getDistrict());
			if(model.getProvince()!=null)regionList.add(model.getProvince());
			if(model.getStreet()!=null)regionList.add(model.getStreet());
			List<EHaiRegion> region_list = eRegionService.region_lists(regionList);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("region_list", region_list);
			rm.setMap(map);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	

	@ResponseBody
	@RequestMapping("/user_info_edit")
	public String user_info_edit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiUsers user) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.users_info_edit(request,user, null,null);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
	@ResponseBody
	@RequestMapping("/fans_list")
	public String fans_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "nickname", required = true) String nickname) {
		try{
			ReturnObject<EHaiUsers> rm = eUsersService.fans_list(request, null, condition,nickname);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
}
