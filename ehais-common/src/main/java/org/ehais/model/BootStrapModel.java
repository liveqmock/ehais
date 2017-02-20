package org.ehais.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BootStrapModel")
public class BootStrapModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5244578574098907999L;

	@XStreamAlias("control")
	private String control;
	
	@XStreamAlias("name")
	private String field_name;
	
	@XStreamAlias("label")
	private String field_label;
	
	@XStreamAlias("value")
	private Object value;
	
	@XStreamAlias("prompt")
	private String prompt;//提示语
	
	@XStreamAlias("oksign")
	private String ok_sign;
	
	@XStreamAlias("errorsign")
	private String error_sign;
	
	@XStreamAlias("optionKey")
	private String optionKey;//识别xml配置文件的值，然后从map获取设置到option
	
	@XStreamAlias("option")
	private Map<String, String> option;
	
	@XStreamAlias("checkboxs")
	private List<BootStrapCheckBoxModel> checkboxs;	
	
	@XStreamAlias("radios")
	private List<BootStrapRadioModel> radios;	
	
	@XStreamAlias("optionformat")
	private String option_format;//格式化的option
	
	@XStreamAlias("listshow")
	private Integer list_show;
	
	@XStreamAlias("tabno")
	private Integer tab_no;
	
	//排序
	@XStreamAlias("sort")
	private Integer sort;
	
	@XStreamAlias("treeKey")
	private String treeKey;//识别xml配置文件的值，然后从map获取设置到treeList
	
	
	private List<TreeModel> treeList;
	
	
	@XStreamAlias("galleryformat")
	private String gallery_format;//格式化的相册的
	
	
	private List<Map<String,String>> gallery_list;
	
	@XStreamAlias("dataSource")
	private String dataSource ;
	
	@XStreamAlias("keyValue")
	private List<BootStrapKeyValueModel> keyValueList;
	
	public BootStrapModel(){
		super();
	}
	
	//全部参数，可以修改增加此方法的参数
	/**
	 * BootStrapModel(control, field_name,
			field_label, value, prompt, ok_sign,
			error_sign, optionKey, option,
			option_format, list_show, tab_no,
			sort, treeKey, treeList,
			gallery_format, gallery_list);
	 */
	public void BootStrapModel(String control, String field_name,
			String field_label, Object value, String prompt, String ok_sign,
			String error_sign, String optionKey, Map<String, String> option,
			String option_format, Integer list_show, Integer tab_no,
			Integer sort, String treeKey, List<TreeModel> treeList,
			String gallery_format, List<Map<String, String>> gallery_list) {
		
		this.control = control;
		this.field_name = field_name;
		this.field_label = field_label;
		this.value = value;
		this.prompt = prompt;
		this.ok_sign = ok_sign;
		this.error_sign = error_sign;
		this.optionKey = optionKey;
		this.option = option;
		this.option_format = option_format;
		this.list_show = list_show;
		this.tab_no = tab_no;
		this.sort = sort;
		this.treeKey = treeKey;
		this.treeList = treeList;
		this.gallery_format = gallery_format;
		this.gallery_list = gallery_list;
	}

	public BootStrapModel(String control, String field_name,
			String field_label, Object value, String prompt, String ok_sign,
			String error_sign, Map<String, String> option, Integer list_show) {
		super();
		BootStrapModel( control,  field_name,
				 field_label,  value,  prompt,  ok_sign,
				 error_sign,  null,  option,
				 null,  list_show,  0,
				 null,  null,  null,
				 null,  null);
		

//		this.control = control;
//		this.field_name = field_name;
//		this.field_label = field_label;
//		this.value = value;
//		this.prompt = prompt;
//		this.ok_sign = ok_sign;
//		this.error_sign = error_sign;
//		this.option = option;
//		this.list_show = list_show;
//		this.tab_no = 0;
	}
	
	public BootStrapModel(String control, String field_name,
			String field_label, Object value, String prompt, String ok_sign,
			String error_sign, Map<String, String> option, Integer list_show, Integer tab_no) {
		super();
		
		BootStrapModel(control, field_name,
				field_label, value, prompt, ok_sign,
				error_sign, null, option,
				null, list_show, tab_no,
				null, null, null,
				null, null);
				
//		this.control = control;
//		this.field_name = field_name;
//		this.field_label = field_label;
//		this.value = value;
//		this.prompt = prompt;
//		this.ok_sign = ok_sign;
//		this.error_sign = error_sign;
//		this.option = option;
//		this.list_show = list_show;
//		this.tab_no = tab_no;
	}
	
	public BootStrapModel(String control, String field_name,
			String field_label, Object value, String prompt, String ok_sign,
			String error_sign ,Map<String, String> option, List<TreeModel> treeList, Integer list_show) {
		super();
		
		BootStrapModel(control, field_name,
				field_label, value, prompt, ok_sign,
				error_sign, null, option,
				null, list_show, 0,
				null, null, treeList,
				null, null);
		
//		this.control = control;
//		this.field_name = field_name;
//		this.field_label = field_label;
//		this.value = value;
//		this.prompt = prompt;
//		this.ok_sign = ok_sign;
//		this.error_sign = error_sign;
//		this.option = option;
//		this.treeList = treeList;
//		this.list_show = list_show;
//		this.tab_no = 0;
	}
	
	
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}
	public String getField_label() {
		return field_label;
	}
	public void setField_label(String field_label) {
		this.field_label = field_label;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getOk_sign() {
		return ok_sign;
	}
	public void setOk_sign(String ok_sign) {
		this.ok_sign = ok_sign;
	}
	public String getError_sign() {
		return error_sign;
	}
	public void setError_sign(String error_sign) {
		this.error_sign = error_sign;
	}
	public Map<String, String> getOption() {
		return option;
	}
	public void setOption(Map<String, String> option) {
		this.option = option;
	}
	public Integer getList_show() {
		return list_show;
	}
	public void setList_show(Integer list_show) {
		this.list_show = list_show;
	}
	public String getOption_format() {
		return option_format;
	}
	public void setOption_format(String option_format) {
		this.option_format = option_format;
	}
	public Integer getTab_no() {
		return tab_no;
	}
	public void setTab_no(Integer tab_no) {
		this.tab_no = tab_no;
	}

	public List<TreeModel> getTreeList() {
		return treeList;
	}

	public void setTreeList(List<TreeModel> treeList) {
		this.treeList = treeList;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getOptionKey() {
		return optionKey;
	}

	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	public String getTreeKey() {
		return treeKey;
	}

	public void setTreeKey(String treeKey) {
		this.treeKey = treeKey;
	}

	public String getGallery_format() {
		return gallery_format;
	}

	public void setGallery_format(String gallery_format) {
		this.gallery_format = gallery_format;
	}

	public List<Map<String, String>> getGallery_list() {
		return gallery_list;
	}

	public void setGallery_list(List<Map<String, String>> gallery_list) {
		this.gallery_list = gallery_list;
	}

	public List<BootStrapCheckBoxModel> getCheckboxs() {
		return checkboxs;
	}

	public void setCheckboxs(List<BootStrapCheckBoxModel> checkboxs) {
		this.checkboxs = checkboxs;
	}

	public List<BootStrapRadioModel> getRadios() {
		return radios;
	}

	public void setRadios(List<BootStrapRadioModel> radios) {
		this.radios = radios;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}


	
	
}
