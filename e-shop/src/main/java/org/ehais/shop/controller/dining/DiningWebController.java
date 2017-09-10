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
import org.ehais.enums.EIsVoidEnum;
import org.ehais.enums.EOrderClassifyEnum;
import org.ehais.enums.EOrderSourceEnum;
import org.ehais.enums.EOrderStatusEnum;
import org.ehais.enums.EPayStatusEnum;
import org.ehais.enums.EShippingStatusEnum;
import org.ehais.enums.EUserTypeEnum;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.WeiXinPayService;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiOrderGoodsMapper;
import org.ehais.shop.mapper.HaiOrderInfoMapper;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiOrderGoods;
import org.ehais.shop.model.HaiOrderInfoWithBLOBs;
import org.ehais.shop.model.HaiStore;
import org.ehais.shop.service.EStoreService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.model.WeiXinTemplateMessage;
import org.ehais.weixin.model.WeiXinWCPay;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

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
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiOrderGoodsMapper haiOrderGoodsMapper;
	@Autowired
	private WeiXinPayService weiXinPayService;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EStoreService eStoreService;
	
	
	
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
			HaiStore store = eStoreService.getEStore(store_id);
			if(store == null){
				return "redirect:"+website; //错误的链接，跳转商城
			}
			Map<String,Object> map = SignUtil.getDiningId(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request) && false){//微信端登录
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
					this.dining(modelMap, request, response,wp,store,store_id, sid);
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/diningStore!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				this.dining(modelMap, request, response,wp,store,store_id, sid);
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
	 * @throws Exception 
	 */
	private void dining(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			WpPublicWithBLOBs wp,
			HaiStore store,
			Integer store_id,
			String sid) throws Exception{
		
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
		
		String link = request.getScheme() + "://" + request.getServerName() + "/diningStore!"+sid;
		WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, 
				store_id, 
				wp.getAppid(), 
				wp.getSecret(), null);
		signature.setTitle(store.getStoreName()+"微信点餐应用");
		signature.setLink(link);
		signature.setDesc(store.getDescription());
		signature.setImgUrl(store.getStoreLogo());
		List<String> jsApiList = new ArrayList<String>();
		jsApiList.add("onMenuShareTimeline");
		jsApiList.add("onMenuShareAppMessage");
		jsApiList.add("onMenuShareQQ");
		jsApiList.add("onMenuShareWeibo");
		jsApiList.add("onMenuShareQZone");
		signature.setJsApiList(jsApiList);
		modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
		
	}
	
	
	@ResponseBody
	@RequestMapping("/diningSubmitOrder")
	public String diningSubmitOrder(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "sid", required = true) String sid,
			@RequestParam(value = "tPay", required = true) Integer tPay,
			@RequestParam(value = "cart", required = true) String cart,
			@RequestParam(value = "message", required = false) String message){
		System.out.println(cart);
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
			HaiStore store = eStoreService.getEStore(store_id);
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
			//价格效验
			if(total.intValue() != amount.intValue()){
				rm.setMsg("you are black person");
				return this.writeJson(rm);
			}
			
			
			Date date = new Date();
			//插入订单主表
			HaiOrderInfoWithBLOBs orderInfo = new HaiOrderInfoWithBLOBs();
			String orderSn = "101"+DateUtil.formatDate(date, DateUtil.FORMATSTR_4) + ECommon.nonceInt(4) + map.get("userId").toString();
			orderInfo.setOrderSn(orderSn);
			orderInfo.setUserId(Long.valueOf(map.get("userId").toString()));
			orderInfo.setOrderStatus(EOrderStatusEnum.init);
			orderInfo.setShippingStatus(EShippingStatusEnum.init);
			orderInfo.setPayStatus(EPayStatusEnum.init);
			//暂时未确认运输方式，这里是空的先
			orderInfo.setShippingId(0);
			orderInfo.setShippingName("");
			
			orderInfo.setPayId(0);
			orderInfo.setPayName("");
			
			orderInfo.setConsignee("");
			orderInfo.setCountry(0);
			orderInfo.setProvince(0);
			orderInfo.setCity(0);
			orderInfo.setCounty(0);
			orderInfo.setDistrict(0);
			orderInfo.setAddress("");
			orderInfo.setZipcode("");
			orderInfo.setTel("");
			orderInfo.setMobile("");
			orderInfo.setEmail("");
			orderInfo.setBestTime("");
			orderInfo.setSignBuilding("");
			
			orderInfo.setPostscript(message);//订单附言
			orderInfo.setShippingId(0);
			orderInfo.setShippingName("");
			orderInfo.setPayId(0);
			orderInfo.setPayName("");
			orderInfo.setInvPayee("");
			orderInfo.setGoodsAmount(amount);//总价钱
			orderInfo.setAddTime(date);
			orderInfo.setOrderSource(EOrderSourceEnum.weixin);
			orderInfo.setConfirmTime(0);
			orderInfo.setPayTime(0);
			orderInfo.setShippingTime(0);
			orderInfo.setTax(0F);
			orderInfo.setIsSeparate(0);
			orderInfo.setStoreId(store_id);
			orderInfo.setParentId(0L);
			orderInfo.setHowOos("");//缺货处理方式，等待所有商品备齐后再发； 取消订单；与店主协商
			orderInfo.setHowSurplus("");//根据字段猜测应该是余额处理方式，程序未作这部分实现
			orderInfo.setPackName("");//包装名称，取值表ecs_pack
			orderInfo.setCardName("");//贺卡的名称，取值ecs_card
			orderInfo.setCardMessage("");//'贺卡内容，由用户提交',
			orderInfo.setInvPayee("");//'发票抬头，用户页面填写',
			orderInfo.setInvContent("");//'发票内容，用户页面选择，取值ecs_shop_config的code字段的值为invoice_content的value',
			orderInfo.setShippingFee(0);//'配送费用',
			orderInfo.setInsureFee(0);//'保价费用',
			orderInfo.setPayFee(0);//'支付费用,跟支付方式的配置相关，取值表ecs_payment',
			orderInfo.setPackFee(0);//'包装费用，取值表取值表ecs_pack',
			orderInfo.setCardFee(0);//'贺卡费用，取值ecs_card ',
			orderInfo.setMoneyPaid(0);//'已付款金额',
			orderInfo.setSurplus(0);//'该订单使用余额的数量，取用户设定余额，用户可用余额，订单金额中最小者',
			orderInfo.setIntegral(0);//'使用的积分的数量，取用户使用积分，商品可用积分，用户拥有积分中最小者',
			orderInfo.setIntegralMoney(0);//'使用积分金额',
			orderInfo.setBonus(0);//'使用红包金额',
			orderInfo.setOrderAmount(0);//'应付款金额',
			orderInfo.setFromAd(0);//'订单由某广告带来的广告id，应该取值于ecs_ad',
			orderInfo.setReferer("");//'订单的来源页面',
			orderInfo.setAddTime(date);//'订单生成时间',
			orderInfo.setConfirmTime(0);//'订单确认时间',Long.valueOf(System.currentTimeMillis() / 1000).intValue()
			orderInfo.setPayTime(0);//'订单支付时间',
			orderInfo.setShippingTime(0);//'订单配送时间',
			orderInfo.setPackId(0);//'包装id，取值取值表ecs_pack',
			orderInfo.setCardId(0);//'贺卡id，用户在页面选择，取值取值ecs_card ',
			orderInfo.setBonusId(0);//'红包的id，ecs_user_bonus的bonus_id',
			orderInfo.setInvoiceNo("");//'发货单号，发货时填写，可在订单查询查看',
			orderInfo.setExtensionCode("");//'通过活动购买的商品的代号；GROUP_BUY是团购；AUCTION，是拍卖；SNATCH，夺宝奇兵；正常普通产品该处为空',
			orderInfo.setExtensionId(0);//'通过活动购买的物品的id，取值ecs_goods_activity；如果是正常普通商品，该处为0',
			orderInfo.setToBuyer("");//'商家给客户的留言,当该字段有值时可以在订单查询看到',
			orderInfo.setPayNote("");//'付款备注，在订单管理里编辑修改',
			orderInfo.setAgencyId(0);//'该笔订单被指派给的办事处的id，根据订单内容和办事处负责范围自动决定，也可以有管理员修改，取值于表ecs_agency',
			orderInfo.setInvType("");//'发票类型，用户页面选择，取值ecs_shop_config的code字段的值为invoice_type的value',
			orderInfo.setTax(0F);//'发票税额',
			orderInfo.setIsSeparate(0);//'0，未分成或等待分成；1，已分成；2，取消分成；',
			orderInfo.setParentId(0L);// '能获得推荐分成的用户id，id取值于表ecs_users',
			orderInfo.setDiscount(0F);//'折扣金额',
			orderInfo.setIsVoid(EIsVoidEnum.valid);
			orderInfo.setGoodsDesc(sb.toString());
			orderInfo.setRemark("");
			orderInfo.setClassify(EOrderClassifyEnum.dining);
			
			int code = haiOrderInfoMapper.insert(orderInfo);
			Long order_id = orderInfo.getOrderId();
			
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
			
			if(tPay.intValue() == 1){
				
				//跳转支付信息返回
				WeiXinWCPay cpay = weiXinPayService.WeiXinPayApi(request, store_id, 
						Long.valueOf(map.get("userId").toString()), users.getOpenid(), orderSn, amount, "易海司微信支付订单", "hai_order", order_id);
				Map<String,Object> mapPay = new HashMap<String,Object>();
				mapPay.put("WeiXinWCPay", cpay);
				rm.setMap(mapPay);
			}else{
				//推送消息
				//给用户推送消息
				WeiXinTemplateMessage template = new WeiXinTemplateMessage();
				template.setTemplate_id("LFdLrMKmvqCgJ3sbIB2cbaZsEChQmFwfnvpn1VrbhOI");//订单支付成功通知
				template.setTouser(users.getOpenid());
				template.setUrl(request.getScheme()+"://"+request.getServerName()+"/dining_order_detail!"+SignUtil.setOid(store_id, orderInfo.getOrderId(), orderInfo.getOrderSn(), orderInfo.getUserId(), users.getOpenid(), wp.getToken()));
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
				keyword3.put("value", String.format("%.2f", amount.doubleValue() / 100));
				keyword3.put("color", "#173177");
				mapTemplateUser.put("keyword3", keyword3);
				
				Map<String,String> remark = new HashMap<String,String>();
				remark.put("value", "陛下，掌柜已吩咐厨房开火了，请稍等！");
				remark.put("color", "#173177");
				mapTemplateUser.put("remark", remark);
				
				template.setData(mapTemplateUser);
				
				String template_result = WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret()).getAccess_token(), template);
				
				
				EHaiUsersExample userExample = new EHaiUsersExample();
				userExample.createCriteria().andStoreIdEqualTo(store_id).andUserTypeEqualTo(EUserTypeEnum.dining);
				List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(userExample);
				if(listUsers != null && listUsers.size() > 0){
					//给商家发信息
					WeiXinTemplateMessage templateStore = new WeiXinTemplateMessage();
					templateStore.setTemplate_id("Vlmhl4el_dW2Zcq_5cf9KkgRlDx7XI5G_XLuJQ4f2gM");//订单支付成功通知
					templateStore.setTouser(listUsers.get(0).getOpenid());
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
					keyword3Store.put("value", String.format("%.2f", amount.doubleValue() / 100));
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
					
					String template_result_store = WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret()).getAccess_token(), templateStore);
					
				}
	            
	            
			}
			
			rm.setCode(1);
			rm.setModel(orderInfo);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return this.writeJson(rm);
	}
	
}
