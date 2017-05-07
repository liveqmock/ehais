package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.TreeModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.CriteriaObject;
import org.ehais.util.TreeUtil;
import org.ehais.weixin.mapper.HaiArticleCatMapper;
import org.ehais.weixin.model.HaiArticleCat;
import org.ehais.weixin.model.HaiArticleCatExample;
import org.ehais.weixin.service.wx.impl.WeiXinCommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class ArticleCommonServiceImpl extends WeiXinCommonServiceImpl{

	@Autowired
	protected HaiArticleCatMapper haiArticleCatMapper;
	
	
	protected TreeUtil treeArticleCat(HttpServletRequest request){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);

		HaiArticleCatExample example = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria c = example.createCriteria();
		
		CriteriaObject co = this.storeIdCriteriaObject(request);
		example.CriteriaStoreId(c, co);
		
		List<HaiArticleCat> list = haiArticleCatMapper.hai_article_cat_list_by_example(example);
		
		TreeUtil tree = new TreeUtil();
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		for (HaiArticleCat haiArticleCat : list) {
			treeList.add(new TreeModel(haiArticleCat.getCatId().intValue(), haiArticleCat.getCatName(), haiArticleCat.getParentId().intValue(), 0, haiArticleCat.getCode(),null));
		}
		tree.setTreeList(treeList);
		tree.getTree(0);
		return tree;
	}
	
}
