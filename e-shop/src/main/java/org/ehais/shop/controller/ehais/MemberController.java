package org.ehais.shop.controller.ehais;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.HaiOrderInfo;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class MemberController extends EhaisCommonController{
	
//	@Autowired
//	private OrderInfoService orderInfoService;
	
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	

	//获取用户信息的公共方法
	private String w_member_common(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,Integer store_id,String code) throws Exception{
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		
		if(this.isWeiXin(request)){//微信端登录
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
				return this.redirect_wx_authorize(request , wp.getAppid() , "/w_member");
			}else if(StringUtils.isNotEmpty(code)){
				EHaiUsers user = this.saveUserByOpenIdInfo(request, code, store_id);
				if(user.getSubscribe() == null || user.getSubscribe() != 1){
					//跳转关注页面
					return "/ehais/attention";
				}
			}
			
			return "/ehais/member/member";
		}else{
			//网页登录
		}
		
		return "/ehais/member/member";
	}
	
	
	//http://127.0.0.1/w_member
	//http://ac4a53dd.ngrok.io/w_member
	@RequestMapping("/w_member")
	public String w_member(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code
			){
		try{
			return this.w_member_common(modelMap, request, response, default_store_id, code);			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/ehais/member/member";
	}
	
	
	@RequestMapping("/w_member!{store_id}")
	public String w_member(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "store_id") Integer store_id,
			@RequestParam(value = "code", required = false) String code
			){
		try{
			return this.w_member_common(modelMap, request, response, store_id, code);			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/ehais/member/member";
	}
	
	@RequestMapping("/w_member_order")
	public String member_order(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/member/member_order";
	}
	
	
	@RequestMapping("/w_member_fans")
	public String member_fans(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/member/member_fans";
	}
	
	
	@RequestMapping("/w_favorites")
	public String favorites(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		return "/ehais/member/favorites";
	}
	
	
	@RequestMapping("/w_address")
	public String address(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/w_address_list";
	}
	
	@RequestMapping("/w_comment_wait")
	public String comment_wait(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response ){
		
		
		return "/ehais/member/comment_wait";
	}
	
	@RequestMapping("/w_comment_write!{orderId}")
	public String comment_write(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable(value = "orderId") Long orderId){
		
		modelMap.addAttribute("orderId", orderId);
		
		Long user_id = (Long) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try {
			HaiOrderInfo model = haiOrderInfoMapper.get_hai_order_info_info(user_id, orderId);
			modelMap.addAttribute("model", model);
			
			HaiOrderGoodsExample example = new HaiOrderGoodsExample();
			example.createCriteria().andOrderIdEqualTo(orderId);
			List<HaiOrderGoods> orderGoods = haiOrderGoodsMapper.selectByExampleWithBLOBs(example);
			modelMap.addAttribute("orderGoods", orderGoods);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/ehais/member/comment_write";
	}
	
	
}
