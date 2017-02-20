package org.ehais.model.gdrc;

import java.util.List;

public class GdrcJobList {

	private String resultCode;
	private String msg;
	private List<GdrcJob> dataList;
	private Integer totalItem;
	public List<GdrcJob> getDataList() {
		return dataList;
	}
	public void setDataList(List<GdrcJob> dataList) {
		this.dataList = dataList;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
