package org.ehais.weixin.service.action.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.EConstants;
import org.ehais.epublic.model.WpPublicWithBLOBs;
import org.ehais.model.BootStrapModel;
import org.ehais.tools.ReturnObject;
import org.ehais.util.EHttpClientUtil;
import org.ehais.weixin.WXConstants;
import org.ehais.weixin.mapper.HaiAdMapper;
import org.ehais.weixin.model.AccessToken;
import org.ehais.weixin.model.HaiAd;
import org.ehais.weixin.model.HaiAdExample;
import org.ehais.weixin.service.action.AdService;
import org.ehais.weixin.service.wx.impl.WeiXinCommonServiceImpl;
import org.ehais.weixin.utils.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("adService")
public class AdServiceImpl  extends WeiXinCommonServiceImpl implements AdService{
	
	@Autowired
	private HaiAdMapper haiAdMapper;
	
	public ReturnObject<HaiAd> ad_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<HaiAd> ad_list_json(HttpServletRequest request,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		Integer start = (page - 1 ) * len;
		
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		
		c.andStoreIdEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		List<HaiAd> list = haiAdMapper.hai_ad_list_by_example(example);
		Integer total = haiAdMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	
	public ReturnObject<HaiAd> ad_list_json(Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer start = (page - 1 ) * len;
		
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		
		c.andStoreIdEqualTo(store_id);
		example.setStart(start);
		example.setLen(len);
		List<HaiAd> list = haiAdMapper.hai_ad_list_by_example(example);
		Integer total = haiAdMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}
	

	public ReturnObject<HaiAd> ad_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();	
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiAd model = new HaiAd();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<HaiAd> ad_insert_submit(HttpServletRequest request,HaiAd model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();

		if(model.getAdName() == null || model.getAdName().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}
		
		if(model.getAdCode() == null || model.getAdCode().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		model.setStoreId(store_id);
		model.setStartTime(new Date());
		model.setEndTime(new Date());
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		c.andAdNameEqualTo(model.getAdName());
		c.andStoreIdEqualTo(store_id);
		int count = haiAdMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = haiAdMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<HaiAd> ad_update(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiAd model = haiAdMapper.selectByPrimaryKey(Short.valueOf(adId+""));
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<HaiAd> ad_update_submit(HttpServletRequest request,HaiAd model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		
		c.andStoreIdEqualTo(store_id);
		c.andAdIdEqualTo(model.getAdId());
		c.andStoreIdEqualTo(store_id);

		int count = haiAdMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}
		
		String path = request.getRealPath("/Uploads/images")+"/1460010841261.jpg";
		System.out.println(path);
		
		WpPublicWithBLOBs wp = this.getWpPublic(store_id);
		AccessToken token = WeiXinUtil.getAccessToken(store_id, wp.getAppid(), wp.getSecret());
		
		String upload_media = WXConstants.upload_media
		.replaceAll("ACCESS_TOKEN", token.getAccess_token())
		.replaceAll("TYPE", "image");
		
		Map<String,String> fileMap = new HashMap<String,String>();
		fileMap.put("media", path);
		
		String reqData = EHttpClientUtil.postHttpClientFile(upload_media, null, fileMap, null);
		System.out.println(reqData);
		
		
		int code = haiAdMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<HaiAd> ad_find(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		
		HaiAd model = haiAdMapper.selectByPrimaryKey(Short.valueOf(adId+""));
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<HaiAd> ad_delete(HttpServletRequest request,Integer adId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiAd> rm = new ReturnObject<HaiAd>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_WX_ID);
		HaiAdExample example = new HaiAdExample();
		HaiAdExample.Criteria c = example.createCriteria();
		c.andStoreIdEqualTo(store_id);
		c.andAdIdEqualTo(Short.valueOf(adId+""));
		int code = haiAdMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(HaiAd model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		bootStrapList.add(new BootStrapModel("hidden", "adId", "", model.getAdId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "positionId", "", model.getPositionId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "mediaType", "", model.getMediaType(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "adName", "名称", model.getAdName(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "adLink", "链接", model.getAdLink(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("images", "adCode", "图片", model.getAdCode(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "linkMan", "", model.getLinkMan(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "linkEmail", "", model.getLinkEmail(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("text", "linkPhone", "", model.getLinkPhone(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "clickCount", "", model.getClickCount(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("select_format", "enabled", "", model.getEnabled(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "storeId", "", model.getStoreId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("datepicker", "startTime", "", model.getStartTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("datepicker", "endTime", "", model.getEndTime(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "navId", "", model.getNavId(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "sort", "", model.getSort(), "请输入", "", "", null, 0));
//		bootStrapList.add(new BootStrapModel("checkbox", "isMobile", "", model.getIsMobile(), "请输入", "", "", null, 0));
		
		
		return bootStrapList;
	}
	
}











