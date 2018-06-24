package org.ehais.shop.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ehais.service.impl.CommonServiceImpl;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )

/**
@NotBlank(message = "分类编号不能为空")//cat_id
@NotBlank(message = "条形码不能为空")//goods_sn
@NotBlank(message = "商品名称不能为空")//goods_name
@NotBlank(message = "goods_name_style不能为空")//goods_name_style
@NotBlank(message = "click_count不能为空")//click_count
@NotBlank(message = "brand_id不能为空")//brand_id
@NotBlank(message = "provider_name不能为空")//provider_name
@NotBlank(message = "goods_number不能为空")//goods_number
@NotBlank(message = "goods_weight不能为空")//goods_weight
@NotBlank(message = "market_price不能为空")//market_price
@NotBlank(message = "销售价格不能为空")//shop_price
@NotBlank(message = "promote_price不能为空")//promote_price
@NotBlank(message = "warn_number不能为空")//warn_number
@NotBlank(message = "keywords不能为空")//keywords
@NotBlank(message = "goods_thumb不能为空")//goods_thumb
@NotBlank(message = "goods_img不能为空")//goods_img
@NotBlank(message = "original_img不能为空")//original_img
@NotBlank(message = "is_real不能为空")//is_real
@NotBlank(message = "extension_code不能为空")//extension_code
@NotBlank(message = "is_on_sale不能为空")//is_on_sale
@NotBlank(message = "is_alone_sale不能为空")//is_alone_sale
@NotBlank(message = "is_shipping不能为空")//is_shipping
@NotBlank(message = "integral不能为空")//integral
@NotBlank(message = "add_time不能为空")//add_time
@NotBlank(message = "排序不能为空")//sort_order
@NotBlank(message = "is_delete不能为空")//is_delete
@NotBlank(message = "is_best不能为空")//is_best
@NotBlank(message = "is_new不能为空")//is_new
@NotBlank(message = "is_hot不能为空")//is_hot
@NotBlank(message = "is_promote不能为空")//is_promote
@NotBlank(message = "is_special不能为空")//is_special
@NotBlank(message = "bonus_type_id不能为空")//bonus_type_id
@NotBlank(message = "last_update不能为空")//last_update
@NotBlank(message = "goods_type不能为空")//goods_type
@NotBlank(message = "seller_note不能为空")//seller_note
@NotBlank(message = "give_integral不能为空")//give_integral
@NotBlank(message = "rank_integral不能为空")//rank_integral

**/
/**
@NotBlank(message = "goods_id不能为空")//goods_id
@NotBlank(message = "attr_id不能为空")//attr_id
@NotBlank(message = "attr_value不能为空")//attr_value
@NotBlank(message = "attr_price不能为空")//attr_price

**/




@Service("haiGoodsService")
public class HaiGoodsServiceImpl  extends CommonServiceImpl implements HaiGoodsService{
	
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;

	@Autowired
	private HaiGoodsAttrMapper haiGoodsAttrMapper;

	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,EConditionObject condition,Integer goodsAttrId,String goodsName) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("last_update_date desc");
		if(goodsAttrId > 0 ) c.andGoodsAttrIdEqualTo(goodsAttrId);
		if(StringUtils.isNotEmpty(goodsName))c.andGoodsNameLike("%"+goodsName+"%");
		List<HaiGoods> list = haiGoodsMapper.selectByExample(example);
		long total = haiGoodsMapper.countByExample(example);



		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsattrList",this.goodsattrList(request));
		rm.setMap(map);


		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiGoodsWithBLOBs> goods_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsWithBLOBs model = new HaiGoodsWithBLOBs();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsattrList",this.goodsattrList(request));
		rm.setMap(map);


		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiGoodsWithBLOBs> goods_insert_submit(HttpServletRequest request,HaiGoodsWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		
		Date date = new Date();
		model.setCreateDate(date);
		model.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		model.setCreateAdminId(admin_id);
		model.setLastUpdateAdminId(admin_id);


		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		c.andGoodsNameEqualTo(model.getGoodsName());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiGoodsMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiGoodsMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiGoodsWithBLOBs> goods_update(HttpServletRequest request,Integer goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

/**
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(goodsId);
		long count = haiGoodsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsWithBLOBs model = haiGoodsMapper.selectByPrimaryKey(goodsId);
**/
		HaiGoodsWithBLOBs model = haiGoodsMapper.get_hai_goods_info(goodsId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsattrList",this.goodsattrList(request));
		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiGoodsWithBLOBs> goods_update_submit(HttpServletRequest request,HaiGoodsWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(model.getGoodsId());
		//c.andStoreIdEqualTo(store_id);

		long count = haiGoodsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiGoodsWithBLOBs bean = haiGoodsMapper.selectByPrimaryKey(model.getGoodsId());

bean.setCatId(model.getCatId());
bean.setGoodsSn(model.getGoodsSn());
bean.setGoodsCode(model.getGoodsCode());
bean.setGoodsName(model.getGoodsName());
bean.setGoodsNameStyle(model.getGoodsNameStyle());
bean.setClickCount(model.getClickCount());
bean.setBrandId(model.getBrandId());
bean.setProviderName(model.getProviderName());
bean.setGoodsNumber(model.getGoodsNumber());
bean.setGoodsWeight(model.getGoodsWeight());
bean.setMarketPrice(model.getMarketPrice());
bean.setShopPrice(model.getShopPrice());
bean.setCostPrice(model.getCostPrice());
bean.setPromotePrice(model.getPromotePrice());
bean.setPromoteStartDate(model.getPromoteStartDate());
bean.setPromoteEndDate(model.getPromoteEndDate());
bean.setWarnNumber(model.getWarnNumber());
bean.setKeywords(model.getKeywords());
bean.setGoodsBrief(model.getGoodsBrief());
bean.setGoodsDesc(model.getGoodsDesc());
bean.setActDesc(model.getActDesc());
bean.setAppDesc(model.getAppDesc());
bean.setGoodsThumb(model.getGoodsThumb());
bean.setGoodsImg(model.getGoodsImg());
bean.setOriginalImg(model.getOriginalImg());
bean.setIsReal(model.getIsReal());
bean.setExtensionCode(model.getExtensionCode());
bean.setIsOnSale(model.getIsOnSale());
bean.setIsAloneSale(model.getIsAloneSale());
bean.setIsShipping(model.getIsShipping());
bean.setIntegral(model.getIntegral());
bean.setAddTime(model.getAddTime());
bean.setSortOrder(model.getSortOrder());
bean.setIsDelete(model.getIsDelete());
bean.setIsBest(model.getIsBest());
bean.setIsNew(model.getIsNew());
bean.setIsHot(model.getIsHot());
bean.setIsPromote(model.getIsPromote());
bean.setIsSpecial(model.getIsSpecial());
bean.setBonusTypeId(model.getBonusTypeId());
bean.setLastUpdate(model.getLastUpdate());
bean.setGoodsType(model.getGoodsType());
bean.setSellerNote(model.getSellerNote());
bean.setGiveIntegral(model.getGiveIntegral());
bean.setRankIntegral(model.getRankIntegral());
bean.setBusinessId(model.getBusinessId());
bean.setIsCheck(model.getIsCheck());
bean.setTbIid(model.getTbIid());
bean.setTbData(model.getTbData());
bean.setIsExport(model.getIsExport());
bean.setUserId(model.getUserId());
bean.setExchangeRate(model.getExchangeRate());
bean.setProfit(model.getProfit());
bean.setShipping(model.getShipping());
bean.setProfitType(model.getProfitType());
bean.setCurrency(model.getCurrency());
bean.setGoodsUrl(model.getGoodsUrl());
bean.setTabbox(model.getTabbox());
bean.setGoodsSource(model.getGoodsSource());
bean.setSaleCount(model.getSaleCount());
bean.setClassify(model.getClassify());


		Date date = new Date();
		bean.setUpdateDate(date);
		bean.setLastUpdateDate(date);

		Long admin_id = (Long)request.getSession().getAttribute(EConstants.SESSION_ADMIN_ID);
		bean.setUpdateAdminId(admin_id);
		bean.setLastUpdateAdminId(admin_id);

		int code = haiGoodsMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiGoodsWithBLOBs> goods_info(HttpServletRequest request,Integer goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		/**
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsWithBLOBs> list = haiGoodsMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsWithBLOBs model = list.get(0);
		**/

		HaiGoodsWithBLOBs model = haiGoodsMapper.get_hai_goods_info(goodsId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsattrList",this.goodsattrList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiGoodsWithBLOBs> goods_find(HttpServletRequest request,Integer goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);

		/**
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goodsId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsWithBLOBs> list = haiGoodsMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsWithBLOBs model = list.get(0);
		**/

		HaiGoodsWithBLOBs model = haiGoodsMapper.get_hai_goods_info(goodsId,store_id);
		if(model == null){
			rm.setMsg("记录不存在");
			return rm;
		}

		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsattrList",this.goodsattrList(request));
		rm.setMap(map);

		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiGoods> goods_delete(HttpServletRequest request,Integer goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(goodsId);

		long count = haiGoodsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		int code = haiGoodsMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiGoodsWithBLOBs model) throws Exception {
		
//		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();

		Map<String,Object> optionMap = new HashMap<String, Object>();
//		optionMap.put("categoryTree", this.categoryTreeList(request));

		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "goods.xml",model,"hai_goods",optionMap);
		
		
		return bootStrapList;
	}


/////////////////////////////////////////////////////////////////////////////////////

	public List<HaiGoodsAttr> goodsattrList(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		
		
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsAttr> list = haiGoodsAttrMapper.selectByExample(example);
		
		return list;
	}


	public ReturnObject<HaiGoodsAttr> goodsattr_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiGoodsAttr> goodsattr_list_json(HttpServletRequest request,EConditionObject condition,String attrValue ) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		if(condition.getStore_id() == null)condition.setStore_id((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		
		
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		if(StringUtils.isNotEmpty(attrValue ))c.andAttrValue Like("%"+attrValue +"%");
		List<HaiGoodsAttr> list = haiGoodsAttrMapper.selectByExample(example);
		long total = haiGoodsAttrMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiGoodsAttr> goodsattr_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsAttr model = new HaiGoodsAttr();

		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiGoodsAttr> goodsattr_insert_submit(HttpServletRequest request,HaiGoodsAttr model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);

		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		c.andAttrValue EqualTo(model.getAttrValue ());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		long count = haiGoodsAttrMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiGoodsAttrMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiGoodsAttr> goodsattr_update(HttpServletRequest request,Integer goodsAttrId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		c.andGoodsAttrIdEqualTo(goodsAttrId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsAttr> list = haiGoodsAttrMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsAttr model = list.get(0);


		Map<String, Object> map = new HashMap<String, Object>();


		rm.setMap(map);

		rm.setAction("edit");
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiGoodsAttr> goodsattr_update_submit(HttpServletRequest request,HaiGoodsAttr model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsAttrIdEqualTo(model.getGoodsAttrId());
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));

		long count = haiGoodsAttrMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiGoodsAttr bean = haiGoodsAttrMapper.selectByPrimaryKey(model.getGoodsAttrId());

		bean.setGoodsId(model.getGoodsId());
bean.setAttrId(model.getAttrId());
bean.setAttrValue(model.getAttrValue());
bean.setAttrPrice(model.getAttrPrice());
bean.setTaobaoVid(model.getTaobaoVid());
bean.setAttrPic(model.getAttrPic());


		int code = haiGoodsAttrMapper.updateByExampleSelective(bean, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiGoodsAttr> goodsattr_info(HttpServletRequest request,Integer goodsAttrId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		c.andGoodsAttrIdEqualTo(goodsAttrId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsAttr> list = haiGoodsAttrMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsAttr model = list.get(0);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}


	public ReturnObject<HaiGoodsAttr> goodsattr_find(HttpServletRequest request,Integer goodsAttrId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		c.andGoodsAttrIdEqualTo(goodsAttrId);
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		List<HaiGoodsAttr> list = haiGoodsAttrMapper.selectByExample(example);
		if(list == null || list.size() == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		HaiGoodsAttr model = list.get(0);
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiGoodsAttr> goodsattr_delete(HttpServletRequest request,Integer goodsAttrId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsAttr> rm = new ReturnObject<HaiGoodsAttr>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsAttrExample example = new HaiGoodsAttrExample();
		HaiGoodsAttrExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsAttrIdEqualTo(goodsAttrId);

		long count = haiGoodsAttrMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}


		HaiGoodsExample exampleCheck = new HaiGoodsExample();
		HaiGoodsExample.Criteria cCheck = exampleCheck.createCriteria();
		cCheck.andGoodsAttrIdEqualTo(goodsAttrId);
		exampleCheck.CriteriaStoreId(cCheck, this.storeIdCriteriaObject(request));
		long countCheck = haiGoodsMapper.countByExample(exampleCheck);
		if(countCheck > 0){
			rm.setMsg("存在关联记录，请先移除关联记录后再删除此分类");
			return rm;
		}

		int code = haiGoodsAttrMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}











	
}











