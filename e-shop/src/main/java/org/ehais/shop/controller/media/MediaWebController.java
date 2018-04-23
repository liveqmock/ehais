package org.ehais.shop.controller.media;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiAdMapper;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.mapper.HaiShopConfigMapper;
import org.ehais.epublic.model.EHaiAd;
import org.ehais.epublic.model.EHaiAdExample;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.HaiShopConfig;
import org.ehais.epublic.model.HaiShopConfigExample;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiCartWithBLOBs;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
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
public class MediaWebController extends CommonController{
	
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	@Autowired
	private EHaiAdMapper eHaiAdMapper;
	@Autowired
	private HaiShopConfigMapper haiShopConfigMapper;
	
	
	protected String video_transfer_website = ResourceUtil.getProValue("video.transfer.website");
	protected String video_url_website = ResourceUtil.getProValue("video.url.website");
	
	
	private Integer store_id = 1;
	private String modal = "web";
	private Integer len  = 4;
	private Integer hot_len  = 9;
	
	private void v_common(ModelMap modelMap,HttpServletRequest request) {
		store_id = 1;
//		modelMap.addAttribute("logo", "logo");
//		modelMap.addAttribute("company", "广州城市职业学院");			
//		String serverName = request.getServerName();
//		if(serverName.equals("gjs.ehais.org") || serverName.equals("gmedia.ehais.com")) {
//			store_id = 2;
			modelMap.addAttribute("logo", "g_logo");
			modelMap.addAttribute("company", "广师视频网");
//		}
		
			
		String browser = request.getHeader( "USER-AGENT" );
		if (browser.toLowerCase().indexOf( "msie" ) > 0) {
			modelMap.addAttribute("browser", "ie");
		}else {
			modelMap.addAttribute("browser", "notie");
		}
		
		//访问量
		modelMap.addAttribute("visit", this.visit(request));
		
	}

	@RequestMapping("/index.do")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("currentNav", "index");
			modelMap.addAttribute("video_url_website", video_url_website);
			
			modal = "web";
			hot_len  = 9;
			
			this.v_common(modelMap,request);
			
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				modal = "h5";
				hot_len = 4;
			}
			
			Map<String,Object> map = new HashMap<String,Object>();
			
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			
			EHaiAdExample adExample = new EHaiAdExample();
			EHaiAdExample.Criteria cad = adExample.createCriteria();
			cad.andStoreIdEqualTo(store_id);
			if(modal.equals("h5")) {
				cad.andIsMobileEqualTo(1);
			}else {
				cad.andIsMobileEqualTo(0);
			}
			adExample.setOrderByClause("ad_id desc");
			List<EHaiAd> adList = eHaiAdMapper.selectByExample(adExample);
			modelMap.addAttribute("adList", adList);
			
			
			EHaiArticleExample ae = new EHaiArticleExample();
			
			//推荐的
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setLimitStart(0);
			ae.setLimitEnd(hot_len);
			ae.setOrderByClause("update_date desc");
			List<EHaiArticle> listArticleIndex = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleIndex", listArticleIndex);
			
			Map<String, List<EHaiArticle>> mapArticle = new HashMap<String, List<EHaiArticle>>();
			//分类
			for (EHaiArticleCat c : listArticleCat) {
				ae.clear();
				ae.createCriteria()
				.andStoreIdEqualTo(store_id)
				.andIsHotEqualTo(true)
				.andCatIdEqualTo(c.getCatId());
				ae.setOrderByClause("update_date desc");
				ae.setLimitStart(0);
				ae.setLimitEnd(len);
				List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
				mapArticle.put(c.getCatId().toString(), listArticle);
			}
			modelMap.addAttribute("mapArticle", mapArticle);
			
			
			if(isWeiXin(request) || JudgeIsMoblie(request)){
				map.put("listArticleCat", listArticleCat);
				map.put("hotArticleList", listArticleIndex);
				
				
				modelMap.addAttribute("json", JSONObject.fromObject(map).toString());
			}
			
			return "/media/"+modal+"/index";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}		
	}
	
	
	@ResponseBody
	@RequestMapping("/media_cat_list_{cid}.lv")
	public String media_cat_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") Integer cid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows){
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		
		this.v_common(modelMap,request);
		modelMap.addAttribute("video_url_website", video_url_website);
		try{
			int psize = rows==null?len:rows;
			EHaiArticleExample ae = new EHaiArticleExample();
			ae.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(cid);
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(( page - 1 ) * psize);
			ae.setLimitEnd(psize);
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
			
			modelMap.addAttribute("listArticle", listArticle);
			
			Long count = eHaiArticleMapper.countByExample(ae);
			
			
			rm.setRows(listArticle);
			rm.setTotal(count);
			rm.setCode(1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
		
	}
	
	
	@RequestMapping("/list_{cid}.lv")
	public String list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "cid") Integer cid,
			@RequestParam(value = "page", required = false) Integer page){
		
		modelMap.addAttribute("currentNav", cid.toString());
		modelMap.addAttribute("video_url_website", video_url_website);
		this.v_common(modelMap,request);
		
		try{
			modal = "web";
			if(isWeiXin(request) || JudgeIsMoblie(request))modal = "h5";
			if(page == null)page = 1;
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			
			ace.clear();
			ace.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(cid);
			List<EHaiArticleCat> listArticleCat2 = eHaiArticleCatMapper.selectByExample(ace);
			if(listArticleCat2 == null || listArticleCat2.size() == 0){
				return this.errorJump(modelMap, "空数据");
			}
			
			EHaiArticleCat articleCat = listArticleCat2.get(0);
			modelMap.addAttribute("articleCat", articleCat);
			
			
			
			EHaiArticleExample ae = new EHaiArticleExample();
			ae.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(cid);
			ae.setOrderByClause("update_date desc,article_id desc");
			ae.setLimitStart(( page - 1 ) * len);
			ae.setLimitEnd(len);
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
			
			modelMap.addAttribute("listArticle", listArticle);
			
			Long count = eHaiArticleMapper.countByExample(ae);
			
			ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
			rm.setRows(listArticle);
			rm.setTotal(count);
			rm.setAction("list_"+cid+".lv");
			rm.setPageSize(len);
			rm.setCurrentPage(page);
			modelMap.addAttribute("rm", rm);
			
			ae.clear();
			ae.createCriteria()
			.andCatIdEqualTo(cid)
			.andStoreIdEqualTo(store_id)
			.andIsHotEqualTo(true);
//			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("update_date desc");
			ae.setLimitStart(0);
			ae.setLimitEnd(10);
			
			List<EHaiArticle> listArticleHot = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleHot", listArticleHot);
			
			
			return "/media/"+modal+"/list";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	@RequestMapping("/play{id}.lv")
	public String play(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "id") Integer id){
		
		String s_encode = (String) request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		modelMap.addAttribute("video_url_website", video_url_website);
		this.v_common(modelMap,request);
		
		try{
			modal = "web";
			if(isWeiXin(request) || JudgeIsMoblie(request))modal = "h5";
			
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			
			EHaiArticleExample ae = new EHaiArticleExample();
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andArticleIdEqualTo(id);
			ae.setLimitStart(0);
			ae.setLimitEnd(1);
			ae.setOrderByClause("update_date desc");
			
			
			Long count = eHaiArticleMapper.countByExample(ae);
			if(count == 0){
				return this.errorJump(modelMap, "空数据");
			}
			
			EHaiArticle article =  eHaiArticleMapper.selectByPrimaryKey(id);
			
			String videoUrl = article.getVideoUrl();
			if(StringUtils.isNotBlank(videoUrl) && videoUrl.indexOf("mp4") > 0){
				modelMap.addAttribute("playHtml", "mp4");
			}else if(StringUtils.isNotBlank(videoUrl) && videoUrl.indexOf("flv") > 0){
				modelMap.addAttribute("playHtml", "flv");
			}else{
				modelMap.addAttribute("playHtml", "");
			}
			
			modelMap.addAttribute("currentNav", article.getCatId().toString());
			
			
			ae.clear();
			ae.createCriteria()
			.andCatIdEqualTo(article.getCatId())
			.andStoreIdEqualTo(store_id)
			.andIsHotEqualTo(true);
//			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(4);
			List<EHaiArticle> listArticleHot = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleHot", listArticleHot);
			
			
			HaiCartExample cartExample = new HaiCartExample();
			cartExample.createCriteria()
			.andSessionIdEqualTo(s_encode)
			.andArticleIdEqualTo(id)
			.andExtensionCodeEqualTo("video");
			Long c = haiCartMapper.countByExample(cartExample);
			if(c == 0){//访问数量
				HaiCartWithBLOBs cart = new HaiCartWithBLOBs();
				cart.setSessionId(s_encode);
				cart.setArticleId(id);
				cart.setExtensionCode("video");
				cart.setUserId(0L);
				cart.setGoodsId(0L);
				cart.setGoodsName("");
				cart.setMarketPrice(0);
				cart.setGoodsPrice(0);
				cart.setGoodsNumber(0);
				cart.setGoodsSn("");
				
				cart.setGoodsAttr("");
				cart.setStoreId(store_id);
				cart.setParentUserId(0L);//来源分销的用户
				cart.setAgencyId(0);
				cart.setProductId(0L);
				cart.setIsReal(true);
				cart.setParentId(0L);
				cart.setRecType(true);
				cart.setIsGift(Short.parseShort("0"));
				cart.setIsShipping(true);
				cart.setCanHandsel(Byte.valueOf("0"));
				
				haiCartMapper.insert(cart);
				
				article.setReadCount((article.getReadCount()==null?0:article.getReadCount())+1);
				
				eHaiArticleMapper.updateByPrimaryKey(article);
			}
			
			modelMap.addAttribute("article", article);
			modelMap.addAttribute("video_transfer_website", video_transfer_website);
			
			
			//上一篇
			ae.clear();
			ae.createCriteria()
			.andArticleIdLessThan(article.getArticleId())
			.andCatIdEqualTo(article.getCatId())
			.andStoreIdEqualTo(store_id);
			ae.setOrderByClause("article_id asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(1);
			List<EHaiArticle> listArticlePre = eHaiArticleMapper.selectByExample(ae);
			if(listArticlePre!=null && listArticlePre.size() > 0) {
				modelMap.addAttribute("pre_article", listArticlePre.get(0));
			}
			
			
			
			//下一篇
			ae.clear();
			ae.createCriteria()
			.andArticleIdGreaterThan(article.getArticleId())
			.andCatIdEqualTo(article.getCatId())
			.andStoreIdEqualTo(store_id);
			ae.setOrderByClause("article_id asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(1);
			List<EHaiArticle> listArticleNext = eHaiArticleMapper.selectByExample(ae);
			if(listArticleNext!=null && listArticleNext.size() > 0) {
				modelMap.addAttribute("next_article", listArticleNext.get(0));
			}
			
			
			
			return "/media/"+modal+"/play";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	
	
	
	@RequestMapping("/live.lv")
	public String live(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response){
		String s_encode = (String) request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		modelMap.addAttribute("video_url_website", video_url_website);
		this.v_common(modelMap,request);
		
		EHaiArticleCatExample ace = new EHaiArticleCatExample();
		ace.createCriteria().andStoreIdEqualTo(store_id);
		ace.setOrderByClause("sort_order asc");
		List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
//		System.out.println("listArticleCat.size():"+listArticleCat.size());
		modelMap.addAttribute("listArticleCat", listArticleCat);
		
		modelMap.addAttribute("currentNav", "index");
		modelMap.addAttribute("video_url_website", video_url_website);
//		List<EHaiArticleCat> list=eHaiArticleCatMapper.hai_article_cat_list_zhibo();
//		modelMap.addAttribute("list", list);
//		System.out.println(list.get(0).getImages());
		
		
		HaiShopConfigExample example = new HaiShopConfigExample();
		HaiShopConfigExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andIsvoidEqualTo("1");
//		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiShopConfig> list = haiShopConfigMapper.selectByExampleWithBLOBs(example);
		
		for (HaiShopConfig haiShopConfig : list) {
			modelMap.addAttribute( haiShopConfig.getCode() + "_icon", haiShopConfig.getValue());
			modelMap.addAttribute( haiShopConfig.getCode() + "_text", haiShopConfig.getCodeText());
		}
		
		
		
		return "/media/web/live";
		
	}

	
	
	@RequestMapping("/search___{search}.lv")
	public String search(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "search") String search,
			@RequestParam(value = "page", required = false) Integer page){
		
		int len40 = 40;
		modelMap.addAttribute("video_url_website", video_url_website);
		this.v_common(modelMap,request);
		
		try{
			modal = "web";
			if(isWeiXin(request) || JudgeIsMoblie(request))modal = "h5";
			if(page == null)page = 1;
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			
			modelMap.addAttribute("search", search);
			
			EHaiArticleExample ae = new EHaiArticleExample();
			ae.createCriteria().andStoreIdEqualTo(store_id)
			.andTitleLike("%"+search+"%");
			
			ae.setOrderByClause("article_id desc");
			ae.setLimitStart(( page - 1 ) * len40);
			ae.setLimitEnd(len40);
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
			
			modelMap.addAttribute("listArticle", listArticle);
			
			Long count = eHaiArticleMapper.countByExample(ae);
			
			ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
			rm.setRows(listArticle);
			rm.setTotal(count);
			rm.setAction("search___"+search+".lv");
			rm.setPageSize(len40);
			rm.setCurrentPage(page);
			modelMap.addAttribute("rm", rm);
			
			
			
			return "/media/"+modal+"/search";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	    
		
		
	}
	
	
	/**
	 * 访问
	 * @param request
	 */
	private long visit(HttpServletRequest request) {
		String s_encode = (String) request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		
		HaiCartExample cartExample = new HaiCartExample();
		cartExample.createCriteria()
		.andSessionIdEqualTo(s_encode)
		.andExtensionCodeEqualTo("visit");
		Long c = haiCartMapper.countByExample(cartExample);
		if(c == 0){//访问数量
			HaiCartWithBLOBs cart = new HaiCartWithBLOBs();
			cart.setSessionId(s_encode);
			cart.setExtensionCode("visit");
			cart.setUserId(0L);
			cart.setGoodsId(0L);
			cart.setGoodsName("");
			cart.setMarketPrice(0);
			cart.setGoodsPrice(0);
			cart.setGoodsNumber(0);
			cart.setGoodsSn("");
			
			cart.setGoodsAttr("");
			cart.setStoreId(store_id);
			cart.setParentUserId(0L);//来源分销的用户
			cart.setAgencyId(0);
			cart.setProductId(0L);
			cart.setIsReal(true);
			cart.setParentId(0L);
			cart.setRecType(true);
			cart.setIsGift(Short.parseShort("0"));
			cart.setIsShipping(true);
			cart.setCanHandsel(Byte.valueOf("0"));
			
			haiCartMapper.insert(cart);
			c++;
		}
		
		
		cartExample.clear();
		cartExample.createCriteria()
		.andExtensionCodeEqualTo("visit");
		c = haiCartMapper.countByExample(cartExample);
		
		return c;
	}
	
	
}



