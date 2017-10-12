package org.ehais.weixin.service.wx.impl;

import org.ehais.epublic.mapper.weixin.WxNotifyPayMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderMapper;
import org.ehais.epublic.mapper.weixin.WxUnifiedorderResultMapper;
import org.ehais.weixin.mapper.EOrderMapper;
import org.ehais.weixin.mapper.HaiArticleMapper;
import org.ehais.weixin.mapper.WpCustomMenuMapper;
import org.ehais.weixin.mapper.WpKeywordMapper;
import org.ehais.weixin.service.wx.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("weiXinService")
public class WeiXinServiceImpl extends WeiXinCommonServiceImpl implements WeiXinService {


	@Autowired
	private WxNotifyPayMapper wxNotityPayMapper;
	@Autowired
	private WpCustomMenuMapper customMenuMapper ;
	@Autowired
	private EOrderMapper eOrderMapper;
	@Autowired
	private WxUnifiedorderMapper wxUnifiedorderMapper;
	@Autowired
	private WxUnifiedorderResultMapper wxUnifiedorderResultMapper;
	@Autowired
	private WpKeywordMapper wpKeywordMapper;
	@Autowired
	private HaiArticleMapper haiArticleMapper;
//
//	public ReturnObject<Object> wxIndex(Integer wxid) throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<Object> ro = new ReturnObject<Object>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		String token = null;
//		//根据id找到缓存中的token
//		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(wxid);//临时使用tyler
////		WpPublicWithBLOBs wpPublic = WXPublicCacheManager.getInstance().getWXPublic(wxid);
////		if(wpPublic == null){
////			wpPublic = wpPublicMapper.selectByPrimaryKey(wxid);
////			WXPublicCacheManager.getInstance().putWXPublic(wxid, wpPublic);
////		}
//		token = wpPublic.getToken();
//		//根据token获取菜单
//		WpCustomMenuExample example = new WpCustomMenuExample();
//		WpCustomMenuExample.Criteria c = example.createCriteria();
//		c.andTokenEqualTo(token);
//		List<WpCustomMenu> menu_list = customMenuMapper.selectByExample(example);
//		map.put("menu_list", menu_list);
//		
//		
//		ro.setMap(map);
//		return ro;
//	}
//	
//	/* 支付回调结果
//	 * @see org.ehais.weixin.service.wx.WeiXinService#notifyPay(org.ehais.weixin.model.WeiXinNotifyPay)
//	 */
//	public ReturnObject<WeiXinNotifyPay> notifyPay(WeiXinNotifyPay notifyPay)
//			throws Exception {
//		// TODO Auto-generated method stub
//		ReturnObject<WeiXinNotifyPay> ro = new ReturnObject<WeiXinNotifyPay>();
//		ro.setCode(0);
//		wxNotityPayMapper.insert(new WxNotityPay(notifyPay));
//		try{
//			if(notifyPay.getReturn_code().equals("SUCCESS") && notifyPay.getReturn_code().equals("SUCCESS")){
////				EOrder order = eOrderMapper.findEOrderBySN(notifyPay.getOut_trade_no());
////				if(order == null){
////					ro.setMsg("不存在此订单");
////					notifyPay.setReturn_code("FAIL");notifyPay.setReturn_msg(ro.getMsg());
////				}
////				order.setePayStatus(Short.valueOf("1"));//设置支付成功
////				eOrderMapper.updateByPrimaryKeySelective(order);
//				
//				eOrderMapper.UpdatePayStatue(notifyPay.getOut_trade_no(), 1);
//				wxUnifiedorderMapper.UpdatePayStatue(notifyPay.getOut_trade_no(), 1);
//				
//			}else{
//				ro.setMsg("不存在此订单");
//				notifyPay.setReturn_code("FAIL");notifyPay.setReturn_msg(ro.getMsg());
//			}
//		}catch(Exception e){
//			
//		}
//		
//		ro.setModel(notifyPay);
//		ro.setCode(1);
//		return ro;
//	}
//	
//	
//
//	/**
//	 * 微信回复推送接口
//	 */
//	public String WeiXinNotityEvent(HttpServletRequest request, Integer wxid, WeiXinNotityXml notity) throws Exception {
//		// TODO Auto-generated method stub
//		WpPublicWithBLOBs wpPublic = eWPPublicService.getWpPublic(wxid);//临时使用tyler
//		
//		if(notity.getMsgType().equals("event") && notity.getEvent().equals("subscribe")){//关注事件
//			if(notity.getEventKey() == null || notity.getEventKey().equals("")){	
//				
//				return this.MsgType_Text(wpPublic, notity);	//普通关注		
//				
//			}else{//扫描带参数二维码事件，关注推送
//				
//			}
//		}else if(notity.getMsgType().equals("event") && notity.getEvent().equals("unsubscribe")){//取消关注事件
//		
//		}else if(notity.getMsgType().equals("event") && notity.getEvent().toUpperCase().equals("CLICK")){//点击菜单拉取消息时的事件推送
//			
//			return this.MsgType_Event_Click(request , wpPublic, notity);
//			
//		}else if(notity.getMsgType().equals("event") && notity.getEvent().toUpperCase().equals("VIEW")){//点击菜单跳转链接时的事件推送
//			
//		}
//		
//		return "success";
//	}
//	
//	
//	//普通关注
//	private String MsgType_Text(WpPublicWithBLOBs wpPublic , WeiXinNotityXml notity) throws Exception{
//		Gson gson = new Gson();
//		PublicAddonConfig pac = gson.fromJson(wpPublic.getAddonConfig(), PublicAddonConfig.class);
//		notity.setMsgType("text");
//		notity.setContent(pac.getWecome().getDescription());
//		
//		String toUserName = notity.getToUserName();
//		notity.setToUserName(notity.getFromUserName());
//		notity.setFromUserName(toUserName);
//		String reqXml = WeiXinUtil.fromNotityXml(notity);
//		return reqXml;
//		
//	}
//	
//	//点击推送事件
//	private String MsgType_Event_Click(HttpServletRequest request, WpPublicWithBLOBs wpPublic , WeiXinNotityXml notity){
//		
//		WpKeywordExample example = new WpKeywordExample();
//		WpKeywordExample.Criteria c = example.createCriteria();
//		c.andKeywordEqualTo(notity.getEventKey());
//		c.andTokenEqualTo(wpPublic.getToken());
//		List<WpKeyword> keywordList = wpKeywordMapper.selectByExampleWithBLOBs(example);
//		if(keywordList == null || keywordList.size() == 0)return "success";
//		WpKeyword k = keywordList.get(0);
//		if(k.getExtraText() == null || k.getExtraText().equals(""))return "success";
//		//回复文章部分
//		if(k.getExtraText().equals("custom_reply_mult") || k.getExtraText().equals("article_mult")){
//			
//			List<HaiArticle> articleList = haiArticleMapper.article_mult_list(wpPublic.getId(), k.getMultIds(),0,10);
//			
//			WeiXinArticles article = new WeiXinArticles();
//			article.setToUserName(notity.getFromUserName());
//			article.setFromUserName(notity.getToUserName());
//			article.setCreateTime(System.currentTimeMillis());
//			article.setMsgType("news");
//			article.setArticleCount(articleList.size());
//			
//			List<WeiXinArticlesItem> Articles = new ArrayList<WeiXinArticlesItem>();
//			
//			String url = request.getScheme() + "://"
//					+ request.getServerName() + ":"
//					+ request.getLocalPort();
//		
//			for (HaiArticle haiArticle : articleList) {
//				WeiXinArticlesItem item = new WeiXinArticlesItem();
//				item.setTitle(haiArticle.getTitle());
//				item.setDescription(haiArticle.getDescription());
//				item.setPicUrl(haiArticle.getArticleImages());
//				if(haiArticle.getLink() != null && !haiArticle.getLink().equals("")){
//					item.setUrl(haiArticle.getLink());
//				}else{
//					item.setUrl(url + "/weixin/ai-"+wpPublic.getId()+"-"+haiArticle.getArticleId()+".html");
//				}
//				
//				Articles.add(item);
//				
//			}
//			
//			article.setArticles(Articles);
//			
//			
//			XStream xStream = new XStream();
//			xStream.autodetectAnnotations(true);
////			xStream.alias("xml",WeiXinArticles.class);
////			xStream.alias("item",WeiXinArticlesItem.class);
//			
//			String content = xStream.toXML(article);
//			
//			return content ;
//		}
//		
//		return "success";
//	}
	
}
