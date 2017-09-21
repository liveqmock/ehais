package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiCartMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiCart;
import org.ehais.shop.model.HaiCartExample;
import org.ehais.shop.model.HaiCartWithBLOBs;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.service.CartService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("cartService")
public class CartServiceImpl  extends CommonServiceImpl implements CartService{
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiCartMapper haiCartMapper;
	
	public ReturnObject<HaiCart> cart_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCart> cart_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setLimitStart(start);
		example.setLimitEnd(len);
		List<HaiCart> list = haiCartMapper.hai_cart_list_by_example(example);
		long total = haiCartMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiCartWithBLOBs> cart_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCartWithBLOBs model = new HaiCartWithBLOBs();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiCartWithBLOBs> cart_insert_submit(HttpServletRequest request,HaiCartWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();

		if(model.getGoodsId() == null || model.getGoodsId().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		c.andGoodsIdEqualTo(model.getGoodsId());
		c.andStoreIdEqualTo(store_id);
		long count = haiCartMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiCartMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiCartWithBLOBs> cart_update(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCartWithBLOBs model = haiCartMapper.selectByPrimaryKey(recId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiCartWithBLOBs> cart_update_submit(HttpServletRequest request,HaiCartWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andRecIdEqualTo(model.getRecId());
		c.andStoreIdEqualTo(store_id);

		long count = haiCartMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiCartMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiCartWithBLOBs> cart_find(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiCartWithBLOBs model = haiCartMapper.selectByPrimaryKey(recId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiCart> cart_delete(HttpServletRequest request,Long recId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andRecIdEqualTo(recId);
		int code = haiCartMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiCartWithBLOBs model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	public ReturnObject<HaiCart> cart_quantity(HttpServletRequest request, Long user_id,String session_shop_encode) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		if(session_shop_encode == null ){
			session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		}
		
		if(user_id == null && session_shop_encode == null){
			
		}
		
		HaiCartExample example = new HaiCartExample();
		if(user_id != null)example.or().andUserIdEqualTo(user_id);
		if(session_shop_encode != null)example.or().andSessionIdEqualTo(session_shop_encode);
		
		Long total = haiCartMapper.countByExample(example);
		
		rm.setCode(1);
		rm.setTotal(total);
		
		return rm;
	}
	
	public ReturnObject<HaiCart> cart_list(HttpServletRequest request, Long user_id,String session_shop_encode) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);
//		request.getSession().setAttribute(Constants.SESSION_USER_ID, Long.valueOf("1"));
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		if(session_shop_encode == null ){
			session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		}
		
		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		if(user_id != null){//登录状态使用
			c.andUserIdEqualTo(user_id);
		}else if(session_shop_encode != null){//没有登录的状态使用
			c.andSessionIdEqualTo(session_shop_encode);
		}
		
		long total = haiCartMapper.countByExample(example);
		List<HaiCart> list = haiCartMapper.hai_cart_list_by_example(example);
		
		rm.setCode(1);
		rm.setTotal(total);
		rm.setRows(list);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCartWithBLOBs> cart_info(HttpServletRequest request, Long rec_id,Long goods_id, Long user_id,  String session_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();
		rm.setCode(0);
		
		HaiCartWithBLOBs model = haiCartMapper.get_hai_cart_info( rec_id, goods_id,  user_id,  session_id);
		rm.setModel(model);
		
		rm.setCode(1);
		return rm;
	}
	
	

	public ReturnObject<HaiCart> cart_list_session(HttpServletRequest request, String sessioin_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);
		
		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		c.andSessionIdEqualTo(sessioin_id);
		List<HaiCart> list = haiCartMapper.hai_cart_list_by_example(example);
		
		rm.setCode(1);
		rm.setRows(list);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiCart> cart_info_session(HttpServletRequest request, Long recId, String sessioin_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);
		
		HaiCartWithBLOBs model = haiCartMapper.get_hai_cart_info_session(sessioin_id, recId);
		rm.setModel(model);
		
		rm.setCode(1);
		return rm;
	}
	
	

	public ReturnObject<HaiCartWithBLOBs> cart_add_submit(HttpServletRequest request, Long goods_id,Integer store_id,Integer quantity, Long user_id,String session_shop_encode,Long parent_user_id,Integer agency_id,Integer article_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		if(session_shop_encode == null ){
			session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		}
		if(store_id == null ){
			store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		}
		
		if((user_id == null || user_id == 0) && (session_shop_encode == null || session_shop_encode.equals(""))){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		if(goods_id == null || goods_id == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		
		if(store_id == null || store_id == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		
		if(quantity == null || quantity == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		

		//即时返回此用户的购物车存在商品
		HaiCartExample example = new HaiCartExample();
		if(user_id != null)example.or().andUserIdEqualTo(user_id).andGoodsIdEqualTo(goods_id);
		if(session_shop_encode != null)example.or().andSessionIdEqualTo(session_shop_encode).andGoodsIdEqualTo(goods_id);
		
		List<HaiCartWithBLOBs> listCart = haiCartMapper.selectByExampleWithBLOBs(example);
		if(listCart!=null && listCart.size()>0){
			rm.setCode(2);
			rm.setMsg("此商品已存在购物车中，请进入购物车修改数量");
			rm.setModel(listCart.get(0));
			return rm;
		}
//		int count = haiCartMapper.get_hai_cart_goods(goods_id,user_id,session_shop_encode);
//		if(count > 0){
//			rm.setMsg("此商品已存在购物车中，请进入购物车修改数量");
//			return rm;
//		}
		
		HaiGoods goods = haiGoodsMapper.get_app_goods(store_id, goods_id);
		if(goods == null){
			rm.setMsg("不存在此商品");
			return rm;
		}
		
		if(goods.getIsDelete() != null && goods.getIsDelete()){
			rm.setMsg("商品已停售！");
			return rm;
		}
		
		if(goods.getIsOnSale() != null && !goods.getIsOnSale()){
			rm.setMsg("商品已下架！");
			return rm;
		}
		
		if(goods.getGoodsNumber().intValue() != -1 && goods.getGoodsNumber() == 0){
			rm.setMsg("商品已售完！");
			return rm;
		}

		HaiCartWithBLOBs model = new HaiCartWithBLOBs();
		model.setGoodsId(goods_id);
		model.setUserId(user_id);
		model.setSessionId(session_shop_encode);
		model.setGoodsNumber(quantity);
		model.setGoodsName(goods.getGoodsName());
		model.setMarketPrice(goods.getMarketPrice());
		model.setGoodsPrice(goods.getShopPrice());
		model.setGoodsThumb(StringUtils.isEmpty(goods.getGoodsThumb())?goods.getOriginalImg():goods.getGoodsThumb());
		model.setGoodsAttr("");
		model.setStoreId(goods.getStoreId());
		model.setParentUserId(parent_user_id);//来源分销的用户
		model.setAgencyId(agency_id);
		model.setArticleId(article_id);
		
		int code = haiCartMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg(code==1?"添加成功":"添加失败");
		
		
		example.clear();
		if(user_id != null)example.or().andUserIdEqualTo(user_id);
		if(session_shop_encode != null)example.or().andSessionIdEqualTo(session_shop_encode);
		
		long total = haiCartMapper.countByExample(example);
		rm.setTotal(total);//购物车的数量
		rm.setModel(model);
		
		
		return rm;
	}

	public ReturnObject<HaiCartWithBLOBs> cart_edit_quantity(HttpServletRequest request,Long recId,Long goods_id,Integer store_id,Integer quantity,Long user_id,String session_shop_encode) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCartWithBLOBs> rm = new ReturnObject<HaiCartWithBLOBs>();
		rm.setCode(0);
		

		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		if(session_shop_encode == null ){
			session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		}
		
		if(store_id == null ){
			store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		}
		
		if((user_id == null || user_id == 0) && (session_shop_encode == null || session_shop_encode.equals(""))){
			rm.setMsg("非客户端使用");
			return rm;
		}
		
		if(goods_id == null || goods_id == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		
		if(store_id == null || store_id == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		
		if(quantity == null || quantity == 0){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		

		HaiCartExample example = new HaiCartExample();
		
		
		if(user_id!=null && user_id > 0)example.or().andGoodsIdEqualTo(goods_id).andUserIdEqualTo(user_id);
		if(StringUtils.isNotEmpty(session_shop_encode))example.or().andGoodsIdEqualTo(goods_id).andSessionIdEqualTo(session_shop_encode);
		HaiCart cart = haiCartMapper.selectByPrimaryKey(recId);
		if(cart == null){
			rm.setMsg("此商品不存在购物车中");
			return rm;
		}
		
		HaiGoods goods = haiGoodsMapper.get_app_goods(store_id, goods_id);
		if(goods == null){
			rm.setMsg("不存在此商品");
			return rm;
		}
		
		if(goods.getIsDelete() != null && goods.getIsDelete()){
			rm.setMsg("商品已停售！");
			return rm;
		}
		
		if(goods.getIsOnSale() != null && !goods.getIsOnSale()){
			rm.setMsg("商品已下架！");
			return rm;
		}
		
		if(goods.getGoodsNumber().intValue() != -1 && goods.getGoodsNumber() == 0){
			rm.setMsg("商品已售完！");
			return rm;
		}
		
		int code = haiCartMapper.updateCartQuantity(recId, goods_id,  quantity,user_id,session_shop_encode);
		rm.setCode(code);
		rm.setMsg(code==1?"编辑成功":"编辑失败");
		return rm;
	}

	public ReturnObject<HaiCart> cart_delete(HttpServletRequest request, Long recId, Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		HaiCartExample example = new HaiCartExample();
		HaiCartExample.Criteria c = example.createCriteria();
		c.andRecIdEqualTo(recId);
		c.andUserIdEqualTo(user_id);
		int code = haiCartMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg(code==1?"删除成功":"删除失败");
		return rm;
	}

	public ReturnObject<HaiCart> cart_clear(HttpServletRequest request, Long user_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);

		int code = haiCartMapper.deleteUserBySession(user_id);
		rm.setCode(code);
		rm.setMsg(code==1?"编辑成功":"编辑失败");
		return rm;
	}

	public ReturnObject<HaiCart> cart_update_user_session(HttpServletRequest request, Long user_id,
			String session_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiCart> rm = new ReturnObject<HaiCart>();
		rm.setCode(0);

		int code = haiCartMapper.updateUserBySession(user_id, session_id);
		rm.setCode(code);
		rm.setMsg(code==1?"编辑成功":"编辑失败");
		return rm;
	}

	
}











