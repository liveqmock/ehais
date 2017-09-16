package org.ehais.shop.controller.ehais;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.EHaiUsersExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EStoreService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.util.MatrixToImageWriter;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.OpenidInfo;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class EhaisCommonController extends CommonController{
	//三级分销的主页面，可以将代理，与三个分销关系的ID组成商城主页，从而确定到三层关系与分销员的唯一商城地址

	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	@Autowired
	protected EStoreService eStoreService;
	
	/**
	 * 1.判断session的userid,openid随便一个不存在，即走微信网络请求链接
	 * 2.如果userid与openid同时存在，则判断链接的userid与session的userid是否一样，如果不一样，则清空session同时走微信网络请求链接
	 * @param request
	 * @param code
	 * @param map
	 * @return
	 * @throws Exception
	 */
	protected EHaiUsers saveUserByOpenIdInfo(HttpServletRequest request,String code,Map<String ,Object> map) throws Exception{
		//获取openid
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(Integer.valueOf(map.get("store_id").toString()));
		OpenidInfo open = WeiXinUtil.getOpenid(code,wp.getAppid(),wp.getSecret());
		if(open == null) return null;
		//根据openid获取用户是否存在
		EHaiUsersExample userExp = new EHaiUsersExample();
		EHaiUsersExample.Criteria userC = userExp.createCriteria();
		userC.andOpenidEqualTo(open.getOpenid());
		System.out.println("openid:"+open.getOpenid());
		List<EHaiUsers> list = eHaiUsersMapper.selectByExample(userExp);
		Date date = new Date();
		EHaiUsers user = null;
		if(list == null || list.size() == 0){//用户不存在，入库
			user = new EHaiUsers();
			user.setOpenid(open.getOpenid());
			user.setParentId(Long.valueOf(map.get("userId").toString()));
			user.setAgencyId(Integer.valueOf(map.get("agencyId").toString()));
			user.setStoreId(Integer.valueOf(map.get("store_id").toString()));
			if(map.get("partnerId")!=null){
				user.setPartnerId(Integer.valueOf(map.get("partnerId").toString()));
			}			
			user.setEmail("");
			user.setUserName(open.getOpenid());
			user.setPassword("");					
			user.setRegTime(date);
			user.setLastLogin(date);
			eHaiUsersMapper.insert(user);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}else{
			user = list.get(0);
			user.setLastLogin(date);
			eHaiUsersMapper.updateByPrimaryKeyWithBLOBs(user);
			request.getSession().setAttribute(EConstants.SESSION_USER_ID, user.getUserId());
		}
//		request.getSession(true).setAttribute(EConstants.SESSION_OPEN_ID, open.getOpenid());
		
		return user;
	}
	
	//跳转微信认证
	protected String redirect_wx_authorize(HttpServletRequest request ,String appid , String path ) throws Exception {
		String REDIRECT_URI = request.getScheme()+"://"+request.getServerName() + path;
		REDIRECT_URI = java.net.URLEncoder.encode(REDIRECT_URI, "utf-8");
		return "redirect:"+WeiXinUtil.authorize_snsapi(appid, "snsapi_base", REDIRECT_URI);
	}
	
	
	protected void article_qrcode(HttpServletRequest request,
			HttpServletResponse response,
			EHaiArticleMapper eHaiArticleMapper,
			HaiGoodsMapper haiGoodsMapper,
			HaiArticleGoodsMapper haiArticleGoodsMapper,
			Integer store_id,
			Integer agencyId,
			Long parentId,
			Long userId,
			Integer articleId,
			Integer download) throws Exception{
		EHaiArticleExample articleExample = new EHaiArticleExample();
		articleExample.createCriteria().andArticleIdEqualTo(articleId).andStoreIdEqualTo(store_id);
		List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(articleExample);
		if(listArticle == null || listArticle.size() == 0){
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().print("错误信息");
			return ;
		}
		EHaiArticle article = listArticle.get(0);
		Long goodsId = 0l;
		
		HaiArticleGoodsExample agExample = new HaiArticleGoodsExample();
		agExample.createCriteria().andArticleIdEqualTo(articleId);
		List<HaiArticleGoods> listArticleGoods = haiArticleGoodsMapper.selectByExample(agExample);
		if(listArticleGoods != null && listArticleGoods.size() > 0){
			HaiArticleGoods articleGoods = listArticleGoods.get(0);
			goodsId = articleGoods.getGoodsId();
			if(goodsId.longValue() != 0){
				HaiGoodsExample goodsExample = new HaiGoodsExample();
				goodsExample.createCriteria().andGoodsIdEqualTo(goodsId).andStoreIdEqualTo(store_id);
				List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
				if(listGoods == null || listGoods.size() == 0){
					response.setHeader("Content-type", "text/html;charset=UTF-8");
					response.getWriter().print("关联商品错误信息");
					return ;
				}
			}
			
		}
		
		
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		String content = request.getScheme()+"://"+request.getServerName()+"/w_article_detail!"+SignUtil.setSid(store_id,agencyId,parentId,userId,articleId,goodsId,wp.getToken());
		System.out.println(content);
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        
        //设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN,1);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        
        //width:图片完整的宽;height:图片完整的高
        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
        int width = 400;
        int height = 500;
        
        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//        
        Graphics2D g = image.createGraphics();
        String pressText = article.getTitle();
        int fontSize = 25; //字体大小
        int fontStyle = 1; //字体风格
        
        int startX = (width-(fontSize*pressText.length()))/2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height-(height-width)/2 + 5; 
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, fontStyle, fontSize));
        g.drawString(pressText, startX, startY);
        
        
        fontSize = 12; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "广州易海司信息科技有限公司";
        startX = (width-(fontSize*pressText.length()))/2;
        g.drawString(pressText, startX, startY + 30);
        
        fontSize = 10; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "www.ehais.com";
        startX = (width-(fontSize*pressText.length()) / 2 )/2;
        g.drawString(pressText, startX, startY + 40);
        
        g.dispose();
        
        if(download != null && download == 1){//下载
        	response.setContentType("application/octet-stream");  
            response.setHeader("Accept-Ranges", "bytes");  
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(article.getTitle().getBytes("utf-8"), "ISO8859-1")+".jpg");  
            
        }
         
        
        OutputStream stream = response.getOutputStream();
        ImageIO.write(image, "jpg", stream);
        
	}

	protected void goods_qrcode(HttpServletRequest request,
			HttpServletResponse response,
			HaiGoodsMapper haiGoodsMapper,
			Integer store_id,
			Integer agencyId,
			Long parentId,
			Long userId,
			Long goodsId,
			Integer download) throws Exception{
		
		Integer articleId=0;
		HaiGoodsExample goodsExample = new HaiGoodsExample();
		goodsExample.createCriteria()
		.andGoodsIdEqualTo(goodsId)
		.andStoreIdEqualTo(store_id);
		List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
		if(listGoods == null || listGoods.size() == 0){
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.getWriter().print("商品错误信息");
			return ;
		}
		HaiGoods goods = listGoods.get(0);
		
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		String content = request.getScheme()+"://"+request.getServerName()+"/w_goods_detail!"+SignUtil.setSid(store_id,agencyId,parentId,userId,articleId,goodsId,wp.getToken());
		System.out.println(content);
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		@SuppressWarnings("rawtypes")
        Map hints = new HashMap();
        
        //设置UTF-8， 防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN,1);
        //设置二维码的容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        
        //width:图片完整的宽;height:图片完整的高
        //因为要在二维码下方附上文字，所以把图片设置为长方形（高大于宽）
        int width = 400;
        int height = 500;
        
        //画二维码，记得调用multiFormatWriter.encode()时最后要带上hints参数，不然上面设置无效
        BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//        
        Graphics2D g = image.createGraphics();
        String pressText = goods.getGoodsName();
        int fontSize = 25; //字体大小
        int fontStyle = 1; //字体风格
        
        int startX = (width-(fontSize*pressText.length()))/2;
        //y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height-(height-width)/2 + 5; 
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, fontStyle, fontSize));
        g.drawString(pressText, startX, startY);
        
        
        fontSize = 12; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "广州易海司信息科技有限公司";
        startX = (width-(fontSize*pressText.length()))/2;
        g.drawString(pressText, startX, startY + 30);
        
        fontSize = 10; //字体大小
        g.setFont(new Font(null, fontStyle, fontSize));
        pressText = "www.ehais.com";
        startX = (width-(fontSize*pressText.length()) / 2 )/2;
        g.drawString(pressText, startX, startY + 40);
        
        g.dispose();
        
        if(download != null && download == 1){//下载
        	response.setContentType("application/octet-stream");  
            response.setHeader("Accept-Ranges", "bytes");  
            response.setHeader("Content-Disposition", "attachment;fileName=" + new String(goods.getGoodsName().getBytes("utf-8"), "ISO8859-1")+".jpg");  
            
        }
         
        
        OutputStream stream = response.getOutputStream();
        ImageIO.write(image, "jpg", stream);
        
	}
	
	
	

}
