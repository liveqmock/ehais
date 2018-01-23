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
import org.ehais.epublic.model.EHaiAd;
import org.ehais.epublic.model.EHaiAdExample;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.model.HaiCart;
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

import net.sf.json.JSONArray;
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
	
	
	protected String video_transfer_website = ResourceUtil.getProValue("video.transfer.website");
	
	
	private Integer store_id = 1;
	private String modal = "web";
	private Integer len  = 4;
	private Integer hot_len  = 9;
	
	private void v_common(ModelMap modelMap,HttpServletRequest request) {
		store_id = 1;
		modelMap.addAttribute("logo", "logo");
		modelMap.addAttribute("company", "广州城市职业学院");			
		String serverName = request.getServerName();
		if(serverName.equals("gjs.ehais.org") || serverName.equals("gmedia.ehais.com")) {
			store_id = 2;
			modelMap.addAttribute("logo", "g_logo");
			modelMap.addAttribute("company", "广东技术师范学院");
		}
	}

	@RequestMapping("/index.do")
	public String index(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			modelMap.addAttribute("currentNav", "index");
			
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
			}
			adExample.setOrderByClause("ad_id desc");
			List<EHaiAd> adList = eHaiAdMapper.selectByExample(adExample);
			modelMap.addAttribute("adList", adList);
			
			
			EHaiArticleExample ae = new EHaiArticleExample();
			
			//推荐的
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(hot_len);
			List<EHaiArticle> listArticleIndex = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleIndex", listArticleIndex);
			
			Map<String, List<EHaiArticle>> mapArticle = new HashMap<String, List<EHaiArticle>>();
			//分类
			for (EHaiArticleCat c : listArticleCat) {
				ae.clear();
				ae.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(c.getCatId());
				ae.setOrderByClause("sort asc");
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
			ae.setOrderByClause("sort asc,article_id desc");
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
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
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
			List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
			
			if(listArticle == null || listArticle.size() == 0){
				return this.errorJump(modelMap, "空数据");
			}
			
			EHaiArticle article =  listArticle.get(0);
			
			
			
			String videoUrl = article.getVideoUrl();
			if(StringUtils.isNotBlank(videoUrl) && videoUrl.indexOf("mp4") > 0){
				modelMap.addAttribute("playHtml", "mp4");
			}else if(StringUtils.isNotBlank(videoUrl) && videoUrl.indexOf("flv") > 0){
				modelMap.addAttribute("playHtml", "flv");
			}else{
				modelMap.addAttribute("playHtml", "");
			}
			
			modelMap.addAttribute("currentNav", listArticle.get(0).getCatId().toString());
			
			
			ae.clear();
			ae.createCriteria()
			.andStoreIdEqualTo(store_id)
			.andOpenTypeEqualTo(Short.valueOf("1"));
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
			
			return "/media/"+modal+"/play";
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}
	}
	
	
	
	 

	
}



