package org.ehais.weixin.service.action.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ehais.common.Constants;
import org.ehais.model.BootStrapModel;
import org.ehais.model.ExtendsField.ExtendsFieldsGroup;
import org.ehais.model.ExtendsField.ExtendsFieldsXml;
import org.ehais.service.impl.CommonServiceImpl;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.ehais.weixin.mapper.HaiExtendValueMapper;
import org.ehais.weixin.model.HaiExtendValue;
import org.ehais.weixin.model.HaiExtendValueExample;
import org.ehais.weixin.service.action.ExtendsFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;


@Service("extendsFieldsService")
public class ExtendsFieldsServiceImpl extends CommonServiceImpl implements ExtendsFieldsService{

	@Autowired
	private HaiExtendValueMapper haiExtendValueMapper;
	
	//读取扩展字段的列表
	public List<BootStrapModel> bootStrapModelList(HttpServletRequest request,
			String code, String table,Integer tableId) throws IOException{

		String menu_path = "";
		if(request.getServerName().equals("localhost")){
			menu_path = request.getRealPath("").replace("webapp", "");
			menu_path += "/resources/config/extends_type.xml";
		}else{
			menu_path = request.getRealPath("/WEB-INF/classes/config/extends_type.xml");
		}
		
		String menu_content = FSO.BufferedReader(menu_path);
		XStream xStream = new XStream();
		xStream.processAnnotations(ExtendsFieldsXml.class);
		xStream.processAnnotations(ExtendsFieldsGroup.class);
		xStream.processAnnotations(BootStrapModel.class);
		boolean isRole = false;
		String role = (String)request.getSession().getAttribute(Constants.SESSION_ROLE_TYPE);
		String username = (String)request.getSession().getAttribute(Constants.SESSION_USER_NAME);
		if(role!=null && !role.equals("") && username == null)isRole = true;//管理员登录状态
		
		ExtendsFieldsXml atxml = (ExtendsFieldsXml) xStream.fromXML(menu_content);
		List<ExtendsFieldsGroup> group = atxml.getGroup();
		List<BootStrapModel> bootStrapModelList = null;
		for (ExtendsFieldsGroup extendsTypeGroup : group) {
			if(isRole){
				if(extendsTypeGroup.getRole().equals(role) && extendsTypeGroup.getCode().equals(code) && extendsTypeGroup.getTable().equals(table)){
					bootStrapModelList = extendsTypeGroup.getField();
					break;
				}
			}else{
				if(extendsTypeGroup.getManager().equals(username) && extendsTypeGroup.getCode().equals(code) && extendsTypeGroup.getTable().equals(table)){
					bootStrapModelList = extendsTypeGroup.getField();
					break;
				}
			}
		}
		
		if(tableId != null && tableId > 0){
			HaiExtendValueExample example = new HaiExtendValueExample();
			example.createCriteria().andTableIdEqualTo(tableId).andTableNameEqualTo(table);
			List<HaiExtendValue> list = haiExtendValueMapper.hai_extend_value_list_by_example(example);
			for (HaiExtendValue haiExtendValue : list) {
				for (BootStrapModel bsModel : bootStrapModelList) {
					if(haiExtendValue.getFieldName().equals(bsModel.getField_name())){
						bsModel.setValue(haiExtendValue.getContent());
						continue;
					}
				}
			}
		}
		return bootStrapModelList;
	}
	@Override
	public ReturnObject<Object> ExtendsFieldsInfo(HttpServletRequest request,
			String code, String table,Integer tableId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<Object> rm = new ReturnObject<Object>();
		
		
		rm.setBootStrapList(bootStrapModelList(request,code,table,tableId));
		
		return rm;
	}

	@Override
	public ReturnObject<HaiExtendValue> ExtendsFieldsSave(
			HttpServletRequest request, String code, String table,
			Integer tableId) throws Exception {
		// TODO Auto-generated method stub
		ReturnObject<HaiExtendValue> rm = new ReturnObject<HaiExtendValue>();
		
		List<BootStrapModel> bsm_list = bootStrapModelList(request,code,table,tableId);
		
		HaiExtendValueExample example = new HaiExtendValueExample();
		
		Integer store_id = 0;
		Integer admin_id = (Integer)request.getSession().getAttribute(Constants.SESSION_ADMIN_ID);
		if(admin_id != null && admin_id > 0){
			store_id = admin_id;
		}else{
			Long user_id = (Long)request.getSession().getAttribute(Constants.SESSION_USER_ID);
			store_id = user_id.intValue();
		}
		
		
		//以下的方式是有点效率低
		for (BootStrapModel bsm : bsm_list) {
			String val = request.getParameter(bsm.getField_name());
			if(val == null)continue;
			HaiExtendValue e = new HaiExtendValue();
			e.setTableId(tableId);
			e.setFieldName(bsm.getField_name());
			e.setContent(val);
			e.setTableName(table);
			e.setStoreId(store_id);
			
			example.clear();
			example.createCriteria().andTableIdEqualTo(tableId).andTableNameEqualTo(table).andStoreIdEqualTo(store_id);
			int count = haiExtendValueMapper.countByExample(example);
			
			if(count == 0){
				haiExtendValueMapper.insertSelective(e);
			}else{
				haiExtendValueMapper.updateByExampleWithBLOBs(e, example);
			}
		}
		
		rm.setCode(1);
		return rm;
	}

}
