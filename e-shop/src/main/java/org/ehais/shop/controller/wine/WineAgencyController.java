package org.ehais.shop.controller.wine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EUsersService;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.WArticleGoodsMapper;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.WArticleGoods;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EncryptUtils;
import org.ehais.util.MatrixToImageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Controller
@RequestMapping("/wine")
public class WineAgencyController extends CommonController{
	private Integer store_id = 56;
	@Autowired
	private EWPPublicService eWPPublicService;
	@Autowired
	private EUsersService eUsersService;
	@Autowired
	private WArticleGoodsMapper wArticleGoodsMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	
	@RequestMapping("/login")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			modelMap.addAttribute("submit", "agency_login_submit");
			modelMap.addAttribute("redirect", "agency/main");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/login";
	}
	
	@ResponseBody
	@RequestMapping("/agency_login_submit")
	public String agency_login_submit(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "verificationcode", required = true) String verificationcode
			) {		
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			ReturnObject<EHaiUsers> rm = eUsersService.agencyLogin(request, username, password,verificationcode, "web");
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{'code':-1,'msg':'wrong'}";
	}
	
	@RequestMapping("/agency/logout")
	public String logout(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		
		try{
			HttpSession session = request.getSession(true);
			session.removeAttribute(EConstants.SESSION_USER_ID);
			session.removeAttribute(EConstants.SESSION_USER_NAME);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:login";
	}
	
	@RequestMapping("/agency/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/a_main";
	}
	
	
	@RequestMapping("/agency/article")
	public String article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/a_article";
	}
	@ResponseBody
	@RequestMapping(value="/agency/article_json",method=RequestMethod.POST)
	public String article_json(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "goods_name", required = false) String goods_name
			
			) {		
		try{
			ReturnObject<WArticleGoods> rm = new ReturnObject<WArticleGoods>();
			rm.setCode(0);
			List<WArticleGoods> list = wArticleGoodsMapper.listArticleGoods(store_id, title, goods_name, condition.getStart(), condition.getRows());
			Long total = wArticleGoodsMapper.countArticleGoods(store_id, title, goods_name);
			rm.setRows(list);
			rm.setTotal(total);
			rm.setCode(1);
			
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{'code':-1,'msg':'wrong'}";
	}
	
	private String setSid(Long agencyId,Integer articleId,Long userId,Long goodsId) throws Exception{
		WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
		String md5 = EncryptUtils.md5(agencyId.toString()+articleId.toString()+userId.toString()+goodsId.toString()+wp.getToken());
		String sid = md5.substring(0, 5)+agencyId.toString()+"-"+md5.substring(5,15)+articleId.toString()+"_"+md5.substring(15,26)+userId.toString()+"-"+md5.substring(26,32)+goodsId.toString();
		return sid;
	}
	
	@RequestMapping(value="/agency/article_qrcode",method=RequestMethod.POST)
	public void article_qrcode(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "goodsId", required = true) Long goodsId,
			@RequestParam(value = "download", required = false) Integer download
			
			) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		 
		try{
			EHaiArticleExample articleExample = new EHaiArticleExample();
			articleExample.createCriteria().andArticleIdEqualTo(articleId).andStoreIdEqualTo(store_id);
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(articleExample);
			if(listArticle == null || listArticle.size() == 0){
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.getWriter().print("错误信息");
				return ;
			}
			EHaiArticle article = listArticle.get(0);
			HaiGoodsExample goodsExample = new HaiGoodsExample();
			goodsExample.createCriteria().andGoodsIdEqualTo(goodsId).andStoreIdEqualTo(store_id);
			List<HaiGoods> listGoods = haiGoodsMapper.selectByExample(goodsExample);
			if(listGoods == null || listGoods.size() == 0){
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				response.getWriter().print("错误信息");
				return ;
			}
			
			String content = request.getScheme()+"://"+request.getServerName()+"/wine/wxarticle!"+this.setSid(user_id,articleId,user_id,goodsId);
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
	        int startY = height-(height-width)/2; 
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
	        
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	@RequestMapping("/agency/trade")
	public String trade(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/a_trade";
	}
	
	@RequestMapping("/agency/modify_password")
	public String modify_password(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/wine/a_modify_password";
	}
	
}
