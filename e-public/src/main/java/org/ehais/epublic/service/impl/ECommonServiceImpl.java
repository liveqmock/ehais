package org.ehais.epublic.service.impl;

import org.ehais.epublic.mapper.ECommonMapper;
import org.ehais.epublic.service.ECommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("eCommonServiceImpl")
public class ECommonServiceImpl implements ECommonService {

	@Autowired
	private ECommonMapper eCommonMapper;
	@Override
	public int commonUnique(String tableName, String field, String value) {
		// TODO Auto-generated method stub
		return eCommonMapper.commonUnique(tableName, field, value);
	}

}
