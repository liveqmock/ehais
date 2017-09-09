package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EArticleModuleEnum;
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
import org.ehais.shop.model.HaiArticleGoods;
import org.ehais.shop.model.HaiArticleGoodsExample;
import org.ehais.shop.model.HaiForumUser;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.WArticleGoods;
import org.ehais.shop.service.ArticleService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	

	public ReturnObject<EHaiArticle> article_list_cid(Integer store_id, Integer cat_id,Integer page,Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		Integer start = (((page == null)?1:page) - 1 ) * len;
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		if(cat_id > 0)c.andCatIdEqualTo(cat_id);
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<EHaiArticle> list = eHaiArticleMapper.selectByExample(example);
		rm.setTotal(eHaiArticleMapper.countByExample(example));
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}
	
	
	public ReturnObject<EHaiArticle> article_list_code(Integer store_id, String cat_code,Integer page,Integer len) throws Exception {
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

	public ReturnObject<EHaiArticle> article_info(Integer store_id, Integer article_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		EHaiArticle model = eHaiArticleMapper.get_hai_article_info(store_id, article_id);
		rm.setModel(model);
		rm.setCode(1);
		return rm;
	}


	@Override
	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request, EConditionObject condition,
			Integer cat_id, String title) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		if(cat_id > 0)c.andCatIdEqualTo(cat_id);
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
	
	
public ReturnObject<EHaiArticle> article_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiArticle> article_list_json(HttpServletRequest request,EConditionObject condition) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
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
	
	private List<EHaiArticleCat> articleCatList(HttpServletRequest request){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleCatExample acExample = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = acExample.createCriteria();
		c.andStoreIdEqualTo(store_id);
		List<EHaiArticleCat> acList = eHaiArticleCatMapper.selectByExample(acExample);
		return acList;
	}

	public ReturnObject<EHaiArticle> article_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticle model = new EHaiArticle();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleCatList", this.articleCatList(request));
		map.put("goodsList", this.goodsList(request));
		map.put("agId", 0);
		
		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<EHaiArticle> article_insert_submit(HttpServletRequest request,EHaiArticle model,Long goodsId)
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
		
		model.setModule(EArticleModuleEnum.ARTICLE);

		int code = eHaiArticleMapper.insertSelective(model);
		this.saveArticleGoods(request, model.getArticleId(), goodsId);//保存更新信息的商品
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiArticle> article_update(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andArticleIdEqualTo(articleId);
		c.andStoreIdEqualTo(store_id);
		List<EHaiArticle> list = eHaiArticleMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		EHaiArticle model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("articleCatList", this.articleCatList(request));
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
		HaiArticleGoodsExample example = new HaiArticleGoodsExample();
		HaiArticleGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(articleId);
		
		haiArticleGoodsMapper.deleteByExample(example);
		
		HaiArticleGoods ag = new HaiArticleGoods();
		ag.setArticleId(articleId);
		ag.setGoodsId(goodsId);
		ag.setStoreId((Integer)request.getSession(true).getAttribute(EConstants.SESSION_STORE_ID));
		haiArticleGoodsMapper.insert(ag);
		
//		c.andGoodsIdEqualTo(goodsId);
//		List<HaiArticleGoods> list = haiArticleGoodsMapper.selectByExample(example);
//		if(list == null || list.size() == 0){
//			
//		}else{
//			
//		}
	}
	
	public ReturnObject<EHaiArticle> article_update_submit(HttpServletRequest request,EHaiArticle model,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(model.getArticleId());
		c.andStoreIdEqualTo(store_id);

		long count = eHaiArticleMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		EHaiArticle bean = eHaiArticleMapper.selectByPrimaryKey(model.getArticleId());

bean.setCatId(model.getCatId());
bean.setCode(model.getCode());
bean.setModule(EArticleModuleEnum.ARTICLE);
bean.setTitle(model.getTitle());
bean.setContent(model.getContent());
bean.setAuthor(model.getAuthor());
bean.setAuthorEmail(model.getAuthorEmail());
bean.setArticleDate(model.getArticleDate());
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


		int code = eHaiArticleMapper.updateByExampleSelective(bean, example);
		this.saveArticleGoods(request, model.getArticleId(), goodsId);//保存更新信息的商品
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiArticle> article_info(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		c.andArticleIdEqualTo(articleId);
		c.andStoreIdEqualTo(store_id);
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


	public ReturnObject<EHaiArticle> article_find(HttpServletRequest request,Integer articleId)
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

	public ReturnObject<EHaiArticle> article_delete(HttpServletRequest request,Integer articleId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		EHaiArticleExample example = new EHaiArticleExample();
		EHaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andArticleIdEqualTo(articleId);

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
			Map<String,Object> map = SignUtil.getSid(sid,wp.getToken());
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
					eHaiArticle.setLink("/w_article_detail!"+SignUtil.setSid(
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
	
	
}
