package org.ehais.shop.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.epublic.mapper.EHaiAdMapper;
import org.ehais.epublic.model.EHaiAd;
import org.ehais.epublic.model.EHaiAdExample;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.shop.service.AdService;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adService")
public class AdServiceImpl extends CommonServiceImpl implements AdService{

	@Autowired
	private EHaiAdMapper eHaiAdMapper;

	public ReturnObject<EHaiAd> hai_ad_list(Integer store_id , Integer position_id , Integer enabled,
			Integer is_mobile, Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiAd> rm = new ReturnObject<EHaiAd>();
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		EHaiAdExample example = new EHaiAdExample();
		example.createCriteria()
		.andStoreIdEqualTo(store_id)
		.andPositionIdEqualTo(Short.valueOf(position_id+""))
		.andEnabledEqualTo(Byte.valueOf(enabled+""))
		.andIsMobileEqualTo(Short.valueOf(is_mobile+""));
		example.setStart(start);
		example.setLen(len);
		
		List<EHaiAd> list = eHaiAdMapper.selectByExampleWithBLOBs(example);
				
//		List<EHaiAd> list = haiAdMapper.hai_ad_list(store_id , position_id , enabled, is_mobile, start, len);
		
		rm.setRows(list);
		rm.setCode(1);
		
		return rm;
		
	}

	@Override
	public List<EHaiAd> list(HttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub
		EHaiAdExample example = new EHaiAdExample();
		example.createCriteria()
		.andStoreIdEqualTo((Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID));
		
		List<EHaiAd> list = eHaiAdMapper.selectByExampleWithBLOBs(example);
		
		return list;
	}
	
	
}
