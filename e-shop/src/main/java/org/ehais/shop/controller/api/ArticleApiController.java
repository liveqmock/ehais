package org.ehais.shop.controller.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EArticleModuleEnum;
import org.ehais.epublic.mapper.ECommonMapper;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.ehais.shop.controller.api.include.ArticleIController;
import org.ehais.tools.ReturnObject;
import org.ehais.util.Bean2Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class ArticleApiController extends ArticleIController{
	
	@Autowired
	private EHaiArticleCatMapper haiArticleCatMapper;
	@Autowired
	private EHaiArticleMapper haiArticleMapper;
	
	
	@ResponseBody
	@RequestMapping("/article_save")
	public String article_save(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EHaiArticle article,
			@RequestParam(value = "cat_name", required = true) String cat_name			
			){		
		try {
//			Bean2Utils.printEntity(article);
			//获取分类
			EHaiArticleCatExample acExp = new EHaiArticleCatExample();
			acExp.createCriteria()
			.andStoreIdEqualTo(article.getStoreId())
			.andCatNameEqualTo(cat_name)
			.andModuleEqualTo(EArticleModuleEnum.ARTICLE);
			List<EHaiArticleCat> articleCatList = haiArticleCatMapper.selectByExample(acExp);
			EHaiArticleCat ac = null;
			if(articleCatList == null || articleCatList.size() == 0){
				ac = new EHaiArticleCat();
				ac.setCatName(cat_name);
				ac.setStoreId(article.getStoreId());
				ac.setModule(EArticleModuleEnum.ARTICLE);
				ac.setIsValid(true);ac.setCatType(true);ac.setKeywords("");ac.setCatDesc("");ac.setSortOrder(Byte.valueOf("1"));ac.setShowInNav(true);ac.setParentId(0);
				haiArticleCatMapper.insert(ac);
			}else{
				ac = articleCatList.get(0);
			}
			
			article.setCatId(ac.getCatId());
			
			
			EHaiArticleExample aExp = new EHaiArticleExample();
			aExp.createCriteria()
			.andStoreIdEqualTo(article.getStoreId())
			.andModuleEqualTo(EArticleModuleEnum.ARTICLE)
			.andTitleEqualTo(article.getTitle())
			.andLinkEqualTo(article.getLink()) ;
			
			List<EHaiArticle> articleList = haiArticleMapper.selectByExample(aExp);
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
								
//				haiArticleMapper.insert(article);
				haiArticleMapper.insertSelective(article);
			}else{
				article.setArticleId(articleList.get(0).getArticleId());
				haiArticleMapper.updateByPrimaryKeySelective(article);
			}
			ReturnObject<EHaiArticle> rm = new ReturnObject<EHaiArticle>();
			rm.setModel(article);
			
			return this.writeJson(rm);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return null;
	}
	
	
	
	
	

}
