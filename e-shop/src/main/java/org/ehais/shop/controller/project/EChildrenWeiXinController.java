package org.ehais.shop.controller.project;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.mapper.HaiIntegralRecordMapper;
import org.ehais.shop.model.HaiIntegralRecord;
import org.ehais.shop.model.HaiIntegralRecordExample;
import org.ehais.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/")
public class EChildrenWeiXinController extends CommonController{

	Integer store_id = 1;
	
	@Autowired
	private HaiIntegralRecordMapper haiIntegralRecordMapper;
	
	/**
	 * http://a74f1eff.ngrok.io/children_integral
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/children_integral")
	public String integral(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response) {
		
		Integer store_id = (Integer)request.getSession().getAttribute(EConstants.SESSION_STORE_ID);
		String open_id = (String)request.getSession().getAttribute(EConstants.SESSION_OPEN_ID);
		Long user_id = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		
		System.out.println("store_id:"+store_id);
		System.out.println("open_id:"+open_id);
		System.out.println("user_id:"+user_id);
		
		if(store_id == null || store_id == 0 
				|| StringUtils.isBlank(open_id) || StringUtils.isEmpty(open_id)
				|| user_id == null || user_id == 0L ) {
			return "redirect:"+request.getScheme()+"://"+request.getServerName()+"/gowx/1/snsapi_userinfo/children_integral.=";
		}
		
		
		long count = haiIntegralRecordMapper.countIntegral(user_id, "children", DateUtil.formatDate(new Date(), DateUtil.FORMATSTR_3));
		if(count >= 3) {
			//满次数与无权限积分
			return "/children/user_not_integral";
		}
		
		
		//用户积分增加
		HaiIntegralRecord integralRecord = new HaiIntegralRecord();
		integralRecord.setUserId(user_id);
		integralRecord.setTableName("children");
		integralRecord.setTableId(0L);
		integralRecord.setIntegral(10);
		integralRecord.setAddTime(new Date());
		integralRecord.setIntegralIntro("儿童中心活动积分");
		
		haiIntegralRecordMapper.insert(integralRecord);
		
		return "/children/integral";
	}
	
	
	
}
