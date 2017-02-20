package org.ehais.model.gdrc;

import java.util.List;

public class GdrcCompanyList {

	private List<GdrcCompany> dataList;
	private Integer totalItem;
	public List<GdrcCompany> getDataList() {
		return dataList;
	}
	public void setDataList(List<GdrcCompany> dataList) {
		this.dataList = dataList;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	
}
