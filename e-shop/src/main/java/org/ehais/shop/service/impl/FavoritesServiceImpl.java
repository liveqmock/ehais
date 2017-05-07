package org.ehais.shop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiFavoritesMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.model.HaiFavorites;
import org.ehais.shop.model.HaiFavoritesExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.service.FavoritesService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("favoritesService")
public class FavoritesServiceImpl  extends CommonServiceImpl implements FavoritesService{
	
	@Autowired
	private HaiFavoritesMapper haiFavoritesMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	
	
	public ReturnObject<HaiFavorites> favorites_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiFavorites> favorites_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		HaiFavoritesExample example = new HaiFavoritesExample();
		HaiFavoritesExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<HaiFavorites> list = haiFavoritesMapper.hai_favorites_list_by_example(example);
		Integer total = haiFavoritesMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<HaiFavorites> favorites_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiFavorites model = new HaiFavorites();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiFavorites> favorites_insert_submit(HttpServletRequest request,HaiFavorites model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();

//		if(model.getuser_id() == null || model.getuser_id().equals("")){
//			rm.setMsg("必填项不能为空");
//			return rm;
//		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		HaiFavoritesExample example = new HaiFavoritesExample();
		HaiFavoritesExample.Criteria c = example.createCriteria();
//		c.anduser_idEqualTo(model.getuser_id());
		c.andStoreIdEqualTo(store_id);
		int count = haiFavoritesMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiFavoritesMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiFavorites> favorites_update(HttpServletRequest request,Long id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiFavorites model = haiFavoritesMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiFavorites> favorites_update_submit(HttpServletRequest request,HaiFavorites model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiFavoritesExample example = new HaiFavoritesExample();
		HaiFavoritesExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		c.andidEqualTo(model.getid());
		c.andStoreIdEqualTo(store_id);

		int count = haiFavoritesMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = haiFavoritesMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiFavorites> favorites_find(HttpServletRequest request,Long id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		
		HaiFavorites model = haiFavoritesMapper.selectByPrimaryKey(id);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiFavorites> favorites_delete(HttpServletRequest request,Long id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		HaiFavoritesExample example = new HaiFavoritesExample();
		HaiFavoritesExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
//		c.andidEqualTo(id);
		int code = haiFavoritesMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiFavorites model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}

	@Override
	public ReturnObject<HaiFavorites> favorites_add(HttpServletRequest request,
			Long goods_id, Long user_id, String session_shop_encode)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiFavorites> rm = new ReturnObject<HaiFavorites>();
		rm.setCode(0);
		
		if(user_id == null ){
			user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		}
		if(session_shop_encode == null ){
			session_shop_encode = (String)request.getSession().getAttribute(EConstants.SESSION_SHOP_ENCODE);
		}
		
		HaiFavoritesExample fExample = new HaiFavoritesExample();
		if(user_id != null)fExample.or().andUserIdEqualTo(user_id).andGoodsIdEqualTo(goods_id);
		if(session_shop_encode != null)fExample.or().andSesskeyEqualTo(session_shop_encode).andGoodsIdEqualTo(goods_id);
		Integer favorites_quantity = haiFavoritesMapper.countByExample(fExample);
		if(favorites_quantity > 0){
			rm.setMsg("此商品已收藏了");
		}
		
		HaiFavorites favorites = new HaiFavorites();
		favorites.setAddTime(System.currentTimeMillis());
		favorites.setGoodsId(goods_id);
		favorites.setUserId(user_id);
		favorites.setSesskey(session_shop_encode);
		
		
		haiFavoritesMapper.insertSelective(favorites);
		rm.setCode(1);
		rm.setMsg("收藏成功");
		return rm;
	}

	@Override
	public ReturnObject<HaiGoods> goods_list_json(HttpServletRequest request, Long user_id, Integer page,
			Integer len,String session_shop_encode) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoods> rm = new ReturnObject<HaiGoods>();
		if(user_id == null) user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		
		
		List<HaiGoods> list = haiGoodsMapper.hai_goods_list_by_favorites(user_id, start, len);
		
		rm.setRows(list);
		rm.setCode(1);
		
		return rm;
	}
	
}











