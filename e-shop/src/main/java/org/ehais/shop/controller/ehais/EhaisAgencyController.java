package org.ehais.shop.controller.ehais;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EUsersService;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.WArticleGoodsMapper;
import org.ehais.shop.mapper.WOrderGoodsActionMapper;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.WArticleGoods;
import org.ehais.shop.model.WOrderGoodsAction;
import org.ehais.shop.service.UsersAgencyService;
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
@RequestMapping("/ehais")
public class EhaisAgencyController extends EhaisCommonController{
	//store_id在ehaiscommoncontroller设置
	
	@Autowired
	private UsersAgencyService usersAgencyService;
	@Autowired
	private WArticleGoodsMapper wArticleGoodsMapper;
	@Autowired
	private HaiArticleGoodsMapper haiArticleGoodsMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private WOrderGoodsActionMapper wOrderGoodsActionMapper;
	
	
	@RequestMapping("/login")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
//		request.getSession(true).setAttribute(EConstants.SESSION_STORE_ID, store_id);
		try{
			modelMap.addAttribute("submit", "agency_login_submit");
			modelMap.addAttribute("redirect", "agency/main");
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/login";
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
			ReturnObject<EHaiUsers> rm = usersAgencyService.agencyLogin(request, username, password,verificationcode);
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
			session.removeAttribute(EConstants.SESSION_STORE_ID);
			session.removeAttribute(EConstants.SESSION_STORE_NAME);
			session.removeAttribute(EConstants.SESSION_AGENCY_ID);
			session.removeAttribute(EConstants.SESSION_AGENCY_NAME);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "redirect:login";
	}
	
	@RequestMapping("/agency/main")
	public String main(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/a_main";
	}
	
	
	@RequestMapping("/agency/article")
	public String article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/a_article";
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
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
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
	
	
	
	@RequestMapping(value="/agency/article_qrcode",method=RequestMethod.POST)
	public void agency_article_qrcode(ModelMap modelMap,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "download", required = false) Integer download
			
			) {	
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer agencyId = (Integer)request.getSession().getAttribute(EConstants.SESSION_AGENCY_ID);
		try{
			
			this.article_qrcode(request, response, eHaiArticleMapper, haiGoodsMapper, haiArticleGoodsMapper, store_id, agencyId, user_id, user_id, articleId, download);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	@RequestMapping("/agency/trade")
	public String trade(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/a_trade";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/agency/trade_json",method=RequestMethod.POST)
	public String trade_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "start_time", required = true) String start_time,
			@RequestParam(value = "end_time", required = true) String end_time
			) {	
		try{
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			System.out.println(System.currentTimeMillis() / 1000);
			
			ReturnObject<WOrderGoodsAction> rm = new ReturnObject<WOrderGoodsAction>();
			rm.setCode(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			Date sdate = sdf.parse(start_time); 
			Date edate = sdf.parse(end_time); 
			List<WOrderGoodsAction> list = wOrderGoodsActionMapper.listOrderGoods(store_id, Long.valueOf(sdate.getTime() / 1000).intValue() , Long.valueOf(edate.getTime() / 1000).intValue(), condition.getStart(), condition.getRows());
			
			
			Long total = wOrderGoodsActionMapper.countOrderGoods(store_id, Long.valueOf(sdate.getTime() / 1000).intValue() , Long.valueOf(edate.getTime() / 1000).intValue());
			rm.setRows(list);
			rm.setTotal(total);
			rm.setCode(1);
			
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "{'code':-1,'msg':'wrong'}";
	}
	
	@RequestMapping("/agency/modify_password")
	public String modify_password(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/ehais/a_modify_password";
	}
	
}
