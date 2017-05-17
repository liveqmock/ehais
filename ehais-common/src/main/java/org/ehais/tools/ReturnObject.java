package org.ehais.tools;

import java.util.List;
import java.util.Map;

import org.ehais.model.BootStrapModel;
import org.ehais.model.ExtendsField.ExtendsFieldsTabs;
import org.ehais.model.ExtendsField.ExtendsFieldsXml;
import org.springframework.validation.ObjectError;

public class ReturnObject<T> {
	
	/**
	 * 默认开始页码
	 */
	private static final int DEFAULT_PAGE = 1;
	/**
	 * 页面显示的条数
	 */
	public static int DEFAULT_PAGE_SIZE = 15;

	private Integer pageSize = DEFAULT_PAGE_SIZE;
	private Integer currentPage = DEFAULT_PAGE;
	private Integer pageCount = 0;
	
	private Integer code;//返回值,1:成功,非1则异常，参考异常码附件
	private String msg;//返回的值
	private String[] msgs;//多信息返回
	private Map<String, Object> map;
	private List<T> rows;//兼容easyui datagirl
	private T model;
	private Integer total = 0;//兼容easyui的总记录数
	private List<ExtendsFieldsTabs> extendsFieldsTabs;//带tab的自动列表
	private List<BootStrapModel> bootStrapList;
	private String action;
	private List<ObjectError> errorList;//多个错误信息返回
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setTotal(Long total) {
		this.total = total.intValue();
	}
	public List<BootStrapModel> getBootStrapList() {
		return bootStrapList;
	}
	public void setBootStrapList(List<BootStrapModel> bootStrapList) {
		this.bootStrapList = bootStrapList;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<ExtendsFieldsTabs> getExtendsFieldsTabs() {
		return extendsFieldsTabs;
	}
	public void setExtendsFieldsTabs(List<ExtendsFieldsTabs> extendsFieldsTabs) {
		this.extendsFieldsTabs = extendsFieldsTabs;
	}
	public String[] getMsgs() {
		return msgs;
	}
	public void setMsgs(String[] msgs) {
		this.msgs = msgs;
	}
	public List<ObjectError> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<ObjectError> errorList) {
		this.errorList = errorList;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public Integer getPageCount() {
		return (pageCount > 0 ? pageCount : (((total / pageSize) + ((total % pageSize) > 0 ? 1 : 0)))) ;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	
	
	
}