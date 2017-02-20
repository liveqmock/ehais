package org.ehais.epublic.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.epublic.mapper.EHaiStoreAppkeySecretMapper;
import org.ehais.epublic.model.EHaiStoreAppkeySecret;
import org.ehais.epublic.model.EHaiStoreAppkeySecretExample;
import org.ehais.epublic.service.EStoreAppkeySecretService;
import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//model里面对应的日期添加的 @DateTimeFormat( pattern = "yyyy-MM-dd" )


@Service("eStoreAppkeySecretService")
public class EStoreAppkeySecretServiceImpl  extends CommonServiceImpl implements EStoreAppkeySecretService{
	
	@Autowired
	private EHaiStoreAppkeySecretMapper eHaiStoreAppkeySecretMapper;
	
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_list(HttpServletRequest request) throws Exception{
		
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		rm.setCode(1);
		return rm;
	}

	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_list_json(HttpServletRequest request,Integer store_id,
			Integer page, Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();
		if(store_id == null) store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		Integer start = ((page != null)? ((page - 1 ) * len ) : 0 );
		
		EHaiStoreAppkeySecretExample example = new EHaiStoreAppkeySecretExample();
		EHaiStoreAppkeySecretExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		example.setStart(start);
		example.setLen(len);
		List<EHaiStoreAppkeySecret> list = eHaiStoreAppkeySecretMapper.hai_store_appkey_secret_list_by_example(example);
		Integer total = eHaiStoreAppkeySecretMapper.countByExample(example);
		rm.setCode(1);
		rm.setRows(list);
		rm.setTotal(total);
		
		
		return rm;
	}

	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_insert(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();	
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiStoreAppkeySecret model = new EHaiStoreAppkeySecret();
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_insert_submit(HttpServletRequest request,EHaiStoreAppkeySecret model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();

		if(model.getTitle() == null || model.getTitle().equals("")){
			rm.setMsg("必填项不能为空");
			return rm;
		}


		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		model.setStoreId(store_id);

		EHaiStoreAppkeySecretExample example = new EHaiStoreAppkeySecretExample();
		EHaiStoreAppkeySecretExample.Criteria c = example.createCriteria();
		c.andTitleEqualTo(model.getTitle());
		c.andStoreIdEqualTo(store_id);
		int count = eHaiStoreAppkeySecretMapper.countByExample(example);
		if(count > 0){
			rm.setMsg("存在相同的记录");
			return rm;
		}


		int code = eHaiStoreAppkeySecretMapper.insertSelective(model);
		rm.setCode(code);
		rm.setMsg("添加成功");
		return rm;
	}

	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_update(HttpServletRequest request,Integer appId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiStoreAppkeySecret model = eHaiStoreAppkeySecretMapper.selectByPrimaryKey(appId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}
	
	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_update_submit(HttpServletRequest request,EHaiStoreAppkeySecret model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiStoreAppkeySecretExample example = new EHaiStoreAppkeySecretExample();
		EHaiStoreAppkeySecretExample.Criteria c = example.createCriteria();
		
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAppIdEqualTo(model.getAppId());
		c.andStoreIdEqualTo(store_id);

		int count = eHaiStoreAppkeySecretMapper.countByExample(example);
		if(count == 0){
			rm.setMsg("记录不存在");
			return rm;
		}

		int code = eHaiStoreAppkeySecretMapper.updateByExampleSelective(model, example);
		rm.setCode(code);
		rm.setMsg("编辑成功");
		return rm;
	}

	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_find(HttpServletRequest request,Integer appId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		
		EHaiStoreAppkeySecret model = eHaiStoreAppkeySecretMapper.selectByPrimaryKey(appId);
		rm.setBootStrapList(this.formatBootStrapList(model));
		
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<EHaiStoreAppkeySecret> storeappkeysecret_delete(HttpServletRequest request,Integer appId)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<EHaiStoreAppkeySecret> rm = new ReturnObject<EHaiStoreAppkeySecret>();
		Integer store_id = (Integer)request.getSession().getAttribute(Constants.SESSION_STORE_ID);
		EHaiStoreAppkeySecretExample example = new EHaiStoreAppkeySecretExample();
		EHaiStoreAppkeySecretExample.Criteria c = example.createCriteria();
		example.CriteriaStoreId(c, this.storeIdCriteriaObject(request));
		c.andAppIdEqualTo(appId);
		int code = eHaiStoreAppkeySecretMapper.deleteByExample(example);
		rm.setCode(code);
		rm.setMsg("删除成功");
		return rm;
	}
	
	private List<BootStrapModel> formatBootStrapList(EHaiStoreAppkeySecret model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		
		return bootStrapList;
	}
	
}











