package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiArticleCatMapper;
import org.ehais.epublic.mapper.EHaiArticleMapper;
import org.ehais.epublic.model.EHaiArticleCat;
import org.ehais.epublic.model.EHaiArticleCatExample;
import org.ehais.model.TreeModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class EArticleCommonServiceImpl extends CommonServiceImpl{

	@Autowired
	protected EHaiArticleCatMapper eHaiArticleCatMapper;
	@Autowired
	protected EHaiArticleMapper eHaiArticleMapper;
	
	protected List<TreeModel> eTreeArticleCat(HttpServletRequest request,String module){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		EHaiArticleCatExample example = new EHaiArticleCatExample();
		EHaiArticleCatExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andModuleEqualTo(module);
		List<EHaiArticleCat> list = eHaiArticleCatMapper.selectByExample(example);
		
		TreeUtil tree = new TreeUtil();
		List<TreeModel> treeList = new ArrayList<TreeModel>();
		for (EHaiArticleCat haiArticleCat : list) {
			treeList.add(new TreeModel(haiArticleCat.getCatId().intValue(), haiArticleCat.getCatName(), haiArticleCat.getParentId().intValue(), 0,haiArticleCat.getCode(), null));
		}
		tree.setTreeList(treeList);
		tree.getTree(0);
		return tree.getTreeNewList();
	}
	
}
