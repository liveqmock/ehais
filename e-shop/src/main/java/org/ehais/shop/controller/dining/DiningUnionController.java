package org.ehais.shop.controller.dining;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EUserTypeEnum;
import org.ehais.epublic.mapper.EHaiAdminUserMapper;
import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.mapper.WpPublicMapper;
import org.ehais.epublic.model.EHaiAdminUser;
import org.ehais.epublic.model.EHaiAdminUserExample;
import org.ehais.epublic.model.EHaiAdminUserWithBLOBs;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiStoreExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class DiningUnionController extends EhaisCommonController{
	private static Logger log = LoggerFactory.getLogger(DiningUnionController.class);
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private EWPPublicService eWPPublicService;
	@Autowired
	protected WpPublicMapper wpPublicMapper;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	
	
	//http://127.0.0.1/diningUnion!5674d100-033b4b301-1299581252-2e64baa931f09d6c22
	@RequestMapping("/diningUnion!{pid}")
	public String diningUnion(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "pid") String pid	,
			@RequestParam(value = "code", required = false) String code ) {	
		
		try{
			Map<String,Object> map = SignUtil.getPartnerId(pid,weixin_token);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			modelMap.addAttribute("pid", pid);
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/diningUnion!"+pid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					if(user.getSubscribe() == null || user.getSubscribe() != 1){
						//跳转关注页面
						
					}
					String newPid = SignUtil.setPartnerId(default_store_id,Integer.valueOf(map.get("partnerId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), weixin_token);
					String link = request.getScheme() + "://" + request.getServerName() + "/diningUnion!"+newPid;
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
					
					
					String link = request.getScheme() + "://" + request.getServerName() + "/diningUnion!"+pid;
					WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), weixin_appid, weixin_appsecret, null);
					signature.setTitle("尚价微信点餐应用");
					signature.setLink(link);
					signature.setDesc("帮助餐厅“互联网+”转型的移动O2O服务平台");
					signature.setImgUrl(defaultimg);
					List<String> jsApiList = new ArrayList<String>();
					jsApiList.add("onMenuShareTimeline");
					jsApiList.add("onMenuShareAppMessage");
					jsApiList.add("onMenuShareQQ");
					jsApiList.add("onMenuShareWeibo");
					jsApiList.add("onMenuShareQZone");
					signature.setJsApiList(jsApiList);
					modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
					
					
					if(user == null || user.getUserType() != EUserTypeEnum.dining){
						return "/dining/diningUnion";
					}else{
						HaiStoreStatistics storeStatistics = haiStoreStatisticsMapper.selectByPrimaryKey(Integer.valueOf(map.get("store_id").toString()));
						if(storeStatistics == null){
							storeStatistics = new HaiStoreStatistics();
							storeStatistics.setStoreId(Integer.valueOf(map.get("store_id").toString()));
							storeStatistics.setWeixinAmount(0);
							storeStatistics.setWeixinQuantity(0);
							storeStatistics.setCashAmount(0);
							storeStatistics.setCashQuantity(0);
						}
						modelMap.addAttribute("storeStatistics", storeStatistics);
						request.getSession().setAttribute(EConstants.SESSION_STORE_ID,user.getStoreId());
						return "/dining/diningManage";
					}
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,weixin_appid, "/diningUnion!"+pid);
				}else{
					System.out.println(pid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:"+website;
		
	}
	
	
	@ResponseBody
	@RequestMapping("/diningRegiterUnion!{pid}")
	public String diningRegiterUnion(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "pid") String pid	,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "confirmPassword", required = false) String confirmPassword,
			@RequestParam(value = "store_name", required = false) String store_name,
			@RequestParam(value = "contacts", required = false) String contacts,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "address", required = false) String address
			
			) {	
		ReturnObject<EHaiAdminUser> rm = new ReturnObject<EHaiAdminUser>();
		rm.setCode(0);
		Map<String,Object> map = SignUtil.getPartnerId(pid,weixin_token);
		try{
			
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(user_id == null || user_id == 0){rm.setMsg("user sess empty");return this.writeJson(rm);}
			if(user_id.longValue() != Long.valueOf(map.get("userId").toString()).longValue()){rm.setMsg("user sess wrong");return this.writeJson(rm);}
			
			EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
			if(user == null){rm.setMsg("user obj empty");return this.writeJson(rm);}
			//效验用户名
			EHaiUsersExample userExp = new EHaiUsersExample();
			userExp.createCriteria().andUserNameEqualTo(username).andUserIdNotEqualTo(user_id);
			Long cUser = eHaiUsersMapper.countByExample(userExp);
			if(cUser > 0){rm.setMsg("此用户名已存在");return this.writeJson(rm);}

			//效验用户名
			EHaiAdminUserExample adminExp = new EHaiAdminUserExample();
			adminExp.createCriteria().andUserNameEqualTo(username);
			long aUser = eHaiAdminUserMapper.countByExample(adminExp);
			if(aUser > 0){rm.setMsg("此用户名已存在");return this.writeJson(rm);}
			
			//效验商家名称
			EHaiStoreExample storeExp = new EHaiStoreExample();
			storeExp.createCriteria().andStoreNameEqualTo(store_name);
			long sUser = eHaiStoreMapper.countByExample(storeExp);
			if(sUser > 0){rm.setMsg("此商户名称已存在，如同名请联系管理员微信:haisoftware");return this.writeJson(rm);}
			
			Integer addTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
			EHaiStore store = new EHaiStore();
			store.setStoreName(store_name);
			store.setContacts(contacts);
			store.setMobile(mobile);
			store.setAddress(address);
			store.setTheme(EAdminClassifyEnum.dining);
			store.setOwnerName(contacts);
			store.setZipcode("");
			store.setTel(mobile);
			store.setAddTime(addTime);
			store.setPartnerId(Integer.valueOf(map.get("partnerId").toString()));
			store.setPublicId(default_public_id);
			store.setState(true);
			eHaiStoreMapper.insert(store);
			
			user.setStoreId(store.getStoreId());
			user.setUserType(EUserTypeEnum.dining);
			eHaiUsersMapper.updateByPrimaryKey(user);
			
			EHaiAdminUserWithBLOBs admin = new EHaiAdminUserWithBLOBs();
			admin.setUserName(username);
			admin.setPassword(EncryptUtils.md5(password));
			admin.setStoreId(store.getStoreId());
			admin.setEmail("");
			admin.setClassify(EAdminClassifyEnum.dining);
			admin.setAddTime(addTime);
			admin.setLastLogin(addTime);
			admin.setPartnerId(Integer.valueOf(map.get("partnerId").toString()));
			eHaiAdminUserMapper.insert(admin);
			
//			WpPublicWithBLOBs wp = new WpPublicWithBLOBs();
//			wp.setPublicName(store_name);
//			wp.setToken(weixin_token);
//			wp.setAppid(weixin_appid);
//			wp.setSecret(weixin_appsecret);
//			wp.setMchId(weixin_mch_id);
//			wp.setMchSecret(weixin_mch_secret);
//			wp.setStoreId(store.getStoreId());
//			wpPublicMapper.insert(wp);
			
			
			rm.setModel(admin);
			rm.setCode(1);
			rm.setMsg("注册成功");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return this.writeJson(rm);
	}
	
	
	
	@ResponseBody
	@RequestMapping("/diningOrder")
	public String diningOrder(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
//			@RequestParam(value = "sid", required = true) String sid,
			@ModelAttribute EConditionObject condition 
			){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
//		Integer storeId = SignUtil.getUriStoreId(sid);
//		request.getSession().setAttribute(EConstants.SESSION_STORE_ID,58);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		if(store_id.intValue() != storeId.intValue()){
//			rm.setMsg("store is wrong");return this.writeJson(rm);
//		}
		
//		EHaiStore store = eStoreService.getEStore(store_id);
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			HaiOrderInfoExample orderInfoExample = new HaiOrderInfoExample();
			orderInfoExample.createCriteria().andStoreIdEqualTo(store_id).andOrderStatusEqualTo(EOrderStatusEnum.success).andClassifyEqualTo("dining");
			orderInfoExample.setOrderByClause("pay_time desc");
			orderInfoExample.setLimitStart(condition.getStart());
			orderInfoExample.setLimitEnd(condition.getRows());
			List<HaiOrderInfoWithBLOBs> listOrder = haiOrderInfoMapper.selectByExampleWithBLOBs(orderInfoExample);
			for (HaiOrderInfoWithBLOBs haiOrderInfo : listOrder) {
				if(StringUtils.isNotEmpty(haiOrderInfo.getSid())){
					Map<String,Object> sMap = SignUtil.getDiningId(haiOrderInfo.getSid(), wp.getToken());
					haiOrderInfo.setZipcode(sMap.get("tableNo").toString());
				}
			}
			Long total = haiOrderInfoMapper.countByExample(orderInfoExample);
			rm.setRows(listOrder);
			rm.setTotal(total);
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
}
