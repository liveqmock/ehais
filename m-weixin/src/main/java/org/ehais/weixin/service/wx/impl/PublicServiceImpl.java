package org.ehais.weixin.service.wx.impl;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.BootStrapModel;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.weixin.mapper.WpPublicMapper;
import org.ehais.weixin.model.WpPublic;
import org.ehais.weixin.model.WpPublicExample;
import org.ehais.weixin.model.WpPublicWithBLOBs;
import org.ehais.weixin.service.wx.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("publicService")
public class PublicServiceImpl extends CommonServiceImpl implements PublicService {

	@Autowired
	private WpPublicMapper wpPublicMapper;
	
	public ReturnObject<WpPublicWithBLOBs> public_by_user(Long user_id)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();
		WpPublicWithBLOBs p = wpPublicMapper.public_by_user(user_id);
		if(p == null){
			p = new WpPublicWithBLOBs();
			p.setUid(user_id);
			wpPublicMapper.insertSelective(p);
		}
		rm.setModel(p);
		rm.setCode(1);
		return rm;
	}
	
	public ReturnObject<WpPublic> public_list(Long userId, Integer page,
			Integer len) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublic> ro = new ReturnObject<WpPublic>();
		Integer start = (page - 1 ) * len;
		List<WpPublic> list = wpPublicMapper.public_list(userId , start, len);
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andUidEqualTo(userId);
		Integer total = wpPublicMapper.countByExample(example);
		ro.setCode(1);
		ro.setRows(list);
		ro.setTotal(total);
		
			
		return ro;
	}

	public ReturnObject<WpPublic> public_insert(Integer store_id)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public ReturnObject<WpPublicWithBLOBs> public_insert_submit(WpPublicWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> ro = new ReturnObject<WpPublicWithBLOBs>();
		int code = wpPublicMapper.insertSelective(model);
		ro.setCode(code);
		ro.setMsg("添加成功");
		return ro;
	}

	public ReturnObject<WpPublicWithBLOBs> public_update(Integer store_id, Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> rm = new ReturnObject<WpPublicWithBLOBs>();		
		WpPublicWithBLOBs model = wpPublicMapper.selectByPrimaryKey(key);
		rm.setBootStrapList(this.formatBootStrapList(model));
		rm.setCode(1);
		rm.setModel(model);
		return rm;
	}

	public ReturnObject<WpPublicWithBLOBs> public_update_submit(WpPublicWithBLOBs model)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublicWithBLOBs> ro = new ReturnObject<WpPublicWithBLOBs>();
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andUidEqualTo(model.getUid());
		c.andIdEqualTo(model.getId());
		if(model.getToken() == null || model.getToken().equals(""))model.setToken(model.getPublicId());
		int code = wpPublicMapper.updateByExampleSelective(model, example);
		ro.setModel(model);
		ro.setCode(code);
		ro.setMsg("编辑成功");
		return ro;
	}

	public ReturnObject<WpPublic> public_find(Integer store_id, Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublic> ro = new ReturnObject<WpPublic>();
		WpPublic model = wpPublicMapper.selectByPrimaryKey(key);
		ro.setCode(1);
		ro.setModel(model);
		return ro;
	}
	
	public ReturnObject<WpPublic> public_find(Integer key) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublic> ro = new ReturnObject<WpPublic>();
		WpPublic model = wpPublicMapper.selectByPrimaryKey(key);
		ro.setCode(1);
		ro.setModel(model);
		return ro;
	}

	public ReturnObject<WpPublic> public_delete(Long userId, Integer key)
			throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<WpPublic> ro = new ReturnObject<WpPublic>();
		WpPublicExample example = new WpPublicExample();
		WpPublicExample.Criteria c = example.createCriteria();
		c.andUidEqualTo(userId);
		c.andIdEqualTo(key);
		int code = wpPublicMapper.deleteByExample(example);
		ro.setCode(code);
		ro.setMsg("删除成功");
		return ro;
	}

	private List<BootStrapModel> formatBootStrapList(WpPublic model){
		
		List<BootStrapModel> bootStrapList = new ArrayList<BootStrapModel>();
		
		bootStrapList.add(new BootStrapModel("hidden", "id", "", model.getId(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("hidden", "uid", "", model.getUid(), "请输入", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "publicName", "公众号名", model.getPublicName(), "请输入公众号名", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "publicId", "原始ID", model.getPublicId(), "请输入原始ID", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "wechat", "微信号", model.getWechat(), "请输入微信号", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "appid", "应用APPID", model.getAppid(), "请输入应用APPID", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "secret", "应用密钥", model.getSecret(), "请输入应用密钥", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "encodingaeskey", "EncodingAESKey ", model.getEncodingaeskey(), "请输入encodingaeskey,安全模式下才使用", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "payAppid", "支付ID", model.getPayAppid(), "请输入支付ID，一般情况跟应用appid相同，可以不填", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "paySecret", "支付密钥", model.getPaySecret(), "请输入支付ID，一般情况跟应用密钥相同，可以不填", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "mchId", "商家编号", model.getMchId(), "请输入商家编号", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "subMch_id", "子商家编号", model.getSubMchId(), "请输入子商家编号", "", "", null, 0));
		bootStrapList.add(new BootStrapModel("text", "mchSecret", "商家密钥", model.getMchSecret(), "请输入商家密钥", "", "", null, 0));

		
		return bootStrapList;
	}


}
