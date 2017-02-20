package org.ehais.shop.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.mapper.HaiGoodsGalleryMapper;
import org.ehais.shop.model.HaiGoodsGallery;
import org.ehais.shop.model.HaiGoodsGalleryExample;
import org.ehais.shop.service.GoodsGalleryService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("goodsGalleryService")
public class GoodsGalleryServiceImpl extends CommonServiceImpl implements GoodsGalleryService {

	@Autowired
	private HaiGoodsGalleryMapper haiGoodsGalleryMapper;
	
	@Override
	public ReturnObject<HaiGoodsGallery> goods_gallery_list(HttpServletRequest request,
			Long goods_id, Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		example.createCriteria().andStoreIdEqualTo(store_id).andGoodsIdEqualTo(goods_id);
		List<HaiGoodsGallery> list = haiGoodsGalleryMapper.selectByExample(example);
		rm.setRows(list);
		
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<HaiGoodsGallery> goods_gallery_find(HttpServletRequest request, Long img_id,
			Long goods_id, Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		HaiGoodsGallery goodsGallery = haiGoodsGalleryMapper.get_hai_goods_gallery_info(store_id, img_id, goods_id);
		rm.setModel(goodsGallery);
		
		rm.setCode(1);
		return rm;
	}

	@Override
	public ReturnObject<HaiGoodsGallery> goods_gallery_insert(HttpServletRequest request, HaiGoodsGallery model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		
		if(model.getStoreId() == null) model.setStoreId((Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID));
		
		Integer i = haiGoodsGalleryMapper.insert(model);
		rm.setMsg("新增成功");
		
		rm.setCode(i);
		return rm;
	}

	@Override
	public ReturnObject<HaiGoodsGallery> goods_gallery_update(HttpServletRequest request, HaiGoodsGallery model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		
		if(model.getStoreId() == null) model.setStoreId((Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID));
		
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		example.createCriteria().andStoreIdEqualTo(model.getStoreId()).andImgIdEqualTo(model.getImgId());
		
		Integer i = haiGoodsGalleryMapper.updateByExampleSelective(model, example);
		rm.setMsg("编辑成功");
		
		rm.setCode(i);
		return rm;
	}

	@Override
	public ReturnObject<HaiGoodsGallery> goods_gallery_delete(HttpServletRequest request, Long img_id,
			Long goods_id, Integer store_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiGoodsGallery> rm = new ReturnObject<HaiGoodsGallery>();
		rm.setCode(0);
		
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		HaiGoodsGalleryExample example = new HaiGoodsGalleryExample();
		example.createCriteria()
//		.andStoreIdEqualTo(store_id)
		.andGoodsIdEqualTo(goods_id).andImgIdEqualTo(img_id);
		
		Integer i = haiGoodsGalleryMapper.deleteByExample(example);
		rm.setMsg("删除成功");
		
		
		rm.setCode(i);
		return rm;
	}

}
