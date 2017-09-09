package org.ehais.shop.controller.dining;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.util.ResourceUtil;
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
public class DiningWebController extends EhaisCommonController{
	private static Logger log = LoggerFactory.getLogger(DiningWebController.class);
	public static String website = ResourceUtil.getProValue("website");
	public static String defaultimg = ResourceUtil.getProValue("defaultimg");
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	//http://127.0.0.1/diningStore!272bb580-04dd7b01-14f11b02-20ae4903-3f8bfaC104-481adf49
	@RequestMapping("/diningStore!{sid}")
	public String tpDiningView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "code", required = false) String code ) {	
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getDiningId(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/diningStore!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setDiningId(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(),map.get("tableNo").toString(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/diningStore!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					this.dining(modelMap, request, response,store_id, sid);
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/diningStore!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				this.dining(modelMap, request, response,store_id, sid);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return "/dining/diningStore";
		
	}
	
	/**
	 * 读取店铺菜谱信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 */
	private void dining(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			Integer store_id,
			String sid){
		
		//读取菜谱列表信息
		HaiCategoryExample cExp = new HaiCategoryExample();
		cExp.createCriteria().andStoreIdEqualTo(store_id);
		cExp.setOrderByClause("sort_order asc");
		List<HaiCategory> listCategory = haiCategoryMapper.selectByExample(cExp);
		modelMap.addAttribute("listCategory", listCategory);
		
		
		//读取菜品列表信息
		HaiGoodsExample gExp = new HaiGoodsExample();
		gExp.createCriteria().andStoreIdEqualTo(store_id).andIsDeleteEqualTo(false).andIsOnSaleEqualTo(true);
		gExp.setOrderByClause("sort_order asc");
		List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(gExp);
		modelMap.addAttribute("listGoods", listGoods);
		
		modelMap.addAttribute("defaultimg", defaultimg);
		
	}
	
	
}
