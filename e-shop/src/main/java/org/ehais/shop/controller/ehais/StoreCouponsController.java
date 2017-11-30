package org.ehais.shop.controller.ehais;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.common.EConstants;
import org.ehais.enums.EUserTypeEnum;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.shop.service.HaiCouponsService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class StoreCouponsController extends EhaisCommonController {

	private static Logger log = LoggerFactory.getLogger(StoreCouponsController.class);

	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	
	@Autowired
	private HaiCouponsService haiCouponsService;
	
	@RequestMapping("/wx_store_coupons!{cid}")
	public String wx_store_coupons(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid	,
			@RequestParam(value = "code", required = false) String code ) {	
		
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == 0 || store_id == null){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		try{
			
			Map<String,Object> map = SignUtil.getCid(cid,weixin_token);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			modelMap.addAttribute("cid", cid);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/wx_store_coupons!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					if(user.getSubscribe() == null || user.getSubscribe() != 1){
						//跳转关注页面
						
					}
					String newPid = SignUtil.setCid(default_store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), weixin_token);
					String link = request.getScheme() + "://" + request.getServerName() + "/wx_store_coupons!"+newPid;
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					return this.couponsData(modelMap, request, user_id, cid, map);
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,weixin_appid, "/wx_store_coupons!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return this.couponsData(modelMap, request, user_id, cid, map);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("coupons", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	
	
	private String couponsData(ModelMap modelMap,
			HttpServletRequest request,Long user_id,String cid,Map<String,Object> map) throws Exception{
		EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(Integer.valueOf(map.get("store_id").toString()));
		String link = request.getScheme() + "://" + request.getServerName() + "/wx_store_coupons!"+cid;
		
		this.shareWeiXin(modelMap, request, null, wp, Integer.valueOf(map.get("store_id").toString()), weixin_share_description , link, "", "");
		
		
		if(user == null || user.getUserType() != EUserTypeEnum.shop || user.getStoreId() == null || user.getStoreId().intValue() == 0){
			return "/ehais/store_coupons";
		}else{
			
			EHaiStore store = eStoreService.getEStore(Integer.valueOf(map.get("store_id").toString()));
			modelMap.addAttribute("store",store);
			
			request.getSession().setAttribute(EConstants.SESSION_STORE_ID,user.getStoreId());
			return "/ehais/store_coupons";
		}
	}
	
	
	
}
