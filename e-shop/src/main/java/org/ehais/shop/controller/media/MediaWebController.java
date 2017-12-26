package org.ehais.shop.controller.media;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticle;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.epublic.model.EHaiArticleExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MediaWebController extends CommonController{
	
	@Autowired
	private EHaiArticleMapper eHaiArticleMapper;
	@Autowired
	private EHaiArticleCatMapper eHaiArticleCatMapper;
	
	private Integer store_id = 27;

	@RequestMapping("/index.php")
	public String login(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
			String modal = "web";
			if(isWeiXin(request))modal = "h5";
			
			EHaiArticleCatExample ace = new EHaiArticleCatExample();
			ace.createCriteria().andStoreIdEqualTo(store_id);
			ace.setOrderByClause("sort_order asc");
			List<EHaiArticleCat> listArticleCat = eHaiArticleCatMapper.selectByExample(ace);
			modelMap.addAttribute("listArticleCat", listArticleCat);
			
			EHaiArticleExample ae = new EHaiArticleExample();
			
			//推荐的视频
			ae.createCriteria().andStoreIdEqualTo(store_id).andOpenTypeEqualTo(Short.valueOf("1"));
			ae.setOrderByClause("sort asc");
			ae.setLimitStart(0);
			ae.setLimitEnd(9);
			List<EHaiArticle> listArticleHot = eHaiArticleMapper.selectByExample(ae);
			modelMap.addAttribute("listArticleIndex", listArticleHot);
			
			Map<String, List<EHaiArticle>> mapArticle = new HashMap<String, List<EHaiArticle>>();
			//分类视频
			for (EHaiArticleCat c : listArticleCat) {
				ae.clear();
				ae.createCriteria().andStoreIdEqualTo(store_id).andCatIdEqualTo(c.getCatId());
				ae.setOrderByClause("sort asc");
				ae.setLimitStart(0);
				ae.setLimitEnd(4);
				List<EHaiArticle> listArticle = eHaiArticleMapper.selectByExample(ae);
				mapArticle.put(c.getCatId().toString(), listArticle);
			}
			modelMap.addAttribute("mapArticle", mapArticle);
			
			return "/media/"+modal+"/index";
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("article", e);
			return this.errorJump(modelMap, e.getMessage());
		}		
	}
	
}
