package org.ehais.shop.controller.ehais;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.ehais.epublic.mapper.HaiPartnerMapper;
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
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderGoodsExample;
import org.ehais.shop.service.HaiStoreService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.EncryptUtils;
import org.ehais.util.SignUtil;
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

@Controller
@RequestMapping("/")
public class EhaisUnionController extends EhaisCommonController{
	private static Logger log = LoggerFactory.getLogger(EhaisUnionController.class);
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EHaiAdminUserMapper eHaiAdminUserMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private EWPPublicService eWPPublicService;
	@Autowired
	protected WpPublicMapper wpPublicMapper;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	@Autowired
	private HaiPartnerMapper haiPartnerMapper;
	@Autowired
	private HaiStoreService haiStoreService;
	
	//http://127.0.0.1/ehaisUnion!7623c10-01151d01-10865702-2816ee03-32c12e32b9acd
	//http://mg.ehais.com/ehaisUnion!7623c10-01151d01-10865702-2816ee03-32c12e32b9acd
	//http://0f778f25.ngrok.io/ehaisUnion!7623c10-01151d01-10865702-2816ee03-32c12e32b9acd
	@RequestMapping("/ehaisUnion!{cid}")
	public String ehaisUnion(ModelMap modelMap,
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
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ehaisUnion!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					if(user.getSubscribe() == null || user.getSubscribe() != 1){
						//跳转关注页面
						
					}
					String newPid = SignUtil.setCid(default_store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(), weixin_token);
					String link = request.getScheme() + "://" + request.getServerName() + "/ehaisUnion!"+newPid;
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					return this.ehaisUnionData(modelMap, request, user_id, cid, map);
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
				    return this.redirect_wx_authorize(request,weixin_appid, "/ehaisUnion!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
//				return this.ehaisUnionData(modelMap, request, user_id, cid, map);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:"+website;
	}
	
	private String ehaisUnionData(ModelMap modelMap,
			HttpServletRequest request,Long user_id,String cid,Map<String,Object> map) throws Exception{
		EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(Integer.valueOf(map.get("store_id").toString()));
		String link = request.getScheme() + "://" + request.getServerName() + "/ehaisUnion!"+cid;
		
		this.shareWeiXin(modelMap, request, null, wp, Integer.valueOf(map.get("store_id").toString()), "易微销事业加盟", link, "", "");
		
		
		if(user == null || user.getUserType() != EUserTypeEnum.shop || user.getStoreId() == null || user.getStoreId().intValue() == 0){
			return "/ehais/ehaisUnion";
		}else{
			
			EHaiStore store = eStoreService.getEStore(Integer.valueOf(map.get("store_id").toString()));
			modelMap.addAttribute("store",store);
			
//			HaiStoreStatistics storeStatistics = haiStoreStatisticsMapper.selectByPrimaryKey(Integer.valueOf(map.get("store_id").toString()));
//			if(storeStatistics == null){
//				storeStatistics = new HaiStoreStatistics();
//				storeStatistics.setStoreId(Integer.valueOf(map.get("store_id").toString()));
//				storeStatistics.setWeixinAmount(0);
//				storeStatistics.setWeixinQuantity(0);
//				storeStatistics.setCashAmount(0);
//				storeStatistics.setCashQuantity(0);
//			}
//			modelMap.addAttribute("storeStatistics", storeStatistics);
			request.getSession().setAttribute(EConstants.SESSION_STORE_ID,user.getStoreId());
			return "/ehais/ehaisManage";
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/ehaisRegiterUnion!{cid}")
	public String ehaisRegiterUnion(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid	,
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
		Map<String,Object> map = SignUtil.getCid(cid,weixin_token);
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
			
			//代理编号
//			Integer partnerId = Integer.valueOf(map.get("partnerId").toString());
//			HaiPartner partner = haiPartnerMapper.selectByPrimaryKey(partnerId);
//			if(partner == null){
//				rm.setMsg("代理帐号不存在");return this.writeJson(rm);
//			}
			
			Integer addTime = Long.valueOf(System.currentTimeMillis() / 1000).intValue();
			EHaiStore store = new EHaiStore();
			store.setStoreName(store_name);
			store.setContacts(contacts);
			store.setMobile(mobile);
			store.setAddress(address);
			store.setTheme(EAdminClassifyEnum.shop);
			store.setOwnerName(contacts);
			store.setZipcode("");
			store.setTel(mobile);
			store.setAddTime(addTime);
			store.setPublicId(default_public_id);
			store.setState(true);
			if(map.get("partnerId")!=null)store.setPartnerId(Integer.valueOf(map.get("partnerId").toString()));
//			store.setPayModule(partner.getPayModule());//继承代理的默认支付模式
			eHaiStoreMapper.insert(store);
			
			user.setStoreId(store.getStoreId());
			user.setUserType(EUserTypeEnum.shop);
			eHaiUsersMapper.updateByPrimaryKey(user);
			
			EHaiAdminUserWithBLOBs admin = new EHaiAdminUserWithBLOBs();
			admin.setUserName(username);
			admin.setPassword(EncryptUtils.md5(password));
			admin.setStoreId(store.getStoreId());
			admin.setEmail("");
			admin.setClassify(EAdminClassifyEnum.shop);
			admin.setAddTime(addTime);
			admin.setLastLogin(addTime);
			if(map.get("partnerId")!=null)admin.setPartnerId(Integer.valueOf(map.get("partnerId").toString()));
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
		

//		try {
//			ReturnObject<EHaiStore> rm = haiStoreService.store_register(request, pid, username, password, confirmPassword, store_name, contacts, mobile, address, EAdminClassifyEnum.dining, weixin_token,EUserTypeEnum.dining);
//			return this.writeJson(rm);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "注册失败");}});
		
		
	}
	
	
	/**
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param order_time 
	 * @param orderSort New：即最新的，order_time的之后的订单；Old：即以往的订单，order_time之前的订单，配合获取订单数量rows
	 * @param condition 预留，暂无用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ehaisOrder")
	public String ehaisOrder(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "order_time", required = false) String order_time,
			@RequestParam(value = "orderSort", required = false) String orderSort,
			@ModelAttribute EConditionObject condition 
			){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			HaiOrderInfoExample orderInfoExample = new HaiOrderInfoExample();
			HaiOrderInfoExample.Criteria c = orderInfoExample.createCriteria();
			c.andStoreIdEqualTo(store_id)
			.andOrderStatusEqualTo(EOrderStatusEnum.success)
			.andClassifyEqualTo(EAdminClassifyEnum.shop);
			
			if(StringUtils.isNotBlank(order_time) && ECommon.isDate(order_time)){				
				Date d = DateUtil.formatDate(order_time, DateUtil.FORMATSTR_2);
				if(orderSort.equals("New")){//定时器读取比当前时间之后的订单，不受数量限制
					c.andAddTimeGreaterThan(d);
					orderInfoExample.setOrderByClause("add_time asc");
				}else if(orderSort.equals("Old")){//下拉列表获取此时间之前的订单
					c.andAddTimeLessThan(d);
					orderInfoExample.setOrderByClause("add_time desc");
					orderInfoExample.setLimitStart(condition.getStart());
					orderInfoExample.setLimitEnd(condition.getRows());
				}else{
					rm.setMsg("错误参数");
					return this.writeJson(rm);
				}				
			}else{//第一次进入界面，获取最新的订单
				orderInfoExample.setOrderByClause("add_time desc");
				orderInfoExample.setLimitStart(condition.getStart());
				orderInfoExample.setLimitEnd(condition.getRows());
			}
			
			
			
			Long total = haiOrderInfoMapper.countByExample(orderInfoExample);
			List<HaiOrderInfoWithBLOBs> listOrder = haiOrderInfoMapper.selectByExampleWithBLOBs(orderInfoExample);
			List<Long> orderIds = new ArrayList<Long>();
			
			for (HaiOrderInfoWithBLOBs haiOrderInfoWithBLOBs : listOrder) {
				orderIds.add(haiOrderInfoWithBLOBs.getOrderId());
			}
			Map<String,Object> map = new HashMap<String,Object>();
			if(orderIds.size() > 0){
				HaiOrderGoodsExample oge = new HaiOrderGoodsExample();
				oge.createCriteria().andOrderIdIn(orderIds);
				List<HaiOrderGoods> listOrderGoods = haiOrderGoodsMapper.selectByExampleWithBLOBs(oge);
				map.put("listOrderGoods", listOrderGoods);
			}
			
			rm.setMap(map);
			rm.setRows(listOrder);
			rm.setTotal(total);
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	
	public static void main(String[] args) throws Exception {
		String cid = SignUtil.setCid(1, 0, 0L, 0L, "ehais_wxdev");
		System.out.println(cid);
		Map<String,Object> map = SignUtil.getCid(cid,"ehais_wxdev");
		
		String newPid = SignUtil.setCid(1,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), 128L, "ehais_wxdev");
		System.out.println(newPid);
		
	}
	
}
