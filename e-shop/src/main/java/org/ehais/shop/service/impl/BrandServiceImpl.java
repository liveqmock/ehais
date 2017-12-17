package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiBrandMapper;
import org.ehais.shop.model.HaiBrand;
import org.ehais.shop.model.HaiBrandExample;
import org.ehais.shop.service.BrandService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("brandService")
public class BrandServiceImpl  extends CommonServiceImpl implements BrandService{
	
	@Autowired
	private HaiBrandMapper haiBrandMapper;
	
	
	
	public ReturnObject<HaiBrand> brand_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiBrand> brand_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = (page - 1 ) * len;
		
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		example.setStart(start);
//		example.setLen(len);
		List<HaiBrand> list = haiBrandMapper.hai_brand_list_by_example(example);
		long total = haiBrandMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
//		Integer cc = haiBrandMapper.commonUnique("hai_brand", "brand_name", "易海司1");
//		System.out.println("commonUnique:"+cc);
		
		return rm;
	}

	public ReturnObject<HaiBrand> brand_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrand model = new HaiBrand();
//		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setBootStrapList(this.BootStrapXml(request, "brand.xml", model, "hai_brand", null));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiBrand> brand_insert_submit(HttpServletRequest request,HaiBrand model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		int code = haiBrandMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiBrand> brand_update(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrand model = haiBrandMapper.selectByPrimaryKey(brandId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiBrand> brand_update_submit(HttpServletRequest request,HaiBrand model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBrandIdEqualTo(model.getBrandId());
		int code = haiBrandMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiBrand> brand_find(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiBrand model = haiBrandMapper.selectByPrimaryKey(brandId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiBrand> brand_delete(HttpServletRequest request,Integer brandId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiBrand> rm = new ReturnObject<HaiBrand>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andBrandIdEqualTo(brandId);
		int code = haiBrandMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request , HaiBrand model) throws Exception{
		

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "brand.xml",model,"hai_brand",optionMap);
		
		
		return bootStrapList;
		
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
//		
//		bootStrapList.add(new BootStrapModel("hidden", "brandId", "", model.getBrandId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "brandName", "品牌名称", model.getBrandName(), "请输入品牌名称", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("images", "brandLogo", "品牌图片", model.getBrandLogo(), "请输入品牌图片，尺寸400*400", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("textarea", "brandDesc", "品牌描述", model.getBrandDesc(), "请输入品牌描述", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "siteUrl", "链接地址", model.getSiteUrl(), "请输入链接地址", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "sortOrder", "排序", model.getSortOrder(), "请输入排序", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isShow", "", model.getIsShow(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "storeId", "", model.getStoreId(), "请输入", "", "", null, 0));
//		
//		return bootStrapList;
	}

	@Override
	public List<HaiBrand> list(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		HaiBrandExample example = new HaiBrandExample();
		HaiBrandExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		c.andIsShowEqualTo(true);
		
		List<HaiBrand> list = haiBrandMapper.hai_brand_list_by_example(example);
		
		return list;
	}
	
	
	public enum BrandStatus{
		HOT("热门",1),
		BEST("最佳",2),
		RECOMMEND("推荐",3);
		
		private BrandStatus(String key,Integer value){
			this.key = key;
			this.value = value;
		}
		
		private String key;
		private Integer value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		
		
		
		
	}
	
	public static void main(String[] args) {
        System.out.println("EnumTest.FRI 的 value = " + BrandStatus.RECOMMEND.getValue());
    }
	
}











