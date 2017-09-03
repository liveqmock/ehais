package org.ehais.shop.controller.wine;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.controller.CommonController;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.util.EncryptUtils;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

public class WineCommonController extends CommonController{
	//三级分销的主页面，可以将代理，与三个分销关系的ID组成商城主页，从而确定到三层关系与分销员的唯一商城地址

	protected Integer store_id = 56;
	
	@Autowired
	protected EWPPublicService eWPPublicService;
	
	//加密设置
	protected String setSid(Long agencyId,Integer articleId,Long userId,Long goodsId) throws Exception{
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		String md5 = EncryptUtils.md5(agencyId.toString()+articleId.toString()+userId.toString()+goodsId.toString()+wp.getToken());
		String sid = md5.substring(0, 5)+agencyId.toString()+"-"+md5.substring(5,15)+articleId.toString()+"_"+md5.substring(15,26)+userId.toString()+"-"+md5.substring(26,32)+goodsId.toString();
		return sid;
	}
	
	
	/**
	 * 解析参数
	 * @param sid
	 * @return
	 */
	protected Map<String,Object> getSid(String sid){
		if(StringUtils.isEmpty(sid))return null;
		Map<String,Object> map = null;
		try{
			String s1 = sid.substring(0, 5);if(StringUtils.isEmpty(s1))return null;
			String sAgencyId = sid.substring(5,5 + sid.substring(5).indexOf("-"));if(StringUtils.isEmpty(sAgencyId))return null;
			String s2 = sid.substring(sid.indexOf("-") + 1 ,sid.indexOf("-") + 11);if(StringUtils.isEmpty(s2))return null;
			String sArticleId = sid.substring(sid.indexOf("-") + 11 ,sid.indexOf("_"));if(StringUtils.isEmpty(sArticleId))return null;
			String s3 = sid.substring(sid.indexOf("_")+1,sid.indexOf("_") +12);if(StringUtils.isEmpty(s3))return null;
			String sUserId = sid.substring(sid.indexOf("_") +12,sid.indexOf("_") +sid.substring(sid.indexOf("_")).indexOf("-"));if(StringUtils.isEmpty(sUserId))return null;
			String s4 = sid.substring(sid.lastIndexOf("-")+1,sid.lastIndexOf("-")+7);if(StringUtils.isEmpty(s4))return null;
			String sGoodsId = sid.substring(sid.lastIndexOf("-")+7);if(StringUtils.isEmpty(sGoodsId))return null;
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			if(EncryptUtils.md5(sAgencyId+sArticleId+sUserId+sGoodsId+wp.getToken()).equals(s1+s2+s3+s4)){
				map = new HashMap<String,Object>();
				map.put("agencyId", sAgencyId);
				map.put("articleId", sArticleId);
				map.put("userId", sUserId);
				map.put("goodsId", sGoodsId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	//跳转微信认证
	protected String redirect_wx_authorize(HttpServletRequest request , String path) throws Exception {
		String REDIRECT_URI = request.getScheme()+"://"+request.getServerName() + path;
		REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		return "redirect:"+WeiXinUtil.authorize_snsapi(wp.getAppid(), "snsapi_base", REDIRECT_URI);
	}
	
}
