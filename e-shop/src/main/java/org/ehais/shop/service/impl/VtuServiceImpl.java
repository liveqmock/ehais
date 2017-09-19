package org.ehais.shop.service.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.shop.mapper.vtu.VtuShareMapper;
import org.ehais.shop.mapper.vtu.VtuSignMapper;
import org.ehais.shop.model.vtu.VtuShare;
import org.ehais.shop.model.vtu.VtuSign;
import org.ehais.shop.model.vtu.VtuSignExample;
import org.ehais.shop.service.VtuService;
import org.ehais.tools.ReturnObject;
import org.ehais.util.DateUtil;
import org.ehais.util.ECommon;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.FSO;
import org.ehais.util.ResourceUtil;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.WeiXinTemplateMessage;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("vtuService")
public class VtuServiceImpl implements VtuService {
	
	private Integer store_id = 5;

	@Autowired
	private VtuSignMapper vtuSignMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private VtuShareMapper vtuShareMapper;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	
	public static String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	public static String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	public static String weixin_token = ResourceUtil.getProValue("weixin_token");
	
	
	private static String accessKey = ResourceUtil.getProValue("qiniu.accesskey");
	private static String secretKey = ResourceUtil.getProValue("qiniu.secretkey");
	private static String bucket = ResourceUtil.getProValue("qiniu.bucket");
	private static String domain = ResourceUtil.getProValue("qiniu.domain");
	
	
	@Override
	public ReturnObject<VtuShare> vtuMessage(HttpServletRequest request,Long vtuId) throws Exception {
		ReturnObject<VtuShare> rm = new ReturnObject<VtuShare>();
		rm.setCode(0);
		VtuSign vtuSign = vtuSignMapper.selectByPrimaryKey(vtuId);
		Long user_id = (Long) request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if(user_id == null || user_id == 0 || user_id.longValue() != vtuSign.getUserId().longValue()){
			rm.setMsg("user session wrong");
			return rm;
		}
		
		String vtujson = request.getRealPath("/vtu")+"/vtu.json";
		
		String vtuContent = FSO.ReadFileName(vtujson);
		JSONObject json = JSONObject.fromObject(vtuContent);
		JSONArray arr = json.getJSONArray("org");
		InputStream orgImg = EHttpClientUtil.getUriStream(arr.get(ECommon.getRand(0, arr.size()-1)).toString());
		InputStream hourImg = null;

		String art = "";
		EHaiArticle article = eHaiArticleMapper.article_rand(store_id);
		if(article != null ){
			art = article.getTitle();
		}
		
		Date date = new Date();
		String vtime = DateUtil.formatDate(date, "HH:mm");
		
		int hour = Integer.parseInt(vtime.substring(0,2));
		if(hour < 12){
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("morning").get(0).toString());
		}else if(hour >= 12 && hour < 18){
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("midday").get(0).toString());
		}else if(hour >= 18 && hour < 24){
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("night").get(0).toString());
		}else{
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("night").get(0).toString());
		}
		
//		String path = this.sharePic(orgImg, hourImg, 
//				vtuSign.getRealname(), 
//				vtuSign.getMobile(), 
//				vtuSign.getBusiness(), 
//				vtuSign.getInspire(),
//				vtuSign.getPic(),
//				art
//				);
//		
//		//将路径保存数据库中
//		VtuShare share = new VtuShare();
//		share.setVtuId(vtuSign.getVtuId());
//		share.setUserId(vtuSign.getUserId());
//		share.setVtuTime(DateUtil.formatDate(date, "HH:mm"));
//		share.setVtuPic(path);
//		share.setCreateDate(date);
//		
//		vtuShareMapper.insert(share);
		VtuShare share = vtuShareMapper.selectByPrimaryKey(1L);
		
		rm.setModel(share);
		rm.setCode(1);

		return rm;
	}
	@Override
	public String vtuMessage(String appdomain,String vtujson,String vtime) throws Exception {
		// TODO Auto-generated method stub
		
		String vtuContent = FSO.ReadFileName(vtujson);
		JSONObject json = JSONObject.fromObject(vtuContent);
		JSONArray arr = json.getJSONArray("org");
		InputStream orgImg = EHttpClientUtil.getUriStream(arr.get(ECommon.getRand(0, arr.size()-1)).toString());
		InputStream hourImg = null;
		
		
		VtuSignExample exp = new VtuSignExample();
		VtuSignExample.Criteria c = exp.createCriteria();
		c.andIsValidEqualTo(true);
		
		
		int hour = Integer.parseInt(vtime.substring(0,2));
		if(hour < 12){
			c.andMorningTimeEqualTo(vtime);
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("morning").get(0).toString());
		}else if(hour >= 12 && hour < 18){
			c.andMiddayTimeEqualTo(vtime);
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("midday").get(0).toString());
		}else if(hour >= 18 && hour < 24){
			c.andNightTimeEqualTo(vtime);
			hourImg = EHttpClientUtil.getUriStream(json.getJSONArray("night").get(0).toString());
		}else{
			return null;
		}
		
		List<VtuSign> listVtuSign = vtuSignMapper.selectByExample(exp);
		if(listVtuSign == null || listVtuSign.size() == 0){
			return null;
		}
			
		String art = "";
		EHaiArticle article = eHaiArticleMapper.article_rand(store_id);
		if(article != null ){
			art = article.getTitle();
		}
		
		Date date = new Date();
		String path = null;
		for (VtuSign vtuSign : listVtuSign) {
			System.out.println("vtu service message ,.........."+vtuSign.getVtuId());
			path = this.sharePic(orgImg, hourImg, 
					vtuSign.getRealname(), 
					vtuSign.getMobile(), 
					vtuSign.getBusiness(), 
					vtuSign.getInspire(),
					vtuSign.getPic(),
					art
					);
			
			//将路径保存数据库中
			VtuShare share = new VtuShare();
			share.setVtuId(vtuSign.getVtuId());
			share.setUserId(vtuSign.getUserId());
			share.setVtuTime(vtime);
			share.setVtuPic(path);
			share.setCreateDate(date);
			
			vtuShareMapper.insert(share);
			
			Long vtuShareId = share.getVtuShareId();
			
			EHaiUsers user = eHaiUsersMapper.selectByPrimaryKey(vtuSign.getUserId());
			
			this.postWeiXinTemplate(appdomain,vtuSign,share,user,date,hour);
		}
		
		return "success";
	}
	
	private String sharePic(InputStream orgImg,
			InputStream hourImg,
			String realname,
			String mobile,
			String business,
			String inspire,
			String pic,//头像之类
			String article
			) throws Exception{
		
		
		
		BufferedImage biOrgImg =ImageIO.read(orgImg); 
		BufferedImage biHourImg =ImageIO.read(hourImg); 
		
		Graphics2D g2d = biOrgImg.createGraphics();
		int orgImgWidth = biOrgImg.getWidth();// 获取原图的宽度
		int orgImgHeight = biOrgImg.getHeight();// 获取原图的高度
		System.out.println(orgImgWidth+"==="+orgImgHeight);
		
		
		int hourImgWidth = biHourImg.getWidth();// 获取层图的宽度
		int hourImgHeight = biHourImg.getHeight();// 获取层图的高度
		System.out.println(hourImgWidth+"===****==="+hourImgHeight);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));//SRC_ATOP
		g2d.drawImage(biHourImg, 40 , 20, hourImgWidth, hourImgHeight, null);
		
		int line = 3;
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font(null, 0, 28));
		
		
		
		g2d.drawString(realname, 15, orgImgHeight - line * 36);
		if(StringUtils.isNotBlank(mobile)){
			line = line - 1;
			g2d.drawString(mobile, 15, orgImgHeight - line * 36);
		}
		if(StringUtils.isNotBlank(business)){
			line = line - 1;
			g2d.drawString(business, 15, orgImgHeight - line * 36);
		}
		
		int r = 0;
		

//		inspire = "为你祝福为你欢笑因为在今天我的笑因为在今天我";
		if(StringUtils.isNotBlank(inspire)){
			StringBuilder sb = new StringBuilder(inspire);
			if(sb.length() > 15)sb.insert(15, " ");
			if(sb.length() > 30)sb.insert(30, " ");
			
			String[] in = sb.toString().split(" ");
			
			for (String string : in) {
				g2d.drawString(string, 50, hourImgHeight + 40 + (36 * r));
				r++;
			}
			
		}
		

//		article = "我读的书愈多就愈亲近世界愈明了生活的意义愈觉得生活的重要";
		if(StringUtils.isNotBlank(article)){

			StringBuilder art = new StringBuilder(article);
			if(art.length() > 10)art.insert(10, " ");
			if(art.length() > 20)art.insert(20, " ");
			if(art.length() > 30)art.insert(30, " ");
			if(art.length() > 40)art.insert(40, " ");
			String[] ain = art.toString().split(" ");
			r++;
			for (String string : ain) {
				g2d.drawString(string, 250, hourImgHeight + 40 + (36 * r));
				r++;
			}
		}
		
//		pic = "http://pic.qianmi.com/qmui/v0.1/img/pro-login-logo.png";
		if(pic != null && StringUtils.isNotBlank(pic)){

			InputStream picImg = EHttpClientUtil.getUriStream(pic);
			BufferedImage biPicImg =ImageIO.read(picImg);
			int picImgWidth = biPicImg.getWidth();// 获取层图的宽度
			int picImgHeight = biPicImg.getHeight();// 获取层图的高度
			System.out.println(picImgWidth+"==="+picImgWidth);
			
			if(picImgWidth > picImgHeight){
				if(picImgWidth > 200){
					picImgHeight = picImgHeight / (picImgWidth / 200) ;
					picImgWidth = 200;
				}
			}else if(picImgWidth < picImgHeight){
				if(picImgHeight > 200){
					picImgWidth = picImgWidth / (picImgHeight / 200) ;
					picImgHeight = 200;
				}
			}else if(picImgWidth == picImgHeight){
				if(picImgHeight > 200){
					picImgWidth = picImgHeight = 200;
				}
			}
			
			g2d.drawImage(biPicImg, orgImgWidth - picImgWidth - 15, orgImgHeight - picImgHeight - 60, picImgWidth , picImgHeight , null);
			
		}
		
		
		
		g2d.setColor(Color.getColor("#fdfdfd"));
		g2d.setFont(new Font(null, 0, 30));
		
		g2d.drawString("易海司·微签", orgImgWidth - 180, orgImgHeight - 30 );
		
		g2d.dispose();// 释放图形上下文使用的系统资源
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
		ImageIO.write(biOrgImg, "png", os);  
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		
//		ImageIO.write(biOrgImg, "png", new File("d:/hello.png"));  
		
		
		String path = this.postQiniuStream(is);
		
		return path;
	}
	
	
	private String postQiniuStream(InputStream morImg)throws Exception{
		String path = "";
		Configuration cfg = new Configuration(Zone.zone2());
		UploadManager uploadManager = new UploadManager(cfg);
		Auth auth = Auth.create(accessKey, secretKey);
		StringMap putPolicy = new StringMap();
		putPolicy.put("returnBody",
				"{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
		long expireSeconds = 3600;
		String upToken = auth.uploadToken(bucket, null, expireSeconds, putPolicy);
		
		String key = null;
		Response qiniuResponse = uploadManager.put(morImg, key, upToken, null, null);
		// 解析上传成功的结果
		DefaultPutRet putRet = new Gson().fromJson(qiniuResponse.bodyString(), DefaultPutRet.class);
		System.out.println(domain + putRet.key);
		return domain + putRet.key;
	}
	
	private String postWeiXinTemplate(String webappdomain,VtuSign vtuSign,VtuShare share,EHaiUsers users,Date date,int hour) throws Exception{
		WeiXinTemplateMessage template = new WeiXinTemplateMessage();
		template.setTemplate_id("LFdLrMKmvqCgJ3sbIB2cbaZsEChQmFwfnvpn1VrbhOI");//订单支付成功通知
		template.setTouser(users.getOpenid());
		
		template.setUrl(webappdomain+"/vtu_share!"+SignUtil.setVtuShareId(store_id, 0L, users.getUserId(), vtuSign.getVtuId(), share.getVtuShareId() , weixin_token));
		System.out.println("template.getUrl():::::::::::::"+template.getUrl());
		template.setTopcolor("#FF0000");
		
		Map<String,Object> mapTemplateUser = new HashMap<String,Object>();
        
		Map<String,String> first = new HashMap<String,String>();
		first.put("value", "您好，向您的微信朋友圈打个照呼问个好吧");
		first.put("color", "#173177");
		mapTemplateUser.put("first", first);
		
		Map<String,String> keyword1 = new HashMap<String,String>();
		keyword1.put("value", vtuSign.getRealname());
		keyword1.put("color", "#173177");
		mapTemplateUser.put("keyword1", keyword1);
		
		Map<String,String> keyword2 = new HashMap<String,String>();
		keyword2.put("value", (hour < 12 ) ? "早安":((hour >= 12 && hour < 18) ? "中午好" : ( hour >= 18 ? "晚上好" : "每一天好"))  );
		keyword2.put("color", "#173177");
		mapTemplateUser.put("keyword2", keyword2);
		
		Map<String,String> keyword3 = new HashMap<String,String>();
		keyword3.put("value", DateUtil.formatDate(date, DateUtil.FORMATSTR_2));
		keyword3.put("color", "#173177");
		mapTemplateUser.put("keyword3", keyword3);
		
		Map<String,String> remark = new HashMap<String,String>();
		remark.put("value", vtuSign.getMorningCount() == null ? "0" : vtuSign.getMorningCount().toString());
		remark.put("color", "#173177");
		mapTemplateUser.put("remark", remark);
		
		template.setData(mapTemplateUser);
		
		return WeiXinUtil.TemplateSend(WeiXinUtil.getAccessToken(store_id, weixin_appid, weixin_appsecret).getAccess_token(), template);
		
	}
	
	public static void main(String[] args) throws Exception {
		String url = "http://127.0.0.1/vtudebug";
		String req = EHttpClientUtil.methodGet(url);
		System.out.println(req);
	}

}
