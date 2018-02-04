package org.ehais.shop.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ehais.common.EConstants;
import org.ehais.controller.CommonController;
import org.ehais.shop.mapper.HaiIntegralRecordMapper;
import org.ehais.shop.model.HaiIntegralRecord;
import org.ehais.shop.model.HaiIntegralRecordExample;
import org.ehais.tools.EConditionObject;
import org.ehais.tools.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 积分
 * @author lgj628
 *
 */
@Controller
@RequestMapping("/ws")
public class IntegralWSController extends CommonController{

	@Autowired
	private HaiIntegralRecordMapper haiIntegralRecordMapper;
	
	@ResponseBody
	@RequestMapping("/integral_list")
	public String integral_list(ModelMap modelMap,
			HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute EConditionObject condition) {
		Long userId = (Long)request.getSession().getAttribute(EConstants.SESSION_USER_ID);
		try{
			ReturnObject<HaiIntegralRecord> rm = new ReturnObject<HaiIntegralRecord>();
			HaiIntegralRecordExample exp = new HaiIntegralRecordExample();
			exp.createCriteria().andUserIdEqualTo(userId);
			exp.setLimitStart(condition.getStart());
			exp.setLimitEnd(condition.getRows());
			exp.setOrderByClause("add_time desc");
			
			List<HaiIntegralRecord> list = haiIntegralRecordMapper.selectByExample(exp);
			long count = haiIntegralRecordMapper.countByExample(exp);
			
			rm.setRows(list);
			rm.setTotal(count);
			rm.setCode(1);
			
			return this.writeJson(rm);
		}catch(Exception e){
			e.printStackTrace();
			log.error("users", e);
		}
		return null;
	}
	
	
	
}
