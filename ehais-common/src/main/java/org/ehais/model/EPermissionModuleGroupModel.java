package org.ehais.model;

import java.util.List;

public class EPermissionModuleGroupModel {

	private String name;
	private List<EPermissionControllerModel> listController;
	
	public EPermissionModuleGroupModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EPermissionModuleGroupModel(String name, List<EPermissionControllerModel> listController) {
		super();
		this.name = name;
		this.listController = listController;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<EPermissionControllerModel> getListController() {
		return listController;
	}
	public void setListController(List<EPermissionControllerModel> listController) {
		this.listController = listController;
	}
	
	
	
	
}
