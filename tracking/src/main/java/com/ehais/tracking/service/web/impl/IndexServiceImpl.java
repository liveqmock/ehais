package com.ehais.tracking.service.web.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.epublic.mapper.EHaiAdMapper;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiAd;
import org.ehais.epublic.model.EHaiAdExample;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.CriteriaObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Environments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.tracking.dao.QuestionnaireDao;
import com.ehais.tracking.entity.Questionnaire;
import com.ehais.tracking.service.web.IndexService;

@Service("indexService")
public class IndexServiceImpl extends CommonServiceImpl implements IndexService{

	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private QuestionnaireDao questionnaireDao;
	@Autowired
	private EHaiAdMapper eHaiAdMapper;
	@Autowired
	private Environments env;
	
	
	
	@Override
	public ReturnObject<Object> index(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		Map<String,Object> map = new HashMap<String, Object>();
		
		CriteriaObject co = this.storeIdCriteriaObject(request, env.getStore_id());
		//查找资讯分类
		EHaiArticleCatExample articleCatExample = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria aCC = articleCatExample.createCriteria();
		articleCatExample.CriteriaStoreId(aCC, co);
		articleCatExample.setLimitStart(0);
		articleCatExample.setLimitEnd(5);
		
		List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(articleCatExample);
		
		//查找资讯
		EHaiArticleExample articleExample = new EHaiArticleExample();
		EHaiArticleExample.Criteria aC = articleExample.createCriteria();
		articleExample.CriteriaStoreId(aC, co);
		articleExample.setLimitStart(0);
		articleExample.setLimitEnd(5);
		List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(articleExample);
		
		//查找广告
		EHaiAdExample adExample = new EHaiAdExample();
		EHaiAdExample.Criteria adC = adExample.createCriteria();
		adExample.CriteriaStoreId(adC, co);
		adExample.setLimitStart(0);
		adExample.setLimitEnd(5);
		List<EHaiAd> listAd = eHaiAdMapper.selectByExampleWithBLOBs(adExample);
		
		//查找最新问卷
		Map<String,Object> mapOrder = new HashMap<String, Object>();
		List<Questionnaire> listQuestionnaire = questionnaireDao.selectAll(Questionnaire.class, 1, 10, null,null, mapOrder);
		
		
		map.put("listAd", listAd);
		map.put("listArticleCat", listArticleCat);
		map.put("listArticle", listArticle);
		map.put("listQuestionnaire", listQuestionnaire);
		
		
		
		
		
		rm.setCode(1);
		rm.setMap(map);
		return rm;
	}

}
