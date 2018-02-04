package org.ehais.shop.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.shop.controller.api.include.CategoryIController;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/api")
public class CategoryApiController extends CategoryIController{
	
	@Autowired
	protected HaiCategoryMapper haiCategoryMapper;
	
	@ResponseBody
	@RequestMapping("/category_save")
	public String category(ModelMap modelMap, 
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam(value = "store_id", required = true) Integer store_id,
			@RequestParam(value = "json", required = true) String json){
		try{
			Gson gson = new Gson();
			List<HaiCategoryEntity> list = gson.fromJson(json, new TypeToken<List<HaiCategoryEntity>>(){}.getType());  
			for (HaiCategoryEntity haiCategory : list) {
				this.saveCategory(store_id,haiCategory,0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
	}
	
	
	private void saveCategory(Integer store_id,HaiCategoryEntity haiCategory,Integer parentId){
		Integer catId = null;
		HaiCategoryExample example = new HaiCategoryExample();
		HaiCategoryExample.Criteria c = example.createCriteria();
		c.andCatNameEqualTo(haiCategory.getCatName().trim());
		c.andStoreIdEqualTo(store_id);
		if(StringUtils.isNotBlank(haiCategory.getCategoryUrl()))
			c.andCategoryUrlEqualTo(haiCategory.getCategoryUrl().trim());

		haiCategory.setParentId(parentId);
		haiCategory.setStoreId(store_id);
		haiCategory.setIsShow(true);
		
		List<HaiCategory> listModel = haiCategoryMapper.selectByExample(example);
			//如果数据库不存在这样的分类名，并且 url 不存在，则将分类插入到数据库中
		if(listModel == null || listModel.size() == 0){
			haiCategoryMapper.insertSelective(haiCategory);
			catId = haiCategory.getCatId();
			//否则更新数据库的信息，将 catId 设置为 null， 不改变它的值
		}else{
			catId = listModel.get(0).getCatId();
			haiCategory.setCatId(null);
			haiCategoryMapper.updateByExampleSelective(haiCategory, example);
		}
		
		//如果这个分类还有子分类，在递归遍历分类
		if(haiCategory.getChildren()!=null && haiCategory.getChildren().size()>0){
			for (HaiCategoryEntity haiCategory2 : haiCategory.getChildren()) {
				this.saveCategory(store_id,haiCategory2, catId);
			}
		}
		
	}
	
	
}
