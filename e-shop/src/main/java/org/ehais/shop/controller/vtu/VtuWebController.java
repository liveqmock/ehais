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
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.shop.mapper.vtu.VtuSignMapper;
import org.ehais.shop.model.vtu.VtuSign;
import org.ehais.shop.model.vtu.VtuSignExample;
import org.ehais.tools.ReturnObject;
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
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private VtuSignMapper vtuSignMapper;
	
	//http://127.0.0.1/vtu_sign!5ab1650-0f864c01-1aa90b02-26ccab03-3a166089fc253
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
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(sid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			modelMap.addAttribute("sid", sid); 
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/vtu_sign!"+sid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setCid(store_id,Integer.valueOf(map.get("agencyId").toString()),Long.valueOf(map.get("userId").toString()), user.getUserId(),wp.getToken());
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
//						return "/vtu/vtu_sign";
					}else{
						modelMap.addAttribute("vtuSign", list.get(0));
//						return "/vtu/vtu_record";
					}
					String link = request.getScheme() + "://" + request.getServerName() + "/vtu_sign!"+sid;
					
					WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
					signature.setTitle("微签");
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

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/vtu_sign!"+sid);
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
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getCid(sid,wp.getToken());
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
			if(vtuList == null || vtuList.size() == 0){
				VtuSign vs = new VtuSign();
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
				VtuSign vs = vtuList.get(0);
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
			rm.setMsg("保存成功，等待相应时间推送签到信息");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
	}
	
	
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
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = SignUtil.getVtuId ( vid,wp.getToken());
			if(map == null){
			    return "redirect:"+website; //错误的链接，跳转商城
			}
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			modelMap.addAttribute("vid", vid); 
			
			
			if(this.isWeiXin(request)){//微信端登录
				if((user_id == null || user_id == 0 ) && StringUtils.isEmpty(code)){
					return this.redirect_wx_authorize(request , wp.getAppid() , "/vtu_share!"+vid);
				}else if(StringUtils.isNotEmpty(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, map);
					String newSid = SignUtil.setVtuId(store_id,Long.valueOf(map.get("userId").toString()), user.getUserId(),Long.valueOf(map.get("vtuId").toString()),Long.valueOf(map.get("vtuShareId").toString()),wp.getToken());
					String link = request.getScheme() + "://" + request.getServerName() + "/vtu_share!"+newSid;
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
					}
					String link = request.getScheme() + "://" + request.getServerName() + "/vtu_share!"+vid;
					
					WeiXinSignature signature = WeiXinUtil.SignatureJSSDK(request, Integer.valueOf(map.get("store_id").toString()), wp.getAppid(), wp.getSecret(), null);
					signature.setTitle("微签");
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
					
					return "/vtu/vtu_share";
					
				}else if(Long.valueOf(map.get("userId").toString()).longValue() != user_id.longValue()){
					System.out.println("user_id != map.userId condition is worng");
					request.getSession().removeAttribute(EConstants.SESSION_USER_ID);

				    return this.redirect_wx_authorize(request,wp.getAppid(), "/vtu_share!"+vid);
				}else{
					System.out.println(vid+" condition is worng");
					return "redirect:"+website; //错误的链接，跳转商城
				}
			}else{
				return "redirect:"+website;
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		return "/vtu/vtu_share";
	}
	
	public static void main(String[] args) {
		
		try {
			String cid = SignUtil.setCid(5, 0, 0l, 0l, "ehais_wxdev");
			System.out.println(cid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
