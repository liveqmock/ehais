package org.ehais.shop.service.impl;

import java.util.Date;
import java.util.List;

import org.ehais.epublic.mapper.EHaiStoreMapper;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.mapper.HaiStoreStatisticsMapper;
import org.ehais.epublic.model.EHaiStore;
import org.ehais.epublic.model.HaiStoreStatistics;
import org.ehais.epublic.model.HaiStoreStatisticsExample;
import org.ehais.shop.service.HaiStoreStatisticsService;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("haiStoreStatisticsService")
public class HaiStoreStatisticsServiceImpl implements HaiStoreStatisticsService{

	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	@Autowired
	private HaiStoreStatisticsMapper haiStoreStatisticsMapper;
	@Autowired
	private EHaiStoreMapper eHaiStoreMapper;
	
	
	//每天的销售额统计
	@Override
	public void dayStoreStatistics() throws Exception {
		// TODO Auto-generated method stub
		Date date = new Date();
		date = DateUtil.addDate(date, -1);//从零晨4点计算昨天的金额
		String pay_date = DateUtil.formatDate(date, DateUtil.FORMATSTR_3);
		try{
			List<EHaiStore> storeList = eHaiStoreMapper.selectByExample(null);
			for (EHaiStore eHaiStore : storeList) {
				List<HaiStoreStatistics> sst = haiStoreStatisticsMapper.order_store_statistics(eHaiStore.getStoreId(), pay_date, pay_date);
				if(sst == null || sst.size() ==0)continue;
				
				HaiStoreStatisticsExample exp = new HaiStoreStatisticsExample();
				exp.createCriteria().andStoreIdEqualTo(eHaiStore.getStoreId())
				.andStatisticsDateEqualTo(date);
				
				long count = haiStoreStatisticsMapper.countByExample(exp);
				if(count == 0){
					haiStoreStatisticsMapper.insert(sst.get(0));
				}else{
					haiStoreStatisticsMapper.updateByExampleSelective(sst.get(0), exp);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	

}
