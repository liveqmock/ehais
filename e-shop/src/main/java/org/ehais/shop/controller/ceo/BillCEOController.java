package org.ehais.shop.controller.ceo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.annotation.EPermissionController;
import org.ehais.annotation.EPermissionMethod;
import org.ehais.annotation.EPermissionModuleGroup;
import org.ehais.controller.CommonController;
import org.ehais.epublic.mapper.HaiOrderInfoMapper;
import org.ehais.epublic.model.HaiOrderInfoWithBLOBs;
import org.ehais.protocol.PermissionProtocol;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.ehais.util.FSO;
import org.ehais.util.ResourceUtil;
import org.ehais.util.UploadUtils;
import org.ehais.weixin.utils.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@EPermissionModuleGroup(name="模组")

@EPermissionController(intro="对帐功能",value="BillCEOController")
@Controller
@RequestMapping("/ceo")
public class BillCEOController extends CommonController{
	private static Logger log = LoggerFactory.getLogger(BillCEOController.class);

	protected String weixin_appid = "wxb7e05d362dab27b1";//ResourceUtil.getProValue("weixin_appid");
	protected String weixin_mch_id = ResourceUtil.getProValue("weixin_mch_id");
	protected String weixin_mch_secret = ResourceUtil.getProValue("weixin_mch_secret");
	
	
	@Autowired
	private HaiOrderInfoMapper haiOrderInfoMapper;
	
	@EPermissionMethod(name="查询",intro="打开对帐页面",value="haiBillView",relation="haiBillListJson",type=PermissionProtocol.URL)
	@RequestMapping("/haiBillView")
	public String haiBillView(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response ) {	
		try{
//			modelMap.addAttribute("bill_date", DateUtil.formatDate(new Date(), DateUtil.FORMATSTR_3));
			modelMap.addAttribute("bill_date", new Date());
			return "/ceo/bill/view";
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJump(modelMap, e.getMessage());
		}
		
	}
	
	@ResponseBody
	@EPermissionMethod(intro="返回对帐管理数据",value="haiBillListJson",type=PermissionProtocol.JSON)
	@RequestMapping(value="/haiBillListJson",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	public String haiBillListJson(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition,
			@RequestParam(value = "bill_date", required = true) String bill_date) {
		ReturnObject<Object> rm = new ReturnObject<Object>();
		try{
			bill_date = bill_date.replaceAll("-", "");
			String content = WeiXinUtil.downloadbill(weixin_appid, weixin_mch_id, bill_date, weixin_mch_secret);
			rm.setMsg(content);
			rm.setCode(1);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJSON(e);
		}
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/weixin_import_csv.upd", method = RequestMethod.POST)
	public String weixin_import_csv(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "bill_date", required = false) String bill_date
	) {
		
		bill_date = "2018-02-16";
		
		try{
			String req = UploadUtils.upload_file(request, response);
			System.out.println("上传路径："+req);
			JSONObject json = JSONObject.fromObject(req);
			System.out.println(json.getString("msg"));
			
			
			int iIndex = json.getString("msg").lastIndexOf(".");
	        String ext = (iIndex < 0) ? "" : json.getString("msg").substring(iIndex + 1).toLowerCase();
	        if (!"csv".contains(ext) || "".contains(ext)) {
	            System.out.println("文件类型不是微信对帐单CSV文件！");
	        }
	        
	        DataInputStream in = new DataInputStream(new FileInputStream(new File(json.getString("msg"))));
	        BufferedReader br= new BufferedReader(new InputStreamReader(in,"GBK"));
	        
	        String line = "";
	        String everyLine = "";
	        List<String> allString = new ArrayList<>();
	        int i = 0 ;
	        while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                everyLine = line;
                System.out.println(everyLine);
                if(i>4)allString.add(everyLine);
                i++;
            }
            System.out.println("csv表格中所有行数："+allString.size());
            br.close();
            
            FSO.deletefile(json.getString("msg"));//将缓存文件删除
	        
            ReturnObject<Object> rm = this.compareBill(allString, bill_date);
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("partner", e);
			return this.errorJSON(e);
		}
	}
	
	
	private ReturnObject<Object> compareBill(List<String> allString,String bill_date) throws Exception{
		ReturnObject<Object> rm = new ReturnObject<Object>();
		
		List<HaiOrderInfoWithBLOBs> list = haiOrderInfoMapper.listOrderInfoAddTimeBill(bill_date);
		
		boolean f = false;
		Long dAmount = 0L ;
		//微信帐单与线上帐单对帐
		for(String str : allString){
			f = false;
			String[] bill = str.split(",");
			for (HaiOrderInfoWithBLOBs order : list) {
				if(bill[2].replaceAll("`", "").equals(order.getOrderSn()) && bill[4].equals("买家已支付")) {
					f = true;
					//可能出现的情况：1.无更新状态
					if(order.getPayStatus()!=1) {
						System.out.println(order.getOrderId()+":"+order.getOrderSn()+"状态不正确,金额"+order.getOrderAmount());
						dAmount += order.getOrderAmount();
					}
					if( Double.valueOf(order.getOrderAmount() / 100).doubleValue() != Double.parseDouble(bill[5]) ) {
						System.out.println(order.getOrderId()+":"+order.getOrderSn()+"金额不正确");
					}
					
					break;
				}
			}
		}
		
		System.out.println("差额为"+dAmount);
				
		//线上帐单与微信对帐
		for (HaiOrderInfoWithBLOBs order : list) {
			if(order.getPayStatus().intValue()==1) {
				for(String str : allString) {
					String[] bill = str.split(",");
				}
			}
			
		}
		
		
		
		
		return rm;
	}
	
}
