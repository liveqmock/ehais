package org.ehais.shop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.model.EIDCodeNameModel;
import org.ehais.model.TreeModel;
import org.ehais.shop.mapper.HaiAccountMapper;
import org.ehais.shop.mapper.HaiAccountingMapper;
import org.ehais.shop.mapper.HaiBusinessMapper;
import org.ehais.shop.mapper.HaiBusinessTypeMapper;
import org.ehais.shop.mapper.HaiCategoryMapper;
import org.ehais.shop.mapper.HaiEmployeeMapper;
import org.ehais.shop.mapper.HaiExpensesMapper;
import org.ehais.shop.mapper.HaiGoodsMapper;
import org.ehais.shop.mapper.HaiIncomeMapper;
import org.ehais.shop.mapper.HaiLabourMapper;
import org.ehais.shop.mapper.HaiPaymentMapper;
import org.ehais.shop.mapper.HaiPropertyMapper;
import org.ehais.shop.mapper.HaiSectorsMapper;
import org.ehais.shop.mapper.HaiUnitMapper;
import org.ehais.shop.mapper.HaiWarehouseMapper;
import org.ehais.shop.model.HaiAccount;
import org.ehais.shop.model.HaiAccountExample;
import org.ehais.shop.model.HaiAccounting;
import org.ehais.shop.model.HaiAccountingExample;
import org.ehais.shop.model.HaiBusiness;
import org.ehais.shop.model.HaiBusinessExample;
import org.ehais.shop.model.HaiBusinessType;
import org.ehais.shop.model.HaiBusinessTypeExample;
import org.ehais.shop.model.HaiCategory;
import org.ehais.shop.model.HaiCategoryExample;
import org.ehais.shop.model.HaiEmployee;
import org.ehais.shop.model.HaiEmployeeExample;
import org.ehais.shop.model.HaiExpenses;
import org.ehais.shop.model.HaiExpensesExample;
import org.ehais.shop.model.HaiGoods;
import org.ehais.shop.model.HaiGoodsExample;
import org.ehais.shop.model.HaiIncome;
import org.ehais.shop.model.HaiIncomeExample;
import org.ehais.shop.model.HaiLabour;
import org.ehais.shop.model.HaiLabourExample;
import org.ehais.shop.model.HaiPayment;
import org.ehais.shop.model.HaiPaymentExample;
import org.ehais.shop.model.HaiProperty;
import org.ehais.shop.model.HaiPropertyExample;
import org.ehais.shop.model.HaiSectors;
import org.ehais.shop.model.HaiUnit;
import org.ehais.shop.model.HaiUnitExample;
import org.ehais.shop.model.HaiWarehouse;
import org.ehais.shop.model.HaiWarehouseExample;
import org.ehais.tools.ReturnObject;
import org.ehais.util.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin/baseapi/")
public class TreeDataAdminController extends CommonController{
	
	
	@Autowired
	private HaiSectorsMapper haiSectorsMapper;
	@Autowired
	private HaiBusinessMapper haiBusinessMapper;
	@Autowired
	private HaiBusinessTypeMapper haiBusinessTypeMapper;
	@Autowired
	private HaiCategoryMapper haiCategoryMapper;
	@Autowired
	private HaiGoodsMapper haiGoodsMapper;
	@Autowired
	private HaiUnitMapper haiUnitMapper;
	@Autowired
	private HaiPaymentMapper haiPaymentMapper;
	@Autowired
	private HaiLabourMapper haiLabourMapper;
	@Autowired
	private HaiWarehouseMapper haiWarehouseMapper; 
	@Autowired
	private HaiEmployeeMapper haiEmployeeMapper; 
	@Autowired
	private HaiAccountMapper haiAccountMapper; 
	@Autowired
	private HaiIncomeMapper haiIncomeMapper; 
	@Autowired
	private HaiExpensesMapper haiExpensesMapper; 
	@Autowired
	private HaiPropertyMapper haiPropertyMapper; 
	@Autowired
	private HaiAccountingMapper haiAccountingMapper; 
	
	
	
	//销售类型信息
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
				tree.add(new TreeModel(haiSectors.getSectorsId(),haiSectors.getSectorsCode(),haiSectors.getSectorsName(),haiSectors.getParentId() == null ? 0 : haiSectors.getParentId()));
			}
			TreeUtil treeUtil = new TreeUtil();
			treeUtil.setTreeList(tree);
			treeUtil.getTree(0);
			rm.setRows(treeUtil.getTreeNewList());
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}

	//客户、供应商信息
	@ResponseBody
	@RequestMapping("/business_{classify}.api")
	public String business_api(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "classify") String classify,
			@RequestParam(value = "code", required = true) String code) {
		ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
		rm.setCode(0);
		List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			HaiBusinessExample exp = new HaiBusinessExample();
			exp.createCriteria().andClassifyEqualTo(classify).andStoreIdEqualTo(store_id).andBusinessCodeLike("%"+code.toUpperCase()+"%");
			exp.or().andClassifyEqualTo(classify).andStoreIdEqualTo(store_id).andBusinessNameLike("%"+code+"%");
			List<HaiBusiness> list = haiBusinessMapper.selectByExample(exp);
			for (HaiBusiness m : list) {
				icnList.add(new EIDCodeNameModel(m.getBusinessId().toString(),m.getBusinessCode(),m.getBusinessName(),null));
			}
			
			rm.setRows(icnList);
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	//客户、供应商类别
	@ResponseBody
	@RequestMapping("/business_type_{classify}.api")
	public String business_type_api(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@PathVariable(value = "classify") String classify,
			@RequestParam(value = "code", required = true) String code) {
		ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
		rm.setCode(0);
		List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			HaiBusinessTypeExample exp = new HaiBusinessTypeExample();
			exp.createCriteria().andClassifyEqualTo(classify).andStoreIdEqualTo(store_id).andBusinessTypeCodeLike("%"+code.toUpperCase()+"%");
			exp.or().andClassifyEqualTo(classify).andStoreIdEqualTo(store_id).andBusinessTypeNameLike("%"+code+"%");
			List<HaiBusinessType> list = haiBusinessTypeMapper.selectByExample(exp);
			for (HaiBusinessType m : list) {
				icnList.add(new EIDCodeNameModel(m.getBusinessTypeId().toString(),m.getBusinessTypeCode(),m.getBusinessTypeName(),null));
			}			
			rm.setRows(icnList);
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
	

	//客户、供应商类别
	@ResponseBody
	@RequestMapping("/category.api")
	public String category_api(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = true) String code) {
		ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
		rm.setCode(0);
		List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			HaiCategoryExample exp = new HaiCategoryExample();
			exp.createCriteria().andStoreIdEqualTo(store_id).andCatCodeLike("%"+code.toUpperCase()+"%");
			exp.or().andStoreIdEqualTo(store_id).andCatNameLike("%"+code+"%");
			List<HaiCategory> list = haiCategoryMapper.selectByExample(exp);
			for (HaiCategory m : list) {
				icnList.add(new EIDCodeNameModel(m.getCatId().toString(),m.getCatCode(),m.getCatName(),null));
			}			
			rm.setRows(icnList);
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	/**
	 * 当前cat_id是排除条件
	 * @param modelMap
	 * @param request
	 * @param response
	 * @param cat_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/admin_category.api")
	public String admin_category_api(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "cat_id", required = false) Integer cat_id,
			@RequestParam(value = "parent_id", required = false) Integer parent_id) {
		ReturnObject<TreeModel> rm = new ReturnObject<TreeModel>();
		rm.setCode(0);
		List<TreeModel> icnList = new ArrayList<TreeModel>();
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		try {
			HaiCategoryExample exp = new HaiCategoryExample();
			HaiCategoryExample.Criteria c = exp.createCriteria();
			c.andStoreIdEqualTo(store_id);
//			if(cat_id > 0) {
//				c.andCatIdNotEqualTo(cat_id);
//				c.andParentIdNotEqualTo(cat_id);
//			}
			List<HaiCategory> list = haiCategoryMapper.selectByExample(exp);
			for (HaiCategory m : list) {
				icnList.add(new TreeModel(m.getCatId(),m.getCatCode(),m.getCatName(),m.getParentId()));
			}			
			rm.setRows(icnList);
			rm.setCode(1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.writeJson(rm);
	}
	
	
		//商品信息
		@ResponseBody
		@RequestMapping("/goods.api")
		public String goods_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiGoodsExample exp = new HaiGoodsExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andGoodsCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andGoodsNameLike("%"+code+"%");
				List<HaiGoods> list = haiGoodsMapper.selectByExample(exp);
				for (HaiGoods m : list) {
					icnList.add(new EIDCodeNameModel(m.getGoodsId().toString(),m.getGoodsCode(),m.getGoodsName(),null));
				}			
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		@ResponseBody
		@RequestMapping("/order_goods.api")
		public String order_goods_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				
							
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//计量单位信息
		@ResponseBody
		@RequestMapping("/unit.api")
		public String unit_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiUnitExample exp = new HaiUnitExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andUnitCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andUnitNameLike("%"+code+"%");
				List<HaiUnit> list = haiUnitMapper.selectByExample(exp);
				for (HaiUnit m : list) {
					icnList.add(new EIDCodeNameModel(m.getUnitId().toString(),m.getUnitCode(),m.getUnitName(),null));
				}			
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//结算信息
		@ResponseBody
		@RequestMapping("/payment.api")
		public String payment_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiPaymentExample exp = new HaiPaymentExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andPayCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andPayNameLike("%"+code+"%");
				List<HaiPayment> list = haiPaymentMapper.selectByExample(exp);
				for (HaiPayment m : list) {
					icnList.add(new EIDCodeNameModel(m.getPayId().toString(),m.getPayCode(),m.getPayName(),null));
				}			
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//劳务信息
		@ResponseBody
		@RequestMapping("/labour.api")
		public String labour_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiLabourExample exp = new HaiLabourExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andLabourCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andLabourNameLike("%"+code+"%");
				List<HaiLabour> list = haiLabourMapper.selectByExample(exp);
				for (HaiLabour m : list) {
					icnList.add(new EIDCodeNameModel(m.getLabourId().toString(),m.getLabourCode(),m.getLabourName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//仓库信息
		@ResponseBody
		@RequestMapping("/warehouse.api")
		public String warehouse_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiWarehouseExample exp = new HaiWarehouseExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andWarehouseCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andWarehouseNameLike("%"+code+"%");
				List<HaiWarehouse> list = haiWarehouseMapper.selectByExample(exp);
				for (HaiWarehouse m : list) {
					icnList.add(new EIDCodeNameModel(m.getWarehouseId().toString(),m.getWarehouseCode(),m.getWarehouseName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		
		//职员信息
		@ResponseBody
		@RequestMapping("/employee.api")
		public String employee_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiEmployeeExample exp = new HaiEmployeeExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andEmployeeCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andEmployeeNameLike("%"+code+"%");
				List<HaiEmployee> list = haiEmployeeMapper.selectByExample(exp);
				for (HaiEmployee m : list) {
					icnList.add(new EIDCodeNameModel(m.getEmployeeId().toString(),m.getEmployeeCode(),m.getEmployeeName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//账户信息
		@ResponseBody
		@RequestMapping("/account.api")
		public String account_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiAccountExample exp = new HaiAccountExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andAccountCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andAccountNameLike("%"+code+"%");
				List<HaiAccount> list = haiAccountMapper.selectByExample(exp);
				for (HaiAccount m : list) {
					icnList.add(new EIDCodeNameModel(m.getAccountId().toString(),m.getAccountCode(),m.getAccountName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//收入信息
		@ResponseBody
		@RequestMapping("/income.api")
		public String income_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiIncomeExample exp = new HaiIncomeExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andIncomeCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andIncomeNameLike("%"+code+"%");
				List<HaiIncome> list = haiIncomeMapper.selectByExample(exp);
				for (HaiIncome m : list) {
					icnList.add(new EIDCodeNameModel(m.getIncomeId().toString(),m.getIncomeCode(),m.getIncomeName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		//费用信息
		@ResponseBody
		@RequestMapping("/expenses.api")
		public String expenses_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiExpensesExample exp = new HaiExpensesExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andExpensesCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andExpensesNameLike("%"+code+"%");
				List<HaiExpenses> list = haiExpensesMapper.selectByExample(exp);
				for (HaiExpenses m : list) {
					icnList.add(new EIDCodeNameModel(m.getExpensesId().toString(),m.getExpensesCode(),m.getExpensesName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		//固定资产信息
		@ResponseBody
		@RequestMapping("/property.api")
		public String property_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiPropertyExample exp = new HaiPropertyExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andPropertyCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andPropertyNameLike("%"+code+"%");
				List<HaiProperty> list = haiPropertyMapper.selectByExample(exp);
				for (HaiProperty m : list) {
					icnList.add(new EIDCodeNameModel(m.getPropertyId().toString(),m.getPropertyCode(),m.getPropertyName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		//会计科目信息
		@ResponseBody
		@RequestMapping("/accounting.api")
		public String accounting_api(ModelMap modelMap,
				HttpServletRequest request,HttpServletResponse response,
				@RequestParam(value = "code", required = true) String code) {
			ReturnObject<EIDCodeNameModel> rm = new ReturnObject<EIDCodeNameModel>();
			rm.setCode(0);
			List<EIDCodeNameModel> icnList = new ArrayList<EIDCodeNameModel>();
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			try {
				HaiAccountingExample exp = new HaiAccountingExample();
				exp.createCriteria().andStoreIdEqualTo(store_id).andAccountingCodeLike("%"+code.toUpperCase()+"%");
				exp.or().andStoreIdEqualTo(store_id).andAccountingNameLike("%"+code+"%");
				List<HaiAccounting> list = haiAccountingMapper.selectByExample(exp);
				for (HaiAccounting m : list) {
					icnList.add(new EIDCodeNameModel(m.getAccountingId().toString(),m.getAccountingCode(),m.getAccountingName(),null));
				}
				rm.setRows(icnList);
				rm.setCode(1);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return this.writeJson(rm);
		}
		
		
		
	
}
