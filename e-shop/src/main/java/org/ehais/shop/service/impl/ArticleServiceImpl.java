package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EArticleClassifyEnum;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.enums.EWXMediaTypeEnum;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.epublic.service.EWPPublicService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiArticleGoodsMapper;
import org.ehais.shop.mapper.HaiForumUserMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.WArticleGoodsMapper;
import org.ehais.shop.mapper.weixin.WxMediaMapper;
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiForumUser;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.WArticleGoods;
import org.ehais.shop.model.weixin.WxMedia;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EHttpClientUtil;
import org.ehais.util.FSO;
import org.ehais.util.SignUtil;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.WeiXinMPNews;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("articleService")
public class ArticleServiceImpl  extends CommonServiceImpl implements ArticleService{

	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiArticleGoodsMapper haiArticleGoodsMapper;
	@Autowired
	private WArticleGoodsMapper wArticleGoodsMapper;
	@Autowired
	protected EWPPublicService eWPPublicService;
	@Autowired
	private HaiForumUserMapper haiForumUserMapper;
	@Autowired
	private WxMediaMapper wxMediaMapper;
	

	public ReturnObject<EHaiArticle> article_list_cid(HttpServletRequest request,String moduleEnum,Integer store_id, Integer cat_id,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		if(store_id == null || store_id == 0){
			store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		}
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		if(cat_id != null && cat_id > 0)c.andCatIdEqualTo(cat_id);
		if(cat_id != null && cat_id == 0)c.andIsHotEqualTo(true);
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<EHaiArticle> list = eHaiArticleMapper.selectByExample(example);
		rm.setTotal(eHaiArticleMapper.countByExample(example));
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}
	
	
	public ReturnObject<EHaiArticle> article_list_code(String moduleEnum,Integer store_id, String cat_code,Integer page,Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		Integer start = (((page == null)?1:page) - 1 ) * len;
		List<EHaiArticle> list = eHaiArticleMapper.article_list_by_catcode(store_id, cat_code, start, len);
		
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}
	
	private List<HaiGoods> goodsList(HttpServletRequest request){
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoods> list = haiGoodsMapper.selectByExample(example);
		return list;
	}

	public ReturnObject<EHaiArticle> article_info(String moduleEnum,Integer store_id, Integer article_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		EHaiArticle model = eHaiArticleMapper.get_hai_article_info(store_id, article_id);
		rm.setModel(model);
		rm.setCode(1);
		return rm;
	}


	@Override
	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,String moduleEnum, EConditionObject condition,
			Integer cat_id, String title) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andModuleEqualTo(moduleEnum);
		if(cat_id != null && cat_id > 0)c.andCatIdEqualTo(cat_id);
		if(StringUtils.isNotEmpty(title))c.andTitleLike("%"+title+"%");
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("article_id desc");
		List<EHaiArticle> list = eHaiArticleMapper.selectByExample(example);
		long total = eHaiArticleMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		EHaiArticleCatExample examplecat = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria ccat = examplecat.createCriteria();
		examplecat.CriteriaStoreId(ccat, this.storeIdCriteriaObject(request));
		List<EHaiArticleCat> listCat = eHaiArticleCatMapper.selectByExample(examplecat);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("listCat", listCat);
		rm.setMap(map);
		
		return rm;
	}
	
	
public ReturnObject<EHaiArticle> article_list(HttpServletRequest request,String moduleEnum) throws Exception{
		
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,String moduleEnum,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andModuleEqualTo(moduleEnum);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		List<EHaiArticle> list = eHaiArticleMapper.selectByExample(example);
		long total = eHaiArticleMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	private List<EHaiArticleCat> articleCatList(HttpServletRequest request,String moduleEnum){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample acExample = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = acExample.createCriteria();
		acExample.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		List<EHaiArticleCat> acList = eHaiArticleCatMapper.selectByExample(acExample);
		return acList;
	}

	public ReturnObject<EHaiArticle> article_insert(HttpServletRequest request,String moduleEnum)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticle model = new EHaiArticle();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleCatList", this.articleCatList(request,moduleEnum));
		map.put("goodsList", this.goodsList(request));
		map.put("agId", 0);
		
		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,String moduleEnum,EHaiArticle model,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		c.andStoreIdEqualTo(store_id);
		long count = eHaiArticleMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}
		
		Date date = new Date();
//		model.setModule(EArticleModuleEnum.ARTICLE);
		model.setModule(moduleEnum);
		if(model.getArticleDate() == null)model.setArticleDate(date);
		model.setCreateDate(date);
		model.setUpdateDate(date);

		int code = eHaiArticleMapper.insertSelective(model);
		if(goodsId != null && goodsId > 0)this.saveArticleGoods(request, model.getArticleId(), goodsId);//保存更新信息的商品
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiArticle> article_update(HttpServletRequest request,String moduleEnum,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(articleId);
//		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		
		
		List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticle model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleCatList", this.articleCatList(request,moduleEnum));
		map.put("goodsList", this.goodsList(request));
		map.put("agId", this.getAgId(request, model.getArticleId()));//查找关联商品
		
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	private Long getAgId(HttpServletRequest request,Integer articleId){
		HaiArticleGoodsExample example = new HaiArticleGoodsExample();
		HaiArticleGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(articleId);
		List<HaiArticleGoods> list = haiArticleGoodsMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0).getGoodsId();
		}
		return 0L;
	}
	
	private void saveArticleGoods(HttpServletRequest request,Integer articleId,Long goodsId){
		Integer store_id = (Integer)request.getSession(true).getAttribute(EConstants.SESSION_STORE_ID);
		HaiArticleGoodsExample example = new HaiArticleGoodsExample();
		HaiArticleGoodsExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andArticleIdEqualTo(articleId);
		
		haiArticleGoodsMapper.deleteByExample(example);
		
		if(goodsId != null && goodsId != 0){
			HaiArticleGoods ag = new HaiArticleGoods();
			ag.setArticleId(articleId);
			ag.setGoodsId(goodsId);
			ag.setStoreId(store_id);
			haiArticleGoodsMapper.insert(ag);
		}
		
	}
	
	public ReturnObject<EHaiArticle> article_update_submit(HttpServletRequest request,String moduleEnum,EHaiArticle model,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(model.getArticleId());
//		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		long count = eHaiArticleMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiArticle bean = eHaiArticleMapper.selectByPrimaryKey(model.getArticleId());
		Date date = new Date();
bean.setCatId(model.getCatId());
bean.setCode(model.getCode());
//bean.setModule(EArticleModuleEnum.ARTICLE);
bean.setTitle(model.getTitle());
bean.setContent(model.getContent());
bean.setAuthor(model.getAuthor());
bean.setAuthorEmail(model.getAuthorEmail());
if(model.getArticleDate()!=null){
	bean.setArticleDate(model.getArticleDate());
}else{
	bean.setArticleDate(date);
}
bean.setStartPublishDate(model.getStartPublishDate());
bean.setEndPublishDate(model.getEndPublishDate());
bean.setStartApplyDate(model.getStartApplyDate());
bean.setEndApplyDate(model.getEndApplyDate());
bean.setKeywords(model.getKeywords());
bean.setIsOpen(model.getIsOpen());
bean.setFileUrl(model.getFileUrl());
bean.setOpenType(model.getOpenType());
bean.setLink(model.getLink());
bean.setDescription(model.getDescription());
bean.setStoreId(model.getStoreId());
bean.setSort(model.getSort());
bean.setArticleThumb(model.getArticleThumb());
bean.setArticleImages(model.getArticleImages());
bean.setVideoUrl(model.getVideoUrl());
bean.setArticleTypeCode(model.getArticleTypeCode());
bean.setUserId(model.getUserId());
bean.setUpdateDate(new Date());
bean.setArticleLabel(model.getArticleLabel());
bean.setIsHot(model.getIsHot());
bean.setArticleSource(model.getArticleSource());//网络来源


		int code = eHaiArticleMapper.updateByExampleSelective(bean, example);
		if(goodsId != null && goodsId > 0)this.saveArticleGoods(request, model.getArticleId(), goodsId);//保存更新信息的商品
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiArticle> article_info(HttpServletRequest request,String moduleEnum,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andArticleIdEqualTo(articleId);
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticle model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<EHaiArticle> article_find(HttpServletRequest request,String moduleEnum,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		EHaiArticle model = eHaiArticleMapper.selectByPrimaryKey(articleId);
//		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiArticle> article_delete(HttpServletRequest request,String moduleEnum,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(articleId);
		c.andModuleEqualTo(moduleEnum);

		long count = eHaiArticleMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = eHaiArticleMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}


	@Override
	public ReturnObject<EHaiArticle> article_extends_list_json(HttpServletRequest request, String sid)
			throws Exception {
		// TODO Auto-generated method stub
		
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		if(user_id == null || user_id == 0){
			rm.setMsg("未登录");
			return rm;
		}
		Integer store_id = SignUtil.getUriStoreId(sid);
		if(store_id == 0){
			rm.setMsg("商码不正确");
			return rm;
		}
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			Map<String,Object> map = null;
			map = SignUtil.getSid(sid,wp.getToken());
			
			if(map == null){
				rm.setMsg("参数不正确");
				return rm;
			}
			if(user_id.longValue() != Long.parseLong(map.get("userId").toString())){
				rm.setMsg("用户不正确");
				return rm;
			}
			
			Map<String, Object> mapReturn = new HashMap<String,Object>();
			
			
			//读取文章信息
			EHaiArticle article = eHaiArticleMapper.selectByPrimaryKey(Integer.valueOf(map.get("articleId").toString()));
			
			
			List<HaiForumUser> listForum = haiForumUserMapper.listForumUser(
					Integer.valueOf(map.get("store_id").toString()), 
					Long.valueOf(map.get("articleId").toString()), 
					"hai_article");
			mapReturn.put("listForum", listForum);
			
			System.out.println(JSONArray.fromObject(listForum).toString());
			
			
			String keywords = article.getKeywords();
			if(StringUtils.isNotEmpty(keywords)){

				keywords = keywords.replaceAll("，", ",");//将中文的逗号也过滤一下
				
				StringBuffer sb = new StringBuffer();
				String[] sqlKeywords = keywords.split(",");
				for (String string : sqlKeywords) {
					sb.append(" keywords like '%"+string+"%' or");
				}
				keywords = sb.toString();
				if(keywords.length() > 0){
					keywords = keywords.substring(0,keywords.length() - 2);
				}
				
				List<WArticleGoods> listRecommend = wArticleGoodsMapper.listRecommendArticle(Integer.valueOf(map.get("store_id").toString()), 
						Integer.valueOf(map.get("articleId").toString()), 
						keywords, 0, 5);
				
				for (WArticleGoods eHaiArticle : listRecommend) {
					eHaiArticle.setLink("/w_article_goods!"+SignUtil.setSid(
							Integer.valueOf(map.get("store_id").toString()), 
							Integer.valueOf(map.get("agencyId").toString()), 
							Long.valueOf(map.get("parentId").toString()), 
							Long.valueOf(map.get("userId").toString()), 
							eHaiArticle.getArticleId(), 
							eHaiArticle.getGoodsId() == null ? 0 : eHaiArticle.getGoodsId(), 
							wp.getToken()));
				}
				
				mapReturn.put("listRecommend", listRecommend);
				
			}
			
			rm.setMap(mapReturn);
			
			rm.setCode(1);
			
			return rm;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}


	/**
	 * 微信群发消息功能
	 */
	@Override
	public ReturnObject<EHaiArticle> ehaisArticleSendGroupWeixin(HttpServletRequest request, String moduleEnum,
			Integer articleId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		//读取当前文章信息
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		String tmpFilename = null;
		try{
			WpPublicWithBLOBs wp = eWPPublicService.getWpPublic(store_id);
			EHaiArticleExample aExp = new EHaiArticleExample();
			EHaiArticleExample.Criteria c = aExp.createCriteria();
			aExp.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
			c.andModuleEqualTo(moduleEnum).andArticleIdEqualTo(articleId);
			List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(aExp);
			if(list == null || list.size() == 0){
				rm.setMsg("不存在文章编号");
				return rm;
			}
			EHaiArticle article = list.get(0);
			AccessToken accessToken = WeiXinUtil.getAccessToken(wp.getId(), wp.getAppid(), wp.getSecret());
			
			WxMedia mediaDetail = wxMediaMapper.getWxMediaDetail("hai_article", article.getArticleId().toString());
			if(mediaDetail == null){
				//***************************读取文章的media_id
				WeiXinMPNews wxNews = null;
				
				
				String content = article.getContent();
				//使用正则获取文章里面的图片
				Pattern p = Pattern.compile("<img.*src\\s*=\\s*(.*?)[^>]*?>", Pattern.CASE_INSENSITIVE);
			    Matcher m = p.matcher(content);
			    String quote = null;
			    String src = null;
			    Matcher matcher = null;
			    while (m.find()) {
			        quote = m.group();
			        matcher = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(quote);  
		            while (matcher.find()) { 
		            	src = matcher.group().substring(5, matcher.group().length() - 1);
		            	tmpFilename = request.getRealPath("/")+"eUploads/"+System.currentTimeMillis()+".jpg";
						EHttpClientUtil.downloadFile(src, tmpFilename);
						
		            	String reqUrl = WeiXinUtil.uploadImg(accessToken.getAccess_token(), tmpFilename);
		            	
		            	JSONObject json = JSONObject.fromObject(reqUrl);
		            	if(json.has("errcode")){
							rm.setMsg("媒体上传失败");
							return rm;
						}
		            	if(json.has("url")){
		            		content.replaceAll(src, json.getString("url"));
		            	}
		            	FSO.deletefile(tmpFilename);
		            	
		            }
		            
			    }
				
				
				if(StringUtils.isBlank(article.getArticleImages())){
					//如果无图片，直接调用文本消息
					wxNews = new WeiXinMPNews(
				    		article.getTitle(), 
				    		"",//media_id 
				    		article.getAuthor(),
							"",//来源网址
							content,
							(StringUtils.isNotBlank(article.getDescription()) ? (article.getDescription().length() > 64 ? article.getDescription().substring(0, 64) : article.getDescription() ):""), 
							"0") ;
				}else{
					//如果存在图片，判断是否存在med_id是否存在，如果 不存在，先获取meth
					WxMedia mediaPic = wxMediaMapper.getWxMediaPath(article.getArticleImages());
					if(mediaPic == null){
						///////////////////////////////读取图片的media_id start
						tmpFilename = request.getRealPath("/")+"eUploads/"+System.currentTimeMillis()+".jpg";
						EHttpClientUtil.downloadFile(article.getArticleImages(), tmpFilename);
						
						Map<String, String> fileMap = new HashMap<>(); 
						fileMap.put("media", tmpFilename);
						String reqData = WeiXinUtil.uploadMedia(accessToken.getAccess_token(), EWXMediaTypeEnum.IMAGE, null, fileMap);
						JSONObject json = JSONObject.fromObject(reqData);
						
						if(json.has("errcode")){
							rm.setMsg("媒体上传失败");
							return rm;
						}
						
						if(json.has("media_id")){
							String media_id = json.getString("media_id"); 
							mediaPic = new WxMedia();
							mediaPic.setMediaPath(article.getArticleImages());
							mediaPic.setMediaId(media_id);
							
							wxMediaMapper.insert(mediaPic);
						}
						
						
						FSO.deletefile(tmpFilename);
						///////////////////////////////读取图片的media_id end
					}
					
					wxNews = new WeiXinMPNews(
				    		article.getTitle(), 
				    		mediaPic.getMediaId(), 
				    		article.getAuthor(),
							"",//来源网址
							content,
							(StringUtils.isNotBlank(article.getDescription()) ? (article.getDescription().length() > 64 ? article.getDescription().substring(0, 64) : article.getDescription() ):""), 
							"1") ;
					
				}
				
				
				
				//群发消息
			    List<WeiXinMPNews> newsList = new ArrayList<WeiXinMPNews>();
			    
			    newsList.add(wxNews);
			    
				String upnewData = WeiXinUtil.uploadnews(accessToken.getAccess_token(), newsList);
				System.out.println(upnewData);
//				{"type":"news","media_id":"zqfJ2JqisIhA9gJVgI0mWMcvr5gxRxAXUe237wxhFbcZ9PhIxXH46WR-wuMFFhsR","created_at":1507987488}
				JSONObject json = JSONObject.fromObject(upnewData);
				if(json.has("errcode")){
					rm.setMsg("群发消息失败");
					return rm;
				}
				
				if(json.has("media_id")){
					String media_id = json.getString("media_id"); 
					mediaDetail = new WxMedia();
					mediaDetail.setMediaPath("");
					mediaDetail.setTableName("hai_article");
					mediaDetail.setTableId(article.getArticleId().toString());
					mediaDetail.setMediaId(media_id);
					
					wxMediaMapper.insert(mediaDetail);
				}
				
				//***************************读取文章的media_id end
			}
			
			
			//根据media_id 发送消息
			String sendallData = WeiXinUtil.mass_sendall(accessToken.getAccess_token(), true, null, EWXMediaTypeEnum.MPNEWS, mediaDetail.getMediaId(), "");
			System.out.println(sendallData);
//			{"msgtype":"mpnews","mpnews":{"media_id":"RXDSlQjXHofFNHv-5kFBuTC0VZtGy52ENviiJw1tZGh_RjfqsR-R3ey2oPe8lczh"},"filter":{"is_to_all":true}}
//			{"errcode":48008,"errmsg":"no permission for this msgtype hint: [LTnDna04222111]"}
			JSONObject json = JSONObject.fromObject(sendallData);
			if(json.has("errcode")){
				rm.setMsg("群发消息失败【"+json.getString("errmsg")+"】");
				return rm;
			}
			rm.setCode(1);
			rm.setMsg("群发消息成功");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return rm;
	}


	@Override
	public ReturnObject<EHaiArticle> article_module(HttpServletRequest request, String moduleEnum) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(moduleEnum);
		c.andClassifyEqualTo(EArticleClassifyEnum.SINGLE);
		List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(example);
		EHaiArticle model = null;
		if(list == null || list.size() == 0){
			model = new EHaiArticle();
			model.setModule(moduleEnum);
			model.setStoreId(store_id);
			model.setContent("");
			model.setClassify(EArticleClassifyEnum.SINGLE);
			eHaiArticleMapper.insertSelective(model);
		}else{
			model = list.get(0);
		}
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	@Override
	public EHaiArticle articleSave(EHaiArticleCat cate , EHaiArticle article) throws Exception {
		// TODO Auto-generated method stub
				
		article.setCatId(cate.getCatId());
		
		EHaiArticleExample aExp = new EHaiArticleExample();
		aExp.createCriteria()
		.andStoreIdEqualTo(article.getStoreId())
		.andModuleEqualTo(EArticleModuleEnum.ARTICLE)
		.andTitleEqualTo(article.getTitle())
		.andLinkEqualTo(article.getLink()) ;
		
		List<EHaiArticle> articleList = eHaiArticleMapper.selectByExample(aExp);
		if(articleList == null || articleList.size() == 0){
			Date date = new Date();
			article.setModule(EArticleModuleEnum.ARTICLE);
			if(article.getArticleDate() == null)article.setArticleDate(date);
			article.setCreateDate(date);
			article.setUpdateDate(date);
			article.setIsOpen(true);
			article.setOpenType(true);
			if(StringUtils.isNotBlank(article.getAuthor()))article.setAuthor("");
			if(StringUtils.isNotBlank(article.getAuthorEmail()))article.setAuthorEmail("");
			if(StringUtils.isNotBlank(article.getKeywords()))article.setKeywords("");
			if(StringUtils.isNotBlank(article.getFileUrl()))article.setFileUrl("");
							
//			haiArticleMapper.insert(article);
			eHaiArticleMapper.insertSelective(article);
		}else{
			article.setArticleId(articleList.get(0).getArticleId());
			eHaiArticleMapper.updateByPrimaryKeySelective(article);
		}
		
		
		return article;
	}
	
	
}
