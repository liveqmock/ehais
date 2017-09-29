package org.ehais.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.service.VtuService;
import org.ehais.util.DateUtil;
import org.ehais.util.EmojiFilterUtils;
import org.ehais.util.ResourceUtil;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WeiXinUserInfo;
import org.ehais.weixin.model.WeiXinUserInfoBatch;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

/**
 * 基于注解的定时器
 * 
 * @author tyler
 */
@Component
public class MyTaskAnnotation {

	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	private EWPPublicService eWPPublicService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private VtuService vtuService;

	public static String webapp_domain = ResourceUtil.getProValue("webapp.domain");
	public static String webapp_vtu = ResourceUtil.getProValue("webapp.vtu");

	// /**
	// * 定时计算。每天凌晨 01:00 执行一次
	// */
	// @Scheduled(cron = "0 0 1 * * *")
	// public void show(){
	// System.out.println("Annotation：is show run");
	// }
	//
	// /**
	// * 心跳更新。启动时执行一次，之后每隔2秒执行一次
	// */
	// @Scheduled(fixedRate = 1000*2)
	// public void print(){
	// System.out.println("Annotation：print run");
	// }
	//
	// @Scheduled(cron="0 0/2 * * * ? ")
	public void updateUserNickFace() {
		Date date = new Date();
		System.out.println(DateUtil.formatDate(date, DateUtil.FORMATSTR_1)
				+ "========================================updateUserNickFace");

		EHaiUsersExample example = new EHaiUsersExample();
		example.createCriteria().andOpenidIsNotNull().andNicknameIsNull().andStoreIdIsNotNull();
		List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(example);
		System.out.println("listUsers.size()" + listUsers.size());
		for (EHaiUsers eHaiUsers : listUsers) {
			try {
				System.out.println(eHaiUsers.getOpenid());
				WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(eHaiUsers.getStoreId());
				AccessToken access_token = WeiXinUtil.getAccessToken(eHaiUsers.getStoreId(), wp.getAppid(),
						wp.getSecret());
				WeiXinUserInfo userInfo = WeiXinUtil.getUserInfo(access_token.getAccess_token(), eHaiUsers.getOpenid());
				System.out.println("userInfo.getNickname()" + userInfo.getNickname());
				System.out.println("userInfo.getHeadimgurl()" + userInfo.getHeadimgurl());
				System.out.println("userInfo.getSubscribe()" + userInfo.getSubscribe());
				eHaiUsers.setNickname(EmojiFilterUtils.filterEmoji(userInfo.getNickname()));
				eHaiUsers.setFaceImage(userInfo.getHeadimgurl());
				eHaiUsers.setSubscribe(userInfo.getSubscribe());

				eHaiUsersMapper.updateByPrimaryKeySelective(eHaiUsers);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Scheduled(cron = "0 0/7 *  * * ? ")
	public void batchUserInfo() {
		List<Integer> store_ids = eHaiUsersMapper.wxDistinctStoreId();
		for (Integer integer : store_ids) {
			List<Map<String,String>> user_list = new ArrayList<Map<String,String>>();
			EHaiUsersExample example = new EHaiUsersExample();
			example.createCriteria().andOpenidIsNotNull().andNicknameIsNull().andStoreIdIsNotNull();
			List<EHaiUsers> listUsers = eHaiUsersMapper.selectByExample(example);
			for (EHaiUsers eHaiUsers : listUsers) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("openid", eHaiUsers.getOpenid());
				map.put("lang", "zh_CN");
				user_list.add(map);
			}
			Map<String,List<Map<String,String>>> m = new HashMap<String,List<Map<String,String>>>();
			m.put("user_list", user_list);
			
			JSONObject json = JSONObject.fromObject(m);
			System.out.println(json.toString());
			
			try{
				WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(integer);
				AccessToken access_token = WeiXinUtil.getAccessToken(integer, wp.getAppid(), wp.getSecret());
				WeiXinUserInfoBatch batch = WeiXinUtil.batchUserInfo(access_token.getAccess_token(), json.toString());
				if(batch == null) return ;
				List<WeiXinUserInfo> user_info_list = batch.getUser_info_list();
				for (WeiXinUserInfo weiXinUserInfo : user_info_list) {
					example.clear();
					example.createCriteria().andStoreIdEqualTo(integer).andOpenidEqualTo(weiXinUserInfo.getOpenid());
					List<EHaiUsers> luser = eHaiUsersMapper.selectByExample(example);
					if(luser != null && luser.size() > 0){
						EHaiUsers record = luser.get(0);
						record.setNickname(weiXinUserInfo.getNickname());
						record.setFaceImage(weiXinUserInfo.getHeadimgurl());
						record.setSubscribe(weiXinUserInfo.getSubscribe());
						eHaiUsersMapper.updateByExampleSelective(record, example);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}

	@Scheduled(cron = "0 0/5 *  * * ? ")
	public void vtuShareRemind() {
		Date date = new Date();
		System.out.println(DateUtil.formatDate(date, DateUtil.FORMATSTR_1) + "========vtuShareRemind");

		try {
			vtuService.vtuMessage(webapp_domain, webapp_vtu, DateUtil.formatDate(date, "HH:mm"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
