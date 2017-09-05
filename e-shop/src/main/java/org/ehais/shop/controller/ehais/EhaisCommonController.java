package org.ehais.shop.controller.ehais;

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

public class EhaisCommonController extends CommonController{
	//三级分销的主页面，可以将代理，与三个分销关系的ID组成商城主页，从而确定到三层关系与分销员的唯一商城地址

	@Autowired
	protected EWPPublicService eWPPublicService;
	
	/**
	 * 加密设置
	 * 0-5+商家ID
	 * 6-10+代理ID
	 * 11-15+上级分销人ID
	 * 16-20+自己或分销的人ID
	 * 21-25+文章ID
	 * 26-30+商品ID
	 * 31-32原字符串
	 * @param agencyId
	 * @param articleId
	 * @param userId
	 * @param goodsId
	 * @param store_id
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	protected String setSid(Integer store_id,Integer agencyId,Long parentId,Long userId,Integer articleId,Long goodsId,String secret) throws Exception{
		String md5 = EncryptUtils.md5(store_id.toString()+agencyId.toString()+parentId.toString()+userId.toString()+articleId.toString()+goodsId.toString()+secret);
		String sid = md5.substring(0, 5)+store_id.toString()+"0-0"+
				md5.substring(5,10)+agencyId.toString()+"1-1"+
				md5.substring(10,15)+parentId.toString()+"2-2"+
				md5.substring(15,20)+userId.toString()+"3-3"+
				md5.substring(20,25)+articleId.toString()+"4-4"+
				md5.substring(25,30)+goodsId.toString()+"5-5"+
				md5.substring(30,32);
		return sid;
	}
	
	public static void main(String[] args) throws Exception {
		Integer store_id = 56;
		Integer agencyId = 15;
		Long parentId = 12l;
		Long userId = 29l;
		Integer articleId = 209;
		Long goodsId = 203l;
		String secret = "ehais_wxdev";
		EhaisCommonController wc = new EhaisCommonController();
		String sid = wc.setSid(store_id, agencyId, parentId, userId, articleId, goodsId, secret);
		Integer n0 = sid.indexOf("0-0");
		Integer n1 = sid.indexOf("1-1");
		Integer n2 = sid.indexOf("2-2");
		Integer n3 = sid.indexOf("3-3");
		Integer n4 = sid.indexOf("4-4");
		Integer n5 = sid.indexOf("5-5");
		
		System.out.println(n0+"-"+n1+"-"+n2+"-"+n3+"-"+n4+"-"+n5);
		
		String s0 = sid.substring(0, 5);
		String s_store_id = sid.substring(5,n0);
		
		String s1 = sid.substring(n0+3,n0+8);
		String s_agencyId = sid.substring(n0+8, n1);
		
		String s2 = sid.substring(n1+3, n1+8);
		String s_parendId = sid.substring(n1+8, n2);
		
		String s3 = sid.substring(n2+3,n2+8);
		String s_userId = sid.substring(n2+8, n3);
		
		String s4 = sid.substring(n3+3,n3+8);
		String s_articleId = sid.substring(n3+8, n4);
		
		String s5 = sid.substring(n4+3,n4+8);
		String s_goodsId = sid.substring(n4+8, n5);
		
		String s6 = sid.substring(n5+3,n5+5);
		
		System.out.println(s0+"-"+s1+"-"+s2+"-"+s3+"-"+s4+"-"+s5+"-"+s6);
		
		System.out.println(s_store_id+"-"+s_agencyId+"-"+s_parendId+"-"+s_userId+"-"+s_articleId+"-"+s_goodsId);
		
		Map<String,Object> map = wc.getSid(sid, secret);
		if(map!=null){
			System.out.println(map.get("store_id"));
			System.out.println(map.get("agencyId"));
			System.out.println(map.get("parendId"));
			System.out.println(map.get("userId"));
			System.out.println(map.get("articleId"));
			System.out.println(map.get("goodsId"));
		}
	}
	
	/**
	 * 解析参数
	 * @param sid
	 * @return
	 */
	protected Map<String,Object> getSid(String sid,String secret){
		if(StringUtils.isEmpty(sid))return null;
		Map<String,Object> map = null;
		try{
			Integer n0 = sid.indexOf("0-0");
			Integer n1 = sid.indexOf("1-1");
			Integer n2 = sid.indexOf("2-2");
			Integer n3 = sid.indexOf("3-3");
			Integer n4 = sid.indexOf("4-4");
			Integer n5 = sid.indexOf("5-5");
			
			String s0 = sid.substring(0, 5);
			String s_store_id = sid.substring(5,n0);
			
			String s1 = sid.substring(n0+3,n0+8);
			String s_agencyId = sid.substring(n0+8, n1);
			
			String s2 = sid.substring(n1+3, n1+8);
			String s_parendId = sid.substring(n1+8, n2);
			
			String s3 = sid.substring(n2+3,n2+8);
			String s_userId = sid.substring(n2+8, n3);
			
			String s4 = sid.substring(n3+3,n3+8);
			String s_articleId = sid.substring(n3+8, n4);
			
			String s5 = sid.substring(n4+3,n4+8);
			String s_goodsId = sid.substring(n4+8, n5);
			
			String s6 = sid.substring(n5+3,n5+5);
			
			if(EncryptUtils.md5(s_store_id+s_agencyId+s_parendId+s_userId+s_articleId+s_goodsId+secret).equals(s0+s1+s2+s3+s4+s5+s6)){
				map = new HashMap<String,Object>();
				map.put("store_id", s_store_id);
				map.put("agencyId", s_agencyId);
				map.put("parendId", s_parendId);
				map.put("userId", s_userId);
				map.put("articleId", s_articleId);				
				map.put("goodsId", s_goodsId);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return map;
	}
	
	/**
	 * 通过地址栏获取商家编号
	 * @param sid
	 * @return
	 */
	protected Integer getUriStoreId(String sid){
		Integer n0 = sid.indexOf("0-0");
		if(n0 < 6)return 0;
		String s_store_id = sid.substring(5,n0);
		if(StringUtils.isNotEmpty(s_store_id)){
			return Integer.valueOf(s_store_id);
		}
		return 0;
	}
	
	//跳转微信认证
	protected String redirect_wx_authorize(HttpServletRequest request ,String appid , String path ) throws Exception {
		String REDIRECT_URI = request.getScheme()+"://"+request.getServerName() + path;
		REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
		return "redirect:"+WeiXinUtil.authorize_snsapi(appid, "snsapi_base", REDIRECT_URI);
	}
	
	

}
