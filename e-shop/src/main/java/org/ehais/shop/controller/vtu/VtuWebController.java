package org.ehais.shop.controller.vtu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.vtu.VtuShareMapper;
import org.ehais.shop.mapper.vtu.VtuSignMapper;
import org.ehais.shop.model.vtu.VtuShare;
import org.ehais.shop.model.vtu.VtuShareExample;
import org.ehais.shop.model.vtu.VtuSign;
import org.ehais.shop.model.vtu.VtuSignExample;
import org.ehais.shop.service.VtuService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinSignature;
import org.ehais.weixin.utils.WeiXinUtil;
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
public class VtuWebController extends EhaisCommonController{

	public static String website = ResourceUtil.getProValue("website");
	public static String defaultimg = ResourceUtil.getProValue("defaultimg");
//	private Integer store_id = 5;
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	public static String weixin_token = ResourceUtil.getProValue("weixin_token");
	
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private VtuSignMapper vtuSignMapper;
	@Autowired
	private VtuService vtuService;
	@Autowired
	private VtuShareMapper vtuShareMapper;
	
	//http://127.0.0.1/vtu_sign!917fc50-097b3801-10cf5f02-29a3931243-3829351a72516
	@RequestMapping("/vtu_sign!{sid}")
	public String vtu_sign(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "code", required = false) String code
			){
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			Map<String,Object> map = SignUtil.getCid(sid,weixin_token);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			modelMap.addAttribute("sid", sid); 
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/vtu_sign!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(),weixin_token);
					String link = request.getScheme() + "://" + request.getServerName() + "/vtu_sign!"+newSid;
					System.out.println("code:"+link);
					return "redirect:"+link;
				}else if(user_id > 0 && Long.valueOf(map.get("userId").toString()).longValue() == user_id.longValue()){//经过code获取用户信息跳回自己的链接中来
					//判断是否已设置签到记录
					VtuSignExample vs_exp = new VtuSignExample();
					vs_exp.createCriteria().andUserIdEqualTo(user_id);
					List<VtuSign> list = vtuSignMapper.selectByExample(vs_exp);
					if(list == null || list.size() == 0){
						modelMap.addAttribute("vtuSign", new VtuSign());
					}else{
						modelMap.addAttribute("vtuSign", list.get(0));
						modelMap.addAttribute("vtu_share_now", "/vtu_share_now!"+SignUtil.setVtuSignId(store_id, Long.valueOf(map.get("parentId").toString()), user_id, list.get(0).getVtuId(), weixin_token));
					}
					String link = request.getScheme() + "://" + request.getServerName() + "/vtu_sign!"+sid;
					
					WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), weixin_appid, weixin_appsecret, null);
					signature.setTitle("微签 每一天朋友圈签到");
					signature.setLink(link);
					signature.setDesc("每天定时提醒你在朋友圈签到，让朋友知道你从事哪种职业，增加朋友圈的曝光率!");
					signature.setImgUrl(defaultimg);
					List<String> jsApiList = new ArrayList<String>();
					jsApiList.add("onMenuShareTimeline");
					jsApiList.add("onMenuShareAppMessage");
					jsApiList.add("onMenuShareQQ");
					jsApiList.add("onMenuShareWeibo");
					jsApiList.add("onMenuShareQZone");
					jsApiList.add("chooseImage");
					jsApiList.add("uploadImage");
					signature.setJsApiList(jsApiList);
					modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
					
					return "/vtu/vtu_sign";
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,weixin_appid, "/vtu_sign!"+sid);
				}else{
					System.out.println(sid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return "redirect:"+website;
			}
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "redirect:"+website;
	}
	
	
	@ResponseBody
	@RequestMapping("/vtuSignSave!{sid}")
	public String vtuSignSave(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "sid") String sid	,
			@RequestParam(value = "morning", required = false) String morning,
			@RequestParam(value = "midday", required = false) String midday,
			@RequestParam(value = "night", required = false) String night,
			@RequestParam(value = "inspire", required = false) String inspire,
			@RequestParam(value = "mobile", required = false) String mobile,
			@RequestParam(value = "realname", required = false) String realname,
			@RequestParam(value = "business", required = false) String business,
			@RequestParam(value = "pic", required = false) String pic
			){
		ReturnObject<VtuSign> rm = new ReturnObject<VtuSign>();
		rm.setCode(0);
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			rm.setMsg("store is wrong");return this.writeJson(rm);
		}
		try{
			Map<String,Object> map = SignUtil.getCid(sid,weixin_token);
			if(map == null){
				rm.setMsg("c store is wrong");return this.writeJson(rm);
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(user_id == null || user_id == 0){rm.setMsg("user sess empty");return this.writeJson(rm);}
			if(user_id.longValue() != Long.valueOf(map.get("userId").toString()).longValue()){rm.setMsg("user sess wrong");return this.writeJson(rm);}
			
			EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
			if(user == null){rm.setMsg("user obj empty");return this.writeJson(rm);}
			
			VtuSignExample vtuExample = new VtuSignExample();
			vtuExample.createCriteria().andUserIdEqualTo(user_id);
			List<VtuSign> vtuList = vtuSignMapper.selectByExample(vtuExample);
			Date date = new Date();
			VtuSign vs = null;
			if(vtuList == null || vtuList.size() == 0){
				vs = new VtuSign();
				vs.setUserId(user_id);
				vs.setMorningTime(morning);
				vs.setMiddayTime(midday);
				vs.setNightTime(night);
				vs.setInspire(inspire);
				vs.setBusiness(business);
				vs.setPic(pic);
				vs.setRealname(realname);
				vs.setMobile(mobile);
				vs.setCreateDate(date);
				vs.setUpdateDate(date);
				vs.setIsValid(true);
				vtuSignMapper.insert(vs);
			}else{
				vs = vtuList.get(0);
				vs.setMorningTime(morning);
				vs.setMiddayTime(midday);
				vs.setNightTime(night);
				vs.setInspire(inspire);
				vs.setBusiness(business);
				vs.setRealname(realname);
				vs.setMobile(mobile);
				vs.setPic(pic);
				vs.setCreateDate(date);
				vs.setUpdateDate(date);
				vs.setIsValid(true);
				vtuSignMapper.updateByPrimaryKey(vs);
			}
			
			rm.setCode(1);
			rm.setMsg("保存成功，等待相应时间推送签到信息或选择即时签到");
			rm.setAction("/vtu_share_now!"+SignUtil.setVtuSignId(store_id, Long.valueOf(map.get("parentId").toString()), user_id, vs.getVtuId(), weixin_token));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	//http://127.0.0.1/vtu_share!e34cc50-0bdc2601-1d44161242-2eb57913-36583c14-4fb4e80a
	@RequestMapping("/vtu_share!{vid}")
	public String vtu_share(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "vid") String vid	,
			@RequestParam(value = "code", required = false) String code
			){
		Integer store_id = SignUtil.getUriStoreId(vid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			Map<String,Object> map = SignUtil.getVtuShareId ( vid,weixin_token);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			modelMap.addAttribute("vid", vid); 
			
			
			if(this.isWeiXin(request)){//微信端登录
				if(StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/vtu_share!"+vid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map,false);
					Long user_id = user.getUserId();
					if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
						String newSid = SignUtil.setCid(store_id,0,Long.valueOf(map.get("userId").toString()), user.getUserId(),weixin_token);
						String link = request.getScheme() + "://" + request.getServerName() + "/vtu_sign!"+newSid;
						modelMap.addAttribute("link", link); 
					}
					//判断是否已设置签到记录
					VtuShareExample vs_exp = new VtuShareExample();
					vs_exp.createCriteria()
					.andUserIdEqualTo(Long.valueOf(map.get("userId").toString()))
					.andVtuIdEqualTo(Long.valueOf(map.get("vtuId").toString()))
					.andVtuShareIdEqualTo(Long.valueOf(map.get("vtuShareId").toString()));
					List<VtuShare> list = vtuShareMapper.selectByExample(vs_exp);
					if(list == null || list.size() == 0){
						return "redirect:"+website; //错误的链接，跳转商城
					}
					modelMap.addAttribute("vtuShare", list.get(0)); 
					String link = request.getScheme() + "://" + request.getServerName() + "/vtu_share!"+vid;
					WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), weixin_appid, weixin_appsecret, null);
					signature.setTitle("微签 每一天朋友圈签到");
					signature.setLink(link);
					signature.setDesc("每天定时提醒你在朋友圈签到，让朋友知道你从事哪种职业，增加朋友圈的曝光率!");
					signature.setImgUrl(defaultimg);
					List<String> jsApiList = new ArrayList<String>();
					jsApiList.add("onMenuShareTimeline");
					jsApiList.add("onMenuShareAppMessage");
					jsApiList.add("onMenuShareQQ");
					jsApiList.add("onMenuShareWeibo");
					jsApiList.add("onMenuShareQZone");
					
					signature.setJsApiList(jsApiList);
					signature.setShareSuccess("vtuShareSuccess");
					signature.setShareCancel("vtuShareCancel");
					modelMap.addAttribute("signature", JSONObject.fromObject(signature).toString());
					
					return "/vtu/vtu_share";
					
				}
			}else{
				return "redirect:"+website;
			}
				
			
			
			
			return "/vtu/vtu_share";
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/vtu/vtu_share";
	}
	
	@ResponseBody
	@RequestMapping("/vtuShareSuccess!{vid}")
	public String vtuShareSuccess(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "vid") String vid	
			){
		
		ReturnObject<VtuShare> rm = new ReturnObject<VtuShare>();
		rm.setCode(0);
		
		Integer store_id = SignUtil.getUriStoreId(vid);
		if(store_id == 0){
			rm.setMsg("store is wrong 101");
			return this.writeJson(rm);
		}
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			Map<String,Object> map = SignUtil.getVtuShareId ( vid,weixin_token);
			if(map == null){
				rm.setMsg("map is wrong 102");
				return this.writeJson(rm);
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			
			if(this.isWeiXin(request)){//微信端登录
				if(user_id == null || user_id == 0 || Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					rm.setMsg("user id is wrong 103");
					return this.writeJson(rm);
				}
				
				//判断是否已设置签到记录
				VtuShareExample vs_exp = new VtuShareExample();
				vs_exp.createCriteria()
				.andUserIdEqualTo(user_id)
				.andVtuIdEqualTo(Long.valueOf(map.get("vtuId").toString()))
				.andVtuShareIdEqualTo(Long.valueOf(map.get("vtuShareId").toString()));
				List<VtuShare> list = vtuShareMapper.selectByExample(vs_exp);
				if(list == null || list.size() == 0){
					rm.setMsg("vtu share id is wrong 104");
					return this.writeJson(rm);
				}
				
				VtuShare share = list.get(0);
				if(share.getIsShare()){
					rm.setMsg("vtu share id has share 1040");
					return this.writeJson(rm);
				}
				Date date = new Date();
				share.setShareTime(date);
				share.setIsShare(true);
				vtuShareMapper.updateByPrimaryKey(share);
				
				VtuSignExample vsignExp = new VtuSignExample();
				VtuSignExample.Criteria c = vsignExp.createCriteria();
				c.andVtuIdEqualTo(Long.valueOf(map.get("vtuId").toString()))
				.andUserIdEqualTo(user_id);
				List<VtuSign> listSign = vtuSignMapper.selectByExample(vsignExp);
				if(listSign == null || listSign.size() == 0){
					rm.setMsg("vtu id is wrong 105");
					return this.writeJson(rm);
				}
				
				VtuSign vtuSign = listSign.get(0);
				
				int hour = Integer.parseInt(share.getVtuTime().substring(0,2));
				if(hour < 12){
					vtuSign.setMorningCount((vtuSign.getMorningCount() == null ? 0 : vtuSign.getMorningCount()) + 1);
				}else if(hour >= 12 && hour < 18){
					vtuSign.setMiddayCount((vtuSign.getMiddayCount() == null ? 0 : vtuSign.getMiddayCount()) + 1);
				}else if(hour >= 18 && hour < 24){
					vtuSign.setNightCount((vtuSign.getNightCount() == null ? 0 : vtuSign.getNightCount()) + 1);
				}else{
					rm.setMsg("vtime id is wrong 106");
					return this.writeJson(rm);
				}
				
				vtuSignMapper.updateByPrimaryKey(vtuSign);
				
				rm.setCode(1);
				rm.setMsg("success");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	
	
	@RequestMapping("/vtu_share_now!{vid}")
	public String vtu_share_now(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "vid") String vid
			){
		Integer store_id = SignUtil.getUriStoreId(vid);
		if(store_id == 0){
			return "redirect:"+website; //错误的链接，跳转商城
		}
		
		request.getSession().setAttribute(EConstants.SESSION_STORE_ID, store_id);
		
		try{
			Map<String,Object> map = SignUtil.getVtuSignId(vid,weixin_token);
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			modelMap.addAttribute("vid", vid);
			
			if(user_id == null || user_id == 0){return "redirect:"+website;}
			if(user_id.longValue() != Long.valueOf(map.get("userId").toString()).longValue()){return "redirect:"+website;}
			
			EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(user_id);
			if(user == null){return "redirect:"+website;}
			
			ReturnObject<VtuShare> rm = vtuService.vtuMessage(request, Long.valueOf(map.get("vtuId").toString()));
			if(rm.getCode() != 1){
				return "redirect:"+website;
			}
			modelMap.addAttribute("vtuShare", rm.getModel());
			
			return "/vtu/vtu_share";
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:"+website;
		}
	}
	
	@ResponseBody
	@RequestMapping("/vtudebug")
	public String vtudebug(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		String img = "";
		try {
			
			String domain = request.getScheme()+"://"+request.getServerName();
			String vtujson = request.getRealPath("/vtu")+"/vtu.json";
			
			String path = vtuService.vtuMessage(domain ,vtujson, "08:30");
			img = "<img src='"+path+"'>";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img;
	}
	
	public static void main(String[] args) {
		
		try {
//			String cid = SignUtil.setCid(5, 0, 0l, 124l, "ehais_wxdev");
//			System.out.println(cid);
			
			String vid = SignUtil.setVtuShareId(5, 0L, 124L, 1L, 1L, "ehais_wxdev");
			System.out.println(vid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
