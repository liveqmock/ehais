package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.enums.EAdminClassifyEnum;
import org.ehais.enums.EStoreDistributionTypeEnum;
import org.ehais.model.BootStrapModel;
import org.ehais.model.ExtendsField.ExtendsFieldsTabs;
import org.ehais.shop.mapper.HaiAgencyMapper;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiFavoritesMapper;
import org.ehais.shop.mapper.HaiGoodsAgencyMapper;
import org.ehais.shop.mapper.HaiGoodsDistributionMapper;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiStoreSettingMapper;
import org.ehais.shop.model.HaiAgency;
import org.ehais.shop.model.HaiAgencyExample;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiFavoritesExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsAgency;
import org.ehais.shop.model.HaiGoodsAgencyExample;
import org.ehais.shop.model.HaiGoodsDistribution;
import org.ehais.shop.model.HaiGoodsDistributionExample;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.model.HaiGoodsWithBLOBs;
import org.ehais.shop.model.HaiStoreSetting;
import org.ehais.shop.service.GoodsService;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("goodsService")
public class GoodsServiceImpl  extends EShopCommonServiceImpl implements GoodsService{
	
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	@Autowired
	private HaiFavoritesMapper haiFavoritesMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiAgencyMapper haiAgencyMapper;
	@Autowired
	private HaiGoodsAgencyMapper haiGoodsAgencyMapper;
	@Autowired
	private HaiStoreSettingMapper haiStoreSettingMapper;
	@Autowired
	private HaiGoodsDistributionMapper haiGoodsDistributionMapper;
	
	
	/**
	 * 获取商户的设置
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private HaiStoreSetting getStoreSetting(HttpServletRequest request) throws Exception{
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiStoreSetting storeSetting = haiStoreSettingMapper.selectByPrimaryKey(store_id);
		
		return storeSetting;
	}
	
	private HaiGoodsDistribution getGoodsDistribution(HttpServletRequest request,Long goodsId,HaiStoreSetting setting) throws Exception{
		HaiGoodsDistribution goodsDistribution = null;
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		if(goodsId == null || goodsId == 0){			
			goodsDistribution = this.initGoodsDistribution(setting);
		}else{
			goodsDistribution = haiGoodsDistributionMapper.selectByPrimaryKey(goodsId);
			if(goodsDistribution == null)goodsDistribution = this.initGoodsDistribution(setting);
		}
		
		return goodsDistribution;
	}
	
	//当新增商品或查不到商品的信息时，初始化一下商品对应的分销值，非插入数据库
	private HaiGoodsDistribution initGoodsDistribution(HaiStoreSetting setting){
		if(setting == null)return null;
		HaiGoodsDistribution gd = new HaiGoodsDistribution();
		gd.setJoinDistribution(true);
		gd.setDefaultDistribution(true);
		gd.setDistributionType(setting.getDistributionType());
		gd.setDistributionPercentage(setting.getDistributionPercentage());
		gd.setDistributionMoney(0);
		gd.setFirstValue(setting.getFirstPercentage());
		gd.setSecondValue(setting.getSecondPercentage());
		gd.setThirdValue(setting.getThirdPercentage());
		gd.setToIntegral(0);
		return gd;
	}
	
	
	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
//		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "goods.xml",null,"hai_goods",null,null);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = (page - 1 ) * len;
		
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<HaiGoods> list = haiGoodsMapper.hai_goods_list_by_example(example);
		Long total = haiGoodsMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request,
			EConditionObject condition,Integer cat_id , String goods_name) throws Exception{
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		if(cat_id > 0)c.andCatIdEqualTo(cat_id);
		if(StringUtils.isNotEmpty(goods_name))c.andGoodsNameLike("%"+goods_name+"%");
		example.setLimitStart(condition.getStart());
		example.setLimitEnd(condition.getRows());
		example.setOrderByClause("goods_id desc");
		List<HaiGoods> list = haiGoodsMapper.hai_goods_list_by_example(example);
		Long total = haiGoodsMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		HaiCategoryExample categoryExample = new HaiCategoryExample();
		categoryExample.createCriteria().andStoreIdEqualTo(store_id);
		List<HaiCategory> categoryList = haiCategoryMapper.selectByExample(categoryExample);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categoryList", categoryList);
		rm.setMap(map);
		
		return rm;
	}
	
	/**
	 * 获取分类列表
	 * @param request
	 * @return
	 */
	private List<HaiCategory> catList(HttpServletRequest request){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCategoryExample cateExample = new HaiCategoryExample();
		HaiCategoryExample.Criteria cc = cateExample.createCriteria();
		cc.andStoreIdEqualTo(store_id);
		List<HaiCategory> list = haiCategoryMapper.selectByExample(cateExample);
		return list;
	}
	
	/**
	 * 代理列表
	 * @param request
	 * @return
	 */
	private List<HaiAgency> agencyList(HttpServletRequest request){
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiAgencyExample agyExample = new HaiAgencyExample();
		HaiAgencyExample.Criteria cc = agyExample.createCriteria();
		cc.andStoreIdEqualTo(store_id);
		agyExample.setOrderByClause("agency_level asc");
		List<HaiAgency> list = haiAgencyMapper.selectByExample(agyExample);
		return list;
	}
	
	/**
	 * 获取某商品的代理提成
	 * @param request
	 * @param goodsId
	 * @return
	 */
	private List<HaiGoodsAgency> goodsAgencyList(HttpServletRequest request,Long goodsId){
		HaiGoodsAgencyExample gaExp = new HaiGoodsAgencyExample();
		HaiGoodsAgencyExample.Criteria c = gaExp.createCriteria();
		if(goodsId > 0)c.andGoodsIdEqualTo(goodsId);
		List<HaiGoodsAgency> gaList = haiGoodsAgencyMapper.selectByExample(gaExp);
		return gaList;
	}
	
	/**
	 * 整理代理取货金额或代理提现金额
	 * @param agencyList
	 * @param goodsAgencyList
	 * @return
	 */
	private List<Map<String,Object>> goodsAgencyPrice(List<HaiAgency> agencyList,List<HaiGoodsAgency> goodsAgencyList){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for (HaiAgency agency : agencyList) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("agencyId", agency.getAgencyId());
			map.put("agencyName", agency.getAgencyName());
			map.put("agencyLevel", agency.getAgencyLevel());
			map.put("price", 0);
			for(HaiGoodsAgency goodsAgency : goodsAgencyList){
				if(agency.getAgencyId() == goodsAgency.getAgencyId()){
					map.put("price", goodsAgency.getPrice());
				}
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 保存代理取货价格
	 * @param request
	 * @param goodsId
	 */
	private void saveGoodsAgencyPrice(HttpServletRequest request,Long goodsId){
		List<HaiAgency> agencyList = this.agencyList(request);
		for (HaiAgency haiAgency : agencyList) {
			String agency_price = request.getParameter("agencyPrice_"+haiAgency.getAgencyId());
			Integer price = 0;
			if(StringUtils.isNotBlank(agency_price)){
				price = Float.valueOf( Float.valueOf(agency_price) * 100).intValue();
			}
			
			HaiGoodsAgencyExample example = new HaiGoodsAgencyExample();
			HaiGoodsAgencyExample.Criteria c = example.createCriteria();
			c.andGoodsIdEqualTo(goodsId).andAgencyIdEqualTo(haiAgency.getAgencyId());
			List<HaiGoodsAgency> list = haiGoodsAgencyMapper.selectByExample(example);
			if(list == null || list.size() == 0){
				HaiGoodsAgency ga = new HaiGoodsAgency();
				ga.setAgencyId(haiAgency.getAgencyId());
				ga.setGoodsId(goodsId);
				ga.setPrice(price);
				haiGoodsAgencyMapper.insert(ga);
			}else{
				HaiGoodsAgency ga = list.get(0);
				ga.setPrice(price);
				haiGoodsAgencyMapper.updateByExample(ga, example);
			}
		}
	}
	
	/**
	 * 保存商品分销价
	 * @param request
	 * @param goodsId
	 */
	private void saveGoodsDistribution(HttpServletRequest request,Long goodsId,HaiGoodsDistribution goodsDistribution){
		HaiGoodsDistributionExample example = new HaiGoodsDistributionExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		Long c  = haiGoodsDistributionMapper.countByExample(example);
		if(c == 0){
			goodsDistribution.setGoodsId(goodsId);
			haiGoodsDistributionMapper.insert(goodsDistribution);
		}else{
			goodsDistribution.setGoodsId(goodsId);
			haiGoodsDistributionMapper.updateByPrimaryKey(goodsDistribution);
		}
	}

	public ReturnObject<HaiGoodsWithBLOBs> goods_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();	
		HaiGoodsWithBLOBs model = new HaiGoodsWithBLOBs();
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("catList", this.catList(request));
		map.put("goodsAgencyPrice", this.goodsAgencyPrice(this.agencyList(request), this.goodsAgencyList(request,0L)));
		
		List<HaiGoodsGallery> gallery = new ArrayList<HaiGoodsGallery>();
		map.put("gallery", gallery);
		
		String adminClassify = (String)request.getSession().getAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
		if(StringUtils.isNotBlank(adminClassify) && adminClassify.equals(EAdminClassifyEnum.shop)){
			Map<String,String> mapDT = EStoreDistributionTypeEnum.map;
			mapDT.remove("0");
			map.put("distributionTypeMap", mapDT);//分销类型
			HaiStoreSetting storeSetting = this.getStoreSetting(request);
			map.put("storeSetting", storeSetting);//商户设置
			map.put("goodsDistribution", this.getGoodsDistribution(request, null, storeSetting));//商品分销设置
		}
		
		rm.setMap(map);
		rm.setModel(model);
		rm.setCode(1);
		rm.setAction("add");
		return rm;
	}
	
	public ReturnObject<HaiGoodsWithBLOBs> goods_insert_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery)
			throws Exception {
		// TODO Auto-generated method stub
		
//		Map<String,String> galleryMap = new HashMap<String,String>();
		Set<Integer> galleryNo = new HashSet<Integer>();
		
		for(Enumeration e = request.getParameterNames() ; e.hasMoreElements();){
			String thisName=e.nextElement().toString();
			String thisValue=request.getParameter(thisName);
			if(thisName.indexOf("[]") >= 0){
				String[] thisValues = request.getParameterValues(thisName);
//				System.out.println(thisName+"是数组 :"+thisValues.length);
				thisValue = "";
				for(int iv = 0 ; iv < thisValues.length ; iv++){
					thisValue += thisValues[iv] + ",";
				}
				if(thisValue.length() > 0)thisValue = thisValue.substring(0, thisValue.length()-1);
			}
			
			if(thisName.indexOf("hid_gallery_")>=0){
//				galleryMap.put(thisName, thisValue);
//				System.out.println(thisName+"--------------"+thisValue);
				galleryNo.add(Integer.valueOf(thisName.substring(thisName.lastIndexOf("_")+1)));//获取序号
			}

		}

//		if(true)throw new Exception("顶你个肺的错误");
		
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		int code = haiGoodsMapper.insertSelective(model);
		
		//添加数组
		for (String string : gallery) {
			HaiGoodsGallery g = new HaiGoodsGallery();			
			g.setGoodsId(model.getGoodsId());
			g.setTableName("hai_goods");
			g.setStoreId(store_id);
			g.setImgOriginal(string);		
			haiGoodsGalleryMapper.insertSelective(g);
			
		}
/*		for (Integer integer : galleryNo) {
			HaiGoodsGallery gallery = new HaiGoodsGallery();
			
			gallery.setGoodsId(model.getGoodsId());
			gallery.setTableName("hai_goods");
			gallery.setStoreId(store_id);
			if(request.getParameter("hid_gallery_thumb_gallery_"+integer) != null)gallery.setThumbUrl(request.getParameter("hid_gallery_thumb_gallery_"+integer));
			if(request.getParameter("hid_gallery_img_gallery_"+integer) != null)gallery.setImgUrl(request.getParameter("hid_gallery_img_gallery_"+integer));
			if(request.getParameter("hid_gallery_original_gallery_"+integer) != null)gallery.setImgOriginal(request.getParameter("hid_gallery_original_gallery_"+integer));
		
			haiGoodsGalleryMapper.insertSelective(gallery);
			
		}*/

		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}
	
	public ReturnObject<HaiGoodsWithBLOBs> ehais_goods_insert_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery,HaiGoodsDistribution goodsDistribution)
			throws Exception {
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);
		String costPriceFloat = request.getParameter("costPriceFloat");
		String shopPriceFloat = request.getParameter("shopPriceFloat");
		if(StringUtils.isNotBlank(costPriceFloat)){
			Integer costPrice = Float.valueOf(Float.valueOf(StringUtils.trim(costPriceFloat)) * 100).intValue();
			model.setCostPrice(costPrice);
		}
		if(StringUtils.isNotBlank(shopPriceFloat)){
			Integer shopPrice = Float.valueOf(Float.valueOf(StringUtils.trim(shopPriceFloat)) * 100).intValue();
			model.setShopPrice(shopPrice);
		}
		
		int code = haiGoodsMapper.insertSelective(model);
		
		//保存代理价格
		this.saveGoodsAgencyPrice(request, model.getGoodsId());
		
		if(gallery != null){
			for (String string : gallery) {
				HaiGoodsGallery g = new HaiGoodsGallery();			
				g.setGoodsId(model.getGoodsId());
				g.setTableName("hai_goods");
				g.setStoreId(store_id);
				g.setImgOriginal(string);		
				haiGoodsGalleryMapper.insertSelective(g);
			}
		}
		
		
		String adminClassify = (String)request.getSession().getAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
		if(StringUtils.isNotBlank(adminClassify) && adminClassify.equals(EAdminClassifyEnum.shop)){
			this.saveGoodsDistribution(request, model.getGoodsId(), goodsDistribution);
		}
		
		
		rm.setCode(1);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiGoodsWithBLOBs> goods_update(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
//		HaiGoodsWithBLOBs model = haiGoodsMapper.selectByPrimaryKey(goodsId);
		HaiGoodsWithBLOBs model = haiGoodsMapper.get_hai_goods_info(store_id, goodsId);
		
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		List<HaiGoodsGallery> goodsGallery = haiGoodsGalleryMapper.selectByExample(example);
//		rm.setExtendsFieldsTabs(this.formatBootStrapTab(request,model));
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("catList", this.catList(request));
		map.put("gallery", goodsGallery);
		map.put("goodsAgencyPrice", this.goodsAgencyPrice(this.agencyList(request), this.goodsAgencyList(request,goodsId)));
		
		String adminClassify = (String)request.getSession().getAttribute(EConstants.SESSION_ADMIN_CLASSIFY);
		if(StringUtils.isNotBlank(adminClassify) && adminClassify.equals(EAdminClassifyEnum.shop)){
			Map<String,String> mapDT = EStoreDistributionTypeEnum.map;
			mapDT.remove("0");
			map.put("distributionTypeMap", mapDT);//分销类型
			
			HaiStoreSetting storeSetting = this.getStoreSetting(request);
			map.put("storeSetting", storeSetting);//商户设置
			map.put("goodsDistribution", this.getGoodsDistribution(request, goodsId, storeSetting));//商品分销设置
			
		}
		
		rm.setMap(map);
		
		rm.setCode(1);
		rm.setModel(model);
		rm.setAction("edit");
		return rm;
	}
	
	public ReturnObject<HaiGoodsWithBLOBs> goods_update_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(model.getGoodsId());
		if(model.getIsAloneSale() == null)model.setIsAloneSale(false);
		if(model.getIsBest() == null)model.setIsBest(false);
		if(model.getIsDelete() == null)model.setIsDelete(false);
		if(model.getIsHot() == null)model.setIsHot(false);
		if(model.getIsNew() == null)model.setIsNew(false);
		if(model.getIsOnSale() == null)model.setIsOnSale(false);
		if(model.getIsPromote() == null)model.setIsPromote(false);
		if(model.getIsShipping() == null)model.setIsShipping(false);
		if(model.getIsSpecial() == null)model.setIsSpecial(false);
		if(model.getIsReal() == null)model.setIsReal(Byte.valueOf("0"));
		
		int code = haiGoodsMapper.updateByExampleSelective(model, example);
		
		HaiGoodsGalleryExample galleryExample = new HaiGoodsGalleryExample();
		
//		haiGoodsGalleryMapper.deleteByExample(galleryExample);
		
		galleryExample.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andGoodsIdEqualTo(model.getGoodsId());
		List<HaiGoodsGallery> listGoodsGallery = haiGoodsGalleryMapper.selectByExample(galleryExample);
		for (String string : gallery) {
			boolean has = false;
			for (HaiGoodsGallery haiGoodsGallery : listGoodsGallery) {
				if(haiGoodsGallery.getImgOriginal().equals(string)){
					has = true;
					break;
				}
			}
			if(!has){
				//当前string可以添加到数据库
				HaiGoodsGallery g = new HaiGoodsGallery();			
				g.setGoodsId(model.getGoodsId());
				g.setTableName("hai_goods");
				g.setStoreId(store_id);
				g.setImgOriginal(string);		
				haiGoodsGalleryMapper.insertSelective(g);	
			}
		}
		for (HaiGoodsGallery haiGoodsGallery : listGoodsGallery) {
			boolean del = true;
			for (String string : gallery) {
				if(haiGoodsGallery.getImgOriginal().equals(string)){
					del = false;
					break;
				}
			}
			if(del){
				haiGoodsGalleryMapper.deleteByPrimaryKey(haiGoodsGallery.getImgId());
			}
		}
		
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}
	
	
	public ReturnObject<HaiGoodsWithBLOBs> ehais_goods_update_submit(HttpServletRequest request,HaiGoodsWithBLOBs model,String[] gallery,HaiGoodsDistribution goodsDistribution)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		rm.setCode(0);
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(model.getGoodsId());
		c.andStoreIdEqualTo(store_id);

		long count = haiGoodsMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		HaiGoodsWithBLOBs bean = haiGoodsMapper.selectByPrimaryKey(model.getGoodsId());
		
		bean.setCatId(model.getCatId());
		bean.setGoodsSn(model.getGoodsSn());
		bean.setGoodsName(model.getGoodsName());
		bean.setStoreId(model.getStoreId());
		bean.setBrandId(model.getBrandId());
		bean.setGoodsNumber(model.getGoodsNumber());
		bean.setGoodsWeight(model.getGoodsWeight());
		bean.setMarketPrice(model.getMarketPrice());
		bean.setShopPrice(model.getShopPrice());
		bean.setCostPrice(model.getCostPrice());
		bean.setWarnNumber(model.getWarnNumber());
		bean.setKeywords(model.getKeywords());
		bean.setGoodsBrief(model.getGoodsBrief());
		bean.setGoodsDesc(model.getGoodsDesc());
		bean.setActDesc(model.getActDesc());
		bean.setAppDesc(model.getAppDesc());
		bean.setGoodsThumb(model.getGoodsThumb());
		bean.setGoodsImg(model.getGoodsImg());
		bean.setOriginalImg(model.getOriginalImg());
		bean.setIsReal(model.getIsReal()==null?Byte.valueOf("0"):Byte.valueOf("1"));
		bean.setIsOnSale(model.getIsOnSale()==null?false:true);
		bean.setIsAloneSale(model.getIsAloneSale()==null?false:true);
		bean.setIsShipping(model.getIsShipping()==null?false:true);
		bean.setIntegral(model.getIntegral());
		bean.setSortOrder(model.getSortOrder());
		bean.setIsDelete(model.getIsDelete()==null?false:true);
		bean.setIsBest(model.getIsBest()==null?false:true);
		bean.setIsNew(model.getIsNew()==null?false:true);
		bean.setIsHot(model.getIsHot()==null?false:true);
		bean.setIsPromote(model.getIsPromote()==null?false:true);
		bean.setIsSpecial(model.getIsSpecial()==null?false:true);
		bean.setBonusTypeId(model.getBonusTypeId());
		bean.setGoodsType(model.getGoodsType());
		bean.setGiveIntegral(model.getGiveIntegral());
		bean.setRankIntegral(model.getRankIntegral());
		bean.setUserId(model.getUserId());
		bean.setCreateDate(model.getCreateDate());
		bean.setUpdateDate(model.getUpdateDate());
		
		
//		if(model.getIsAloneSale() == null)model.setIsAloneSale(false);
//		if(model.getIsBest() == null)model.setIsBest(false);
//		if(model.getIsDelete() == null)model.setIsDelete(false);
//		if(model.getIsHot() == null)model.setIsHot(false);
//		if(model.getIsNew() == null)model.setIsNew(false);
//		if(model.getIsOnSale() == null)model.setIsOnSale(false);
//		if(model.getIsPromote() == null)model.setIsPromote(false);
//		if(model.getIsShipping() == null)model.setIsShipping(false);
//		if(model.getIsSpecial() == null)model.setIsSpecial(false);
//		if(model.getIsReal() == null)model.setIsReal(Byte.valueOf("0"));
		
		String costPriceFloat = request.getParameter("costPriceFloat");
		String shopPriceFloat = request.getParameter("shopPriceFloat");
		if(StringUtils.isNotBlank(costPriceFloat)){
			Integer costPrice = Float.valueOf(Float.valueOf(StringUtils.trim(costPriceFloat)) * 100).intValue();
			bean.setCostPrice(costPrice);
		}
		if(StringUtils.isNotBlank(shopPriceFloat)){
			Integer shopPrice = Float.valueOf(Float.valueOf(StringUtils.trim(shopPriceFloat)) * 100).intValue();
			bean.setShopPrice(shopPrice);
		}

		int code = haiGoodsMapper.updateByExampleSelective(bean, example);
		
		//保存代理价格
		this.saveGoodsAgencyPrice(request, model.getGoodsId());

		HaiGoodsGalleryExample galleryExample = new HaiGoodsGalleryExample();
		
		galleryExample.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andGoodsIdEqualTo(model.getGoodsId());
		List<HaiGoodsGallery> listGoodsGallery = haiGoodsGalleryMapper.selectByExample(galleryExample);
		if(gallery != null){
			for (String string : gallery) {
				boolean has = false;
				for (HaiGoodsGallery haiGoodsGallery : listGoodsGallery) {
					if(haiGoodsGallery.getImgOriginal().equals(string)){
						has = true;
						break;
					}
				}
				if(!has){
					//当前string可以添加到数据库
					HaiGoodsGallery g = new HaiGoodsGallery();			
					g.setGoodsId(model.getGoodsId());
					g.setTableName("hai_goods");
					g.setStoreId(store_id);
					g.setImgOriginal(string);		
					haiGoodsGalleryMapper.insertSelective(g);	
				}
			}
			for (HaiGoodsGallery haiGoodsGallery : listGoodsGallery) {
				boolean del = true;
				for (String string : gallery) {
					if(haiGoodsGallery.getImgOriginal().equals(string)){
						del = false;
						break;
					}
				}
				if(del){
					haiGoodsGalleryMapper.deleteByPrimaryKey(haiGoodsGallery.getImgId());
				}
			}
		}
		
		this.saveGoodsDistribution(request, model.getGoodsId(), goodsDistribution);
		
		
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}
	

	public ReturnObject<HaiGoodsWithBLOBs> goods_find(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiGoodsWithBLOBs model = haiGoodsMapper.selectByPrimaryKey(goodsId);
		rm.setBootStrapList(this.formatBootStrapList(request,model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiGoods> goods_delete(HttpServletRequest request,Long goodsId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andGoodsIdEqualTo(goodsId);
		int code = haiGoodsMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HttpServletRequest request,HaiGoodsWithBLOBs model) throws Exception{
		
		Map<String,Object> optionMap = new HashMap<String, Object>();
		optionMap.put("categoryTree", this.categoryTreeList(request));
		
		Map<String,Object> galleryMap = new HashMap<String, Object>();
		if(model != null )galleryMap.put("goods_gallery", this.gallery(request,model.getGoodsId(),null,"hai_goods"));

		
		List<BootStrapModel> bootStrapList = this.BootStrapXml(request, "goods.xml",model,"hai_goods",optionMap,galleryMap);
		
		
		
		
/**
		bootStrapList.add(new BootStrapModel("hidden", "goodsId", "", model.getGoodsId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("select_tree", "catId", "商品分类", model.getCatId(), "请输入", "", "", null, this.categoryTreeList(request), 0));
		bootStrapList.add(new BootStrapModel("text", "goodsSn", "商品编号", model.getGoodsSn(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "goodsName", "商品名称", model.getGoodsName(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "goodsName_style", "", model.getGoodsName_style(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "clickCount", "", model.getClickCount(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "brandId", "", model.getBrandId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "providerName", "", model.getProviderName(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "goodsNumber", "当前数量", model.getGoodsNumber(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("", "goodsWeight", "", model.getGoodsWeight(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "marketPrice", "市场价格", model.getMarketPrice(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "shopPrice", "本店价格", model.getShopPrice(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "costPrice", "成本价格", model.getCostPrice(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "promotePrice", "促销价格", model.getPromotePrice(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "promoteStart_date", "促销开始时间", model.getPromoteStartDate(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("datepicker", "promoteEnd_date", "促销截止时间", model.getPromoteEndDate(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "warnNumber", "报", model.getWarnNumber(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "keywords", "商品关键字", model.getKeywords(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("textareaV1", "goodsBrief", "商品简述", model.getGoodsBrief(), "请输入", "", "", null, 0,1));
		bootStrapList.add(new BootStrapModel("textarea", "goodsDesc", "商品描述", model.getGoodsDesc(), "请输入", "", "", null, 0,1));
		bootStrapList.add(new BootStrapModel("textareaV1", "actDesc", "移动端简述", model.getActDesc(), "请输入", "", "", null, 0,2));
		bootStrapList.add(new BootStrapModel("textareaV1", "appDesc", "移动端描述", model.getAppDesc(), "请输入", "", "", null, 0,2));
		bootStrapList.add(new BootStrapModel("images", "goodsThumb", "缩略图", model.getGoodsThumb(), "请输入200*200", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "goodsImg", "商品图片", model.getGoodsImg(), "请输入680*680", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "originalImg", "商品原图", model.getOriginalImg(), "请输入980*980", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isReal", "", model.getIsReal(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "extensionCode", "", model.getExtensionCode(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isOn_sale", "", model.getIsOn_sale(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isAlone_sale", "", model.getIsAlone_sale(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isShipping", "", model.getIsShipping(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "integral", "", model.getIntegral(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "addTime", "", model.getAddTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "sortOrder", "", model.getSortOrder(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isDelete", "", model.getIsDelete(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isBest", "", model.getIsBest(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isNew", "", model.getIsNew(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isHot", "", model.getIsHot(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isPromote", "", model.getIsPromote(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isSpecial", "", model.getIsSpecial(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "bonusType_id", "", model.getBonusType_id(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "lastUpdate", "", model.getLastUpdate(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "goodsType", "", model.getGoodsType(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "sellerNote", "", model.getSellerNote(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "giveIntegral", "", model.getGiveIntegral(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "rankIntegral", "", model.getRankIntegral(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "businessId", "", model.getBusinessId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isCheck", "", model.getIsCheck(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "tbIid", "", model.getTbIid(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("textarea", "tbData", "", model.getTbData(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isExport", "", model.getIsExport(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "userId", "", model.getUserId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "storeId", "", model.getStoreId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("", "exchangeRate", "", model.getExchangeRate(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("", "profit", "", model.getProfit(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("", "shipping", "", model.getShipping(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "profitType", "", model.getProfitType(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "currency", "", model.getCurrency(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "goodsUrl", "", model.getGoodsUrl(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "tabbox", "", model.getTabbox(), "请输入", "", "", null, 0));
**/
		return bootStrapList;
	}
	
	private List<ExtendsFieldsTabs> formatBootStrapTab(HttpServletRequest request,HaiGoodsWithBLOBs model) throws Exception{
		
		Map<String,Object> optionMap = new HashMap<String, Object>();
		optionMap.put("categoryTree", this.categoryTreeList(request));
//		optionMap.put("is_hot", arg1);
		
		Map<String,Object> galleryMap = new HashMap<String, Object>();
		if(model != null )galleryMap.put("gallery", this.gallery(request,model.getGoodsId(),null,"hai_goods"));

		List<ExtendsFieldsTabs> tab = this.BootStrapTabXml(request, "goods.xml",model,"hai_goods",optionMap,galleryMap);
		return tab;
	}
		

	@Override
	public ReturnObject<HaiGoods> goods_list(HttpServletRequest request,
			Integer store_id, Integer catId,Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		if(catId!=null && catId.intValue() != 0)c.andCatIdEqualTo(catId);
		example.setLimitStart((page - 1 ) * len);
		example.setLimitEnd(len);
		example.setOrderByClause("goods_id desc");
		
		Long total = haiGoodsMapper.countByExample(example);		
		List<HaiGoods> list = haiGoodsMapper.hai_goods_list_by_example(example);
		
		rm.setTotal(total);
		rm.setCode(1);
		rm.setRows(list);
		
		return rm;
	}

	@Override
	public ReturnObject<HaiGoodsWithBLOBs> goods_info(HttpServletRequest request,
			Integer store_id, Long goodsId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		
		HaiGoodsWithBLOBs model = haiGoodsMapper.get_hai_goods_info(store_id, goodsId);
		//获取图片库
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	@Override
	public ReturnObject<HaiGoodsWithBLOBs> app_goods_info(
			HttpServletRequest request, Integer store_id,Long user_id,String session_shop_encode ,Long goodsId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsWithBLOBs> rm = new ReturnObject<HaiGoodsWithBLOBs>();
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		if(session_shop_encode == null ){
			session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		}
		
		HaiGoodsWithBLOBs model = haiGoodsMapper.get_app_goods(store_id, goodsId);
		rm.setModel(model);
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//读取图片库
		HaiGoodsGalleryExample galleryExample = new HaiGoodsGalleryExample();
		galleryExample.createCriteria().andGoodsIdEqualTo(goodsId).andStoreIdEqualTo(store_id);
		List<HaiGoodsGallery> gallery = haiGoodsGalleryMapper.selectByExample(galleryExample);
		map.put("gallery", gallery);
		
		//读取购物车信息
		HaiCartExample example = new HaiCartExample();
		if(user_id != null)example.or().andUserIdEqualTo(user_id);
		if(session_shop_encode != null)example.or().andSessionIdEqualTo(session_shop_encode);
		Long cart_quantity = haiCartMapper.countByExample(example);
		map.put("cart_quantity", cart_quantity);
		
		HaiFavoritesExample fExample = new HaiFavoritesExample();
		if(user_id != null)fExample.or().andUserIdEqualTo(user_id);
		if(session_shop_encode != null)fExample.or().andSesskeyEqualTo(session_shop_encode);
		long favorites_quantity = haiFavoritesMapper.countByExample(fExample);
		map.put("favorites_quantity", favorites_quantity);
		
		rm.setMap(map);
		rm.setCode(1);
		
		return rm;
	}
	
	
	
	private List<Map<String,String>> gallery(HttpServletRequest request,Long goods_id,Integer store_id,String table_name)throws Exception{
		List<Map<String,String>> gallery_list = new ArrayList<Map<String,String>>();
		if(goods_id == null || goods_id == 0)return gallery_list;
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		HaiGoodsGalleryExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(goods_id);
		c.andTableNameEqualTo(table_name);
		List<HaiGoodsGallery> ggl = haiGoodsGalleryMapper.selectByExample(example);
		for (HaiGoodsGallery haiGoodsGallery : ggl) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("imgId", haiGoodsGallery.getImgId().toString());
			map.put("goodsId", haiGoodsGallery.getGoodsId().toString());
			map.put("thumb", haiGoodsGallery.getThumbUrl().indexOf("http://") >= 0 ? haiGoodsGallery.getThumbUrl() : "/"+haiGoodsGallery.getThumbUrl());
			map.put("img", haiGoodsGallery.getImgUrl().indexOf("http://") >= 0 ? haiGoodsGallery.getImgUrl() : "/"+haiGoodsGallery.getImgUrl());
			map.put("original", haiGoodsGallery.getImgOriginal().indexOf("http://") >= 0 ? haiGoodsGallery.getImgOriginal() : "/"+haiGoodsGallery.getImgOriginal());
			map.put("tableName", table_name);
			gallery_list.add(map);
		}
		
		return gallery_list;
	}

	@Override
	public List<HaiGoods> list(HttpServletRequest request,  Integer catId, Integer page, Integer len)
			throws Exception {
		// TODO Auto-generated method stub
		
		HaiGoodsExample example = new HaiGoodsExample();
		HaiGoodsExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo((Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID));
		if(catId!=null && catId != 0)c.andCatIdEqualTo(catId);
		example.setLimitStart((page-1) * len );
		example.setLimitEnd(len);
		List<HaiGoods> list = haiGoodsMapper.hai_goods_list_by_example(example);
		return list;
	}
	
	
	@Override
	public HaiGoods info(HttpServletRequest request,Long goodsId) throws Exception {
		// TODO Auto-generated method stub
		
//		HaiGoodsExample example = new HaiGoodsExample();
//		HaiGoodsExample.Criteria c = example.createCriteria();
//		c.andStoreIdEqualTo((Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID));
		
		HaiGoods info = haiGoodsMapper.selectByPrimaryKey(goodsId);
		
		
		return info;
	}

	@Override
	public List<HaiGoodsGallery> galleryList(HttpServletRequest request, Long goodsId) throws Exception {
		// TODO Auto-generated method stub
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId);
		
		return haiGoodsGalleryMapper.selectByExample(example);
	}
	
	
}











