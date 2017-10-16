package org.ehais.shop.controller.activity;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.enums.EActivityParticipationModuleEnum;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiActivityParticipationMapper;
import org.ehais.shop.model.HaiActivityParticipation;
import org.ehais.shop.model.HaiActivityParticipationExample;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class ActivityWebController extends EhaisCommonController{
	
	@Autowired
	private HaiActivityParticipationMapper haiActivityParticipationMapper;

	@RequestMapping(value="/haiActivityApply!{aid}")
	public String haiActivityApply(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid){
		
		
		
		
		
		return "/ehais/w_activity_apply";
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/haiActivityApplySubmit!{aid}")
	public String haiActivityApplySubmit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid){
		
		return null;
	}
	
	
	/**
	 * 扫码签到
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param aid
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/haiActivitySign!{aid}")
	public String haiActivitySign(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "aid") String aid,
			@RequestParam(value = "code", required = false) String code){
		
		Integer store_id = SignUtil.getUriStoreId(aid);
		if(store_id == 0 || store_id == null){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		try{
			EHaiStore store = eStoreService.getEStore(store_id);
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getAid(aid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			
			Integer activityId = Integer.valueOf(map.get("articleId").toString());
			
			if(this.isWeiXin(request)){//微信端登录
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/haiActivitySign!"+aid , "snsapi_userinfo");
				}else{
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					
					HaiActivityParticipationExample exp = new HaiActivityParticipationExample();
					exp.createCriteria()
					.andUserIdEqualTo(user.getUserId())
					.andActivityIdEqualTo(activityId)
					.andModuleEqualTo(EActivityParticipationModuleEnum.SIGN);
					
					long c = haiActivityParticipationMapper.countByExample(exp);
					if(c == 0){
						HaiActivityParticipation ap = new HaiActivityParticipation();
						ap.setActivityId(activityId);
						ap.setUserId(user.getUserId());
						ap.setModule(EActivityParticipationModuleEnum.SIGN);
						ap.setCreateTime(new Date());
						haiActivityParticipationMapper.insertSelective(ap);
					}
				}
				
			}	
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("MobileController", e);
		}
		
		
		return "/ehais/w_activity_sign";
		
	}
	
	
	
	
}
