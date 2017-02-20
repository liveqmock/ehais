package org.ehais.util;

import java.util.ArrayList;
import java.util.List;

import org.ehais.model.TreeModel;

public class TreeUtil {

	private List<TreeModel> treeList;
	private List<TreeModel> treeNewList;
	
	private Integer level;
	
	
	
	public TreeUtil() {
		super();
		// TODO Auto-generated constructor stub
		treeNewList = new ArrayList<TreeModel>();
		level = 0;
	}
	
	public void getTree(Integer pid){
		List<TreeModel> tList = this.getChild(pid);
		if(tList != null && tList.size() > 0){
			level ++;
			for (TreeModel treeModel : tList) {
				treeModel.setLevel(level);
				treeNewList.add(treeModel);
				this.getTree(treeModel.getId());
			}
			level --;
		}
	}
	
	
	private List<TreeModel> getChild(Integer pid){
		List<TreeModel> tl = new ArrayList<TreeModel>();
		for (TreeModel treeModel : treeList) {
			if(treeModel.getParent_id().intValue() == pid.intValue()){
				tl.add(treeModel);
			}
		}
		return tl;
	}

	public List<TreeModel> getTreeNewList() {
		return treeNewList;
	}

	public void setTreeList(List<TreeModel> treeList) {
		this.treeList = treeList;
	}

	public List<TreeModel> getTreeList() {
		return treeList;
	}
	
	
	
}
