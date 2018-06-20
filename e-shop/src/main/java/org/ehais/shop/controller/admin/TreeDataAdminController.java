package org.ehais.shop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.controller.CommonController;
import org.ehais.model.TreeModel;
import org.ehais.shop.mapper.HaiSectorsMapper;
import org.ehais.shop.model.HaiSectors;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class TreeDataAdminController extends CommonController{
	
	
	@Autowired
	private HaiSectorsMapper haiSectorsMapper;
	
	
	@ResponseBody
	@RequestMapping("/sectors.api")
	public String sectors_api(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		rm.setCode(0);
		List<TreeModel> tree = new ArrayList<TreeModel>();
		try {
			List<HaiSectors> list = haiSectorsMapper.selectByExample(null);
			for (HaiSectors haiSectors : list) {
				tree.add(new TreeModel(haiSectors.getSectorsId(),haiSectors.getSectorsCode(),haiSectors.getSectorsName(),haiSectors.getParentId()));
			}
			rm.setRows(tree);
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return this.writeJson(rm);
		
		
	}

}
