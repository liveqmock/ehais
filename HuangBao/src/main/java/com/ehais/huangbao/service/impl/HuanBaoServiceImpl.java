package com.ehais.huangbao.service.impl;


import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ehais.huangbao.mapper.HaiContributionMapper;
import com.ehais.huangbao.model.HaiContribution;
import com.ehais.huangbao.service.HuanBaoService;

@Service("huanBaoService")
public class HuanBaoServiceImpl extends WXPayServiceImpl implements HuanBaoService {

	@Autowired
	private HaiContributionMapper haiContributionMapper;
	
	public ReturnObject<HaiContribution> Contribution_List(Integer wxid,
			String cat_code, Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		Integer start = (page - 1) * len;
		rm.setRows(haiContributionMapper.hai_contribution_cate_list(wxid, cat_code, start, len));
		rm.setCode(1);
		
		return rm;
	}

	public ReturnObject<HaiContribution> Contribution_Detail(Integer wxid, Integer conId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiContribution> rm = new ReturnObject<HaiContribution>();
		HaiContribution model = haiContributionMapper.selectByPrimaryKey(conId);
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	

}
