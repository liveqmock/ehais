package org.ehais.Junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapCheckBoxModel;
import org.ehais.model.BootStrapModel;
import org.ehais.model.ExtendsField.ExtendsFieldsGroup;
import org.ehais.model.ExtendsField.ExtendsFieldsXml;
import org.ehais.util.FSO;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

public class ExtendsFieldsJUnit {

	@Test
	public void create() {
		XStream xStream = new XStream();
		
		
		ExtendsFieldsXml atxml = new ExtendsFieldsXml();
		atxml.setGroup(new ArrayList<ExtendsFieldsGroup>());
		
		ExtendsFieldsGroup atGroup = new ExtendsFieldsGroup();
		atGroup.setRole("weixin");
		atGroup.setTable("hai_weixin");
		
		List<BootStrapModel> listField = new ArrayList<BootStrapModel>();
		listField.add(new BootStrapModel("text", "goodsName1", "商品名称", null, "请输入", "", "", null, 0));
		listField.add(new BootStrapModel("text", "goodsSn1", "商品编号", null, "请输入", "", "", null, 0));
		atGroup.setField(listField);
		atxml.getGroup().add(atGroup);
		
		atGroup = new ExtendsFieldsGroup();
		atGroup.setRole("company");
		atGroup.setTable("hai_company");
		
		listField = new ArrayList<BootStrapModel>();
		listField.add(new BootStrapModel("text", "goodsSn2", "商品编号", null, "请输入", "", "", null, 0));
		listField.add(new BootStrapModel("text", "goodsName2", "商品名称", null, "请输入", "", "", null, 0));
		
		atGroup.setField(listField);
		atxml.getGroup().add(atGroup);
		
		
		xStream.autodetectAnnotations(true); 
		
		
		String xml = xStream.toXML(atxml);
		System.out.println(xml);
		
		String xmlPath = "D:/workspace_luna/ehais/m-weixin/src/main/resources/config/extends_type.xml";
		FSO.WriteTextFile2(xmlPath, xml);
		
		
	}
	
	@Test
	public void find() throws IOException{
//		String xmlPath = "D:/workspace_luna/ehais/m-weixin/src/main/resources/config/extends_type.xml";
		String xmlPath = "/Users/gzepro/developer/j2ee/workspace/ehais/m-weixin/src/main/resources/config/extends_type.xml";
		String xmlContent = FSO.BufferedReader(xmlPath);
		
		XStream xStream = new XStream();
		xStream.processAnnotations(ExtendsFieldsXml.class);
		xStream.processAnnotations(ExtendsFieldsGroup.class);
		xStream.processAnnotations(BootStrapModel.class);
		
		xStream.processAnnotations(BootStrapCheckBoxModel.class);
		
		
		ExtendsFieldsXml atxml = (ExtendsFieldsXml) xStream.fromXML(xmlContent);
		List<ExtendsFieldsGroup> group = atxml.getGroup();
		for (ExtendsFieldsGroup extendsTypeGroup : group) {
			System.out.println(extendsTypeGroup.getRole()+" "+ extendsTypeGroup.getTable());
			List<BootStrapModel> field = extendsTypeGroup.getField();
			for (BootStrapModel bootStrapModel : field) {
				System.out.println("==="+bootStrapModel.getField_name());
			}
		}
	}
	
	
	@Test
	public void bootstrapxml(){
		BootStrapModel bsm = new BootStrapModel("text", "goodsSn2", "商品编号", null, "请输入", "", "", null, 0);
//		Map<String, String> checkbox = new HashMap<String,String>();
//		checkbox.put("1", "热门");
//		checkbox.put("1", "最新");
		
		List<BootStrapCheckBoxModel> checkbox = new ArrayList<BootStrapCheckBoxModel>();
		checkbox.add(new BootStrapCheckBoxModel("1", "最新"));
		checkbox.add(new BootStrapCheckBoxModel("1", "最热"));
		
		bsm.setCheckboxs(checkbox);
		XStream xStream = new XStream();
		xStream.processAnnotations(BootStrapModel.class);
		xStream.processAnnotations(BootStrapCheckBoxModel.class);
//		xStream.alias("classes", Map.class); 
//		xStream.alias("class", Map.Entry.class);  
		String xml = xStream.toXML(bsm);
		System.out.println(xml);
		
	}
}
