package org.ehais.epublic.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.ECommonMapper;
import org.ehais.model.TreeModel;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/admin/baseapi/")
public class ETreeDataController extends CommonController{
	
	@Autowired
	private ECommonMapper eCommonMapper;

	@ResponseBody
	@RequestMapping("/category.tree.data")
	public String category_tree_data(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			List<TreeModel> list = eCommonMapper.commonTreeData("hai_category", "cat_id", "cat_name", "parent_id", store_id);
			rm.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	@ResponseBody
	@RequestMapping("/nav.tree.data")
	public String nav_tree_data(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			List<TreeModel> list = eCommonMapper.commonTreeData("hai_nav", "id", "name", "parent_id", store_id);
			rm.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	@ResponseBody
	@RequestMapping("/article.cat.tree.data")
	public String article_cat_tree_data(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "module", required = true) String module,
			@RequestParam(value = "classify", required = true) String classify) {
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			List<TreeModel> list = eCommonMapper.commonModuleClassifyTreeData("hai_article_cat", "cat_id", "cat_name", "parent_id", store_id,module,classify);
			rm.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	@ResponseBody
	@RequestMapping("/module.tree.data")
	public String module_tree_data(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			List<TreeModel> list = eCommonMapper.commonTreeData("hai_article_cat", "cat_id", "cat_name", "parent_id", store_id);
			rm.setRows(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	
}
