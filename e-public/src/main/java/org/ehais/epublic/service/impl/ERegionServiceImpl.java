package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.epublic.mapper.EHaiRegionMapper;
import org.ehais.epublic.model.EHaiRegion;
import org.ehais.epublic.model.EHaiRegionExample;
import org.ehais.epublic.service.ERegionService;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("eRegionService")
public class ERegionServiceImpl extends CommonServiceImpl implements ERegionService{

	@Autowired
	private EHaiRegionMapper eHaiRegionMapper;

	public ReturnObject<EHaiRegion> region_parent_list(Integer parent_id) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiRegion> rm = new ReturnObject<EHaiRegion>();
		EHaiRegionExample example = new EHaiRegionExample();
		example.createCriteria().andParentIdEqualTo(parent_id);
		example.setOrderByClause("region_id asc");
		List<EHaiRegion>  list = eHaiRegionMapper.selectByExample(example);
		rm.setRows(list);
		rm.setCode(1);
		
		return rm;
	}

	public ReturnObject<EHaiRegion> region_id_list(String region_ids) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiRegion> rm = new ReturnObject<EHaiRegion>();
		EHaiRegionExample example = new EHaiRegionExample();
		List<Integer> values = new ArrayList<Integer>();
		String[] reg = region_ids.split(",");
		for (String str : reg) {
			values.add(Integer.valueOf(str));
		}		
		example.createCriteria().andRegionIdIn(values);
		List<EHaiRegion>  list = eHaiRegionMapper.selectByExample(example);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	@Override
	public List<EHaiRegion> region_lists(List<Integer> region_list) throws Exception {
		// TODO Auto-generated method stub
		if(region_list == null || region_list.size() == 0)return null;
		EHaiRegionExample example = new EHaiRegionExample();			
		example.createCriteria().andRegionIdIn(region_list);
		List<EHaiRegion>  list = eHaiRegionMapper.selectByExample(example);
		return list;
	}

	@Override
	public ReturnObject<EHaiRegion> region_parentid_list(String region_ids) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiRegion> rm = new ReturnObject<EHaiRegion>();
		EHaiRegionExample example = new EHaiRegionExample();
		List<Integer> values = new ArrayList<Integer>();
		String[] reg = region_ids.split(",");
		for (String str : reg) {
			values.add(Integer.valueOf(str));
		}		
		example.createCriteria().andParentIdIn(values);
		List<EHaiRegion>  list = eHaiRegionMapper.selectByExample(example);
		rm.setRows(list);
		rm.setCode(1);
		return rm;
	}

	@Override
	public List<EHaiRegion> region_parent_lists(List<Integer> region_list) throws Exception {
		// TODO Auto-generated method stub
		if(region_list == null || region_list.size() == 0)return null;
		EHaiRegionExample example = new EHaiRegionExample();			
		example.createCriteria().andParentIdIn(region_list);
		List<EHaiRegion>  list = eHaiRegionMapper.selectByExample(example);
		return list;
	}
	

	
}
