package org.ehais.shop.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.ECommonMapper;
import org.ehais.shop.controller.api.include.ArticleIController;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ws")
public class ArticleWSController extends ArticleIController{

	@Autowired
	private ECommonMapper eCommonMapper;
	
	@ResponseBody
	@RequestMapping(value="/set_hot_article",method=RequestMethod.POST)
	public String set_hot_article(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "articleId", required = true) Integer articleId,
			@RequestParam(value = "hot", required = true) Integer hot ){
		
		ReturnObject<Integer> rm = new ReturnObject<Integer>();
		rm.setCode(0);
		try {
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			int c = eCommonMapper.commonUpdateBooleanValue("hai_article", "is_hot", hot.toString(), "article_id", articleId.toString(), store_id);
			
			rm.setCode(1);
			rm.setMsg("success");
			rm.setModel(c);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("error :", e);
		}
		return this.writeJson(rm);
	}
	
	
}
