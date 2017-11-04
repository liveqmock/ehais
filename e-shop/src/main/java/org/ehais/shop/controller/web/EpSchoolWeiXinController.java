package org.ehais.shop.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ehais.common.EConstants;
import org.ehais.epublic.mapper.EHaiUsersMapper;
import org.ehais.epublic.model.EHaiUsers;
import org.ehais.shop.controller.ehais.EhaisCommonController;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.ResourceUtil;
import org.ehais.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/")
public class EpSchoolWeiXinController extends EhaisCommonController {

	protected Integer default_store_id = Integer.valueOf(ResourceUtil.getProValue("default_store_id"));
	protected Integer default_public_id = Integer.valueOf(ResourceUtil.getProValue("default_public_id"));
	protected String website = ResourceUtil.getProValue("website");
	protected String defaultimg = ResourceUtil.getProValue("defaultimg");
	protected String weixin_appid = ResourceUtil.getProValue("weixin_appid");
	protected String weixin_appsecret = ResourceUtil.getProValue("weixin_appsecret");
	protected String weixin_token = ResourceUtil.getProValue("weixin_token");
	
	
	@Autowired
	private EHaiUsersMapper eHaiUsersMapper;
	
	@RequestMapping("/ep_school_begoff")
	public String begoff(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code) {	
	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_begoff");
				}else if(StringUtils.isNotBlank(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, default_store_id);
					if(user == null || user.getSubscribe() == null || user.getSubscribe().intValue() == 0){
						//进入关注页面
						return "/epschoo/webl/subscribe";
					}else{
						return "/epschoo/webl/begoff";
					}
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return "/epschoo/webl/begoff";
	}
	
	
	
	@RequestMapping("/ep_school_bind")
	public String bind(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_begoff");
				}else if(StringUtils.isNotBlank(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, default_store_id);
					if(user.getSubscribe() == null || user.getSubscribe().intValue() == 0){
						//进入关注页面
						return "/epschoo/webl/subscribe";
					}else{
						return "/epschoo/webl/begoff";
					}
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/epschoo/webl/begbind";
	}
	
	
	
	@RequestMapping("/ep_school_begapprove")
	public String begapprove(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value = "begid", required = true) Integer begid,
			@RequestParam(value = "code", required = false) String code ) throws Exception {	
		try{
			if(this.isWeiXin(request)){
				if(StringUtils.isBlank(code)){
					return this.redirect_wx_authorize(request , weixin_appid , "/ep_school_begoff");
				}else if(StringUtils.isNotBlank(code)){
					EHaiUsers user = this.saveUserByOpenIdInfo(request, code, default_store_id);
					if(user.getSubscribe() == null || user.getSubscribe().intValue() == 0){
						//进入关注页面
						return "/epschoo/webl/subscribe";
					}else{
						return "/epschoo/webl/begoff";
					}
				}
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/epschoo/webl/begapprove";
	}
	
	
	
	
	@RequestMapping("/admin/ep_school_user_list")
	public String user_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		
		return "/ep_school/user/user_list";
	}
	
	
	@RequestMapping("/admin/ep_school_user_list_json")
	public String user_list_json(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "keyword", required = false) String keyword
			) {
		ReturnObject<EHaiUsers> rm = new ReturnObject<EHaiUsers>();
		rm.setCode(0);
		try{
			Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
			List<EHaiUsers> list = eHaiUsersMapper.selectUsersLike(store_id, keyword, "user_id", condition.getStart(), condition.getRows());
			Integer total = eHaiUsersMapper.selectUsersLikeCount(store_id, keyword);
			rm.setCode(1);
			rm.setRows(list);
			rm.setTotal(total);
			return this.writeJson(rm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.writeJsonObject(new HashMap<String,Object>(){{this.put("code", 0);this.put("msg", "fail");}});
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/admin/ep_school_excel.upd", method = RequestMethod.POST)
	public String saveFile(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response
	) {

		ReturnObject<Object> rm = new ReturnObject<Object>();
		rm.setCode(0);
		try {
			
			String req = UploadUtils.upload_file(request, response);
			System.out.println("上传路径："+req);
			JSONObject json = JSONObject.fromObject(req);
			System.out.println(json.getString("msg"));
			
	        int iIndex = json.getString("msg").lastIndexOf(".");
	        String ext = (iIndex < 0) ? "" : json.getString("msg").substring(iIndex + 1).toLowerCase();
	        if (!"xls,xlsx".contains(ext) || "".contains(ext)) {
	            System.out.println("文件类型不是EXCEL！");
	        }
	        
	        boolean isE2007 = false;    //判断是否是excel2007格式 
	        if(ext.endsWith("xlsx")) isE2007 = true;  
	        InputStream input = new FileInputStream(new File(json.getString("msg")));  //建立输入流  
	        Workbook wb  = null;  
            //根据文件格式(2003或者2007)来初始化  
            if(isE2007){
            	wb = new XSSFWorkbook(input);  
            }else{
            	wb = new HSSFWorkbook(input); 
            }
            
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器  
            while (rows.hasNext()) {  
                Row row = rows.next();  //获得行数据  
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始  
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器  
                while (cells.hasNext()) {  
                    Cell cell = cells.next();  
                    System.out.println("Cell #" + cell.getColumnIndex());  
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据  
                    case HSSFCell.CELL_TYPE_NUMERIC:  
                        System.out.println(cell.getNumericCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_STRING:  
                        System.out.println(cell.getStringCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_BOOLEAN:  
                        System.out.println(cell.getBooleanCellValue());  
                        break;  
                    case HSSFCell.CELL_TYPE_FORMULA:  
                        System.out.println(cell.getCellFormula());  
                        break;  
                    default:  
                        System.out.println("unsuported sell type");  
                    break;  
                    }  
                }  
            }  
            
	        
			return this.writeJson(rm);
		} catch (Exception e) {
			log.error("上传文件失败.", e);

		}

		return null;
	}
	
	
	
}
