package org.ehais.shop.controller.dining;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdMediaTypeEnum;
import org.ehais.enums.ECouponsTypeEnum;
import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EOrderSourceEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EPayEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EUserTypeEnum;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.HaiOrderInfoExample;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.epublic.model.HaiPartner;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EPartnerService;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiAdMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiCouponsMapper;
import org.ehais.shop.mapper.HaiCouponsUserMapper;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.model.HaiAd;
import org.ehais.shop.model.HaiAdExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCoupons;
import org.ehais.shop.model.HaiCouponsUser;
import org.ehais.shop.model.HaiCouponsUserExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.service.OrderInfoService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.IpUtil;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinTemplateMessage;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/")
public class DiningWebController extends EhaisCommonController{
	private static Logger log = LoggerFactory.getLogger(DiningWebController.class);
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private HaiAdMapper haiAdMapper;
	@Autowired
	private WeiXinPayService weiXinPayService;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private HaiCouponsMapper haiCouponsMapper;
	@Autowired
	private EStoreService eStoreService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	@Autowired
	private EPartnerService ePartnerService;
	@Autowired
	private HaiCouponsUserMapper haiCouponsUserMapper;
	
	
	protected String prefix_order_dining = ResourceUtil.getProValue("prefix.order.dining");
	
	//http://127.0.0.1/diningStore!934a1580-0c1e0501-156ed21242-2b36621253-314dd0C104-49175b56
	//http://286ef960.ngrok.io/diningStore!934a1580-0c1e0501-156ed21242-2b36621253-314dd0C104-49175b56
	@RequestMapping("/diningStore!{sid}")
	public String diningStore(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "code", required = false) String code ) {	
		
		System.out.println("request.getRequestURL():"+request.getRequestURL());
		System.out.println("IP address : "+IpUtil.getIpAddrV2(request));
		
		log.info("request.getRequestURL():"+request.getRequestURL());
		log.info("IP address : "+IpUtil.getIpAddrV2(request));
		
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			EHaiStore store = eStoreService.getEStore(store_id);
			modelMap.addAttribute("store", store);
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			Map<String,Object> map = SignUtil.getDiningId(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			modelMap.addAttribute("sidMap", map);
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/diningStore!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setDiningId(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(),map.get("tableNo").toString(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/diningStore!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					this.dining(modelMap, request, response,wp,store,store_id, sid);
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
//					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
//					根据user_id重置sid
					sid = SignUtil.setDiningId(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("parentId").toString()), user_id, map.get("tableNo").toString(), wp.getToken());
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/diningStore!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
//				return "redirect:"+website; //错误的链接，跳转商城
				this.dining(modelMap, request, response,wp,store,store_id, sid);
			}
			
			return "/"+this.webThemes(store, store.getTheme())+"/diningStore";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
//		return "/dining/diningStore";
		
	}
	
	/**
	 * 读取店铺菜谱信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @throws Exception 
	 */
	private void dining(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			Integer store_id,
			String sid) throws Exception{
		
		//支付模式{"weixin":["",""],"cash":["",""]}
		modelMap.addAttribute("payModule", store.getPayModule());
		
		HaiAdExample adExample = new HaiAdExample();
		HaiAdExample.Criteria c = adExample.createCriteria();
		c.andIsMobileEqualTo(1)
		.andIsVoidEqualTo(1).andMediaTypeEqualTo(EAdMediaTypeEnum.carousel_mobile);
		//如果存在代理有广告，就使用代理的广告
		if(store.getPartnerId() != null && store.getPartnerId() > 0){
			c.andPartnerIdEqualTo(store.getPartnerId());
		}else{
			c.andStoreIdEqualTo(default_store_id);
		}
		
		List<HaiAd> adList = haiAdMapper.selectByExample(adExample);
		modelMap.addAttribute("adList", adList);
		
		
		//读取菜谱列表信息
		HaiCategoryExample cExp = new HaiCategoryExample();
		cExp.createCriteria().andStoreIdEqualTo(store_id);
		cExp.setOrderByClause("sort_order asc");
		List<HaiCategory> listCategory = haiCategoryMapper.selectByExample(cExp);
		modelMap.addAttribute("listCategory", listCategory);
//		modelMap.addAttribute("listCategory", JSONArray.fromObject(listCategory).toString());
		
		
		//读取菜品列表信息
		HaiGoodsExample gExp = new HaiGoodsExample();
		gExp.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andIsDeleteEqualTo(false)
		.andIsOnSaleEqualTo(true);
		gExp.setOrderByClause("sort_order asc");
		List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(gExp);
		for (HaiGoods haiGoods : listGoods) {
			String goodsUrl = SignUtil.setGid(store_id, 0, 0L, 0L, haiGoods.getGoodsId(), wp.getToken());
			haiGoods.setGoodsUrl(goodsUrl);
		}
		modelMap.addAttribute("listGoods", listGoods);
//		modelMap.addAttribute("listGoods", JSONArray.fromObject(listGoods).toString());
		
		modelMap.addAttribute("defaultimg", defaultimg);

		String link = request.getScheme() + "://" + request.getServerName() + "/diningStore!"+sid;
		
		String partnerName = "";
		if(store.getPartnerId()!=null && store.getPartnerId() > 0){
			HaiPartner partner = ePartnerService.getEPartner(store.getPartnerId());
			partnerName = "["+partner.getPartnerName()+"]";
		}
		
		
		this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName() + partnerName, link, store.getDescription(), store.getStoreLogo());

		//获取此商家是否有优惠券
		long countCoupons = haiCouponsMapper.countStoreCoupons(store_id);
		modelMap.addAttribute("countCoupons", countCoupons);//是否有可用优惠券
		
//		System.out.println("countCoupons:"+countCoupons);
	}
	
	
	/**
	 * 返回用户的订单列表
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/diningUserOrderList",method=RequestMethod.POST)
	public String diningUserOrderList(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition ){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		Long user_id = (Long) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		HaiOrderInfoExample example = new HaiOrderInfoExample();
		example.createCriteria()
		.andOrderStatusEqualTo(EOrderStatusEnum.success)
		.andUserIdEqualTo(user_id);
		example.setOrderByClause("order_id desc");
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		
		List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.selectByExampleWithBLOBs(example);
		try{
			for (HaiOrderInfoWithBLOBs haiOrderInfoWithBLOBs : list) {
				EHaiStore store = eStoreService.getEStore(haiOrderInfoWithBLOBs.getStoreId());
				if(store!=null)haiOrderInfoWithBLOBs.setConsignee(store.getStoreName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		Long total = haiOrderInfoMapper.countByExample(example);
		
		rm.setRows(list);
		rm.setTotal(total);
		
		rm.setCode(1);
		
		return this.writeJson(rm);
	}
	
	/**
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @param couponsId
	 * @param tPay
	 * @param cart
	 * @param message
	 * @param wTotal : 参数总价格
	 * @param wPayAmount : 参数支付的价格
	 * @param total :商品总价格
	 * @param amount : 订单的价格[去掉优惠券的，折扣的]
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/diningSubmitOrder",method=RequestMethod.POST)
	public String diningSubmitOrder(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sid", required = true) String sid,
			@RequestParam(value = "couponsId", required = true) Integer couponsId,//优惠券ID
			@RequestParam(value = "tPay", required = true) String tPay,
			@RequestParam(value = "cart", required = true) String cart,
			@RequestParam(value = "message", required = false) String message,
			@RequestParam(value = "wTotal", required = true) Integer wTotal,//总价格，经后台运算与前端的匹配
			@RequestParam(value = "wPayAmount", required = true) Integer wPayAmount//支付价格，经后台运算与前端的匹配
			){
		ReturnObject<HaiOrderInfoWithBLOBs> rm = new ReturnObject<HaiOrderInfoWithBLOBs>();
		rm.setCode(0);
		try{
			//判断购物车是否有值
			if(StringUtils.isEmpty(cart)){
				rm.setMsg("cart is wrong");
				return this.writeJson(rm);
			}
			Integer store_id = SignUtil.getUriStoreId(sid);
			if(store_id == 0){
				rm.setMsg("store is wrong");
				return this.writeJson(rm);
			}
			//效验地址商号与session商号是否一致
			Integer sStoreId = (Integer) request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			if(store_id.intValue() != sStoreId.intValue()){
				rm.setMsg("ses store is wrong");
				return this.writeJson(rm);
			}
			EHaiStore store = eStoreService.getEStore(store_id);
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			//解析地址效验
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getDiningId(sid,wp.getToken());
			Long sUserId = (Long) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(sUserId.longValue() != Long.valueOf(map.get("userId").toString()).longValue()){
				rm.setMsg("ses user is wrong");
				return this.writeJson(rm);
			}
			
			//获取用户编号效验
			EHaiUsers users = eHaiUsersMapper.selectByPrimaryKey(Long.valueOf(map.get("userId").toString()));
			if(users == null){
				rm.setMsg("user is wrong");
				return this.writeJson(rm);
			}

			//效验购物车数量
			JSONObject cartObj = JSONObject.fromObject(cart);
			if(cartObj.size() == 0){
				rm.setMsg("cart is empty");
				return this.writeJson(rm);
			}
			Iterator iterator = cartObj.keys();
			List<Long> goodsIds = new ArrayList<Long>();
			Integer total = 0;
			Integer badge = 0;
			while(iterator.hasNext()){
				String key = (String) iterator.next();
				JSONObject goodsObj = cartObj.getJSONObject(key);
				goodsIds.add(Long.valueOf(key));
				
				total += goodsObj.getInt("price") * goodsObj.getInt("badge");
				badge += goodsObj.getInt("badge");
			}
			
			//参数中的商品数组价格与传递价格不一致的情况v2...........
			if(total.intValue() != wTotal.intValue()){
				rm.setMsg("you are black person");
				return this.writeJson(rm);
			}
			
			//读取商品信息
			HaiGoodsExample goodsExample = new HaiGoodsExample();
			goodsExample.createCriteria().andStoreIdEqualTo(store_id).andGoodsIdIn(goodsIds);
			List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
			//数量效验
			if(cartObj.size()!=listGoods.size()){
				rm.setMsg("cart quantity is wrong");
				return this.writeJson(rm);
			}
			Integer amount = 0;
			StringBuffer sb = new StringBuffer();
			for (HaiGoods haiGoods : listGoods) {
				JSONObject goodsObj = cartObj.getJSONObject(haiGoods.getGoodsId().toString());
				amount += haiGoods.getShopPrice() * goodsObj.getInt("badge");
				sb.append(haiGoods.getGoodsName()+"【"+goodsObj.getInt("badge")+"】 ");
			}
			
			if(total.intValue() != amount.intValue()){
				rm.setMsg("you are black person");
				return this.writeJson(rm);
			}
			
			if(couponsId != null && couponsId > 0){
				HaiCoupons haiCoupons = haiCouponsMapper.selectByPrimaryKey(couponsId);
				if(haiCoupons == null ){
					rm.setMsg("优惠券无效");
					return this.writeJson(rm);
				}
				if(haiCoupons.getUseCount() != null && haiCoupons.getCouponsQuantity() != null && haiCoupons.getCouponsQuantity() > 0 && haiCoupons.getUseCount().intValue() >= haiCoupons.getCouponsQuantity().intValue()){
					rm.setMsg("优惠券已使用满额，请刷新重新下单");
					return this.writeJson(rm);
				}
				
				//带优惠券的价格效验v2版本.......................
				//获取优惠券，判断优惠券ID是否正确
				List<HaiCoupons> list = haiCouponsMapper.selectStoreCoupons(store_id);
				
				Integer _amount = 0;//优惠的金额，以分为单位判断
				Integer _couponsId = 0;
				for (HaiCoupons hcp : list) {
					if(total.intValue() >= hcp.getQuota().intValue()){
						if(hcp.getCouponsType().equals(ECouponsTypeEnum.reduce)){//立减优惠
							if(hcp.getDiscounts().intValue() * 100 > _amount.intValue()){
								_amount = hcp.getDiscounts() * 100;
								_couponsId = hcp.getCouponsId();
							}
						}else if(hcp.getCouponsType().equals(ECouponsTypeEnum.rebate)){//折扣优惠
							if(total.intValue() * (100 - hcp.getDiscounts().intValue()) / 100 > _amount.intValue()){
								_amount = total.intValue() * (100 - hcp.getDiscounts().intValue()) / 100;
								_couponsId = hcp.getCouponsId();
							}
						}
					}
				}
				//判断优惠券的价格是否正确
				if((wTotal.intValue() - wPayAmount.intValue()) != _amount.intValue() ){
					rm.setMsg("优惠券金额不正确");
					return this.writeJson(rm);
				}
				if(_couponsId.intValue() != couponsId.intValue()){
					rm.setMsg("优惠券使用有误");
					return this.writeJson(rm);
				}
				//更新支付金额为优惠后的金额
				amount = amount -_amount;
			}
			
			
			Date date = new Date();
			//插入订单主表
			HaiOrderInfoWithBLOBs orderInfo = new HaiOrderInfoWithBLOBs();
			orderInfoService.setDefaultOrder(orderInfo,date,store_id);//公共设置订单的默认值
			
			String orderSn = prefix_order_dining + DateUtil.formatDate(date, DateUtil.FORMATSTR_4) + map.get("userId").toString() + ECommon.nonceInt(4);
			orderInfo.setOrderSn(orderSn);
			orderInfo.setUserId(Long.valueOf(map.get("userId").toString()));
			orderInfo.setOrderStatus(EOrderStatusEnum.init);
			if(tPay.equals(EPayEnum.cash)){
				orderInfo.setOrderStatus(EOrderStatusEnum.success);//现金支付，不需要理订单状态
				orderInfo.setPayTime(System.currentTimeMillis());
				orderInfo.setPayStatus(EPayStatusEnum.cash);
				orderInfo.setPayName("现金支付");
				
				//流水号+1
				int daySerialCount = haiOrderInfoMapper.daySerialNumber(store_id);
				orderInfo.setDaySerialNumber(daySerialCount+1);
				
			}else if(tPay.equals(EPayEnum.weixin)){
				orderInfo.setPayName("微信支付");
			}
			
			orderInfo.setPostscript(message!=null?message:"");//订单附言
			orderInfo.setGoodsAmount(total);//商品价格
			orderInfo.setOrderAmount(amount);//订单价格
			orderInfo.setAddTime(date);
			orderInfo.setOrderSource(EOrderSourceEnum.dining_in_store);
			orderInfo.setGoodsDesc(sb.toString());
			orderInfo.setClassify(EOrderClassifyEnum.dining);
			orderInfo.setSid(sid);
			//保存优惠券编号
			if(couponsId != null && couponsId > 0){
				orderInfo.setCouponsId(couponsId);
				orderInfo.setCouponsUserId(0L);
				orderInfo.setDiscount(wTotal.intValue() - wPayAmount.intValue());
			}
			
			haiOrderInfoMapper.insert(orderInfo);
			Long order_id = orderInfo.getOrderId();
			
			if(couponsId != null && couponsId > 0){//更新优惠券记录使用数量				
				haiOrderInfoMapper.updateCouponsUseCount(store_id, couponsId);
			}
			
			List<HaiOrderGoods> orderGoodsList = new ArrayList<HaiOrderGoods>();
			for (HaiGoods haiGoods : listGoods) {
				HaiOrderGoods orderGoods = new HaiOrderGoods();
				JSONObject goodsObj = cartObj.getJSONObject(haiGoods.getGoodsId().toString());
				orderGoods.setOrderId(order_id);
				orderGoods.setGoodsId(haiGoods.getGoodsId());
				orderGoods.setGoodsName(haiGoods.getGoodsName());
				orderGoods.setGoodsSn(haiGoods.getGoodsSn());
				orderGoods.setGoodsNumber(goodsObj.getInt("badge"));
				orderGoods.setMarketPrice(haiGoods.getMarketPrice());
				orderGoods.setGoodsPrice(haiGoods.getShopPrice());
				orderGoods.setGoodsThumb(haiGoods.getOriginalImg());
				orderGoods.setProductId(0L);
				orderGoods.setParentId(0L);
				orderGoods.setIsGift(Short.valueOf("0"));
				orderGoods.setGoodsAttrId("");
				orderGoods.setGoodsAttr("");
				orderGoods.setExtensionCode("");
				orderGoods.setIsReal(true);
				orderGoods.setSendNumber(0);
				orderGoods.setAgencyId(Integer.valueOf(map.get("agencyId").toString()));
				orderGoods.setArticleId(0);
				orderGoods.setSellerUserId(Long.valueOf(map.get("parentId").toString()));
				orderGoodsList.add(orderGoods);
			}
			
			int codeBatch = haiOrderGoodsMapper.insertBatch(orderGoodsList);
			
			if(tPay.equals(EPayEnum.weixin)){
				
				//跳转支付信息返回
				WeiXinWCPay cpay = weiXinPayService.WeiXinPayApi(request,sid, 
						users.getOpenid(), 
						orderSn, 
						amount, 
						"易海司微信支付订单", 
						"hai_order", 
						order_id,EOrderClassifyEnum.dining);
				Map<String,Object> mapPay = new HashMap<String,Object>();
				mapPay.put("WeiXinWCPay", cpay);
				rm.setMap(mapPay);
			}else{
				
				HaiStoreStatistics storeStatistics = haiStoreStatisticsMapper.selectByPrimaryKey(store_id);
				if(storeStatistics == null){
					storeStatistics = new HaiStoreStatistics();
					storeStatistics.setStoreId(store_id);
				}
				storeStatistics.setCashAmount((storeStatistics.getCashAmount() == null ? 0 : storeStatistics.getCashAmount()) + amount);
				storeStatistics.setCashQuantity((storeStatistics.getCashQuantity() == null ? 0 : storeStatistics.getCashQuantity()) + 1);
				haiStoreStatisticsMapper.updateByPrimaryKey(storeStatistics);
				
				//推送消息
				//给用户推送消息
				String diningUserTemp = this.diningUserTemplateMessage(request, wp, store, orderInfo, users, map, store_id, date, sb);
				System.out.println(diningUserTemp);
				
				EHaiUsersExample userExample = new EHaiUsersExample();
				userExample.createCriteria().andStoreIdEqualTo(store_id).andUserTypeEqualTo(EUserTypeEnum.dining);
				List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(userExample);
				if(listUsers != null && listUsers.size() > 0){
					//给商家发信息
					String diningStoreTemp = this.diningStoreTemplateMessage(request, wp, store, orderInfo, listUsers.get(0), map, store_id, date, sb);
					System.out.println(diningStoreTemp);
				}
	            
	            
			}
			
			rm.setCode(1);
			rm.setModel(orderInfo);
			rm.setMsg("订单提交成功");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return this.writeJson(rm);
	}
	
	
	
	/**
	 * 点餐模式给用户发送消息
	 * @param request
	 * @param notifyPay
	 * @param orderInfo
	 * @param wpPublic
	 * @param store_id
	 * @throws Exception 
	 */
	private String diningUserTemplateMessage(HttpServletRequest request,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			HaiOrderInfoWithBLOBs orderInfo,
			EHaiUsers users,
			Map<String,Object> map,
			Integer store_id,
			Date date,
			StringBuffer sb
			) throws Exception{
		WeiXinTemplateMessage template = new WeiXinTemplateMessage();
		template.setTemplate_id("LFdLrMKmvqCgJ3sbIB2cbaZsEChQmFwfnvpn1VrbhOI");//订单支付成功通知
		template.setTouser(users.getOpenid());
		template.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_user_order_detail!"+SignUtil.setOid(store_id, orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), users.getOpenid(), wp.getToken()));
		template.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateUser = new HashMap<String,Object>();
        
		Map<String,String> first = new HashMap<String,String>();
		first.put("value", map.get("tableNo").toString() + "餐台/房的客官您好，你刚下的订单已成功交结掌柜，请等待送餐！");
		first.put("color", "#173177");
		mapTemplateUser.put("first", first);
		
		Map<String,String> keyword1 = new HashMap<String,String>();
		keyword1.put("value", store.getStoreName());
		keyword1.put("color", "#173177");
		mapTemplateUser.put("keyword1", keyword1);
		
		Map<String,String> keyword2 = new HashMap<String,String>();
		keyword2.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword2.put("color", "#173177");
		mapTemplateUser.put("keyword2", keyword2);
		
		Map<String,String> keyword3 = new HashMap<String,String>();
		keyword3.put("value", String.format("%.2f", orderInfo.getGoodsAmount().doubleValue() / 100));
		keyword3.put("color", "#173177");
		mapTemplateUser.put("keyword3", keyword3);
		
		Map<String,String> remark = new HashMap<String,String>();
		remark.put("value", "陛下，您点了"+sb.toString()+"，掌柜已吩咐厨房开火了，请稍等！");
		remark.put("color", "#173177");
		mapTemplateUser.put("remark", remark);
		
		template.setData(mapTemplateUser);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret()).getAccess_token(), template);
		
	}
	
	/**
	 * 点餐模式给商户发送消息
	 * @throws Exception 
	 */
	private String diningStoreTemplateMessage(HttpServletRequest request,
			WpPublicWithBLOBs wp,
			EHaiStore store,
			HaiOrderInfoWithBLOBs orderInfo,
			EHaiUsers users,
			Map<String,Object> map,
			Integer store_id,
			Date date,
			StringBuffer sb) throws Exception{
		WeiXinTemplateMessage templateStore = new WeiXinTemplateMessage();
		templateStore.setTemplate_id("Vlmhl4el_dW2Zcq_5cf9KkgRlDx7XI5G_XLuJQ4f2gM");//订单支付成功通知
		templateStore.setTouser(users.getOpenid());
		templateStore.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_store_order_detail!"+SignUtil.setOid(store_id, orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), users.getOpenid(), wp.getToken()));
		templateStore.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateStore = new HashMap<String,Object>();
        
		Map<String,String> firstStore = new HashMap<String,String>();
		firstStore.put("value", map.get("tableNo").toString() + "餐台/房的客官已下了订单");
		firstStore.put("color", "#173177");
		mapTemplateStore.put("first", firstStore);
		
		Map<String,String> keyword1Store = new HashMap<String,String>();
		keyword1Store.put("value", orderInfo.getOrderSn());
		keyword1Store.put("color", "#173177");
		mapTemplateStore.put("keyword1", keyword1Store);
		
		Map<String,String> keyword2Store = new HashMap<String,String>();
		keyword2Store.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword2Store.put("color", "#173177");
		mapTemplateStore.put("keyword2", keyword2Store);
		
		Map<String,String> keyword3Store = new HashMap<String,String>();
		keyword3Store.put("value", String.format("%.2f", orderInfo.getGoodsAmount().doubleValue() / 100));
		keyword3Store.put("color", "#173177");
		mapTemplateStore.put("keyword3", keyword3Store);
		
		Map<String,String> keyword4Store = new HashMap<String,String>();
		keyword4Store.put("value", "现金付款");
		keyword4Store.put("color", "#173177");
		mapTemplateStore.put("keyword4", keyword4Store);
		
		Map<String,String> remarkStore = new HashMap<String,String>();
		remarkStore.put("value", "客官点了"+sb.toString()+"，掌柜您可以为客官出菜了！");
		remarkStore.put("color", "#173177");
		mapTemplateStore.put("remark", remarkStore);
		
		templateStore.setData(mapTemplateStore);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret()).getAccess_token(), templateStore);
		
	}
	
	//http://127.0.0.1/dining_user_order_detail!d3172580-0ff0d6231-1b45db1012017091511435351591252-2bf77a1253-36d5b3oiGBot1K1vYJA2DFv2B-0W2xL9O04-44cdc5ba
	@RequestMapping(value="/dining_user_order_detail!{oid}",method=RequestMethod.POST)
	public String dining_user_order_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "oid") String oid	,
			@RequestParam(value = "code", required = false) String code ){
		Integer store_id = SignUtil.getUriStoreId(oid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			EHaiStore store = eStoreService.getEStore(store_id);
			modelMap.addAttribute("store", store);
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			Map<String,Object> map = SignUtil.getOid(oid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(this.isWeiXin(request)){//微信端登录
				if(StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/dining_user_order_detail!"+oid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					if(user == null){
						return "redirect:"+website; //错误的链接，跳转商城
					}
					user_id = user.getUserId();
					Long orderId = Long.valueOf(map.get("orderId").toString());
					
					HaiOrderInfoExample orderExample = new HaiOrderInfoExample();
					orderExample.createCriteria()
					.andStoreIdEqualTo(store_id)
					.andOrderIdEqualTo(orderId)
					.andUserIdEqualTo(user_id);
					List<HaiOrderInfoWithBLOBs> listOrder = haiOrderInfoMapper.selectByExampleWithBLOBs(orderExample);
					if(listOrder == null || listOrder.size() == 0){
						return "redirect:"+website; //错误的链接，跳转商城
					}
					modelMap.addAttribute("orderInfo", listOrder.get(0));
				}else{
				}
				
			}else{
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return "/dining/dining_order_detail";
	}
	
	
	@RequestMapping(value="/dining_store_order_detail!{oid}",method=RequestMethod.POST)
	public String dining_store_order_detail(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "oid") String oid	,
			@RequestParam(value = "code", required = false) String code ){
		
		Integer store_id = SignUtil.getUriStoreId(oid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			EHaiStore store = eStoreService.getEStore(store_id);
			modelMap.addAttribute("store", store);
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			Map<String,Object> map = SignUtil.getOid(oid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			if(this.isWeiXin(request)){//微信端登录
				if(StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/dining_store_order_detail!"+oid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					if(user == null){
						return "redirect:"+website; //错误的链接，跳转商城
					}
					
					Long orderId = Long.valueOf(map.get("orderId").toString());
					
					HaiOrderInfoExample orderExample = new HaiOrderInfoExample();
					orderExample.createCriteria()
					.andStoreIdEqualTo(store_id)
					.andOrderIdEqualTo(orderId)
					.andUserIdEqualTo(Long.valueOf(map.get("userId").toString()));
					
					List<HaiOrderInfoWithBLOBs> listOrder = haiOrderInfoMapper.selectByExampleWithBLOBs(orderExample);
					if(listOrder == null || listOrder.size() == 0){
						return "redirect:"+website; //错误的链接，跳转商城
					}
					modelMap.addAttribute("orderInfo", listOrder.get(0));
				}
			}else{

			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
		return "/dining/dining_order_detail";
	}
	
	
	/**
	 * 点击菜品图片展示菜品详情信息
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param gid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dining_goods!{gid}",method=RequestMethod.POST)
	public String diningGoods(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "gid") String gid	){
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		rm.setCode(0);
		Integer store_id = SignUtil.getUriStoreId(gid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			EHaiStore store = eStoreService.getEStore(store_id);
			modelMap.addAttribute("store", store);
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			Map<String,Object> map = SignUtil.getGid(gid, wp.getToken())  ;//DiningId(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			
			Long goodsId = Long.valueOf(map.get("goodsId").toString());
			HaiGoodsWithBLOBs model = haiGoodsMapper.selectByPrimaryKey(goodsId);
			rm.setModel(model);
			
			map.clear();
			
			//读取图片库
			HaiGoodsGalleryExample galleryExample = new HaiGoodsGalleryExample();
			galleryExample.createCriteria().andGoodsIdEqualTo(goodsId).andStoreIdEqualTo(store_id).andTableNameEqualTo("hai_goods");
			List<HaiGoodsGallery> gallery = haiGoodsGalleryMapper.selectByExample(galleryExample);
			map.put("gallery", gallery);
			
			rm.setMap(map);
			
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm) ;
	}
	
	/**
	 * 获取优惠券
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param sid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/coupons!{sid}",method=RequestMethod.POST)
	public String coupons(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid){
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return this.writeJson(rm) ;
		}
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			//获取店铺的优惠券
			List<HaiCoupons> list = haiCouponsMapper.selectStoreCoupons(store_id);
			List<Integer> couponsIds = new ArrayList<Integer>();
			for (HaiCoupons c : list) {
				couponsIds.add(c.getCouponsId());
			}
			//获取用户的优惠券
			HaiCouponsUserExample cuexp = new HaiCouponsUserExample();
			cuexp.createCriteria()
			.andCouponsIdIn(couponsIds)
			.andUserIdEqualTo(user_id)
			.andIsUseEqualTo(false);
			List<HaiCouponsUser> culist = haiCouponsUserMapper.selectByExample(cuexp);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user_coupons", culist);
			rm.setMap(map);
			rm.setRows(list);
			rm.setCode(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm) ;
	}
	
	@ResponseBody
	@RequestMapping(value="/receive_coupons!{sid}",method=RequestMethod.POST)
	public String receive_coupons(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid,
			@RequestParam(value = "couponsId", required = true) Integer couponsId){
		ReturnObject<HaiCoupons> rm = new ReturnObject<HaiCoupons>();
		rm.setCode(0);
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return this.writeJson(rm) ;
		}
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);

		try{
			HaiCouponsUserExample cuexp = new HaiCouponsUserExample();
			cuexp.createCriteria()
			.andCouponsIdEqualTo(couponsId)
			.andUserIdEqualTo(user_id)
			.andIsUseEqualTo(false);
			long c = haiCouponsUserMapper.countByExample(cuexp);
			if(c > 0){
				rm.setMsg("已领取");
				return this.writeJson(rm) ;
			}
			
			HaiCouponsUser cu = new HaiCouponsUser();
			cu.setCouponsId(couponsId);
			cu.setUserId(user_id);
			cu.setReceiveTime(new Date());
			cu.setIsUse(false);
			haiCouponsUserMapper.insert(cu);
			
			rm.setMsg("成功领取");
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.writeJson(rm) ;
	}
	
	
	@RequestMapping("/e_shop_vue_jroll!{cid}")
	public String e_shop_vue_jroll(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") String cid	,
			@RequestParam(value = "code", required = false) String code ) {	
		
		System.out.println("request.getRequestURL():"+request.getRequestURL());
		System.out.println("IP address : "+IpUtil.getIpAddrV2(request));
		
		log.info("request.getRequestURL():"+request.getRequestURL());
		log.info("IP address : "+IpUtil.getIpAddrV2(request));
		
		Integer store_id = SignUtil.getUriStoreId(cid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			EHaiStore store = eStoreService.getEStore(store_id);
			modelMap.addAttribute("store_id", store.getStoreId());
			modelMap.addAttribute("payModule", store.getPayModule());
			
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			Map<String,Object> map = SignUtil.getCid(cid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			modelMap.addAttribute("sidMap", map);
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/e_shop_vue_jroll!"+cid);
				}else if(StringUtils.isNotEmpty(code)){
					System.out.println(code);
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setDiningId(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(),map.get("tableNo").toString(), wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/e_shop_vue_jroll!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
//					this.dining(modelMap, request, response,wp,store,store_id, sid);
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
//					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);
//					根据user_id重置sid
					cid = SignUtil.setCid(store_id, Integer.valueOf(map.get("agencyId").toString()), Long.valueOf(map.get("parentId").toString()), user_id, wp.getToken());
				    return this.redirect_wx_authorize(request,wp.getAppid(), "/e_shop_vue_jroll!"+cid);
				}else{
					System.out.println(cid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
				
				String link = request.getScheme() + "://" + request.getServerName() + "/diningStore!"+cid;
				
				String partnerName = "";
				if(store.getPartnerId()!=null && store.getPartnerId() > 0){
					HaiPartner partner = ePartnerService.getEPartner(store.getPartnerId());
					partnerName = "["+partner.getPartnerName()+"]";
				}
				
				this.shareWeiXin(modelMap, request, response, wp, store_id, store.getStoreName() + partnerName, link, store.getDescription(), store.getStoreLogo());

				request.getSession().setAttribute("sessionId", request.getSession().getId());
				modelMap.addAttribute("cid", cid);
				
				return "/"+this.webThemes(store, store.getTheme())+"/e_shop_vue_jroll";
				
			}else {
				return "redirect:"+website; //错误的链接，跳转商城
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("dining", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
//		return "/dining/diningStore";
		
	}
	
	
	public static void main(String[] args) {
		try {
			String newSid = SignUtil.setCid(1,1,1L,1L, "ehais_wxdev");
			System.out.println(newSid);
			
			String pid = SignUtil.setPartnerId(58, 1, 23L, 125L, "ehais_wxdev");
			System.out.println(pid);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
