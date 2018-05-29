package org.ehais.shop.controller.ehais;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.OrderStoreStatistics;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/")
public class EhaisWeiXinManagerController extends EhaisCommonController{
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;

	
	//http://6a0e4a53.ngrok.io/weixin_manager
	@RequestMapping("/weixin_manager")
	public String weixin_manager(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code) {	
		
		try{
			if(this.isWeiXin(request)){//微信端登录
				Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
				String openid = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
				System.out.println(user_id + "--"+ openid + "--"+ code);
				if(user_id == null || openid == null) {
					if(code == null || code.equals("")) {
						return this.redirect_wx_authorize(request, weixin_appid, "/weixin_manager");
					}else {
						EHaiUsers user = this.getUserByOpenIdInfo(request, code, default_store_id);
						System.out.println("user:"+user.getUserId());
						if(user == null) return "redirect:"+website;
						request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
						request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, user.getOpenid());
						return this.weixin_manager_value(modelMap, request, response, user_id , user);
					}
				} else {
					return this.weixin_manager_value(modelMap, request, response, user_id,null);
				}
			}else {
				return this.weixin_manager_value(modelMap, request, response, 1615L,null);//--测试使用
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:"+website;
	}
	
	
	private String weixin_manager_value(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response,Long user_id,EHaiUsers user) throws Exception {
		if(user == null) {
			user = eHaiUsersMapper.selectByPrimaryKey(user_id);
			System.out.println("usersss:"+user.getOpenid()+"--"+user.getUserType()+"=="+user.getStoreId());
			if(user == null)return "redirect:"+website;
		}
		if(user.getUserType() == null || user.getUserType() == 0 || user.getUserType().intValue() == 0) return "redirect:"+website;//如果非商家，即跳转商城
		if(user.getStoreId() == null || user.getStoreId() == 0 || user.getStoreId().intValue() == 0) return "redirect:"+website;//如果非商家，即跳转商城
		
		
		EHaiStore store = eHaiStoreMapper.selectByPrimaryKey(user.getStoreId());
		if(store == null) return "redirect:"+website;//如果非商家，即跳转商城
		
		//可提现金额
		Integer balance = store.getBalance() == null ? 0 : store.getBalance();
		
		//获取当天微信支付与现金支付的金额
		Date date = new Date();
		String today = DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
		System.out.println(today);
		Date today_date = DateUtil.formatDate(today, DateUtil.FORMATSTR_3);
		Date endDate = DateUtil.addDate(today_date, 1);
		Long start_time = today_date.getTime();
		Long end_time = endDate.getTime();
		System.out.println(start_time+"--"+end_time);
		List<OrderStoreStatistics> listOSS = haiOrderInfoMapper.order_store_statistics(user.getStoreId(),start_time,end_time);
		if(listOSS != null && listOSS.size() > 0) {
			OrderStoreStatistics oss = listOSS.get(0);
			modelMap.addAttribute("weixinAmount", oss.getWeixinAmount());
			modelMap.addAttribute("weixinQuantity", oss.getWeixinQuantity());
			modelMap.addAttribute("cashAmount", oss.getCashAmount());
			modelMap.addAttribute("cashQuantity", oss.getCashQuantity());
		}
		
		modelMap.addAttribute("balance", balance);
		String theme = store.getTheme() == null ? "admin" : store.getTheme();
		
		return "/"+theme+"/bms_"+theme+"_manage";
	}
	
	
}
