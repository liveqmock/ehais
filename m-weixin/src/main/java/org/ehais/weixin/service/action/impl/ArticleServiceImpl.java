package org.ehais.weixin.service.action.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.model.TreeModel;
import org.ehais.model.ExtendsField.ExtendsFieldsGroup;
import org.ehais.model.ExtendsField.ExtendsFieldsXml;
import org.ehais.tools.CriteriaObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.ehais.util.TreeUtil;
import org.ehais.weixin.mapper.HaiArticleCatMapper;
import org.ehais.weixin.mapper.HaiArticleMapper;
import org.ehais.weixin.model.HaiArticle;
import org.ehais.weixin.model.HaiArticleCat;
import org.ehais.weixin.model.HaiArticleCatExample;
import org.ehais.weixin.model.HaiArticleExample;
import org.ehais.weixin.service.action.ArticleService;
import org.ehais.weixin.service.action.ExtendsFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONArray;

@Service("articleService")
public class ArticleServiceImpl  extends ArticleCommonServiceImpl implements ArticleService{

	@Autowired
	private HaiArticleMapper haiArticleMapper;
	@Autowired
	private HaiArticleCatMapper haiArticleCatMapper;
	@Autowired
	private ExtendsFieldsService extendsFieldsService;
	
	public ReturnObject<HaiArticle> article_list(HttpServletRequest request,Integer store_id,Integer cat_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		if(page <= 0) page = 1;
		Integer start = (page - 1 ) * len;
		List<HaiArticle> list = haiArticleMapper.article_list(store_id , cat_id , start, len);
		HaiArticleExample example = new HaiArticleExample();
		HaiArticleExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		
		Integer total = haiArticleMapper.countByExample(example);
		ro.setCode(1);
		ro.setRows(list);
		ro.setTotal(total);
		
		Map<String, Object> map = new HashMap<String, Object>();
		HaiArticleCatExample exampleCat = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria cCat = exampleCat.createCriteria();
		cCat.andStoreIdEqualTo(store_id);
		List<HaiArticleCat> cat_list = haiArticleCatMapper.selectByExample(exampleCat);
		map.put("cat_list", cat_list);
		
		ro.setMap(map);		
		return ro;
	}
	
	public ReturnObject<HaiArticle> article_list_json(HttpServletRequest request,Integer store_id,Integer cat_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		if(page <= 0)page = 1;
		Integer start = (page - 1 ) * len;
		List<HaiArticle> list = haiArticleMapper.article_list_v2(store_id , cat_id , start, len);
		HaiArticleExample example = new HaiArticleExample();
		HaiArticleExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo(store_id);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		Integer total = haiArticleMapper.countByExample(example);
		ro.setCode(1);
		ro.setRows(list);
		ro.setTotal(total);
		
		Map<String, Object> map = new HashMap<String, Object>();
		HaiArticleCatExample exampleCat = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria cCat = exampleCat.createCriteria();
		cCat.andStoreIdEqualTo(store_id);
		List<HaiArticleCat> cat_list = haiArticleCatMapper.selectByExample(exampleCat);
		map.put("cat_list", cat_list);
		
		ro.setMap(map);		
		return ro;
	}

	public ReturnObject<HaiArticle> article_insert(HttpServletRequest request,Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		HaiArticleCatExample exampleCat = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria cCat = exampleCat.createCriteria();
//		cCat.andStoreIdEqualTo(store_id);
		exampleCat.CriteriaStoreId(cCat, this.storeIdCriteriaObject(request));
		
		List<HaiArticleCat> cat_list = haiArticleCatMapper.selectByExample(exampleCat);
		map.put("cat_list", cat_list);
		
		ro.setMap(map);	
		
		ro.setCode(1);
		return ro;
	}
	
	public ReturnObject<HaiArticle> article_insert_submit(HttpServletRequest request,HaiArticle model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> rm = new ReturnObject<HaiArticle>();
		rm.setCode(0);
		if(model.getTitle() == null || model.getTitle().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		
		
		
		CriteriaObject co = this.storeIdCriteriaObject(request);
		if(co.getRoleType().equals("user")){
			model.setUserId(co.getUserId());
			model.setStoreId(co.getUserId().intValue());
		}else{
			model.setStoreId(co.getStoreId());
		}
		
		
		HaiArticleExample example = new HaiArticleExample();
		HaiArticleExample.Criteria c = example.createCriteria();
		HaiArticleCat articleCat = null;
		//获取分类信息是否存在
		if(model.getCatId() != null && model.getCatId() > 0){
			c.andCatIdEqualTo(model.getCatId());
			//检查分类是否存在
			HaiArticleCatExample catExample = new HaiArticleCatExample();
			HaiArticleCatExample.Criteria catC = catExample.createCriteria();
			catExample.CriteriaStoreId(catC, co);
			catC.andCatIdEqualTo(model.getCatId());
			
			List<HaiArticleCat> articleCatList = haiArticleCatMapper.selectByExample(catExample);
			if(articleCatList == null || articleCatList.size() == 0 || articleCatList.size() > 1){
				rm.setMsg("不存在此分类信息");
				return rm;
			}
			articleCat = articleCatList.get(0);
		}
		
		
		
		
		c.andTitleEqualTo(model.getTitle());
		example.CriteriaStoreId(c, co);
		
		
		
		int count = haiArticleMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}
		
		int code = haiArticleMapper.insertSelective(model);
		//开始添加扩展表信息
		
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiArticle> article_update(HttpServletRequest request,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		HaiArticleCatExample exampleCat = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria cCat = exampleCat.createCriteria();
//		cCat.andStoreIdEqualTo(store_id);
		exampleCat.CriteriaStoreId(cCat, this.storeIdCriteriaObject(request));
		List<HaiArticleCat> cat_list = haiArticleCatMapper.selectByExample(exampleCat);
		map.put("cat_list", cat_list);
		
		ro.setMap(map);	
		
		HaiArticle model = haiArticleMapper.selectByPrimaryKey(key);
		ro.setCode(1);
		ro.setModel(model);
		return ro;
	}
	
	public ReturnObject<HaiArticle> article_update_submit(HttpServletRequest request,HaiArticle model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		HaiArticleExample example = new HaiArticleExample();
		HaiArticleExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		c.andStoreIdEqualTo(model.getStoreId());
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		if(store_id != null && store_id > 0){
//			c.andStoreIdEqualTo(store_id);
			model.setStoreId(store_id);
			model.setUserId(null);
		}else{
			Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
			if(user_id != null && user_id > 0){
//				c.andUserIdEqualTo(user_id);
				model.setUserId(user_id);
			}
			model.setStoreId(null);
		}
		
		c.andArticleIdEqualTo(model.getArticleId());
		int code = haiArticleMapper.updateByExampleSelective(model, example);
		ro.setCode(code);
		ro.setMsg("编辑成功");
		
//		WpPublicWithBLOBs wp = this.getWpPublic(model.getStoreId());
//		AccessToken token = WeiXinUtil.getAccessToken(model.getStoreId(), wp.getAppid(), wp.getSecret());
//		
//		String reqData = WeiXinUtil.mass_sendall(token.getToken(), true, 0, "image", "5tJKkfmy1aGksLu4T3NNVQMvfecJl9sVn7_5Dmxz6fS9iAzOsD741tDUR3APAW3U", model.getDescription());
//		System.out.println(reqData);
		
		return ro;
	}

	public ReturnObject<HaiArticle> article_find(HttpServletRequest request,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		HaiArticleCatExample exampleCat = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria cCat = exampleCat.createCriteria();
		cCat.andStoreIdEqualTo(store_id);
		List<HaiArticleCat> cat_list = haiArticleCatMapper.selectByExample(exampleCat);
		map.put("cat_list", cat_list);
		
		ro.setMap(map);	
		
		HaiArticle model = haiArticleMapper.selectByPrimaryKey(key);
		ro.setCode(1);
		ro.setModel(model);
		return ro;
	}

	public ReturnObject<HaiArticle> article_delete(HttpServletRequest request,Integer store_id,Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		HaiArticleExample example = new HaiArticleExample();
		HaiArticleExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andArticleIdEqualTo(key);
		int code = haiArticleMapper.deleteByExample(example);
		ro.setCode(code);
		ro.setMsg("删除成功");
		return ro;
	}

	public HaiArticleCat article_cat(HttpServletRequest request,Integer key) throws Exception {
		// TODO Auto-generated method stub
		return haiArticleCatMapper.selectByPrimaryKey(key.shortValue());
	}

	public HaiArticle article(HttpServletRequest request,Integer key) throws Exception {
		// TODO Auto-generated method stub
		return haiArticleMapper.selectByPrimaryKey(key);
	}
	
	public HaiArticle article_code(HttpServletRequest request,Integer store_id,String code) throws Exception {
		// TODO Auto-generated method stub
		return haiArticleMapper.article_code(store_id,code);
	}

	public ReturnObject<HaiArticle> article_code_list(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		Integer start = (page - 1 ) * len;
		List<HaiArticle> list = haiArticleMapper.article_code_list(store_id , start, len);
		
		HaiArticleExample example = new HaiArticleExample();
		HaiArticleExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andCodeIsNotNull();
		Integer total = haiArticleMapper.countByExample(example);
		ro.setCode(1);
		ro.setRows(list);
		ro.setTotal(total);
		
			
		return ro;
	}
	
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request , HaiArticle model) throws IOException{
		//读取article_user.xml的路径
		String file_path = "";
		if(request.getServerName().equals("localhost")){
			file_path = request.getRealPath("").replace("webapp", "");
			file_path += "/resources/config/article_user.xml";
		}else{
			file_path = request.getRealPath("/WEB-INF/classes/config/article_user.xml");
		}	
		//判断对象是否存在
		String xmlContent = FSO.BufferedReader(file_path);
		XStream xStream = new XStream();
		xStream.processAnnotations(ExtendsFieldsXml.class);
		xStream.processAnnotations(ExtendsFieldsGroup.class);
		xStream.processAnnotations(BootStrapModel.class);
		
		ExtendsFieldsXml atxml = (ExtendsFieldsXml) xStream.fromXML(xmlContent);
		List<ExtendsFieldsGroup> group = atxml.getGroup();
		for (ExtendsFieldsGroup extendsTypeGroup : group) {
			System.out.println(extendsTypeGroup.getRole()+" "+ extendsTypeGroup.getTable());
			List<BootStrapModel> field = extendsTypeGroup.getField();
			for (BootStrapModel bootStrapModel : field) {
				System.out.println("==="+bootStrapModel.getField_name());
			}
		}
		
		
		//如果对象不存在 || (对象存在 && 对象此值也存在的)就加入此项
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		TreeUtil tree = treeArticleCat(request);
		List<TreeModel> treeCat = tree.getTreeNewList();
		
		bootStrapList.add(new BootStrapModel("hidden", "articleId", "", model.getArticleId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "title", "标题", model.getTitle(), "请输入标题", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "code", "编码", model.getCode(), "用于识别文章唯一的编码", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select_tree", "catId", "分类", model.getCatId(), "请输入", "", "", null,treeCat, 0));
		bootStrapList.add(new BootStrapModel("text", "description", "摘要", model.getDescription(), "请输入摘要", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textarea", "content", "内容", model.getContent(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "author", "", model.getAuthor(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "authorEmail", "", model.getAuthorEmail(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "keywords", "关键字", model.getKeywords(), "请输入关键字", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "articleType", "", model.getArticleType(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isOpen", "", model.getIsOpen(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "addTime", "", model.getAddTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "fileUrl", "", model.getFileUrl(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "openType", "", model.getOpenType(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "link", "链接", model.getLink(), "请输入链接", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "storeId", "", model.getStoreId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "articleImages", "图片", model.getArticleImages(), "图片尺寸200*200", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "videoUrl", "", model.getVideoUrl(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "goodsId", "", model.getGoodsId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "chaId", "", model.getChaId(), "请输入", "", "", null, 0));

		//以下是扩展信息使用
		JSONArray json = JSONArray.fromObject(tree.getTreeList());
		bootStrapList.add(new BootStrapModel("hidden", "catIdList", "", json.toString().replace("\"", "'"), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("hidden", "table", "", "hai_article", "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("hidden", "tableId", "", model.getArticleId(), "请输入", "", "", null, 0));
		
		return bootStrapList;
	}
	
	

	public ReturnObject<HaiArticle> article_insert_v2(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> rm = new ReturnObject<HaiArticle>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiArticle model = new HaiArticle();
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiArticle> article_update_v2(
			HttpServletRequest request, Integer key) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		Map<String, Object> map = new HashMap<String, Object>();
		HaiArticleCatExample exampleCat = new HaiArticleCatExample();
		HaiArticleCatExample.Criteria cCat = exampleCat.createCriteria();
		cCat.andStoreIdEqualTo(store_id);
		List<HaiArticleCat> cat_list = haiArticleCatMapper.selectByExample(exampleCat);
		map.put("cat_list", cat_list);
		
		ro.setMap(map);	
		
		HaiArticle model = haiArticleMapper.selectByPrimaryKey(key);
		ro.setBootStrapList(this.formatBootStrapList(request,model));
		ro.setCode(1);
		return ro;
	}

	public ReturnObject<HaiArticle> article_list_by_catcode(HttpServletRequest request,Integer store_id,
			String code, Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiArticle> ro = new ReturnObject<HaiArticle>();
		Integer start = (page - 1 ) * len;
		List<HaiArticle> list = haiArticleMapper.article_list_by_catcode(store_id , code , start, len);
		
		
		ro.setCode(1);
		ro.setRows(list);
		
			
		return ro;
	}

}
