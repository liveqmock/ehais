package org.ehais.shop.controller.ehais;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.enums.EWithdrawDepositStatusEnum;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiWithdrawDepositMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.HaiWithdrawDepositExample;
import org.ehais.epublic.model.OrderStoreStatistics;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.util.DateUtil;
import org.ehais.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class EhaisWeiXinManagerController extends EhaisCommonController{
	
	protected String prefix_order_transfers = ResourceUtil.getProValue("prefix.order.transfers");
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private WeiXinPayService weiXinPayService;
	@Autowired
	private HaiWithdrawDepositMapper haiWithdrawDepositMapper;

	
	//http://eg.ehais.com/weixin_manager
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
						
						if(user == null) {
							System.out.println("无用户记录");
							return "redirect:"+website;
						}
						user_id = user.getUserId();
						System.out.println("user:"+user.getUserId());
						request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
						request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, user.getOpenid());
						return this.weixin_manager_value(modelMap, request, response, user_id , user);
					}
				} else {
					return this.weixin_manager_value(modelMap, request, response, user_id,null);
				}
			}
//			else {
//				request.getSession().setAttribute(EConstants.SESSION_USER_ID, 1615L);
//				request.getSession().setAttribute(EConstants.SESSION_OPEN_ID, "oiGBot1sqVBQ-jQaRixS3k93GrHQ");
//				return this.weixin_manager_value(modelMap, request, response, 1615L,null);//--测试使用
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:"+website;
	}
	
	
	private String weixin_manager_value(ModelMap modelMap, HttpServletRequest request,HttpServletResponse response,Long user_id,EHaiUsers user) throws Exception {
		if(user == null) {
			user = eHaiUsersMapper.selectByPrimaryKey(user_id);
			if(user == null)return "redirect:"+website;
		}
		if(user.getUserType() == null || user.getUserType() == 0 || user.getUserType().intValue() == 0) return "redirect:"+website;//如果非商家，即跳转商城
		if(user.getStoreId() == null || user.getStoreId() == 0 || user.getStoreId().intValue() == 0) return "redirect:"+website;//如果非商家，即跳转商城
		
		
		EHaiStore store = eHaiStoreMapper.selectByPrimaryKey(user.getStoreId());
		if(store == null) return "redirect:"+website;//如果非商家，即跳转商城
		
		modelMap.addAttribute("store", store);
		
		//可提现金额
		Integer balance = store.getBalance() == null ? 0 : store.getBalance();
		
		//获取当天微信支付与现金支付的金额
		Date date = new Date();
		String today = DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
//		System.out.println(today);
		Date today_date = DateUtil.formatDate(today, DateUtil.FORMATSTR_3);
		Date endDate = DateUtil.addDate(today_date, 1);
		Long start_time = today_date.getTime();
		Long end_time = endDate.getTime();
		List<OrderStoreStatistics> listOSS = haiOrderInfoMapper.order_store_statistics(user.getStoreId(),start_time,end_time);
		if(listOSS != null && listOSS.size() > 0) {
			OrderStoreStatistics oss = listOSS.get(0);
			modelMap.addAttribute("weixinAmount", oss.getWeixinAmount());
			modelMap.addAttribute("weixinQuantity", oss.getWeixinQuantity());
			modelMap.addAttribute("cashAmount", oss.getCashAmount());
			modelMap.addAttribute("cashQuantity", oss.getCashQuantity());
		}
		
		
		String theme = store.getTheme() == null ? "admin" : store.getTheme();
		
		modelMap.addAttribute("balance", balance);
		modelMap.addAttribute("classify", theme);
		modelMap.addAttribute("balance", balance);
		
		
		HaiWithdrawDepositExample wde = new HaiWithdrawDepositExample();
		wde.createCriteria().andUserIdEqualTo(user_id).andStatusEqualTo(EWithdrawDepositStatusEnum.INIT);
		wde.or().andUserIdEqualTo(user_id).andStatusEqualTo(EWithdrawDepositStatusEnum.CONTINUED);
		Long countic = haiWithdrawDepositMapper.countByExample(wde);
		if(countic > 0) {
			modelMap.addAttribute("countic", 0);
		}else {
			modelMap.addAttribute("countic", 1);
		}
		
		
		return "/"+theme+"/bms_"+theme+"_manage";
	}
	
	
	@ResponseBody
	@RequestMapping("/withdraw_deposit!{classify}")
	public String withdraw_deposit(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "money", required = false) String money,
			@PathVariable(value = "classify") String classify) {
		try {
			return this.writeJson(weiXinPayService.transfers(request, money, classify, prefix_order_transfers,weixin_cert_p12));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
